package com.lyj.agriculture.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

public class NetUtil {

	/** 无网络连接 */
	public static final int NETWORK_TYPE_NONE = -1;
	public static final String NETWORK_TYPE_NONE_MSG = "无网络";

	/** 通过WIFI连接网络 */
	public static final int NETWORK_TYPE_WIFI = 0;
	public static final String NETWORK_TYPE_WIFI_MSG = "WIFI网络";

	/** 通过低速网络连接网络 */
	public static final int NETWORK_TYPE_LOW = 1;
	public static final String NETWORK_TYPE_LOW_MSG = "低速网络";

	/** 通过高速网络连接网络 */
	public static final int NETWORK_TYPE_HIGH = 2;
	public static final String NETWORK_TYPE_HIGH_MSG = "高速网络";

	/**
	 * 检查网络接连类型.
	 * 
	 * @param context
	 * @return Sys NETWORK_TYPE_NONE: 无网络连接; Sys NETWORK_TYPE_WIFI: 通过WIFI连接网络;
	 *         Sys NETWORK_TYPE_WAP: 通过GPRS连接网络.
	 */
	public static int checkNetWorkType(Context context) {
		if (isAirplaneModeOn(context)) {
			return NETWORK_TYPE_NONE;
		}
		State wifiState = null;
		State mobileState = null;
		int subType = -1;
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();

		if (wifiState != null && mobileState != null && State.CONNECTED != wifiState && State.CONNECTED == mobileState) {
			LogUtil.i("NetInfo", "当前网络类型:蜂窝网络");
			subType = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getSubtype();
			if (subType == TelephonyManager.NETWORK_TYPE_1xRTT || subType == TelephonyManager.NETWORK_TYPE_CDMA
					|| subType == TelephonyManager.NETWORK_TYPE_EDGE || subType == TelephonyManager.NETWORK_TYPE_GPRS
					|| subType == TelephonyManager.NETWORK_TYPE_IDEN) {
				// 低速网络,速度在200K以下
				Log.i("NetInfo", "当前网络为:低速网络");
				return NETWORK_TYPE_LOW;
			} else if (subType == TelephonyManager.NETWORK_TYPE_UNKNOWN) {// 无网络
				Log.i("NetInfo", "当前网络为:无网络");
				return NETWORK_TYPE_NONE;
			} else {
				// 高速网络,速度在200K以上
				Log.i("NetInfo", "当前网络为:高速网络");
				return NETWORK_TYPE_HIGH;
			}

		} else if (wifiState != null && mobileState != null && State.CONNECTED != wifiState
				&& State.CONNECTED != mobileState) {
			Log.i("NetInfo", "当前网络类型:无网络");
			return NETWORK_TYPE_NONE;
		} else if (wifiState != null && State.CONNECTED == wifiState) {
			Log.i("NetInfo", "当前网络类型:Wifi网络");
			return NETWORK_TYPE_WIFI;
		} else {
			Log.i("NetInfo", "当前网络类型:无网络");
			return NETWORK_TYPE_NONE;
		}
	}

	/**
	 * 判断手机是否处于飞行模式.
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isAirplaneModeOn(Context context) {
		return Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) != 0;
	}

}
