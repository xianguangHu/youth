package com.hxg.u1.xiaoyuan.contract;

import com.hxg.u1.xiaoyuan.bean.LostFound;

import java.util.List;

/**
 * Created by huxianguang on 2017/3/14.
 */

public interface LostFoundContract {
    interface View extends BaseView{
        void update2loadData(List<LostFound> datas);
    }

    interface Presenter{
        void loadData(int type);
    }
}
