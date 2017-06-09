package com.itheima.mobilesafe13.activity;


import com.itheima.mobilesafe13.R;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author Administrator
 * @comp 黑马程序员
 * @date 2015-9-12
 * @desc 手机卫士的主界面

 * @version $Rev: 8 $
 * @author $Author: goudan $
 * @Date  $Date: 2015-09-12 14:21:09 +0800 (Sat, 12 Sep 2015) $
 * @Id    $Id: HomeActivity.java 8 2015-09-12 06:21:09Z goudan $
 * @Url   $URL: https://188.188.4.100/svn/mobilesafeserver/trunk/MobileSafe13/src/com/itheima/mobilesafe13/activity/HomeActivity.java $
 * 
 */
public class HomeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initView();
	}

	private void initView() {
		setContentView(R.layout.activity_home);
	}
}
