package com.wjz.smart1.newscenterpages;

import java.util.ArrayList;
import java.util.List;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.wjz.smart1.activity.MainActivity;
import com.wjz.smart1.domain.NewsCenterData;
import com.wjz.smart1.domain.NewsCenterData.NewsData.ViewTagData;

public class NewsBaseNewsCenterPage extends BaseNewsCenterPage {
	private List<NewsCenterData.NewsData.ViewTagData> viewTagData = new ArrayList<NewsCenterData.NewsData.ViewTagData>();

	public NewsBaseNewsCenterPage(MainActivity mainActivity,
			List<ViewTagData> children) {
		super(mainActivity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View initView() {
		// 要展示的内容，
		TextView tv = new TextView(mainActivity);
		tv.setText("新闻的内容");
		tv.setTextSize(25);
		tv.setGravity(Gravity.CENTER);
		return tv;
	}

}
