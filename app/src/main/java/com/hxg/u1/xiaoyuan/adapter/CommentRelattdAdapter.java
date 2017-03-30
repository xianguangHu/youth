package com.hxg.u1.xiaoyuan.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.bean.Comment;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huxianguang on 2017/3/28.
 */

public class CommentRelattdAdapter extends BaseRecycleViewAdapter {


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.commentrelated_item, parent, false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Log.v("数量1",datas.size()+"");
        ViewHoder hoder= (ViewHoder) viewHolder;
        Comment comment= (Comment) datas.get(position);
        hoder.mCommentRelatedItemIv.setImageURI(Uri.parse(comment.getCreator().getImageUri()));
        hoder.mCommentRelatedItemName.setText(comment.getCreator().getUsername());
        String commentTime=new SimpleDateFormat("yyyy-MM-dd HH时mm分").format(comment.getCreatedAt());
        hoder.mCommentRelatedItemDate.setText(commentTime);
        hoder.mCommentRelatedItemContent.setText(comment.getContent());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        @BindView(R.id.comment_related_item_iv)
        SimpleDraweeView mCommentRelatedItemIv;
        @BindView(R.id.comment_related_item_name)
        TextView mCommentRelatedItemName;
        @BindView(R.id.comment_related_item_date)
        TextView mCommentRelatedItemDate;
        @BindView(R.id.comment_related_item_content)
        TextView mCommentRelatedItemContent;
        public ViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
