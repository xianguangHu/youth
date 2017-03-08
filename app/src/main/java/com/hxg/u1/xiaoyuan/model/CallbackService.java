package com.hxg.u1.xiaoyuan.model;

import java.util.List;

/**
 * Created by huxianguang on 2017/3/4.
 */

public interface CallbackService<T> {
    void callback(List<T> list);
}
