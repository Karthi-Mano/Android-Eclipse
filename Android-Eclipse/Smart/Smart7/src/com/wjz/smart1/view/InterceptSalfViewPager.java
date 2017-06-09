package com.wjz.smart1.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class InterceptSalfViewPager extends ViewPager {

	private float downX;
	private float downY;
	private float moveX;
	private float moveY;

	public InterceptSalfViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public InterceptSalfViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub

		// true 申请父控件不拦截自己的touch事件，false默认父类先拦截事件
		// getParent().requestDisallowInterceptTouchEvent(true);

		// 事件有自身处理

		// 1.如果在第一个页面，并且是从左往右滑动，让父控件拦截
		// 2.如果是最后一个页面,并且是从有往左滑动，让父控件拦截
		// 3.否则都不让父控件拦截

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:// 按下
			getParent().requestDisallowInterceptTouchEvent(true);
			downX = ev.getX();
			//System.out.println(ev.getX());
			//System.out.println(getX());
			downY = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:// 移动
			moveX = ev.getX();
			moveY = ev.getY();

			float dx = moveX - downX;
			float dy = moveY - downY;

			if (Math.abs(dx) > Math.abs(dy)) {// 横向滑动

			//	System.out.println("横向滑动");
				
				
				// 如果在第一个页面，并且是从左往右滑动,让父控件拦截我
				if (getCurrentItem() == 0 && dx > 0) {
					// 由父控件处理该事件
					getParent().requestDisallowInterceptTouchEvent(false);
				} else if (getCurrentItem() == getAdapter().getCount()-1
						&& dx < 0) {// 如果在最后一个页面,并且是从右往左滑动，父控件拦截
					// 由父控件处理该事件
					getParent().requestDisallowInterceptTouchEvent(false);
				} else {//不是第一个也不是最后一个
					getParent().requestDisallowInterceptTouchEvent(true);
					// 否则都不让父类拦截
				}

			} else {// 纵向滑动
				
				//System.out.println("纵向滑动");
					// 由父控件处理该事件
				getParent().requestDisallowInterceptTouchEvent(false);
			}

			break;
		default:
			break;

		}

		return super.dispatchTouchEvent(ev);
	}
}
