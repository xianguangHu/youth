package com.hxg.u1.xiaoyuan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.fragment.base.LostFoundFragment;

public class FoundFragment extends LostFoundFragment {


    public FoundFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_found, container, false);
    }

}
