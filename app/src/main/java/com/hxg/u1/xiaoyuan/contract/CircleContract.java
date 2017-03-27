package com.hxg.u1.xiaoyuan.contract;

import com.avos.avoscloud.AVException;
import com.hxg.u1.xiaoyuan.adapter.CircleAdapter;
import com.hxg.u1.xiaoyuan.bean.Circle;
import com.hxg.u1.xiaoyuan.bean.Comment;

import java.util.List;


/**
 * Created by huxianguang on 2017/2/28.
 */

public interface CircleContract {
    interface View extends BaseView{
        void update2loadData(int loadType, List<Circle> datas);
        void updateEditTextBodyVisible(int visibility,String circleId,int circlePostition,String installationId);
        void update2AddComment(int circlePosition, Comment comment);
        void updata2AddLike(int circlePosition);
    }
    interface Presenter {
        void loadData(int loadType, CircleAdapter adapter) throws AVException;
    }
}
