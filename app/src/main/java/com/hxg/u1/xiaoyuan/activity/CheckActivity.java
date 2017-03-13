package com.hxg.u1.xiaoyuan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.bean.Schools;
import com.hxg.u1.xiaoyuan.model.Model;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.newsmssdk.BmobSMS;
import cn.bmob.newsmssdk.exception.BmobException;
import cn.bmob.newsmssdk.listener.VerifySMSCodeListener;

public class CheckActivity extends AppCompatActivity {

    @BindView(R.id.check_return)
    ImageView mCheckReturn;
    @BindView(R.id.check_yan)
    EditText mCheckYan;
    @BindView(R.id.check_register)
    Button mCheckRegister;
    @BindView(R.id.check_setphone)
    TextView mCheckSetphone;
    private String mYan;
    private String mPhone;
    private String mPassword;
    private Schools mMySchools;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mPhone = intent.getStringExtra("phone");
        mPassword = intent.getStringExtra("password");
        mMySchools= (Schools) intent.getSerializableExtra("schools");
        mCheckSetphone.setText(mPhone);
    }


    @OnClick({R.id.check_return, R.id.check_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.check_return:
                finish();
                break;
            case R.id.check_register:
                mYan = mCheckYan.getText().toString().trim();
                Model.getInstance().getGlobalThreadpool().execute(new Runnable() {
                    @Override
                    public void run() {
                        BmobSMS.verifySmsCode(getApplicationContext(), mPhone, mYan, new VerifySMSCodeListener() {

                            @Override
                            public void done(BmobException ex) {
                                // TODO Auto-generated method stub
                                if (ex == null) {//短信验证码已验证成功
                                    AVUser user=new AVUser();
                                    user.setUsername(mPhone);
                                    user.setPassword(mPassword);
                                    user.put("schoolId",mMySchools);
                                    user.signUpInBackground(new SignUpCallback() {
                                        @Override
                                        public void done(AVException e) {
                                            if (e==null){
                                                //注册成功
                                                startActivity(new Intent(CheckActivity.this, MainActivity.class));
                                            }else {
                                                Toast.makeText(CheckActivity.this,"注册失败",Toast.LENGTH_LONG);
                                            }
                                        }
                                    });
                                } else {
                                    Log.i("bmob", "验证失败：code =" + ex.getErrorCode() + ",msg = " + ex.getLocalizedMessage());
                                }
                            }
                        });
                    }
                });
                break;
        }
    }
}
