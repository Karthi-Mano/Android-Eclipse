package com.itheima.mobilesafe13.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Administrator
 * @comp 黑马程序员
 * @date 2015-9-12
 * @desc  对Sharedpreferences功能的封装

 * @version $Rev: 55 $
 * @author $Author: goudan $
 * @Date  $Date: 2015-09-18 16:50:48 +0800 (Fri, 18 Sep 2015) $
 * @Id    $Id: SPUtils.java 55 2015-09-18 08:50:48Z goudan $
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
	public static void putInt(Context context,String key,int value){
		SharedPreferences sp = context.getSharedPreferences(MyConstaints.SPFILENAME, Context.MODE_PRIVATE);
		//添加保存数据
		sp.edit().putInt(key, value).commit();
		
	}
	
	public static int getInt(Context context,String key,int defValue){
		SharedPreferences sp = context.getSharedPreferences(MyConstaints.SPFILENAME, Context.MODE_PRIVATE);
		return sp.getInt(key, defValue);
		
	}
}
