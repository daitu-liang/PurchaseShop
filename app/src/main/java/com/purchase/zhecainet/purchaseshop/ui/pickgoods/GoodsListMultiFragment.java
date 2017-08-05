package com.purchase.zhecainet.purchaseshop.ui.pickgoods;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.purchase.zhecainet.purchaseshop.R;
import com.purchase.zhecainet.purchaseshop.api.pickgoods.PickGoodsApi;
import com.purchase.zhecainet.purchaseshop.base.BaseFragment;
import com.purchase.zhecainet.purchaseshop.model.GoodsSaleListInfo;
import com.purchase.zhecainet.purchaseshop.model.PurchaseOrderInfo;
import com.purchase.zhecainet.purchaseshop.net.ApiSubscriber;
import com.purchase.zhecainet.purchaseshop.net.HttpTransformer;
import com.purchase.zhecainet.purchaseshop.net.RetrofitClient;
import com.purchase.zhecainet.purchaseshop.ui.commom.GoodsDetailActivity;
import com.purchase.zhecainet.purchaseshop.utils.HeadUtils;
import com.purchase.zhecainet.purchaseshop.utils.ListUtil;
import com.purchase.zhecainet.purchaseshop.utils.Logger;
import com.purchase.zhecainet.purchaseshop.utils.UserUtil;

import java.util.ArrayList;
import java.util.HashMap;
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
    List<GoodsSaleListInfo> mListData = new ArrayList<GoodsSaleListInfo>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_pickgoods_list, null);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    private void initView(View view) {
        String categoryIdKey = (String) getArguments().getSerializable("category_id_key");
        String categoryNameKey = (String) getArguments().getSerializable("category_name_key");
        getPickGoodsListData(categoryIdKey, categoryNameKey);
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
        mAdapter = new GoodsAdpater(getActivity(), mListData);
        mAdapter.setGridviewOnClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.refresh();

    }

    private void initData() {
        for (int i = 0; i < 50; i++) {
            GoodsSaleListInfo info = new GoodsSaleListInfo();
            info.setName("西班牙牛排" + i);
            info.setPhoto("http://t.img.i.hsuperior.com/80a388ed-93f5-44a0-8aa7-e65f0f8809f2");
            info.setPoint(2.5f);
            info.setWeight("54");
            info.setPrice("845.0");
            mListData.add(info);
        }
    }

    @Override
    public void OnItemListClickListener(int position) {
        startActivity(GoodsDetailActivity.getIntent(getActivity(), mListData.get(position).getId()));
    }

    @Override
    public void OnItemAddClickListener(int position) {
        addPurchase(mListData.get(position));
    }

    /**
     * 获取商品列表数据
     *
     * @param goodsId
     * @param goodsName
     */
    private void getPickGoodsListData(String goodsId, String goodsName) {
        String userId = UserUtil.getUid();
        if (TextUtils.isEmpty(userId)) return;
        Map<String, String> paramsmap = new LinkedHashMap<>();
        paramsmap.put("category_id", goodsId);
        paramsmap.put("keyword", goodsName);
        String headVaule = HeadUtils.getAuthorization(paramsmap.toString());
        RetrofitClient.getInstance()
                .builder(PickGoodsApi.class)
                .getGoodsSaleListQuery(headVaule, userId, paramsmap)
                .compose(HttpTransformer.<List<GoodsSaleListInfo>>toTransformer())
                .subscribe(new ApiSubscriber<List<GoodsSaleListInfo>>() {
                    @Override
                    protected void onSuccess(List<GoodsSaleListInfo> bean) {
                        if (!ListUtil.isEmpty(bean)) {
                            mListData.addAll(bean);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    /**
     * 加入采购
     *
     * @param saleListInfo
     */
    private void addPurchase(GoodsSaleListInfo saleListInfo) {
        String userId = UserUtil.getUid();
        if (TextUtils.isEmpty(userId)) return;
        String collaborator_id = UserUtil.userInfo.getDepartmnetId();
        if (TextUtils.isEmpty(collaborator_id)) return;
        Map<String, String> paramsmap = new HashMap<>();
        paramsmap.put("goods_sale_id", saleListInfo.getId());
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(mListData!=null){
            mListData.clear();
        }

    }
}
