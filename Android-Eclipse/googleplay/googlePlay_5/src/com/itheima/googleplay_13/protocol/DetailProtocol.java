package com.itheima.googleplay_13.protocol;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.itheima.googleplay_13.base.BaseProtocol;
import com.itheima.googleplay_13.bean.AppInfoBean;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-24 下午2:37:05
 * @描述	     TODO
 *
 * @版本       $Rev: 71 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-25 18:39:31 +0800 (星期日, 25 十月 2015) $
 * @更新描述    TODO
 */
public class DetailProtocol extends BaseProtocol<AppInfoBean> {

	private String	mPackageName;

	public DetailProtocol(String packageName) {
		super();
		mPackageName = packageName;
	}

	@Override
	protected String getInterceKey() {
		return "detail";
	}

/*	@Override
	protected AppInfoBean parseJsonString(String jsonString) {
		Gson gson = new Gson();
		return gson.fromJson(jsonString, AppInfoBean.class);
	}*/

	@Override
	protected Map<String, String> getExtraParmas() {
		Map<String, String> extraParmas = new HashMap<String, String>();
		extraParmas.put("packageName", mPackageName);
		return extraParmas;
	}
}
