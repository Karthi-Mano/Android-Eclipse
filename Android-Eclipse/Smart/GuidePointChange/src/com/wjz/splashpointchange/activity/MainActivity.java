package com.wjz.splashpointchange.activity;

import com.wjz.splashpointchange.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		initView();
		initEvent();
		initData();
	}

	private void initView() {

		setContentView(R.layout.activity_main);
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		
	}

	private void initData() {
		// TODO Auto-generated method stub
		
	}
}
