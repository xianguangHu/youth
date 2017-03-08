package com.hxg.u1.xiaoyuan.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.adapter.CircleAdapter;
import com.hxg.u1.xiaoyuan.bean.Circle;
import com.hxg.u1.xiaoyuan.contract.CircleContract;
import com.hxg.u1.xiaoyuan.model.CirclePresenter;
import com.hxg.u1.xiaoyuan.utils.CommentUtil;
import com.hxg.u1.xiaoyuan.utils.MainUtil;
import com.hxg.u1.xiaoyuan.widgets.DivItemDecoration;
import com.hxg.u1.xiaoyuan.widgets.TitleBar;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CircleActivity extends Activity implements CircleContract.View {

    @BindView(R.id.circle_title_bar)
    TitleBar mCircleTitleBar;
    @BindView(R.id.recyclerView)
    SuperRecyclerView mRecyclerView;
    @BindView(R.id.circle_bodyLayout)
    RelativeLayout mCircleBodyLayout;
    @BindView(R.id.circleEt)
    EditText mCircleEt;
    @BindView(R.id.sendIv)
    ImageView mSendIv;
    @BindView(R.id.editTextBodyLl)
    LinearLayout mEditTextBodyLl;
    private LinearLayoutManager mLayoutManager;
    private CirclePresenter presenter;
    private final static int TYPE_PULLREFRESH = 1;
    private CircleAdapter mCircleAdapter;
    private SwipeRefreshLayout.OnRefreshListener mRefreshListener;
    private String mCircleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        ButterKnife.bind(this);
        presenter = new CirclePresenter(this, this);
        initView();
//        mRecyclerView.getSwipeToRefresh().post(new Runnable(){
//            @Override
//            public void run() {
//                mRecyclerView.setRefreshing(true);//执行下拉刷新的动画
//                mRefreshListener.onRefresh();//执行数据加载操作
//            }
//        });
    }

    @SuppressLint({"ClickableViewAccessibility", "InlinedApi"})
    private void initView() {
        initTitle();
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DivItemDecoration(2, true));
        mRecyclerView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(mEditTextBodyLl.getVisibility()==View.VISIBLE){
                    updateEditTextBodyVisible(View.GONE,null);
                    return true;
                }
                return false;
            }
        });
        mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                System.out.println("刷新中。。。。。。。");
                presenter.loadData(TYPE_PULLREFRESH);
            }
        };
        mRecyclerView.setRefreshListener(mRefreshListener);
        mCircleAdapter = new CircleAdapter(this);
        mCircleAdapter.setCirclePresenter(presenter);
        mRecyclerView.setAdapter(mCircleAdapter);

        //发送评论
        mSendIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter!=null){
                    //发布评论
                    String content=mCircleEt.getText().toString().trim();
                    if (TextUtils.isEmpty(content)){
                        MainUtil.ToastUtil(CircleActivity.this,"评论内容不能为空...");
                        return;
                    }
                    presenter.addComment(content,mCircleId);
                }
            }
        });
    }

    private void initTitle() {
        mCircleTitleBar.setLeftImageResource(R.mipmap.left);
        mCircleTitleBar.setLeftText("返回");
        mCircleTitleBar.setTitle("校园圈");
        mCircleTitleBar.setTitleColor(getResources().getColor(R.color.white));
        mCircleTitleBar.setBackgroundColor(getResources().getColor(R.color.title_bg));
        TextView textView = (TextView) mCircleTitleBar.addAction(new TitleBar.TextAction("发布") {

            @Override
            public void performAction(View view) {
                //跳转到发布界面
                startActivity(new Intent(CircleActivity.this, CricleReleaseActivity.class));
            }
        });
        textView.setTextColor(getResources().getColor(R.color.white));
        mCircleTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void update2loadData(int loadType, List<Circle> datas) {
        if (loadType == TYPE_PULLREFRESH) {
            //设置刷新关闭
            mRecyclerView.setRefreshing(false);
            mCircleAdapter.setDatas(datas);
            System.out.println("刷新完毕。。。。。");
            mCircleAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void updateEditTextBodyVisible(int visibility,String circleId) {
        this.mCircleId=circleId;
        mEditTextBodyLl.setVisibility(visibility);
        if(View.VISIBLE==visibility){
            mCircleEt.requestFocus();
            //弹出键盘
            CommentUtil.showSoftInput(mCircleEt.getContext(),mCircleEt);
        }else if(View.GONE==visibility){
            //隐藏键盘
            CommentUtil.hideSoftInput(mCircleEt.getContext(),mCircleEt);
        }
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
