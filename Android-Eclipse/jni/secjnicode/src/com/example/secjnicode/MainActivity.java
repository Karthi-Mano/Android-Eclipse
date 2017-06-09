package com.example.secjnicode;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {
	
	static{
		System.loadLibrary("secjnicode");
	}

	/**
	 * 加密字符串 
	 * @param text 原文
	 * @param len 字符串的长度
	 * @return 密文
	 */
	public native String encodeStr(String text,int len);
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        String str ="aceg579;=?ACE=/O";
        
        String encodeStr = encodeStr(str,str.length());
        System.out.println("encodeStr1232133:    "+encodeStr);
        
        
        // aceg579;=?ACE=/O

        
    }


   
}
