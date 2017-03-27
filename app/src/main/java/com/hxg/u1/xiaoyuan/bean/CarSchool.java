package com.hxg.u1.xiaoyuan.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

import java.io.Serializable;

/**
 * Created by huxianguang on 2017/3/26.
 */
@AVClassName("carSchool")
public class CarSchool extends AVObject implements Serializable{
    public CarSchool() {
        super();
    }
    public String getCarSchoolName(){
        return getString("carSchoolName");
    }

    public String getImageUri(){
        String uri=getAVFile("imageUri").getUrl();
        if (uri!=null){
            return uri;
        }else {
            return "qqq";
        }
    }

    public String getContent(){
        return getString("content");
    }

    public String getPrice(){
        return getString("price");
    }

    public int getSold(){
        return getInt("sold");
    }

    public String getAddress(){
        return getString("address");
    }
}
