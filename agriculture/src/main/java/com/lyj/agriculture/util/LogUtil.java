package com.lyj.agriculture.util;

import android.util.Log;

public class LogUtil {
	private static boolean flag = true;

	public static void i(String tag, String msg) {
		if (flag) {
			Log.i(tag, msg);
		}
	}
}
