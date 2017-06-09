package com.itheima.googleplay_13.base;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.itheima.googleplay_13.activity.DetailActivity;
import com.itheima.googleplay_13.bean.AppInfoBean;
import com.itheima.googleplay_13.holder.ItemHolder;
import com.itheima.googleplay_13.manager.DownLoadManager;
import com.itheima.googleplay_13.utils.UIUtils;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-24 下午2:18:36
 * @描述	     TODO
 *
 * @版本       $Rev: 67 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-25 17:00:04 +0800 (星期日, 25 十月 2015) $
 * @更新描述    TODO
 */
public class BaseItemAdapter extends SuperBaseAdapter<AppInfoBean> {
	/**定义集合保存ItemHolder(观察者)*/
	private List<ItemHolder> mItemHolders = new ArrayList<ItemHolder>();
	
	public List<ItemHolder> getItemHolders() {
		return mItemHolders;
	}

	public BaseItemAdapter(AbsListView absListView, List<AppInfoBean> datas) {
		super(absListView, datas);
	}

	@Override
	protected BaseHolder<AppInfoBean> getSpecialHolder(int position) {
		ItemHolder itemHolder = new ItemHolder();
		
		mItemHolders.add(itemHolder);
		
		//添加到观察者集合中
		DownLoadManager.getInstance().addObserver(itemHolder);
		
		return itemHolder;
	}

	// 普通条目的点击事件
	@Override
	public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
		intent.putExtra("packageName", mDataSource.get(position).packageName);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		UIUtils.getContext().startActivity(intent);
	}

}
