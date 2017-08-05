package com.purchase.zhecainet.purchaseshop.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.purchase.zhecainet.purchaseshop.R;
import com.purchase.zhecainet.purchaseshop.api.common.CommonApi;
import com.purchase.zhecainet.purchaseshop.base.BaseActivity;
import com.purchase.zhecainet.purchaseshop.model.VersionInfo;
import com.purchase.zhecainet.purchaseshop.net.ApiSubscriber;
import com.purchase.zhecainet.purchaseshop.net.HttpTransformer;
import com.purchase.zhecainet.purchaseshop.net.RetrofitClient;
import com.purchase.zhecainet.purchaseshop.ui.menucollection.MenuCollectionFragment;
import com.purchase.zhecainet.purchaseshop.ui.pickgoods.PickGoodsFragment;
import com.purchase.zhecainet.purchaseshop.ui.purchaseplan.PurchasePlanFragment;
import com.purchase.zhecainet.purchaseshop.utils.HeadUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class MainHomeActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener{
    private PurchasePlanFragment mPurchasePlanFragment;
    private PickGoodsFragment mPickGoodsFragment;
    private MenuCollectionFragment mMenuCollectionFragment;
    private Fragment currentFragment;
    private BottomNavigationBar bottomNavigationBar;
    int lastSelectedPosition = 1;
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, MainHomeActivity.class);
        return intent;
    }
    private TextView mTitleTv;

    /*private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
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

    };*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        mTitleTv = (TextView) findViewById(R.id.title_bar_title);
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setItemTextColor(getResources().getColorStateList(R.color.selector_nav_menu_color,null));
//        int navigation1 = navigation.getMaxItemCount();
//        navigation.getMenu().getItem(1).setChecked(true);
        setVisableActionBackBtn(false);
        setVisableActionBackBtn(false);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initBottomNavigationBar();
        initFragments(savedInstanceState);
        getSolftVersionData();
    }
    private void initBottomNavigationBar() {
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setAutoHideEnabled(true);
        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
//        numberBadgeItem = new BadgeItem()
//                .setBorderWidth(4)
//                .setBackgroundColorResource(R.color.blue)
//                .setText("5")
//                .setHideOnSelect(false);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_plan,R.string.title_dashboard).setActiveColorResource(R.color.home_memu1))
                .addItem(new BottomNavigationItem(R.drawable.ic_home_pick_goods, R.string.title_home).setActiveColorResource(R.color.home_memu0))
                .addItem(new BottomNavigationItem(R.drawable.ic_home_collection_menu, R.string.title_notifications).setActiveColorResource(R.color.home_memu2))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();
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

    @Override
    public void onTabSelected(int position) {
        Fragment switchTo = null;
        switch (position) {
            case 1:
                mTitleTv.setText(R.string.title_home);
                if (mPurchasePlanFragment == null) {
                    mPurchasePlanFragment = new PurchasePlanFragment();
                }
                switchTo = mPurchasePlanFragment;
                break;
            case 0:
                mTitleTv.setText(R.string.title_dashboard);
                if (mPickGoodsFragment == null) {
                    mPickGoodsFragment = new PickGoodsFragment();
                }
                switchTo = mPickGoodsFragment;
                break;
            case 2:
                mTitleTv.setText(R.string.title_notifications);
                if (mMenuCollectionFragment == null) {
                    mMenuCollectionFragment = new MenuCollectionFragment();
                }
                switchTo = mMenuCollectionFragment;
                break;
        }
        if (switchTo != null) {
            switchContent(currentFragment, switchTo);
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
    private void getSolftVersionData() {
        Map<String, String> paramsmap = new LinkedHashMap<>();
        paramsmap.put("version", "1.0.0");
        paramsmap.put("package", "com.purchase.zhecainet.purchaseshop");
        paramsmap.put("client_type", "3");
        String headVaule = HeadUtils.getAuthorization(paramsmap.toString());
        RetrofitClient.getInstance()
                .builder(CommonApi.class)
                .getVersionInfo(headVaule, paramsmap)
                .compose(HttpTransformer.<VersionInfo>toTransformer())
                .subscribe(new ApiSubscriber<VersionInfo>() {
                    @Override
                    protected void onSuccess(VersionInfo bean) {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
