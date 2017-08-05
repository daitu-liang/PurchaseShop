package com.purchase.zhecainet.purchaseshop.ui.pickgoods;

import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.purchase.zhecainet.purchaseshop.R;
import com.purchase.zhecainet.purchaseshop.api.pickgoods.PickGoodsApi;
import com.purchase.zhecainet.purchaseshop.base.BaseFragment;
import com.purchase.zhecainet.purchaseshop.model.GoodsCategory;
import com.purchase.zhecainet.purchaseshop.model.GoodsQuerySearchListInfo;
import com.purchase.zhecainet.purchaseshop.model.GoodsSaleListInfo;
import com.purchase.zhecainet.purchaseshop.net.ApiSubscriber;
import com.purchase.zhecainet.purchaseshop.net.HttpTransformer;
import com.purchase.zhecainet.purchaseshop.net.RetrofitClient;
import com.purchase.zhecainet.purchaseshop.utils.HeadUtils;
import com.purchase.zhecainet.purchaseshop.utils.ListUtil;
import com.purchase.zhecainet.purchaseshop.utils.Logger;
import com.purchase.zhecainet.purchaseshop.utils.UserUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

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
    @BindView(R.id.goods_search_et)
    TextView goodsSearchEt;
    private Logger log = Logger.getLogger(TAG);
    private GoodsFragmentPagerAdapter myFragmentPagerAdapter;
    private Unbinder bind;
    List<GoodsCategory> categoryList = new ArrayList<>();
    List<GoodsQuerySearchListInfo> categorySearchList = new ArrayList<>();
    private View searchView;
    private PopupWindow mPopupView;
    private CommonAdapter<GoodsQuerySearchListInfo> mSearchAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_pickgoods, null);
        bind = ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    private void initView(View view) {

        getGoodsCategoryData();
        intData();
        myFragmentPagerAdapter = new GoodsFragmentPagerAdapter(getChildFragmentManager(), categoryList);
        mViewPager.setAdapter(myFragmentPagerAdapter);
        //将TabLayout与ViewPager绑定在一起
        mTabLayout.setupWithViewPager(mViewPager);
        setupTabIcons();
        goodsSearchEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchPop();
            }
        });

    }



    /**
     * 搜索
     */
    private void showSearchPop() {

        if (mPopupView == null) {
            searchView = getActivity().getLayoutInflater().inflate(R.layout.pop_search_goods_list, null, false);
            mPopupView = new PopupWindow(searchView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            mPopupView.setBackgroundDrawable(new ColorDrawable(0));
            mPopupView.setAnimationStyle(android.R.style.Animation_InputMethod);
            mPopupView.setTouchable(true);
            mPopupView.setFocusable(true);
            mPopupView.setOutsideTouchable(true);
//        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
            //实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0x00000000);
            //设置SelectPicPopupWindow弹出窗体的背景
            mPopupView.setBackgroundDrawable(dw);
        }
//        backgroundAlpha(0.5f);
        mPopupView.update();
        mPopupView.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);

            }
        });
        RecyclerView mRecyclerView = (RecyclerView) searchView.findViewById(R.id.recyclerview);
        EditText popSearchEt = (EditText) searchView.findViewById(R.id.pop_search_et);

        LinearLayout popbg = (LinearLayout) searchView.findViewById(R.id.bg_pop);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        popSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchDataByKey(s.toString());

            }

        });

         mSearchAdapter = new CommonAdapter<GoodsQuerySearchListInfo>(getActivity(), R.layout.item_search, categorySearchList) {
            @Override
            protected void convert(ViewHolder holder, GoodsQuerySearchListInfo goodsQueryListInfo, int position) {
                holder.setText(R.id.section_labe_tv, goodsQueryListInfo.getName());

                if (getcheckItemPosition() != -1) {
                    if (getcheckItemPosition() == position) {
                        holder.setTextColor(R.id.section_labe_tv, R.color.btn_green_bg);
                    } else {
                        holder.setTextColor(R.id.section_labe_tv, R.color.text_dark);
                    }
                }
            }

            private int getcheckItemPosition() {
                return checkItemPosition;
            }

            private int checkItemPosition = 0;

            public void setCheckItem(int position) {
                checkItemPosition = position;
                notifyDataSetChanged();
            }

        };
        mRecyclerView.setAdapter(mSearchAdapter);
        mSearchAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                String goodsId = categorySearchList.get(position).getId();
                String goodsName = categorySearchList.get(position).getName();
                getPickGoodsListData(goodsId,goodsName);
                mPopupView.dismiss();

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        mPopupView.showAsDropDown(mTabLayout);
        popbg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupView.dismiss();
            }
        });
    }
    /**
     * 获取商品分类
     */
    private void getGoodsCategoryData() {

        Map<String, String> paramsmap = new LinkedHashMap<>();
//        paramsmap.put("account", phone);
        String headVaule = HeadUtils.getAuthorization(paramsmap.toString());
        String userId= UserUtil.getUid();
        if(TextUtils.isEmpty(userId))return;
        RetrofitClient.getInstance()
                .builder(PickGoodsApi.class)
                .getGoodsCategoryQuery(headVaule)
                .compose(HttpTransformer.<List<GoodsCategory>>toTransformer())
                .subscribe(new ApiSubscriber<List<GoodsCategory>>() {
                    @Override
                    protected void onSuccess(List<GoodsCategory> bean) {
                        if(categoryList!=null){
                            categoryList.addAll(bean);
                            myFragmentPagerAdapter.setListType(categoryList);
                            setupTabIcons();
                            myFragmentPagerAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
    /**
     * 调搜索
     * @param keyValue
     */
    private void searchDataByKey(String keyValue) {
        if(TextUtils.isEmpty(keyValue))return;
        String userId = UserUtil.getUid();
        if(TextUtils.isEmpty(userId))return;
        Map<String, String> paramsmap = new LinkedHashMap<>();
        paramsmap.put("keyword", keyValue);
        String headVaule = HeadUtils.getAuthorization(paramsmap.toString());
        RetrofitClient.getInstance()
                .builder(PickGoodsApi.class)
                .getGoodsListSearchQuery(headVaule,userId,paramsmap)
                .compose(HttpTransformer.<List<GoodsQuerySearchListInfo>>toTransformer())
                .subscribe(new ApiSubscriber<List<GoodsQuerySearchListInfo>>() {
                    @Override
                    protected void onSuccess(List<GoodsQuerySearchListInfo> bean) {

                        if(ListUtil.isEmpty(bean)){
                            categorySearchList.addAll(bean);
                            mSearchAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    /**
     * 获取商品列表数据
     * @param goodsId
     * @param goodsName
     */
    private void getPickGoodsListData(String goodsId, String goodsName) {
        String userId= UserUtil.getUid();
        if(TextUtils.isEmpty(userId))return;
        Map<String, String> paramsmap = new LinkedHashMap<>();
        paramsmap.put("category_id", goodsId);
        paramsmap.put("keyword", goodsName);
        String headVaule = HeadUtils.getAuthorization(paramsmap.toString());
        RetrofitClient.getInstance()
                .builder(PickGoodsApi.class)
                .getGoodsSaleListQuery(headVaule,userId,paramsmap)
                .compose(HttpTransformer.<List<GoodsSaleListInfo>>toTransformer())
                .subscribe(new ApiSubscriber<List<GoodsSaleListInfo>>() {
                    @Override
                    protected void onSuccess(List<GoodsSaleListInfo> bean) {
                        if(!ListUtil.isEmpty(bean)){

                        }
                    }
                });
    }
    private void intData() {
        for (int i = 0; i < 15; i++) {
            GoodsQuerySearchListInfo categoryInfo = new GoodsQuerySearchListInfo();
            categoryInfo.setName("西红柿" + i);
            categoryInfo.setId(i + "");
            categorySearchList.add(categoryInfo);
        }

        for (int i = 0; i < 10; i++) {
            GoodsCategory category = new GoodsCategory();
            category.setName("apple" + i);
            category.setIcon("http://t.img.i.hsuperior.com/80a388ed-93f5-44a0-8aa7-e65f0f8809f2");
            categoryList.add(category);
        }
    }

    /**
     * 初始化tab数据
     */
    private void setupTabIcons() {
        for (int i = 0; i < categoryList.size(); i++) {
            mTabLayout.getTabAt(i).setCustomView(getTabView(i));
        }
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_tab, null);
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setText(categoryList.get(position).getName());
        Uri uri = Uri.parse(categoryList.get(position).getIcon());
        SimpleDraweeView draweeView = (SimpleDraweeView) view.findViewById(R.id.title_image_view);
        draweeView.setImageURI(uri);
        return view;
    }



    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (bind != null) {
            bind.unbind();
        }
        if (categoryList != null) {
            categoryList.clear();
            categoryList=null;
        }
        if(categorySearchList!=null){
            categorySearchList.clear();
            categorySearchList=null;
        }

    }
}
