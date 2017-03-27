package com.hxg.u1.xiaoyuan.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.bean.CarSchool;
import com.hxg.u1.xiaoyuan.widgets.ObservableScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarSchoolDetailActivity extends AppCompatActivity implements ObservableScrollView.ScrollViewListener{

    @BindView(R.id.iv_detail)
    SimpleDraweeView mIvDetail;
    @BindView(R.id.carSchool_detail_title)
    TextView mCarSchoolDetailTitle;
    @BindView(R.id.carSchool_detail_decs)
    TextView mCarSchoolDetailDecs;
    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.carSchool_detail_sold)
    TextView mCarSchoolDetailSold;
    @BindView(R.id.carSchool_detail_title2)
    TextView mCarSchoolDetailTitle2;
    @BindView(R.id.carSchool_detail_address)
    TextView mCarSchoolDetailAddress;
    @BindView(R.id.carSchool_detail_call)
    ImageView mCarSchoolDetailCall;
    @BindView(R.id.scrollView)
    ObservableScrollView mScrollView;
    @BindView(R.id.tv_titlebar)
    TextView mTvTitlebar;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.layout_title)
    RelativeLayout mLayoutTitle;
    @BindView(R.id.carSchool_detail_price)
    TextView mCarSchoolDetailPrice;
    @BindView(R.id.carSchool_detail_buy)
    Button mCarSchoolDetailBuy;
    @BindView(R.id.layout_buy)
    RelativeLayout mLayoutBuy;
    @BindView(R.id.activity_detail)
    RelativeLayout mActivityDetail;
    private CarSchool mCarSchool;

    private int mImageHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_school_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mCarSchool = (CarSchool) intent.getSerializableExtra("carSchool");
        initData();
        initListener();
    }

    private void initListener() {
        ViewTreeObserver vto = mIvDetail.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mIvDetail.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                mImageHeight = mIvDetail.getHeight();

                mScrollView.setScrollViewListener(CarSchoolDetailActivity.this);
            }
        });
    }

    private void initData() {
        mCarSchoolDetailTitle.setText(mCarSchool.getCarSchoolName());
        //已售量
        mCarSchoolDetailSold.setText(mCarSchool.getSold()+"");
        //卖家信息标题
        mCarSchoolDetailTitle2.setText(mCarSchool.getCarSchoolName());
        //地址
        mCarSchoolDetailAddress.setText(mCarSchool.getAddress());
        //价格
        mCarSchoolDetailPrice.setText(mCarSchool.getPrice());

    }
    @OnClick({R.id.iv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            mTvTitlebar.setVisibility(View.GONE);
            mLayoutTitle.setBackgroundColor(Color.argb(0, 0, 0, 0));
        } else if (y > 0 && y <= mImageHeight) {
            float scale = (float) y / mImageHeight;
            float alpha = (255 * scale);
            mTvTitlebar.setVisibility(View.VISIBLE);
            mTvTitlebar.setText("测试测试测试测试测试测试测试");
            mTvTitlebar.setTextColor(Color.argb((int) alpha, 0, 0, 0));
            mLayoutTitle.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
        } else {
            mTvTitlebar.setVisibility(View.VISIBLE);
            mTvTitlebar.setText("测试测试测试测试测试测试测试");
            mLayoutTitle.setBackgroundColor(Color.argb(255, 255, 255, 255));
            mTvTitlebar.setTextColor(Color.argb(0, 0, 0, 0));
        }
    }
}
