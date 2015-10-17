package com.jinshui.thridclass.utils;

import android.util.Log;

/**
 * Created by Administrator on 2015/10/17.
 */
public class MyLog {

    private static final boolean LOG_ON = true;

    private MyLog(){

    }
    public static void d(String tag,String msg){
        if (LOG_ON){
            Log.d(tag,msg);
        }
    }
    public static void e(String tag,String msg){
        if (LOG_ON){
            Log.e(tag, msg);
        }
    }
    public static void i(String tag,String msg){
        if (LOG_ON){
            Log.i(tag, msg);
        }
    }
    public static void w(String tag,String msg){
        if (LOG_ON){
            Log.w(tag, msg);
        }
    }
    public static void v(String tag,String msg){
        if (LOG_ON){
            Log.v(tag, msg);
        }
    }

}
