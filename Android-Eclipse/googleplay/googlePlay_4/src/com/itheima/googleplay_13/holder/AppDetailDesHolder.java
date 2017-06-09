package com.itheima.googleplay_13.holder;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.itheima.googleplay_13.R;
import com.itheima.googleplay_13.base.BaseHolder;
import com.itheima.googleplay_13.bean.AppInfoBean;
import com.itheima.googleplay_13.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-24 下午3:27:10
 * @描述	     TODO
 *
 * @版本       $Rev: 54 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-24 17:15:35 +0800 (星期六, 24 十月 2015) $
 * @更新描述    TODO
 */
public class AppDetailDesHolder extends BaseHolder<AppInfoBean> implements OnClickListener {

	@ViewInject(R.id.app_detail_des_tv_author)
	TextView			mTvAuthor;
	@ViewInject(R.id.app_detail_des_tv_des)
	TextView			mTvDes;
	@ViewInject(R.id.app_detail_des_iv_arrow)
	ImageView			mIvArrow;

	private boolean		isOpen	= true;
	private int			mTvDesMeasureHeight;
	private AppInfoBean	mAppInfoBean;

	@Override
	protected View initHolderView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.item_detail_des, null);
		view.setOnClickListener(this);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	protected void refreshHolderView(AppInfoBean data) {
		// 保存成成员变量
		mAppInfoBean = data;

		mTvAuthor.setText(data.author);
		mTvDes.setText(data.des);

		mTvDes.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				mTvDesMeasureHeight = mTvDes.getMeasuredHeight();

				mTvDes.getViewTreeObserver().removeGlobalOnLayoutListener(this);

				// 默认折叠
				toggleAnimation(false);
			}
		});

	}

	@Override
	public void onClick(View v) {
		toggleAnimation(true);
	}

	public void toggleAnimation(boolean isAnimation) {
		if (isOpen) {// 折叠
			// 折叠:mTvDes的高度 从 应有的高度--->7行的高度

			/*	mTvDes.measure(0, 0);
				int measuredHeight = mTvDes.getMeasuredHeight();*/

			// Toast.makeText(UIUtils.getContext(), "" + mTvDesMeasureHeight, 0).show();

			int start = mTvDesMeasureHeight;
			int end = getShorHeight(7, mAppInfoBean.des);// 7行高度
			if (isAnimation) {
				doAnimation(start, end);
			} else {
				mTvDes.setHeight(end);
			}
		} else {// 打开
			// 打开:mTvDes的高度 从 7行的高度---> 应有的高度

			int start = getShorHeight(7, mAppInfoBean.des);// 7行高度
			int end = mTvDesMeasureHeight;
			if (isAnimation) {
				doAnimation(start, end);
			} else {
				mTvDes.setHeight(end);
			}
		}
		isOpen = !isOpen;
	}

	public void doAnimation(int start, int end) {
		ObjectAnimator animator = ObjectAnimator.ofInt(mTvDes, "height", start, end);
		animator.start();

		// 2.监听动画执行的过程(开始,结束,取消)
		animator.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animator) {// 动画开始
				// TODO

			}

			@Override
			public void onAnimationRepeat(Animator animator) {// 动画重复
				// TODO

			}

			@Override
			public void onAnimationEnd(Animator animator) {// 动画结束
				// 找到ScrollView,进行滚动效果
				ViewParent parent = mTvDes.getParent();// 爹
				while (true) {
					parent = parent.getParent();// 爹 爹
					if (parent == null) {
						break;
					}

					if (parent instanceof ScrollView) {// 找到了
						// 滚动  ScrollView 底部
						((ScrollView) parent).fullScroll(View.FOCUS_DOWN);
						break;
					}
				}
			}

			@Override
			public void onAnimationCancel(Animator animator) {// 动画取消
				// TODO

			}
		});

		// 箭头跟着动
		if (isOpen) {
			ObjectAnimator.ofFloat(mIvArrow, "rotation", 180, 0).start();
		} else {
			ObjectAnimator.ofFloat(mIvArrow, "rotation", 0, 180).start();
		}
	}

	private int getShorHeight(int line, String des) {
		TextView tempTextView = new TextView(UIUtils.getContext());
		tempTextView.setText(des);
		tempTextView.setLines(line);// 需要设置行高才可以拿到具体的高度

		tempTextView.measure(0, 0);
		int measuredHeight = tempTextView.getMeasuredHeight();

		return measuredHeight;
	}

}
