package com.example.smssdkdemo;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SMSSDK.initSDK(this, "您的appkey", "您的appsecret");
		TextView tv = (TextView) findViewById(R.id.tv);
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 打开注册页面
				RegisterPage registerPage = new RegisterPage();
				registerPage.setRegisterCallback(new EventHandler() {
					public void afterEvent(int event, int result, Object data) {
						// 解析注册结果
						if (result == SMSSDK.RESULT_COMPLETE) {
							@SuppressWarnings("unchecked")
							HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
							String country = (String) phoneMap.get("country");
							String phone = (String) phoneMap.get("phone");

							Toast.makeText(MainActivity.this, "phone"+phone, 0).show();
							// 提交用户信息
							//registerUser(country, phone);
						}
					}
				});
				registerPage.show(MainActivity.this);

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
