package com.itheima.googleplay_13.base;

import java.util.HashMap;
import java.util.Map;

import com.itheima.googleplay_13.bean.HomeBean;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-20 上午9:55:32
 * @描述	     单例,全局的盒子,放置大家都可以用的东西
 *
 * @版本       $Rev: 27 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-23 10:50:08 +0800 (星期五, 23 十月 2015) $
 * @更新描述    TODO
 */
public class BaseApplication extends Application {

	private static Context			mContext;
	private static Handler			mHandler;
	private static long				mMainThreadId;

	// 对象
	private Map<String, String>	mCacheMap	= new HashMap<String, String>();

	public Map<String, String> getCacheMap() {
		return mCacheMap;
	}

	public static Context getContext() {
		return mContext;
	}

	public static Handler getHandler() {
		return mHandler;
	}

	public static long getMainThreadId() {
		return mMainThreadId;
	}

	@Override
	public void onCreate() {// 程序的入口方法

		// 1.上下文
		mContext = getApplicationContext();

		// 2.放置一个主线程的Handler
		mHandler = new Handler();

		// 3.得到主线程的Id
		mMainThreadId = android.os.Process.myTid();

		// Tid Thread
		// Pid Process
		// Uid User

		super.onCreate();
	}

}
