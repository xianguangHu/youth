package com.hxg.u1.xiaoyuan.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by huxianguang on 2017/3/13.
 */

public class FragmentsAdapter extends FragmentPagerAdapter {
    private static String title[]=new String[]{"失物","招领"};
    private List<Fragment> mArrayList;
    public FragmentsAdapter(FragmentManager fm, List<Fragment> fragmentArrayList) {
        super(fm);
        this.mArrayList=fragmentArrayList;
    }

    @Override
    public Fragment getItem(int i) {
        return mArrayList.get(i);
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
