package com.itheima.googleplay_13.protocol;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itheima.googleplay_13.base.BaseProtocol;
import com.itheima.googleplay_13.bean.SubjectInfoBean;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-23 下午4:33:57
 * @描述	     TODO
 *
 * @版本       $Rev: 33 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-23 16:43:11 +0800 (星期五, 23 十月 2015) $
 * @更新描述    TODO
 */
public class SujectProtocol extends BaseProtocol<List<SubjectInfoBean>> {

	@Override
	protected String getInterceKey() {
		// TODO
		return "subject";
	}

	@Override
	protected List<SubjectInfoBean> parseJsonString(String jsonString) {
		Gson gson = new Gson();
		return gson.fromJson(jsonString, new TypeToken<List<SubjectInfoBean>>(){}.getType());
	}

}
