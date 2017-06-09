package com.itheima.googleplay_13.base;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


import android.widget.Toast;

import com.itheima.googleplay_13.MainActivity;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-25 下午6:07:42
 * @描述	     TODO
 *
 * @版本       $Rev: 69 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-25 18:21:15 +0800 (星期日, 25 十月 2015) $
 * @更新描述    TODO
 */
public abstract class BaseActivity extends FragmentActivity {
	/**
	* 1.只关心自己定义的方法,不用关心相关的生命周期方法
	* 2.还可以定义哪些方法是"必须实现",哪些方法是"选择性的实现"
	* 3.放置共有的属性以及共有的方法
	*/
	// 共有的方法
	// 1.应用的完全退出
	// 2.显示最前端activity
	// 3.退出提示
	BaseActivity				mTopActivity;
	private List<BaseActivity>	activitys	= new LinkedList<BaseActivity>();
	private long				mPreClickTime;

	public BaseActivity getTopActivity() {
		return mTopActivity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		initView();
		initData();
		initListener();
	}

	@Override
	protected void onResume() {
		mTopActivity = this;
		activitys.add(this);
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		activitys.remove(this);
		super.onDestroy();
	}

	protected abstract void initView();

	protected void init() {

	}

	protected void initData() {

	}

	protected void initListener() {

	}

	/**完全退出*/
	public void exit() {
		for (BaseActivity baseActivity : activitys) {
			baseActivity.finish();
		}
	}

	@Override
	public void onBackPressed() {
		if (this instanceof MainActivity) {// 主页
			if (System.currentTimeMillis() - mPreClickTime > 2000) {// 两次点击的间隔大于2s中
				Toast.makeText(getApplicationContext(), "再按一次,退出谷歌市场", 0).show();
				mPreClickTime = System.currentTimeMillis();
				return;
			} else {
				// 完全退出
				exit();
			}
		} else {
			super.onBackPressed();// finish
		}
	}
}
