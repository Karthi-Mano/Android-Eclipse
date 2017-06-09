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
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class YogaActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_yoga);
		
		TextView tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText("�٤����");
		
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
				 Uri uri = Uri.parse("http://www.iqiyi.com/w_19rrboq9el.html");  
				   Intent it = new Intent(Intent.ACTION_VIEW, uri);  
				   startActivity(it);
			}
		});
	}
}
