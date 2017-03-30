package com.hxg.u1.xiaoyuan.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.hxg.u1.xiaoyuan.bean.AvUser;
import com.hxg.u1.xiaoyuan.bean.Comment;

import java.util.List;

/**
 * Created by huxianguang on 2017/3/29.
 * 评论相关的service
 */

public class CommentRelatedService {
    /**
     *
     * @param limit 返回多少条数据
     * @param skip 跳过多少条数据
     */
    public static void getMyRelated(int limit, int skip, final CommentRelatedCallBack callback){
        AVQuery<Comment> query= AVObject.getQuery(Comment.class);
        query.whereEqualTo("toUserId", AvUser.getCurrentUserId());
        query.whereNotEqualTo("creator", AVUser.getCurrentUser());
        query.setLimit(limit);
        query.setSkip(skip);
        query.include("creator");
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> list, AVException e) {
                if (e==null&&list!=null){
                    callback.callback(list);
                }
            }
        });
    }
   public interface CommentRelatedCallBack{
        void callback(List<Comment> list);
    }
}
