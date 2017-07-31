package com.purchase.zhecainet.purchaseshop.ui.pickgoods;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.purchase.zhecainet.purchaseshop.R;
import com.purchase.zhecainet.purchaseshop.api.common.CommonApi;
import com.purchase.zhecainet.purchaseshop.base.BaseFragment;
import com.purchase.zhecainet.purchaseshop.model.SmsInfo;
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

public class PickGoodsFragment extends BaseFragment {

    private static final String TAG ="PickGoodsFragment" ;
    private Logger log = Logger.getLogger(TAG);
    @BindView(R.id.btn)
    Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_pickgoods, null);
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
        String phone = "18593276078";
        Map<String, String> paramsmap = new LinkedHashMap<>();
        paramsmap.put("phone",phone);
        String headVaule= HeadUtils.getAuthorization(paramsmap.toString());
        RetrofitClient.getInstance()
                .builder(CommonApi.class)
                .getBasicSms(headVaule,paramsmap)
                .compose(HttpTransformer.<SmsInfo>toTransformer())
                .subscribe(new ApiSubscriber<SmsInfo>() {
                    @Override
                    protected void onSuccess(SmsInfo bean) {
                        log.e(TAG,"bean="+bean.getTran_id());

                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
