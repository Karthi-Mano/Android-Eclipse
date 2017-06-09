package com.itheima.googleplay_13.protocol;

import com.google.gson.Gson;
import com.itheima.googleplay_13.base.BaseProtocol;
import com.itheima.googleplay_13.bean.HomeBean;
import com.itheima.googleplay_13.conf.Constants.URlS;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-23 上午9:08:13
 * @描述	     负责HomeFragemnt中的网络请求
 *
 * @版本       $Rev: 26 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-23 09:24:12 +0800 (星期五, 23 十月 2015) $
 * @更新描述    TODO
 */
public class HomeProtocol extends BaseProtocol<HomeBean> {

	@Override
	protected String getInterceKey() {
		return "home";
	}

	@Override
	protected HomeBean parseJsonString(String jsonString) {
		Gson gson = new Gson();
		HomeBean homeBean = gson.fromJson(jsonString, HomeBean.class);
		return homeBean;
	}
	/*public HomeBean loadData(int index) throws Exception {
		HttpUtils httpUtils = new HttpUtils();
		// "http://localhost:8080/GooglePlayServer/home?index=0"
		String url = URlS.BASEURL + "home";
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("index", index + "");
		ResponseStream responseStream = httpUtils.sendSync(HttpMethod.GET, url, params);

		String jsonString = responseStream.readString();

		Gson gson = new Gson();
		HomeBean homeBean = gson.fromJson(jsonString, HomeBean.class);
		return homeBean;
	}*/
}
