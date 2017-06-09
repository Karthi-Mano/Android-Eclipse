package com.wjz.smart1.tagpage;

import android.view.Gravity;
import android.widget.TextView;

import com.wjz.smart1.activity.MainActivity;

public class SettingCenterBasePageTag extends BasePageTag {

	public SettingCenterBasePageTag(MainActivity mainActivity) {
		super(mainActivity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initData() {
		
		//更改标题
		base_tv.setText("设置中心");
		
		TextView tv = new TextView(mainActivity);
		tv.setText("设置中心内容");
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(48);
		
		base_fl.addView(tv);
		
		super.initData();
	}

}
