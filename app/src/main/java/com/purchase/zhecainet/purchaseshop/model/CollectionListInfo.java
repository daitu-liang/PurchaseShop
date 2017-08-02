package com.purchase.zhecainet.purchaseshop.model;

import java.io.Serializable;

/**
 * Created by leixiaoliang on 2017/7/26.
 * 邮箱：lxliang1101@163.com
 */

public class CollectionListInfo implements Serializable {
    private String id;
    private String goods_sale_id;
    private String trace_code;
    private String name;
    private String goods_id;
    private String photo;
    private String specifications;

    private String weight;
    private String price;
    private float point;
    private String last_number;

    public String getGoods_sale_id() {
        return goods_sale_id;
    }

    public void setGoods_sale_id(String goods_sale_id) {
        this.goods_sale_id = goods_sale_id;
    }

    public String getLast_number() {
        return last_number;
    }

    public void setLast_number(String last_number) {
        this.last_number = last_number;
    }

    private ComConent com_content;
    private SupplierContent supplier_content;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }

    public ComConent getCom_content() {
        return com_content;
    }

    public void setCom_content(ComConent com_content) {
        this.com_content = com_content;
    }

    public SupplierContent getSupplier_content() {
        return supplier_content;
    }

    public void setSupplier_content(SupplierContent supplier_content) {
        this.supplier_content = supplier_content;
    }



}
