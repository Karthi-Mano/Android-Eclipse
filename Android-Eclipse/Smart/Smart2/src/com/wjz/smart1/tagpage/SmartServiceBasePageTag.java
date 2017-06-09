package com.wjz.smart1.tagpage;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.wjz.smart1.activity.MainActivity;

public class SmartServiceBasePageTag extends BasePageTag {

	public SmartServiceBasePageTag(MainActivity mainActivity) {
		super(mainActivity);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initData() {
		
		//更改标题
		base_tv.setText("智慧服务");
		base_ib.setVisibility(View.VISIBLE);
		
		TextView tv = new TextView(mainActivity);
		tv.setText("智慧服务内容");
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(48);
		
		base_fl.addView(tv);
		
		super.initData();
	}

}
