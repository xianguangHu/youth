package com.hxg.u1.xiaoyuan.contract;

import com.hxg.u1.xiaoyuan.bean.CarSchool;

import java.util.List;

/**
 * Created by huxianguang on 2017/3/26.
 */

public interface CarSchoolContract {
    interface View extends BaseView{
        void update2loadData(List<CarSchool> datas);
    }
    interface Presenter {
        void loadData();
    }
}
