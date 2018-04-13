package com.example.sy0317.keke.entity;

import cn.bmob.v3.BmobUser;

/**
 * Created by shushu on 2018/4/11.
 * 项目名：KeKe
 * 包名：  com.example.sy0317.keke.entity
 * 文件名：MyUser
 * 描述    KeKe
 */

public class MyUser extends BmobUser{
    private int age;
    private boolean sex;
    private String desc;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
