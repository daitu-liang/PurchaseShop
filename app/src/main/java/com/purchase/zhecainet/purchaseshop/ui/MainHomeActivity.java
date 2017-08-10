package com.purchase.zhecainet.purchaseshop.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
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
import com.purchase.zhecainet.purchaseshop.net.download.DownloadUtil;
import com.purchase.zhecainet.purchaseshop.ui.menucollection.MenuCollectionFragment;
import com.purchase.zhecainet.purchaseshop.ui.pickgoods.PickGoodsFragment;
import com.purchase.zhecainet.purchaseshop.ui.purchaseplan.PurchasePlanFragment;
import com.purchase.zhecainet.purchaseshop.utils.HeadUtils;
import com.purchase.zhecainet.purchaseshop.utils.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

public class MainHomeActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    private static final String TAG = "MainHomeActivity";
    private Logger log = Logger.getLogger(TAG);
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
                .addItem(new BottomNavigationItem(R.drawable.ic_home_pick_goods, R.string.title_dashboard).setActiveColorResource(R.color.home_memu0))
                .addItem(new BottomNavigationItem(R.drawable.ic_home_plan, R.string.title_home).setActiveColorResource(R.color.home_memu1))
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

    /**
     * 检查是否有新的版本
     */
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
                        if(Integer.parseInt(bean.getVersion())>getAPPLocalVersion(MainHomeActivity.this)){
                            if("0".equals(bean.getAttr())){//不需要更更新

                            }else if("1".equals(bean.getAttr())){//需要强制更更新
                                loadAPK(bean);
                            }else if("2".equals(bean.getAttr())){//用户可选更更新
                                showDialogUpdateTip(bean);
                            }
                        }
                    }
                });
    }
    public static int getAPPLocalVersion(Context ctx) {
        int currentVersionCode = 1;
        PackageManager manager = ctx.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            String appVersionName = info.versionName; // 版本名
            currentVersionCode = info.versionCode; // 版本号
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return currentVersionCode;
    }
    //强制更新
    private void loadAPK(VersionInfo bean) {
        if(TextUtils.isEmpty(bean.getDown_url()))return;
        DownloadUtil.get().download(bean.getDown_url(), MainHomeActivity.this, new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
                if (!TextUtils.isEmpty(DownloadUtil.downloadUpdateApkFilePath)) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setDataAndType(
                            Uri.parse("file://"
                                    + DownloadUtil.downloadUpdateApkFilePath),
                            "application/vnd.android.package-archive");
                    //todo 针对不同的手机 以及sdk版本  这里的uri地址可能有所不同
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
            }

            @Override
            public void onDownloading(int progress) {
            }
            @Override
            public void onDownloadFailed() {
            }
        });
    }


    /**
     * 更新最新版本展示对话框
     */
    private void showDialogUpdateTip(VersionInfo bean) {
        if(TextUtils.isEmpty(bean.getDown_url()))return;
        final String appUpdateUlr = bean.getDown_url();
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("发现新版本， 需要更新吗")
                .setCancelable(false)
                .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        showProgressDialog(appUpdateUlr);
                    }
                })
                .setNegativeButton("稍后再说", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    /**
     * 下载apk对话框
     * @param appUpdateUlr
     */
    private void showProgressDialog(String appUpdateUlr) {
        // 默认的初始值
         int progress = 0;
        // 创建ProgressDialog类
       final ProgressDialog progressDialog = new ProgressDialog(MainHomeActivity.this);
        // 设置进度对话框为水平进度条风格
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("正在下载最新版本...");
        // 设置是否可以通过点击Back键取消  默认true
        progressDialog.setCancelable(false);
        // 设置在点击Dialog外是否取消Dialog进度条  默认true
        progressDialog.setCanceledOnTouchOutside(false);
        //设置最大值
        progressDialog.setMax(100);
        // 设置进度初始值
        progress = (progress > 0) ? progress : 0;
        progressDialog.setProgress(progress);
        progressDialog.show();
        DownloadUtil.get().download(appUpdateUlr, MainHomeActivity.this, new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
//                                myHandler.sendEmptyMessage(MainActivity.downLoadFinish);
                progressDialog.dismiss();
                log.i(TAG,"apk_url="+DownloadUtil.downloadUpdateApkFilePath);
                if (!TextUtils.isEmpty(DownloadUtil.downloadUpdateApkFilePath)) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setDataAndType(
                            Uri.parse("file://"
                                    + DownloadUtil.downloadUpdateApkFilePath),
                            "application/vnd.android.package-archive");
                    //todo 针对不同的手机 以及sdk版本  这里的uri地址可能有所不同
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
            }

            @Override
            public void onDownloading(int progress) {
                progressDialog.setProgress(progress);
            }

            @Override
            public void onDownloadFailed() {
                progressDialog.dismiss();
                showToast(getString(R.string.apk_load_fail));
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
