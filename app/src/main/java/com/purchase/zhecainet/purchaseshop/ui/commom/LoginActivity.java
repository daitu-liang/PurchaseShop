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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.purchase.zhecainet.purchaseshop.R;
import com.purchase.zhecainet.purchaseshop.base.BaseActivity;
import com.purchase.zhecainet.purchaseshop.ui.MainHomeActivity;
import com.purchase.zhecainet.purchaseshop.utils.EditUtil;


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

        Button mEmailSignInButton = (Button) findViewById(R.id.login_button);
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

    private void doRequestLoginApi(String phone, String password) {


    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 5;
    }
}

