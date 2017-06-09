package com.example.level3encrypt;

import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.level3encrypt.util.LanguageTransform;
import com.example.level3encrypt.util.DES;

public class MainActivity extends Activity {

    protected static final String TAG = "MainActivity";
	private TextView output;
	private EditText input;
	private Button start;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        
        
        start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				String inputText = input.getText().toString();
				Log.i(TAG, "input:" + inputText);
				
				//获取秘钥
				String key = getKey();
				Toast.makeText(getApplicationContext(), "获取到的KEY为：" + key, Toast.LENGTH_SHORT).show();
				
				//进行DES加密:key必须为16位
				DES des = new DES();
				String encrypt = des.authcode(inputText,"DECODE", key);
				
				Toast.makeText(getApplicationContext(), "获取到的encrypt为：" + encrypt, Toast.LENGTH_SHORT).show();
				output.setText(encrypt);
			}
		});
    }

    //初始化控件
	private void init() {
		input = (EditText) findViewById(R.id.et_input);
		output = (TextView) findViewById(R.id.tv_output);
		start = (Button) findViewById(R.id.start);
	}
	
	private String getKey() {
		return LanguageTransform.getKey();
	}

}
