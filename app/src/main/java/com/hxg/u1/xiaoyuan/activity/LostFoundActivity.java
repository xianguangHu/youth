package com.hxg.u1.xiaoyuan.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.adapter.FragmentsAdapter;
import com.hxg.u1.xiaoyuan.fragment.FoundFragment;
import com.hxg.u1.xiaoyuan.fragment.LostFragment;
import com.hxg.u1.xiaoyuan.utils.Constant;
import com.hxg.u1.xiaoyuan.widgets.TitleBar;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LostFoundActivity extends AppCompatActivity {

    @BindView(R.id.LostFound_title_bar)
    TitleBar mLostFoundTitleBar;
    @BindView(R.id.LostFound_tablayout)
    TabLayout mLostFoundTablayout;
    @BindView(R.id.LostFound_viewpager)
    ViewPager mLostFoundViewpager;
    private static ArrayList<Fragment> fragmentList = new ArrayList<>();
    private FragmentsAdapter mFragmentsAdapter;
    private PopupWindow mPop;
    private LinearLayout mLl_popup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_found);
        ButterKnife.bind(this);
        initTitle();
        //创建fragment
        initFragment();
        //初始化popup
        initPop();
        //给viewpager设置适配器
        setViewPagerAdapter();
        mLostFoundTablayout.setupWithViewPager(mLostFoundViewpager);
        mLostFoundTablayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(mLostFoundTablayout,70,70);
            }
        });
    }

    private void initPop() {
        mPop = new PopupWindow(LostFoundActivity.this);
        View view = getLayoutInflater().inflate(R.layout.lostfound_popup_item, null);
        mLl_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        mPop.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        mPop.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        mPop.setBackgroundDrawable(new BitmapDrawable());
        mPop.setFocusable(true);
        mPop.setOutsideTouchable(true);
        mPop.setContentView(view);
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        parent.setOnClickListener(new MyOnClickListener());
        Button cameraBtn = (Button) view.findViewById(R.id.item_popupwindows_lost);
        cameraBtn.setOnClickListener(new MyOnClickListener());
        Button photoBtn = (Button) view.findViewById(R.id.item_popupwindows_found);
        photoBtn.setOnClickListener(new MyOnClickListener());
        Button cancelBtn = (Button) view.findViewById(R.id.item_popupwindows_cancel);
        cancelBtn.setOnClickListener(new MyOnClickListener());
    }

    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(LostFoundActivity.this,AddLostFoundActivity.class);
            switch (v.getId()) {
                //我遗失了
                case R.id.item_popupwindows_lost:
                    intent.putExtra(Constant.LOSTANDFOUND,Constant.LOST);
                    startActivity(intent);
                    break;
                //我拾到了
                case R.id.item_popupwindows_found:
                    intent.putExtra(Constant.LOSTANDFOUND,Constant.FOUND);
                    startActivity(intent);
                    break;
            }
            mPop.dismiss();
            mLl_popup.clearAnimation();
            backgroundAlpha(1f);
        }
    }

    private void initFragment() {
        fragmentList.add(new LostFragment());
        fragmentList.add(new FoundFragment());
    }

    private void setViewPagerAdapter() {
        mFragmentsAdapter = new FragmentsAdapter(getSupportFragmentManager(),fragmentList);
        mLostFoundViewpager.setAdapter(mFragmentsAdapter);
    }

    private void initTitle() {
        mLostFoundTitleBar.setLeftImageResource(R.mipmap.left_white);
        mLostFoundTitleBar.setLeftText("返回");
        mLostFoundTitleBar.setLeftTextColor(getResources().getColor(R.color.white));
        mLostFoundTitleBar.setBackgroundColor(getResources().getColor(R.color.Wathet));
        mLostFoundTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mLostFoundTitleBar.setActionTextColor(Color.WHITE);
        mLostFoundTitleBar.addAction(new TitleBar.TextAction("发布") {
            @Override
            public void performAction(View view) {
                mLl_popup.startAnimation(AnimationUtils.loadAnimation(LostFoundActivity.this, R.anim.activity_translate_in));
                mPop.showAsDropDown(mLostFoundTitleBar);
                backgroundAlpha(0.6f);
            }
        });
    }

    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);  getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
    //大神代码 抄的 设置下划线长度 表示不想看
    public void setIndicator (TabLayout tabs,int leftDip,int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
        }
    }
