package com.hxg.u1.xiaoyuan.model;

import android.content.Context;
import android.view.View;

import com.hxg.u1.xiaoyuan.adapter.CircleAdapter;
import com.hxg.u1.xiaoyuan.bean.Circle;
import com.hxg.u1.xiaoyuan.bean.Comment;
import com.hxg.u1.xiaoyuan.contract.CircleContract;
import com.hxg.u1.xiaoyuan.utils.Constant;
import com.hxg.u1.xiaoyuan.utils.StatusNetAsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huxianguang on 2017/2/28.
 * 通知model请求服务器和通知view更新
 */

public class CirclePresenter implements CircleContract.Presenter {
    private CircleContract.View view;
    Context mContext;
    private List<Circle> datas = new ArrayList<>();

    public CirclePresenter(CircleContract.View view, Context context) {
        this.view = view;
        this.mContext = context;
    }

    @Override
    public void loadData(final int loadType, final CircleAdapter adapter) {
        new StatusNetAsyncTask(mContext) {
            @Override
            protected void doInBack() throws Exception {
                if (loadType== Constant.TYPE_PULLREFRESH){
                datas = StatusService.getStatusDatas(10,0);
                }else if (loadType==Constant.TYPE_UPLOADREFRESH){//底部查询
                    datas = StatusService.getStatusDatas(10,adapter.getDatas().size()+1);
                }
            }

            @Override
            protected void onPost(Exception e) {
                if (view != null) {
                    view.update2loadData(loadType, datas);
                }
            }
        }.execute();
    }

    public void showEditTextBody(String circleId,int circlePostition,String installationId) {
        if (view != null) {
            view.updateEditTextBodyVisible(View.VISIBLE, circleId,circlePostition,installationId);
        }
    }

    /**
     *
     * @param content
     * @param circle circleId
     */
    public void addComment(final String content, final String circle, final int circlePostition, final String installationId) {
        new StatusNetAsyncTask(mContext) {
            Comment comment;
            @Override
            protected void doInBack() throws Exception {
                comment=StatusService.addComment(content, circle,installationId);
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
