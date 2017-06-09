package com.wjz.smart1.newscenterpages;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;
import com.wjz.smart1.R;
import com.wjz.smart1.activity.MainActivity;
import com.wjz.smart1.domain.NewsCenterData;
import com.wjz.smart1.domain.NewsCenterData.NewsData.ViewTagData;

public class NewsBaseNewsCenterPage extends BaseNewsCenterPage {
	private List<NewsCenterData.NewsData.ViewTagData> viewTagData = new ArrayList<NewsCenterData.NewsData.ViewTagData>();

	@ViewInject(R.id.vp_newscenter_content)
	private ViewPager vp_newscenter;

	@ViewInject(R.id.tp_newscenter_content)
	private TabPageIndicator tp_newscenter;

	@OnClick(R.id.ib_newscenter_content)
	private void next(View v) {
		vp_newscenter.setCurrentItem(vp_newscenter.getCurrentItem() + 1);
	}

	public NewsBaseNewsCenterPage(MainActivity mainActivity,
			List<ViewTagData> children) {
		super(mainActivity);
		// TODO Auto-generated constructor stub
		this.viewTagData = children;
	}

	@Override
	public View initView() {

		View root = View.inflate(mainActivity, R.layout.newscenter_content,
				null);

		ViewUtils.inject(this, root);
		return root;
	}

	@Override
	protected void initEvent() {
		// TODO Auto-generated method stub
		
		//给vp_newscenter设置页面切换的点击事件
		tp_newscenter.setOnPageChangeListener(new OnPageChangeListener() {
			
			
			//页面停留的位置
			@Override
			public void onPageSelected(int position) {
				
				//当页面位于第0个页面是可以滑动出左侧菜单
				if(position==0){
					mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				}else{
					//当前页面不是第0个页面不能滑动出左侧菜单
					mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
				}
				
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		
		
		super.initEvent();
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

		MyAdapter adapter = new MyAdapter();

		vp_newscenter.setAdapter(adapter);

		// 把ViewPager和Tabpagerindicator关联
		tp_newscenter.setViewPager(vp_newscenter);
		super.initData();
	}

	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return viewTagData.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return viewTagData.get(position).title;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			TextView tv = new TextView(mainActivity);
			tv.setText(viewTagData.get(position).title);
			tv.setTextSize(25);
			tv.setGravity(Gravity.CENTER);

			container.addView(tv);

			return tv;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView((View) object);
		}

	}
}
