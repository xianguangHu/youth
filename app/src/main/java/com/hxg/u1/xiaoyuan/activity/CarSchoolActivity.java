package com.hxg.u1.xiaoyuan.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.adapter.CarSchoolAdapter;
import com.hxg.u1.xiaoyuan.bean.CarSchool;
import com.hxg.u1.xiaoyuan.contract.CarSchoolContract;
import com.hxg.u1.xiaoyuan.model.CarSchoolPresenter;
import com.hxg.u1.xiaoyuan.widgets.DivItemDecoration;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarSchoolActivity extends Activity implements CarSchoolContract.View{

    @BindView(R.id.carSchool_list)
    SuperRecyclerView mCarSchoolList;
    private CarSchoolPresenter mPresenter;
    private CarSchoolAdapter mCarSchoolAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_school);
        ButterKnife.bind(this);
        mPresenter = new CarSchoolPresenter(this,this);
        initRecyclserView();
    }
    //初始化recyclseView
    private void initRecyclserView() {
//        //保持固定的大小
//        mCarSchoolList.setHasFixedSize(true);
//        //item动画
//        mCarSchoolList.setItemAnimator(new NoChangeAnimationItemAnimator());
        mCarSchoolList.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        //添加分割线
        mCarSchoolList.addItemDecoration(new DivItemDecoration(3, true));
        mCarSchoolList.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        //刷新
        mCarSchoolList.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadData();
            }
        });
        mCarSchoolAdapter = new CarSchoolAdapter(this);
        mCarSchoolList.setAdapter(mCarSchoolAdapter);
    }
    @Override
    public void update2loadData(List<CarSchool> datas) {
        mCarSchoolList.setRefreshing(false);
        mCarSchoolAdapter.setDatas(datas);
        mCarSchoolAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {

    }

}
