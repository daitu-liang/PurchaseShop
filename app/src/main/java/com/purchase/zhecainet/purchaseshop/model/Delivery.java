package com.purchase.zhecainet.purchaseshop.model;

import java.io.Serializable;

/**
 * Created by leixiaoliang on 2017/8/2.
 * 邮箱：lxliang1101@163.com
 */

public class Delivery implements Serializable {
    private  String id;
    private  String group_id;
    private  String leader_name;
    private  String leader_phone;
    private  String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getLeader_name() {
        return leader_name;
    }

    public void setLeader_name(String leader_name) {
        this.leader_name = leader_name;
    }

    public String getLeader_phone() {
        return leader_phone;
    }

    public void setLeader_phone(String leader_phone) {
        this.leader_phone = leader_phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
