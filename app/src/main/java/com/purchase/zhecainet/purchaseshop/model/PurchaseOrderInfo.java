package com.purchase.zhecainet.purchaseshop.model;

import java.io.Serializable;

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

    private  Detail detail;
    private  SupplierContent com_content;
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

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public SupplierContent getCom_content() {
        return com_content;
    }

    public void setCom_content(SupplierContent com_content) {
        this.com_content = com_content;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    class Detail{
        private  String id;
        private  String goods_sale_id;
        private  String total_number;
        private  String total_weight;
        private  String name;
        private  String trace_code;
        private  String specifications;
        private  String price;
        private  String total_cost;

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
    }
    class  SupplierContent{
        private  String supplier_id;
        private  String supplier_name;
        private  String producer;
        private  String tags;//多个标签用逗号“，”分隔
        private  String advantage;

        public String getSupplier_id() {
            return supplier_id;
        }

        public void setSupplier_id(String supplier_id) {
            this.supplier_id = supplier_id;
        }

        public String getSupplier_name() {
            return supplier_name;
        }

        public void setSupplier_name(String supplier_name) {
            this.supplier_name = supplier_name;
        }

        public String getProducer() {
            return producer;
        }

        public void setProducer(String producer) {
            this.producer = producer;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getAdvantage() {
            return advantage;
        }

        public void setAdvantage(String advantage) {
            this.advantage = advantage;
        }
    }
    class Delivery{
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
}
