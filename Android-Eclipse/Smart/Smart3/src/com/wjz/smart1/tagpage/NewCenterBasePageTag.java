package com.wjz.smart1.tagpage;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.wjz.smart1.activity.MainActivity;
import com.wjz.smart1.domain.NewsCenterData;
import com.wjz.smart1.utils.MyContainer;


public class NewCenterBasePageTag extends BasePageTag {

	public NewCenterBasePageTag(MainActivity mainActivity) {
		super(mainActivity);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initData() {

		
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, MyContainer.URL,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						System.out.println("获取数据成功" + responseInfo);
						String jsonData=responseInfo.result;
						parseJson(jsonData);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub
						System.out.println("连接失败");
						System.out.println(error);
						System.out.println(msg);
					}
				});
	
		
		
		
		// 更改标题
		base_tv.setText("新闻中心");
		base_ib.setVisibility(View.VISIBLE);

		TextView tv = new TextView(mainActivity);
		tv.setText("新闻中心内容");
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(48);

		base_fl.addView(tv);

		super.initData();
	}

	protected void parseJson(String jsonData) {
			Gson gson = new Gson();
			NewsCenterData newsCenterData = gson.fromJson(jsonData, NewsCenterData.class);
			//System.out.println(newsCenterData.data.get(0).children.get(0).title);
			//将数据传给左侧菜单
			mainActivity.getLeftMenuFragment().setLeftMenuData(newsCenterData.data);
			
	}

}
