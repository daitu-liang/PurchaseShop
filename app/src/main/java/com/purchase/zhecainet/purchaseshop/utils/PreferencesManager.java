package com.purchase.zhecainet.purchaseshop.utils;

import android.content.Context;


public class PreferencesManager extends BasePreferencesManager {

    private static PreferencesManager instance;
    private static final String NUNIX_VALUE = "Nunix";//保存时间戳

    private static final String BAIDU_TOKEN = "baidToken";//百度token
    private static final String IS_FIRST = "isFirst";//是否是第一次下载使用
    public String getSaveNunix() {
        return saveNunix;
    }

    private String saveNunix;

    public String getSaveBaiduToken() {
        return saveBaiduToken;
    }


    private String saveBaiduToken;

    public static PreferencesManager getInstance(Context context) {
        if (null == instance) instance = new PreferencesManager(context.getApplicationContext());
        return instance;
    }

    private PreferencesManager(Context context) {
        super(context);
        loadData();
    }

    @Override
    public void logout() {

    }

    private void loadData() {
        try {
            saveNunix=getString(NUNIX_VALUE, "");
            saveBaiduToken=getString(BAIDU_TOKEN,"");

        } catch (Exception e){}
    }

    /**
     * 保存时间戳
     * @param nunix
     */
    public void setSaveNunix(String nunix) {
        this.saveNunix = nunix;
        saveString(NUNIX_VALUE, nunix);
        Logger.getLogger("").e("保存时间戳","nunix--Times="+nunix);
    }

    public void setBaiduToken(String baiduToken) {
        this.saveBaiduToken = baiduToken;
        saveString(BAIDU_TOKEN, baiduToken);

    }

    public  void writeFistUsed(Context context, boolean v) {
        saveBoolean(IS_FIRST, v);
    }
    public  boolean readBooleanIsFisrt(Context context, boolean defV) {
        return getInstance(context).getBoolean(IS_FIRST,defV);
    }
}
