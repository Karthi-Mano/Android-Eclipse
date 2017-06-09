package com.itheima.googleplay_13.holder;

import com.itheima.googleplay_13.R;
import com.itheima.googleplay_13.utils.UIUtils;

import android.view.View;
import android.widget.TextView;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-21 上午11:02:09
 * @描述	     1.提供视图
 * @描述	     2.接收数据
 * @描述	     3.内部让  数据和视图绑定
 *
 * @版本       $Rev: 17 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-21 14:22:05 +0800 (星期三, 21 十月 2015) $
 * @更新描述    TODO
 */
public class CopyOfHomeHolder {
	public View			mHolderView;//v
	private String		mData;//m

	private TextView	mTvTmp1;
	private TextView	mTvTmp2;

	public CopyOfHomeHolder() {
		mHolderView = initHolderView();
		// 4.根视图,找到一个Holder,然后把它绑定在自己身上
		mHolderView.setTag(this);
	}

	/**
	 * @des 初始化持有的视图
	 * @des 初始化持有的视图里面的孩子对象
	 * @return
	 */
	private View initHolderView() {
		// 2.初始化根视图
		View rootView = View.inflate(UIUtils.getContext(), R.layout.item_temp, null);
		mTvTmp1 = (TextView) rootView.findViewById(R.id.tmp_tv_1);
		mTvTmp2 = (TextView) rootView.findViewById(R.id.tmp_tv_2);
		return rootView;
	}

	/**
	 * @des 接收数据,然后数据和视图绑定
	 * @call 外部有数据有视图, 想数据和视图绑定
	 */
	public void setDataAndRefreshHolderView(String data) {
		// 保存数据
		mData = data;
		// 数据和视图绑定
		refreshHolderView(data);
	}

	/**
	 * @des 数据和视图绑定
	 * @param data
	 */
	private void refreshHolderView(String data) {
		mTvTmp1.setText("我是头-" + data);
		mTvTmp2.setText("我是尾巴-" + data);
	}
}
