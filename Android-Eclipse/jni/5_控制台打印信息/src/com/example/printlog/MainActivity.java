package com.example.printlog;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	static{
		System.loadLibrary("showlog");
	}
	
	private EditText et_username;
	private EditText et_password;
	private EditText et_money;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		et_money = (EditText) findViewById(R.id.et_money);
    }

    public void pay(View view){
		String username = et_username.getText().toString().trim();
		String password = et_password.getText().toString().trim();
		String money = et_money.getText().toString().trim();
		
		if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)||TextUtils.isEmpty(money)){
			Toast.makeText(this, "数据输入错误", 0).show();
			return;
		}
		
		int result = safePay(username, password, Float.parseFloat(money));
		switch (result) {
		case 200:
			Toast.makeText(this, "支付成功", 0).show();
			break;

		case 404:
			Toast.makeText(this, "用户名密码错误", 0).show();
			break;
		case 250:
			Toast.makeText(this, "余额不足", 0).show();
			break;
		}
	}
  
    /**
	 * 由c代码实现的安全支付的方法
	 * @param username 用户名
	 * @param password 密码
	 * @param money 金钱
	 * @return 200支付成功 404 用户名密码错误  250余额不足
	 */
	public native int safePay(String username,String password, float money);
	
}
