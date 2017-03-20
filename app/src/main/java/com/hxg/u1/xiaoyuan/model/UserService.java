package com.hxg.u1.xiaoyuan.model;

import android.graphics.Bitmap;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.hxg.u1.xiaoyuan.bean.AvUser;
import com.hxg.u1.xiaoyuan.bean.Schools;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by huxianguang on 2017/3/12.
 * user Service
 */

public class UserService {
    /**
     * 初始化当前user对象
     * @throws AVException
     */
    public static void initUser() throws AVException {
        final AVUser user=AVUser.getCurrentUser();
        AVQuery<AVUser> query=new AVQuery<>("_User");
        query.include("schoolId");
        Schools schools= (Schools) query.find().get(0).get("schoolId");
        user.put("schoolId",schools);
    }
    public static AvUser queryUser() throws AVException {
        AVQuery<AvUser> user= AVObject.getQuery(AvUser.class);
        user.whereEqualTo("objectId",AVUser.getCurrentUser().getObjectId());
        List<AvUser> list=user.find();
        AvUser avUser=list.get(0);
        return avUser;
    }

    /**
     *  更新user头像
     * @param bitmap 头像
     * @param saveCallback
     */
    public static void updateUserImage(Bitmap bitmap, final CallbackService saveCallback){
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        byte[] bs = out.toByteArray();
        String name = AVUser.getCurrentUser().getUsername() + "_" + System.currentTimeMillis();
        final AVFile file = new AVFile(name, bs);
        file.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                AVObject user=AVObject.createWithoutData("_User",AVUser.getCurrentUser().getObjectId());
                user.put("imageuri",file.getUrl());
                user.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        saveCallback.callback(file.getUrl(),e);
                    }
                });
            }
        });
    }
}
