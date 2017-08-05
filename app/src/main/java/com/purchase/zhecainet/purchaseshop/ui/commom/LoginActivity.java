package com.purchase.zhecainet.purchaseshop.ui.commom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.purchase.zhecainet.purchaseshop.R;
import com.purchase.zhecainet.purchaseshop.api.common.CommonApi;
import com.purchase.zhecainet.purchaseshop.base.BaseActivity;
import com.purchase.zhecainet.purchaseshop.model.LoginUserInfo;
import com.purchase.zhecainet.purchaseshop.model.UserInitInfo;
import com.purchase.zhecainet.purchaseshop.net.ApiSubscriber;
import com.purchase.zhecainet.purchaseshop.net.HttpTransformer;
import com.purchase.zhecainet.purchaseshop.net.RetrofitClient;
import com.purchase.zhecainet.purchaseshop.ui.MainHomeActivity;
import com.purchase.zhecainet.purchaseshop.utils.EditUtil;
import com.purchase.zhecainet.purchaseshop.utils.HeadUtils;
import com.purchase.zhecainet.purchaseshop.utils.UserUtil;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    private AutoCompleteTextView mPoneView;
    private EditText mPasswordView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setVisableActionBarOperate(false);
        setVisableActionBackBtn(false);
        setActionBarTitle(getString(R.string.login));
        mPoneView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
//                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        ImageButton mEmailSignInButton = (ImageButton) findViewById(R.id.login_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainHomeActivity.getIntent(LoginActivity.this));
//                attemptLogin();
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        TextView mForgetPasswordButton = (TextView) findViewById(R.id.forget_password);
        mForgetPasswordButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(FindPwdActivity.getIntent(LoginActivity.this));
            }
        });
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mPoneView.setError(null);
        mPasswordView.setError(null);
        // Store values at the time of the login attempt.
        String phone = mPoneView.getText().toString();
        String password = mPasswordView.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            showToast(getString(R.string.login_account_hint_demand));
            return;
        }
        if (!EditUtil.isMobileNum(phone)) {
            showToast(getString(R.string.login_account_right_phone_num));
            return;
        }

        if (TextUtils.isEmpty(password)) {
            showToast(getString(R.string.login_password_hint_demand));
            return;
        }

        if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            return;
        }

        doRequestLoginApi(phone, password);

    }

    /**
     * 用户登录
     *
     * @param phone
     * @param password
     */
    private void doRequestLoginApi(String phone, String password) {
        Map<String, String> paramsmap = new LinkedHashMap<>();
        paramsmap.put("account", phone);
        paramsmap.put("password", password);
//        paramsmap.put("lng", "");//经度
//        paramsmap.put("lat", "");//纬度
        String headVaule = HeadUtils.getAuthorization(paramsmap.toString());

        RetrofitClient.getInstance()
                .builder(CommonApi.class)
                .getUserLogin(headVaule, paramsmap)
                .compose(HttpTransformer.<LoginUserInfo>toTransformer())
                .subscribe(new ApiSubscriber<LoginUserInfo>() {
                    @Override
                    protected void onSuccess(LoginUserInfo bean) {
                        UserUtil.dealLoginResponse(bean);
                        getUserInitData();
                    }
                });
    }

    /**
     * 获取用户初始化信息
     */
    private void getUserInitData() {
        Map<String, String> paramsmap = new LinkedHashMap<>();
//        paramsmap.put("account", phone);
        String headVaule = HeadUtils.getAuthorization(paramsmap.toString());
       String userId=UserUtil.getUid();
        if(TextUtils.isEmpty(userId))return;
        RetrofitClient.getInstance()
                .builder(CommonApi.class)
                .getUserInfoInit(headVaule,userId)
                .compose(HttpTransformer.<UserInitInfo>toTransformer())
                .subscribe(new ApiSubscriber<UserInitInfo>() {
                    @Override
                    protected void onSuccess(UserInitInfo bean) {
                        UserUtil.userInfo.setName(bean.getName());
                        UserUtil.userInfo.setCode(bean.getCode());
                        UserUtil.userInfo.setPhone(bean.getPhone());
                        if(bean.getCollaborator()!=null){
                            UserUtil.userInfo.setDepartmnetId(bean.getCollaborator().getId());
                            UserUtil.userInfo.setDepartmnetName(bean.getCollaborator().getName());
                        }
                    }
                });
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 5;
    }
}

