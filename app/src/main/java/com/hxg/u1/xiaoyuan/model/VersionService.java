package com.hxg.u1.xiaoyuan.model;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.ProgressCallback;
import com.hxg.u1.xiaoyuan.MyApplication;
import com.hxg.u1.xiaoyuan.bean.VersionInfo;
import com.hxg.u1.xiaoyuan.utils.VersionUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by huxianguang on 2017/3/22.
 * 检查版本更新
 */

public class VersionService {


    /**
     * 检查是否有新版本
     * @return true有
   */
    public static void isUpdate(final VersionUtil.UpdateListener updateListener){
        AVQuery<VersionInfo> query=AVObject.getQuery(VersionInfo.class);
        //获取最新的第一条数据
        query.orderByDescending("createdAt");
        query.include("versionFile");
        query.getFirstInBackground(new GetCallback<VersionInfo>() {
            @Override
            public void done(VersionInfo versionInfo, AVException e) {
                if (e==null){

                    int versionService=versionInfo.getVersionCode();
                    int versionCode=getPackageInfo(MyApplication.getContext()).versionCode;
                    if (versionService>versionCode){
                        //有版本更新
                        Log.v("=======","==========");
                        updateListener.onUpdateReturned(VersionUtil.YES,versionInfo);
                    }else {
                        Log.v("=======","====ewewewewe=====");
                        updateListener.onUpdateReturned(VersionUtil.NO,null);
                    }
                }
            }
        });
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }
    //下载文件
    public static void downloadFile(AVFile file, final UpdateProgress updateProgress){
        file.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, AVException e) {
                if (e==null){
                FileOutputStream fos=null;
                BufferedOutputStream bos=null;
                    File file=null;
                    try {
                    file = new File("/mnt/sdcard/youth.apk");
                    fos=new FileOutputStream(file);
                    bos=new BufferedOutputStream(fos);
                    bos.write(bytes);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }finally {
                    if (bos!=null){
                        try {
                            bos.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if (fos!=null){
                        try {
                            fos.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                }
            }
        }, new ProgressCallback() {
            @Override
            public void done(Integer integer) {
                //下载进度  0~100
                updateProgress.updateprogress(integer);
            }
        });

    }
    public interface UpdateProgress{
        void updateprogress(Integer integer);
    }

}
