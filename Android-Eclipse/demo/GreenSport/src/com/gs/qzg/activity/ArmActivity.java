package com.gs.qzg.activity;

import com.gs.qzg.greensport.R;
import com.gs.qzg.greensport.R.id;
import com.gs.qzg.greensport.R.layout;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ArmActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_arm);
		
		TextView tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText("…œ÷´∂Õ¡∂");
		
		ImageView tv_back = (ImageView)findViewById(R.id.tv_back);
		tv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		Button chest_video = (Button)findViewById(R.id.video_teacher);
		chest_video.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Uri uri = Uri.parse("http://my.tv.sohu.com/us/63291893/25646096.shtml");  
				   Intent it = new Intent(Intent.ACTION_VIEW, uri);  
				   startActivity(it);
			}
		});
	}
}
