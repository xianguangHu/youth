package com.hxg.u1.xiaoyuan.adapter;

import android.support.v7.widget.RecyclerView;

import com.hxg.u1.xiaoyuan.listner.RecycleViewItemListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huxianguang on 2017/2/28.
 */

public abstract class BaseRecycleViewAdapter<T,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {
    protected RecycleViewItemListener itemListener;
    protected List<T> datas=new ArrayList<>();

    public List<T> getDatas() {
        if (datas==null)
            datas = new ArrayList<T>();
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public void setItemListener(RecycleViewItemListener listener){
        this.itemListener = listener;
    }

}
