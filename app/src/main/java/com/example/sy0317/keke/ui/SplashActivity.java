package com.example.sy0317.keke.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.example.sy0317.keke.MainActivity;
import com.example.sy0317.keke.R;
import com.example.sy0317.keke.utils.L;
import com.example.sy0317.keke.utils.ShareUtils;
import com.example.sy0317.keke.utils.StaticClass;
import com.example.sy0317.keke.utils.UtilsTools;

/**
 * Created by shushu on 2018/4/9.
 * 项目名：KeKe
 * 包名：  com.example.sy0317.keke.ui
 * 文件名：SplashActivity
 * 描述    KeKe  闪屏页
 */

public class SplashActivity extends AppCompatActivity {
    /**
     * 1.延时2000ms
     * 2.判断程序是否第一次运行
     * 3.自定义字体
     * 4.Activity全屏主题
     */

    private TextView tv_splash;

   private Handler handler = new Handler(){
       @Override
       public void handleMessage(Message msg) {
           super.handleMessage(msg);
           switch (msg.what){
               case StaticClass.HANDLER_SPLASH:
                   //判断程序是否是第一次运行
                   if (isFirst()){
                        startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                   }else {
//                       startActivity(new Intent(SplashActivity.this, MainActivity.class));
                       startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                   }
                   finish();
                   break;
           }
       }
   };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//remove title bar 即隐藏标题栏
//        getSupportActionBar().hide();// 隐藏ActionBar
        L.d("splash_activity_onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        L.d("splash_activity_onCreate");
        initView();
    }


    //初始化view
    private void initView() {
        L.d("splash_activity_initView");
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH,2000);
        tv_splash = findViewById(R.id.tv_splash);

        UtilsTools.setFont(this,tv_splash);

    }
    //判断程序是否是第一次运行
    private boolean isFirst(){
       boolean isFirst =  ShareUtils.getBoolean(this,StaticClass.SHARE_IS_FIRST,true);
       if (isFirst){
           L.d("SplashActivity_isFirst true");
           ShareUtils.putBoolean(this,StaticClass.SHARE_IS_FIRST,false);
           return true;
       }else {
           L.d("SplashActivity_isFirst false");
           return false;
       }
    }
    //禁止返回键
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
