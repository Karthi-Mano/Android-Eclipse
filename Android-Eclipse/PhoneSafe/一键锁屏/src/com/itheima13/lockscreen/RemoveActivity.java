package com.itheima13.lockscreen;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class RemoveActivity extends Activity {

	private ComponentName mDeviceAdminSample;
	private DevicePolicyManager mDPM;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		mDeviceAdminSample = new ComponentName(this,
				MyDeviceAdminReceiver.class);
		mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		
		//取消激活
		mDPM.removeActiveAdmin(mDeviceAdminSample);//取消激活
		//卸载
		
        Intent intent = new Intent("android.intent.action.DELETE");
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent); //卸载意图
		//lockScreen(null);
		
	}

	public void lockScreen(View v) {
		// 一键锁屏
		
		// 判断 是否激活
		
		
		boolean adminActive = mDPM.isAdminActive(mDeviceAdminSample);
		if (adminActive) {
			mDPM.lockNow();// 锁屏
			finish();//退出
		} else {
			jihuo(null);
		}
	}

	public void jihuo(View v) {
		// 打开激活设备管理器的界面
		 Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
         intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdminSample);
         intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                 "开启激活设备管理");
         startActivityForResult(intent, 1);

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		boolean adminActive = mDPM.isAdminActive(mDeviceAdminSample);
		if (adminActive) {
			mDPM.lockNow();// 锁屏
			finish();//退出
		} 
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
