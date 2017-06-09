package com.gs.qzg.activity;

import com.gs.qzg.greensport.R;
import com.gs.qzg.greensport.R.layout;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;

public class NewPlanActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_new_plan);
	}
}
