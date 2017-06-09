package com.example.hellojni;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	static{
		System.loadLibrary("hello");
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
       System.out.println(helloFromC());
       System.out.println(helloFromC());
       System.out.println(helloFromC());
       System.out.println(helloFromC());
       System.out.println(helloFromC());
       
    }


    public native String helloFromC();
    
}
