package com.purchase.zhecainet.purchaseshop.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.purchase.zhecainet.purchaseshop.R;
import com.purchase.zhecainet.purchaseshop.utils.CommonUtils;
import com.purchase.zhecainet.purchaseshop.utils.NetWorkUtil;
import com.purchase.zhecainet.purchaseshop.utils.ScreenManager;


/**
 * Created by leixiaoliang on 2017/4/12.
 * 邮箱：lxliang1101@163.com
 */

public class BaseActivity extends AppCompatActivity {
    protected boolean isDestroy;
    private Toast mToast;


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ScreenManager.getScreenManager().pushActivity(this, true);
        if (!NetWorkUtil.isNetworkConnected(getApplicationContext())) {
            Toast toast = Toast.makeText(getApplicationContext(), "亲，没网哦，请检查您的网络状态！", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * 显示toast
     */
    public void showToast(String msg) {
        if (isDestroy)
            return;
        // 不用getApplicationContext的话,导致内存泄露
        if (mToast == null) {
            mToast = Toast.makeText(this.getApplicationContext(), msg,
                    Toast.LENGTH_SHORT);
            mToast.show();
        } else {
            mToast.setText(msg);
            mToast.show();

        }
    }

    /**
     * 显示toast
     */
    public void showToast(int msg) {
        showToast(getString(msg));
    }


    protected void setActionBarTitle(String title) {
        TextView mTitleView = (TextView) findViewById(R.id.title_bar_title);
        if (mTitleView != null) {
            mTitleView.setText(title);
        }
    }

    protected void initActionBackBtn() {
        View back = findViewById(R.id.title_bar_back);
        if (back != null) {
            back.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (CommonUtils.isFastDoubleClick()) {
                        return;
                    }
                    finish();
                    executeFinishActivityAnim();
                }
            });
        }
    }

    protected void initActionBarOperate() {
        ImageView view = (ImageView) findViewById(R.id.title_bar_operate);
        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (CommonUtils.isFastDoubleClick()) {
                        return;
                    }
                    showToast("more");

                }

            });
        }
    }

    protected void setVisableActionBarOperate(boolean isVisable) {
        ImageView view = (ImageView) findViewById(R.id.title_bar_operate);
        if (view != null) {
            if (isVisable) {
                view.setVisibility(View.VISIBLE);
                initActionBarOperate();
            } else {
                view.setVisibility(View.GONE);
            }
        }
    }

    protected void setVisableActionBackBtn(boolean isVisable) {
        View back = findViewById(R.id.title_bar_back);
        if (back != null) {
            if (isVisable) {
                back.setVisibility(View.VISIBLE);
                initActionBackBtn();
            } else {
                back.setVisibility(View.GONE);
            }
        }
    }

    public void startActivity(Intent intent, boolean isDefaultAnim) {
        super.startActivity(intent);
        if (isDefaultAnim) {
            executeStartActivityAnim();
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        executeStartActivityAnim();
    }

    public void startNoAnimActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        try {
            super.startActivityForResult(intent, requestCode);
            executeStartActivityAnim();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startNoAnimActivityForResult(Intent intent, int requestCode) {
        try {
            super.startActivityForResult(intent, requestCode);
            overridePendingTransition(0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finish() {
        super.finish();
        executeFinishActivityAnim();
    }

    public void finish(boolean isDefaultAnim) {
        super.finish();
        if (isDefaultAnim) {
            executeFinishActivityAnim();
        }
    }

    protected void executeStartActivityAnim() {
        overridePendingTransition(getEnterAnim(), 0);
    }

    protected void executeFinishActivityAnim() {
        overridePendingTransition(0, getExitAnim());
    }

    /**
     * 进入动画
     */
    protected int getEnterAnim() {
        return R.anim.activity_push_right_in;
    }

    /**
     * 退出动画
     */
    protected int getExitAnim() {
        return R.anim.activity_push_right_out;
    }

    protected int getTopInAnim() {
        return R.anim.translate_top_in;
    }

    protected int getTopOutAnim() {
        return R.anim.translate_top_out;
    }

    @Override
    protected void onStop() {
        super.onStop();
        ScreenManager.getScreenManager().changeActivityStates(this, false);
    }

    @Override
    protected void onDestroy() {
        isDestroy = true;
        super.onDestroy();
        ScreenManager.getScreenManager().popActivity(this);
    }

}
