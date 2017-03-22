package com.hxg.u1.xiaoyuan.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;

/**
 * Created by huxianguang on 2017/3/22.
 */
@AVClassName("Version")
public class VersionInfo extends AVObject{
    public VersionInfo(){
        super();
    }
    public int getVersionCode(){ //版本号
        return getInt("versionCode");
    }

    public String getVersionName(){//版本名
        return getString("versionName");
    }

    public String getVersionDesc(){//版本描述信息
        return getString("versionDesc");
    }

    public AVFile getVersionFile(){//版本文件
        return getAVFile("versionFile");
    }
    public String getVersionSize(){//版本大小
        return getString("versionSize");
    }
}
