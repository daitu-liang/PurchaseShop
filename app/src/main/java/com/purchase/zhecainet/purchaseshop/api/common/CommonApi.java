package com.purchase.zhecainet.purchaseshop.api.common;

import com.purchase.zhecainet.purchaseshop.api.NetApi;
import com.purchase.zhecainet.purchaseshop.model.BaseInfo;
import com.purchase.zhecainet.purchaseshop.model.LoginUserInfo;
import com.purchase.zhecainet.purchaseshop.model.PushInfo;
import com.purchase.zhecainet.purchaseshop.model.SmsInfo;
import com.purchase.zhecainet.purchaseshop.model.UserInitInfo;
import com.purchase.zhecainet.purchaseshop.model.VersionInfo;
import com.purchase.zhecainet.purchaseshop.net.HttpResult;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
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

public interface CommonApi {

    /**
     * 软件升级检查
     *
     * @param params
     * @return
     */
    @GET(NetApi.getBasicVersion)
    Observable<HttpResult<VersionInfo>> getVersionInfo(@Header("Authorization") String authorization,@QueryMap Map<String, String> params);

    /**
     * 推送注册
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(NetApi.getBasicPush)
    Observable<HttpResult<PushInfo>> getBasicPush(@FieldMap() Map<String, String> map);

    /**
     * 短信发送
     * @param params
     * @return
     */
    @GET(NetApi.getBasicSms)
    Observable<HttpResult<SmsInfo>> getBasicSms(@Header("Authorization") String authorization, @QueryMap Map<String, String> params);

    /**
     * ⽤户登录
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(NetApi.getUserLogin)
    Observable<HttpResult<LoginUserInfo>> getUserLogin(@FieldMap() Map<String, String> map);

    /**
     * ⽤户信息初始化
     * @param user_id
     * @return
     */
    @GET(NetApi.getUserInfoInit+"{user_id}+/init")
    Observable<HttpResult<UserInitInfo>> getUserInfoInit(@Path("user_id") String user_id);

    /**
     * 退出登录
     * @return
     */
    @FormUrlEncoded
    @POST(NetApi.getUserLogout)
    Observable<HttpResult<BaseInfo>> getUserLogout();

    @FormUrlEncoded
    @POST(NetApi.getUpdateUserPassword)
    Observable<HttpResult<BaseInfo>> getUpdateUserPassword(@FieldMap() Map<String, String> map);
}
