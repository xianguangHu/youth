package com.hxg.u1.xiaoyuan.utils;

import android.text.TextUtils;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.hxg.u1.xiaoyuan.MyApplication;

import java.util.List;

/**
 * Created by huxianguang on 2017/3/29.
 * 判断String工具类
 */

public class StringUtil {
    /**
     * 判断登陆是否合法
     *
     * @param phone
     * @param password
     * @return
     */
    public static boolean isLogin(String phone, String password) {
        if (TextUtils.isEmpty(phone)) {
            MainUtil.ToastUtil(MyApplication.getContext(), "请输入手机号!");
            return false;
        } else if (TextUtils.isEmpty(password)) {
            MainUtil.ToastUtil(MyApplication.getContext(), "请输入密码!");
            return false;
        } else if (!isMobile(phone)) {
            MainUtil.ToastUtil(MyApplication.getContext(), "手机号不合法，请输入正确的手机号!");
            return false;
        }
        return true;
    }

    /**
     * 判断注册是否合法
     *
     * @param phone
     * @param password
     * @return
     */
    public static boolean isRegister(String phone, String password) {
        if (TextUtils.isEmpty(phone)) {
            MainUtil.ToastUtil(MyApplication.getContext(), "手机号不能为空!");
            return false;
        } else if (TextUtils.isEmpty(password)) {
            MainUtil.ToastUtil(MyApplication.getContext(), "密码不能为空!");
            return false;
        } else if (password.length() <= 6) {
            MainUtil.ToastUtil(MyApplication.getContext(), "密码长度不能小于6位!");
            return false;
        }else if (!isMobile(phone)){
            MainUtil.ToastUtil(MyApplication.getContext(), "手机号不正确!");
            return false;
        }
        return true;
    }

    /**
     * 服务器查询是否手机号重复
     */
    public static void isPhone(String phone, final RegisterCallBack registerCallBack) {
        AVQuery query = new AVQuery("_User");
        query.whereEqualTo("mobilePhoneNumber", phone);
        query.findInBackground(new FindCallback() {
            @Override
            public void done(List list, AVException e) {
                if (e == null) {
                    if (list.size() ==0) {
                        //说明服务器端没有此号码 可以注册
                        registerCallBack.callBack();
                    } else {
                        Log.v("长度",list.size()+"");
                        MainUtil.ToastUtil(MyApplication.getContext(), "此号码已注册!");
                    }
                } else {
                    MainUtil.ToastUtil(MyApplication.getContext(),e+"");
                }
            }
        });
    }

    /**
     * 判断手机号是否合法
     *
     * @param number
     * @return
     */
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (number.length()==11) {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
        return false;
    }

    public interface RegisterCallBack {
        void callBack();
    }

}
