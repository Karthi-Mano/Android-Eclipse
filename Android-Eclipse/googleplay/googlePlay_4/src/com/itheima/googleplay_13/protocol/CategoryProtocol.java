package com.itheima.googleplay_13.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.itheima.googleplay_13.base.BaseProtocol;
import com.itheima.googleplay_13.bean.CategoryInfoBean;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-24 上午9:56:03
 * @描述	     TODO
 *
 * @版本       $Rev: 39 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-24 10:11:06 +0800 (星期六, 24 十月 2015) $
 * @更新描述    TODO
 */
public class CategoryProtocol extends BaseProtocol<List<CategoryInfoBean>> {

	@Override
	protected String getInterceKey() {
		return "category";
	}

	@Override
	protected List<CategoryInfoBean> parseJsonString(String jsonString) {
		List<CategoryInfoBean> result = new ArrayList<CategoryInfoBean>();
		/*--------------- android sdk里面的json解析 ---------------*/
		try {
			JSONArray rootJsonArray = new JSONArray(jsonString);
			for (int i = 0; i < rootJsonArray.length(); i++) {
				JSONObject itemJsonObject = rootJsonArray.getJSONObject(i);

				String title = itemJsonObject.getString("title");
				CategoryInfoBean titleBean = new CategoryInfoBean();
				titleBean.title = title;
				titleBean.isTitle = true;

				// 加入集合中
				result.add(titleBean);

				JSONArray infosArray = itemJsonObject.getJSONArray("infos");
				for (int j = 0; j < infosArray.length(); j++) {
					JSONObject infoJsonObject = infosArray.getJSONObject(j);
					String name1 = infoJsonObject.getString("name1");
					String name2 = infoJsonObject.getString("name2");
					String name3 = infoJsonObject.getString("name3");
					String url1 = infoJsonObject.getString("url1");
					String url2 = infoJsonObject.getString("url2");
					String url3 = infoJsonObject.getString("url3");

					CategoryInfoBean infoCategoryInfoBean = new CategoryInfoBean();
					infoCategoryInfoBean.name1 = name1;
					infoCategoryInfoBean.name2 = name2;
					infoCategoryInfoBean.name3 = name3;
					infoCategoryInfoBean.url1 = url1;
					infoCategoryInfoBean.url3 = url3;
					infoCategoryInfoBean.url2 = url2;

					result.add(infoCategoryInfoBean);
				}

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return result;
	}

}
