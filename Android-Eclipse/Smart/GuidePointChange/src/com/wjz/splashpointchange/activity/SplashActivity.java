package com.wjz.splashpointchange.activity;

import com.wjz.splashpointchange.R;
import com.wjz.splashpointchange.utils.MyContains;
import com.wjz.splashpointchange.utils.SpTools;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class SplashActivity extends Activity {

	private ImageView sIcon;
	private AnimationSet as;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		initView();// 初始化界面
		startAnimation();// 设置动画
		initEvent();// 初始化事件

	}

	private void startAnimation() {

		as = new AnimationSet(false);// 设置动画集，每种动画使用各自的时间轴

		/*
		 * 旋转动画 0 - 360 锚点，中心点
		 */
		RotateAnimation ra = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		ra.setDuration(2000);// 持续时间
		ra.setFillAfter(true);// 动画结束停留
		as.addAnimation(ra);// 加入动画集

		/*
		 * 渐变动画 0 - 1
		 */
		AlphaAnimation aa = new AlphaAnimation(0, 1);
		aa.setDuration(2000);// 持续时间
		aa.setFillAfter(true);// 动画结束停留
		as.addAnimation(aa);// 加入动画集

		/*
		 * 缩放动画 x：0 - 1 y：0 - 1 锚点，中心点
		 */
		ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		sa.setDuration(2000);// 持续时间
		sa.setFillAfter(true);// 动画结束停留
		as.addAnimation(sa);// 加入动画集

		// 为图片设置动画集
		sIcon.startAnimation(as);

	}

	private void initEvent() {
		// 设置as动画监听，监听动画完成事件
		// 动画完成进入 向导界面或主界面
		as.setAnimationListener(new AnimationListener() {

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

				// 1.动画结束判断进入的界面
				// 2.获取标记，判断标记，true：主界面，false：向导界面
				if (SpTools.getBoolean(SplashActivity.this, MyContains.ISSETUP,
						false)) {
					Intent mainIntent = new Intent(SplashActivity.this,
							MainActivity.class);
					startActivity(mainIntent);
				} else {
					Intent guideIntent = new Intent(SplashActivity.this,
							GuideActivity.class);
					startActivity(guideIntent);
				}
				finish();
			}
		});
	}

	private void initView() {
		setContentView(R.layout.activity_splash);
		sIcon = (ImageView) findViewById(R.id.iv_splash_icon);
	}
}
