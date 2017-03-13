package com.hxg.u1.xiaoyuan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.widgets.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddLostFoundActivity extends AppCompatActivity {

    @BindView(R.id.addLostFound_title)
    TitleBar mAddLostFoundTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lost_found);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        initTitle();
    }

    private void initTitle() {
        mAddLostFoundTitle.setLeftImageResource(R.mipmap.left_white);
        mAddLostFoundTitle.setLeftText("返回");
        mAddLostFoundTitle.setLeftTextColor(getResources().getColor(R.color.Wathet));
        mAddLostFoundTitle.setBackgroundColor(getResources().getColor(R.color.white));
        mAddLostFoundTitle.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAddLostFoundTitle.setActionTextColor(getResources().getColor(R.color.Wathet));
        mAddLostFoundTitle.addAction(new TitleBar.TextAction("提交") {
            @Override
            public void performAction(View view) {

            }
        });
    }
}
