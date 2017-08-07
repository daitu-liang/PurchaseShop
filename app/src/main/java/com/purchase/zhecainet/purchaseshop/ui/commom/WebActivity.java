package com.purchase.zhecainet.purchaseshop.ui.commom;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.purchase.zhecainet.purchaseshop.R;
import com.purchase.zhecainet.purchaseshop.base.BaseActivity;
import com.purchase.zhecainet.purchaseshop.utils.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends BaseActivity {

    private static final String TAG = "WebActivity";
    Logger log = Logger.getLogger("WebActivity");
    @BindView(R.id.webView)
    WebView mWebView;

    public static Intent getIntent(Context context, String url, String title) {
        Intent intent=new Intent(context, WebActivity.class);
        intent.putExtra("url_key",url);
        intent.putExtra("title_key",title);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        initSetting();
        initViews();
    }

    private void initViews() {
        setVisableActionBackBtn(true);
        setVisableActionBarOperate(false);
        String url = getIntent().getStringExtra("url_key");
        String title = getIntent().getStringExtra("title_key");
        setActionBarTitle(title);
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

        if(Build.VERSION.SDK_INT > 19){
            settings.setLoadsImagesAutomatically(true);
        }else{
            settings.setLoadsImagesAutomatically(false);		}

        settings.setDefaultTextEncodingName("utf-8");


    }
}
