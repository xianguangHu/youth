package com.hxg.u1.xiaoyuan.adapter.viewholder;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.widgets.ExpandTextView;

/**
 * Created by huxianguang on 2017/3/15.
 */

public abstract class LostFoundViewHolder extends ViewHolder{
    public ImageView mHeadIv;
    public TextView mNameTv;
    public ExpandTextView mContentTv;
    public TextView mTimeTv;
    public TextView mDeleteBtn;
    public TextView mGoods;
    public TextView mLocal;
    public TextView mTime;

    public LostFoundViewHolder(View itemView) {
        super(itemView);
        ViewStub viewStub= (ViewStub) itemView.findViewById(R.id.viewStub);
        initSubView(viewStub);
        mHeadIv = (ImageView) itemView.findViewById(R.id.headIv);
        mNameTv = (TextView) itemView.findViewById(R.id.nameTv);
        mContentTv = (ExpandTextView) itemView.findViewById(R.id.contentTv);
        mTimeTv = (TextView) itemView.findViewById(R.id.timeTv);
        mDeleteBtn = (TextView) itemView.findViewById(R.id.deleteBtn);
        mGoods = (TextView) itemView.findViewById(R.id.lostfound_item_goods);
        mLocal = (TextView) itemView.findViewById(R.id.lostfound_item_local);
        mTime = (TextView) itemView.findViewById(R.id.lostfound_item_time);
    }
    public abstract void initSubView(ViewStub viewStub);
}
