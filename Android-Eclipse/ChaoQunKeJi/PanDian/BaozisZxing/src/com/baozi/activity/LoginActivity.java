package com.baozi.activity;

import java.util.Map;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import com.example.baoziszxing.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.CheckBox;

import android.widget.EditText;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.baoyuan.view.HandyTextView;
import com.example.entity.User;

import com.example.service.PreferencesService;

import com.google.gson.Gson;

@SuppressLint("ShowToast")
public class LoginActivity extends Activity {
	TextView tv_findpwd;
	EditText et_user;
	EditText et_pwd;
	CheckBox checkBox;
	PreferencesService service;
	String info;
	String str_url;
	User user = new User();
	private ImageView iv;
	
 @Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	System.exit(0);//直接结束程序
}
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);

		service = new PreferencesService(LoginActivity.this);
		tv_findpwd = (TextView) findViewById(R.id.findpwd);
		et_user = (EditText) findViewById(R.id.et_user);
		et_pwd = (EditText) findViewById(R.id.et_pwd);
		checkBox = (CheckBox) findViewById(R.id.checkBox1);
		final Map<String, String> params = service.getPreferences();
		et_user.setText(params.get("jizhuwo"));

		if (params.get("check").equals("1")) {

			et_pwd.setText(params.get("pwd"));
			checkBox.setChecked(true);
		} else {
			et_pwd.setText("");
			checkBox.setChecked(false);
		}

		Button btn_login = (Button) findViewById(R.id.btn_login);

		btn_login.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (et_user.getText().toString().equals("")||et_pwd.getText().toString().equals("")) {
					
					showCustomToast("请输入用户名或密码");
					
					return;

				} else {
					new Thread(new Runnable() {

						@Override
						public void run() {
							str_url = params.get("app_address");
							String url = str_url + "/bank_phone/login/check";

							RequestParams params = new RequestParams(url);
							params.addBodyParameter("cusecode", et_user
									.getText().toString());
							params.addBodyParameter("cusepassword", et_pwd
									.getText().toString());
							x.http().post(params, new CommonCallback<String>() {

								@Override
								public void onCancelled(CancelledException arg0) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onError(Throwable arg0, boolean arg1) {
									showCustomToast("网络连接失败,请检查网络连接是否正常");
									
								}

								@Override
								public void onFinished() {
									// TODO Auto-generated method stub

								}

								@Override
								public void onSuccess(String info) {
									Log.i("ss", info);
									if (info.equals("用户与密码不匹配！")) {
										showCustomToast("用户与密码不匹配");

										return;

									} else if (info.equals("用户:<"
											+ et_user.getText().toString()
											+ ">,不存在，请确认!")) {
										showCustomToast("用户不存在");

										return;

									} else {
										if (checkBox.isChecked()) {
											service.check("1");

										} else {
											service.check("0");
										}

										service.jizhuwo(et_user.getText()
												.toString());

										service.pwd(et_pwd.getText().toString());

										Gson gson = new Gson();
										user = gson.fromJson(info, User.class);
										service.iid(user.getIid());

										showCustomToast("登陆成功");
										Intent intent = new Intent(
												LoginActivity.this,
												TabActivity.class);
										intent.putExtra("user", user);

										startActivity(intent);
									}

								}

							});

						}
					}).start();

				}

			}
		});

	}

	// 点击跳转服务配置页面
	public void setService(View view) {
		Intent intent = new Intent(LoginActivity.this, SetserviceActivity.class);
		startActivity(intent);

	}

	// 点击跳转找回密码页面 跳转之前判断是否输入用户名称并且将用户名存储
	public void setFindPwd(View view) {

		if (et_user.getText().toString().length() == 0) {

			showCustomToast("请选输入用户名");
			return;

		} else if (et_user.getText().toString().length() > 0) {

			// 利用PreferencesService方法保存用户名
			service.saveUser(et_user.getText().toString());

			Intent intent = new Intent(LoginActivity.this, FindPwdActivity.class);
			intent.putExtra("user", user);
			startActivity(intent);
		}

	}

	/** 显示自定义Toast提示(来自String) **/
	protected void showCustomToast(String text) {
		View toastRoot = LayoutInflater.from(LoginActivity.this).inflate(
				R.layout.common_toast, null);
		((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(LoginActivity.this);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}

	


}
