package com.hxg.u1.xiaoyuan.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

/**
 * Created by huxianguang on 2017/3/8.
 */
@AVClassName("Comment")
public class Comment extends AVObject {
    public Comment() {
    }

    public String getContent() {
        return getString("content");
    }

    public void setContent(String value) {
        put("content", value);
    }

    public void setCreator(AVUser user) {
        put("creator", user);
    }

    public AvUser getCreator() {
        return getAVUser("creator");
    }

    public void fetchCreator() {
        AVUser usr = getAVUser("creator");
        if (null == usr.getCreatedAt()) {
            try {
                usr.fetchInBackground(null);
            } catch (Exception ex) {
            }
        }
    }

    public String getToUserId() {
        return getString("toUserId");
    }

    public void setToUserId(String userId) {
        put("toUserId", userId);
    }

    public void setCircle(Circles circle) {
        put("CircleId", circle);
    }

}
