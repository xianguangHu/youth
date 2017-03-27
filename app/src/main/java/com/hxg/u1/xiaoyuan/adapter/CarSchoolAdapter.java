package com.hxg.u1.xiaoyuan.adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.activity.CarSchoolDetailActivity;
import com.hxg.u1.xiaoyuan.bean.CarSchool;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by huxianguang on 2017/3/25.
 */

public class CarSchoolAdapter extends BaseRecycleViewAdapter {
    private Context mContext;



    public CarSchoolAdapter(Context context) {
        this.mContext=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carschool_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        final CarSchool carSchool = (CarSchool) datas.get(position);
        holder.mCarSchoolItemIv.setImageURI(Uri.parse(carSchool.getImageUri()));
        holder.mCarSchoolItemTitle.setText(carSchool.getCarSchoolName());
        holder.mCarSchoolItemContent.setText(carSchool.getContent());
        holder.mCarSchoolItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, CarSchoolDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("carSchool",carSchool);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.carSchool_item)
        public LinearLayout mCarSchoolItem;
        @BindView(R.id.carSchool_item_iv)
        public SimpleDraweeView mCarSchoolItemIv;
        @BindView(R.id.carSchool_item_title)
        public TextView mCarSchoolItemTitle;
        @BindView(R.id.carSchool_item_content)
        public TextView mCarSchoolItemContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
