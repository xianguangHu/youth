package com.hxg.u1.xiaoyuan.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.activity.CircleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends Fragment {


    @BindView(R.id.friend_fragment_dynamic)
    RelativeLayout mFriendFragmentDynamic;
    Unbinder unbinder;

    public FriendFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.friend_fragment_dynamic)
    public void onViewClicked(View view) {
        startActivity(new Intent(getActivity(), CircleActivity.class));
    }
}
