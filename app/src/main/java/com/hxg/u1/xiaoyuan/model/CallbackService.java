package com.hxg.u1.xiaoyuan.model;

import com.avos.avoscloud.AVException;

/**
 * Created by huxianguang on 2017/3/4.
 */

public interface CallbackService {
//    void callback(List<T> list);
    void callback(String uri, AVException e);
}
