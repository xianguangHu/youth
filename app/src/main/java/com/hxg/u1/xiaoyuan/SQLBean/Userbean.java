package com.hxg.u1.xiaoyuan.SQLBean;

import org.litepal.crud.DataSupport;

/**
 * Created by huxianguang on 2017/3/20.
 */

public class Userbean extends DataSupport{
    private String userName;
    private String age;
    private String gender;
    private String SchoolName;
    private String headImageUri;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSchoolName() {
        return SchoolName;
    }

    public void setSchoolName(String schoolName) {
        SchoolName = schoolName;
    }

    public String getHeadImageUri() {
        return headImageUri;
    }

    public void setHeadImageUri(String headImageUri) {
        this.headImageUri = headImageUri;
    }
}
