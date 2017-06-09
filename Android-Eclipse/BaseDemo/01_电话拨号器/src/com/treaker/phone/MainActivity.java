package com.treaker.phone;


import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View btn = findViewById(R.id.btn_call);
        btn.setOnClickListener(new MyListener());
        
    }
    private class MyListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel://123"));
			startActivity(intent);
		}
    	
    }
 
}
