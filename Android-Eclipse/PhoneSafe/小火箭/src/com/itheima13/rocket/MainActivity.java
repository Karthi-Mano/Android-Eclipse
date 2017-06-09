package com.itheima13.rocket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	/*	ImageView iv_rockets = (ImageView) findViewById(R.id.iv_rocket);
		
		//����
		AnimationDrawable ad = (AnimationDrawable) iv_rockets.getBackground();
		ad.start();//��ʼ����
*/	}
	
	public void startRocket(View v){
		//����С���
		//��������
		Intent service = new Intent(this,RocketService.class);
		startService(service);
		
		//finish
		finish();//�ر�Activity
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
	