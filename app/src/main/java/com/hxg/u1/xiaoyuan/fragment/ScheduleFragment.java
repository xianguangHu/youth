package com.hxg.u1.xiaoyuan.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.utils.Constant;
import com.hxg.u1.xiaoyuan.utils.SharedPrefsUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment {


    @BindView(R.id.leftNo)
    LinearLayout mLeftNo;
    @BindView(R.id.monday)
    LinearLayout mMonday;
    @BindView(R.id.tuesday)
    LinearLayout mTuesday;
    @BindView(R.id.wednesday)
    LinearLayout mWednesday;
    @BindView(R.id.thursday)
    LinearLayout mThursday;
    @BindView(R.id.firday)
    LinearLayout mFirday;
    @BindView(R.id.saturday)
    LinearLayout mSaturday;
    @BindView(R.id.weekend)
    LinearLayout mWeekend;
    Unbinder unbinder;

    // 左边节课的高度
    private float mLeftHeight;
    // 左边节课的宽度
    private float mLeftWidth;

    // 每天有多少节课
    private int mMaxCouese;

    public ScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        unbinder = ButterKnife.bind(this, view);
        initDate();
        //绘制左边的课程节数
        drawLeftNo();

        // 绘制所有课程 其实可以使用redrawAll替代三步
        drawAllCourse();
        return view;
    }




    private void drawLeftNo() {
        mLeftHeight = getResources().getDimension(R.dimen.left_height);
        mLeftWidth = getResources().getDimension(R.dimen.left_width);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(
                (int) mLeftWidth,(int) mLeftHeight);
        for (int i=1;i<=mMaxCouese;i++){
            TextView tv=new TextView(getActivity());
            tv.setText(i+"");
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(getResources().getColor(R.color.Navy_Blue));
            tv.setBackgroundResource(R.color.white);
            mLeftNo.addView(tv,lp);
        }

    }

    private void initDate() {
        // 读取配置信息
        readIniFile();
    }
    private void drawAllCourse() {

    }

    private void drawCourse(LinearLayout ll,String day){

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void readIniFile() {
        mMaxCouese=SharedPrefsUtil.getValue(getActivity(), Constant.FILE_NAME,"MaxCouese",-1);
        if (-1==mMaxCouese){
            SharedPrefsUtil.putValue(getActivity(), Constant.FILE_NAME,"MaxCouese",12);
            mMaxCouese=SharedPrefsUtil.getValue(getActivity(), Constant.FILE_NAME,"MaxCouese",-1);
        }
    }

}
