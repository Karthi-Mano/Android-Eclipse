package com.example.implement;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	private Button bt1;
	private Button bt2;
	private Button bt3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt1 = (Button) findViewById(R.id.click1);
        bt2 = (Button) findViewById(R.id.click2);
        bt3 = (Button) findViewById(R.id.click3);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
    }
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.click1:
			Toast.makeText(this, "°´Å¥1", 0).show();
			break;
		case R.id.click2:
			Toast.makeText(this, "°´Å¥2", 0).show();
			break;
		case R.id.click3:
			Toast.makeText(this, "°´Å¥3", 0).show();
			break;	
		}
	}
}
