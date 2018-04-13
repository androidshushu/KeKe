package com.example.sy0317.keke.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by shushu on 2018/4/9.
 * 项目名：KeKe
 * 包名：  com.example.sy0317.keke.utils
 * 文件名：ShareUtils
 * 描述    SharedPreferences封装
 */

public class ShareUtils {
    public static final String NAME  ="config";

    public static void putString (Context mContext,String key,String value) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_APPEND);
        sp.edit().putString(key,value).commit();
    }

    public static String  getString(Context mContext,String key,String defValue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_APPEND);
        return sp.getString(key,defValue);
    }

    public static void putInt (Context mContext,String key,int value) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_APPEND);
        sp.edit().putInt(key,value).commit();
    }

    public static int  getInt(Context mContext,String key,int defValue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_APPEND);
        return sp.getInt(key,defValue);
    }

    public static void putBoolean (Context mContext,String key,boolean value) {
        L.d("ShareUtil_putBoolean");
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_APPEND);
        sp.edit().putBoolean(key,value).commit();
    }

    public static boolean  getBoolean(Context mContext,String key,boolean defValue){
        L.d("ShareUtil_getBoolean");
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_APPEND);
        return sp.getBoolean(key,defValue);
    }

    public static void deleShare(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences(NAME,Context.MODE_APPEND);
        sp.edit().remove(key).commit();
    }

    public static void deleAllShare(Context context){
        SharedPreferences sp = context.getSharedPreferences(NAME,Context.MODE_APPEND);
        sp.edit().clear().commit();
    }




















    private void test (Context context){
        SharedPreferences sp = context.getSharedPreferences("config",Context.MODE_APPEND);
        //拿出来用
        sp.getString("key","未获取的的值给他的默认值");
        //存储进去
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("","");
        editor.commit();
    }
}
