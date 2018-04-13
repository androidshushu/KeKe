package com.example.sy0317.keke.utils;

import android.util.Log;

/**
 * Created by shushu on 2018/4/9.
 * 项目名：KeKe
 * 包名：  com.example.sy0317.keke.utils
 * 文件名：L
 * 描述    KeKe
 */

public class L {
    //开关
    private static final boolean DEBUG = true;
    //TAG
    private static final String TAG = "KeKeLog";
    //五个等级DIWEF

    public static  void d (String text){
        if (DEBUG){
            Log.d(TAG, text);
        }
    }
    public static  void i (String text){
        if (DEBUG){
            Log.i(TAG, text);
        }
    }
    public static  void e (String text){
        if (DEBUG){
            Log.e(TAG, text);
        }
    }
    public static  void w (String text){
        if (DEBUG){
            Log.w(TAG, text);
        }
    }

}
