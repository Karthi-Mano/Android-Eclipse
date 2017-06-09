package com.itheima.mobilesafe13.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.itheima.mobilesafe13.R;
import com.itheima.mobilesafe13.view.SettingCenterItem;
import com.itheima.mobilesafe13.view.SettingCenterItem.OnToggleChangedListener;

public class AToolActivity extends Activity {
	private SettingCenterItem sci_phonequery;
	private SettingCenterItem sci_servicequery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initView();
		
		initEvent();
	}

	private void initEvent() {
		// 事件
		OnToggleChangedListener listener  = new OnToggleChangedListener() {
			
			@Override
			public void onToggleChange(View v, boolean isOpen) {
				switch (v.getId()) {
				case R.id.sci_atool_mobilephone:
					//电话查询
					Intent phone = new Intent(AToolActivity.this,PhoneLocationActivity.class);
					startActivity(phone);
					break;
				case R.id.sci_atool_servicephone:
					//服务号码
					Intent serviceNumber = new Intent(AToolActivity.this,ServiceNumberActivity.class);
					startActivity(serviceNumber);
					break;
				default:
					break;
				}
				
			}
		};
		
		sci_phonequery.setOnToggleChangedListener(listener);
		sci_servicequery.setOnToggleChangedListener(listener);
		
	}

	private void initView() {
		
		setContentView(R.layout.activity_atool);
		sci_phonequery = (SettingCenterItem) findViewById(R.id.sci_atool_mobilephone);
		sci_servicequery = (SettingCenterItem) findViewById(R.id.sci_atool_servicephone);
	}
}
