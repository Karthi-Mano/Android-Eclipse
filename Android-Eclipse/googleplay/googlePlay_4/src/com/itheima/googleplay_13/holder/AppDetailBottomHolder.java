package com.itheima.googleplay_13.holder;

import android.view.View;
import android.widget.TextView;

import com.itheima.googleplay_13.R;
import com.itheima.googleplay_13.base.BaseHolder;
import com.itheima.googleplay_13.bean.AppInfoBean;
import com.itheima.googleplay_13.utils.UIUtils;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-24 下午3:27:10
 * @描述	     TODO
 *
 * @版本       $Rev: 48 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-24 15:33:26 +0800 (星期六, 24 十月 2015) $
 * @更新描述    TODO
 */
public class AppDetailBottomHolder extends BaseHolder<AppInfoBean> {

	private TextView	mTv;

	@Override
	protected View initHolderView() {
		return mTv;
	}

	@Override
	protected void refreshHolderView(AppInfoBean data) {
		mTv.setText(this.getClass().getSimpleName());
	}

}
