package com.purchase.zhecainet.purchaseshop.ui.pickgoods;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.purchase.zhecainet.purchaseshop.model.GoodsCategory;

import java.util.List;


public class GoodsFragmentPagerAdapter extends FragmentPagerAdapter {


    private List<GoodsCategory> listType;

    public List<GoodsCategory> getListType() {
        return listType;
    }

    public void setListType(List<GoodsCategory> listType) {
        this.listType = listType;
    }

    public GoodsFragmentPagerAdapter(FragmentManager fm, List<GoodsCategory> listType) {
        super(fm);
        this.listType = listType;
    }

    @Override
    public Fragment getItem(int position) {

        if(listType.size()>0){
            int size=listType.size();
        }
        if (listType != null && listType.size() > 0 && listType.get(position) != null) {
            GoodsCategory typeInfo = listType.get(position);
            Fragment fg = null;
            if (fg == null) {

            }
            if (position == 1||position == 0) {
                fg = new GoodsListMultiFragment();
            }else {
                fg = new GoodsListSingleFragment();
            }

            Bundle bundle = new Bundle();
            bundle.putSerializable("category_url", typeInfo.getIcon());
            bundle.putSerializable("category_name", typeInfo.getName());
            fg.setArguments(bundle);
            return fg;
        }
        return null;
    }

    @Override
    public int getCount() {
        if (listType != null) {
            return listType.size();
        } else {
            return 0;
        }
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        if (listType != null && listType.size() > 0) {
            return listType.get(position).getName();
        } else {
            return "";
        }
    }
}
