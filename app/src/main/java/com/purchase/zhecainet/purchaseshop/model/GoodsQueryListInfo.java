package com.purchase.zhecainet.purchaseshop.model;

import java.io.Serializable;

/**
 * Created by leixiaoliang on 2017/7/28.
 * 邮箱：lxliang1101@163.com
 */

public class GoodsQueryListInfo implements Serializable {
    private  String id;
    private  String name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
