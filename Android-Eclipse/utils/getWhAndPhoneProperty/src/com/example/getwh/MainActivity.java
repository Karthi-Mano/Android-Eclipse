package com.example.getwh;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	private SoundPool soundPool;
	private int soundid;
	private TextView tv4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Display mDisplay = getWindowManager().getDefaultDisplay();
		int W = mDisplay.getWidth();
		int H = mDisplay.getHeight();
		TextView tv = (TextView) findViewById(R.id.tv);
		tv.setText(W + "*" + H);

		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);

		TextView tv2 = (TextView) findViewById(R.id.tv2);
		TextView tv3 = (TextView) findViewById(R.id.tv3);
		tv4 = (TextView) findViewById(R.id.tv4);
		tv2.setText("density:密度;scaledensity:按比例缩小的密度");
		tv3.setText(mDisplayMetrics + "");

		soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		soundid = soundPool.load(this, R.raw.a, 1);
		
		
		
	}

	public void click(View v) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				soundPool.play(soundid, 1.0f, 1.0f, 0, 0, 1.0f);

			}
		}).start();

	}

	public void click2(View v) {

		ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setTitle("提示：");
		progressDialog.setMessage("正在提交，请稍后...");
		progressDialog.show();
		
		
		//获取application
				MyApplication my = (MyApplication)getApplication();
				
				my.soutPublic();
	}

	public void click3(View v){
		
String a =
android.os.Build.BOARD	      +":  BOARD   "+         "   ||   "     +
android.os.Build.BOOTLOADER	  +":  BOOTLOADER   "+  "   ||   "+            
android.os.Build.BRAND     	  +":  BRAND   "+    "   ||   " +          
android.os.Build.CPU_ABI	  +":  CPU_ABI   "+"   ||   "   +  
android.os.Build.CPU_ABI2	  +":  CPU_ABI2   "+"   ||   "  +   
android.os.Build.DEVICE	      +":  DEVICE   "+"   ||   "  +   
android.os.Build.DISPLAY	  +":  DISPLAY   "+"   ||   "   +  
android.os.Build.FINGERPRINT  +":  FINGERPRINT   "+"   ||   " +    
android.os.Build.HARDWARE	  +":  HARDWARE   "+"   ||   "  +   
android.os.Build.HOST	      +":  HOST   "+"   ||   "    + 
android.os.Build.ID           +":  ID   "+"   ||   "    + 
android.os.Build.MANUFACTURER +":  MANUFACTURER   "+"   ||   "   +  
android.os.Build.MODEL	      +":  MODEL   "+"   ||   "    + 
android.os.Build.PRODUCT	  +":  PRODUCT   "+"   ||   "  +   
android.os.Build.RADIO	      +":  RADIO   "+"   ||   "   +  
android.os.Build.TAGS	      +":  TAGS   "+"   ||   "    + 
android.os.Build.TIME	      +":  TIME   "+"   ||   "   +  
android.os.Build.TYPE	      +":  TYPE   "+"   ||   "   +  
android.os.Build.UNKNOWN	  +":  UNKNOWN   "+"   ||   "  +   
android.os.Build.USER      +":  USER   " ;
		
		//String str = "获取手机型号:"+android.os.Build.MODEL+"  获取系统版本号:"+ android.os.Build.VERSION.RELEASE+" PRODUCT:"+android.os.Build.PRODUCT;
		tv4.setText(a);
		
		
		
					
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
