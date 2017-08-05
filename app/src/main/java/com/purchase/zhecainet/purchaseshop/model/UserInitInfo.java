package com.purchase.zhecainet.purchaseshop.model;

import java.io.Serializable;

/**
 * Created by leixiaoliang on 2017/7/28.
 * 邮箱：lxliang1101@163.com
 */

public class UserInitInfo implements Serializable {
    private String  id;
    private String  code;
    private String  name;
    private String  phone;
    private Collaboratpr collaborator;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Collaboratpr getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(Collaboratpr collaborator) {
        this.collaborator = collaborator;
    }


}
