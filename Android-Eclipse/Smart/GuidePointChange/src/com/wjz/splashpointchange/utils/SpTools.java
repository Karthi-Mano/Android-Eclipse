package com.wjz.splashpointchange.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SpTools {

	public static void setBoolean(Context context, String key, boolean value) {
		// 通过上下文得到一个共享参数的实例对象
		SharedPreferences sp = context.getSharedPreferences(
				MyContains.CONFIGFILE, Context.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).commit();
	}

	public static boolean getBoolean(Context context, String key,
			boolean defValue) {
		// 通过上下文得到一个共享参数的实例对象
		SharedPreferences sp = context.getSharedPreferences(MyContains.CONFIGFILE, Context.MODE_PRIVATE);
		return sp.getBoolean(key, defValue);
	}
}
