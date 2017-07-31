package com.purchase.zhecainet.purchaseshop.ui.purchaseplan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.purchase.zhecainet.purchaseshop.R;
import com.purchase.zhecainet.purchaseshop.api.common.CommonApi;
import com.purchase.zhecainet.purchaseshop.base.BaseFragment;
import com.purchase.zhecainet.purchaseshop.model.VersionInfo;
import com.purchase.zhecainet.purchaseshop.net.ApiSubscriber;
import com.purchase.zhecainet.purchaseshop.net.HttpTransformer;
import com.purchase.zhecainet.purchaseshop.net.RetrofitClient;
import com.purchase.zhecainet.purchaseshop.utils.HeadUtils;
import com.purchase.zhecainet.purchaseshop.utils.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by leixiaoliang on 2017/7/26.
 * 邮箱：lxliang1101@163.com
 */

public class PurchasePlanFragment extends BaseFragment {
    private static final String TAG ="PurchasePlanFragment" ;
    private Logger log = Logger.getLogger(TAG);
    @BindView(R.id.btn)
    Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_purchase_plan, null);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    private void initView(View view) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPickGoodsData();
            }
        });

    }

    private void getPickGoodsData() {
        Map<String, String> paramsmap = new LinkedHashMap<>();
        paramsmap.put("version","1.0.0");
        paramsmap.put("package","com.purchase.zhecainet.purchaseshop");
        paramsmap.put("client_type","3");
        String headVaule= HeadUtils.getAuthorization(paramsmap.toString());
        RetrofitClient.getInstance()
                .builder(CommonApi.class)
                .getVersionInfo(headVaule,paramsmap)
                .compose(HttpTransformer.<VersionInfo>toTransformer())
                .subscribe(new ApiSubscriber<VersionInfo>() {
                    @Override
                    protected void onSuccess(VersionInfo bean) {
                        log.e(TAG,"bean="+bean.toString());

                    }
                });
    }
}
