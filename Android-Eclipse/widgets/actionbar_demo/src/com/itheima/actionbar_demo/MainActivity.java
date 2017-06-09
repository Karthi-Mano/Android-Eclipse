package com.itheima.actionbar_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.btn1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this, TitleActivity.class);
				startActivity(intent);
			}
		});
		findViewById(R.id.btn2).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
				startActivity(intent);
			}
		});
		findViewById(R.id.btn3).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this, ActionButtonActivity.class);
				startActivity(intent);
			}
		});
		findViewById(R.id.btn4).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this, ActionModeActivity.class);
				startActivity(intent);
			}
		});
	}

}
