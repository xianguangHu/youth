package com.hxg.u1.xiaoyuan.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.activity.CircleActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends Fragment {


    public FriendFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        startActivity(new Intent(getActivity(), CircleActivity.class));
        return inflater.inflate(R.layout.fragment_friend, container, false);
    }

}
