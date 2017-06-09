package com.itheima.googleplay_13.holder;

import com.itheima.googleplay_13.R;
import com.itheima.googleplay_13.base.BaseHolder;
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
 * @版本       $Rev: 20 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-21 15:26:19 +0800 (星期三, 21 十月 2015) $
 * @更新描述    TODO
 */
public class Copy_2_of_HomeHolder extends BaseHolder<String> {

	private TextView	mTvTmp1;
	private TextView	mTvTmp2;

	@Override
	protected View initHolderView() {//视图是啥
		View rootView = View.inflate(UIUtils.getContext(), R.layout.item_temp, null);
		mTvTmp1 = (TextView) rootView.findViewById(R.id.tmp_tv_1);
		mTvTmp2 = (TextView) rootView.findViewById(R.id.tmp_tv_2);
		return rootView;
	}

	@Override
	protected void refreshHolderView(String data) {//有了数据,数据和视图如何绑定
		mTvTmp1.setText("我是头-" + data);
		mTvTmp2.setText("我是尾巴-" + data);
	}

}
