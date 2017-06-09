package com.wjz.splashpointchange.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import com.wjz.splashpointchange.R;
import com.wjz.splashpointchange.utils.DensityUtil;
import com.wjz.splashpointchange.utils.MyContains;
import com.wjz.splashpointchange.utils.SpTools;

public class GuideActivity extends Activity {

	private ViewPager gViewPager;
	private View gRedPoint;
	private LinearLayout gLlGrayPointsContainer;
	private Button gButtonExp;
	private VpAdapter vpAdapter;
	private List<ImageView> gListDatas;
	private int distance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		initView();
		initEvent();
		initData();
	}

	private void initView() {
		setContentView(R.layout.activity_guide);
		// viewpager组件
		gViewPager = (ViewPager) findViewById(R.id.vp_guide);
		// 红点
		gRedPoint = findViewById(R.id.v_guide_redPoint);
		// 灰点容器
		gLlGrayPointsContainer = (LinearLayout) findViewById(R.id.ll_gray_points);
		gButtonExp = (Button) findViewById(R.id.bt_guide_startexp);

		// 创建viewPager的适配器
		vpAdapter = new VpAdapter();
		gViewPager.setAdapter(vpAdapter);

	}

	private class VpAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			if (gListDatas != null) {
				return gListDatas.size();
			}
			return 0;// 返回数据的个数
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object; // 缓存
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			ImageView child = gListDatas.get(position);
			container.addView(child);

			return child;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

			container.removeView((View) object);// 移除view
		}

	}

	private void initEvent() {

		// 监听LinearLayout事件完成
		gLlGrayPointsContainer.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						// 取消监听
						gLlGrayPointsContainer.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
						// 获得两点之间的距离
						distance = gLlGrayPointsContainer.getChildAt(1)
								.getLeft()
								- gLlGrayPointsContainer.getChildAt(0)
										.getLeft();
					}
				});

		// 按钮点击事件,点击
		gButtonExp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent mainIntent = new Intent(GuideActivity.this,
						MainActivity.class);
				startActivity(mainIntent);
				SpTools.setBoolean(GuideActivity.this, MyContains.ISSETUP, true);
				finish();
			}
		});

		// 监听ViewPager页码改变，当改变到最后一个是显示体验按钮
		gViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// 显示当前ViewPager的页码，当显示到最后一个是显示按钮
				if (position == gListDatas.size() - 1) {
					gButtonExp.setVisibility(View.VISIBLE);
				} else {
					gButtonExp.setVisibility(View.GONE);
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

				float pointsMargin = distance * (position + positionOffset);
				RelativeLayout.LayoutParams marginParams = (RelativeLayout.LayoutParams) gRedPoint.getLayoutParams();
				marginParams.leftMargin = Math.round(pointsMargin);
				// 重新设置布局
				gRedPoint.setLayoutParams(marginParams);
			}

			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void initData() {

		int[] pics = new int[] { R.drawable.one, R.drawable.two,
				R.drawable.three };

		// 定义ViewPage的容器
		gListDatas = new ArrayList<ImageView>();

		for (int i = 0; i < pics.length; i++) {
			ImageView iv = new ImageView(this);
			iv.setBackgroundResource(pics[i]);

			// 将ImageView加入集合
			gListDatas.add(iv);

			// 动态的给容器添加灰色的点
			// 1.创建view
			View gPoint = new View(this);
			// 2.指定颜色
			gPoint.setBackgroundResource(R.drawable.gray_point);
			// 3.设置大小
			int dp = 10;
			LayoutParams params = new LayoutParams(DensityUtil.dip2px(this, dp), DensityUtil.dip2px(this, dp));
			if (i != 0) {
				params.leftMargin = 10;
			}
			gPoint.setLayoutParams(params);

			gLlGrayPointsContainer.addView(gPoint);
		}

	}
}
