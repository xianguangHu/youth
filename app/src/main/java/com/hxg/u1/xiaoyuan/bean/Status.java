package com.hxg.u1.xiaoyuan.bean;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVStatus;

import java.util.List;

/**
 * Created by huxianguang on 2017/2/28.
 * Status bean
 */

public class Status {
    /**
     * 可以理解  innerStatus为数据库中Status对象
     *             datail为数据库Status_dataill对象
     */
    private AVStatus innerStatus;
    private AVObject datail;
    private List<PhotoInfo> images;

    public List<PhotoInfo> getImages() {
        return images;
    }

    public void setImages(List<PhotoInfo> images) {
        this.images = images;
    }

    public AVStatus getInnerStatus() {
        return innerStatus;
    }

    public void setInnerStatus(AVStatus innerStatus) {
        this.innerStatus = innerStatus;
    }

    public AVObject getDatail() {
        return datail;
    }

    public void setDatail(AVObject datail) {
        this.datail = datail;
    }


}
