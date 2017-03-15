package com.hxg.u1.xiaoyuan.fragment;

import com.hxg.u1.xiaoyuan.fragment.base.LostFoundFragment;
import com.hxg.u1.xiaoyuan.utils.Constant;

public class LostFragment extends LostFoundFragment{
    public LostFragment() {
        // Required empty public constructor
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        init();
//        return inflater.inflate(R.layout.fragment_lost, container, false);
//    }

//    private void init() {
//
//
//    }
    //下拉刷新

    @Override
    public void BaseonRefresh() {
        mPresenter.loadData(Constant.LOSTSTATE);
    }
}
