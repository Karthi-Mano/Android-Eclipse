package com.itheima.googleplay_13.base;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @param <ITEMBEANTYPE>
 * @创建者	 Administrator
 * @创时间 	 2015-10-21 上午10:44:37
 * @描述	     TODO
 *
 * @版本       $Rev: 17 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-21 14:22:05 +0800 (星期三, 21 十月 2015) $
 * @更新描述    TODO
 */
public abstract class MyBaseAdapter<ITEMBEANTYPE> extends BaseAdapter {
	private List<ITEMBEANTYPE>	mDataSource;

	public MyBaseAdapter(List<ITEMBEANTYPE> datas) {
		super();
		mDataSource = datas;
	}

	@Override
	public int getCount() {
		if (mDataSource != null) {
			return mDataSource.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (mDataSource != null) {
			return mDataSource.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO
		return null;
	}
}
