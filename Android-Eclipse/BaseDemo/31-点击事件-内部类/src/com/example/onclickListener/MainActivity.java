package com.example.onclickListener;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = (Button) findViewById(R.id.click);
        bt.setOnClickListener(new MyListener());
    }
    private class MyListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			Toast.makeText(MainActivity.this, "ÎÒ±»üc“ôÁË", 0).show();
		}
    }
}
