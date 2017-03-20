package com.hxg.u1.xiaoyuan.fragment;

import com.hxg.u1.xiaoyuan.fragment.base.LostFoundFragment;
import com.hxg.u1.xiaoyuan.utils.Constant;

public class FoundFragment extends LostFoundFragment {


    public FoundFragment() {
        // Required empty public constructor
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_found, container, false);
//    }


    @Override
    public void BaseonRefresh() {
        mPresenter.loadData(Constant.FOUNDSTATE);
    }
}
