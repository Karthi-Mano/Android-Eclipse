package com.example.activity;

import java.util.Map;

import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.Callback.CancelledException;
import org.xutils.http.RequestParams;

import com.baoyuan.view.HandyTextView;
import com.example.service.PreferencesService;
import com.example.test.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//服务配置页面
public class SetserviceActivity extends Activity {

	EditText et_address;
	Button btn_show;
	TextView tv_showRrror;
	TextView tv_configurationAdress;

	private PreferencesService service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_setservice);
		et_address = (EditText) findViewById(R.id.et_upwd);
		tv_showRrror = (TextView) findViewById(R.id.tv_showerror);
		tv_configurationAdress = (TextView) findViewById(R.id.tv_configurationAdress);
		service = new PreferencesService(SetserviceActivity.this);
		Map<String, String> params = service.getPreferences();
		String address =params.get("app_address");
		et_address.setText(params.get("app_address"));
	
		if (address.length()!=0) {
			
			et_address.setText(params.get("app_address").subSequence(7,
					params.get("app_address").length()));

		}
	}

	// 判断连接是否正确
	@SuppressLint("ShowToast")
	public void doAddress(View view) {
		final String str_address = et_address.getText().toString();
		String url = "http://"+str_address + "/bank_phone/index.jsp";
		RequestParams params = new RequestParams(url);
		params.setConnectTimeout(200);
		params.setMaxRetryCount(200);
		x.http().post(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String o) {
				service.save("http://"+str_address);

				// 如果连接正确的话，tv_showRrror设置显示并且设置颜色 显示10ms后消失
				tv_showRrror.setVisibility(tv_showRrror.VISIBLE);
				tv_configurationAdress
						.setVisibility(tv_configurationAdress.INVISIBLE);

				tv_showRrror.setText("连接成功");
				
				tv_showRrror.setTextColor(Color.GREEN);
				tv_showRrror.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						tv_showRrror.setVisibility(View.INVISIBLE);
						tv_configurationAdress
								.setVisibility(tv_configurationAdress.VISIBLE);
						
						Intent intent = new Intent(SetserviceActivity.this,
								MainActivity.class);
						intent.putExtra("et_address", et_address.getText()
								.toString());
						startActivity(intent);

					}
				}, 2000);// 2s 后设置不可见
				

			}

			@Override
			public void onError(Throwable throwable, boolean b) {
				et_address.setVisibility(et_address.VISIBLE);

				tv_showRrror.setVisibility(tv_showRrror.VISIBLE);
				tv_configurationAdress
						.setVisibility(tv_configurationAdress.INVISIBLE);

				tv_showRrror.setText("连接失败");
				tv_showRrror.setTextColor(Color.RED);
				tv_showRrror.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						tv_showRrror.setVisibility(View.INVISIBLE);
						tv_configurationAdress
								.setVisibility(tv_configurationAdress.VISIBLE);

					}
				}, 2000);// 2秒 后设置不可见

			}

			@Override
			public void onCancelled(CancelledException e) {

			}

			@Override
			public void onFinished() {

			}
		});

	}

	/** 显示自定义Toast提示(来自String) **/
	protected void showCustomToast(String text) {
		View toastRoot = LayoutInflater.from(SetserviceActivity.this).inflate(
				R.layout.common_toast, null);
		((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(SetserviceActivity.this);
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
