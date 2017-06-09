package com.itheima.mobilesafe13.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Administrator
 * @comp 黑马程序员
 * @date 2015-9-12
 * @desc  对Sharedpreferences功能的封装

 * @version $Rev: 14 $
 * @author $Author: goudan $
 * @Date  $Date: 2015-09-13 10:44:52 +0800 (Sun, 13 Sep 2015) $
 * @Id    $Id: SPUtils.java 14 2015-09-13 02:44:52Z goudan $
 * @Url   $URL: https://188.188.4.100/svn/mobilesafeserver/trunk/MobileSafe13/src/com/itheima/mobilesafe13/utils/SPUtils.java $
 * 
 */
public class SPUtils {
	/**
	 * @param context
	 * @param key
	 * 		关键字
	 * @param value
	 * 		值
	 */
	public static void putBoolean(Context context,String key,boolean value){
		SharedPreferences sp = context.getSharedPreferences(MyConstaints.SPFILENAME, Context.MODE_PRIVATE);
		//添加保存数据
		sp.edit().putBoolean(key, value).commit();
		
	}
	
	public static boolean getBoolean(Context context,String key,boolean defValue){
		SharedPreferences sp = context.getSharedPreferences(MyConstaints.SPFILENAME, Context.MODE_PRIVATE);
		return sp.getBoolean(key, defValue);
		
	}
	
	public static void putString(Context context,String key,String value){
		SharedPreferences sp = context.getSharedPreferences(MyConstaints.SPFILENAME, Context.MODE_PRIVATE);
		//添加保存数据
		sp.edit().putString(key, value).commit();
		
	}
	
	public static String getString(Context context,String key,String defValue){
		SharedPreferences sp = context.getSharedPreferences(MyConstaints.SPFILENAME, Context.MODE_PRIVATE);
		return sp.getString(key, defValue);
		
	}
}
