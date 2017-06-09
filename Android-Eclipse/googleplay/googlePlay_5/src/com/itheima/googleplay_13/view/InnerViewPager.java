package com.itheima.googleplay_13.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-23 下午3:39:23
 * @描述	     TODO
 *
 * @版本       $Rev: 33 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-23 16:43:11 +0800 (星期五, 23 十月 2015) $
 * @更新描述    TODO
 */
public class InnerViewPager extends ViewPager {

	private float	mDownX;
	private float	mDownY;

	public InnerViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public InnerViewPager(Context context) {
		super(context);
	}

	/**
	 左右滚动的时候:请求父亲不拦截事件(true)-->自己处理action_move
	 上下滚动的时候:请求父亲不拦截事件(false)-->父容器处理action_move
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mDownX = ev.getRawX();
			mDownY = ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			float moveX = ev.getRawX();
			float moveY = ev.getRawY();

			int diffX = (int) (moveX - mDownX + .5f);
			int diffY = (int) (moveY - mDownY + .5f);
			
			//getParent(父亲).request(请求)Disallow(不)Intercept(拦截)TouchEvent(touch事件)(true(同意))
			
			if (Math.abs(diffX) > Math.abs(diffY)) {// 请求父亲不拦截事件(true)
				getParent().requestDisallowInterceptTouchEvent(true);
			} else {// 请求父亲不拦截事件(false)
				getParent().requestDisallowInterceptTouchEvent(false);
			}
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			
			break;

		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

}
