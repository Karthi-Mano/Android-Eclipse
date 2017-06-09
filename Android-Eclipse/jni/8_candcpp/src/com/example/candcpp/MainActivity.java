package com.example.candcpp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {
	static{
		System.loadLibrary("candcpp");
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toast.makeText(this, helloFromCpp(), 0).show();
		
		System.out.println("helloFromCpp:   "+helloFromCpp());
	}

	public native String helloFromCpp();
}
