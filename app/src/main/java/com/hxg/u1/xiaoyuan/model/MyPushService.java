package com.hxg.u1.xiaoyuan.model;

import android.content.Context;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVPush;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SendCallback;
import com.hxg.u1.xiaoyuan.activity.CircleActivity;
import com.hxg.u1.xiaoyuan.activity.MainActivity;
import com.hxg.u1.xiaoyuan.bean.AvUser;

/**
 * Created by huxianguang on 2017/3/27.
 * 推送服务
 */

public class MyPushService {
    //推送初始化
    public static void initPush(Context context) {
        com.avos.avoscloud.PushService.setDefaultPushCallback(context, MainActivity.class);
        PushService.subscribe(context, "CricleMessage", CircleActivity.class);
        AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (AvUser.getCurrentUser().getInstallationId() == null) {
                    UserService.setInstallationId(AVInstallation.getCurrentInstallation().getInstallationId());
                }
            }
        });
    }

    /**
     * @param installationId 推送的id
     * @param content        推送的内容
     */
    public static void pushUserComment(String installationId, String content) {
        AVQuery pushQuery = AVInstallation.getQuery();
        pushQuery.whereEqualTo("channels", "CricleMessage");
        pushQuery.whereEqualTo("installationId", installationId);
        AVPush.sendMessageInBackground("您收到来自 " + content + " 一条回复!", pushQuery, new SendCallback() {
            @Override
            public void done(AVException e) {

            }
        });
    }
}
