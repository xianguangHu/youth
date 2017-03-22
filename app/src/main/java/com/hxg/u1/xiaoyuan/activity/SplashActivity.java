package com.hxg.u1.xiaoyuan.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.hxg.u1.xiaoyuan.MyApplication;
import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.bean.Schools;
import com.hxg.u1.xiaoyuan.model.UserService;
import com.hxg.u1.xiaoyuan.utils.Constant;
import com.hxg.u1.xiaoyuan.utils.MainUtil;
import com.hxg.u1.xiaoyuan.utils.SharedPrefsUtil;
import com.hxg.u1.xiaoyuan.utils.StatusNetAsyncTask;

public class SplashActivity extends Activity {
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
                SharedPreferences sp = getPreferences(MODE_PRIVATE);
                boolean isFirst = sp.getBoolean("isFirst", true);
                Intent intent = new Intent();
                if (isFirst) {
                    sp.edit().putBoolean("isFirst", false).commit();
                    //第一次进入引导页
                    intent.setClass(SplashActivity.this, GuideActivity.class);
                } else {
                    intent.setClass(SplashActivity.this, WelcomeActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    //初始化用户数据  根据闪屏页时间停留
    private void initData() {
        if (AVUser.getCurrentUser() != null) {
            UserService.initUser(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e==null){
                        String school=SharedPrefsUtil.getValue(SplashActivity.this, Constant.FILE_NAME,"School","");
                        if (TextUtils.isEmpty(school)){
                            new StatusNetAsyncTask(SplashActivity.this) {
                                Schools schools;

                                @Override
                                protected void doInBack() throws Exception {
                                    schools = UserService.getSchool();
                                }

                                @Override
                                protected void onPost(Exception e) {
                                    //储存用户学校信息
                                    SharedPrefsUtil.putValue(SplashActivity.this,Constant.FILE_NAME,"School",schools.getSchoolName());
                                }
                            }.execute();
                        }
                    }else {
                        MainUtil.ToastUtil(MyApplication.getContext(),"用户初始化失败");
                    }
                }
            });

        }



    }
}
