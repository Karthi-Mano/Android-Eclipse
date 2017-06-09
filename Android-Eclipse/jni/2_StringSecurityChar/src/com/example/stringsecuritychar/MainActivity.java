package com.example.stringsecuritychar;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	private EditText et_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_str = (EditText) findViewById(R.id.et_str);
        
    }

    public void encode(View view){
		String str = et_str.getText().toString().trim();
		String newstr = encodeStr(str, str.length());
		et_str.setText(newstr);
	}
	public void decode(View view){
		String str = et_str.getText().toString().trim();
		String newstr = decodeStr(str, str.length());
		et_str.setText(newstr);
	}
    
    /**
	 * 加密字符串 
	 * @param text 原文
	 * @param len 字符串的长度
	 * @return 密文
	 */
	public native String encodeStr(String text,int len);
	/**
	 * 解密字符串
	 * @param text 密文
	 * @param len 字符串的长度
	 * @return 原文
	 */
	public native String decodeStr(String text,int len);
  
    static{
		System.loadLibrary("StringSecurityChar");
	}
}
