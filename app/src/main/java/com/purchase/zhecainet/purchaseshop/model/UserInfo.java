package com.purchase.zhecainet.purchaseshop.model;

/**
 * Created by leixiaoliang on 2017/7/26.
 * 邮箱：lxliang1101@163.com
 */

public class UserInfo {
    private String u_guid;
    private String user_name;
    private String nick_name;
    private String mobile;
    private String avatar;
    private String token ;
    private String version ;
    private String from ;

    private String sex ;
    private String birth ;
    private String prov ;
    private String city ;

    private String district ;
    private String summary ;
    private String pushTag;
    private String pushAlias;
    private String email;
    private boolean is_identity;
    private boolean is_setpwd;

    public String getU_guid() {
        return u_guid;
    }

    public void setU_guid(String u_guid) {
        this.u_guid = u_guid;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPushTag() {
        return pushTag;
    }

    public void setPushTag(String pushTag) {
        this.pushTag = pushTag;
    }

    public String getPushAlias() {
        return pushAlias;
    }

    public void setPushAlias(String pushAlias) {
        this.pushAlias = pushAlias;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean is_identity() {
        return is_identity;
    }

    public void setIs_identity(boolean is_identity) {
        this.is_identity = is_identity;
    }

    public boolean is_setpwd() {
        return is_setpwd;
    }

    public void setIs_setpwd(boolean is_setpwd) {
        this.is_setpwd = is_setpwd;
    }
}
