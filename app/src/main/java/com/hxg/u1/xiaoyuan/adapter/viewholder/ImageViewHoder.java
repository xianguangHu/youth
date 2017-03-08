package com.hxg.u1.xiaoyuan.adapter.viewholder;

import android.view.View;
import android.view.ViewStub;

import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.widgets.MultiImageView;

/**
 * Created by huxianguang on 2017/2/28.
 */

public class ImageViewHoder extends CircleViewHolder {
    /** 图片*/
    public MultiImageView multiImageView;
    public ImageViewHoder(View itemView) {
        super(itemView, TYPE_IMAGE);
    }

    @Override
    public void initSubView(int viewType, ViewStub viewStub) {
        if(viewStub == null){
            throw new IllegalArgumentException("viewStub is null...");
        }
        viewStub.setLayoutResource(R.layout.viewstub_imgbody);
        View subView = viewStub.inflate();
        MultiImageView multiImageView = (MultiImageView) subView.findViewById(R.id.multiImagView);
        if(multiImageView != null){
            this.multiImageView = multiImageView;
        }
    }
}
