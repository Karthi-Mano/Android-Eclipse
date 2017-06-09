package com.example.wulianwang;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	static {
		System.loadLibrary("wulianwang");
	}

	public native void startMonitor();
	public native void stopMonitor();
	
	private MyProgressBar pb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		pb = (MyProgressBar) findViewById(R.id.pb);
		pb.setMax(100);
	}
	
	/**
	 * 设置进度条的刻度
	 * @param value
	 */
	public void setPb(int value){
		pb.setProgress(value);
	}

	/**
	 * 开始监视
	 * @param view
	 */
	public void start(View view) {
		new Thread(){
			public void run() {
				startMonitor();
			};
		}.start();
	}

	/**
	 * 停止监视
	 * @param view
	 */
	public void stop(View view) {
			stopMonitor();
	}
	
	/**
	 * 短信
	 */
	public void sendSms(){
		//SmsManager.getDefault().sendTextMessage("5556", null, "快爆炸了,赶紧通知工人跑路...", null, null);
	}
	/**
	 * 音乐
	 */
	public void playmusic(){
		//MediaPlayer 
	}
	
}
