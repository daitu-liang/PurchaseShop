package com.purchase.zhecainet.purchaseshop.ui.pickgoods;

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
import com.purchase.zhecainet.purchaseshop.model.BaseInfo;
import com.purchase.zhecainet.purchaseshop.model.GoodsSaleListInfo;
import com.purchase.zhecainet.purchaseshop.model.PurchaseOrderInfo;
import com.purchase.zhecainet.purchaseshop.model.RecommendListInfo;
import com.purchase.zhecainet.purchaseshop.model.SupplierContent;
import com.purchase.zhecainet.purchaseshop.net.ApiSubscriber;
import com.purchase.zhecainet.purchaseshop.net.HttpTransformer;
import com.purchase.zhecainet.purchaseshop.net.RetrofitClient;
import com.purchase.zhecainet.purchaseshop.ui.commom.GoodsDetailActivity;
import com.purchase.zhecainet.purchaseshop.ui.commom.WebActivity;
import com.purchase.zhecainet.purchaseshop.utils.FrescoImageLoader;
import com.purchase.zhecainet.purchaseshop.utils.HeadUtils;
import com.purchase.zhecainet.purchaseshop.utils.ListUtil;
import com.purchase.zhecainet.purchaseshop.utils.Logger;
import com.purchase.zhecainet.purchaseshop.utils.UserUtil;
import com.purchase.zhecainet.purchaseshop.widget.RatingBarView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
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

import static com.purchase.zhecainet.purchaseshop.R.id.add_collection_btn;

/**
 * Created by leixiaoliang on 2017/7/31.
 * 邮箱：lxliang1101@163.com
 */

