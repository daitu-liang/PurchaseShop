package com.purchase.zhecainet.purchaseshop.model;

import java.io.Serializable;

/**
 * Created by leixiaoliang on 2017/8/2.
 * 邮箱：lxliang1101@163.com
 */

public class Collaborator implements Serializable {
    private String collaborator_id;
    private String name;

    public String getCollaborator_id() {
        return collaborator_id;
    }

    public void setCollaborator_id(String collaborator_id) {
        this.collaborator_id = collaborator_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
