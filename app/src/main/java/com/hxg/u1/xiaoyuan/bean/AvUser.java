package com.hxg.u1.xiaoyuan.bean;

import com.avos.avoscloud.AVUser;

/**
 * Created by huxianguang on 2017/3/12.
 */

public class AvUser extends AVUser{
    public static String getCurrentUserId(){
        AvUser user=getCurrentUser(AvUser.class);
        return (null!=user?user.getObjectId():null);
    }

    public static AvUser getCurrentUser(){
        return getCurrentUser(AvUser.class);
    }
    public String getImageUri(){
        String uri=getString("imageuri");
       if (uri!=null){
           return uri;
       }else {
           return "qqq";
       }
    }
    public String getAge(){
        return getString("age");
    }
    public String getGender(){
        return getString("gender");
    }

    public Schools getSchool(){
        return (Schools) get("schoolId");
    }
    public String getInstallationId(){
        return getString("installationId");
    }
}
