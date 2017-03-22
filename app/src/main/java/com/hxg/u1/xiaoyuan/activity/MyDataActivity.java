package com.hxg.u1.xiaoyuan.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.bean.AvUser;
import com.hxg.u1.xiaoyuan.model.UserService;
import com.hxg.u1.xiaoyuan.utils.Constant;
import com.hxg.u1.xiaoyuan.utils.SharedPrefsUtil;
import com.hxg.u1.xiaoyuan.widgets.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyDataActivity extends AppCompatActivity {

    @BindView(R.id.MyData_title_bar)
    TitleBar mMyDataTitleBar;
    @BindView(R.id.mydata_name)
    EditText mMydataName;
    @BindView(R.id.mydata_gender)
    LinearLayout mMydataGender;
    @BindView(R.id.mydata_age)
    EditText mMydataAge;
    @BindView(R.id.mydata_school)
    EditText mMydataSchool;
    @BindView(R.id.mydata_gender_input)
    TextView mMydataGenderInput;

    private PopupWindow mPop;
    private LinearLayout mLl_popup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_data);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        initTitle();
        initPop();
        mMydataName.setText(AvUser.getCurrentUser().getUsername());
        mMydataGenderInput.setText(AvUser.getCurrentUser().getGender());
        mMydataAge.setText(AvUser.getCurrentUser().getAge());
        String school= SharedPrefsUtil.getValue(this, Constant.FILE_NAME,"School","");
        mMydataSchool.setText(school);
    }

    private void initPop() {
        mPop = new PopupWindow(MyDataActivity.this);
        View view = getLayoutInflater().inflate(R.layout.mydata_popup_item, null);
        mLl_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        mPop.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        mPop.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        mPop.setBackgroundDrawable(new BitmapDrawable());
        mPop.setFocusable(true);
        mPop.setOutsideTouchable(true);
        mPop.setContentView(view);
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        parent.setOnClickListener(new MyOnClickListener());
        Button man = (Button) view.findViewById(R.id.item_popupwindows_man);
        man.setOnClickListener(new MyOnClickListener());
        Button woman = (Button) view.findViewById(R.id.item_popupwindows_woman);
        woman.setOnClickListener(new MyOnClickListener());
        Button cancelBtn = (Button) view.findViewById(R.id.item_popupwindows_cancel);
        cancelBtn.setOnClickListener(new MyOnClickListener());
    }

    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_popupwindows_man:
                    mMydataGenderInput.setText("男");
                    break;
                case R.id.item_popupwindows_woman:
                    mMydataGenderInput.setText("女");
                    break;
            }
            mPop.dismiss();
            mLl_popup.clearAnimation();
            backgroundAlpha(1f);
        }
    }

    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);  getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    private void initTitle() {
        mMyDataTitleBar.setLeftText("取消");
        mMyDataTitleBar.setLeftTextColor(getResources().getColor(R.color.white));
        mMyDataTitleBar.setBackgroundColor(getResources().getColor(R.color.Wathet));
        mMyDataTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mMyDataTitleBar.setActionTextColor(getResources().getColor(R.color.white));
        mMyDataTitleBar.addAction(new TitleBar.TextAction("完成") {

            @Override
            public void performAction(View view) {
                String name = mMydataName.getText().toString();
                String age = mMydataAge.getText().toString().trim();
                String gender=mMydataGenderInput.getText().toString().trim();
                //保存到数据库
                UserService.updateUserDate(name, age, gender, new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e==null){
                            finish();
                        }
                    }
                });
            }
        });
    }

    @OnClick(R.id.mydata_gender)
    public void onClick() {
        mLl_popup.startAnimation(AnimationUtils.loadAnimation(MyDataActivity.this, R.anim.activity_translate_in));
        mPop.showAsDropDown(mMyDataTitleBar);
        backgroundAlpha(0.6f);
    }
}
