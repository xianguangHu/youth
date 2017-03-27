package com.hxg.u1.xiaoyuan.model;

import android.content.Context;

import com.hxg.u1.xiaoyuan.bean.CarSchool;
import com.hxg.u1.xiaoyuan.contract.CarSchoolContract;

import java.util.List;

/**
 * Created by huxianguang on 2017/3/26.
 */

public class CarSchoolPresenter implements CarSchoolContract.Presenter{
    private Context mContext;
    private CarSchoolContract.View mView;
    public CarSchoolPresenter(Context context,CarSchoolContract.View view){
        this.mContext=context;
        this.mView=view;
    }
    /**
     * 加载数据
     */
    @Override
    public void loadData(){
        CarSchoolService.getData(new CarSchoolService.CarSchoolCallBack() {
            @Override
            public void callback(List<CarSchool> list) {
                mView.update2loadData(list);
            }
        });
    }
}
