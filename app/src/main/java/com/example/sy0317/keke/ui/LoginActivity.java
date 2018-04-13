package com.example.sy0317.keke.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telecom.GatewayInfo;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sy0317.keke.MainActivity;
import com.example.sy0317.keke.R;
import com.example.sy0317.keke.entity.MyUser;
import com.example.sy0317.keke.utils.L;
import com.example.sy0317.keke.utils.ShareUtils;
import com.example.sy0317.keke.view.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by shushu on 2018/4/10.
 * 项目名：KeKe
 * 包名：  com.example.sy0317.keke.ui
 * 文件名：LoginActivity
 * 描述    KeKe
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_register;
    private EditText et_name;
    private EditText et_password;
    private Button btn_login;
    private CheckBox keep_password;

    private TextView tv_forget;
    private CustomDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        btn_register = findViewById(R.id.btn_register);
        et_name = findViewById(R.id.account);
        et_password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        keep_password = findViewById(R.id.keepPassword);
        tv_forget = findViewById(R.id.tv_forget);
        tv_forget.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        L.d("loginActivity initView");

        dialog = new CustomDialog(this,300,300,R.layout.dialog_loding,R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);

        //色或者屏幕外点击无效
        dialog.setCancelable(false);
        //设置选中的状态
        boolean isCheck = ShareUtils.getBoolean(this,"keeppass",false);
        keep_password.setChecked(isCheck);
        if (isCheck){
            et_name.setText(ShareUtils.getString(this,"account",""));
            et_password.setText(ShareUtils.getString(this,"password",""));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_forget:
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                break;
            case R.id.btn_register:
                startActivity(new Intent(this,RegisteredActivity.class));
                break;
            case R.id.btn_login:
                L.d("loginActivity btn_login onClick");
                //输入框信息
                String  name = et_name.getText().toString().trim();
                String  password = et_password.getText().toString().trim();
                L.d("loginActivity btn_login onClick name = "+name+"    password  ="+password);
                //判断是否为空
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(password)) {
                    dialog.show();
                    L.d("loginActivity btn_login not null");
                    final MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<Object>() {
                        @Override
                        public void done(Object o, BmobException e) {
                            dialog.dismiss();
                            if (e== null){
                                //判断邮箱是否验证
                                if (user.getEmailVerified()){
                                    //跳转
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }else {
                                    Toast.makeText(LoginActivity.this,"请前往邮箱验证",Toast.LENGTH_LONG).show();
                                    L.d("请前往邮箱验证");
                                }

                            }else {
                                Toast.makeText(LoginActivity.this,"登录失败 e："+e,Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                    L.d("loginActivity btn_login 输入框不能为空");
                }
                break;
        }
    }
    //不应该在onDestroy里面保存用户密码

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //保存状态
        ShareUtils.putBoolean(this,"keeppass",keep_password.isChecked());
        //是否记住密码
        if (keep_password.isChecked()){
            //记住用户名跟密码
            ShareUtils.putString(this,"account",et_name.getText().toString().trim());
            ShareUtils.putString(this,"password",et_password.getText().toString().trim());
        }else {
            ShareUtils.deleShare(this,"account");
            ShareUtils.deleShare(this,"password");
        }


    }
}
