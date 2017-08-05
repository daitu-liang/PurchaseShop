package com.purchase.zhecainet.purchaseshop.api.purchaseplan;

import com.purchase.zhecainet.purchaseshop.api.NetApi;
import com.purchase.zhecainet.purchaseshop.model.BaseInfo;
import com.purchase.zhecainet.purchaseshop.model.PurchaseOrderInfo;
import com.purchase.zhecainet.purchaseshop.net.HttpResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by leixiaoliang on 2017/7/26.
 * 邮箱：lxliang1101@163.com
 */

public interface PurchasePlanApi {
    /**
     *
     * @param authorization
     * @param user_id
     * @param collaborator_id
     * @return
     */
    @GET(NetApi.getPurchaseOrderQuery)
    Observable<HttpResult<PurchaseOrderInfo>> getPurchaseOrderQuery(@Header("Authorization") String authorization,
                                                                    @Path("user_id") String user_id,
                                                                    @Path("collaborator_id") String collaborator_id);

    /**
     * 采购单商品修改
     * @param authorization
     * @param user_id
     * @param collaborator_id
     * @param purchase_order_id
     * @return
     */
    @PUT(NetApi.getPurchaseOrderModify)
    Observable<HttpResult<PurchaseOrderInfo>> getPurchaseOrderModify(@Header("Authorization") String authorization,
                                                                     @Path("user_id") String user_id,
                                                                     @Path("collaborator_id") String collaborator_id,
                                                                     @Path("purchase_order_id") String purchase_order_id);

    /**
     * 采购单商品缺货登记
     * @param authorization
     * @param user_id
     * @param collaborator_id
     * @param purchase_order_id
     * @param purchase_goods_id
     * @return
     */
    @GET(NetApi.getPurchaseOrderLackModify)
    Observable<HttpResult<BaseInfo>> getPurchaseOrderLackModify(@Header("Authorization") String authorization,
                                                                @Path("user_id") String user_id,
                                                                @Path("collaborator_id") String collaborator_id,
                                                                @Path("purchase_order_id") String purchase_order_id,
                                                                @Path("purchase_goods_id") String purchase_goods_id);


}
