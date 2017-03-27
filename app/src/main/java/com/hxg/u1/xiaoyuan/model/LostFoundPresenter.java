package com.hxg.u1.xiaoyuan.model;

import android.content.Context;

import com.avos.avoscloud.AVUser;
import com.hxg.u1.xiaoyuan.bean.LostFound;
import com.hxg.u1.xiaoyuan.bean.Schools;
import com.hxg.u1.xiaoyuan.contract.LostFoundContract;
import com.hxg.u1.xiaoyuan.utils.StatusNetAsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huxianguang on 2017/3/14.
 * 失物招领  请求服务器和通知view更新
 */

public class LostFoundPresenter implements LostFoundContract.Presenter {
    private Context mContext;
    private List<LostFound> datas=new ArrayList<>();
    private LostFoundContract.View mView;
    public LostFoundPresenter(Context context,LostFoundContract.View view) {
        this.mContext=context;
        this.mView=view;
    }

    @Override
    public void loadData(final int type) {
        //获取数据
        new StatusNetAsyncTask(mContext) {
            @Override
            protected void doInBack() throws Exception {
                Schools schools= (Schools) AVUser.getCurrentUser().get("schoolId");
                datas=LostFoundService.getLostFoundDatas(type,schools);
            }

            @Override
            protected void onPost(Exception e) {
                if (mView!=null){
                    mView.update2loadData(datas);
                }
            }
        }.execute();

    }
}
