package com.hxg.u1.xiaoyuan.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.bean.VersionInfo;
import com.hxg.u1.xiaoyuan.model.VersionService;

import java.io.File;

/**
 * Created by huxianguang on 2017/3/22.
 */

public class VersionUtil {
    public static final int YES=0;
    public static final int NO=1;

    /**
     * 回调接口
     */
    public interface UpdateListener{
        void onUpdateReturned(int updateStatus, VersionInfo versionInfo);
    }

    /**
     * 弹出新版本的提示
     * @param context 上下文
     */
    public static void showDialog(final Context context, final VersionInfo versionInfo){
        final Dialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCancelable(true);// 可以用“返回键”取消
        dialog.setCanceledOnTouchOutside(false);//
        dialog.show();
        View view = LayoutInflater.from(context).inflate(R.layout.version_update_dialog, null);
        dialog.setContentView(view);
        final Button btnOk = (Button) view.findViewById(R.id.btn_update_id_ok);
        Button btnCancel = (Button) view.findViewById(R.id.btn_update_id_cancel);
        TextView tvContent = (TextView) view.findViewById(R.id.tv_update_content);
        TextView tvUpdateTile = (TextView) view.findViewById(R.id.tv_update_title);
        TextView tvUpdateMsgSize = (TextView) view.findViewById(R.id.tv_update_msg_size);
        tvContent.setText(versionInfo.getVersionDesc());
        tvUpdateTile.setText("最新版本："+versionInfo.getVersionName());
        tvUpdateMsgSize.setText("新版本大小："+versionInfo.getVersionSize());

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //没有下载 开启下载
                final ProgressDialog progressDialog=new ProgressDialog(context);
                progressDialog.setTitle("正在下载...");
                progressDialog.setCanceledOnTouchOutside(true);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.show();
                VersionService.downloadFile(versionInfo.getVersionFile(), new VersionService.UpdateProgress() {
                    @Override
                    public void updateprogress(Integer integer) {
                        progressDialog.setProgress(integer);
                        if (integer==100){
                            progressDialog.dismiss();
                            Intent intent=getInstallIntent(Environment.getExternalStorageDirectory());
                            context.startActivity(intent);
                        }
                    }
                });
            }
        });
    }
    /**
     * 得到安装的intent
     * @param apkFile
     * @return
     */
    public static Intent getInstallIntent(File apkFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(apkFile,"youth.apk")),"application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

}

