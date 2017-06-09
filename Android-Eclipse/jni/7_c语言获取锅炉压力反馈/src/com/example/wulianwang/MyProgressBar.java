package com.example.wulianwang;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MyProgressBar extends View {
	private int max;
	private int progress;
	private Paint paint;

	public MyProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyProgressBar(Context context) {
		super(context);
	}

	/**
	 * 设置进度条的最大值
	 * 
	 * @param max
	 */
	public void setMax(int max) {
		this.max = max;
		postInvalidate();
	}

	/**
	 * 设置进度条的进度
	 * 
	 * @param max
	 */
	public void setProgress(int progress) {
		this.progress = progress;
		// invalidate(); 主线程调用
		postInvalidate();// 子线程调用
	}

	@Override
	protected void onDraw(Canvas canvas) {
		paint = new Paint();
		if (progress < 50) {
			paint.setColor(Color.GREEN);
		} else if (progress < 80) {
			paint.setColor(Color.YELLOW);
		} else {
			paint.setColor(Color.RED);
		}
		canvas.drawRect(0, 300 - progress, 10, 300, paint);
		paint.setTextSize(20);
		paint.setStrokeWidth(3);
		canvas.drawText("压力为:" + progress, 20, 250, paint);
	}
}
