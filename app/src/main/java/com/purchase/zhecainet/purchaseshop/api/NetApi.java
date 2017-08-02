package com.purchase.zhecainet.purchaseshop.api;

/**
 *
 * Created by leixiaoliang on 2017/7/28.
 * 邮箱：lxliang1101@163.com
 * 接口
 */

public class NetApi {
    //表⽰接⼜地址的域名或ip地址和端口

    //正式环境：API_END_POINT：http://api.tqtworld.com
    //测试环境：http://testapi.tqtworld.com
    //    Base地址
    public static final String BASE_URL = "http://api.tqtworld.com/1.0/";


    /**
     * **************基础 接口**************************
     */
    //软件升级检查
    public static final String getBasicVersion="basic/version";
    //推送注册接口
    public static final String getBasicPush="basic/push";
    //短信发送接口
    public static final String getBasicSms="basic/sms";
    // ⽤户登录接口
    public static final String getUserLogin="user/login";
    // ⽤户信息初始化
    public static final String getUserInfoInit="user/{user_id}/init";
    // ⽤户注销
    public static final String getUserLogout="user/logout";
    // 找回密码/修改密码
    public static final String getUpdateUserPassword="user/password";



    /**
     * **************优品选购 接口**************************
     */
    //查询商品分类信息
    public static final String getGoodsCategoryQuery="goods/category";
    //商品库备选商品快速查询
    public static final String getGoodsListQuery="user/{user_id}/goods";
    //在售商品列表查询
    public static final String getGoodsSaleListQuery="user/{user_id}/goods_sale";
    //在售商品详情查询
    public static final String getGoodsSaleDetailQuery="user/{user_id}/goods_sale/{goods_sale_id}";


    /**
     * **************采购计划 接口**************************
     */
    //采购单查询
    public static final String getPurchaseOrderQuery="user/{user_id}/Collaborator/{collaborator_id}/purchase_order";
    //⽤户采购单下单时调⽤
    public static final String getPurchaseOrderaAdd="user/{user_id}/collaborator/{collaborator_id}/purchase_order";
    //采购单修改
    public static final String getPurchaseOrderModify="user/{user_id}/Collaborator/{collaborator_id}/purchase_order/\n" +
            "{purchase_order_id}";
    // ⽤户采购单确认收货时
    public static final String getPurchaseOrderConfirmReceipt="user/{user_id}/Collaborator/{collaborator_id}/purchase_order/{purchase_order_id}";
    //添加采购收藏
    public static final String getPurchaseTemplateAdd="user/{user_id}/collaborator/{collaborator_id}/purchase_template";
    //采购收藏列表查询
    public static final String getPurchaseTemplateQuery="user/{user_id}/Collaborator/{collaborator_id}/purchase_template";



    /**
     * **************菜单收藏接口**************************
     */
    //推荐位查询
    public static final String getRecommendListQuery="recommend";

}
