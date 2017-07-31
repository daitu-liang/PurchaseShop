package com.purchase.zhecainet.purchaseshop.net;

/**
 * Created by leixiaoliang on 2017/1/5.
 */
public class ApiException extends RuntimeException  {
    public static final int USER_NOT_EXIST = 100;
    public static final int AUTHORISE_NO_USERDW_CODE = 403;
    public static final int AUTHORISE_FAILCODE = 401;//授权失败
    public static final int WRONG_NO_RESOURCE_CODE = 404;

    public ApiException(int resultCode, String errMsg) {
        this(getApiExceptionMessage(resultCode,errMsg));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     * @param code
     * @param errMsg
     * @return
     */
    private static String getApiExceptionMessage(int code, String errMsg){
        String message = "";
        switch (code) {
            case USER_NOT_EXIST:
                message = "该用户不存在";
                break;
            case AUTHORISE_NO_USERDW_CODE:
                message = "无访问权限";
                break;
            case AUTHORISE_FAILCODE:
                message = errMsg;
                break;
            case WRONG_NO_RESOURCE_CODE:
                message = "访问资源不存在";
                break;


            default:
                message = errMsg;

        }
        return message;
    }
}
