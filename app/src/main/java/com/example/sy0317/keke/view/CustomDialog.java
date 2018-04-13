package com.example.sy0317.keke.view;

import android.app.Dialog;
import android.content.Context;
import android.icu.text.LocaleDisplayNames;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.sy0317.keke.R;

/**
 * Created by shushu on 2018/4/11.
 * 项目名：KeKe
 * 包名：  com.example.sy0317.keke.view
 * 文件名：CustomDialog
 * 描述    KeKe
 */

public class CustomDialog extends Dialog {
    //定义模板
    public CustomDialog(@NonNull Context context,int layout,int style) {
        this(context, WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT,layout,style, Gravity.CENTER);
    }
    //定义属性
    public CustomDialog(Context context,int width,int height,int layout,int style,int gravity,int anim ){
        super(context, style);
        setContentView(layout);
        Window window  = getWindow();
        WindowManager.LayoutParams layoutParams  = window.getAttributes();
        layoutParams.width = width;
        layoutParams.height = height;
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(anim);

    }
    public CustomDialog(Context context,int width,int height,int layout,int style,int gravity ){
        this(context,width,height,layout,style,gravity,R.style.pop_anim_style);

    }
}
