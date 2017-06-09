package com.example.zhiling;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class MyService extends Service{

	private ScreenOffReceiver screenOffReceiver;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		screenOffReceiver = new ScreenOffReceiver();
		IntentFilter filter = new IntentFilter();
		//���������㲥
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		//ע��㲥
		registerReceiver(screenOffReceiver, filter);
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(screenOffReceiver);
	}
	
	
}
