package com.hxg.u1.xiaoyuan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVUser;
import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.activity.ImagePagerActivity;
import com.hxg.u1.xiaoyuan.adapter.viewholder.CircleViewHolder;
import com.hxg.u1.xiaoyuan.adapter.viewholder.ImageViewHoder;
import com.hxg.u1.xiaoyuan.bean.Circle;
import com.hxg.u1.xiaoyuan.bean.Comment;
import com.hxg.u1.xiaoyuan.bean.PhotoInfo;
import com.hxg.u1.xiaoyuan.model.CirclePresenter;
import com.hxg.u1.xiaoyuan.utils.MainUtil;
import com.hxg.u1.xiaoyuan.utils.StatusNetAsyncTask;
import com.hxg.u1.xiaoyuan.utils.UrlUtils;
import com.hxg.u1.xiaoyuan.widgets.CommentListView;
import com.hxg.u1.xiaoyuan.widgets.MultiImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by huxianguang on 2017/2/28.
 */

public class CircleAdapter extends BaseRecycleViewAdapter{
    private static final int TYPE_HEAD = 0;
    private Context mContext;
    private CirclePresenter presenter;
    private HeaderViewHolder viewHolder;

    public CircleAdapter(Context context) {
        this.mContext=context;
    }
    public void setCirclePresenter(CirclePresenter presenter){
        this.presenter=presenter;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return TYPE_HEAD;
        }
        int itemType=CircleViewHolder.TYPE_IMAGE;
        Circle status= (Circle) datas.get(position-1);
        if (status.getImages().size()>0){
            itemType= CircleViewHolder.TYPE_IMAGE;
        }
        return itemType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        if (viewType==TYPE_HEAD){
            View headView= LayoutInflater.from(parent.getContext()).inflate(R.layout.head_circle,parent,false);
            viewHolder = new HeaderViewHolder(headView);
        }else {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_circle_item,parent,false);
            if (viewType== CircleViewHolder.TYPE_IMAGE){
                    viewHolder=new ImageViewHoder(view);
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewholder, int position) {
        if (getItemViewType(position)==TYPE_HEAD){

        }else {
            final int circlePostition=position-1;
            final CircleViewHolder holder= (CircleViewHolder) viewholder;
            final Circle status= (Circle) datas.get(position-1);
            AVUser name= (AVUser) status.getInnerStatus().get("userId");
//            String name=status.getInnerStatus().getString("1");
            String statusTime=new SimpleDateFormat("yyyy-MM-dd").format(status.getInnerStatus().getCreatedAt());
            final String content=status.getInnerStatus().getString("message");
//            final String content=status.getInnerStatus().getString("1");
            holder.mNameTv.setText(name.getUsername());
            holder.mTimeTv.setText(statusTime);
            if (!TextUtils.isEmpty(content)){
                holder.mContentTv.setText(UrlUtils.formatUrlString(content));
            }
            holder.mContentTv.setVisibility(TextUtils.isEmpty(content)?View.GONE:View.VISIBLE);
            if (AVUser.getCurrentUser().getObjectId().equals(status.getInnerStatus().getObjectId())){
                //说明此条信息是自己发的 可以删除 显示删除
                holder.mDeleteBtn.setVisibility(View.VISIBLE);
            }else {
                holder.mDeleteBtn.setVisibility(View.GONE);
            }
            holder.mDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除
                    new StatusNetAsyncTask(mContext){

                        @Override
                        protected void doInBack() throws Exception {
//                            StatusService.deleteStatus(status);
                        }

                        @Override
                        protected void onPost(Exception e) {
                            if (e!=null){
                                MainUtil.ToastUtil(mContext,e.getMessage());
                            }else {
                            }
                        }
                    }.execute();
                }
            });
            if (CircleViewHolder.TYPE_IMAGE==holder.viewType) {
                //处理照片
                if (holder instanceof ImageViewHoder){
                    final List<PhotoInfo> photoInfos=status.getImages();
                    if (photoInfos!=null&& photoInfos.size()>0){
                    ((ImageViewHoder) holder).multiImageView.setVisibility(View.VISIBLE);
                    ((ImageViewHoder) holder).multiImageView.setList(photoInfos);
                     ((ImageViewHoder)holder).multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //imagesize是作为loading时的图片size
                                ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());

                                List<String> photoUrls = new ArrayList<String>();
                                for(PhotoInfo photoInfo : photoInfos){
                                    photoUrls.add(photoInfo.url);
                                }
                                ImagePagerActivity.startImagePagerActivity(mContext, photoUrls, position, imageSize);


                            }
                        });
                    }
                }
            }
            //点击评论图标
            holder.mCommentIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.showEditTextBody(status.getInnerStatus().getObjectId());
                }
            });


            //处理评论和点赞
            final List<Comment> comments=status.getInnerStatus().getList("comments");
            if (comments!=null&&comments.size()>0){
                //当评论不为空的时候 处理评论
                holder.mCommentTv.setText(comments.size()+"");

                holder.mCommentList.setOnItemClickListener(new CommentListView.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                    }
                });
                holder.mCommentList.setDatas(comments);
                holder.mCommentList.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return datas.size()+1;
    }
    public class HeaderViewHolder extends RecyclerView.ViewHolder{

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
