package com.itheima.mobilesafe13.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.itheima.mobilesafe13.R;

/**
 * @author Administrator
 * @desc  看门狗的输入密码界面
 * @date 2015-9-25
 * @Author $Author: goudan $ Id Rev URL
 * @Date $Date: 2015-09-25 16:53:55 +0800 (Fri, 25 Sep 2015) $
 * @Id $Id: EnterPassLockActivity.java 109 2015-09-25 08:53:55Z goudan $
 * @Rev $Rev: 109 $
 * @URL $URL: https://188.188.4.100/svn/mobilesafeserver/trunk/MobileSafe13/src/com/itheima/mobilesafe13/activity/EnterPassLockActivity.java $
 *
 */
public class EnterPassLockActivity extends Activity {
	private EditText et_pass;
	private ImageView iv_icon;
	private String packName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initView();
		initData();
	
		
	}
	private void initData() {
		packName = getIntent().getStringExtra("packname");
		PackageManager pm = getPackageManager();
		try {
			iv_icon.setImageDrawable(pm.getApplicationIcon(packName));
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//图标
		
		
	}
	public void enter(View v){
		String password = et_pass.getText().toString().trim();
		if (TextUtils.isEmpty(password)) {
			Toast.makeText(getApplicationContext(), "密码不能为空", 1).show();
			return ;
		}
		
		if (password.equals("1")) {
			// 告诉看门狗是熟人，放行
			Intent intent = new Intent("itheima.shuren");
			intent.putExtra("shuren", packName);
			sendBroadcast(intent);
			//关闭
			finish();
		} else {
			Toast.makeText(getApplicationContext(), "坏人", 1).show();
			return ;
		}
	}
	private void initView() {
		setContentView(R.layout.activity_lock_pass);
		
		et_pass = (EditText) findViewById(R.id.et_lock_pass_password);
		iv_icon = (ImageView) findViewById(R.id.iv_lock_pass_appicon);
		
		
	}
}
