package com.itheima.googleplay_13.base;

import android.view.View;
import android.widget.TextView;

import com.itheima.googleplay_13.R;
import com.itheima.googleplay_13.utils.UIUtils;

/**
 * @param <HOLDERBEANTYPE>
 * @创建者	 Administrator
 * @创时间 	 2015-10-21 上午11:22:02
 * @描述	     1.提供视图
 * @描述	     2.接收数据
 * @描述	     3.内部让  数据和视图绑定
 *
 * @版本       $Rev: 17 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-21 14:22:05 +0800 (星期三, 21 十月 2015) $
 * @更新描述    TODO
 */
public abstract class BaseHolder<HOLDERBEANTYPE> {

	public View				mHolderView;	// v
	private HOLDERBEANTYPE	mData;			// m

	public BaseHolder() {
		mHolderView = initHolderView();
		// 4.根视图,找到一个Holder,然后把它绑定在自己身上
		mHolderView.setTag(this);
	}

	/**
	 * @des 1.初始化持有的视图
	 * @des 2.初始化持有的视图里面的孩子对象
	 * @des 3.必须实现,但是不知道具体实现,定义成为抽象方法,交给子类具体实现
	 * @return
	 */
	protected abstract View initHolderView();

	/**
	 * @des 接收数据,然后数据和视图绑定
	 * @call 外部有数据有视图, 想数据和视图绑定
	 */
	public void setDataAndRefreshHolderView(HOLDERBEANTYPE data) {
		// 保存数据
		mData = data;
		// 数据和视图绑定
		refreshHolderView(data);
	}

	/**
	 * @des 数据和视图绑定
	 * @param data
	 */
	protected abstract void refreshHolderView(HOLDERBEANTYPE data);

}
