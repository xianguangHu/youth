package com.hxg.u1.xiaoyuan.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.hxg.u1.xiaoyuan.R;

public class SplashActivity extends Activity {
    Handler mHandler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp=getPreferences(MODE_PRIVATE);
                boolean isFirst=sp.getBoolean("isFirst",true);
                Intent intent=new Intent();
                if (isFirst){
                    sp.edit().putBoolean("isFirst",false).commit();
                    //第一次进入引导页
                    intent.setClass(SplashActivity.this,GuideActivity.class);
                }else {
                    intent.setClass(SplashActivity.this,WelcomeActivity.class);
                }
                startActivity(intent);
                finish();
            }
        },3000);
    }
}
