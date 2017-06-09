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
import com.itheima.googleplay_13.utils.FileUtils;
import com.itheima.googleplay_13.utils.StringUtils;
import com.itheima.googleplay_13.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-24 下午3:27:10
 * @描述	     TODO
 *
 * @版本       $Rev: 49 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-24 15:45:40 +0800 (星期六, 24 十月 2015) $
 * @更新描述    TODO
 */
public class AppDetailInfoHolder extends BaseHolder<AppInfoBean> {

	@ViewInject(R.id.app_detail_info_rb_star)
	RatingBar	mRbStar;

	@ViewInject(R.id.app_detail_info_iv_icon)
	ImageView	mIvIcon;

	@ViewInject(R.id.app_detail_info_tv_downloadnum)
	TextView	mTvDownLoadNum;

	@ViewInject(R.id.app_detail_info_tv_name)
	TextView	mTvName;

	@ViewInject(R.id.app_detail_info_tv_size)
	TextView	mTvSize;

	@ViewInject(R.id.app_detail_info_tv_time)
	TextView	mTvTime;

	@ViewInject(R.id.app_detail_info_tv_version)
	TextView	mTvVersion;

	@Override
	protected View initHolderView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.item_detail_info, null);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	protected void refreshHolderView(AppInfoBean data) {
		mTvName.setText(data.name);

		String date = UIUtils.getString(R.string.detail_date, data.date);
		String downloadNum = UIUtils.getString(R.string.detail_downloadnum, data.downloadNum);
		String size = UIUtils.getString(R.string.detail_size, StringUtils.formatFileSize(data.size));
		String version = UIUtils.getString(R.string.detail_version, data.version);

		mTvDownLoadNum.setText(downloadNum);
		mTvSize.setText(size);
		mTvTime.setText(date);
		mTvVersion.setText(version);

		mRbStar.setRating(data.stars);

		mIvIcon.setImageResource(R.drawable.ic_default);
		BitmapHelper.display(mIvIcon, URlS.IMAGEBASEURL + data.iconUrl);
	}

}
