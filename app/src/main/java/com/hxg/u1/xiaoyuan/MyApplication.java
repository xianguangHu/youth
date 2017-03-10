package com.hxg.u1.xiaoyuan;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.hxg.u1.xiaoyuan.bean.Circles;
import com.hxg.u1.xiaoyuan.bean.Comment;
import com.hxg.u1.xiaoyuan.bean.Schools;
import com.hxg.u1.xiaoyuan.model.Model;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by huxianguang on 2017/2/22.
 */

public class MyApplication extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);//oom检测
        mContext = getApplicationContext();
        AVObject.registerSubclass(Circles.class);
        AVObject.registerSubclass(Schools.class);
        AVObject.registerSubclass(Comment.class);
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"JyXrfcYLEdipGx0R8oKLVMhS-9Nh9j0Va","jb7lyiuyxHe4lHXhxOoHH0Js");
        // 放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        //调试 在应用发布之前，请关闭调试日志。
        AVOSCloud.setDebugLogEnabled(true);
        //初始化数据模型层类
        Model.getInstance().init(this);
    }
    public static Context getContext(){
        return mContext;
    }
}
