package com.purchase.zhecainet.purchaseshop.ui.purchaseplan;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.purchase.zhecainet.purchaseshop.R;
import com.purchase.zhecainet.purchaseshop.api.pickgoods.PickGoodsApi;
import com.purchase.zhecainet.purchaseshop.api.purchaseplan.PurchasePlanApi;
import com.purchase.zhecainet.purchaseshop.base.BaseFragment;
import com.purchase.zhecainet.purchaseshop.model.GoodsItem;
import com.purchase.zhecainet.purchaseshop.model.PurchaseOrderInfo;
import com.purchase.zhecainet.purchaseshop.net.ApiSubscriber;
import com.purchase.zhecainet.purchaseshop.net.HttpTransformer;
import com.purchase.zhecainet.purchaseshop.net.RetrofitClient;
import com.purchase.zhecainet.purchaseshop.utils.HeadUtils;
import com.purchase.zhecainet.purchaseshop.utils.ListUtil;
import com.purchase.zhecainet.purchaseshop.utils.Logger;
import com.purchase.zhecainet.purchaseshop.utils.UserUtil;
import com.purchase.zhecainet.purchaseshop.widget.RatingBarView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by leixiaoliang on 2017/7/26.
 * 邮箱：lxliang1101@163.com
 */

public class PurchasePlanFragment extends BaseFragment {
    private static final String TAG = "PurchasePlanFragment";
    @BindView(R.id.tv_deparmnent)
    TextView tvDeparmnent;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_center_month)
    TextView tvCenterMonth;
    @BindView(R.id.plan_left_rb)
    RadioButton planLeftRb;
    @BindView(R.id.plan_right_rb)
    RadioButton planRightRb;
    @BindView(R.id.plan_rg)
    RadioGroup planRg;
    @BindView(R.id.tv_plan_catogry)
    TextView tvPlanCatogry;
    @BindView(R.id.tv_plan_center_state)
    TextView tvPlanCenterState;
    @BindView(R.id.tv_plan_weight)
    TextView tvPlanWeight;
    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerview;
    @BindView(R.id.collapsingtoolbarlayout)
    CollapsingToolbarLayout collapsingtoolbarlayout;
    @BindView(R.id.appbarlayout)
    AppBarLayout appbarlayout;
    private Logger log = Logger.getLogger(TAG);
    @BindView(R.id.calendarView)
    MaterialCalendarView mCalendarView;
    @BindView(R.id.confirm_get_goods_button)
    RelativeLayout confirmGetGoodsButton;

    List<GoodsItem> goodsList = new ArrayList<>();
    int selectType = 1;//采购类型  日常采购1 补货2
    int selectState = 0;// 0未下单，1下单中，2派单中，3配送员待提货中，4配送中，5已送达，待收货，6已收货，待评价，7完成
    String selectDate = "";
    private MultiItemTypeAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_purchase_plan, null);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    private void initView(View view) {
        //getPurchaseGoodsListData();
        if (UserUtil.userInfo != null) {
            tvDeparmnent.setText(UserUtil.userInfo.getDepartmnetName());
        }
        tvDeparmnent.setText("卡卡西手机");
        initBaseView();
        initDataCanlander();
        initData(null);

    }

    private void initBaseView() {

        List<GoodsItem> goodsListTest = goodsListData();
        goodsList.addAll(goodsListTest);
        if (ListUtil.isEmpty(goodsList)) return;

        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MultiItemTypeAdapter(getActivity(), goodsList);
        mAdapter.addItemViewDelegate(new MsgFirstItemDelagate());
        mAdapter.addItemViewDelegate(new MsgSecondItemDelagate());
        mAdapter.addItemViewDelegate(new MsgThreeItemDelagate());

        mRecyclerview.setAdapter(mAdapter);

        appbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                log.i(TAG, "verticalOffset=" + verticalOffset);
                if (verticalOffset < -300) {
                    tvCenterMonth.setVisibility(View.VISIBLE);
                } else {
                    tvCenterMonth.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


    private void initDataCanlander() {
        mCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)
                .setMinimumDate(CalendarDay.from(2016, 1, 1))
                .setMaximumDate(CalendarDay.from(2018, 12, 12))
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月");
        String format = sdf.format(date);
        tvMonth.setText(format);


        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy年M月d日");
        String formatday = sdfd.format(date);
        tvCenterMonth.setText(formatday + " " + getWeek(Calendar.getInstance()));

        SimpleDateFormat dateStrFormat = new SimpleDateFormat("yyyyMMdd");
        String dateStr = dateStrFormat.format(date);
        selectDate = dateStr;
        getPurchaseGoodsListData(selectType + "", dateStr);
        mCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                String formatDate = getDateMYD(date);
                log.d("", "onDateSelected-CalendarDay=" + date);
                log.d("", "onDateSelected-formatDate=" + formatDate);
                String dateStr = getDateStr(date);
                log.d("", "onDateSelected-dateStr=" + dateStr);
                selectDate = dateStr;
                getPurchaseGoodsListData(selectType + "", dateStr);
                setCurrentMonth(formatDate);

                Calendar calendar = date.getCalendar();
                tvCenterMonth.setText(formatDate + " " + getWeek(calendar));
            }
        });

        mCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                String formatDate = getDateMYD(date);
                log.d("", "setOnMonthChangedListener=" + date + "");
                log.d("", "setOnMonthChangedListener-formatDate=" + formatDate);
                setCurrentMonth(formatDate);

            }
        });
    }

    private void initData(PurchaseOrderInfo bean1) {
        PurchaseOrderInfo bean = new PurchaseOrderInfo();
        tvPlanCatogry.setText("商品种类：4/5" + bean.getTotal_goods());
        tvPlanCenterState.setText("正在下单");
        tvPlanWeight.setText("总重量：5421KG" + bean.getTotal_weight());
        int selectState = 0;// 0未下单，1下单中，2派单中，3配送员待提货中，4配送中，5已送达，待收货，6已收货，待评价，7完成
        if (selectState == 0) {
            tvPlanCenterState.setText("未下单");
        } else if (selectState == 1) {
            tvPlanCenterState.setText("下单中");
        } else if (selectState == 2) {
            tvPlanCenterState.setText("派单中");
        } else if (selectState == 3) {
            tvPlanCenterState.setText("配送员待提货中");
        } else if (selectState == 4) {
            tvPlanCenterState.setText("配送中");
        } else if (selectState == 5) {
            tvPlanCenterState.setText("已送达，待收货");
        } else if (selectState == 6) {
            tvPlanCenterState.setText("已收货，待评价");
        } else if (selectState == 7) {
            tvPlanCenterState.setText("完成");
        }
    }


    private void setCurrentMonth(String date) {
        tvMonth.setText(date);
        selectState = 1;
        confirmGetGoodsButton.setVisibility(View.GONE);
        mAdapter.notifyDataSetChanged();
    }

    private String getDateMYD(@NonNull CalendarDay date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日");
        return sdf.format(date.getDate());
    }

    private String getDateStr(@NonNull CalendarDay date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date.getDate());
    }

    @OnClick({R.id.plan_left_rb, R.id.plan_right_rb, R.id.confirm_get_goods_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.plan_left_rb:
                confirmGetGoodsButton.setVisibility(View.GONE);
                selectType = 1;//日常采购
                selectState = 1;
                getPurchaseGoodsListData(selectType + "", null);
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.plan_right_rb:
                confirmGetGoodsButton.setVisibility(View.VISIBLE);
                selectType = 2;//补货
                selectState = 5;
                getPurchaseGoodsListData(selectType + "", null);
                mAdapter.notifyDataSetChanged();
                break;

            case R.id.confirm_get_goods_button:
                startActivity(ConfirmReceiptActivity.getIntent(getActivity(), selectType + "", selectDate));
                break;
        }
    }

    public class MsgFirstItemDelagate implements ItemViewDelegate<GoodsItem> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_goods_multi;
        }

        @Override
        public boolean isForViewType(GoodsItem item, int position) {
            return selectState == 0;
        }

        @Override
        public void convert(ViewHolder holder, final GoodsItem info, int position) {
            Uri uri = Uri.parse("http://t.img.i.hsuperior.com/80a388ed-93f5-44a0-8aa7-e65f0f8809f2");
            SimpleDraweeView draweeView = (SimpleDraweeView) holder.getView(R.id.goods_image);
            draweeView.setImageURI(uri);
            holder.setOnClickListener(R.id.add_purchase, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    info.getGoods_sale_id();
                    addPurchase(info.getGoods_sale_id(), info.getTotal_number());
                }
            });
        }
    }

    public class MsgSecondItemDelagate implements ItemViewDelegate<GoodsItem> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_plan_update_num;
        }

        @Override
        public boolean isForViewType(GoodsItem item, int position) {
            return selectState == 1;
        }

        @Override
        public void convert(ViewHolder holder, final GoodsItem info, final int position) {
            Uri uri = Uri.parse("http://t.img.i.hsuperior.com/80a388ed-93f5-44a0-8aa7-e65f0f8809f2");
            SimpleDraweeView draweeView = (SimpleDraweeView) holder.getView(R.id.goods_image);
            draweeView.setImageURI(uri);
            holder.setText(R.id.goods_name_tv, info.getName());
            holder.setText(R.id.goods_num_weight_tv, "1箱/" + info.getTotal_weight() + "KG");
            holder.setText(R.id.goods_price, "￥ " + info.getPrice());
            holder.setText(R.id.tv_goods_num, info.getTotal_number());
            if (Integer.parseInt(info.getLack_number()) > 1) {
                holder.setBackgroundRes(R.id.tv_reduce, R.drawable.ic_down_num_gray);
            } else {
                holder.setBackgroundRes(R.id.tv_reduce, R.drawable.ic_down_num);
            }
            if (Integer.parseInt(info.getLack_number()) < 10) {
                holder.setBackgroundRes(R.id.tv_add, R.drawable.ic_add);
            } else {
                holder.setBackgroundRes(R.id.tv_add, R.drawable.ic_add_gray);
            }
            holder.setOnClickListener(R.id.tv_reduce, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reduceGoodsNum(info, position);
                }
            });
            holder.setOnClickListener(R.id.tv_add, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addGoodsNum(info, position);
                }
            });
        }
    }


    public class MsgThreeItemDelagate implements ItemViewDelegate<GoodsItem> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_plan_three;
        }

        @Override
        public boolean isForViewType(GoodsItem item, int position) {
            return selectState == 5;
        }

        @Override
        public void convert(ViewHolder holder, GoodsItem info, int position) {
            Uri uri = Uri.parse("http://t.img.i.hsuperior.com/80a388ed-93f5-44a0-8aa7-e65f0f8809f2");
            SimpleDraweeView draweeView = (SimpleDraweeView) holder.getView(R.id.goods_image);
            draweeView.setImageURI(uri);

            holder.setText(R.id.goods_name_tv, info.getName());
            holder.setText(R.id.goods_num_weight_tv, "1箱/" + info.getTotal_weight() + "KG");
            holder.setText(R.id.goods_desc_tv, "来自澳大利亚");
            holder.setText(R.id.goods_price, "￥ " + info.getPrice());
            TagGroup mTagGroup = holder.getView(R.id.goods_tag_group);
            mTagGroup.setTags(new String[]{"Tag1", "Tag2", "Tag3"});
            RatingBarView ratingBarView = holder.getView(R.id.rb);
            ratingBarView.setClickable(false);//设置可否点击
            ratingBarView.setStar(2.5f);//设置显示的星星个数
            holder.setText(R.id.goods_num, info.getTotal_number() + "份");

        }
    }

    /**
     * 获取采购单数据列表
     *
     * @param type
     * @param dateStr
     */
    private void getPurchaseGoodsListData(String type, String dateStr) {
        log.d("", "getPurchaseGoodsListData-type=" + type + "--------dateStr=" + dateStr);
        if (TextUtils.isEmpty(type) || TextUtils.isEmpty(dateStr)) return;
        Map<String, String> paramsmap = new LinkedHashMap<>();
        String userId = UserUtil.getUid();
        if (TextUtils.isEmpty(userId)) return;
        String collaborator_id = UserUtil.userInfo.getDepartmnetId();
        if (TextUtils.isEmpty(collaborator_id)) return;
        paramsmap.put("type", type);
        paramsmap.put("purchase_date", dateStr);
        String headVaule = HeadUtils.getAuthorization(paramsmap.toString());
        RetrofitClient.getInstance()
                .builder(PurchasePlanApi.class)
                .getPurchaseOrderQuery(headVaule, userId, collaborator_id)
                .compose(HttpTransformer.<PurchaseOrderInfo>toTransformer())
                .subscribe(new ApiSubscriber<PurchaseOrderInfo>() {
                    @Override
                    protected void onSuccess(PurchaseOrderInfo bean) {
                        initData(bean);
                        selectState = Integer.parseInt(bean.getStatus());
                        if (goodsList != null && !ListUtil.isEmpty(bean.getGoods())) {
                            goodsList.clear();
                            goodsList.addAll(bean.getGoods());
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    /**
     * 加num
     *
     * @param info
     * @param position
     */
    private void addGoodsNum(GoodsItem info, int position) {
        String gooodsNum = info.getTotal_number();
        String goodsSaleId = info.getGoods_sale_id();
        int goodAddNun = Integer.parseInt(gooodsNum) + 1;
        purchaseOrderModify(goodsSaleId, goodAddNun + "");
    }

    /**
     * 减num
     *
     * @param info
     * @param position
     */
    private void reduceGoodsNum(GoodsItem info, int position) {
        String gooodsNum = info.getTotal_number();
        String goodsSaleId = info.getGoods_sale_id();
        int goodAddNun = Integer.parseInt(gooodsNum) - 1;
        purchaseOrderModify(goodsSaleId, goodAddNun + "");
    }

    /**
     * 采购单商品修改
     *
     * @param goodsSaleId
     * @param gooodsNum
     */
    private void purchaseOrderModify(String goodsSaleId, String gooodsNum) {
        Map<String, String> paramsmap = new LinkedHashMap<>();
        String userId = UserUtil.getUid();
        if (TextUtils.isEmpty(userId)) return;
        String collaborator_id = UserUtil.userInfo.getDepartmnetId();
        if (TextUtils.isEmpty(collaborator_id)) return;
        paramsmap.put("goods_sale_id", goodsSaleId);
        paramsmap.put("number", gooodsNum);
        String headVaule = HeadUtils.getAuthorization(paramsmap.toString());
        RetrofitClient.getInstance()
                .builder(PurchasePlanApi.class)
                .getPurchaseOrderModify(headVaule, userId, collaborator_id, gooodsNum)
                .compose(HttpTransformer.<PurchaseOrderInfo>toTransformer())
                .subscribe(new ApiSubscriber<PurchaseOrderInfo>() {
                    @Override
                    protected void onSuccess(PurchaseOrderInfo bean) {
                        if (goodsList != null && !ListUtil.isEmpty(bean.getGoods())) {
                            goodsList.clear();
                            goodsList.addAll(bean.getGoods());
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    /**
     * 加入采购
     *
     * @param goodsId
     * @param goodsNum
     */
    private void addPurchase(String goodsId, String goodsNum) {
        String userId = UserUtil.getUid();
        if (TextUtils.isEmpty(userId)) return;
        String collaborator_id = UserUtil.userInfo.getDepartmnetId();
        if (TextUtils.isEmpty(collaborator_id)) return;
        Map<String, String> paramsmap = new HashMap<>();
        paramsmap.put("goods_sale_id", goodsId);
        paramsmap.put("number", goodsNum);
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


    private List<GoodsItem> goodsListData() {
        List<GoodsItem> goodsList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            GoodsItem info = new GoodsItem();
            info.setName("渴了鸡排" + i);
            info.setTotal_number("7");
            info.setTotal_weight("41");
            info.setTrace_code("51454561515");
            info.setLack_number(i + "");
            info.setPrice("458");
            goodsList.add(info);
        }
        return goodsList;
    }

    /*获取星期几*/
    public static String getWeek(Calendar calendar) {
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        switch (i) {
            case 1:
                return "星期日";
            case 2:
                return "星期一";
            case 3:
                return "星期二";
            case 4:
                return "星期三";
            case 5:
                return "星期四";
            case 6:
                return "星期五";
            case 7:
                return "星期六";
            default:
                return "";
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (goodsList != null) {
            goodsList.clear();
            goodsList = null;
        }
    }
}
