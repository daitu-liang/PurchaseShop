package com.purchase.zhecainet.purchaseshop.model;

import java.io.Serializable;
import java.util.List;

/**
 * 采购单查询
 * Created by leixiaoliang on 2017/7/28.
 * 邮箱：lxliang1101@163.com
 */

public class PurchaseOrderInfo implements Serializable {
    private  String id;
    private  String trace_code;
    private  String purchase_date;
    private  String type;
    private  String status;
    private  String total_cost;

    private  String total_goods;
    private  String total_weight;
    private  String delivery_point;

    private List<GoodsItem> goods;
    private  SupplierContent supplier_content;
    private Delivery delivery;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrace_code() {
        return trace_code;
    }

    public void setTrace_code(String trace_code) {
        this.trace_code = trace_code;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(String total_cost) {
        this.total_cost = total_cost;
    }

    public String getTotal_goods() {
        return total_goods;
    }

    public void setTotal_goods(String total_goods) {
        this.total_goods = total_goods;
    }

    public String getTotal_weight() {
        return total_weight;
    }

    public void setTotal_weight(String total_weight) {
        this.total_weight = total_weight;
    }

    public String getDelivery_point() {
        return delivery_point;
    }

    public void setDelivery_point(String delivery_point) {
        this.delivery_point = delivery_point;
    }

    public List<GoodsItem> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsItem> goods) {
        this.goods = goods;
    }

    public SupplierContent getSupplier_content() {
        return supplier_content;
    }

    public void setSupplier_content(SupplierContent supplier_content) {
        this.supplier_content = supplier_content;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
}
