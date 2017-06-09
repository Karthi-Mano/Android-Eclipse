package com.baozi.activity;



import java.util.Map;

import org.xutils.x;
import org.xutils.common.Callback;

import org.xutils.http.RequestParams;

import com.baoyuan.view.HandyTextView;
import com.example.baoziszxing.R;
import com.example.service.PreferencesService;


import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//重置密码
public class ChongzhiPwdActivity extends Activity {

	String iid;
	EditText et_npwd;
	EditText et_twoPwd;
	Button btn;
	PreferencesService service;
	String str_url;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_resetpwd);

		service = new PreferencesService(ChongzhiPwdActivity.this);
		et_npwd = (EditText) findViewById(R.id.et_upwd);
		Map<String, String> params = service.getPreferences();
		str_url = params.get("app_address");
		PreferencesService service;
		et_twoPwd = (EditText) findViewById(R.id.et_twopwd);
		btn = (Button) findViewById(R.id.btn_sure);
		Intent intent = getIntent();
		final String iid = intent.getStringExtra("iid");

		btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (et_npwd.getText().toString().equals("")
						|| et_twoPwd.getText().toString().equals("")) {
					showCustomToast("请输入密码");
				
					return;

				} else if (!et_npwd.getText().toString()
						.equals(et_twoPwd.getText().toString())) {
					showCustomToast("两次密码输入不一致，请重新输入");
					
					return;
				

				} else {
					String url = str_url + "/bank_phone/login/change";
					RequestParams params = new RequestParams(url);
					params.addBodyParameter("iid", iid);
					params.addBodyParameter("cusepassword", et_npwd.getText()
							.toString());
					x.http().post(params,
							new Callback.CommonCallback<String>() {
								@Override
								public void onSuccess(String o) {
									String info = o;
									System.out.println("info" + info);
									showCustomToast("重置密码成功，请重新登录");
									
									Intent intent = new Intent(
											ChongzhiPwdActivity.this,
											LoginActivity.class);
									startActivity(intent);

								}

								@Override
								public void onError(Throwable throwable,
										boolean b) {

								}

								@Override
								public void onCancelled(CancelledException e) {

								}

								@Override
								public void onFinished() {

								}
							});

				}

			}
		});

	}
	/** 显示自定义Toast提示(来自String) **/
	protected void showCustomToast(String text) {
		View toastRoot = LayoutInflater.from(ChongzhiPwdActivity.this).inflate(
				R.layout.common_toast, null);
		((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(ChongzhiPwdActivity.this);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}
	// 返回上一级
	public void onBack(View v) {

		new Thread() {
			public void run() {
				try {
					Instrumentation inst = new Instrumentation();
					inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
				} catch (Exception e) {
					Log.e("Exception when sendKeyDownUpSync", e.toString());
				}
			}
		}.start();
	}

}
