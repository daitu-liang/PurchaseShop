package com.purchase.zhecainet.purchaseshop.model;

import java.io.Serializable;

/**
 * Created by leixiaoliang on 2017/7/26.
 * 邮箱：lxliang1101@163.com
 */

public class LoginResponseBean implements Serializable {
    private String u_guid;

    public String getU_guid() {
        return u_guid;
    }

    public void setU_guid(String u_guid) {
        this.u_guid = u_guid;
    }
}
