package com.purchase.zhecainet.purchaseshop.net;


import com.purchase.zhecainet.purchaseshop.api.NetApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by leixiaoliang on 2017/4/11.
 * 邮箱：lxliang1101@163.com
 */

public class RetrofitClient {

//    初始化BaseUrl
    private  static String baseUrl= NetApi.BASE_URL;
    private Retrofit retrofit;

    private RetrofitClient()
    {}
    private static  class SingleHolder{
        private static RetrofitClient  INSTANCE=new RetrofitClient();
    }
    public static RetrofitClient getInstance(){
        return SingleHolder.INSTANCE;
    }
    /**
     *  修改BaseUrl地址
     * @param baseUrl
     */
    public RetrofitClient setBaseUrl(String baseUrl)
    {
        this.baseUrl=baseUrl;
        return this;
    }
    /**
     *  获得对应的ApiServcie对象
     * @param service
     * @param <T>
     * @return
     */
    public  <T> T  builder(Class<T> service)
    {
        if(baseUrl==null)
        {
            throw new RuntimeException("baseUrl is null!");
        }
        if (service == null) {
            throw new RuntimeException("api Service is null!");
        }
        retrofit=new Retrofit.Builder()
                .client(OkHttpUtils.getClient())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(service);
    }

}
