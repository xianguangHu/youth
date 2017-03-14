package com.hxg.u1.xiaoyuan.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huxianguang on 2017/3/10.
 */
@AVClassName("Circle")
public class Circles extends AVObject{

    public Circles(){
        super();
    }
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

    /**
     * Like，我们使用 AVRelation 来链接 Image 和 AVUser，给 Image 类增加如下属性和方法：
     */

    public AVRelation getLiker() {
        AVRelation relation = getRelation("likes");
        return relation;
    }
    public void removeLiker(AVUser user) {
        AVRelation users = getLiker();
        users.remove(user);
        this.saveInBackground();
    }
    public void addLiker(AVUser user) {
        AVRelation users = getLiker();
        users.add(user);
        this.saveInBackground();
    }

    /**
     * 为了展示方便，我们给 Image 类增加一个非持久化的属性：
     */
    List likedUsers = new ArrayList();
    public void setLikedUsers(List usr) {
        if (null == usr) return;
        this.likedUsers = usr;
    }
    public List getLikedUsers() {
        return this.likedUsers;
    }
    public int getLikerCount() {
        return this.likedUsers.size();
    }
    public void addLikedUsers(AVUser user){
        likedUsers.add(user);
    }

    /**
     *
     * @return true 表示自己已经点过赞
     * false 表示没有点赞
     */
    public boolean isLike(){
        List list=getLikedUsers();
        for (int i=0;i<list.size();i++){
            AVUser user= (AVUser) list.get(i);
            if (user.getObjectId().equals(AVUser.getCurrentUser().getObjectId())){
                //说明自己已经点过赞
                return true;
            }else {
                return false;
            }
        }
        return false;
    }
}
