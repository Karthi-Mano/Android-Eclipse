package com.wjz.smart1.tagpage;

import android.view.Gravity;
import android.widget.TextView;

import com.wjz.smart1.activity.MainActivity;

public class HomeBasePageTag extends BasePageTag {

	public HomeBasePageTag(MainActivity mainActivity) {
		super(mainActivity);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initData() {
		
		//更改标题
		base_tv.setText("首页");
		
		TextView tv = new TextView(mainActivity);
		tv.setText("首页内容");
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(48);
		
		base_fl.addView(tv);
		
		super.initData();
	}

}
