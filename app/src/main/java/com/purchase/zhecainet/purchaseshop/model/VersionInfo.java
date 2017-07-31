package com.purchase.zhecainet.purchaseshop.model;

import java.io.Serializable;

/**
 * Created by leixiaoliang on 2017/7/28.
 * 邮箱：lxliang1101@163.com
 */

public class VersionInfo implements Serializable {
    private String attr;
    private String desc;
    private String version;
    private String size;
    private String down_url;

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDown_url() {
        return down_url;
    }

    public void setDown_url(String down_url) {
        this.down_url = down_url;
    }

    @Override
    public String toString() {
        return "VersionInfo{" +
                "attr='" + attr + '\'' +
                ", desc='" + desc + '\'' +
                ", version='" + version + '\'' +
                ", size='" + size + '\'' +
                ", down_url='" + down_url + '\'' +
                '}';
    }
}
