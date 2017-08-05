package com.purchase.zhecainet.purchaseshop.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.purchase.zhecainet.purchaseshop.R;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by leixiaoliang on 2016/11/18.
 */
public class FrescoImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //用fresco加载图片简单用法
        Uri uri = Uri.parse((String) path);
        imageView.setImageURI(uri);
    }
    @Override
    public ImageView createImageView(Context context) {
        SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);

        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                .setFadeDuration(300)
                .setFailureImage(context.getResources().getDrawable(R.drawable.ic_fail_pic))
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .build();
        simpleDraweeView.setHierarchy(hierarchy);

        return simpleDraweeView;
    }
}
