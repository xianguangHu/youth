package com.hxg.u1.xiaoyuan.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.widgets.CommentListView;
import com.hxg.u1.xiaoyuan.widgets.ExpandTextView;
import com.hxg.u1.xiaoyuan.widgets.SnsPopupWindow;

/**
 * Created by huxianguang on 2017/2/28.
 */

public abstract class CircleViewHolder extends RecyclerView.ViewHolder {
    public final static int TYPE_URL = 1;
    public final static int TYPE_IMAGE = 2;
    public final static int TYPE_VIDEO = 3;
    public int viewType;
    public SimpleDraweeView mHeadIv;
    public TextView mNameTv;
    public ExpandTextView mContentTv;
    public TextView mUrlTipTv;
    public TextView mTimeTv;
    public TextView mDeleteBtn;
    public SnsPopupWindow snsPopupWindow;
    public ImageView mLikeIv;
    public TextView mLikeTv;
    public ImageView mCommentIv;
    public TextView mCommentTv;
    public CommentListView mCommentList;

    public CircleViewHolder(View itemView, int viewType) {
        super(itemView);
        this.viewType=viewType;
        ViewStub viewStub= (ViewStub) itemView.findViewById(R.id.viewStub);
        initSubView(viewType,viewStub);
        mHeadIv = (SimpleDraweeView) itemView.findViewById(R.id.headIv);
        mNameTv = (TextView) itemView.findViewById(R.id.nameTv);

        mContentTv = (ExpandTextView) itemView.findViewById(R.id.contentTv);
        mUrlTipTv = (TextView) itemView.findViewById(R.id.urlTipTv);
        mTimeTv = (TextView) itemView.findViewById(R.id.timeTv);
        mDeleteBtn = (TextView) itemView.findViewById(R.id.deleteBtn);
        snsPopupWindow = new SnsPopupWindow(itemView.getContext());
        mLikeIv = (ImageView) itemView.findViewById(R.id.circle_like_iv);
        mLikeTv = (TextView) itemView.findViewById(R.id.circle_like_tv);
        mCommentIv = (ImageView) itemView.findViewById(R.id.circle_comment_iv);
        mCommentTv = (TextView) itemView.findViewById(R.id.circle_comment_tv);
        mCommentList = (CommentListView) itemView.findViewById(R.id.commentList);
    }
    public abstract void initSubView(int viewType, ViewStub viewStub);
}
