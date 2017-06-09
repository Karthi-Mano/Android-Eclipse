package com.bac.bacplatform.SubSplash;

import com.bac.bacplatform.R;
import com.bac.bacplatform.R.layout;
import com.bac.bacplatform.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class BeforeLoginActivity extends Activity {

	static {
		System.loadLibrary("bacplatform");
	}

	/**
	 * 加密字符串
	 * 
	 * @param text
	 *            原文
	 * @param len
	 *            字符串的长度
	 * @return 密文
	 */
	public native String encodeStr(String text, int len);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_before_login);

		String str = "aceg579;=?ACE=/O";

		String encodeStr = encodeStr(str, str.length());
		System.out.println("encodeStr1232133:    " + encodeStr);

	}

}
