package com.purchase.zhecainet.purchaseshop.ui.commom;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.purchase.zhecainet.purchaseshop.R;
import com.purchase.zhecainet.purchaseshop.api.menucollection.MenuApi;
import com.purchase.zhecainet.purchaseshop.api.pickgoods.PickGoodsApi;
import com.purchase.zhecainet.purchaseshop.base.BaseActivity;
import com.purchase.zhecainet.purchaseshop.model.BaseInfo;
import com.purchase.zhecainet.purchaseshop.model.GoodsSaleDetailInfo;
import com.purchase.zhecainet.purchaseshop.model.PurchaseOrderInfo;
import com.purchase.zhecainet.purchaseshop.net.ApiSubscriber;
import com.purchase.zhecainet.purchaseshop.net.HttpTransformer;
import com.purchase.zhecainet.purchaseshop.net.RetrofitClient;
import com.purchase.zhecainet.purchaseshop.utils.HeadUtils;
import com.purchase.zhecainet.purchaseshop.utils.Logger;
import com.purchase.zhecainet.purchaseshop.utils.UserUtil;
import com.purchase.zhecainet.purchaseshop.widget.RatingBarView;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.gujun.android.taggroup.TagGroup;

public class GoodsDetailActivity extends BaseActivity {
    private static final String TAG = "GoodsDetailActivity";
    private Logger log = Logger.getLogger(TAG);
    @BindView(R.id.goods_image)
    SimpleDraweeView goodsImage;

    @BindView(R.id.rb)
    RatingBarView ratingBarView;
    @BindView(R.id.goods_name_tv)
    TextView goodsNameTv;
    @BindView(R.id.goods_num_weight_tv)
    TextView goodsNumWeightTv;
    @BindView(R.id.goods_desc_tv)
    TextView goodsDescTv;
    @BindView(R.id.goods_tag_group)
    TagGroup goodsTagGroup;
    @BindView(R.id.goods_price)
    TextView goodsPrice;
    @BindView(R.id.add_collection_btn)
    ImageButton addCollectionBtn;
    @BindView(R.id.webview)
    WebView mWebView;
    @BindView(R.id.tip_tv)
    TextView tipTv;
    @BindView(R.id.add_purchase_button)
    RelativeLayout addPurchaseButton;
    private String goods_sale_id;

    public static Intent getIntent(Context context, String goods_sale_id) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra("goods_sale_id_key", goods_sale_id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        initSetting();
        setVisableActionBackBtn(true);
        setVisableActionBarOperate(false);
        setActionBarTitle(getString(R.string.title_goods_detail));
        goods_sale_id = getIntent().getStringExtra("goods_sale_id_key");
        getGoodsDetailData(goods_sale_id);
        initGoodsDetailData(null);
    }

    @OnClick({R.id.add_collection_btn, R.id.add_purchase_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_collection_btn:
                doCollection();
                break;
            case R.id.add_purchase_button:
                addPurchaseGoods();
                break;
        }
    }

    /**
     * 初始化商品详情
     *
     * @param detailInfos
     */
    private void initGoodsDetailData(GoodsSaleDetailInfo detailInfos) {
        GoodsSaleDetailInfo detailInfo = new GoodsSaleDetailInfo();
        detailInfo.setPhoto("http://t.img.i.hsuperior.com/80a388ed-93f5-44a0-8aa7-e65f0f8809f2");
        detailInfo.setName("意大利牛逼");
        detailInfo.setPoint(3.5f);
        detailInfo.setWeight("4.2");
        detailInfo.setPrice("5484.2");
        if (detailInfo == null) return;
        if (!TextUtils.isEmpty(detailInfo.getPhoto())) {
            Uri uri = Uri.parse(detailInfo.getPhoto());
            goodsImage.setImageURI(uri);
        }
        ratingBarView.setClickable(false);//设置可否点击
        ratingBarView.setStar(detailInfo.getPoint());//设置显示的星星个数
        goodsNameTv.setText(detailInfo.getName());

        goodsNumWeightTv.setText(detailInfo.getWeight() + "/KG");
        goodsDescTv.setText("chaninse kamd,54阿豪");
//         goodsDescTv.setText(detailInfo.getSupplier_content().getProducer());

        goodsTagGroup.setTags(new String[]{"Tag1", "Tag2", "Tag3"});
        goodsPrice.setText("￥ " + detailInfo.getPrice());

        if (detailInfo.getAttachm() != null) {
            loadWeview(detailInfo.getAttachm().getUrl());
        }
        loadWeview("https://my.oschina.net/zhoulc/blog/127065");
    }


