package com.example.mvpdemo_8;

import com.example.mvpdemo_8.presenter.SplashPresenter;
import com.example.mvpdemo_8.view.ISplashView;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**	
	用户进入splash界面
	判断网络是否存在->显示加载框
	如果存在 -->进入下一个页面
	如果不存在-->提示网络错误
	-->隐藏加载框
 */
public class SplashActivity extends Activity implements ISplashView {
	SplashPresenter	mSplashPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSplashPresenter = new SplashPresenter(this);
	}

	@Override
	protected void onResume() {
		mSplashPresenter.doUiLogic(this);
		super.onResume();
	}

	/*=============== ui具体展示 ===============*/
	@Override
	public void showLoadingDialog() {
		System.out.println("--------------showLoadingDialog--------------");

	}

	@Override
	public void startNextActivity() {
		// TODO
		System.out.println("--------------startNextActivity--------------");
	}

	@Override
	public void showNetWorkError() {
		// TODO
		System.out.println("--------------showNetWorkError--------------");
	}

	@Override
	public void hideLoadingDialog() {
		// TODO
		System.out.println("--------------hideLoadingDialog--------------");
	}
}
