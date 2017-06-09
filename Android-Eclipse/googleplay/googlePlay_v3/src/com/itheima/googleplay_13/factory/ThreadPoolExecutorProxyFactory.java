package com.itheima.googleplay_13.factory;

import com.itheima.googleplay_13.manager.ThreadPoolExecutorProxy;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-21 上午10:20:00
 * @描述	     创建不同的线程池代理工厂
 *
 * @版本       $Rev: 15 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-21 10:44:05 +0800 (星期三, 21 十月 2015) $
 * @更新描述    TODO
 */
public class ThreadPoolExecutorProxyFactory {
	/**普通的线程池代理*/
	static ThreadPoolExecutorProxy	mNormalPoolExecutorProxy;
	/**下载的线程池代理*/
	static ThreadPoolExecutorProxy	mDownLoadPoolExecutorProxy;

	/**
	 * 得到普通的线程池代理
	 * @return
	 */
	public static ThreadPoolExecutorProxy getNormalPoolExecutorProxy() {
		if (mNormalPoolExecutorProxy == null) {
			synchronized (ThreadPoolExecutorProxyFactory.class) {
				if (mNormalPoolExecutorProxy == null) {
					mNormalPoolExecutorProxy = new ThreadPoolExecutorProxy(5, 5, 3000);
				}
			}
		}
		return mNormalPoolExecutorProxy;
	}

	/**
	 * 得到下载的线程池代理
	 * @return
	 */
	public static ThreadPoolExecutorProxy getDownLoadPoolExecutorProxy() {
		if (mDownLoadPoolExecutorProxy == null) {
			synchronized (ThreadPoolExecutorProxyFactory.class) {
				if (mDownLoadPoolExecutorProxy == null) {
					// 同一个时刻只能执行3个下载任务
					mDownLoadPoolExecutorProxy = new ThreadPoolExecutorProxy(3, 3, 3000);
				}
			}
		}
		return mDownLoadPoolExecutorProxy;
	}
}
