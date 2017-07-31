package com.purchase.zhecainet.purchaseshop.net;

/**
 * Created by leixiaoliang on 2017/1/5.
 */
public class HttpResult<T>   {
    private int errorCode;
    private  String errorMsg ;
    private T body;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
