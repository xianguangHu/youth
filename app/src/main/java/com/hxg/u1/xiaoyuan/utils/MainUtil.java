package com.hxg.u1.xiaoyuan.utils;

import android.content.Context;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by huxianguang on 2017/2/27.
 */

public class MainUtil {
    public static void ToastUtil(Context context,String s){
        Toast.makeText(context,s,Toast.LENGTH_LONG).show();
    }
    public static boolean filterException(Context context,Exception e){
        if (e!=null){
            ToastUtil(context,e.getMessage());
            return false;
        }else {
            return true;
        }
    }
    //根据Value取Key
    public static String getKeyByValue(Map map, Object value) {
        String keys="";
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object obj = entry.getValue();
            if (obj != null && obj.equals(value)) {
                keys=(String) entry.getKey();
            }


        }
        return keys;
    }
}
