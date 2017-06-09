package com.gs.qzg.activity;

/*
 * 首页
 */
import com.gs.qzg.Fragment.GuideFragment;
import com.gs.qzg.Fragment.InfoFragment;
import com.gs.qzg.Fragment.PlanFragment;
import com.gs.qzg.Fragment.SettingsFragment;
import com.gs.qzg.greensport.R;
import com.gs.qzg.greensport.R.id;
import com.gs.qzg.greensport.R.layout;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;


public class HomeActivity extends FragmentActivity implements OnClickListener {


	private GuideFragment guideFragment;


	private InfoFragment infoFragment;


	private PlanFragment planFragment;

	/**
	 * 用于展示设置的Fragment
	 */
	private SettingsFragment settingFragment;
	

	private View guideLayout;
	private View infoLayout;

	private View planLayout;


	private View settingLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		
		// 初始化布局元素
		initViews();
		
		// 第一次启动时选中第0个tab
		try {
			int check_index = this.getIntent().getIntExtra("check_index", 0);
			setTabSelection(check_index);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void setTabSelection(int check_index) {
		// TODO Auto-generated method stub
		if (check_index == 0) {
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction transaction = fm.beginTransaction();
			guideFragment = new GuideFragment();
			
//			menuCustomerImage.setImageResource(R.drawable.custom_current);
//			menuCustomerText.setTextColor(Color.parseColor("#007cf0"));
			guideLayout.setClickable(false);
			
//			menuCargoImage.setImageResource(R.drawable.goods_default);
//			menuCargoText.setTextColor(Color.parseColor("#82858b"));
			planLayout.setClickable(true);
			
//			menuWoImage.setImageResource(R.drawable.profile_default);
//			menuWoText.setTextColor(Color.parseColor("#82858b"));
			infoLayout.setClickable(true);
			
			settingLayout.setClickable(true);
			
			transaction.replace(R.id.content, guideFragment);
			transaction.commit();
		}
		else if (check_index == 1) {
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction transaction = fm.beginTransaction();
			planFragment = new PlanFragment();
			
//			menuCustomerImage.setImageResource(R.drawable.custom_default);
//			menuCustomerText.setTextColor(Color.parseColor("#82858b"));
			guideLayout.setClickable(true);
			
//			menuCargoImage.setImageResource(R.drawable.goods_current);
//			menuCargoText.setTextColor(Color.parseColor("#007cf0"));
			planLayout.setClickable(false);
			
//			menuWoImage.setImageResource(R.drawable.profile_default);
//			menuWoText.setTextColor(Color.parseColor("#82858b"));
			infoLayout.setClickable(true);
			
			settingLayout.setClickable(true);
			
			transaction.replace(R.id.content, planFragment);
			transaction.commit();
		}
		else if (check_index == 2) {
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction transaction = fm.beginTransaction();
			infoFragment = new InfoFragment();
			
//			menuCustomerImage.setImageResource(R.drawable.custom_default);
//			menuCustomerText.setTextColor(Color.parseColor("#82858b"));
			guideLayout.setClickable(true);
			
//			menuCargoImage.setImageResource(R.drawable.goods_default);
//			menuCargoText.setTextColor(Color.parseColor("#82858b"));
			planLayout.setClickable(true);
			
//			menuWoImage.setImageResource(R.drawable.profile_current);
//			menuWoText.setTextColor(Color.parseColor("#007cf0"));
			infoLayout.setClickable(false);
			
			settingLayout.setClickable(true);
			
			transaction.replace(R.id.content, infoFragment);
			transaction.commit();
		}
		else if (check_index == 3) {
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction transaction = fm.beginTransaction();
			settingFragment = new SettingsFragment();
			
//			menuCustomerImage.setImageResource(R.drawable.custom_default);
//			menuCustomerText.setTextColor(Color.parseColor("#82858b"));
			guideLayout.setClickable(true);
			
//			menuCargoImage.setImageResource(R.drawable.goods_default);
//			menuCargoText.setTextColor(Color.parseColor("#82858b"));
			planLayout.setClickable(true);
			
//			menuWoImage.setImageResource(R.drawable.profile_current);
//			menuWoText.setTextColor(Color.parseColor("#007cf0"));
			infoLayout.setClickable(true);
			
			settingLayout.setClickable(false);
			
			transaction.replace(R.id.content, settingFragment);
			transaction.commit();
		}
	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.teacher_layout://客户
			// 当点击了设置tab时，选中第1个tab
			setTabSelection(0);
			break;
		case R.id.plan_layout:
			// 当点击了消息tab时，选中第2个tab
			setTabSelection(1);
			break;

		case R.id.info_layout:
			// 当点击了动态tab时，选中第3个tab
			setTabSelection(2);
			break;
		case R.id.setting_layout:
			setTabSelection(3);
			break;
		}

	}
	
	/**
	 * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
	 */
	private void initViews() {
		planLayout = findViewById(R.id.plan_layout);
		infoLayout = findViewById(R.id.info_layout);
		guideLayout = findViewById(R.id.teacher_layout);
		settingLayout = findViewById(R.id.setting_layout);
		
//		menuCustomerImage = (ImageView)findViewById(R.id.menu_customer_image);
//		menuCustomerText = (TextView)findViewById(R.id.menu_customer_text);
//		
//		menuCargoImage = (ImageView)findViewById(R.id.menu_cargo_image);
//		menuCargoText = (TextView)findViewById(R.id.menu_cargo_text);
//		
//		menuWoImage = (ImageView)findViewById(R.id.menu_wo_image);
//		menuWoText = (TextView)findViewById(R.id.menu_wo_text);

		planLayout.setOnClickListener(this);
		infoLayout.setOnClickListener(this);
		guideLayout.setOnClickListener(this);
		settingLayout.setOnClickListener(this);
	}


	/**
	 * 监听返回--是否退出程序
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean flag = true;
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		} else {
			flag = super.onKeyDown(keyCode, event);
		}
		return flag;
	}
}



