package com.example.mvpdemo_8.view;

/**
 * @author  Administrator
 * @time 	2015-7-22 下午2:38:16
 * @des	定义可能有的ui操作
 *
 * @version $Rev$
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes TODO
 */
public interface ISplashView {
	/**
	用户进入splash界面
	判断网络是否存在->显示加载框
	如果存在 -->进入下一个页面
	如果不存在-->提示网络错误
	-->隐藏加载框
	 */
	/**显示加载框*/
	void showLoadingDialog();

	/** 进入下一个页面*/
	void startNextActivity();

	/**提示网络错误*/
	void showNetWorkError();

	/**隐藏加载框*/
	void hideLoadingDialog();

}
