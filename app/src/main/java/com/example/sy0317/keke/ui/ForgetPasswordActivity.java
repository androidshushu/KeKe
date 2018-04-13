package com.example.sy0317.keke.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sy0317.keke.R;
import com.example.sy0317.keke.entity.MyUser;
import com.example.sy0317.keke.utils.L;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by shushu on 2018/4/11.
 * 项目名：KeKe
 * 包名：  com.example.sy0317.keke.ui
 * 文件名：ForgetPasswordActivity
 * 描述    KeKe
 */

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_forget_passwoore;
    private EditText et_email;

    private EditText et_now;
    private EditText et_new;
    private EditText et_new_password;
    private Button btn_update_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        
        initView();
    }

    private void initView() {
        btn_forget_passwoore = findViewById(R.id.btn_forget_password);
        et_email = findViewById(R.id.et_email);
        btn_forget_passwoore.setOnClickListener(this);

        et_now = findViewById(R.id.et_now);
        et_new = findViewById(R.id.et_new);
        et_new_password =  findViewById(R.id.et_new_password);
        btn_update_password = findViewById(R.id.btn_update_password);
        btn_update_password.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_forget_password:
                final String email = et_email.getText().toString().trim();
                //判断邮箱是否为空
                if (!email.equals("")){
                    //发送邮件
                    MyUser.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                Toast.makeText(ForgetPasswordActivity.this,"邮件已经成功发送至："+email,Toast.LENGTH_LONG).show();
                                finish();
                            }else {
                                Toast.makeText(ForgetPasswordActivity.this,"邮件发送失败",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(this,"输入邮箱为空",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_update_password:
                //1.获取输入框的值
                String now  = et_now.getText().toString().trim();
                String news = et_new.getText().toString().trim();
                String new_password = et_new_password.getText().toString().trim();
                //判断是否为空
                if (!TextUtils.isEmpty(now) & !TextUtils.isEmpty(news) & !TextUtils.isEmpty(new_password)){
                    //判断两次密码是否一致
                    if (news.equals(new_password)){
                        MyUser.updateCurrentUserPassword(now, news, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e==null){
                                    Toast.makeText(ForgetPasswordActivity.this,"重置密码成功",Toast.LENGTH_LONG).show();
                                    finish();
                                }else {
                                    Toast.makeText(ForgetPasswordActivity.this,"重置密码失败 e"+e ,Toast.LENGTH_LONG).show();
                                    L.d("e="+e);
                                }
                            }
                        });

                    }else {
                        Toast.makeText(this,"两次输入密码不一致",Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(this,"输入框为空",Toast.LENGTH_LONG).show();
                }
                break;
        }

    }
}
