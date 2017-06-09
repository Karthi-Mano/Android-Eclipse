package com.gs.qzg.activity;

import org.apache.commons.lang.StringUtils;

import com.gs.qzg.greensport.PalmDB;
import com.gs.qzg.greensport.R;
import com.gs.qzg.greensport.R.id;
import com.gs.qzg.greensport.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText username;
	private EditText passwd;
	PalmDB palmDB;
	Cursor cursor;
	Button login_btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		
		palmDB = new PalmDB(LoginActivity.this);

		cursor = palmDB.select();
		long row = palmDB.insert("123","456");
		long row1 = palmDB.insert("456","567");
		long row2 = palmDB.insert("234","789");
		cursor.requery();	
		
		username = (EditText)findViewById(R.id.login_username);
		passwd = (EditText)findViewById(R.id.login_password);
		
		login_btn = (Button)findViewById(R.id.login_btn);
		login_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				if (StringUtils.isEmpty(username.getText().toString())) {
					Toast.makeText(LoginActivity.this, "请输入帐号", Toast.LENGTH_LONG).show();
				}
				else if (StringUtils.isEmpty(passwd.getText().toString())) {
					Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_LONG).show();
				}
				else{
				while (cursor.moveToNext()) {
					if (username.getText().toString().equals(cursor.getString(1))&&passwd.getText().toString().equals(cursor.getString(2))) {
						Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
						intent.putExtra("check_index", 0);
						SharedPreferences.Editor editor = getSharedPreferences("test", Activity.MODE_PRIVATE).edit(); 
						//用putString的方法保存数据 
						editor.putString("name", "1"); 
						//提交当前数据 
						editor.commit(); 
						startActivity(intent);
						break;
					}
					else {
						Toast.makeText(LoginActivity.this, "帐号或者密码错误，请重新输入", Toast.LENGTH_LONG).show();
						Intent intent = new Intent(LoginActivity.this,LoginActivity.class);
						startActivity(intent);
						overridePendingTransition(0, 0);
						break;
					}
					
				}
				
				}
			}
		});
		
		
	}
}
