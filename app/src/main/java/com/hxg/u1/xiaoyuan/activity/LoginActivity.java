package com.hxg.u1.xiaoyuan.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.hxg.u1.xiaoyuan.MyApplication;
import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.bean.Schools;
import com.hxg.u1.xiaoyuan.model.Model;
import com.hxg.u1.xiaoyuan.model.UserService;
import com.hxg.u1.xiaoyuan.utils.Constant;
import com.hxg.u1.xiaoyuan.utils.DialogUtil;
import com.hxg.u1.xiaoyuan.utils.MainUtil;
import com.hxg.u1.xiaoyuan.utils.SharedPrefsUtil;
import com.hxg.u1.xiaoyuan.utils.StatusNetAsyncTask;
import com.hxg.u1.xiaoyuan.utils.StringUtil;

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
                ProgressDialog dialog= DialogUtil.showSpinnerDialog(LoginActivity.this);
                mPhone = mLoginPhone.getText().toString().trim();
                mPassword = mLoginPassword.getText().toString().trim();
                if (StringUtil.isLogin(mPhone,mPassword)){
                Model.getInstance().getGlobalThreadpool().execute(new Runnable() {
                    @Override
                    public void run() {
                        AVUser.loginByMobilePhoneNumberInBackground(mPhone, mPassword, new LogInCallback<AVUser>() {
                            @Override
                            public void done(AVUser avUser, AVException e) {
                                if (e==null){
                                    //保存学校信息
                                    new StatusNetAsyncTask(LoginActivity.this) {
                                        Schools schools;
                                        @Override
                                        protected void doInBack() throws Exception {
                                            schools = UserService.getSchool();
                                        }

                                        @Override
                                        protected void onPost(Exception e) {
                                            //储存用户学校信息
                                            SharedPrefsUtil.putValue(LoginActivity.this, Constant.FILE_NAME,"School",schools.getSchoolName());
                                        }
                                    }.execute();
                                    //登陆成功
                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                }else {
                                    Log.v("code错误吗",e+"");
                                    MainUtil.ToastUtil(MyApplication.getContext(),"手机号或密码不正确!");
                                }
                            }
                        });
                    }
                });
                }

                dialog.dismiss();

                break;
            case R.id.login_seek:
                break;
            case R.id.login_register:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
        }
    }
}
