package com.itheima.googleplay_13.holder;

import android.view.View;
import android.widget.LinearLayout;

import com.itheima.googleplay_13.R;
import com.itheima.googleplay_13.base.BaseHolder;
import com.itheima.googleplay_13.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-21 下午3:46:22
 * @描述	     TODO
 *
 * @版本       $Rev: 21 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-21 16:02:53 +0800 (星期三, 21 十月 2015) $
 * @更新描述    TODO
 */
public class LoadMoreHolder extends BaseHolder<Integer> {
	@ViewInject(R.id.item_loadmore_container_loading)
	LinearLayout			mContainerLoading;

	@ViewInject(R.id.item_loadmore_container_retry)
	LinearLayout			mContainerRetry;

	/**
	 loadmore视图有几种显示情况
		1.显示加载
		2.显示重试
		3.都不显示(没有更多)
	 */
	public static final int	LOADMORE_LOADING	= 0;
	public static final int	LOADMORE_RETRY		= 1;
	public static final int	LOADMORE_NONE		= 2;

	@Override
	protected View initHolderView() {
		View rootView = View.inflate(UIUtils.getContext(), R.layout.item_load_more, null);
		ViewUtils.inject(this, rootView);
		return rootView;
	}

	@Override
	protected void refreshHolderView(Integer curState) {
		// 隐藏所有
		mContainerLoading.setVisibility(8);
		mContainerRetry.setVisibility(8);

		switch (curState) {
		case LOADMORE_LOADING:// 显示加载视图
			mContainerLoading.setVisibility(0);
			break;
		case LOADMORE_RETRY:// 显示重试视图
			mContainerRetry.setVisibility(0);
			break;
		case LOADMORE_NONE:// 啥也不显示

			break;

		default:
			break;
		}
	}
	// hoder中数据的作用-->决定视图的展示

}
