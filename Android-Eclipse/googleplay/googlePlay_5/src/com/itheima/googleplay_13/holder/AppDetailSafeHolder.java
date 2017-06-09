package com.itheima.googleplay_13.holder;

import java.util.List;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itheima.googleplay_13.R;
import com.itheima.googleplay_13.base.BaseHolder;
import com.itheima.googleplay_13.bean.AppInfoBean;
import com.itheima.googleplay_13.bean.AppInfoBean.AppinfoSafeBean;
import com.itheima.googleplay_13.conf.Constants.URlS;
import com.itheima.googleplay_13.utils.BitmapHelper;
import com.itheima.googleplay_13.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-24 下午3:27:10
 * @描述	     TODO
 *
 * @版本       $Rev: 51 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-24 16:36:00 +0800 (星期六, 24 十月 2015) $
 * @更新描述    TODO
 */
public class AppDetailSafeHolder extends BaseHolder<AppInfoBean> implements OnClickListener {
	@ViewInject(R.id.app_detail_safe_iv_arrow)
	ImageView		mIvArrow;
	@ViewInject(R.id.app_detail_safe_des_container)
	LinearLayout	mContainerDes;
	@ViewInject(R.id.app_detail_safe_pic_container)
	LinearLayout	mContainerPic;
	private boolean	isOpen	= true;

	@Override
	protected View initHolderView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.item_detail_safe, null);
		view.setOnClickListener(this);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	protected void refreshHolderView(AppInfoBean data) {
		List<AppinfoSafeBean> safes = data.safe;
		for (int i = 0; i < safes.size(); i++) {
			// data
			AppinfoSafeBean appinfoSafeBean = safes.get(i);

			ImageView ivPic = new ImageView(UIUtils.getContext());

			// 加载图片
			BitmapHelper.display(ivPic, URlS.IMAGEBASEURL + appinfoSafeBean.safeUrl);
			mContainerPic.addView(ivPic);

			LinearLayout line = new LinearLayout(UIUtils.getContext());
			int padding = UIUtils.dip2Px(4);
			line.setPadding(padding, padding, padding, padding);

			ImageView ivDesIcon = new ImageView(UIUtils.getContext());
			TextView tvDesNote = new TextView(UIUtils.getContext());

			// 赋值
			BitmapHelper.display(ivDesIcon, URlS.IMAGEBASEURL + appinfoSafeBean.safeDesUrl);
			tvDesNote.setText(appinfoSafeBean.safeDes);

			int safeDesColor = appinfoSafeBean.safeDesColor;
			if (safeDesColor == 0) {
				tvDesNote.setTextColor(UIUtils.getColor(R.color.app_detail_safe_normal));
			} else {
				tvDesNote.setTextColor(UIUtils.getColor(R.color.app_detail_safe_warning));
			}

			line.addView(ivDesIcon);
			line.addView(tvDesNote);

			mContainerDes.addView(line);
		}
		// 默认的折叠,不带动画
		toggleAnimation(false);

	}

	@Override
	public void onClick(View v) {
		toggleAnimation(true);
	}

	public void toggleAnimation(boolean isAnimation) {
		if (isOpen) {// 打开->折叠
			// 打开->折叠 过程:mContainerDes从 应有高度-->0 过程

			mContainerDes.measure(0, 0);
			/**
			 mContainerDes.measure(0, 0);//是下面3行代码的缩写形势 
			 int widthMeasureSpec= MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
			 int heightMeasureSpec= MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
			 mContainerDes.measure(widthMeasureSpec, heightMeasureSpec);
			 */

			int measuredHeight = mContainerDes.getMeasuredHeight();

			int start = measuredHeight;
			int end = 0;
			if (isAnimation) {
				doAnimation(start, end);
			} else {
				LayoutParams params = mContainerDes.getLayoutParams();
				params.height = end;
				mContainerDes.setLayoutParams(params);
			}

		} else {// 折叠-->打开
				// 折叠-->打开 过程:mContainerDes从 0--->应有高度 过程
			mContainerDes.measure(0, 0);
			int measuredHeight = mContainerDes.getMeasuredHeight();

			int start = 0;
			int end = measuredHeight;

			if (isAnimation) {
				doAnimation(start, end);
			} else {
				LayoutParams params = mContainerDes.getLayoutParams();
				params.height = end;
				mContainerDes.setLayoutParams(params);
			}

		}

		isOpen = !isOpen;
	}

	public void doAnimation(int start, int end) {
		ValueAnimator animator = ValueAnimator.ofInt(start, end);
		animator.start();// 300

		// 1.得到动画执行过程中的渐变值
		animator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator value) {

				int height = (Integer) value.getAnimatedValue();
				// System.out.println(height);
				// 得到params
				LayoutParams params = mContainerDes.getLayoutParams();
				// 修改params
				params.height = height;
				// 更新params
				mContainerDes.setLayoutParams(params);
			}
		});

		// 箭头旋转动画
		if (isOpen) {
			ObjectAnimator.ofFloat(mIvArrow, "rotation", 180, 0).start();
		} else {
			ObjectAnimator.ofFloat(mIvArrow, "rotation", 0, 180).start();
		}
	}
}
