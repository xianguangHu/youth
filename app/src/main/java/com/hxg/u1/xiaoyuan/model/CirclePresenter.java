package com.hxg.u1.xiaoyuan.model;

import android.content.Context;
import android.view.View;

import com.hxg.u1.xiaoyuan.bean.Circle;
import com.hxg.u1.xiaoyuan.bean.Comment;
import com.hxg.u1.xiaoyuan.contract.CircleContract;
import com.hxg.u1.xiaoyuan.utils.StatusNetAsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huxianguang on 2017/2/28.
 * 通知model请求服务器和通知view更新
 */

public class CirclePresenter implements CircleContract.Presenter {
    private CircleModel circleModel;
    private CircleContract.View view;
    Context mContext;
    private List<Circle> datas = new ArrayList<>();

    public CirclePresenter(CircleContract.View view, Context context) {
        this.view = view;
        this.mContext = context;
    }

    @Override
    public void loadData(final int loadType) {
        new StatusNetAsyncTask(mContext) {
            @Override
            protected void doInBack() throws Exception {
                datas = StatusService.getStatusDatas(15);
            }

            @Override
            protected void onPost(Exception e) {
                if (view != null) {
                    view.update2loadData(loadType, datas);
                }
            }
        }.execute();
    }

    public void showEditTextBody(String circleId,int circlePostition) {
        if (view != null) {
            view.updateEditTextBodyVisible(View.VISIBLE, circleId,circlePostition);
        }
    }

    /**
     *
     * @param content
     * @param circle circleId
     */
    public void addComment(final String content, final String circle, final int circlePostition) {
        new StatusNetAsyncTask(mContext) {
            Comment comment;
            @Override
            protected void doInBack() throws Exception {
                comment=StatusService.addComment(content, circle);
            }

            @Override
            protected void onPost(Exception e) {
                if (view!=null){
                    view.update2AddComment(circlePostition,comment);
                }
            }
        }.execute();
    }

    /**
     * 处理点赞  向后台提交数据 并跟新ui
     * @param circlePostition
     */
    public void addLike(int circlePostition,String circleId){

        //更新点赞数量
        if (view!=null){
            view.updata2AddLike(circlePostition);
        }
    }

}
