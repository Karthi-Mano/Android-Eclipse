package com.itheima.googleplay_13.protocol;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.itheima.googleplay_13.base.BaseProtocol;
import com.itheima.googleplay_13.bean.AppInfoBean;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-23 上午11:15:00
 * @描述	     TODO
 *
 * @版本       $Rev: 71 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-25 18:39:31 +0800 (星期日, 25 十月 2015) $
 * @更新描述    TODO
 */
public class GameProtocol extends BaseProtocol<List<AppInfoBean>> {

	@Override
	protected String getInterceKey() {
		// TODO
		return "game";
	}

/*	@Override
	protected List<AppInfoBean> parseJsonString(String jsonString) {
//		Gson gson = new Gson();
		List<AppInfoBean> appInfoBeans = new ArrayList<AppInfoBean>();
		return gson.fromJson(jsonString, new TypeToken<List<AppInfoBean>>(){}.getType());
		--------------- json的结点解析 ---------------
		// 获得 解析者
		JsonParser parser = new JsonParser();
		JsonElement rootJsonElement = parser.parse(jsonString);
		// JsonElement-->jsonArray
		JsonArray rootJsonArray = rootJsonElement.getAsJsonArray();
		// 遍历jsonArray
		for (JsonElement itemJsonElement : rootJsonArray) {
			// JsonElement-->jsonObject
			JsonObject itemJsonObject = itemJsonElement.getAsJsonObject();

			JsonElement desJsonElement = itemJsonObject.get("des");
			// JsonElement-->jsonPrimitive
			JsonPrimitive desJsonPrimitive = desJsonElement.getAsJsonPrimitive();
			// JsonPrimitive-->String
			String des = desJsonPrimitive.getAsString();

			String downLoadUrl = itemJsonObject.get("downloadUrl").getAsString();
			String iconUrl = itemJsonObject.get("iconUrl").getAsString();
			long id = itemJsonObject.get("id").getAsLong();
			String name = itemJsonObject.get("name").getAsString();
			String packageName = itemJsonObject.get("packageName").getAsString();
			long size = itemJsonObject.get("size").getAsLong();
			float stars = itemJsonObject.get("stars").getAsFloat();

			AppInfoBean info = new AppInfoBean();
			info.des = des;
			info.downloadUrl = downLoadUrl;
			info.iconUrl = iconUrl;
			info.id = id;
			info.name = name;
			info.packageName = packageName;
			info.size = size;
			info.stars = stars;

			appInfoBeans.add(info);

		}
		
		return appInfoBeans;
	}
*/
}
