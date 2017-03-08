package com.hxg.u1.xiaoyuan.bean;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by huxianguang on 2017/2/27.
 */

public class ImageItem implements Serializable {
    public String imageId;
    public String thumbnailPath;
    public String imagePath;
    private Bitmap bitmap;
    public boolean isSelected=false;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
