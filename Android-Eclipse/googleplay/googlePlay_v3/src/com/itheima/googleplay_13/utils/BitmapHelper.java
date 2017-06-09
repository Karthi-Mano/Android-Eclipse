package com.itheima.googleplay_13.utils;

import android.view.View;

import com.lidroid.xutils.BitmapUtils;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-21 下午3:22:40
 * @描述	     TODO
 *
 * @版本       $Rev: 20 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-21 15:26:19 +0800 (星期三, 21 十月 2015) $
 * @更新描述    TODO
 */
public class BitmapHelper {

	// static BitmapUtils mBitmapUtils = new BitmapUtils(UIUtils.getContext());
	static BitmapUtils	mBitmapUtils;

	static {
		mBitmapUtils = new BitmapUtils(UIUtils.getContext());
	}

	public static <T extends View> void display(T container, String uri) {
		mBitmapUtils.display(container, uri);
	}
}
