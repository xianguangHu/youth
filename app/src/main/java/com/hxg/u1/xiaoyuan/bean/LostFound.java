package com.hxg.u1.xiaoyuan.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

import java.util.List;

/**
 * Created by huxianguang on 2017/3/14.
 */
@AVClassName("LostFound")
public class LostFound extends AVObject{
    public LostFound(){
        super();
    }

    public void setGoods(String goods){
        put("goods",goods);
    }
    public String getGoods(){
        return getString("goods");
    }

    public void setLocal(String local){
        put("local",local);
    }
    public String getLocal(){
        return getString("local");
    }

    public void setTime(String time){
        put("time",time);
    }
    public String getTime(){
        return getString("time");
    }

    public void setDescribe(String describe){
        put("describe",describe);
    }
    public String getDescribe(){
        return getString("describe");
    }

    public AVUser getUserId() {
        return super.getAVUser("userId");
    }
    public void setUserId(AVUser user) {
        super.put("userId", user);
    }

    public void setType(int type){
        put("type",type);
    }

    public int getType(){
        return getInt("type");
    }

    public void setSchool(Schools school){
        put("school",school);
    }

    public Schools getSchool(){
        return (Schools) get("school");
    }
    @SuppressWarnings("unchecked")
    public List getImages() {
        return getList("images");
    }
    public void addImages(Image image) {
        add("images", image);
    }
}
