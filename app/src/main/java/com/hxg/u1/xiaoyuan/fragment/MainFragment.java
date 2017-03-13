package com.hxg.u1.xiaoyuan.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.bean.Schools;
import com.hxg.u1.xiaoyuan.model.UserService;
import com.hxg.u1.xiaoyuan.utils.StatusNetAsyncTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    @BindView(R.id.fragment_main_school)
    TextView mFragmentMainSchool;
    @BindView(R.id.fragment_main_lost)
    LinearLayout mFragmentMainLost;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;

    }

    private void initView() {
        new StatusNetAsyncTask(getActivity()) {
            @Override
            protected void doInBack() throws Exception {
                UserService.initUser();
            }

            @Override
            protected void onPost(Exception e) {
                Schools schools = (Schools) AVUser.getCurrentUser().get("schoolId");
                System.out.println(schools.getSchoolName());
                mFragmentMainSchool.setText(schools.getSchoolName());

            }
        }.execute();
    }

    @OnClick(R.id.fragment_main_lost)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragment_main_lost:
                
                break;
        }
    }
}
