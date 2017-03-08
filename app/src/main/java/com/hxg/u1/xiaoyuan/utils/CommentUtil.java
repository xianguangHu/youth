package com.hxg.u1.xiaoyuan.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by huxianguang on 2017/3/8.
 * 评论的工具类
 */

public class CommentUtil {
    //弹出键盘
    public static void showSoftInput(Context context,View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    //隐藏键盘
    public static void hideSoftInput(Context context,View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }
}
