package com.hxg.u1.xiaoyuan.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.hxg.u1.xiaoyuan.bean.Schools;

/**
 * Created by huxianguang on 2017/3/12.
 * user Service
 */

public class UserService {
    public static void initUser() throws AVException {
        final AVUser user=AVUser.getCurrentUser();
        AVQuery<AVUser> query=new AVQuery<>("_User");
        query.include("schoolId");
        Schools schools= (Schools) query.find().get(0).get("schoolId");
        user.put("schoolId",schools);
    }
}
