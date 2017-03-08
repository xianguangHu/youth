package com.hxg.u1.xiaoyuan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.adapter.MyPagerAdapter;
import com.hxg.u1.xiaoyuan.utils.ColorAnimationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuideActivity extends Activity {
    //获取图片的数据
    int[] images = new int[]{
            R.mipmap.welcome1,
            R.mipmap.welcome3,
            R.mipmap.welcome4,
    };
    private List<View> mViewList=new ArrayList<>();
    @BindView(R.id.guide_colorView)
    ColorAnimationView mGuideColorView;
    @BindView(R.id.guide_viewpager)
    ViewPager mGuideViewpager;
    @BindView(R.id.guide_start)
    Button mGuideStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initData();
        mGuideViewpager.setAdapter(new MyPagerAdapter(mViewList));
        mGuideViewpager.setOnPageChangeListener(new MyListnter());
        mGuideColorView.setmViewPager(mGuideViewpager,images.length);
        mGuideStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this,WelcomeActivity.class));
                finish();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        for (int i=0;i<images.length;i++){
            View inflate=getLayoutInflater().inflate(R.layout.guide_item,null);
            ImageView ivGuide= (ImageView) inflate.findViewById(R.id.guide_item_iv);
            ivGuide.setBackgroundResource(images[i]);
            mViewList.add(inflate);
        }
    }
    class MyListnter implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
        //Viewpager滚动每一张照片所触发的事件
        @Override
        public void onPageSelected(int position) {
            if(position==images.length-1){
                mGuideStart.setVisibility(View.VISIBLE);
                Animation animation= AnimationUtils.loadAnimation(GuideActivity.this,R.anim.anim_guide_btn_start);
                mGuideStart.setAnimation(animation);
            }else {
                mGuideStart.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