    /**
     * 商品详情
     *
     * @param goods_sale_id
     */
    private void getGoodsDetailData(String goods_sale_id) {
        if (TextUtils.isEmpty(goods_sale_id)) return;
        String userId = UserUtil.getUid();
        if (TextUtils.isEmpty(userId)) return;
        Map<String, String> paramsmap = new LinkedHashMap<>();

        String headVaule = HeadUtils.getAuthorization(paramsmap.toString());
        RetrofitClient.getInstance()
                .builder(PickGoodsApi.class)
                .getGoodsSaleDetailQuery(headVaule, userId, goods_sale_id)
                .compose(HttpTransformer.<GoodsSaleDetailInfo>toTransformer())
                .subscribe(new ApiSubscriber<GoodsSaleDetailInfo>() {
                    @Override
                    protected void onSuccess(GoodsSaleDetailInfo bean) {
                        initGoodsDetailData(bean);
                    }
                });
    }


    /**
     * 添加收藏
     */
    private void doCollection() {

        String userId = UserUtil.getUid();
        if (TextUtils.isEmpty(userId)) return;
        String collaborator_id = UserUtil.userInfo.getDepartmnetId();
        if (TextUtils.isEmpty(collaborator_id)) return;
        Map<String, String> paramsmap = new HashMap<>();
        paramsmap.put("goods_sale_id", goods_sale_id);
        String headVaule = HeadUtils.getAuthorization(paramsmap.toString());
        RetrofitClient.getInstance()
                .builder(MenuApi.class)
                .getPurchaseTemplateAdd(headVaule, userId, collaborator_id)
                .compose(HttpTransformer.<BaseInfo>toTransformer())
                .subscribe(new ApiSubscriber<BaseInfo>() {
                    @Override
                    protected void onSuccess(BaseInfo bean) {
                        showToast(getString(R.string.tip_collection_success));
                    }
                });
    }

    /**
     * 添加采购物
     */
    private void addPurchaseGoods() {

        String userId = UserUtil.getUid();
        if (TextUtils.isEmpty(userId)) return;
        String collaborator_id = UserUtil.userInfo.getDepartmnetId();
        if (TextUtils.isEmpty(collaborator_id)) return;
        Map<String, String> paramsmap = new HashMap<>();
        paramsmap.put("goods_sale_id", goods_sale_id);
        paramsmap.put("number", "1");
        String headVaule = HeadUtils.getAuthorization(paramsmap.toString());
        RetrofitClient.getInstance()
                .builder(PickGoodsApi.class)
                .getPurchaseOrderaAdd(headVaule, userId, collaborator_id)
                .compose(HttpTransformer.<PurchaseOrderInfo>toTransformer())
                .subscribe(new ApiSubscriber<PurchaseOrderInfo>() {
                    @Override
                    protected void onSuccess(PurchaseOrderInfo bean) {
                        showToast(getString(R.string.tip_add_goods_success));
                    }
                });
    }

    private void initSetting() {
        WebSettings settings = mWebView.getSettings();
        mWebView.requestFocusFromTouch();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setPluginState(WebSettings.PluginState.ON);

        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(false);
        settings.setDisplayZoomControls(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.supportMultipleWindows();
        settings.setSupportMultipleWindows(true);
        settings.setDomStorageEnabled(true);
//		settings.setDatabaseEnabled(true);
//		settings.setCacheMode(WebSettings.LOAD_DEFAULT);
//		settings.setAppCacheEnabled(true);
//		settings.setAppCachePath(mWebView.getContext().getCacheDir().getAbsolutePath());
        settings.setAllowFileAccess(true);
        settings.setNeedInitialFocus(true);

        if (Build.VERSION.SDK_INT > 19) {
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }

        settings.setDefaultTextEncodingName("utf-8");


    }

    private void loadWeview(String url) {
        if (TextUtils.isEmpty(url)) return;
        mWebView.setBackgroundColor(Color.TRANSPARENT); // 设置背景色
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                log.i(TAG, "错误码：" + error.getErrorCode() + "描述： " + error.getDescription());

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });
        mWebView.setWebChromeClient(new WebChromeClient());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
