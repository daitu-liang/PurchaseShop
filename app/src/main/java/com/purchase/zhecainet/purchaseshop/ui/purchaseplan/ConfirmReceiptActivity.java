package com.purchase.zhecainet.purchaseshop.ui.purchaseplan;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.purchase.zhecainet.purchaseshop.R;
import com.purchase.zhecainet.purchaseshop.base.BaseActivity;
import com.purchase.zhecainet.purchaseshop.model.GoodsItem;
import com.purchase.zhecainet.purchaseshop.model.PurchaseOrderInfo;
import com.purchase.zhecainet.purchaseshop.utils.ListUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfirmReceiptActivity extends BaseActivity {
    @BindView(R.id.tv_plan_date)
    TextView tvPlanDate;
    @BindView(R.id.tv_plan_state)
    TextView tvPlanState;
    @BindView(R.id.tv_plan_catogry)
    TextView tvPlanCatogry;
    @BindView(R.id.tv_plan_weight)
    TextView tvPlanWeight;
    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerview;

    @BindView(R.id.confirm_receipt_button)
    RelativeLayout confirmReceiptButton;
    @BindView(R.id.parent)
    LinearLayout parent;

    private CommonAdapter<GoodsItem> mAdapter;
    private View dailogView;
    private PopupWindow mPopupView;

    public static Intent getIntent(Context context, String type, String purchase_date) {
        Intent intent = new Intent(context, ConfirmReceiptActivity.class);
        intent.putExtra("purchase_date_key", purchase_date);
        intent.putExtra("type_key", type);
        return intent;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_receipt);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {

        setVisableActionBackBtn(true);
        setVisableActionBarOperate(false);
        setActionBarTitle(getString(R.string.title_confirm_receipt));
        String mType = getIntent().getStringExtra("type_key");
        String mDate = getIntent().getStringExtra("purchase_date_key");//格式“yyyymmdd”
        getDate(mType, mDate);
        confirmReceiptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmReceiptDialog();
            }
        });
        PurchaseOrderInfo purchaseItemInfo = new PurchaseOrderInfo();
        purchaseItemInfo.setPurchase_date("2017年4月5日  星期三");
        purchaseItemInfo.setTotal_weight("5454");
        purchaseItemInfo.setTotal_goods("55");
        purchaseItemInfo.setStatus("正在配送");
        initDate(purchaseItemInfo);
    }


    private void getDate(String mType, String mDate) {
        if (TextUtils.isEmpty(mType) || TextUtils.isEmpty(mDate)) return;
        PurchaseOrderInfo purchaseItemInfo = new PurchaseOrderInfo();
        initDate(purchaseItemInfo);
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
            goodsList.add(info);
        }
        return goodsList;
    }

    private void initDate(PurchaseOrderInfo info) {

        if (info == null) return;


        tvPlanDate.setText(info.getPurchase_date());
        tvPlanState.setText(info.getStatus());
//        tvPlanCatogry.setText("商品种类："+info.getTotal_goods());
        tvPlanWeight.setText("总重量：" + info.getTotal_weight());
//        List<GoodsItem> goodsList = info.getGoods();
        List<GoodsItem> goodsList = goodsListData();
        if (ListUtil.isEmpty(goodsList)) return;
        tvPlanCatogry.setText("商品种类：" + goodsList.size() + "/" + info.getTotal_goods());
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CommonAdapter<GoodsItem>(this, R.layout.item_purchase_receipt, goodsList) {
            @Override
            protected void convert(ViewHolder holder, GoodsItem info, int position) {

                Uri uri = Uri.parse("http://t.img.i.hsuperior.com/80a388ed-93f5-44a0-8aa7-e65f0f8809f2");
                SimpleDraweeView draweeView = (SimpleDraweeView) holder.getView(R.id.goods_image);
                draweeView.setImageURI(uri);

                holder.setText(R.id.goods_name_tv, info.getName());
                holder.setText(R.id.goods_num_weight_tv, "1箱/" + info.getTotal_weight() + "KG");
                holder.setText(R.id.goods_desc_tv, info.getTrace_code());
                holder.setText(R.id.goods_price, info.getTotal_number() + "份");
                holder.setText(R.id.tv_empty_goods, "缺货" + info.getLack_number());
                if (Integer.parseInt(info.getLack_number()) > 0) {
                    holder.setBackgroundRes(R.id.tv_empty_goods, R.drawable.ic_empty_solid_goods);
                    holder.setTextColorRes(R.id.tv_empty_goods, R.color.milk_white);
                } else {
                    holder.setBackgroundRes(R.id.tv_empty_goods, R.drawable.ic_empty_goods);
                    holder.setTextColorRes(R.id.tv_empty_goods, R.color.tip_bg_red);
                }
            }
        };
        mRecyclerview.setAdapter(mAdapter);
    }

    private void confirmReceiptDialog() {
        if (mPopupView == null) {
            dailogView = getLayoutInflater().inflate(R.layout.pop_purchase_receipt, null, false);
            mPopupView = new PopupWindow(dailogView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mPopupView.setBackgroundDrawable(new ColorDrawable(0));
            mPopupView.setAnimationStyle(android.R.style.Animation_Dialog);
            mPopupView.setTouchable(true);
            mPopupView.setFocusable(true);
            mPopupView.setOutsideTouchable(false);
//        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
            //实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0x00000000);
            //设置SelectPicPopupWindow弹出窗体的背景
            mPopupView.setBackgroundDrawable(dw);
        }
        backgroundAlpha(0.5f);
        mPopupView.update();
        mPopupView.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);

            }
        });
        SimpleDraweeView goodsImage = (SimpleDraweeView) dailogView.findViewById(R.id.goods_image);
        TextView goodsNameTv = (TextView) dailogView.findViewById(R.id.goods_name_tv);
        TextView goodsWeightTv = (TextView) dailogView.findViewById(R.id.goods_num_weight_tv);
        TextView goodsNum = (TextView) dailogView.findViewById(R.id.goods_price);

        Uri uri = Uri.parse("http://t.img.i.hsuperior.com/80a388ed-93f5-44a0-8aa7-e65f0f8809f2");
        goodsImage.setImageURI(uri);
        goodsNameTv.setText("澳大利亚牛排");
        goodsWeightTv.setText("1箱/12KG");
        goodsNum.setText("7件");

        TextView tv_cancel = (TextView) dailogView.findViewById(R.id.tv_cancel);
        TextView tv_confirm = (TextView) dailogView.findViewById(R.id.tv_confirm);


        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupView.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              showToast("yes");
            }
        });
        mPopupView.showAtLocation(parent, Gravity.CENTER, 0, 0);
}
    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
       getWindow().setAttributes(lp);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
