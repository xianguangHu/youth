package com.hxg.u1.xiaoyuan.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.bean.VersionInfo;
import com.hxg.u1.xiaoyuan.fragment.FriendFragment;
import com.hxg.u1.xiaoyuan.fragment.MainFragment;
import com.hxg.u1.xiaoyuan.fragment.MyFragment;
import com.hxg.u1.xiaoyuan.fragment.ScheduleFragment;
import com.hxg.u1.xiaoyuan.model.MyPushService;
import com.hxg.u1.xiaoyuan.model.VersionService;
import com.hxg.u1.xiaoyuan.utils.VersionUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tabHost)
    FragmentTabHost mTabHost;
    private Class[] fragment=new Class[]{
            MainFragment.class,
            ScheduleFragment.class,
            FriendFragment.class,
            MyFragment.class
    };
    private int[] tabImages=new int[]{
            R.drawable.tab_ic_mian,
            R.drawable.tab_ic_schdule,
            R.drawable.tab_ic_friend,
            R.drawable.tab_ic_my,
    };
    private String[] tabTitle=new String[]{"首页","课程","校园圈","我"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragmentTabhost();
        initData();
    }

    private void initData() {
        //检查版本更新
        VersionService.isUpdate(new VersionUtil.UpdateListener() {
            @Override
            public void onUpdateReturned(int updateStatus, VersionInfo versionInfo) {
                switch (updateStatus){
                    case VersionUtil.YES:
                        //有更新
                        VersionUtil.showDialog(MainActivity.this,versionInfo);
                        break;
                    case VersionUtil.NO:
                        break;
                }

            }
        });
        MyPushService.initPush(this);
    }

    /**
     * 初始化tabhost
     */
    private void initFragmentTabhost() {
        //初始化
        mTabHost.setup(MainActivity.this,getSupportFragmentManager(),R.id.realtabcontent);
        for (int i=0;i<fragment.length;i++){
            View inflate=getLayoutInflater().inflate(R.layout.tab_item,null);
            ImageView ivTab= (ImageView) inflate.findViewById(R.id.tab_iv);
            TextView tvTab= (TextView) inflate.findViewById(R.id.tab_tv);
            ivTab.setImageResource(tabImages[i]);
            tvTab.setText(tabTitle[i]);
            mTabHost.addTab(mTabHost.newTabSpec(""+i).setIndicator(inflate),fragment[i],null);
        }
    }

}
