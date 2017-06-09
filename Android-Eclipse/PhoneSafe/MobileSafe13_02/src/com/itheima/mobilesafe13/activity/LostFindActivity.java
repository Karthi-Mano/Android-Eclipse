package com.itheima.mobilesafe13.activity;

import com.itheima.mobilesafe13.utils.MyConstaints;
import com.itheima.mobilesafe13.utils.SPUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author Administrator
 * @comp 黑马程序员
 * @date 2015-9-13
 * @desc 手机防盗界面

 * @version $Rev: 17 $
 * @author $Author: goudan $
 * @Date  $Date: 2015-09-13 15:08:36 +0800 (Sun, 13 Sep 2015) $
 * @Id    $Id: LostFindActivity.java 17 2015-09-13 07:08:36Z goudan $
 * @Url   $URL: https://188.188.4.100/svn/mobilesafeserver/trunk/MobileSafe13/src/com/itheima/mobilesafe13/activity/LostFindActivity.java $
 * 
 */
public class LostFindActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//直接进行逻辑判断 是否设置向导完成
		if (SPUtils.getBoolean(getApplicationContext(), MyConstaints.ISSETUPFINISH, false)) {
			//设置向导完成
			initView();
		} else {
			//设置向导未完成,进入第一个设置向导界面
			Intent intent = new Intent(this,Setup1Activity.class);
			startActivity(intent);
			//关闭自己
			finish();
			
		}
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		
		
	}
}
