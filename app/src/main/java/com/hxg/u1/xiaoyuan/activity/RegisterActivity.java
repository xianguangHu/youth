package com.hxg.u1.xiaoyuan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.bean.Schools;
import com.hxg.u1.xiaoyuan.model.Model;
import com.hxg.u1.xiaoyuan.model.StatusService;
import com.hxg.u1.xiaoyuan.utils.StatusNetAsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.newsmssdk.BmobSMS;
import cn.bmob.newsmssdk.exception.BmobException;
import cn.bmob.newsmssdk.listener.RequestSMSCodeListener;

public class RegisterActivity extends Activity implements AdapterView.OnItemSelectedListener{

    @BindView(R.id.register_return)
    ImageView mRegisterReturn;
    @BindView(R.id.register_phone)
    EditText mRegisterPhone;
    @BindView(R.id.register_next)
    Button mRegisterNext;
    @BindView(R.id.register_password)
    EditText mRegisterPassword;
    @BindView(R.id.register_spinner)
    Spinner mRegisterSpinner;
    private String mPhone;
    private String mPassword;
    private List<String> dataSchool=new ArrayList<>();
    private ArrayAdapter<String> arr_adapter;
    private Map<String, String> mMap=new HashMap<String, String>();
    private String mSchoolId;
    private List<Schools> mSchoolsList=new ArrayList<>();
    private Schools mMySchool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initData();
        initView();
        BmobSMS.initialize(getApplicationContext(), "d7d8bd7cb82788999dc8bebcc0a7f073");
    }

    private void initView() {
        //适配器
        arr_adapter= new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, dataSchool);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        mRegisterSpinner.setAdapter(arr_adapter);
        mRegisterSpinner.setOnItemSelectedListener(RegisterActivity.this);

        mRegisterSpinner.setDropDownVerticalOffset(30);
    }

    //初始化数据 获取学校信息
    private void initData() {
        new StatusNetAsyncTask(RegisterActivity.this) {

            @Override
            protected void doInBack() throws Exception {
                mSchoolsList = StatusService.getschool();
            }

            @Override
            protected void onPost(Exception e) {
                for (Schools schools:mSchoolsList){
                    String name=schools.getSchoolName();
                    dataSchool.add(name);
                }

                initView();
            }
        }.execute();
    }

    @OnClick({R.id.register_return, R.id.register_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_return:
                finish();
                break;
            case R.id.register_next:
                mPhone = mRegisterPhone.getText().toString().trim();
                mPassword = mRegisterPassword.getText().toString().trim();
                Model.getInstance().getGlobalThreadpool().execute(new Runnable() {
                    @Override
                    public void run() {
                        BmobSMS.requestSMSCode(getApplicationContext(), mPhone, "胡贤广", new RequestSMSCodeListener() {

                            @Override
                            public void done(Integer smsId, BmobException ex) {
                                // TODO Auto-generated method stub
                                if (ex == null) {//验证码发送成功
                                    Log.i("bmob", "短信id：" + smsId);//用于查询本次短信发送详情
                                }
                            }
                        });
                        Intent intent = new Intent(RegisterActivity.this, CheckActivity.class);
                        intent.putExtra("phone", mPhone);
                        intent.putExtra("password", mPassword);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("schools",mMySchool);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String schoolName = (String) arr_adapter.getItem(position);
        for (Schools schools:mSchoolsList){
            if (schools.getSchoolName().equals(schoolName)){
                this.mMySchool=schools;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
