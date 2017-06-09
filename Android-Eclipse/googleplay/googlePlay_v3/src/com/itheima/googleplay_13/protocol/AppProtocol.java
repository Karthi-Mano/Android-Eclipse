package com.itheima.googleplay_13.protocol;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itheima.googleplay_13.base.BaseProtocol;
import com.itheima.googleplay_13.bean.AppInfoBean;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-23 上午11:03:31
 * @描述	     TODO
 *
 * @版本       $Rev: 28 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-23 11:35:52 +0800 (星期五, 23 十月 2015) $
 * @更新描述    TODO
 */
public class AppProtocol extends BaseProtocol<List<AppInfoBean>> {

	@Override
	protected String getInterceKey() {
		return "app";
	}

	@Override
	protected List<AppInfoBean> parseJsonString(String jsonString) {
		// 1.Bean解析
		// 2.泛型解析(List,Map)
		Gson gson = new Gson();
		return gson.fromJson(jsonString, new TypeToken<List<AppInfoBean>>() {
		}.getType());
	}

}
