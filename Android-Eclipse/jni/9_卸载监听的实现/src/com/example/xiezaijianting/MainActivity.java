package com.example.xiezaijianting;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {
	static{
		System.loadLibrary("forkc");
	}
	
	public native void callC();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new Thread(){
			public void run() {
				callC();
				};
		}.start();
	}

	
    
}
