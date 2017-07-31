package com.purchase.zhecainet.purchaseshop.ui.pickgoods;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.purchase.zhecainet.purchaseshop.R;
import com.purchase.zhecainet.purchaseshop.api.common.CommonApi;
import com.purchase.zhecainet.purchaseshop.base.BaseFragment;
import com.purchase.zhecainet.purchaseshop.model.GoodsCategory;
import com.purchase.zhecainet.purchaseshop.model.SmsInfo;
import com.purchase.zhecainet.purchaseshop.net.ApiSubscriber;
import com.purchase.zhecainet.purchaseshop.net.HttpTransformer;
import com.purchase.zhecainet.purchaseshop.net.RetrofitClient;
import com.purchase.zhecainet.purchaseshop.utils.HeadUtils;
import com.purchase.zhecainet.purchaseshop.utils.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by leixiaoliang on 2017/7/26.
 * 邮箱：lxliang1101@163.com
 */

public class PickGoodsFragment extends BaseFragment {

    private static final String TAG = "PickGoodsFragment";
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.home_viewPager)
    ViewPager mViewPager;
    private Logger log = Logger.getLogger(TAG);
    private GoodsFragmentPagerAdapter myFragmentPagerAdapter;
    private Unbinder bind;
    List<GoodsCategory> categoryList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_pickgoods, null);
        bind = ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    private void initView(View view) {

        for (int i = 0; i < 10; i++) {
            GoodsCategory category = new GoodsCategory();
            category.setName("apple" + i);
            category.setIcon("http://t.img.i.hsuperior.com/80a388ed-93f5-44a0-8aa7-e65f0f8809f2");
            categoryList.add(category);
        }
        myFragmentPagerAdapter = new GoodsFragmentPagerAdapter(getActivity().getSupportFragmentManager(), categoryList);
        mViewPager.setAdapter(myFragmentPagerAdapter);
        //将TabLayout与ViewPager绑定在一起
        mTabLayout.setupWithViewPager(mViewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {
        for (int i = 0; i < 10; i++) {
            mTabLayout.getTabAt(i).setCustomView(getTabView(i));
        }
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_tab, null);
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setText(categoryList.get(position).getName());
        Uri uri = Uri.parse("http://t.img.i.hsuperior.com/80a388ed-93f5-44a0-8aa7-e65f0f8809f2");
        SimpleDraweeView draweeView = (SimpleDraweeView) view.findViewById(R.id.title_image_view);
        draweeView.setImageURI(uri);
        return view;
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
        if (bind != null) {
            bind.unbind();
        }
        if (categoryList != null) {
            categoryList.clear();
        }

    }
}
