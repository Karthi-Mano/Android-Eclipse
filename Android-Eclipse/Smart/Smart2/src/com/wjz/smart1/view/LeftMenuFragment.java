package com.wjz.smart1.view;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class LeftMenuFragment extends BaseFragment {

	@Override
	public View initView() {


		TextView tv = new TextView(mainActivity);
		tv.setText("左侧菜单");
		tv.setTextSize(48);
		tv.setGravity(Gravity.CENTER);
		
		
		
		return tv;
	}

}
