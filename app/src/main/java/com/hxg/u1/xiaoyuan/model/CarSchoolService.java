package com.hxg.u1.xiaoyuan.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.hxg.u1.xiaoyuan.bean.CarSchool;

import java.util.List;



/**
 * Created by huxianguang on 2017/3/26.
 */

public class CarSchoolService {
    /**
     * 获取学校附近的驾校
     */
    public static void getData(final CarSchoolCallBack callBack){
        AVQuery<CarSchool> carSchoolAVQuery=AVObject.getQuery(CarSchool.class);
        carSchoolAVQuery.include("imageUri");
        carSchoolAVQuery.findInBackground(new FindCallback<CarSchool>() {
            @Override
            public void done(List<CarSchool> list, AVException e) {
                    callBack.callback(list);
            }
        });
    }

    public interface CarSchoolCallBack{
        void callback(List<CarSchool> list);
    }
}
