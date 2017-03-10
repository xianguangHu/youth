package com.hxg.u1.xiaoyuan.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
/**
 * reated by huxianguang on 2017/3/4.
 */
@AVClassName("Schools")
public class Schools extends AVObject {
    public Schools() {
    }
    public String  getSchoolName(){
        return super.getString("schoolName");
    }
}
