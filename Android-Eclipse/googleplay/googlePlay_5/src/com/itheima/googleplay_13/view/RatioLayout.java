package com.itheima.googleplay_13.view;

import com.itheima.googleplay_13.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-23 下午4:51:47
 * @描述	      已知宽度,能够动态计算高度
 *
 * @版本       $Rev: 52 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-24 16:49:21 +0800 (星期六, 24 十月 2015) $
 * @更新描述    TODO
 */
public class RatioLayout extends FrameLayout {
	// 图片的宽高比==RatioLayout宽/RatioLayout高
	private float			mPicRatio		= 0.0f;

	/**1.已知宽度,能够动态计算高度*/
	public static final int	RELATIVE_WIDTH	= 0;
	/** 2.已知高度,能够动态计算宽度*/
	public static final int	RELATIVE_HEIGHT	= 1;

	private int				relative		= RELATIVE_WIDTH;	// 默认是,已知宽度,能够动态计算高度


	public void setPicRatio(float picRatio) {
		mPicRatio = picRatio;
	}

	public void setRelative(int relative) {
		this.relative = relative;
	}

	public RatioLayout(Context context) {
		this(context, null);
	}

	public RatioLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 取出自定义的属性

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);

		mPicRatio = typedArray.getFloat(R.styleable.RatioLayout_picRatio, 0.0f);// xml-->2.43

		relative = typedArray.getInt(R.styleable.RatioLayout_relative, RELATIVE_WIDTH);

		typedArray.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		/**
		 android.view.View.MeasureSpec.UNSPECIFIED wrap_content
		 android.view.View.MeasureSpec.EXACTLY match_parent 100px 100dp
		 android.view.View.MeasureSpec.AT_MOST 
		 */
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		if (widthMode == MeasureSpec.EXACTLY && relative == RELATIVE_WIDTH) {// 宽度已知的情况-->动态计算高度
			// 获取自身的宽度
			int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
			int parentHeight = (int) (parentWidth / mPicRatio + .5f);

			// 孩子的宽度和高度
			int childWidth = parentWidth - getPaddingLeft() - getPaddingRight();
			int childHeigth = parentHeight - getPaddingBottom() - getPaddingTop();

			// 保存测量结果
			setMeasuredDimension(parentWidth, parentHeight);

			// 告知孩子测绘方式,请求孩子测绘自身
			int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
			int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeigth, MeasureSpec.EXACTLY);
			measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);
		} else if (heightMode == MeasureSpec.EXACTLY && relative == RELATIVE_HEIGHT) {// 高度已知的情况-->动态计算宽度
			// 获取自身的高度
			// 图片的宽高比==RatioLayout宽/RatioLayout高
			int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
			int parentWidth = (int) (mPicRatio * parentHeight + .5f);

			// 孩子的宽度和高度
			int childWidth = parentWidth - getPaddingLeft() - getPaddingRight();
			int childHeigth = parentHeight - getPaddingBottom() - getPaddingTop();

			// 保存测量结果
			setMeasuredDimension(parentWidth, parentHeight);

			// 告知孩子测绘方式,请求孩子测绘自身
			int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
			int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeigth, MeasureSpec.EXACTLY);
			measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}

	}
}
