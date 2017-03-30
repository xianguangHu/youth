package com.hxg.u1.xiaoyuan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hxg.u1.xiaoyuan.R;
import com.hxg.u1.xiaoyuan.adapter.CommentRelattdAdapter;
import com.hxg.u1.xiaoyuan.bean.Comment;
import com.hxg.u1.xiaoyuan.model.CommentRelatedContract;
import com.hxg.u1.xiaoyuan.model.CommentRelatedPresenter;
import com.hxg.u1.xiaoyuan.utils.Constant;
import com.hxg.u1.xiaoyuan.widgets.DividerItemDecoration;
import com.hxg.u1.xiaoyuan.widgets.TitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

import static com.hxg.u1.xiaoyuan.utils.Constant.TYPE_PULLREFRESH;
import static com.hxg.u1.xiaoyuan.utils.Constant.TYPE_UPLOADREFRESH;


public class CommentRelatedActivity extends AppCompatActivity implements CommentRelatedContract.View{

    @BindView(com.hxg.u1.xiaoyuan.R.id.Comment_related_title)
    TitleBar mCommentRelatedTitle;
    @BindView(com.hxg.u1.xiaoyuan.R.id.comment_related_list)
    RecyclerView mCommentRelatedList;
    @BindView(com.hxg.u1.xiaoyuan.R.id.comment_related_ptr)
    PtrClassicFrameLayout mCommentRelatedPtr;
    private CommentRelattdAdapter mAdapter;
    private CommentRelatedPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_related);
        ButterKnife.bind(this);
        mPresenter = new CommentRelatedPresenter(this,this);
        initView();
        initEvent();

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    //初始化时件
    private void initEvent() {

    mCommentRelatedPtr.setPtrHandler(new PtrDefaultHandler2() {
        @Override
        public void onLoadMoreBegin(PtrFrameLayout frame) {//底部上拉刷新
            mPresenter.loadData(TYPE_UPLOADREFRESH,mAdapter.getDatas().size());
        }

        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {//下拉刷新
            mPresenter.loadData(TYPE_PULLREFRESH,0);
        }
    });
        mCommentRelatedPtr.autoRefresh();
    }

    private void initView() {
        initTitle();
        mCommentRelatedList.setLayoutManager(new LinearLayoutManager(this));
        mCommentRelatedList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new CommentRelattdAdapter();
        mCommentRelatedList.setAdapter(mAdapter);
    }

    private void initTitle() {
        mCommentRelatedTitle.setLeftImageResource(R.mipmap.left);
        mCommentRelatedTitle.setLeftText("返回");
        mCommentRelatedTitle.setLeftTextColor(getResources().getColor(R.color.white));
        mCommentRelatedTitle.setTitle("与我相关");
        mCommentRelatedTitle.setTitleColor(getResources().getColor(R.color.white));
        mCommentRelatedTitle.setBackgroundColor(getResources().getColor(R.color.Wathet));
        mCommentRelatedTitle.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void update2loadData(List<Comment> datas,int type) {
        //停止刷新
        mCommentRelatedPtr.refreshComplete();
        if (Constant.TYPE_PULLREFRESH==type) {
            mAdapter.setDatas(datas);
        }else if (Constant.TYPE_UPLOADREFRESH==type){
            mAdapter.getDatas().addAll(datas);
        }
        mAdapter.notifyDataSetChanged();
    }
}
