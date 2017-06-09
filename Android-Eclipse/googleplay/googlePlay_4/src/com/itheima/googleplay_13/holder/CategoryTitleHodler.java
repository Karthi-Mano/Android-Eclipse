package com.itheima.googleplay_13.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.itheima.googleplay_13.base.BaseHolder;
import com.itheima.googleplay_13.bean.CategoryInfoBean;
import com.itheima.googleplay_13.utils.UIUtils;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-24 上午10:15:40
 * @描述	     TODO
 *
 * @版本       $Rev: 41 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-24 10:28:40 +0800 (星期六, 24 十月 2015) $
 * @更新描述    TODO
 */
public class CategoryTitleHodler extends BaseHolder<CategoryInfoBean> {

	private TextView	mTvTitle;

	@Override
	protected View initHolderView() {
		mTvTitle = new TextView(UIUtils.getContext());
		int padding = UIUtils.dip2Px(5);
		mTvTitle.setPadding(padding, padding, padding, padding);
		mTvTitle.setBackgroundColor(Color.WHITE);
		return mTvTitle;
	}

	@Override
	protected void refreshHolderView(CategoryInfoBean data) {
		mTvTitle.setText(data.title);
	}

}
