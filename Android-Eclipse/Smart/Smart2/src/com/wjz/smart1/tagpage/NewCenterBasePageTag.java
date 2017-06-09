package com.wjz.smart1.tagpage;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.wjz.smart1.activity.MainActivity;

public class NewCenterBasePageTag extends BasePageTag {

	public NewCenterBasePageTag(MainActivity mainActivity) {
		super(mainActivity);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initData() {
		
		//更改标题
		base_tv.setText("新闻中心");
		base_ib.setVisibility(View.VISIBLE);
		
		TextView tv = new TextView(mainActivity);
		tv.setText("新闻中心内容");
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(48);
		
		base_fl.addView(tv);
		
		super.initData();
	}

}
