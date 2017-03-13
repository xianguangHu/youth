package com.hxg.u1.xiaoyuan.contract;

/**
 * Created by huxianguang on 2017/3/14.
 */

public interface LostFoundContract {
    interface View extends BaseView{

    }

    interface Presenter{
        void loadData();
    }
}
