package com.itheima.googleplay_13.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-20 下午3:36:28
 * @描述	    Fragment基类,显示常规抽取
 *
 * @版本       $Rev: 9 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-20 15:46:19 +0800 (星期二, 20 十月 2015) $
 * @更新描述    TODO
 */
public abstract class BaseFragmentCommon extends Fragment {
	/**
	 * 1.只关心自己定义的方法,不用关心相关的生命周期方法
	 * 2.还可以定义哪些方法是"必须实现",哪些方法是"选择性的实现"
	 * 3.放置共有的属性以及共有的方法
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		init();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return initView();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		initData();
		initListener();
		super.onActivityCreated(savedInstanceState);
	}
	/**
	 * @des 初始化
	 * @call 子类可以选择性的覆写该方法
	 */
	protected void init() {

	}
	/**
	 * @des 初始化视图
	 * @call 必须实现,基类不知道具体实现,定义成为抽象方法,交给子类具体实现
	 */
	protected abstract View initView();
	/**
	 * @des 初始化数据
	 * @call 子类可以选择性的覆写该方法
	 */
	protected void initData() {

	}
	/**
	 * @des 初始化监听
	 * 子类可以选择性的覆写该方法
	 */
	protected void initListener() {

	}

}
