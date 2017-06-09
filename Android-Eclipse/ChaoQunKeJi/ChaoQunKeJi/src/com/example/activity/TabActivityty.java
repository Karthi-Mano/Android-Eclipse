package com.example.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.example.entity.User;
import com.example.fragment.ChaXunFragment;
import com.example.fragment.ChuKuFragment;
import com.example.fragment.GuiHuanFragment;
import com.example.fragment.JieYongFragment;
import com.example.fragment.PanDianFragment;
import com.example.fragment.RukuFragment;
import com.example.fragment.UserFragment;
import com.example.service.PreferencesService;
import com.example.test.R;
import com.example.testuhfapi.InventorActivity;

//侧边导航栏
@TargetApi(Build.VERSION_CODES.HONEYCOMB)

public class TabActivityty extends InventorActivity implements OnClickListener {

	private RadioButton rb_ruku, rb_chuku, rb_jieyong, rb_guihuan, rb_pandian,
			rb_chaxun, rb_user;
	private FrameLayout fram_layout;
	private RadioButton rb_zhuxiao;
	private PreferencesService service;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_tab);
		RadioButton rb_user = (RadioButton) findViewById(R.id.rb_user);
		Intent intent = getIntent();
		User user = intent.getParcelableExtra("user");
		rb_user.setText(user.getCname());
		service = new PreferencesService(TabActivityty.this);
		service.saveName(user.getCname());
		service.saveID(user.getIid());
		service.cusecode(user.getCusecode());
		init();
		initData();
		// 注销 点击注销跳转到登陆页面
		rb_zhuxiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				service.check("0");
				Intent intent = new Intent(TabActivityty.this,
						MainActivity.class);
				startActivity(intent);

			}
		});


		
	}
	
	

	// 初始化控件
	private void init() {
		rb_ruku = (RadioButton) findViewById(R.id.rb_ruku);
		rb_chuku = (RadioButton) findViewById(R.id.rb_chuku);
		rb_jieyong = (RadioButton) findViewById(R.id.rb_jieyong);
		rb_guihuan = (RadioButton) findViewById(R.id.rb_guihuan);
		rb_pandian = (RadioButton) findViewById(R.id.rb_pandian);
		rb_chaxun = (RadioButton) findViewById(R.id.rb_chaxun);
		fram_layout = (FrameLayout) findViewById(R.id.fl_contain);
		rb_user = (RadioButton) findViewById(R.id.rb_user);
		rb_zhuxiao = (RadioButton) findViewById(R.id.rb_zhuxiao);
		// 点击监听事件
		rb_user.setOnClickListener(this);
		rb_ruku.setOnClickListener(this);
		rb_chuku.setOnClickListener(this);
		rb_jieyong.setOnClickListener(this);
		rb_guihuan.setOnClickListener(this);
		rb_pandian.setOnClickListener(this);
		rb_chaxun.setOnClickListener(this);
		// rb_user.setOnClickListener(this);

	}

	// 初始化界面
	private void initData() {
		FragmentTransaction t;
		t = getSupportFragmentManager().beginTransaction();
		t.replace(R.id.fl_contain, new RukuFragment());
		t.commit();

	}

	@Override
	public void onClick(View v) {
		FragmentTransaction t;
		t = getSupportFragmentManager().beginTransaction();
		switch (v.getId()) {
		case R.id.rb_user:
			t.replace(R.id.fl_contain, new UserFragment());

			t.commit();
			break;
		case R.id.rb_ruku:
			t.replace(R.id.fl_contain, new RukuFragment());
			rb_user.setChecked(false);
			t.commit();

			break;
		case R.id.rb_chuku:
			t.replace(R.id.fl_contain, new ChuKuFragment());
			rb_user.setChecked(false);
			t.commit();

			break;
		case R.id.rb_jieyong:
			t.replace(R.id.fl_contain, new JieYongFragment());
			rb_user.setChecked(false);
			t.commit();
			break;
		case R.id.rb_guihuan:
			t.replace(R.id.fl_contain, new GuiHuanFragment());
			rb_user.setChecked(false);
			t.commit();

			break;
		case R.id.rb_pandian:
			t.replace(R.id.fl_contain, new PanDianFragment());
			rb_user.setChecked(false);
			t.commit();
			break;
		case R.id.rb_chaxun:
			t.replace(R.id.fl_contain, new ChaXunFragment());
			rb_user.setChecked(false);
			t.commit();
			break;

		}

	}
}
