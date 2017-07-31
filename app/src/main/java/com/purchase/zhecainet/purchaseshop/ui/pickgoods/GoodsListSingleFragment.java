package com.purchase.zhecainet.purchaseshop.ui.pickgoods;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.purchase.zhecainet.purchaseshop.R;
import com.purchase.zhecainet.purchaseshop.base.BaseFragment;
import com.purchase.zhecainet.purchaseshop.model.GoodsSaleListInfo;
import com.purchase.zhecainet.purchaseshop.utils.Logger;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
            protected void convert(ViewHolder holder, GoodsSaleListInfo collectionInfo, int position) {
                holder.setText(R.id.tv_pro_name, collectionInfo.getName());
                Uri uri = Uri.parse("http://t.img.i.hsuperior.com/80a388ed-93f5-44a0-8aa7-e65f0f8809f2");
                SimpleDraweeView draweeView = (SimpleDraweeView) holder.getView(R.id.goods_image);
                draweeView.setImageURI(uri);
            }
        };
        mRecyclerview.setAdapter(mAdapter);
    }

    private void initData() {

        for(int i=0;i<50;i++){
            GoodsSaleListInfo info=new GoodsSaleListInfo();
            info.setName("hahhha"+i);
            info.setPhoto("http://t.img.i.hsuperior.com/80a388ed-93f5-44a0-8aa7-e65f0f8809f2");
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
