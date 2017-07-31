package com.purchase.zhecainet.purchaseshop.ui.menucollection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.purchase.zhecainet.purchaseshop.R;
import com.purchase.zhecainet.purchaseshop.base.BaseFragment;
import com.purchase.zhecainet.purchaseshop.model.CollectionInfo;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by leixiaoliang on 2017/7/26.
 * 邮箱：lxliang1101@163.com
 */

public class MenuCollectionFragment extends BaseFragment {

    List<CollectionInfo> mListData = new ArrayList<>();
    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerview;

    private CommonAdapter<CollectionInfo> mAdapter;


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
        mAdapter = new CommonAdapter<CollectionInfo>(getActivity(), R.layout.item_collection,mListData){
            @Override
            protected void convert(ViewHolder holder, CollectionInfo collectionInfo, int position) {
                holder.setText(R.id.tv_pro_name, collectionInfo.getTitle());
               ImageView imageView= holder.getView(R.id.goods_iv);
                Glide.with(mContext)
                        .load(collectionInfo.getUrl())
                        .placeholder(R.drawable.ic_dashboard_black_24dp)
                        .crossFade()
                        .into(imageView);
            }
        };
        mRecyclerview.setAdapter(mAdapter);
    }

    private void initData() {

        for(int i=0;i<50;i++){
            CollectionInfo info=new CollectionInfo();
            info.setTitle("hahhha"+i);
            info.setUrl("http://t.img.i.hsuperior.com/80a388ed-93f5-44a0-8aa7-e65f0f8809f2");
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
