package com.hxg.u1.xiaoyuan.utils;

/**
 * Created by huxianguang on 2017/2/27.
 */

public class Constant {
    //sharedPrefs保存的文件名
    public static final String FILE_NAME="MySetting";
    //版本更新下载文件路径
    public static final String UPDATEFILE_PATH="/updateVersion/youth_app.apk";
    public static final String STATUS_DETAIL="StatusDetail";
    public static final String DETAIL_ID="detailId";
    public static final String CREATED_AT = "createdAt";
    public static final String IMAGE="Image";

    //失物招领常量
    public static final String LOSTANDFOUND="lostAndfound";
    public static final String LOST="lost";
    public static final String FOUND="found";
    public static final int LOSTSTATE=0;
    public static final int FOUNDSTATE=1;
    //用户头像上传
    public static final int CROP_SMALL_PICTURE = 2;//头像剪裁后标签

    //校园圈数据type
    public final static int TYPE_PULLREFRESH = 1;
    //底部加载
    public final static int TYPE_UPLOADREFRESH = 2;
}
