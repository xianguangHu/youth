package com.hxg.u1.xiaoyuan.model;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVStatus;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.hxg.u1.xiaoyuan.bean.AvUser;
import com.hxg.u1.xiaoyuan.bean.Circle;
import com.hxg.u1.xiaoyuan.bean.Circles;
import com.hxg.u1.xiaoyuan.bean.Comment;
import com.hxg.u1.xiaoyuan.bean.PhotoInfo;
import com.hxg.u1.xiaoyuan.bean.Schools;
import com.hxg.u1.xiaoyuan.bean.Status;
import com.hxg.u1.xiaoyuan.utils.Constant;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by huxianguang on 2017/2/27.
 * 事件流service
 */

public class StatusService {


    //将动态信息保存到数据库
    public static void sendStatus(final String text, final List<Bitmap> bitmaps, final SaveCallback saveCallback) {
        final AVUser user = AVUser.getCurrentUser();
        final AVObject statusDetail = new AVObject(Constant.STATUS_DETAIL);
        statusDetail.saveInBackground(new com.avos.avoscloud.SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e != null) {
                    saveCallback.done(e);
                } else {
                    final AVObject circle = new AVObject("Circle");
                    circle.put(Constant.DETAIL_ID, statusDetail.getObjectId());
                    circle.put("message", text);
                    circle.put("userId", user);
                    circle.put("schoolId", AvUser.getCurrentUser().getSchool());
                    //保存到数据库
                    circle.saveInBackground();
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
                                String name = user.getUsername() + "_" + System.currentTimeMillis() + i;
                                final AVFile file = new AVFile(name, bs);
                                file.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(AVException e) {
                                        String url = file.getUrl();
                                        final AVObject image = new AVObject(Constant.IMAGE);
                                        image.put("circleId", circle.getObjectId());
                                        image.put("imageUrl", url);
                                        image.put("width", w);
                                        image.put("height", h);
                                        image.saveInBackground(saveCallback);
                                    }
                                });
                                out.flush();
                                out.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    } else {
                        saveCallback.done(e);
                    }

                }
            }
        });
    }

    /**
     *
     * @param limit 返回数据数量
     * @param skip 跳过数量
     * @return
     * @throws AVException
     */
    public static List<Circle> getStatusDatas(int limit,int skip) throws AVException {
        //获取到当前登录的user
        AVUser user = AVUser.getCurrentUser();
        //服务器获取数据
        AVQuery<AVObject> query = new AVQuery<>("Circle");
        query.setLimit(limit);
        query.setSkip(skip);
        query.include("userId");
        query.include("comments");
        query.include("likes");
        query.orderByDescending("createdAt");
        query.whereEqualTo("schoolId",AvUser.getCurrentUser().getSchool());
        List<AVObject> avCircle = query.find();
        for (AVObject tmp:avCircle){
            List<Comment> comments=tmp.getList("comments");
            if (null != comments) {
                for (Comment cmt: comments) {
                    cmt.fetchCreator();
                }
            }
        }
        return fetchDetailsAndGetStatuses(avCircle);
    }

    public static List<Circle> fetchDetailsAndGetStatuses(List<AVObject> avCircle) throws AVException {
        List<AVObject> objects = new ArrayList<>();
        for (AVObject status : avCircle) {
            //查询datailId
            String datailId = status.getString(Constant.DETAIL_ID);
            AVObject object = AVObject.createWithoutData(Constant.STATUS_DETAIL, datailId);
            objects.add(object);
        }
        //获取datail中的所有数据
        List<AVObject> avObjects = AVObject.fetchAll(objects);
        List<Circle> statuses = new ArrayList<>();
        for (int i = 0; i < avCircle.size(); i++) {
            final Circles avStauts = (Circles) avCircle.get(i);
            AVObject avObject = avObjects.get(i);
            AVRelation likers=avStauts.getLiker();
            likers.getQuery().findInBackground(new FindCallback() {
                @Override
                public void done(List list, AVException e) {
                    if (e == null) {
                        // results have all the Posts the current user liked.
                        avStauts.setLikedUsers(list);
                    }
                }
            });
            final Circle circle = new Circle();
            circle.setCircles(avStauts);
            circle.setDatail(avObject);
            statuses.add(circle);
            //根据Statusid查询到Status的图片文件文件
            AVQuery<AVObject> query = new AVQuery<>("Image");
            query.whereStartsWith("circleId", avCircle.get(i).getObjectId());
            List<AVObject> list = query.find();
            List<PhotoInfo> photoInfos = new ArrayList<>();
            PhotoInfo photoInfo;
            for (int q = 0; q < list.size(); q++) {
                photoInfo = new PhotoInfo();
                photoInfo.setUrl(list.get(q).getString("imageUrl"));
                photoInfo.setH((Integer) list.get(q).getNumber("height"));
                photoInfo.setW((Integer) list.get(q).getNumber("width"));
                photoInfo.setImageId(list.get(q).getObjectId());
                photoInfos.add(photoInfo);
            }
            circle.setImages(photoInfos);
        }

        return statuses;
    }

    public static void deleteStatus(Status status) throws AVException {
        AVStatus innerStatus = status.getInnerStatus();
        AVObject datail = status.getDatail();
        List<PhotoInfo> imags = status.getImages();
        if (imags.size() > 0) {
            for (int i = 0; i < imags.size(); i++) {
                String imageId = imags.get(i).getImageId();
                AVQuery.doCloudQueryInBackground("delete from Image where objectId='" + imageId + "'", new CloudQueryCallback<AVCloudQueryResult>() {
                    @Override
                    public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                        // 如果 e 为空，说明保存成功
                    }
                });
            }
        }
        innerStatus.delete();
        datail.delete();
    }

    public static List<Schools> getschool() throws AVException {
        AVQuery<Schools> schoolAVQuery = AVQuery.getQuery(Schools.class);
        List<Schools> schools = schoolAVQuery.find();
//        Map<String, String> map = new HashMap();
//        for (Schools school : schools) {
//            String name = school.getString("schoolName");
//            String id = school.getObjectId();
//            map.put(id, name);
//        }
        return schools;
    }

    //保存评论
    public static Comment addComment(String value, final String circleId, final String installationId,Circle circle) {
        final Comment comment = new Comment();
        comment.setContent(value);
        comment.setCreator(AVUser.getCurrentUser());
        comment.setToUserId(circle.getCircles().getUserId().getObjectId());
        comment.setCircle(circle.getCircles());
        comment.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (null != e) {
                    return;
                } else {
                    AVObject circles= AVObject.createWithoutData("Circle",circleId);
                    circles.addUnique("comments",comment);
                    circles.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e==null&& !TextUtils.isEmpty(installationId)){
                                //推送给用户
                                if (!installationId.equals(AvUser.getCurrentUser().getInstallationId())){
                                MyPushService.pushUserComment(installationId,AvUser.getCurrentUser().getUsername());
                                }

                            }
                        }
                    });
                }
            }
        });
        return comment;
    }

    /**
     *
     * 添加赞
     */
    public static void addLike(Circles circles){
        circles.addLiker(AVUser.getCurrentUser());
    }
}
