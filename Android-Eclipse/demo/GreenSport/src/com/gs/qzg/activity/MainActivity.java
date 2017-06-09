package com.gs.qzg.activity;

import com.gs.qzg.greensport.R;
import com.gs.qzg.greensport.R.id;
import com.gs.qzg.greensport.R.layout;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button gogo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		SharedPreferences sharedPreferences= getSharedPreferences("test", 
				Activity.MODE_PRIVATE);
		final String name =sharedPreferences.getString("name", ""); 
		gogo = (Button)findViewById(R.id.gogo);
		gogo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				
				if ("1".equals(name)) {
					Intent intent1 = new Intent(MainActivity.this,HomeActivity.class);
					intent1.putExtra("check_index", 1);
					startActivity(intent1);
				}
				else {
					Intent intent = new Intent(MainActivity.this,LoginActivity.class);
//					intent.putExtra("check_index", 1);
					startActivity(intent);
				}
				
			}
		});
	}
}
