package com.treaker.qqlogin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import android.os.Bundle;
import android.app.Activity;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		et_qq = (EditText) findViewById(R.id.am_et_qq);
		et_pwd = (EditText) findViewById(R.id.am_et_pwd);
		cb_rem = (CheckBox) findViewById(R.id.am_cb_rem);
		readSaveData();
	}

	private void readSaveData() {
		File file = new File(getFilesDir(),"info.txt");
		if(file.exists()&&file.length()>0){
			try {
				FileInputStream fis = openFileInput("info.txt");
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				String info = br.readLine();
				String qq = info.split("###")[0];
				String pwd = info.split("###")[1];
				et_qq.setText(qq);
				et_pwd.setText(pwd);
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
			try {
				FileOutputStream fos = openFileOutput("info.txt", MODE_PRIVATE);
				fos.write((qq+"###"+pwd).getBytes());
				fos.close();
				Toast.makeText(this, "保存成功", 1).show();
			} catch (Exception e) {
				Toast.makeText(this, "保存保存失败功", 1).show();
				e.printStackTrace();
			}
		}else{
			Log.i(TAG , "不保存密码");
			
		}

	}

}
