package com.purchase.zhecainet.purchaseshop.api.pickgoods;

import com.purchase.zhecainet.purchaseshop.api.NetApi;
import com.purchase.zhecainet.purchaseshop.model.GoodsCategory;
import com.purchase.zhecainet.purchaseshop.model.GoodsQuerySearchListInfo;
import com.purchase.zhecainet.purchaseshop.model.GoodsSaleDetailInfo;
import com.purchase.zhecainet.purchaseshop.model.GoodsSaleListInfo;
import com.purchase.zhecainet.purchaseshop.model.PurchaseOrderInfo;
import com.purchase.zhecainet.purchaseshop.model.RecommendListInfo;
import com.purchase.zhecainet.purchaseshop.net.HttpResult;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by leixiaoliang on 2017/7/26.
 * 邮箱：lxliang1101@163.com
 */

public interface PickGoodsApi {
    /**
     * 商品分类
     * @param authorization
     * @return
     */
    @GET(NetApi.getGoodsCategoryQuery)
    Observable<HttpResult<List<GoodsCategory>>> getGoodsCategoryQuery(@Header("Authorization") String authorization);

    /**
     * 商品搜索
     * @param authorization
     * @param user_id
     * @return
     */

    @GET(NetApi.getGoodsListSearchQuery)
    Observable<HttpResult<List<GoodsQuerySearchListInfo>>> getGoodsListSearchQuery(@Header("Authorization") String authorization, @Path("user_id") String user_id,@QueryMap Map<String, String> params);

    /**
     * 商品列表查询
     * @param authorization
     * @param user_id
     * @return
     */
    @GET(NetApi.getGoodsSaleListQuery)
    Observable<HttpResult<List<GoodsSaleListInfo>>> getGoodsSaleListQuery(@Header("Authorization") String authorization, @Path("user_id") String user_id, @QueryMap Map<String, String> params);

    /**
     * 采购下单
     * @param authorization
     * @param user_id
     * @param collaborator_id
     * @return
     */
    @FormUrlEncoded
    @POST(NetApi.getPurchaseOrderaAdd)
    Observable<HttpResult<PurchaseOrderInfo>> getPurchaseOrderaAdd(@Header("Authorization") String authorization, @Path("user_id") String user_id, @Path("collaborator_id") String collaborator_id);

    /**
     * 商品详情
     * @param authorization
     * @param user_id
     * @param goods_sale_id
     * @return
     */
    @GET(NetApi.getGoodsSaleDetailQuery)
    Observable<HttpResult<GoodsSaleDetailInfo>> getGoodsSaleDetailQuery(@Header("Authorization") String authorization, @Path("user_id") String user_id, @Path("goods_sale_id") String goods_sale_id);

    /**
     * 推荐位查询
     * @param authorization
     * @param params
     * @return
     */
    @GET(NetApi.getRecommendListQuery)
    Observable<HttpResult<List<RecommendListInfo>>> getRecommendListQuery(@Header("Authorization") String authorization, @QueryMap Map<String, String> params);


}
