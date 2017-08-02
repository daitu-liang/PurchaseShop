package com.purchase.zhecainet.purchaseshop.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.TextView;

import com.purchase.zhecainet.purchaseshop.R;
import com.purchase.zhecainet.purchaseshop.base.BaseActivity;
import com.purchase.zhecainet.purchaseshop.ui.menucollection.MenuCollectionFragment;
import com.purchase.zhecainet.purchaseshop.ui.pickgoods.PickGoodsFragment;
import com.purchase.zhecainet.purchaseshop.ui.purchaseplan.PurchasePlanFragment;

public class MainHomeActivity extends BaseActivity {
    private PurchasePlanFragment mPurchasePlanFragment;
    private PickGoodsFragment mPickGoodsFragment;
    private MenuCollectionFragment mMenuCollectionFragment;
    private Fragment currentFragment;
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, MainHomeActivity.class);
        return intent;
    }
    private TextView mTitleTv;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment switchTo = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTitleTv.setText(R.string.title_home);
                    if (mPurchasePlanFragment == null) {
                        mPurchasePlanFragment = new PurchasePlanFragment();
                    }
                    switchTo = mPurchasePlanFragment;
                    break;
                case R.id.navigation_dashboard:
                    mTitleTv.setText(R.string.title_dashboard);
                    if (mPickGoodsFragment == null) {
                        mPickGoodsFragment = new PickGoodsFragment();
                    }
                    switchTo = mPickGoodsFragment;
                    break;
                case R.id.navigation_notifications:
                    mTitleTv.setText(R.string.title_notifications);
                    if (mMenuCollectionFragment == null) {
                        mMenuCollectionFragment = new MenuCollectionFragment();
                    }
                    switchTo = mMenuCollectionFragment;
                    break;
            }
            if (switchTo != null) {
                switchContent(currentFragment, switchTo);
                return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        mTitleTv = (TextView) findViewById(R.id.title_bar_title);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        int navigation1 = navigation.getMaxItemCount();
        navigation.getMenu().getItem(1).setChecked(true);
        setVisableActionBackBtn(false);
        setVisableActionBackBtn(false);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initFragments(savedInstanceState);
    }
    private void initFragments(Bundle savedInstanceState) {
        mTitleTv.setText(R.string.title_home);
        //判断activity是否重建，如果不是，则不需要重新建立fragment.
        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            if (mPurchasePlanFragment == null) {
                mPurchasePlanFragment = new PurchasePlanFragment();
            }
            if (mPickGoodsFragment == null) {
                mPickGoodsFragment = new PickGoodsFragment();
            }
            if (mMenuCollectionFragment == null) {
                mMenuCollectionFragment = new MenuCollectionFragment();
            }
            currentFragment = mPurchasePlanFragment;
            if (currentFragment.isAdded()) {
                ft.show(currentFragment);
            } else {
                ft.replace(R.id.content, currentFragment);
            }
            ft.commit();
        }
    }
    /**
     * 切换fragment
     *
     * @param from 当前显示的fragment
     * @param to   切换的目标fragment
     */
    public void switchContent(Fragment from, Fragment to) {
        if (from != to) {
            currentFragment = to;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (!to.isAdded()) {
                transaction.hide(from).add(R.id.content, to).commit();
            } else {
                transaction.hide(from).show(to).commit();
            }
        }
    }
}
