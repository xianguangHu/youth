package com.hxg.u1.xiaoyuan.bean;

import com.avos.avoscloud.AVObject;

import java.util.List;

/**
 * Created by huxianguang on 2017/2/28.
 * Status bean
 */

public class Circle {
    /**
     * 可以理解  innerStatus为数据库中Status对象
     *             datail为数据库Status_dataill对象
     */
    private Circles circles;
    private AVObject datail;
    private List<PhotoInfo> images;

    public List<PhotoInfo> getImages() {
        return images;
    }

    public void setImages(List<PhotoInfo> images) {
        this.images = images;
    }


    public AVObject getDatail() {
        return datail;
    }

    public void setDatail(AVObject datail) {
        this.datail = datail;
    }

    public Circles getCircles() {
        return circles;
    }

    public void setCircles(Circles circles) {
        this.circles = circles;
    }
}
