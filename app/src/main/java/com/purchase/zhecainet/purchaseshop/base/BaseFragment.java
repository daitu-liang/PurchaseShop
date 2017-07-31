package com.purchase.zhecainet.purchaseshop.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.purchase.zhecainet.purchaseshop.utils.NetWorkUtil;

/**
 * Created by leixiaoliang on 2017/7/26.
 * 邮箱：lxliang1101@163.com
 */

public class BaseFragment extends Fragment {
    private Toast mToast;
    protected FragmentActivity mAct;
    protected boolean isDestroyView;
    @Override
    public void onAttach(Activity activity) {
        if (null != activity && activity instanceof FragmentActivity)
            mAct = (FragmentActivity)activity;
        super.onAttach(activity);
    }
    /**
     * 显示toast
     */
    protected void showToast(String msg) {
        if(!NetWorkUtil.isNetworkConnected(getActivity().getApplicationContext())) return;
        if (isDetached()) return;
        if (null == mToast) mToast = Toast.makeText(getActivity().getApplicationContext(), "", Toast.LENGTH_SHORT);
        mToast.setText(msg);
        mToast.show();
    }

    /**
     * 显示toast
     */
    protected void showToast(int msg) {
        if(!NetWorkUtil.isNetworkConnected(getActivity().getApplicationContext())) return;
        showToast(getString(msg));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isDestroyView = true;
    }

/*    public void initActionBar(View v) {
        View back = v.findViewById(R.id.btn_back);
        if (null != back) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                	if (CommonUtils.isFastDoubleClick()) {
            			return;
            		}
                    mAct.finish();
                }
            });
        }
    }*/

    public void startActivity(Intent intent) {
        mAct.startActivity(intent);
    }

    public int getResCoclor(int id) {
        return getResources().getColor(id);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

    }
}
