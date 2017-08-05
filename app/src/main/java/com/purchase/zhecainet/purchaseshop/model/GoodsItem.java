package com.purchase.zhecainet.purchaseshop.model;

import java.io.Serializable;

/**
 * Created by leixiaoliang on 2017/8/2.
 * 邮箱：lxliang1101@163.com
 */

public class GoodsItem implements Serializable {
    private  String id;
    private  String goods_sale_id;
    private  String total_number;
    private  String total_weight;
    private  String name;
    private  String trace_code;
    private  String specifications;
    private  String price;
    private  String total_cost;
    private  String lack_number;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoods_sale_id() {
        return goods_sale_id;
    }

    public void setGoods_sale_id(String goods_sale_id) {
        this.goods_sale_id = goods_sale_id;
    }

    public String getTotal_number() {
        return total_number;
    }

    public void setTotal_number(String total_number) {
        this.total_number = total_number;
    }

    public String getTotal_weight() {
        return total_weight;
    }

    public void setTotal_weight(String total_weight) {
        this.total_weight = total_weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrace_code() {
        return trace_code;
    }

    public void setTrace_code(String trace_code) {
        this.trace_code = trace_code;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(String total_cost) {
        this.total_cost = total_cost;
    }

    public String getLack_number() {
        return lack_number;
    }

    public void setLack_number(String lack_number) {
        this.lack_number = lack_number;
    }


}
