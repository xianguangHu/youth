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
import com.hxg.u1.xiaoyuan.adapter.viewholder.LostFoundImageViewHolder;
import com.hxg.u1.xiaoyuan.adapter.viewholder.LostFoundViewHolder;
import com.hxg.u1.xiaoyuan.bean.Image;
import com.hxg.u1.xiaoyuan.bean.LostFound;
import com.hxg.u1.xiaoyuan.bean.PhotoInfo;
import com.hxg.u1.xiaoyuan.model.LostFoundPresenter;
import com.hxg.u1.xiaoyuan.widgets.MultiImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huxianguang on 2017/3/14.
 */

public class LostFoundAdapter extends BaseRecycleViewAdapter {

    private LostFoundPresenter presenter;
    private Context mContext;

    public LostFoundAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lostfound_item, parent, false);
        viewHolder = new LostFoundImageViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        LostFoundViewHolder holder = (LostFoundViewHolder) viewHolder;
        LostFound lostFound = (LostFound) datas.get(position);
        AVUser user = lostFound.getUserId();
        String name = user.getUsername();
        String lostFoundTime = new SimpleDateFormat("yyyy-MM-dd").format(lostFound.getCreatedAt());
        holder.mNameTv.setText(name);
        holder.mTimeTv.setText(lostFoundTime);
        holder.mGoods.setText(lostFound.getGoods());
        holder.mLocal.setText(lostFound.getLocal());
        holder.mTime.setText(lostFound.getTime());
        if (!TextUtils.isEmpty(lostFound.getDescribe())) {
            holder.mContentTv.setText(lostFound.getDescribe());
        }
        holder.mContentTv.setVisibility(TextUtils.isEmpty(lostFound.getDescribe()) ? View.GONE : View.VISIBLE);
        List<Image> list = lostFound.getImages();
        if (list!=null) {
            //处理照片
            if (holder instanceof LostFoundImageViewHolder) {
                final List<PhotoInfo> photoInfos = new ArrayList<>();
                PhotoInfo photoInfo;
                for (int i = 0; i < list.size(); i++) {
                    photoInfo = new PhotoInfo();
                    photoInfo.setH(list.get(i).getHeight());
                    photoInfo.setW(list.get(i).getWidth());
                    photoInfo.setUrl(list.get(i).getImageUrl());
                    photoInfo.setImageId(list.get(i).getObjectId());
                    photoInfos.add(photoInfo);
                }
                if (photoInfos!=null&& photoInfos.size()>0){
                    ((LostFoundImageViewHolder) holder).multiImageView.setVisibility(View.VISIBLE);
                    ((LostFoundImageViewHolder) holder).multiImageView.setList(photoInfos);
                    ((LostFoundImageViewHolder)holder).multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

}
