package com.example.galleryviewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * ViewPager实现画廊效果

 */
public class MainActivity extends Activity {

	private static int TOTAL_COUNT = 5;

	private RelativeLayout viewPagerContainer;
	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		viewPager = (ViewPager) findViewById(R.id.view_pager);
		viewPagerContainer = (RelativeLayout) findViewById(R.id.pager_layout);
		viewPager.setAdapter(new MyPagerAdapter());
		// to cache all page, or we will see the right item delayed
		viewPager.setOffscreenPageLimit(TOTAL_COUNT);
		viewPager.setPageMargin(50);
		//        MyOnPageChangeListener myOnPageChangeListener = new MyOnPageChangeListener();
		//        viewPager.setOnPageChangeListener(myOnPageChangeListener);

		/*viewPagerContainer.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// dispatch the events to the ViewPager, to solve the problem that we can swipe only the middle view.
				return viewPager.dispatchTouchEvent(event);
			}
		});*/
	}

	/**
	 * this is a example fragment, just a imageview, u can replace it with your needs
	 * 
	 */
	class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return TOTAL_COUNT;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return (view == object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView imageView = new ImageView(MainActivity.this);
			imageView.setImageResource(R.drawable.tmp_image1 + position);

			ImageView imageView1 = new ImageView(MainActivity.this);
			imageView1.setImageResource(R.drawable.tmp_image2 + position);

			ImageView imageView2 = new ImageView(MainActivity.this);
			imageView2.setImageResource(R.drawable.tmp_image3 + position);

			ImageView imageView3 = new ImageView(MainActivity.this);
			imageView3.setImageResource(R.drawable.tmp_image4 + position);

			ImageView imageView4 = new ImageView(MainActivity.this);
			imageView4.setImageResource(R.drawable.tmp_image5 + position);

			((ViewPager) container).addView(imageView, position);
			((ViewPager) container).addView(imageView1, position);
			((ViewPager) container).addView(imageView2, position);
			((ViewPager) container).addView(imageView3, position);
			((ViewPager) container).addView(imageView4, position);
			return imageView;

		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((ImageView) object);
		}
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int position) {
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			// 刷新布局
			if (viewPagerContainer != null) {
				viewPagerContainer.invalidate();
			}
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}
}