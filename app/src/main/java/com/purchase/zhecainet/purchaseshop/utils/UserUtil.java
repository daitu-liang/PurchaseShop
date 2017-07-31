package com.purchase.zhecainet.purchaseshop.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.purchase.zhecainet.purchaseshop.app.PurchaseApplication;
import com.purchase.zhecainet.purchaseshop.config.Constant;
import com.purchase.zhecainet.purchaseshop.model.LoginResponseBean;
import com.purchase.zhecainet.purchaseshop.model.UserInfo;

/**
 * Created by leixiaoliang on 2017/4/13.
 * 邮箱：lxliang1101@163.com
 */
public class UserUtil {
	private static Logger log = Logger.getLogger("UserUtil");
	// 用户信息
	public static UserInfo userInfo=null;

	public static String getUid() {
		if (hasLogin()) {
			return userInfo.getU_guid();
		}
		return null;
	}

	/**
	 * 判断是否登录
	 * 
	 * @return true登录 | false未登录
	 */
	public static boolean hasLogin() {
		if (userInfo == null) {
			log.i("RGBH","hasLogin-userInfo="+userInfo);
			initUserInfo();
		}
		boolean is = (null != userInfo) && !(TextUtils.isEmpty(userInfo.getU_guid()));
		log.i("RGBH","用户是否登录成功="+is);
		return (null != userInfo && !TextUtils.isEmpty(userInfo.getU_guid()));
	}

	/**
	 * 保存用户数据
	 */
	public static void saveUserInfo() {
		try {
			log.i("RGBH","保存用户数据getU_guid="+userInfo.getU_guid());
			String userJson = new Gson().toJson(userInfo);
			FileUtil.writeFile(PurchaseApplication.CONTEXT,
					Constant.FileName.USER_INFO, userJson);
		} catch (Exception e) {
		}
		initUserInfo();
	}

	/**
	 * 保存并处理登录信息
	 * 
	 * @param data
	 */
	public static void dealLoginResponse(LoginResponseBean data) {

		try {

				if(userInfo==null){
					userInfo=new UserInfo();
				}
			UserUtil.userInfo.setU_guid(data.getU_guid());


		} catch (Exception e) {
			e.printStackTrace();
		}
		UserUtil.saveUserInfo();
	}



	private static void initUserInfo() {
		String userInfoText = FileUtil.readFile(PurchaseApplication.CONTEXT,
				Constant.FileName.USER_INFO);
		log.i("RGBH","userInfoText-userInfo="+userInfo);
		if (!TextUtils.isEmpty(userInfoText)) {
			try {
				userInfo = new Gson().fromJson(userInfoText, UserInfo.class);
			} catch (Exception e) {
				userInfo = null;
			}
		} else {
			userInfo = null;
		}
	}

	/**
	 * 退出登录
	 */
	public static void logout() {
		FileUtil.writeFile(PurchaseApplication.CONTEXT, Constant.FileName.USER_INFO, "");
		userInfo = null;
		initUserInfo();
		PreferencesManager.getInstance(PurchaseApplication.CONTEXT).logout();
	}
	
}
