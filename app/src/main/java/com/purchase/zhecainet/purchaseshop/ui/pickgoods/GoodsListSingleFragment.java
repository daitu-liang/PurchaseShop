package com.purchase.zhecainet.purchaseshop.ui.pickgoods;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.purchase.zhecainet.purchaseshop.R;
import com.purchase.zhecainet.purchaseshop.base.BaseFragment;
import com.purchase.zhecainet.purchaseshop.model.GoodsSaleListInfo;
import com.purchase.zhecainet.purchaseshop.ui.commom.GoodsDetailActivity;
import com.purchase.zhecainet.purchaseshop.utils.HeadUtils;
import com.purchase.zhecainet.purchaseshop.utils.Logger;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by leixiaoliang on 2017/7/31.
 * 邮箱：lxliang1101@163.com
 */

public class GoodsListSingleFragment extends BaseFragment  {
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
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CommonAdapter<GoodsSaleListInfo>(getActivity(), R.layout.item_goods_single,mListData){
            @Override
            protected void convert(ViewHolder holder, GoodsSaleListInfo saleListInfo, int position) {

                if(position<3){
                    holder.setBackgroundRes(R.id.add_collection_btn,R.drawable.ic_collectioned);
                }else {
                    holder.setBackgroundRes(R.id.add_collection_btn,R.drawable.ic_collection);
                }

                Uri uri = Uri.parse("http://t.img.i.hsuperior.com/80a388ed-93f5-44a0-8aa7-e65f0f8809f2");
                SimpleDraweeView draweeView = (SimpleDraweeView) holder.getView(R.id.goods_image);
                draweeView.setImageURI(uri);
                RatingBar ratingbar =holder.getView(R.id.indicator_ratingbar);
                ratingbar.setNumStars(5);
                ratingbar.setRating(3.5F);
                holder.setText(R.id.goods_name_tv, saleListInfo.getName());
                holder.setText(R.id.goods_num_weight_tv, saleListInfo.getWeight()+"/KG");
//                holder.setText(R.id.goods_desc_tv, collectionInfo.getSupplier_content().getProducer()+"");
                holder.setText(R.id.goods_desc_tv, "来自上海滩");
                holder.setText(R.id.goods_price, "￥ "+saleListInfo.getPrice());
                TagGroup mTagGroup =holder.getView(R.id.goods_tag_group);
                mTagGroup.setTags(new String[]{"Tag1", "Tag2", "Tag3"});

                holder.setOnClickListener(R.id.add_purchase, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addPurchase();
                    }
                });

            }
        };
        mRecyclerview.setAdapter(mAdapter);
    }

    /**
     * 加入采购
     */
    private void addPurchase() {
        Map<String, String> paramsmap = new HashMap<>();
        paramsmap.put("version","1.0.0");
        paramsmap.put("package","com.purchase.zhecainet.purchaseshop");
        paramsmap.put("client_type","3");
        String headVaule= HeadUtils.getAuthorization(paramsmap.toString());


    }

    private void initData() {

        for(int i=0;i<50;i++){
            GoodsSaleListInfo info=new GoodsSaleListInfo();
            info.setName("西班牙牛排"+i);
            info.setPhoto("http://t.img.i.hsuperior.com/80a388ed-93f5-44a0-8aa7-e65f0f8809f2");
            info.setPoint(2.0f);
            info.setWeight("54");
            info.setPrice("845.0");
            mListData.add(info);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                startActivity(GoodsDetailActivity.getIntent(getActivity(),null));
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

    }
}
