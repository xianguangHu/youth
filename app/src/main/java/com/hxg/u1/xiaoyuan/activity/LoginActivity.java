package com.hxg.u1.xiaoyuan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.model.Model;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends Activity {

    @BindView(R.id.login_return)
    ImageView mLoginReturn;
    @BindView(R.id.login_phone)
    EditText mLoginPhone;
    @BindView(R.id.login_password)
    EditText mLoginPassword;
    @BindView(R.id.login_btn)
    Button mLoginBtn;
    @BindView(R.id.login_seek)
    TextView mLoginSeek;
    @BindView(R.id.login_register)
    TextView mLoginRegister;
    private String mPhone;
    private String mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {

    }

    @OnClick({R.id.login_return, R.id.login_btn, R.id.login_seek, R.id.login_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_return:
                finish();
                break;
            case R.id.login_btn:
                mPhone = mLoginPhone.getText().toString().trim();
                mPassword = mLoginPassword.getText().toString().trim();
                Model.getInstance().getGlobalThreadpool().execute(new Runnable() {
                    @Override
                    public void run() {
                        AVUser.logInInBackground(mPhone, mPassword, new LogInCallback<AVUser>() {
                            @Override
                            public void done(AVUser avUser, AVException e) {
                                if (e==null){
                                    //登陆成功
                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                }else {
                                }
                            }
                        });
                    }
                });
                break;
            case R.id.login_seek:
                break;
            case R.id.login_register:
                break;
        }
    }
}
