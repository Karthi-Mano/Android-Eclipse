package com.itheima.googleplay_13.protocol;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itheima.googleplay_13.base.BaseProtocol;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-24 上午9:32:43
 * @描述	     TODO
 *
 * @版本       $Rev: 38 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-24 09:48:07 +0800 (星期六, 24 十月 2015) $
 * @更新描述    TODO
 */
public class RecommendProtocol extends BaseProtocol<List<String>> {

	@Override
	protected String getInterceKey() {
		return "recommend";
	}

	@Override
	protected List<String> parseJsonString(String jsonString) {
		Gson gson = new Gson();
		return gson.fromJson(jsonString, new TypeToken<List<String>>(){}.getType());
	}

}
