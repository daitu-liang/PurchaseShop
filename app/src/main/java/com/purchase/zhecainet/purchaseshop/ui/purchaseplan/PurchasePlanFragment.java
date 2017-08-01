package com.purchase.zhecainet.purchaseshop.ui.purchaseplan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.purchase.zhecainet.purchaseshop.R;
import com.purchase.zhecainet.purchaseshop.api.common.CommonApi;
import com.purchase.zhecainet.purchaseshop.base.BaseFragment;
import com.purchase.zhecainet.purchaseshop.model.VersionInfo;
import com.purchase.zhecainet.purchaseshop.net.ApiSubscriber;
import com.purchase.zhecainet.purchaseshop.net.HttpTransformer;
import com.purchase.zhecainet.purchaseshop.net.RetrofitClient;
import com.purchase.zhecainet.purchaseshop.utils.HeadUtils;
import com.purchase.zhecainet.purchaseshop.utils.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    private Logger log = Logger.getLogger(TAG);
    @BindView(R.id.calendarView)
    MaterialCalendarView mCalendarView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_purchase_plan, null);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tvDeparmnent.setText("卡卡西手机");
        mCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)
                .setMinimumDate(CalendarDay.from(2016, 1, 1))
                .setMaximumDate(CalendarDay.from(2018, 12, 12))
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月");
        String format = sdf.format(new Date());
        tvMonth.setText(format);
        
        mCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日");
                String format1 = sdf.format(date.getDate());
                log.d("MainActivity", format1);
                setCurrentMonth(date);
            }
        });

        mCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                setCurrentMonth(date);
            }
        });
    }

    private void setCurrentMonth(CalendarDay date) {
    }

    private void getPickGoodsData() {
        Map<String, String> paramsmap = new LinkedHashMap<>();
        paramsmap.put("version", "1.0.0");
        paramsmap.put("package", "com.purchase.zhecainet.purchaseshop");
        paramsmap.put("client_type", "3");
        String headVaule = HeadUtils.getAuthorization(paramsmap.toString());
        RetrofitClient.getInstance()
                .builder(CommonApi.class)
                .getVersionInfo(headVaule, paramsmap)
                .compose(HttpTransformer.<VersionInfo>toTransformer())
                .subscribe(new ApiSubscriber<VersionInfo>() {
                    @Override
                    protected void onSuccess(VersionInfo bean) {
                        log.e(TAG, "bean=" + bean.toString());

                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
