package com.hxg.u1.xiaoyuan;

import android.content.Context;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.hxg.u1.xiaoyuan.bean.AvUser;
import com.hxg.u1.xiaoyuan.bean.CarSchool;
import com.hxg.u1.xiaoyuan.bean.Circles;
import com.hxg.u1.xiaoyuan.bean.Comment;
import com.hxg.u1.xiaoyuan.bean.Image;
import com.hxg.u1.xiaoyuan.bean.LostFound;
import com.hxg.u1.xiaoyuan.bean.Schools;
import com.hxg.u1.xiaoyuan.bean.VersionInfo;
import com.hxg.u1.xiaoyuan.model.Model;
import com.hxg.u1.xiaoyuan.model.UserService;
import com.inmobi.sdk.InMobiSdk;
import com.squareup.leakcanary.LeakCanary;

import org.litepal.LitePalApplication;

/**
 * Created by huxianguang on 2017/2/22.
 */

public class MyApplication extends LitePalApplication {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);//oom检测
        mContext = getApplicationContext();
        AVUser.alwaysUseSubUserClass(AvUser.class);
        AVObject.registerSubclass(VersionInfo.class);
        AVObject.registerSubclass(Image.class);
        AVObject.registerSubclass(LostFound.class);
        AVObject.registerSubclass(Circles.class);
        AVObject.registerSubclass(Schools.class);
        AVObject.registerSubclass(Comment.class);
        AVObject.registerSubclass(CarSchool.class);
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"JyXrfcYLEdipGx0R8oKLVMhS-9Nh9j0Va","jb7lyiuyxHe4lHXhxOoHH0Js");
        // 放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        //调试 在应用发布之前，请关闭调试日志。
        AVOSCloud.setDebugLogEnabled(true);
        //初始化数据模型层类
        Model.getInstance().init(this);
        Fresco.initialize(this);
        InMobiSdk.init(this,"af37a12980cf4274868167eb4bf35114"); //'this' is used specify c
        //推送初始化
    }
    public static Context getContext(){
        return mContext;
    }
}
