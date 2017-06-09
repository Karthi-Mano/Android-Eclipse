package com.itheima.mobilesafe13.utils;

import android.util.Log;

public class MyLog {
	public static final boolean isPrintLog = false;
	public static void println(String mess){
		if (isPrintLog)
			System.out.println(mess);
	}
	
	public static void i(String tag,String mess){
		if (isPrintLog)
			Log.i(tag, mess);
	}
}
