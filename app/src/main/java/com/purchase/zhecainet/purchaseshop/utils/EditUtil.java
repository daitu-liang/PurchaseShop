package com.purchase.zhecainet.purchaseshop.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.regex.Pattern;

public class EditUtil {

	public static final String EMAIL_REGEX = "^[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.([A-Za-z]{2,})$";
	public static final String MOBILE_REGEX = "^1\\d{10}$";

	public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
	public static final Pattern MOBILE_PATTERN = Pattern.compile(MOBILE_REGEX);
	/**
	 * 正则表达式：验证身份证
	 */
	public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
	/**
	 * 判断是否为手机号码
	 */
	public static boolean isMobileNum(String mobiles) {
		return MOBILE_PATTERN.matcher(mobiles).matches();
	}
	/**
	 * 校验身份证
	 *
	 * @param idCard
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isIDCard(String idCard) {
		return Pattern.matches(REGEX_ID_CARD, idCard);
	}
	/**
	 * 判断邮箱是否正确
	 */
	public static boolean isEmail(String mobiles) {
		return EMAIL_PATTERN.matcher(mobiles).matches();
	}

	/**
	 * 判断"编辑框"是否有数据
	 */
	public static boolean haveDate(EditText editText) {
		if (!TextUtils.isEmpty(editText.getText().toString().trim())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取"编辑框"里面的数据
	 */
	public static String getDataBtEdit(EditText editText) {
		if (!TextUtils.isEmpty(editText.getText().toString().trim())) {
			return editText.getText().toString().trim();
		} else {
			return "";
		}
	}

	/**
	 * 隐藏输入法
	 */
	public static void hideMethod(Context context, EditText editText) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}

	/**
	 * 显示输入法
	 */
	public static void showKeyboard(Context context, EditText editText) {
		editText.requestFocus();
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(editText, InputMethodManager.RESULT_UNCHANGED_SHOWN);
	}
}
