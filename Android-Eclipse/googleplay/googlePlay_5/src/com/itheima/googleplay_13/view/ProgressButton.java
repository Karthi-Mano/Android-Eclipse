package com.itheima.googleplay_13.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-25 上午11:09:57
 * @描述	     TODO
 *
 * @版本       $Rev: 60 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-25 11:27:33 +0800 (星期日, 25 十月 2015) $
 * @更新描述    TODO
 */
public class ProgressButton extends Button {

	private long	mMax				= 100;
	private long	mProgress;
	private boolean	isProgressEnable	= true;

	/**设置进度的最大值*/
	public void setMax(long max) {
		mMax = max;
	}

	/**设置进度的当前值*/
	public void setProgress(long progress) {
		mProgress = progress;
		// 重绘操作
		invalidate();
	}

	/**设置是否允许进度*/
	public void setProgressEnable(boolean isProgressEnable) {
		this.isProgressEnable = isProgressEnable;
	}

	public ProgressButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ProgressButton(Context context) {
		super(context);
	}

	// onlayout ondraw onmeasure

	@Override
	protected void onDraw(Canvas canvas) {
		if (isProgressEnable) {
			// canvas.drawText("哈哈哈", getLeft(), getTop(), getPaint());
			Drawable bg = new ColorDrawable(Color.BLUE);
			int left = 0;
			int top = 0;
			int right = (int) (mProgress * 1.0f / mMax * getMeasuredWidth() + .5f);
			int bottom = getBottom();

			bg.setBounds(left, top, right, bottom);

			bg.draw(canvas);
		}

		super.onDraw(canvas);// (画文字)
	}
}
