package com.hxg.u1.xiaoyuan.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by huxianguang on 2017/3/22.
 */

public class SDCardUtil {
    /**
     * 获取SD卡的根目录
     * @return null：不存在SD卡
     */
    public static File getRootDirectory(){
        return isAvailable()? Environment.getExternalStorageDirectory():null;
    }

    /**
     * SD卡是否可用
     * @return 只有当SD卡已经安装并且准备好了才返回true
     */
    public static boolean isAvailable(){
        return getState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡的状态
     * @return
     */
    public static String getState(){
        return Environment.getExternalStorageState();
    }
}
