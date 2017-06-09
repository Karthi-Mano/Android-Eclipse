package com.example.activity;




import java.io.File;
import java.util.Map;

import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.http.RequestParams;

import com.baoyuan.view.HandyTextView;
import com.example.service.PreferencesService;
import com.example.test.R;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.pm.PackageInfo;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



//检查更新
public class RenewalActivity extends Activity {
	TextView tv_dangqianbanben;
	ProgressBar progBar;
	public String str_url;

	private PreferencesService service;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_renewal);
		tv_dangqianbanben = (TextView) findViewById(R.id.tv_dangqianbanben);
		int localVersion=geatLocalVersion(RenewalActivity.this);
		tv_dangqianbanben.setText("v"+localVersion);

		service = new PreferencesService(RenewalActivity.this);
		Map<String, String> param = service.getPreferences();
		
		
		str_url = param.get("app_address");
		/**
		 * 下载文件
		 */
		String url =str_url+"/bank_phone/servlet/DownloadFilePhone";
		RequestParams params = new RequestParams(url);
		params.setAutoResume(true);//设置是否在下载是自动断点续传
        params.setAutoRename(false);//设置是否根据头信息自动命名文件
        params.setSaveFilePath("/sdcard/xutils/xUtils_1.avi");
        params.setExecutor(new PriorityExecutor(2));//自定义线程池,有效的值范围[1, 3], 设置为3时, 可能阻塞图片加载.
        params.setCancelFast(true);//是否可以被立即停止.
        x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onCancelled(CancelledException arg0) {
                System.out.println("onCancelled");
            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                // TODO Auto-generated method stub
                System.out.println("onError"+arg0);
            }

            @Override
            public void onFinished() {
                // TODO Auto-generated method stub
                System.out.println("onFinished");
            }

            @Override
            public void onSuccess(File arg0) {
                // TODO Auto-generated method stub
                System.out.println("onSuccess");
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                if (isDownloading) {
                    
                }
            }

            @Override
            public void onStarted() {
                // TODO Auto-generated method stub
            }

            @Override
            public void onWaiting() {
                // TODO Auto-generated method stub
            }

        });
	}

	public static int geatLocalVersion(Context context) {
		int localVersion = 0;
		try {
			PackageInfo packageInfo = context.getApplicationContext()
					.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			localVersion = packageInfo.versionCode;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return localVersion;

	}
	/** 显示自定义Toast提示(来自String) **/
	protected void showCustomToast(String text) {
		View toastRoot = LayoutInflater.from(RenewalActivity.this).inflate(
				R.layout.common_toast, null);
		((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(RenewalActivity.this);
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
