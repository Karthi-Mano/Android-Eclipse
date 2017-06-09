package com.gs.qzg.activity;

import com.gs.qzg.greensport.R;
import com.gs.qzg.greensport.R.id;
import com.gs.qzg.greensport.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SportTypeActivity extends Activity implements OnClickListener{

	//胸部
	private Button chestBtn;
	//腹部
	private Button abdomenBtn;
	//下肢
	private Button downarmBtn;
	//上肢
	private Button armBtn;
	//瑜伽
	private Button yogaBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sport_type);
		
		TextView tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText("健身项目类别");
		
		ImageView tv_back = (ImageView)findViewById(R.id.tv_back);
		tv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		initBtnView();
		
		
	}

	private void initBtnView() {
		// TODO Auto-generated method stub
		chestBtn = (Button)findViewById(R.id.chestBtn);
		abdomenBtn = (Button)findViewById(R.id.abdomenBtn);
		downarmBtn = (Button)findViewById(R.id.downarmBtn);
		armBtn = (Button)findViewById(R.id.armBtn);
		yogaBtn = (Button)findViewById(R.id.yogaBtn);
		
		chestBtn.setOnClickListener(this);
		abdomenBtn.setOnClickListener(this);
		downarmBtn.setOnClickListener(this);
		armBtn.setOnClickListener(this);
		yogaBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		Intent intent;
		
		switch (v.getId()) {
		case R.id.chestBtn:
			
			intent = new Intent(SportTypeActivity.this,ChestActivity.class);
			startActivity(intent);
			
			break;
			
		case R.id.abdomenBtn:
			
			intent = new Intent(SportTypeActivity.this,AbdomenActivity.class);
			startActivity(intent);
			break;
			
		case R.id.downarmBtn:
			intent = new Intent(SportTypeActivity.this,DownarmActivity.class);
			startActivity(intent);
			break;
			
		case R.id.armBtn:
			intent = new Intent(SportTypeActivity.this,ArmActivity.class);
			startActivity(intent);
			break;
			
		case R.id.yogaBtn:
			intent = new Intent(SportTypeActivity.this,YogaActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
