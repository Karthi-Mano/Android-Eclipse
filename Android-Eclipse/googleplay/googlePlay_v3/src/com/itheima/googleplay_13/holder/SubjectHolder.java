package com.itheima.googleplay_13.holder;

import android.text.style.URLSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.googleplay_13.R;
import com.itheima.googleplay_13.base.BaseHolder;
import com.itheima.googleplay_13.bean.SubjectInfoBean;
import com.itheima.googleplay_13.conf.Constants.URlS;
import com.itheima.googleplay_13.utils.BitmapHelper;
import com.itheima.googleplay_13.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-23 下午4:37:02
 * @描述	     TODO
 *
 * @版本       $Rev: 33 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-23 16:43:11 +0800 (星期五, 23 十月 2015) $
 * @更新描述    TODO
 */
public class SubjectHolder extends BaseHolder<SubjectInfoBean> {
	@ViewInject(R.id.item_subject_iv_icon)
	ImageView	mIvIcon;
	@ViewInject(R.id.item_subject_tv_title)
	TextView	mTvTitle;

	@Override
	protected View initHolderView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.item_subject, null);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	protected void refreshHolderView(SubjectInfoBean data) {
		mTvTitle.setText(data.des);
		BitmapHelper.display(mIvIcon, URlS.IMAGEBASEURL + data.url);
	}

}
