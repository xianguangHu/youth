package com.hxg.u1.xiaoyuan.model;

import android.content.Context;

import com.hxg.u1.xiaoyuan.bean.Comment;

import java.util.List;

/**
 * Created by huxianguang on 2017/3/28.
 */

public class CommentRelatedPresenter implements CommentRelatedContract.Presenter{
    private Context mContext;
    private CommentRelatedContract.View mView;

    public CommentRelatedPresenter(Context context, CommentRelatedContract.View view) {
        mContext = context;
        mView = view;
    }

    @Override
    public void loadData(final int type, int size) {
        CommentRelatedService.getMyRelated(10,size, new CommentRelatedService.CommentRelatedCallBack() {
            @Override
            public void callback(List<Comment> list) {
                if (mView!=null){
                    mView.update2loadData(list,type);
                }
            }
        });
    }
}
