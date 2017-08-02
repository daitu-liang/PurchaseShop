package com.purchase.zhecainet.purchaseshop.ui.pickgoods;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.purchase.zhecainet.purchaseshop.R;
import com.purchase.zhecainet.purchaseshop.api.common.CommonApi;
import com.purchase.zhecainet.purchaseshop.base.BaseFragment;
import com.purchase.zhecainet.purchaseshop.model.GoodsSaleListInfo;
import com.purchase.zhecainet.purchaseshop.model.SmsInfo;
import com.purchase.zhecainet.purchaseshop.net.ApiSubscriber;
import com.purchase.zhecainet.purchaseshop.net.HttpTransformer;
import com.purchase.zhecainet.purchaseshop.net.RetrofitClient;
import com.purchase.zhecainet.purchaseshop.ui.commom.GoodsDetailActivity;
import com.purchase.zhecainet.purchaseshop.utils.HeadUtils;
import com.purchase.zhecainet.purchaseshop.utils.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by leixiaoliang on 2017/7/31.
 * 邮箱：lxliang1101@163.com
 */

public class GoodsListMultiFragment extends BaseFragment implements GoodsAdpater.GridviewOnClickListener {
    private static final String TAG = "GoodsListMultiFragment";
    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerView;

    private Logger log = Logger.getLogger(TAG);
    private GoodsAdpater mAdapter;
    List<GoodsSaleListInfo> listData = new ArrayList<GoodsSaleListInfo>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_pickgoods_list, null);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    private void initView(View view) {
//        btn.setText(getArguments().getString("category_name", "Default")+
//                getArguments().getString("category_url", "url"));
        initData();
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 6);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

                mAdapter.notifyDataSetChanged();
                mRecyclerView.refreshComplete();
            }


            @Override
            public void onLoadMore() {

            }
        });
        mAdapter = new GoodsAdpater(getActivity(), listData);
        mAdapter.setGridviewOnClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.refresh();

    }

    private void initData() {
        for (int i = 0; i < 50; i++) {
            GoodsSaleListInfo category = new GoodsSaleListInfo();
            category.setName("car" + i);
            category.setPhoto("http://t.img.i.hsuperior.com/80a388ed-93f5-44a0-8aa7-e65f0f8809f2");
            listData.add(category);
        }
    }

    @Override
    public void OnItemListClickListener(int position) {
        startActivity(GoodsDetailActivity.getIntent(getActivity(), null));
    }

    @Override
    public void OnItemAddClickListener(int position) {
        showToast("加入采购");
    }

    private void getPickGoodsData() {
        String phone = "18593276078";
        Map<String, String> paramsmap = new LinkedHashMap<>();
        paramsmap.put("phone", phone);
        String headVaule = HeadUtils.getAuthorization(paramsmap.toString());
        RetrofitClient.getInstance()
                .builder(CommonApi.class)
                .getBasicSms(headVaule, paramsmap)
                .compose(HttpTransformer.<SmsInfo>toTransformer())
                .subscribe(new ApiSubscriber<SmsInfo>() {
                    @Override
                    protected void onSuccess(SmsInfo bean) {
                        log.e(TAG, "bean=" + bean.getTran_id());

                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }
}
