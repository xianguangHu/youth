package com.hxg.u1.xiaoyuan.model;

import android.graphics.Bitmap;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.hxg.u1.xiaoyuan.bean.Image;
import com.hxg.u1.xiaoyuan.bean.LostFound;
import com.hxg.u1.xiaoyuan.bean.Schools;
import com.hxg.u1.xiaoyuan.utils.Constant;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by huxianguang on 2017/3/14.
 * 失物招领service
 */

public class LostFoundService {
    /**
     * @param type     失物and招领
     * @param goods    武平名称
     * @param local    什么地方
     * @param time     时间
     * @param describe 描述
     */
    public static void sendLostFound(String type, final String goods, final String local, final String time, final String describe, final List<Bitmap> bitmaps, final SaveCallback saveCallback) {
        final LostFound lostFound = new LostFound();
        int mtype = 3;
        if (type.equals(Constant.LOST)) {
            mtype = Constant.LOSTSTATE;
        } else if (type.equals(Constant.FOUND)) {
            mtype = Constant.FOUNDSTATE;
        }
        lostFound.setGoods(goods);
        lostFound.setLocal(local);
        lostFound.setDescribe(describe);
        lostFound.setTime(time);
        lostFound.setUserId(AVUser.getCurrentUser());
        lostFound.setType(mtype);
        lostFound.setSchool((Schools) AVUser.getCurrentUser().get("schoolId"));
        lostFound.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (bitmaps.size() > 0) {
                    ByteArrayOutputStream out;
                    for (int i = 0; i < bitmaps.size(); i++) {
                        Bitmap bitmap = bitmaps.get(i);
                        final int w = bitmap.getWidth();
                        final int h = bitmap.getHeight();
                        try {
                            out = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
                            byte[] bs = out.toByteArray();
                            String name = AVUser.getCurrentUser().getUsername() + "_" + System.currentTimeMillis() + i;
                            final AVFile file = new AVFile(name, bs);
                            file.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    final Image image = new Image();
                                    image.setHeight(h);
                                    image.setWidth(w);
                                    image.setRawImage(file);
                                    image.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(AVException e) {
                                            try {
                                                LostFound update= AVObject.createWithoutData(LostFound.class,lostFound.getObjectId());
                                                update.addImages(image);
                                                update.saveInBackground();
                                            } catch (AVException e1) {
                                                e1.printStackTrace();
                                            }
                                            saveCallback.done(e);
                                        }
                                    });
                                }
                            });

                            out.flush();
                            out.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });


    }


}
