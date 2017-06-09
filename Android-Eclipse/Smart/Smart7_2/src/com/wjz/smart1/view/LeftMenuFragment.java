package com.wjz.smart1.view;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wjz.smart1.R;
import com.wjz.smart1.domain.NewsCenterData.NewsData;

public class LeftMenuFragment extends BaseFragment {

	private List<NewsData> data = new ArrayList<NewsData>();
	private ListView lv;
	private int selectItem;
	private MyAdapter adapter;

	/**
	 * 设置接口方法
	 */
	public interface OnSwitchPageListener {
		void switchPage(int selectItem);
	};

	private OnSwitchPageListener switchPageListener;

	/**
	 * 设置监听回调
	 */
	public void setOnSwitchPageListener(OnSwitchPageListener listener) {
		this.switchPageListener = listener;
	}

	@Override
	public View initView() {

		lv = new ListView(mainActivity);

		// 选中拖动的背景色
		lv.setCacheColorHint(Color.TRANSPARENT);

		// 设置选中时为背景色透明
		lv.setSelector(new ColorDrawable(Color.TRANSPARENT));

		// 没有分割线
		lv.setDividerHeight(0);

		// 距离顶部
		lv.setPadding(0, 68, 0, 0);

		return lv;
	}

	public void setLeftMenuData(List<NewsData> data) {
		this.data = data;
		adapter.notifyDataSetChanged();// 更新数据，刷新界面
	}

	@Override
	public void initData() {

		adapter = new MyAdapter();
		lv.setAdapter(adapter);

		super.initData();
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			/*
			 * TextView tv_currentView; if (convertView == null) {
			 * tv_currentView = (TextView) View.inflate(mainActivity,
			 * R.layout.left_menu_textview, null); } else { tv_currentView =
			 * (TextView) convertView; }
			 * 
			 * // 设置左侧菜单的数据 tv_currentView.setText(data.get(position).title);
			 * 
			 * // 默认选中 tv_currentView.setEnabled(selectItem == position); return
			 * tv_currentView;
			 */
			TextView tv_currentView;

			if (convertView == null) {

				convertView = View.inflate(mainActivity,
						R.layout.left_menu_layout, null);
				tv_currentView = (TextView) convertView
						.findViewById(R.id.tv_left_menu_rl);

				convertView.setTag(tv_currentView);
			} else {
				tv_currentView = (TextView) convertView.getTag();

			}

			// 设置左侧菜单的数据
			tv_currentView.setText(data.get(position).title);

			// 默认选中
			tv_currentView.setEnabled(selectItem == position);

			return convertView;

		}

	}

	@Override
	public void initEvent() {

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				selectItem = position;
				adapter.notifyDataSetChanged();

				// 控制中心，四个新闻界面的显示，新闻-专题-组图-互动

				if (switchPageListener == null) {
					// 1.使用接口调用的方式
					// 调用接口方法
					switchPageListener.switchPage(selectItem);
				} else {
					// 2.使用架构层次方式
					mainActivity.getMainContentFragment()
							.leftMenuClickSwitchPager(selectItem);
				}

				// 点击item后关闭左侧菜单
				mainActivity.getSlidingMenu().toggle();
			}
		});

		super.initEvent();
	}

}
