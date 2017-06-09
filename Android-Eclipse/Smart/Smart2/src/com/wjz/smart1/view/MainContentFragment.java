package com.wjz.smart1.view;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.wjz.smart1.R;
import com.wjz.smart1.tagpage.BasePageTag;
import com.wjz.smart1.tagpage.GovAffairsBasePageTag;
import com.wjz.smart1.tagpage.HomeBasePageTag;
import com.wjz.smart1.tagpage.NewCenterBasePageTag;
import com.wjz.smart1.tagpage.SettingCenterBasePageTag;
import com.wjz.smart1.tagpage.SmartServiceBasePageTag;

public class MainContentFragment extends BaseFragment {

	private View root;
	private RadioGroup rg_radios;
	private ViewPager vp_main_content;
	private List<BasePageTag> pagers = new ArrayList<BasePageTag>();
	private int selectIndex;

	@Override
	public View initView() {

		root = View.inflate(mainActivity, R.layout.fragment_main_content_view,
				null);
		vp_main_content = (ViewPager) root
				.findViewById(R.id.vp_main_content_page);

		rg_radios = (RadioGroup) root.findViewById(R.id.rg_main_content_radios);

		return root;
	}

	@Override
	public void initEvent() {

		rg_radios.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (checkedId) {
				case R.id.rb_main_content_home:
					selectIndex = 0;
					break;

				case R.id.rb_main_content_newscenter:
					selectIndex = 1;
					break;

				case R.id.rb_main_content_smartservice:
					selectIndex = 2;
					break;

				case R.id.rb_main_content_govaffair:
					selectIndex = 3;
					break;

				case R.id.rb_main_content_settingcenter:
					selectIndex = 4;
					break;

				default:
					break;
				}// end

				switchPage();

			}
		});

		super.initEvent();
	}

	protected void switchPage() {

		vp_main_content.setCurrentItem(selectIndex);

		// 如果是第一个或者是左后一个不让左侧菜单划出
		if (selectIndex == 0 || selectIndex == pagers.size() - 1) {
			mainActivity.getSlidingMenu().setTouchModeAbove(
					SlidingMenu.TOUCHMODE_NONE);
		} else {
			mainActivity.getSlidingMenu().setTouchModeAbove(
					SlidingMenu.TOUCHMODE_FULLSCREEN);
		}

	}

	@Override
	public void initData() {
		// viewPager-->PagerAdapter-->List-->显示

		pagers.add(new HomeBasePageTag(mainActivity));
		pagers.add(new NewCenterBasePageTag(mainActivity));
		pagers.add(new SmartServiceBasePageTag(mainActivity));
		pagers.add(new GovAffairsBasePageTag(mainActivity));
		pagers.add(new SettingCenterBasePageTag(mainActivity));

		MyAdapter adapter = new MyAdapter();
		vp_main_content.setAdapter(adapter);

		//设置默认选择首页
		switchPage();
		//设置第一个按钮被选中
		rg_radios.check(R.id.rb_main_content_home);
		
		
		super.initData();
	}

	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return pagers.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			BasePageTag basePageTag = pagers.get(position);
			View child = basePageTag.getView();

			container.addView(child);

			return child;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

			container.removeView((View) object);

		}

	}

}
