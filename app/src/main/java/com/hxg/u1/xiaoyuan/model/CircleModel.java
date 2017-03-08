package com.hxg.u1.xiaoyuan.model;

import android.content.Context;

import com.hxg.u1.xiaoyuan.listner.IDataRequestListener;
import com.hxg.u1.xiaoyuan.utils.StatusNetAsyncTask;

/**
 * Created by huxianguang on 2017/3/8.
 */

public class CircleModel {
    public CircleModel(){}

    public void addComment(IDataRequestListener listener,Context context){
            requestServer(listener,context);
    }
    private void requestServer(final IDataRequestListener listener, Context context) {
        new StatusNetAsyncTask(context) {
            @Override
            protected void doInBack() throws Exception {
                listener.doInBack();
            }

            @Override
            protected void onPost(Exception e) {
                listener.loadSuccess(e);
            }
        }.execute();
    }
}
