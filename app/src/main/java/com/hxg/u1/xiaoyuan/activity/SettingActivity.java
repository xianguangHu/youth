package com.hxg.u1.xiaoyuan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.avos.avoscloud.AVUser;
import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.utils.Constant;
import com.hxg.u1.xiaoyuan.utils.SharedPrefsUtil;
import com.hxg.u1.xiaoyuan.widgets.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.Setting_title_bar)
    TitleBar mSettingTitleBar;
    @BindView(R.id.setting_phone)
    RelativeLayout mSettingPhone;
    @BindView(R.id.setting_password)
    RelativeLayout mSettingPassword;
    @BindView(R.id.setting_about)
    RelativeLayout mSettingAbout;
    @BindView(R.id.setting_exit)
    LinearLayout mSettingExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        initTitle();
    }

    private void initTitle() {
        mSettingTitleBar.setLeftImageResource(R.mipmap.left);
        mSettingTitleBar.setLeftText("设置");
        mSettingTitleBar.setLeftTextColor(getResources().getColor(R.color.black));
        mSettingTitleBar.setBackgroundColor(getResources().getColor(R.color.white));
        mSettingTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.setting_phone, R.id.setting_password, R.id.setting_about, R.id.setting_exit})
    public void onClick(View view) {
        Intent intent=new Intent();
        switch (view.getId()) {
            case R.id.setting_phone://修改手机号码
                break;
            case R.id.setting_password://修改密码
                break;
            case R.id.setting_about://关于
                break;
            case R.id.setting_exit://登出
                Log.v("退出","success");
                AVUser.logOut();
                SharedPrefsUtil.putValue(SettingActivity.this, Constant.FILE_NAME,"School","");
                intent.setClass(SettingActivity.this,SplashActivity.class);
                startActivity(intent);
                finish();
                break;
        }

    }
}
