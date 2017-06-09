package com.itheima.googleplay_13;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import com.astuetz.PagerSlidingTabStripExtends;
import com.itheima.googleplay_13.base.BaseFragment;
import com.itheima.googleplay_13.base.LoadingPager;
import com.itheima.googleplay_13.factory.FragmentFactory;
import com.itheima.googleplay_13.utils.LogUtils;
import com.itheima.googleplay_13.utils.UIUtils;

public class MainActivity extends FragmentActivity {

	private PagerSlidingTabStripExtends	mTabs;
	private ViewPager					mViewPager;
	private String[]					mMainTitles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();// 创建Fragement的过程需要时间(视图,loadingpager)
		initListener();
	}

	private void initView() {
		mTabs = (PagerSlidingTabStripExtends) findViewById(R.id.main_tabs);
		mViewPager = (ViewPager) findViewById(R.id.main_viewPager);
	}

	private void initData() {
		// 初始化数据集(dateset)/数据源(datasource)
		mMainTitles = UIUtils.getStringArr(R.array.main_titles);

		// 设置adapter
		// MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
		MainFragmentStatePagerAdapter adapter = new MainFragmentStatePagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(adapter);

		// Bind the tabs to the ViewPager
		mTabs.setViewPager(mViewPager);
	}

	private void initListener() {
		mTabs.setOnPageChangeListener(mMyOnPageChangeListener);
		mMyOnPageChangeListener.onPageSelected(0);
		mViewPager.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				// 手动触发onPageSelected方法
				mMyOnPageChangeListener.onPageSelected(0);
				mViewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
		});

	}

	MyOnPageChangeListener	mMyOnPageChangeListener	= new MyOnPageChangeListener();

	class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int position) {
			// 开始触发加载数据(loadingPager)
			// 此时的Fragement肯定是已经创建过的Fragement了
			BaseFragment baseFragment = FragmentFactory.createFragement(position);
			if (baseFragment != null) {
				LoadingPager loadingPager = baseFragment.getLoadingPager();
				if (loadingPager != null) {
					loadingPager.triggerLoadData();
				}
			}
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			// TODO

		}

		@Override
		public void onPageScrollStateChanged(int state) {
			// TODO

		}

	}

	// PagerAdapter-->view
	// FragmentPagerAdapter-->Fragment
	class MainFragmentPagerAdapter extends FragmentPagerAdapter {
		public MainFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			LogUtils.sf("初始化 " + mMainTitles[position]);
			Fragment fragement = FragmentFactory.createFragement(position);
			return fragement;
		}

		@Override
		public int getCount() {
			if (mMainTitles != null) {
				return mMainTitles.length;
			}
			return 0;
		}

		/**
		 * 需要覆写pagerAdapter中定义的getPageTitle方法,修改它默认的返回值
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			return mMainTitles[position];
		}

	}

	class MainFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

		public MainFragmentStatePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			LogUtils.sf("初始化 " + mMainTitles[position]);
			Fragment fragement = FragmentFactory.createFragement(position);
			return fragement;
		}

		@Override
		public int getCount() {
			if (mMainTitles != null) {
				return mMainTitles.length;
			}
			return 0;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mMainTitles[position];
		}
	}
}
