package com.purchase.zhecainet.purchaseshop.api.menucollection;

import com.purchase.zhecainet.purchaseshop.api.NetApi;
import com.purchase.zhecainet.purchaseshop.model.BaseInfo;
import com.purchase.zhecainet.purchaseshop.model.CollectionListInfo;
import com.purchase.zhecainet.purchaseshop.net.HttpResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by leixiaoliang on 2017/7/26.
 * 邮箱：lxliang1101@163.com
 */

public interface MenuApi {
    /**
     * 采购下单user/{user_id}/collaborator/{collaborator_id}/purchase_template
     * @param authorization
     * @return
     */
    @FormUrlEncoded
    @POST(NetApi.getPurchaseTemplateAdd)
    Observable<HttpResult<BaseInfo>> getPurchaseTemplateAdd(@Header("Authorization") String authorization, @Path("user_id") String user_id, @Path("collaborator_id") String collaborator_id);

    /**
     * 收藏列表
     * @param authorization
     * @param user_id
     * @param collaborator_id
     * @return
     */
    @GET(NetApi.getPurchaseTemplateQuery)
    Observable<HttpResult<List<CollectionListInfo>>> getPurchaseTemplateQuery(@Header("Authorization") String authorization, @Path("user_id") String user_id,@Path("collaborator_id") String collaborator_id);

}
