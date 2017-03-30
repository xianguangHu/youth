package com.hxg.u1.xiaoyuan.model;

import com.hxg.u1.xiaoyuan.bean.Comment;

import java.util.List;

/**
 * Created by huxianguang on 2017/3/28.
 */

public interface CommentRelatedContract {
    interface View {
        void update2loadData(List<Comment> datas,int type);
    }
    interface Presenter{
        void loadData(int type,int size);
    }
}
