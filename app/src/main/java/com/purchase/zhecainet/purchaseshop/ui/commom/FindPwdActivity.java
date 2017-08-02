package com.purchase.zhecainet.purchaseshop.ui.commom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.purchase.zhecainet.purchaseshop.R;
import com.purchase.zhecainet.purchaseshop.base.BaseActivity;
import com.purchase.zhecainet.purchaseshop.utils.CommonUtils;
import com.purchase.zhecainet.purchaseshop.utils.EditUtil;

import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindPwdActivity extends BaseActivity {
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, FindPwdActivity.class);
        return intent;
    }
    @BindView(R.id.find_phone_et)
    EditText findPhoneEt;
    @BindView(R.id.find_pwd_et)
    EditText findPwdEt;
    @BindView(R.id.check_box)
    CheckBox findPwdShow;
    @BindView(R.id.regist_code_et)
    EditText registCodeEt;
    @BindView(R.id.regist_getcode_btn)
    Button getCodeButn;
    @BindView(R.id.subimt_button)
    RelativeLayout subimtButton;
    private static final int MSG_CACULATE_TIME = 1;
    private boolean isCodeCDTime = true;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_CACULATE_TIME) {
                int time = msg.getData().getInt("wait_time");
                if (time > 0) {
                    getCodeButn.setEnabled(false);
                    getCodeButn.setText(getString(R.string.login_wait_hint,
                            time));
                } else {
                    isCodeCDTime = true;
                    getCodeButn.setEnabled(true);
                    getCodeButn.setText(R.string.login_sendverifycode);
                }
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        setVisableActionBackBtn(true);
        setVisableActionBarOperate(false);
        setActionBarTitle(getString(R.string.forget_password));
        findPwdShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    //如果选中，显示密码
                    findPwdEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    findPwdEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    @OnClick({R.id.regist_getcode_btn, R.id.subimt_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.regist_getcode_btn:
                getCode();
                break;
            case R.id.subimt_button:
                subimt();
                break;
        }
    }


    private void getCode() {
        if (CommonUtils.isFastDoubleClick()) {
            return;
        }
        String telePhoneNum = EditUtil.getDataBtEdit(findPhoneEt);
        if (TextUtils.isEmpty(telePhoneNum)) {
            showToast(getString(R.string.login_account_hint_phone_num));
            return;
        }
        if (!EditUtil.isMobileNum(telePhoneNum)) {
            showToast(getString(R.string.login_account_right_phone_num));
            return;
        }
        getVerifyCode(telePhoneNum);
    }

    private void getVerifyCode(String telePhoneNum) {

        isCodeCDTime = false;
        caculateWaitTime();

    }

    private void subimt() {
        String telePhoneVaule = EditUtil.getDataBtEdit(findPhoneEt);
        String findPwdEtVaule = EditUtil.getDataBtEdit(findPwdEt);
        String registCodeEtVaule = EditUtil.getDataBtEdit(registCodeEt);
        if (TextUtils.isEmpty(telePhoneVaule)) {
            showToast(getString(R.string.login_account_hint_phone_num));
            return;
        }
        if (!EditUtil.isMobileNum(telePhoneVaule)) {
            showToast(getString(R.string.login_account_right_phone_num));
            return;
        }
        if (TextUtils.isEmpty(findPwdEtVaule)) {
            showToast(getString(R.string.login_password_hint_demand));
            return;
        }

        if (!isPasswordValid(findPwdEtVaule)) {
            showToast(getString(R.string.error_invalid_password));
            return;
        }
        if (TextUtils.isEmpty(registCodeEtVaule)) {
            showToast(getString(R.string.login_input_code));
            return;
        }

        Map<String, String> paramsmap = new LinkedHashMap<>();
        paramsmap.put("account",telePhoneVaule);
        paramsmap.put("password",findPwdEtVaule);
        paramsmap.put("verify_code",registCodeEtVaule);
        paramsmap.put("tran_id ","tran_id");
        showToast(paramsmap.toString());

    }
    private void caculateWaitTime() {
        new Thread() {

            @Override
            public void run() {
                int startTime = 60;
                while (!isCodeCDTime) {
                    Message msg = new Message();
                    msg.what = MSG_CACULATE_TIME;
                    Bundle data = new Bundle();
                    data.putInt("wait_time", startTime);
                    msg.setData(data);
                    mHandler.sendMessage(msg);
                    if (startTime <= 0) {
                        isCodeCDTime = true;
                    }
                    try {
                        sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    startTime--;
                }
            }
        }.start();
    }
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 5;
    }
}
