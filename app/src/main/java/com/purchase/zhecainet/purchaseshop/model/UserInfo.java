package com.purchase.zhecainet.purchaseshop.model;

import java.io.Serializable;

/**
 * Created by leixiaoliang on 2017/7/26.
 * 邮箱：lxliang1101@163.com
 */

public class UserInfo implements Serializable {
    private String user_id;
    private String token;
    private String code;
    private String name;
    private String phone;

    private String departmnetId;
    private String departmnetName;

    public String getDepartmnetId() {
        return departmnetId;
    }

    public void setDepartmnetId(String departmnetId) {
        this.departmnetId = departmnetId;
    }

    public String getDepartmnetName() {
        return departmnetName;
    }

    public void setDepartmnetName(String departmnetName) {
        this.departmnetName = departmnetName;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
