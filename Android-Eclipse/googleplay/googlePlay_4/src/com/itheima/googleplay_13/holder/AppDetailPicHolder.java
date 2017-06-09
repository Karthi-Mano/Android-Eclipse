package com.itheima.googleplay_13.holder;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itheima.googleplay_13.R;
import com.itheima.googleplay_13.base.BaseHolder;
import com.itheima.googleplay_13.bean.AppInfoBean;
import com.itheima.googleplay_13.conf.Constants.URlS;
import com.itheima.googleplay_13.utils.BitmapHelper;
import com.itheima.googleplay_13.utils.UIUtils;
import com.itheima.googleplay_13.view.RatioLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-24 下午3:27:10
 * @描述	     TODO
 *
 * @版本       $Rev: 52 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-24 16:49:21 +0800 (星期六, 24 十月 2015) $
 * @更新描述    TODO
 */
public class AppDetailPicHolder extends BaseHolder<AppInfoBean> {

	@ViewInject(R.id.app_detail_pic_iv_container)
	LinearLayout	mContainerPic;

	@Override
	protected View initHolderView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.item_detail_pic, null);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	protected void refreshHolderView(AppInfoBean data) {
		List<String> screenUrls = data.screen;
		for (int i = 0; i < screenUrls.size(); i++) {
			// data
			String url = screenUrls.get(i);

			RatioLayout rl = new RatioLayout(UIUtils.getContext());
			rl.setPicRatio((float) 150 / 250);
			rl.setRelative(RatioLayout.RELATIVE_WIDTH);

			ImageView iv = new ImageView(UIUtils.getContext());
			// 图片加载
			BitmapHelper.display(iv, URlS.IMAGEBASEURL + url);

			// 把imageview放入到rl中
			rl.addView(iv);

			// 给RatioLayout设置了一个parmas

			// 得到屏幕的宽度
			int screenWidth = UIUtils.getResources().getDisplayMetrics().widthPixels;
			screenWidth = screenWidth - UIUtils.dip2Px(5)- UIUtils.dip2Px(5)- UIUtils.dip2Px(5)- UIUtils.dip2Px(5);

			int width = screenWidth / 3;
			int height = LayoutParams.WRAP_CONTENT;

			LinearLayout.LayoutParams parmas = new LinearLayout.LayoutParams(width, height);
			if (i != 0) {
				parmas.leftMargin = UIUtils.dip2Px(5);
			}

			mContainerPic.addView(rl, parmas);

		}
	}
}
