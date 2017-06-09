package com.wjz.smart1.tagpage;

import java.util.ArrayList;
import java.util.List;

import android.preference.SwitchPreference;
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
import com.wjz.smart1.newscenterpages.BaseNewsCenterPage;
import com.wjz.smart1.newscenterpages.InteractBaseNewsCenterPage;
import com.wjz.smart1.newscenterpages.NewsBaseNewsCenterPage;
import com.wjz.smart1.newscenterpages.PhotosBaseNewsCenterPage;
import com.wjz.smart1.newscenterpages.TopicBaseNewsCenterPage;
import com.wjz.smart1.utils.MyContainer;
import com.wjz.smart1.view.LeftMenuFragment.OnSwitchPageListener;

public class NewCenterBasePageTag extends BasePageTag {

	private List<BaseNewsCenterPage> newsCenterPage = new ArrayList<BaseNewsCenterPage>();
	private NewsCenterData newsCenterData;

	public NewCenterBasePageTag(MainActivity mainActivity) {
		super(mainActivity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initData() {

		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, MyContainer.URL,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						System.out.println("获取数据成功" + responseInfo);
						String jsonData = responseInfo.result;
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
		newsCenterData = gson.fromJson(jsonData,
				NewsCenterData.class);
		// System.out.println(newsCenterData.data.get(0).children.get(0).title);
		// 将数据传给左侧菜单
		mainActivity.getLeftMenuFragment().setLeftMenuData(newsCenterData.data);

		// 设置左侧菜单的监听回调
		mainActivity.getLeftMenuFragment().setOnSwitchPageListener(
				new OnSwitchPageListener() {

					@Override
					public void switchPage(int selectItem) {
						NewCenterBasePageTag.this.swtichPage(selectItem);
					}
				});

		// 读取数据封装到容器中，通过左侧菜单点击，显示不同界面
		// 按顺序创建左侧菜单对应的数据
		for (NewsCenterData.NewsData newsData : newsCenterData.data) {
			BaseNewsCenterPage newspage = null;
			switch (newsData.type) {
			case 1:
				newspage = new NewsBaseNewsCenterPage(mainActivity,
						newsCenterData.data.get(0).children);
				break;
			case 10:
				newspage = new TopicBaseNewsCenterPage(mainActivity);
				break;
			case 2:
				newspage = new PhotosBaseNewsCenterPage(mainActivity);
				break;
			case 3:
				newspage = new InteractBaseNewsCenterPage(mainActivity);
				break;

			default:
				break;
			}// end

			// 将数据添加到BaseNewsCenterPage的容器container中
			newsCenterPage.add(newspage);
			
		}
		
		//控制四个页面的显示，默认显示新闻页面
		swtichPage(0);
	}

	/**
	 * 根据位置动态显示不同的新闻中心
	 */
	public void swtichPage(int position){
		BaseNewsCenterPage currentNewsCenterPage = newsCenterPage.get(position);
		
		//显示数据
		//设置本page的标题
		base_tv.setText(newsCenterData.data.get(position).title);
		
		//移除掉动画原来的内容
		base_fl.removeAllViews();
		
		//初始化newscenterpages数据
		
		//用本page替换base_fl的内容
		base_fl.addView(currentNewsCenterPage.getView());
	}
}
