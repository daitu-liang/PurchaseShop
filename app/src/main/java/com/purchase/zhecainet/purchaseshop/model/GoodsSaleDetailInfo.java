package com.purchase.zhecainet.purchaseshop.model;

import java.io.Serializable;

/**
 * 在售商品详情查询
 * Created by leixiaoliang on 2017/7/28.
 * 邮箱：lxliang1101@163.com
 */

public class GoodsSaleDetailInfo implements Serializable {
    private  String id;
    private  String trace_code;
    private  String name;
    private  String goods_id;
    private  String photo;
    private  String specifications;

    private  String weight;
    private  String price;
    private  String point;



    private  ComConent com_content;
    private  SupplierContent supplier_content;
    private Attachm attachm;
    class ComConent{
        private  String brief;
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }
    }

    class  SupplierContent{
        private  String supplier_id;
        private  String supplier_name;//供应商名称
        private  String producer;//商品来源地
        private  String tags;//多个标签用逗号“，”分隔
        private  String advantage;
        private  String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

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
    class Attachm{
        private  String id;
        private String type;
        private String url;
        private String module_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getModule_id() {
            return module_id;
        }

        public void setModule_id(String module_id) {
            this.module_id = module_id;
        }

        public String getObject_id() {
            return object_id;
        }

        public void setObject_id(String object_id) {
            this.object_id = object_id;
        }

        private String object_id;
    }

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

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
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

    public Attachm getAttachm() {
        return attachm;
    }

    public void setAttachm(Attachm attachm) {
        this.attachm = attachm;
    }
}
