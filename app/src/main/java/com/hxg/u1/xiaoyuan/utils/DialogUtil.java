package com.hxg.u1.xiaoyuan.utils;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by huxianguang on 2017/2/27.
 */

public class DialogUtil {
    public static ProgressDialog showSpinnerDialog(Activity activity){
        ProgressDialog dialog=new ProgressDialog(activity);
        dialog.setMessage("努力加载中...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(true);
        dialog.show();
        return dialog;
    }

}
