package com.wjz.smart1.newscenterpages;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.wjz.smart1.activity.MainActivity;

public class PhotosBaseNewsCenterPage extends BaseNewsCenterPage {

	public PhotosBaseNewsCenterPage(MainActivity mainActivity) {
		super(mainActivity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View initView() {
		// 要展示的内容，
				TextView tv = new TextView(mainActivity);
				tv.setText("组图的内容");
				tv.setTextSize(25);
				tv.setGravity(Gravity.CENTER);
				return tv;
	}

}
