package com.example.sy0317.keke.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by shushu on 2018/4/9.
 * 项目名：KeKe
 * 包名：  com.example.sy0317.keke.utils
 * 文件名：UtilsTools
 * 描述    KeKe 统一的工具类
 */

public class UtilsTools {
    //设置字体
    public static void setFont(Context context, TextView textView){
        Typeface fontType = Typeface.createFromAsset(context.getAssets(),"fonts/FONT.ttf");
        textView.setTypeface(fontType);
    }
    //保存图片到Share下
    public static void putImageToShare(Context context,ImageView imageView){
        //保存
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        //1.将Bitmap压缩成为字节数组输出流
        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,80,byStream);
        //2.利用base64将我们的字节数组输出流转换成String
        byte[] byteArray = byStream.toByteArray();
        String imgString = new String(Base64.encodeToString(byteArray,Base64.DEFAULT));
        //3.将String保存sharetils
        ShareUtils.putString(context,"image_title",imgString);
    }
    public static void getImageToShare(Context context,ImageView imageView){
        //1.拿到String
        String imageString = ShareUtils.getString(context,"image_title","");
        if (!imageString.equals("")){
            //2.利用Base64将我们的String转换
            byte[] byteArray = Base64.decode(imageString,Base64.DEFAULT);
            ByteArrayInputStream byStream = new ByteArrayInputStream(byteArray);
            //3.生产bigmap
            Bitmap bitmap = BitmapFactory.decodeStream(byStream);
            imageView.setImageBitmap(bitmap);

        }
    }
}
