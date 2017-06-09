package com.baozi.activity;



import java.util.Map;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
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

//修改密码
public class UpdateActivity extends Activity {
	EditText et_upwd;
	EditText et_npwd;
	EditText et_tpwd;
	String iid;
	String userpwd;
	Button btn;
	public String str_url;
	private PreferencesService service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_updatepwd);
		service = new PreferencesService(UpdateActivity.this);
		Map<String, String> params = service.getPreferences();
		iid = params.get("iid");
		userpwd = params.get("pwd");

		str_url = params.get("app_address");

		et_upwd = (EditText) findViewById(R.id.et_upwd);
		et_npwd = (EditText) findViewById(R.id.et_twopwd);
		et_tpwd = (EditText) findViewById(R.id.et_ruku);

		btn = (Button) findViewById(R.id.btn_sure);

		btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (et_upwd.getText().toString().equals("")
						|| et_npwd.getText().toString().equals("")
						|| et_tpwd.getText().toString().equals("")) {
					showCustomToast("请输入");
					
					return;

				} else if (!et_npwd.getText().toString()
						.equals(et_tpwd.getText().toString())) {
					showCustomToast("两次密码输入不一致");
					
					return;

				} else if (!et_upwd.getText().toString().equals(userpwd)) {
					showCustomToast("用户密码输入不正确");
					
					return;

				} else {
					String url = str_url+"/bank_phone/login/change";

					RequestParams params = new RequestParams(url);
					params.addBodyParameter("iid", iid);
					params.addBodyParameter("cusepassword", et_npwd.getText()
							.toString());

					x.http().post(params, new CommonCallback<String>() {

						@Override
						public void onCancelled(CancelledException arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onError(Throwable arg0, boolean arg1) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onFinished() {
							// TODO Auto-generated method stub

						}

						@Override
						public void onSuccess(String info) {
							showCustomToast("修改密码成功，请重新登录");
							

							System.out.println("info--" + info);
							Intent intent = new Intent(UpdateActivity.this,
									LoginActivity.class);
							startActivity(intent);
							return;

						}

					});

				}

			}
		});

	}
	/** 显示自定义Toast提示(来自String) **/
	protected void showCustomToast(String text) {
		View toastRoot = LayoutInflater.from(UpdateActivity.this).inflate(
				R.layout.common_toast, null);
		((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(UpdateActivity.this);
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
					Log.e("when sendKeyDownUpSync", e.toString());
				}
			}
		}.start();
	}

}
