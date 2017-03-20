package com.hxg.u1.xiaoyuan.fragment.base;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.adapter.LostFoundAdapter;
import com.hxg.u1.xiaoyuan.bean.LostFound;
import com.hxg.u1.xiaoyuan.contract.LostFoundContract;
import com.hxg.u1.xiaoyuan.listner.RecycleViewItemListener;
import com.hxg.u1.xiaoyuan.model.LostFoundPresenter;
import com.hxg.u1.xiaoyuan.widgets.DivItemDecoration;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;




/**
 * A simple {@link Fragment} subclass.
 * Lostfound Base类
 */
public class LostFoundFragment extends Fragment implements LostFoundContract.View{


    @BindView(R.id.fragment_base_LostFound)
    SuperRecyclerView mFragmentBaseLostFound;
    public LinearLayoutManager mLayoutManager;
    public LostFoundPresenter mPresenter;
    public LostFoundAdapter mLostFoundAdapter;

    public LostFoundFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lost_found, container, false);
        ButterKnife.bind(this, view);
        mPresenter = new LostFoundPresenter(getActivity(),this);
        initView();
        return view;
    }
    @SuppressLint({"ClickableViewAccessibility", "InlinedApi"})
    private void initView() {
        //初始化SuperRecyclerView
        mLayoutManager = new LinearLayoutManager(getActivity());
        mFragmentBaseLostFound.setLayoutManager(mLayoutManager);
        //添加分割线
        mFragmentBaseLostFound.addItemDecoration(new DivItemDecoration(3, true));
        mFragmentBaseLostFound.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

        mFragmentBaseLostFound.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                BaseonRefresh();
            }
        });
        //添加适配器
        mLostFoundAdapter = new LostFoundAdapter(getActivity());
        //adapter的监听
        mLostFoundAdapter.setItemListener(new RecycleViewItemListener() {
            @Override
            public void onItemClick(int position) {
            }
        });
        mFragmentBaseLostFound.setAdapter(mLostFoundAdapter);
    }
    //子类实现
    public void BaseonRefresh() {
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

    //获取到数据 将数据给适配器
    @Override
    public void update2loadData(List<LostFound> datas) {
        //刷新关闭
        mFragmentBaseLostFound.setRefreshing(false);
        mLostFoundAdapter.setDatas(datas);
        mLostFoundAdapter.notifyDataSetChanged();
    }
}
