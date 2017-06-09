package com.treaker.qqlogin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText et_qq;
	private EditText et_pwd;
	private CheckBox cb_rem;
	private static final String TAG= "MainActivity";
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		et_qq = (EditText) findViewById(R.id.am_et_qq);
		et_pwd = (EditText) findViewById(R.id.am_et_pwd);
		cb_rem = (CheckBox) findViewById(R.id.am_cb_rem);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		readSaveData();
	}

	private void readSaveData() {
		String qq = sp.getString("qq", "");
		String pwd = sp.getString("pwd", "");
		et_qq.setText(qq);
		et_pwd.setText(pwd);
	}

	public void login(View view) {
		String qq = et_qq.getText().toString().trim();
		String pwd = et_pwd.getText().toString().trim();
		if (TextUtils.isEmpty(qq) || TextUtils.isEmpty(pwd)) {
			Toast.makeText(this, "账号为空或者密码为空", 1).show();
			return;
		} 
		if(cb_rem.isChecked()){
			Log.i(TAG , "保存密码");
			
				Editor editor = sp.edit();
				editor.putString("qq", qq);
				editor.putString("pwd", pwd);
				editor.commit();
				Toast.makeText(this, "保存成功", 1).show();
			
		}else{
			Log.i(TAG , "不保存密码");
			
		}

	}

}
