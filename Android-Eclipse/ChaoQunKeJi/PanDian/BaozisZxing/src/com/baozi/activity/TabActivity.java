package com.baozi.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.MyFragmentAdapter;
import com.example.baoziszxing.R;

import com.example.entity.User;
import com.example.fragment.PanDianFragment;

import com.example.fragment.SelectFragment;
import com.example.fragment.UserFragment;
import com.example.service.PreferencesService;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class TabActivity extends FragmentActivity {
	private RadioButton rb_job, rb_select, rb_user;
	private RadioGroup group;
	private ViewPager vPager;
	private List<Fragment> list = new ArrayList<Fragment>();
	private MyFragmentAdapter adapter;
	private final int[] array = new int[] { R.id.rb_job, R.id.rb_select,
			R.id.rb_user };
	private PreferencesService service;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_tab);
		init();
		Intent intent = getIntent();
		User user = intent.getParcelableExtra("user");
		
		service = new PreferencesService(TabActivity.this);
		service.saveName(user.getCname());
		service.saveID(user.getIid());
		service.cusecode(user.getCusecode());
	
		adapter = new MyFragmentAdapter(getSupportFragmentManager(), list);
		vPager.setAdapter(adapter);
		vPager.setOffscreenPageLimit(2);
		vPager.setCurrentItem(0);
		vPager.setOnPageChangeListener(pageChangeListener);

	}

	public void init() {
		vPager = (ViewPager) findViewById(R.id.vpager);
		rb_job = (RadioButton) findViewById(R.id.rb_job);
		rb_select = (RadioButton) findViewById(R.id.rb_select);
		rb_user = (RadioButton) findViewById(R.id.rb_user);
		group = (RadioGroup) findViewById(R.id.ll_rbtn_contain);

		PanDianFragment jobFragment = new PanDianFragment();
		SelectFragment selectFragment = new SelectFragment();
		UserFragment userFragment = new UserFragment();
		list.add(jobFragment);
		list.add(selectFragment);
		list.add(userFragment);

		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// 设置了ViewPager的当前item就会触发ViewPager的SimpleOnPageChangeListener监听函数
				switch (checkedId) {
				case R.id.rb_job:
					vPager.setCurrentItem(0);
					break;
				case R.id.rb_select:
					vPager.setCurrentItem(1);
					break;
				case R.id.rb_user:
					vPager.setCurrentItem(2);
					break;
				}

			}
		});

	}

	SimpleOnPageChangeListener pageChangeListener = new SimpleOnPageChangeListener() {
		// 不管是Viewpager滑动,还是RadioGroup点击 都会调用这个方法
		public void onPageSelected(int position) {
			change(array[position]);
		}
	};

	/**
	 * 改变背景颜色,背景图片
	 * 
	 * @param checkedId
	 */
	private void change(int checkedId) {

		switch (checkedId) {
		case R.id.rb_job:

			rb_job.setChecked(true);
			break;
		case R.id.rb_select:

			rb_select.setChecked(true);
			break;
		case R.id.rb_user:

			rb_user.setChecked(true);
			break;

		}
	}

}