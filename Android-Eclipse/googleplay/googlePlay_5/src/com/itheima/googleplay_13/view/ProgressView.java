package com.itheima.googleplay_13.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itheima.googleplay_13.R;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-25 下午3:32:30
 * @描述	     TODO
 *
 * @版本       $Rev: 65 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-25 16:15:00 +0800 (星期日, 25 十月 2015) $
 * @更新描述    TODO
 */
public class ProgressView extends LinearLayout {

	private TextView	mTvNote;
	private ImageView	mIvIcon;

	private long		mMax				= 100;
	private long		mProgress;
	private boolean		isProgressEnable	= true;

	/**设置是否允许进度*/
	public void setProgressEnable(boolean isProgressEnable) {
		this.isProgressEnable = isProgressEnable;
	}

	/**设置进度的最大值*/
	public void setMax(long max) {
		mMax = max;
	}

	/**设置进度的当前*/
	public void setProgress(long progress) {
		mProgress = progress;
		// 重绘操作
		invalidate();
	}

	/**修改文字内容*/
	public void setNote(String note) {
		mTvNote.setText(note);
	}

	/**修改图标内容*/
	public void setIcon(int resId) {
		mIvIcon.setImageResource(resId);
	}

	public ProgressView(Context context) {
		this(context, null);
	}

	public ProgressView(Context context, AttributeSet attrs) {
		super(context, attrs);
		View view = View.inflate(context, R.layout.inflate_progressview, this);
		mTvNote = (TextView) view.findViewById(R.id.tvNote);
		mIvIcon = (ImageView) view.findViewById(R.id.ivIcon);
	}

	// onmeasure onlayout ondraw
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);// 绘制背景

	}

	@Override
	protected void dispatchDraw(Canvas canvas) {

		super.dispatchDraw(canvas);// (图标,和文字)

		if (isProgressEnable) {
			Paint paint = new Paint();
			paint.setColor(Color.BLUE);
			paint.setAntiAlias(true);// 消除锯齿

			paint.setStrokeWidth(3);

			paint.setStyle(Style.STROKE);

			RectF oval = new RectF(mIvIcon.getLeft(), mIvIcon.getTop(), mIvIcon.getRight(), mIvIcon.getBottom());
			float startAngle = -90;

			float sweepAngle = mProgress * 360.0f / mMax;

			boolean useCenter = false;
			canvas.drawArc(oval, startAngle, sweepAngle, useCenter, paint);
		}

	}
}
