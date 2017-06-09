package com.example.mvpdemo_8.presenter;

import android.content.Context;

import com.example.mvpdemo_8.model.INetWork;
import com.example.mvpdemo_8.model.impl.INetWorkImpl;
import com.example.mvpdemo_8.view.ISplashView;

/**
 * @author  Administrator
 * @time 	2015-7-22 下午2:42:31
 * @des	处理ui显示逻辑
 *
 * @version $Rev$
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes TODO
 */
public class SplashPresenter {
	// 面向接口编程
	INetWork	mINetWork;
	ISplashView	mISplashView;

	public SplashPresenter(ISplashView iSplashView) {
		super();
		mINetWork = new INetWorkImpl();
		mISplashView = iSplashView;
	}

	public void doUiLogic(Context context) {
		/**	
		用户进入splash界面
		判断网络是否存在->显示加载框
		如果存在 -->进入下一个页面
		如果不存在-->提示网络错误
		-->隐藏加载框
		*/
		mISplashView.showLoadingDialog();
		if (mINetWork.isNetWorkOk(context)) {// 网络可用
			mISplashView.startNextActivity();
		} else {// 网络不可用
			mISplashView.showNetWorkError();
		}
		mISplashView.hideLoadingDialog();
	}

}
