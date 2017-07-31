package com.purchase.zhecainet.purchaseshop.model;

import java.io.Serializable;

/**
 * Created by leixiaoliang on 2017/7/28.
 * 邮箱：lxliang1101@163.com
 */

public class SmsInfo implements Serializable {
    private  String tran_id;//⽤用于后续步骤的校验

    public String getTran_id() {
        return tran_id;
    }

    public void setTran_id(String tran_id) {
        this.tran_id = tran_id;
    }
}
