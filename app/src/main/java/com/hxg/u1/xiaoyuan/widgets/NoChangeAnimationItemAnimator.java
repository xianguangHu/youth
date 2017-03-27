package com.hxg.u1.xiaoyuan.widgets;

import android.support.v7.widget.DefaultItemAnimator;

/**
 * Created by huxianguang on 2017/3/25.
 */

public class NoChangeAnimationItemAnimator extends DefaultItemAnimator {

    public NoChangeAnimationItemAnimator() {
        setSupportsChangeAnimations(false);
    }
}
