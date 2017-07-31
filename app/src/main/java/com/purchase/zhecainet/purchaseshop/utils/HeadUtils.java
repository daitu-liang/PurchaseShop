package com.purchase.zhecainet.purchaseshop.utils;

import java.util.Date;

/**
 * Created by leixiaoliang on 2017/7/28.
 * 邮箱：lxliang1101@163.com
 */

public class HeadUtils {
    private static final String TAG ="HeadUtils" ;
    private static Logger log = Logger.getLogger(TAG);

    private static String appId = "1001";
    private static String appSecret = "ww";
    private static String token = "";

    public  static String getAuthorization(String paramsStr) {
        String newParamsStr=changeStrParams(paramsStr);
        Date date = new Date();
        String timestamp = getSecondTimestampTwo(date);
        String verify = getVerify(newParamsStr, timestamp);
        String str = appId + ":" + timestamp + ":" + verify + ":" +token + ":" +"3";
        log.e(TAG,"str="+str);
        return Base64.getBase64(str);
    }
    public static String changeStrParams(String paramsStr){
        String newStr = paramsStr.trim();
        newStr = newStr.replace("{", "");
        newStr = newStr.replace("}", "");
        newStr = newStr.replace(" ", "");
        newStr = newStr.replace(",", "&");
        return newStr;
    }

    /**
     * 获取精确到秒的时间戳
     *
     * @param date
     * @return
     */
    public static String getSecondTimestampTwo(Date date) {
        if (null == date) {
            return "";
        }
        String timestamp = String.valueOf(date.getTime() / 1000);
        return timestamp;
    }

    /**
     * verify⽣成
     * @param newParamsStr
     * @return
     */
    public static String getVerify(String newParamsStr, String timestamp) {
        log.e(TAG,"newParamsStr="+newParamsStr);
        log.e(TAG,"timestamp="+timestamp);
        String needMD5Str = MD5Utils.MD5(MD5Utils.MD5(newParamsStr) + "|" + timestamp + "|" + 3 + "|" + appSecret);
        return needMD5Str ;
    }

}
