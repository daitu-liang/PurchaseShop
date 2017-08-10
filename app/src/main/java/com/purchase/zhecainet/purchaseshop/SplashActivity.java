package com.purchase.zhecainet.purchaseshop;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.purchase.zhecainet.purchaseshop.app.PurchaseApplication;
import com.purchase.zhecainet.purchaseshop.ui.MainHomeActivity;
import com.purchase.zhecainet.purchaseshop.ui.commom.LoginActivity;
import com.purchase.zhecainet.purchaseshop.utils.Logger;
import com.purchase.zhecainet.purchaseshop.utils.UserUtil;

import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG ="SplashActivity" ;
    private static Logger log = Logger.getLogger("SplashActivity");
    private static final int MSG_ENTER_HOME = 1;
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            if(msg.what == MSG_ENTER_HOME) {
                boolean isFirst = PurchaseApplication.getPreferenceManager().readBooleanIsFisrt(getApplicationContext(), true);
                Log.i(TAG,"isFirst="+isFirst);
                if (isFirst == true) {
                    PurchaseApplication.getPreferenceManager().writeFistUsed(getApplicationContext(), false);
                    comeWelcome();
                }else {
                    if(UserUtil.hasLogin()){
                        startActivity(MainHomeActivity.getIntent(SplashActivity.this));
                        finish();
                    }else {
//                        startActivity(LoginActivity.getIntent(SplashActivity.this));
                        startActivity(MainHomeActivity.getIntent(SplashActivity.this));
                        finish();
                    }
                }
                finish();
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        mHandler.sendEmptyMessageDelayed(MSG_ENTER_HOME, 1 * 1000);
    }
    /**
     * 进入引导界面
     */
    private void comeWelcome() {
        startActivity(LoginActivity.getIntent(SplashActivity.this));
//        startActivity(GuideActivity.getIntent(this));
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mHandler!=null){
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}
