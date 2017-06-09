package com.itheima.googleplay_13.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.itheima.googleplay_13.R;
import com.itheima.googleplay_13.base.BaseHolder;
import com.itheima.googleplay_13.bean.AppInfoBean;
import com.itheima.googleplay_13.conf.Constants.URlS;
import com.itheima.googleplay_13.utils.BitmapHelper;
import com.itheima.googleplay_13.utils.StringUtils;
import com.itheima.googleplay_13.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-21 上午11:02:09
 * @描述	     1.提供视图
 * @描述	     2.接收数据
 * @描述	     3.内部让  数据和视图绑定
 *
 * @版本       $Rev: 28 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-23 11:35:52 +0800 (星期五, 23 十月 2015) $
 * @更新描述    TODO
 */
public class ItemHolder extends BaseHolder<AppInfoBean> {
	@ViewInject(R.id.item_appinfo_iv_icon)
	ImageView	mIvIcon;

	@ViewInject(R.id.item_appinfo_rb_stars)
	RatingBar	mRbStars;

	@ViewInject(R.id.item_appinfo_tv_des)
	TextView	mTvDes;

	@ViewInject(R.id.item_appinfo_tv_size)
	TextView	mTvSize;

	@ViewInject(R.id.item_appinfo_tv_title)
	TextView	mTvTitle;

	@Override
	protected View initHolderView() {// 视图是啥
		View rootView = View.inflate(UIUtils.getContext(), R.layout.item_home_info, null);
		// 找孩子
		ViewUtils.inject(this, rootView);
		return rootView;
	}

	@Override
	protected void refreshHolderView(AppInfoBean data) {// 有了数据,数据和视图如何绑定
		mTvDes.setText(data.des);
		mTvSize.setText(StringUtils.formatFileSize(data.size));
		mTvTitle.setText(data.name);

		mRbStars.setRating(data.stars);

		// 默认图片
		mIvIcon.setImageResource(R.drawable.ic_default);
		// 图片加载
		// BitmapUtils bitmapUtils = new BitmapUtils(UIUtils.getContext());
		// http://localhost:8080/GooglePlayServer/image?name=app/com.itheima.www/icon.jpg
		BitmapHelper.display(mIvIcon, URlS.IMAGEBASEURL + data.iconUrl);

	}
}
