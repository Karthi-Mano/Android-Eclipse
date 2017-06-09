package com.example.activity;

import java.util.Map;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import android.annotation.SuppressLint;
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

import com.baoyuan.view.HandyTextView;
import com.example.service.PreferencesService;
import com.example.test.R;

//登陆页面点击找回密码跳转的页面
@SuppressLint("ShowToast")
public class FindPwdActivity extends Activity {
	private Button btn_next;
	EditText et_pwdWenti;
	EditText et_pwddaan;

	private PreferencesService service;
	String cusecode;
	String str_url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_repwd);
		btn_next = (Button) findViewById(R.id.btn_next);
		et_pwdWenti = (EditText) findViewById(R.id.et_wenti);
		et_pwddaan = (EditText) findViewById(R.id.et_daan);

		service = new PreferencesService(FindPwdActivity.this);
		Map<String, String> params = service.getPreferences();
		cusecode = params.get("username");
		str_url = params.get("app_address");

		btn_next.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (et_pwdWenti.getText().toString().equals("")
						|| et_pwddaan.getText().toString().equals("")) {
					showCustomToast("请输入问题或者答案");
					
					return;

				} else {

					String url = str_url+"/bank_phone/login/getPwdBack";
					RequestParams params = new RequestParams(url);
					params.addBodyParameter("cusecode", cusecode);
					params.addBodyParameter("cquestion", et_pwdWenti.getText()
							.toString());
					params.addBodyParameter("canswer", et_pwddaan.getText()
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
						public void onSuccess(String iid) {
							System.out.println("info" + iid);
							if (iid.equals("您的问题或答案不正确！")) {
								showCustomToast(iid);
								return;

							} else if (iid.equals("用户:<" + cusecode
									+ ">,不存在，请确认!")) {
								showCustomToast(iid);
								return;
							} else {
								Intent intent =  new Intent(FindPwdActivity.this,ChongzhiPwdActivity.class);
								intent.putExtra("iid", iid);
								startActivity(intent);
								return;
							}

						}

					});

				}
			}
		});

	}
	/** 显示自定义Toast提示(来自String) **/
	protected void showCustomToast(String text) {
		View toastRoot = LayoutInflater.from(FindPwdActivity.this).inflate(
				R.layout.common_toast, null);
		((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(FindPwdActivity.this);
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
