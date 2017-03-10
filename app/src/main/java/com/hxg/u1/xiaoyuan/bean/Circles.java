package com.hxg.u1.xiaoyuan.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

import java.util.List;

/**
 * Created by huxianguang on 2017/3/10.
 */
@AVClassName("Circle")
public class Circles extends AVObject{

    public Circles(){}
    public AVUser getUserId(){
        return super.getAVUser("userId");
    }
    public void setUserId(AVUser user){
        super.put("userId",user);
    }

    @SuppressWarnings("unchecked")
    public List getComments(){
        return getList("comments");
    }

    public void setComments(Comment comment){
        addUnique("comments",comment);
    }
    public String getMessage(){
        return super.getString("message");
    }
    public void setMessage(String value){
        super.put("message",value);
    }
    public CirclesDetail getDetailId(){
        return (CirclesDetail) super.get("detailId");
    }
    public void setDetailId(CirclesDetail circlesDetail){
        super.put("detailId",circlesDetail);
    }
    public void setSchools(Schools schools){
        super.put("schoolId",schools);
    }
    public Schools getSchools(){
        return (Schools) super.get("schoolId");
    }
}
