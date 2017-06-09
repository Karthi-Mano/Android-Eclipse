package com.itheima.googleplay_13.holder;

import android.view.View;

import com.itheima.googleplay_13.R;
import com.itheima.googleplay_13.base.BaseHolder;
import com.itheima.googleplay_13.utils.UIUtils;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-25 下午6:40:47
 * @描述	     TODO
 *
 * @版本       $Rev: 72 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-25 18:44:28 +0800 (星期日, 25 十月 2015) $
 * @更新描述    TODO
 */
public class MenuHolder extends BaseHolder<Object> {

	@Override
	protected View initHolderView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.menu_view, null);
		return view;
	}

	@Override
	protected void refreshHolderView(Object data) {
		
	}

}