public class GoodsListSingleFragment extends BaseFragment {
    private static final String TAG = "GoodsListMultiFragment";
    private Logger log = Logger.getLogger(TAG);
    private CommonAdapter mAdapter;
    List<GoodsSaleListInfo> mListData = new ArrayList<GoodsSaleListInfo>();

    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_pickgoods_list, null);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    private void initView(View view) {
        initData();

        String categoryIdKey = (String) getArguments().getSerializable("category_id_key");
        String categoryNameKey = (String) getArguments().getSerializable("category_name_key");

        getPickGoodsListData(categoryIdKey, categoryNameKey);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        initBannerData(new ArrayList<RecommendListInfo>() ,view);

        getBannerAdsData(categoryIdKey,view);
        mAdapter = new CommonAdapter<GoodsSaleListInfo>(getActivity(), R.layout.item_goods_single, mListData) {
            @Override
            protected void convert(ViewHolder holder, final GoodsSaleListInfo saleListInfo, int position) {

                if (position < 3) {
                    holder.setBackgroundRes(add_collection_btn, R.drawable.ic_collectioned);
                } else {
                    holder.setBackgroundRes(add_collection_btn, R.drawable.ic_collection);
                }

                if (!TextUtils.isEmpty(saleListInfo.getPhoto())) {
                    Uri uri = Uri.parse(saleListInfo.getPhoto());
                    SimpleDraweeView draweeView = (SimpleDraweeView) holder.getView(R.id.goods_image);
                    draweeView.setImageURI(uri);
                }
                RatingBarView ratingBarView = holder.getView(R.id.rb);
                ratingBarView.setClickable(false);//设置可否点击
                ratingBarView.setStar(saleListInfo.getPoint());//设置显示的星星个数
                holder.setText(R.id.goods_name_tv, saleListInfo.getName());
                holder.setText(R.id.goods_num_weight_tv, saleListInfo.getWeight() + "/KG");
                holder.setText(R.id.goods_desc_tv, "来自上海滩");
                holder.setText(R.id.goods_price, "￥ " + saleListInfo.getPrice());
                TagGroup mTagGroup = holder.getView(R.id.goods_tag_group);
                SupplierContent supplierInfo = saleListInfo.getSupplier_content();
                if (supplierInfo != null) {
                    holder.setText(R.id.goods_desc_tv, supplierInfo.getProducer());
                    String tagStr = supplierInfo.getTags();
                    String[] tagArr = tagStr.split(",");
                    if (tagArr.length > 0) {
                        mTagGroup.setTags(tagArr);
                    } else {
                        mTagGroup.setVisibility(View.GONE);
                    }

                }
                holder.setOnClickListener(R.id.add_collection_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addCollectGoods(saleListInfo);
                    }
                });
                holder.setOnClickListener(R.id.add_purchase, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addPurchase(saleListInfo);
                    }
                });
            }
        };
        mRecyclerview.setAdapter(mAdapter);
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                startActivity(GoodsDetailActivity.getIntent(getActivity(), mListData.get(position).getId()));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
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
     *推荐位查询
     * @param categoryIdKey
     */
    private void getBannerAdsData(String categoryIdKey,final View view) {
        Map<String, String> paramsmap = new HashMap<>();
        paramsmap.put("category_id", categoryIdKey);
        String headVaule = HeadUtils.getAuthorization(paramsmap.toString());
        RetrofitClient.getInstance()
                .builder(PickGoodsApi.class)
                .getRecommendListQuery(headVaule,paramsmap)
                .compose(HttpTransformer.<List<RecommendListInfo>>toTransformer())
                .subscribe(new ApiSubscriber<List<RecommendListInfo>>() {
                    @Override
                    protected void onSuccess(List<RecommendListInfo> bean) {
                        initBannerData(bean,view);
                    }
                });

    }

    /**
     * 初始化推荐栏
     * @param beanList
     */
    private void initBannerData(List<RecommendListInfo> beanList,View view) {
//        if(ListUtil.isEmpty(bean))return;

        final  List<String> topShowPics=new ArrayList<>();
        final List<RecommendListInfo> beanPic=new ArrayList<>();
        List<RecommendListInfo> beanVedio=new ArrayList<>();
        List<RecommendListInfo> beanText=new ArrayList<>();

        View header = LayoutInflater.from(getActivity()).inflate(R.layout.banner_layout, (ViewGroup)view.findViewById(android.R.id.content),false);
        mRecyclerview.addHeaderView(header);
        Banner banner = (Banner)header.findViewById(R.id.banner);
        banner.setOnBannerListener(new  OnBannerListener(){

            @Override
            public void OnBannerClick(int position) {
                log.i("","OnBannerClick-position="+position);
//               startActivity(WebActivity.getIntent(getActivity(),beanPic.get(position).getUrl(),"活动"));
                startActivity(WebActivity.getIntent(getActivity(),topShowPics.get(position),"活动"));
            }
        });
      /*  if(ListUtil.isEmpty(beanList))return;
        for(int i=0;i<beanList.size();i++){
            if("1".equals(beanList.get(i).getType())){//图片
                beanPic.add(beanPic.get(i));
            }else if("2".equals(beanList.get(i).getType())){//视频
                beanVedio.add(beanPic.get(i));
            }else if("4".equals(beanList.get(i).getType())){//文档
                beanText.add(beanPic.get(i));
            }

        }

        for(int picIndex=0;picIndex<beanPic.size();picIndex++){
            topShowPics.add(beanPic.get(picIndex).getUrl());
        }
*/
        topShowPics.add("http://t.img.i.hsuperior.com/80a388ed-93f5-44a0-8aa7-e65f0f8809f2");
        topShowPics.add("http://t.img.i.hsuperior.com/80a388ed-93f5-44a0-8aa7-e65f0f8809f2");
        topShowPics.add("http://t.img.i.hsuperior.com/80a388ed-93f5-44a0-8aa7-e65f0f8809f2");
        topShowPics.add("http://t.img.i.hsuperior.com/80a388ed-93f5-44a0-8aa7-e65f0f8809f2");
        banner.setImageLoader(new FrescoImageLoader());
        //设置图片集合
        if (topShowPics.size() == 1) {
            banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        }
        banner.setImages(topShowPics);
        banner.start();
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

    /**
     * 添加收藏
     *
     * @param saleListInfo
     */
    private void addCollectGoods(GoodsSaleListInfo saleListInfo) {
        String userId = UserUtil.getUid();
        if (TextUtils.isEmpty(userId)) return;
        String collaborator_id = UserUtil.userInfo.getDepartmnetId();
        if (TextUtils.isEmpty(collaborator_id)) return;
        Map<String, String> paramsmap = new HashMap<>();
        paramsmap.put("goods_sale_id", saleListInfo.getId());
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
    public void onDestroyView() {
        super.onDestroyView();
        if(mListData!=null){
            mListData.clear();
        }

    }

}
