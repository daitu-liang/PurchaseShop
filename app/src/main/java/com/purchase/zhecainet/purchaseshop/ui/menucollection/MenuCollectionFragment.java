package com.purchase.zhecainet.purchaseshop.ui.menucollection;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.purchase.zhecainet.purchaseshop.R;
import com.purchase.zhecainet.purchaseshop.api.menucollection.MenuApi;
import com.purchase.zhecainet.purchaseshop.api.pickgoods.PickGoodsApi;
import com.purchase.zhecainet.purchaseshop.base.BaseFragment;
import com.purchase.zhecainet.purchaseshop.model.CollectionListInfo;
import com.purchase.zhecainet.purchaseshop.model.PurchaseOrderInfo;
import com.purchase.zhecainet.purchaseshop.net.ApiSubscriber;
import com.purchase.zhecainet.purchaseshop.net.HttpTransformer;
import com.purchase.zhecainet.purchaseshop.net.RetrofitClient;
import com.purchase.zhecainet.purchaseshop.ui.commom.GoodsDetailActivity;
import com.purchase.zhecainet.purchaseshop.utils.HeadUtils;
import com.purchase.zhecainet.purchaseshop.utils.ListUtil;
import com.purchase.zhecainet.purchaseshop.utils.UserUtil;
import com.purchase.zhecainet.purchaseshop.widget.RatingBarView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by leixiaoliang on 2017/7/26.
 * 邮箱：lxliang1101@163.com
 */

public class MenuCollectionFragment extends BaseFragment {

    List<CollectionListInfo> mListData = new ArrayList<>();
    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerview;

    private CommonAdapter<CollectionListInfo> mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_menu, null);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    private void initView(View view) {


        initData();
        getCollectionListData();

        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CommonAdapter<CollectionListInfo>(getActivity(), R.layout.item_collection, mListData) {
            @Override
            protected void convert(ViewHolder holder, final CollectionListInfo collectionInfo, int position) {


                if (position < 5) {
                    holder.setBackgroundRes(R.id.add_collection_btn, R.drawable.ic_collectioned);
                } else {
                    holder.setBackgroundRes(R.id.add_collection_btn, R.drawable.ic_collection);
                }

                Uri uri = Uri.parse("http://t.img.i.hsuperior.com/80a388ed-93f5-44a0-8aa7-e65f0f8809f2");
                SimpleDraweeView draweeView = (SimpleDraweeView) holder.getView(R.id.goods_image);
                draweeView.setImageURI(uri);

                holder.setText(R.id.goods_name_tv, collectionInfo.getName());
                holder.setText(R.id.goods_num_weight_tv, collectionInfo.getWeight() + "KG");
//                holder.setText(R.id.goods_desc_tv, collectionInfo.getSupplier_content().getProducer()+"");
                holder.setText(R.id.goods_desc_tv, "上次采购" + collectionInfo.getLast_number() + "份");
                holder.setText(R.id.goods_price, "￥ " + collectionInfo.getPrice());
                TagGroup mTagGroup = holder.getView(R.id.goods_tag_group);
                mTagGroup.setTags(new String[]{"Tag1", "Tag2", "Tag3"});
                RatingBarView ratingBarView = holder.getView(R.id.rb);
                ratingBarView.setClickable(false);//设置可否点击
                ratingBarView.setStar(2.5f);//设置显示的星星个数
//                ratingBarView.setStepSize(RatingBar.StepSize.Half);//设置每次点击增加一颗星还是半颗星
//                ratingBarView.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
//                    @Override
//                    public void onRatingChange(float ratingCount) {//点击星星变化后选中的个数
//                        Log.d("RatingBar","RatingBar-Count="+ratingCount);
//                    }
//                });

                holder.setOnClickListener(R.id.add_purchase, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addPurchase(collectionInfo);
                    }
                });
            }
        };
        mRecyclerview.setAdapter(mAdapter);
    }

    /**
     * 获取收藏列表
     */
    private void getCollectionListData() {
        Map<String, String> paramsmap = new LinkedHashMap<>();
        String userId = UserUtil.getUid();
        if (TextUtils.isEmpty(userId)) return;
        String collaborator_id = UserUtil.userInfo.getDepartmnetId();
        if (TextUtils.isEmpty(collaborator_id)) return;

        String headVaule = HeadUtils.getAuthorization(paramsmap.toString());
        RetrofitClient.getInstance()
                .builder(MenuApi.class)
                .getPurchaseTemplateQuery(headVaule, userId, collaborator_id)
                .compose(HttpTransformer.<List<CollectionListInfo>>toTransformer())
                .subscribe(new ApiSubscriber<List<CollectionListInfo>>() {
                    @Override
                    protected void onSuccess(List<CollectionListInfo> bean) {
                        if (mListData != null && !ListUtil.isEmpty(bean)) {
                            mListData.clear();
                            mListData.addAll(bean);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    /**
     * 加入采购
     *
     * @param collectionInfo
     */
    private void addPurchase(CollectionListInfo collectionInfo) {
              showToast("加入采购");
        String userId = UserUtil.getUid();
        if (TextUtils.isEmpty(userId)) return;
        String collaborator_id = UserUtil.userInfo.getDepartmnetId();
        if (TextUtils.isEmpty(collaborator_id)) return;
        Map<String, String> paramsmap = new HashMap<>();
        paramsmap.put("goods_sale_id", collectionInfo.getId());
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

    private void initData() {
        for (int i = 0; i < 50; i++) {
            CollectionListInfo info = new CollectionListInfo();
            info.setName("渴了鸡排" + i);
            info.setPhoto("http://t.img.i.hsuperior.com/80a388ed-93f5-44a0-8aa7-e65f0f8809f2");
            info.setLast_number("7");
            info.setPrice("487.0");
            info.setPoint(3.0f);
            mListData.add(info);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                startActivity(GoodsDetailActivity.getIntent(getActivity(), null));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mListData != null) {
            mListData.clear();
        }
    }
}
