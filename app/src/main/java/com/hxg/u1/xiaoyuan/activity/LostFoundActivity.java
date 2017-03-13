package com.hxg.u1.xiaoyuan.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.adapter.FragmentsAdapter;
import com.hxg.u1.xiaoyuan.fragment.FoundFragment;
import com.hxg.u1.xiaoyuan.fragment.LostFragment;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_found);
        ButterKnife.bind(this);
        initTitle();
        //创建fragment
        initFragment();
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
                startActivity(new Intent(LostFoundActivity.this,AddLostFoundActivity.class));
            }
        });
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
