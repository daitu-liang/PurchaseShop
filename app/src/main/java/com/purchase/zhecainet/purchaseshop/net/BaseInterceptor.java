package com.purchase.zhecainet.purchaseshop.net;

import com.purchase.zhecainet.purchaseshop.utils.HeadUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by leixiaoliang on 2017/7/28.
 * 邮箱：lxliang1101@163.com
 */

public class BaseInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
//        String nonce = GenerateRandom.getNonce();
//        String timestamp = GenerateRandom.getTimestamp();
//        String signature = GenerateRandom.getSignature(nonce, timestamp);

        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("Content-Encoding", "gzip")

                .addHeader("Connection", "keep-alive")
                .addHeader("Authorization", HeadUtils.getAuthorization(""))
                .build();
        return chain.proceed(request);
    }
}
