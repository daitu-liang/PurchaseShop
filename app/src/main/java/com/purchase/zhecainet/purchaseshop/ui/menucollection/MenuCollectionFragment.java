package com.purchase.zhecainet.purchaseshop.ui.menucollection;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.purchase.zhecainet.purchaseshop.R;
import com.purchase.zhecainet.purchaseshop.base.BaseFragment;
import com.purchase.zhecainet.purchaseshop.model.CollectionListInfo;
import com.purchase.zhecainet.purchaseshop.utils.HeadUtils;
import com.purchase.zhecainet.purchaseshop.widget.RatingBarView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
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

        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CommonAdapter<CollectionListInfo>(getActivity(), R.layout.item_collection,mListData){
            @Override
            protected void convert(ViewHolder holder, CollectionListInfo collectionInfo, int position) {


                if(position<5){
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
                holder.setText(R.id.goods_name_tv, collectionInfo.getName());
                holder.setText(R.id.goods_num_weight_tv, collectionInfo.getWeight()+"KG");
//                holder.setText(R.id.goods_desc_tv, collectionInfo.getSupplier_content().getProducer()+"");
                holder.setText(R.id.goods_desc_tv, "上次采购"+collectionInfo.getLast_number()+"份");
                holder.setText(R.id.goods_price, "￥ "+collectionInfo.getPrice());
                TagGroup mTagGroup =holder.getView(R.id.goods_tag_group);

                RatingBarView ratingBarView=holder.getView(R.id.rb);
                ratingBarView.setClickable(false);//设置可否点击
                ratingBarView.setStar(2.5f);//设置显示的星星个数
//                ratingBarView.setStepSize(RatingBar.StepSize.Half);//设置每次点击增加一颗星还是半颗星
//                ratingBarView.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
//                    @Override
//                    public void onRatingChange(float ratingCount) {//点击星星变化后选中的个数
//                        Log.d("RatingBar","RatingBar-Count="+ratingCount);
//                    }
//                });



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
            CollectionListInfo info=new CollectionListInfo();
            info.setName("渴了鸡排"+i);
            info.setPhoto("http://t.img.i.hsuperior.com/80a388ed-93f5-44a0-8aa7-e65f0f8809f2");
            info.setLast_number("7");
            info.setPrice("487.0");
            mListData.add(info);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
