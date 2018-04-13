package com.example.sy0317.keke.application;

import android.app.Application;

import com.example.sy0317.keke.utils.StaticClass;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;

/**
 * Created by shushu on 2018/4/5.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化bugly
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APPID, true);
        //Bmob默认初始化
        Bmob.initialize(this,StaticClass.BMOB_APPLICATIONID);
    }
}
