package com.purchase.zhecainet.purchaseshop.ui.pickgoods;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.facebook.drawee.view.SimpleDraweeView;
import com.purchase.zhecainet.purchaseshop.R;
import com.purchase.zhecainet.purchaseshop.model.GoodsSaleListInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.purchase.zhecainet.purchaseshop.R.id.goods_image;

/**
 * Created by leixiaoliang on 2017/5/13.
 * 邮箱：lxliang1101@163.com
 */

public class GoodsAdpater extends RecyclerView.Adapter<GoodsAdpater.ViewHolder> {
    private static final int TYPE_TYPE6 = 1;
    private static final int TYPE_TYPE2 = 2;
    private static final int TYPE_TYPE3 = 3;

    private Context context;
    private List<GoodsSaleListInfo> list;
    private GridviewOnClickListener listener;

    public interface GridviewOnClickListener {
        void OnItemListClickListener(int position);

        void OnItemAddClickListener(int position);
    }

    public GoodsAdpater(Context context, List<GoodsSaleListInfo> list) {
        this.list = list;
        this.context = context;
    }

    public void setGridviewOnClickListener(GridviewOnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_goods_multi, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String url = list.get(position).getPhoto();
        if (!TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            holder.goodsImage.setImageURI(uri);
        }

        holder.addPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.OnItemAddClickListener(position);
                }
            }
        });
        holder.goodsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.OnItemListClickListener(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        Log.i("GetFight", "getItemViewType-position=" + position);
        if (position == 1) {
            return TYPE_TYPE6;
        } else if (2 <= position && position <= 7) {
            return TYPE_TYPE3;
        } else if (8 <= position && position <= 9) {
            return TYPE_TYPE3;
        } else if (position == 10) {
            return TYPE_TYPE6;
        } else if (11 <= position && position <= 16) {
            return TYPE_TYPE3;
        } else if (position == 17) {
            return TYPE_TYPE6;
        } else if (18 <= position && position <= 27) {
            return TYPE_TYPE3;
        } else {
            return TYPE_TYPE3;
        }
    }

    //getSpanSize方法，返回值就表示当前item占多少列，
    // 例如如果我们列数设置的为3列，返回3就表示铺满，也就是和列表一样了
    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    Log.i("GetFight", "type===" + type + "position=" + position);
                    switch (type) {
                        case TYPE_TYPE6:
                            return 6;
                        case TYPE_TYPE2:
                            return 2;
                        case TYPE_TYPE3:
                            return 3;
                        default:
                            return 3;
                    }
                }
            });
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(goods_image)
        SimpleDraweeView goodsImage;
        @BindView(R.id.add_purchase)
        ImageButton addPurchase;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
