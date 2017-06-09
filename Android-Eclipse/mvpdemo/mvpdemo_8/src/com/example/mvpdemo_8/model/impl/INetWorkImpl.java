package com.example.mvpdemo_8.model.impl;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.mvpdemo_8.model.INetWork;

/**
 * @author  Administrator
 * @time 	2015-7-22 下午2:40:51
 * @des	TODO
 *
 * @version $Rev$
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes TODO
 */
public class INetWorkImpl implements INetWork {
	/**
	 * 返回网络是否可用
	 */
	@Override
	public boolean isNetWorkOk(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null) {
			return false;
		} else {
			NetworkInfo info = connectivityManager.getActiveNetworkInfo();
			if (info == null) {
				return false;
			} else {
				if (info.isAvailable()) {
					return true;
				}
			}
		}
		return false;
	}

}
