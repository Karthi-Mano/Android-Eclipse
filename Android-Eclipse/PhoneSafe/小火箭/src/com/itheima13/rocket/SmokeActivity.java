package com.itheima13.rocket;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class SmokeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		setContentView(R.layout.somke);
		
		//����
		ImageView iv_smoket = (ImageView) findViewById(R.id.iv_rocket_somket);
		//�̵ײ�
		ImageView iv_smokem = (ImageView) findViewById(R.id.iv_rocket_somkem);
		
		
		AlphaAnimation aa = new AlphaAnimation(1.0f, 0.0f);//�Ӳ�͸����͸��
		aa.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// �ر�activity
				finish();
				
			}
		});
		aa.setDuration(2000);
		
		ScaleAnimation sa = new ScaleAnimation(1.0f, 1.0f, 0, 1.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1.0f);
		sa.setDuration(1000);
		
		
		//����  ���� alpha
		AnimationSet as = new AnimationSet(false);
		as.addAnimation(aa);
		as.addAnimation(sa);
		
		iv_smoket.startAnimation(as);
		
		////�̵ײ�  alpha
		
		iv_smokem.startAnimation(aa);
		
		
		
		
		/*
		new Thread(){
			public void run() {
				SystemClock.sleep(3000);
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						finish();
					}
				});
				
			};
		}.start();
		*/
	}
}
