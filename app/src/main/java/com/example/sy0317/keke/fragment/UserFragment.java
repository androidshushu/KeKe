package com.example.sy0317.keke.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sy0317.keke.R;
import com.example.sy0317.keke.entity.MyUser;
import com.example.sy0317.keke.ui.LoginActivity;
import com.example.sy0317.keke.utils.L;
import com.example.sy0317.keke.utils.ShareUtils;
import com.example.sy0317.keke.utils.UtilsTools;
import com.example.sy0317.keke.view.CustomDialog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by shushu on 2018/4/9.
 * 项目名：KeKe
 * 包名：  com.example.sy0317.keke.fragment
 * 文件名：UserFragment
 * 描述    KeKe
 */

public class UserFragment  extends Fragment implements View.OnClickListener {
    private Button btn_exit_user;
    private TextView edit_user;

    private EditText et_username;
    private EditText et_sex;
    private EditText et_age;
    private EditText et_desc;

    //更新用户信息按钮
    private Button btn_update_ok;
    //圆形头像
    private ImageView profile_image;
    //弹窗
    private CustomDialog dialog;

    //弹窗里面的三个按钮
    private Button btn_camera;
    private Button btn_picture;
    private Button btn_cancel;
    //相机拍照图片保存地址
    private Uri imageUri;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,null);
        findView(view);
        return view;
    }

    private void findView(View v) {
        btn_exit_user = v.findViewById(R.id.btn_exit_user);
        btn_exit_user.setOnClickListener(this);
        edit_user = v.findViewById(R.id.edit_user);
        edit_user.setOnClickListener(this);

        et_username = v.findViewById(R.id.et_username);
        et_sex = v.findViewById(R.id.et_sex);
        et_age = v.findViewById(R.id.et_age);
        et_desc = v.findViewById(R.id.et_desc);
        btn_update_ok = v.findViewById(R.id.btn_update_ok);
        btn_update_ok.setOnClickListener(this);
        profile_image = v.findViewById(R.id.profile_image);
        profile_image.setOnClickListener(this);
        //从缓存中拿出来
        UtilsTools.getImageToShare(getActivity(),profile_image);
        //默认是不可点击的，并且不可输入
        setEnabled(false);
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);

        //设置默认值
        et_username.setText(userInfo.getUsername());
        et_sex.setText(userInfo.isSex()?"男":"女");
        et_age.setText(userInfo.getAge()+"");
        et_desc.setText(userInfo.getDesc());


        dialog = new CustomDialog(getActivity(), WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT,R.layout.dialog_photo,R.style.Theme_dialog, Gravity.BOTTOM,R.style.pop_anim_style);
        //色或者屏幕外点击无效
        dialog.setCancelable(false);
        //按个按钮
        btn_camera = dialog.findViewById(R.id.btn_camera);
        btn_picture = dialog.findViewById(R.id.btn_picture);
        btn_cancel = dialog.findViewById(R.id.btn_cancel);
        btn_camera.setOnClickListener(this);
        btn_picture.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);


    }
    public void setEnabled(boolean is){
        et_username.setEnabled(is);
        et_sex.setEnabled(is);
        et_age.setEnabled(is);
        et_desc.setEnabled(is);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_exit_user:
                //清除缓存用户对象
                MyUser.logOut();
                // 现在的currentUser是null了
                BmobUser currentUser = MyUser.getCurrentUser();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.edit_user:
                setEnabled(true);
                btn_update_ok.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_update_ok:
                //第一先拿到对应输入框的值
                String username = et_username.getText().toString().trim();
                String sex = et_sex.getText().toString().trim();
                String age = et_age.getText().toString().trim();
                String desc = et_desc.getText().toString().trim();
                //判断是否为空
                if (!TextUtils.isEmpty(username)&!TextUtils.isEmpty(sex)&!TextUtils.isEmpty(age)){
                    //3.更新属性
                    MyUser user = new MyUser();
                    user.setUsername(username);
                    user.setAge(Integer.parseInt(age));
                    //性别
                    if (sex.equals("男")){
                        user.setSex(true);
                    }else {
                        user.setSex(false);
                    }
                    if (!TextUtils.isEmpty(desc)){
                        user.setDesc(desc);
                    }else {
                        user.setDesc("这个人很懒，什么也没有留下！");
                    }
                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null){
                                //说明修改成功
                                setEnabled(false);
                                btn_update_ok.setVisibility(View.GONE);
                                Toast.makeText(getActivity(),"修改成功",Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getActivity(),"修改失败",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }else {
                    Toast.makeText(getActivity(), R.string.inputIsNull,Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.profile_image:
                dialog.show();
                break;
            case R.id.btn_camera:
                toCamera();
                break;
            case R.id.btn_picture:
                toPicture();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
        }
    }

    public static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int RESULT_REQUEST_CODE = 102;
    private File tempFile = null;
    //跳转相机
    private void toCamera() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        //判断内存卡是否可用，可用的话就进行储存
//        intent.putExtra(MediaStore.EXTRA_OUTPUT,
//                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME)));
//        startActivityForResult(intent, CAMERA_REQUEST_CODE);
        // 创建File对象，用于存储拍照后的图片
        File outputImage = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT < 24) {
            imageUri = Uri.fromFile(outputImage);
        } else {
            //Android 7.0系统开始 使用本地真实的Uri路径不安全,使用FileProvider封装共享Uri
            //参数二:fileprovider绝对路径 com.dyb.testcamerademo：项目包名
            imageUri = FileProvider.getUriForFile(getActivity(), "com.example.sy0317.keke.fileprovider", outputImage);
        }
        // 启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);

        dialog.dismiss();
    }
    //跳转相册
    public  void toPicture(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_REQUEST_CODE);
        dialog.dismiss();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case IMAGE_REQUEST_CODE:
                startPhotoZoom(data.getData());
                break;
            case CAMERA_REQUEST_CODE:
//                tempFile = new File(Environment.getExternalStorageDirectory(),PHOTO_IMAGE_FILE_NAME);
//                startPhotoZoom(Uri.fromFile(tempFile));
                startPhotoZoom(imageUri);
                break;
            case RESULT_REQUEST_CODE:
                if (data!=null){
                    //拿到图片设置
                    setImageToView(data);
                    //既然已经设置了图片，我们原先的就应该删除
                    if (tempFile!=null){
                        tempFile.delete();
                    }
                }
                break;
        }
    }
    private void startPhotoZoom(Uri uri){
        if (uri == null){
            L.e("uri == null");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        //设置裁剪
        intent.putExtra("crop","true");
        //裁剪宽高
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        //裁剪图片的质量
        intent.putExtra("outputX",350);
        intent.putExtra("outputY",350);
        //发送数据
        intent.putExtra("return-data",true);
        startActivityForResult(intent,RESULT_REQUEST_CODE);
    }

    private void setImageToView(Intent data){
        Bundle bundle = data.getExtras();
        if (bundle!=null){
            Bitmap bitmap = bundle.getParcelable("data");
            profile_image.setImageBitmap(bitmap);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UtilsTools.putImageToShare(getActivity(),profile_image);
    }
}
