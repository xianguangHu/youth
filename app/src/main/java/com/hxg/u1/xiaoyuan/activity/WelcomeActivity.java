package com.hxg.u1.xiaoyuan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.baidu.location.BDLocation;
import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.utils.LocationUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WelcomeActivity extends Activity implements LocationUtil.LocationMap{


    @BindView(R.id.welcome_location)
    TextView mWelcomeLocation;
    @BindView(R.id.welcome_login)
    Button mWelcomeLogin;
    @BindView(R.id.welcome_register)
    Button mWelcomeRegister;

    private Handler mHandler;
    private String mAddrStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        mHandler=new Handler();
        LocationUtil locationUtil=new LocationUtil(WelcomeActivity.this);
        locationUtil.start();
        if (AVUser.getCurrentUser()!=null){
            startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
        }
    }


    @OnClick({R.id.welcome_login, R.id.welcome_register})
    public void onClick(View view) {
        Intent intent=new Intent();
        switch (view.getId()) {
            case R.id.welcome_login:
                intent.setClass(WelcomeActivity.this,LoginActivity.class);
                break;
            case R.id.welcome_register:
                intent.setClass(WelcomeActivity.this,RegisterActivity.class);
                break;
        }
        startActivity(intent);
    }

    @Override
    public void getLocation(BDLocation bdLocation) {
        mAddrStr = bdLocation.getAddrStr();
        new Thread(){
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mWelcomeLocation.setText(mAddrStr);
                    }
                });
            }
        }.start();
    }
}
