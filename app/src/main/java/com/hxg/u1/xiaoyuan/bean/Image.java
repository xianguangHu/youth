package com.hxg.u1.xiaoyuan.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;

/**
 * Created by huxianguang on 2017/3/14.
 */
@AVClassName("Image")
public class Image extends AVObject {
    public Image() {
        super();
    }

    public void setSource(AVObject source) {
        put("source", source);
    }

    public AVObject getSource() {
        return (AVObject) get("source");
    }

    public void setWidth(int width) {
        put("width", width);
    }

    public int getWidth() {
        return getInt("width");
    }

    public void setHeight(int height) {
        put("height", height);
    }

    public int getHeight() {
        return getInt("height");
    }

    public AVFile getRawImage() {
        return super.getAVFile("imageFile");
    }

    public void setRawImage(AVFile file) {
        super.put("imageFile", file);
    }

    public void setImageUrl(AVFile file){
        put("imageUrl",file.getUrl());
    }
    public String getImageUrl(){
        return getString("imageUrl");
    }
}
