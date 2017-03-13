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
import com.hxg.u1.xiaoyuan.contract.LostFoundContract;
import com.hxg.u1.xiaoyuan.model.LostFoundPresenter;
import com.hxg.u1.xiaoyuan.widgets.DivItemDecoration;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LostFoundFragment extends Fragment implements LostFoundContract.View{


    @BindView(R.id.fragment_base_LostFound)
    SuperRecyclerView mFragmentBaseLostFound;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout.OnRefreshListener mRefreshListener;
    private LostFoundPresenter mPresenter;
    private LostFoundAdapter mLostFoundAdapter;

    public LostFoundFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lost_found, container, false);
        ButterKnife.bind(this, view);
        mPresenter = new LostFoundPresenter();
        initView();
        return view;
    }
    @SuppressLint({"ClickableViewAccessibility", "InlinedApi"})
    private void initView() {
        //初始化SuperRecyclerView
        mLayoutManager = new LinearLayoutManager(getActivity());
        mFragmentBaseLostFound.setLayoutManager(mLayoutManager);
        //添加分割线
        mFragmentBaseLostFound.addItemDecoration(new DivItemDecoration(2, true));
        mFragmentBaseLostFound.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        //下拉加载
        mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadData();
            }
        };
        mFragmentBaseLostFound.setRefreshListener(mRefreshListener);
        //添加适配器
        mLostFoundAdapter = new LostFoundAdapter();
        mFragmentBaseLostFound.setAdapter(mLostFoundAdapter);
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
