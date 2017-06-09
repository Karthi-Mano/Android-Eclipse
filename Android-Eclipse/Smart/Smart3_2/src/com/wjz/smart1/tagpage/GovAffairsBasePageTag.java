package com.wjz.smart1.tagpage;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.wjz.smart1.activity.MainActivity;

public class GovAffairsBasePageTag extends BasePageTag {

	public GovAffairsBasePageTag(MainActivity mainActivity) {
		super(mainActivity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initData() {
		
		//更改标题
		base_tv.setText("政务");
		base_ib.setVisibility(View.VISIBLE);
		
		TextView tv = new TextView(mainActivity);
		tv.setText("政务内容");
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(48);
		
		base_fl.addView(tv);
		
		super.initData();
	}

}
