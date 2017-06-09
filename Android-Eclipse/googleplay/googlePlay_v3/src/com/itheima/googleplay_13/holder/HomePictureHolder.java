package com.itheima.googleplay_13.holder;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.itheima.googleplay_13.R;
import com.itheima.googleplay_13.base.BaseHolder;
import com.itheima.googleplay_13.conf.Constants.URlS;
import com.itheima.googleplay_13.utils.BitmapHelper;
import com.itheima.googleplay_13.utils.UIUtils;
import com.itheima.googleplay_13.view.InnerViewPager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-23 下午2:41:46
 * @描述	     TODO
 *
 * @版本       $Rev: 33 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-23 16:43:11 +0800 (星期五, 23 十月 2015) $
 * @更新描述    TODO
 */
public class HomePictureHolder extends BaseHolder<List<String>> {
	@ViewInject(R.id.item_home_picture_pager)
	InnerViewPager				mViewPager;

	@ViewInject(R.id.item_home_picture_container_indicator)
	LinearLayout			mContainerIndicator;

	private List<String>	mUrls;

	@Override
	protected View initHolderView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.item_home_pictures, null);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	protected void refreshHolderView(List<String> urls) {
		// 保存数据
		mUrls = urls;

		mViewPager.setAdapter(new PicturesAdaper());

		// 加上点
		for (int i = 0; i < urls.size(); i++) {
			ImageView ivIndicator = new ImageView(UIUtils.getContext());
			ivIndicator.setImageResource(R.drawable.indicator_normal);
			if (i == 0) {
				ivIndicator.setImageResource(R.drawable.indicator_selected);
			}
			int width = UIUtils.dip2Px(6);// 10px
			int height = UIUtils.dip2Px(6);
			
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
			params.leftMargin = UIUtils.dip2Px(6);
			params.bottomMargin = UIUtils.dip2Px(6);
			mContainerIndicator.addView(ivIndicator, params);
		}
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				position = position % mUrls.size();

				// 选中效果的时候
				// 1.还原效果
				for (int i = 0; i < mUrls.size(); i++) {
					ImageView iv = (ImageView) mContainerIndicator.getChildAt(i);
					iv.setImageResource(R.drawable.indicator_normal);
				}
				// 2.选中应该选中的
				ImageView iv = (ImageView) mContainerIndicator.getChildAt(position);
				
				iv.setImageResource(R.drawable.indicator_selected);
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				// TODO

			}

			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO

			}
		});

		// //设置当前选中index
		int diff = Integer.MAX_VALUE / 2 % mUrls.size();
		mViewPager.setCurrentItem(Integer.MAX_VALUE / 2 - diff);

		// 自动轮播
		final AutoScrollTask autoScrollTask = new AutoScrollTask();
		autoScrollTask.start();
		
		//按下去的时候停止轮播
		mViewPager.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					autoScrollTask.stop();
					break;
				case MotionEvent.ACTION_MOVE:
					
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
					autoScrollTask.start();
					break;

				default:
					break;
				}
				return false;
			}
		});
	}

	class AutoScrollTask implements Runnable {
		/**开始轮播*/
		public void start() {
			UIUtils.getMainThreadHandler().postDelayed(this, 2000);
		}

		/**结束轮播*/
		public void stop() {
			UIUtils.getMainThreadHandler().removeCallbacks(this);
		}

		@Override
		public void run() {
			int currentItem = mViewPager.getCurrentItem();
			currentItem++;
			mViewPager.setCurrentItem(currentItem);

			start();
		}
	}

	class PicturesAdaper extends PagerAdapter {

		@Override
		public int getCount() {
			if (mUrls != null) {
				return Integer.MAX_VALUE;
				// return mUrls.size();
			}
			return 0;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// position = Integer.MAX_VALUE/2;

			position = position % mUrls.size();

			ImageView iv = new ImageView(UIUtils.getContext());
			iv.setScaleType(ScaleType.FIT_XY);

			// 图片加载
			BitmapHelper.display(iv, URlS.IMAGEBASEURL + mUrls.get(position));

			container.addView(iv);

			return iv;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}

}
