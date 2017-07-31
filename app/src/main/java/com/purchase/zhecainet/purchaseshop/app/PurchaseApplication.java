package com.purchase.zhecainet.purchaseshop.app;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.purchase.zhecainet.purchaseshop.utils.PreferencesManager;

/**
 * Created by leixiaoliang on 2017/7/26.
 * 邮箱：lxliang1101@163.com
 */

public class PurchaseApplication extends Application {
    public static Context CONTEXT;
    public static PreferencesManager preferenceManager;
    @Override
    public void onCreate() {
        super.onCreate();
        setContext(this);
        init();
    }
    private void init() {
        preferenceManager = PreferencesManager.getInstance(this);
        Fresco.initialize(this);
    }
    private static void setContext(Context mContext) {
        CONTEXT = mContext;
    }

    public static Context getContext() {
        return CONTEXT;
    }
    public static PreferencesManager getPreferenceManager() {
        if(preferenceManager==null){
            preferenceManager = PreferencesManager.getInstance(getContext());
            return preferenceManager;
        }
        return preferenceManager;
    }
}
