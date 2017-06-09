package com.wjz.smart1.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.wjz.smart1.R;
import com.wjz.smart1.view.LeftMenuFragment;
import com.wjz.smart1.view.MainContentFragment;

public class MainActivity extends SlidingFragmentActivity {

	private static final String LEFT_MENU_TAG = "left_menu_tag";
	private static final String MAIN_CONTENTE_TAG = "main_contente_tag";
	private FragmentManager manager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();

		initEvent();
		initData();
	}

	private void initData() {

		// 获取Fragment管理器
		manager = getSupportFragmentManager();

		// 1.获取事物
		FragmentTransaction beginTransaction = manager.beginTransaction();
		
		
		// 2.替换
		// i.左侧菜单的替换
		beginTransaction.replace(R.id.fl_left_menu_tag, new LeftMenuFragment(),
				LEFT_MENU_TAG);
		// ii.主界面的替换
		beginTransaction.replace(R.id.fl_main_content_tag,
				new MainContentFragment(), MAIN_CONTENTE_TAG);

		
		// 3.提交事务
		beginTransaction.commit();
	}

	public void initEvent() {
		// TODO Auto-generated method stub

	}

	public void initView() {

		// 初始化SlidingFragmengActivity
		// 设置主界面
		setContentView(R.layout.fragment_main_content_tag);
		// 设置左侧菜单
		setBehindContentView(R.layout.fragment_left_menu_tag);

		// 获取左侧菜单对象
		SlidingMenu sm = getSlidingMenu();

		// 设置菜单的滑动方式
		sm.setMode(SlidingMenu.LEFT);

		// 设置触发方式
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		// 设置主界面剩余像素
		sm.setBehindOffset(300);

	}

}
