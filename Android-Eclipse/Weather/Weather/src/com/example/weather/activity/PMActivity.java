package com.example.weather.activity;

import com.example.weather.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class PMActivity extends Activity {

	private WebView wv_pm;
	private WebSettings wv_settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pm);
		wv_pm = (WebView) findViewById(R.id.wv_pm);
		
		//控制WebView的显示设置
		wv_settings = wv_pm.getSettings();
		
		//双击放大缩小
		wv_settings.setUseWideViewPort(true);
		
		String url="http://m.pm25.com";
		//String url="http://www.baidu.com";
		//设置webView加载的URL
		wv_pm.loadUrl(url);
		
		
	}
}
