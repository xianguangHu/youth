package com.hxg.u1.xiaoyuan.bean;

import com.avos.avoscloud.AVUser;

/**
 * Created by huxianguang on 2017/3/12.
 */

public class AvUser extends AVUser{
    public void setImageUri(String uri){
        this.put("imageuri",uri);
    }
    public String getImageUri(){
        return this.getString("imageuri");
    }
}
