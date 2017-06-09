package com.itheima.googleplay_13.manager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-21 上午9:51:01
 * @描述	     帮ThreadPoolExecutor做一些事情
 * @描述	     使用ThreadPoolExecutor更加方面,关心真正关心的即可
 * @描述	     提交任务,执行任务,移除任务
 * 
 *
 * @版本       $Rev: 14 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-21 10:25:51 +0800 (星期三, 21 十月 2015) $
 * @更新描述    TODO
 */
public class ThreadPoolExecutorProxy {
	
	ThreadPoolExecutor	mExecutor;

	private int			mCorePoolSize;
	private int			mMaximumPoolSize;
	private long		mKeepAliveTime;

	public ThreadPoolExecutorProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
		super();
		mCorePoolSize = corePoolSize;
		mMaximumPoolSize = maximumPoolSize;
		mKeepAliveTime = keepAliveTime;
	}

	/**
	 * 双重检查加锁
	 * 只有在第一次初始化的时候才启用同步机制,提高了性能
	 */
	private void initThreadPoolExecutor() {
		if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {
			synchronized (ThreadPoolExecutorProxy.class) {
				if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {
					TimeUnit unit = TimeUnit.MILLISECONDS;
					BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
					ThreadFactory threadFactory = Executors.defaultThreadFactory();
					RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
					mExecutor = new ThreadPoolExecutor(//
							mCorePoolSize, // 核心线程数
							mMaximumPoolSize,// 最大线程数
							mKeepAliveTime, // 保持时间
							unit, // 时间单位
							workQueue,// 工作队列
							threadFactory,// 线程工厂
							handler// 异常捕获器
							);
				}
			}
		}
	}

	/**
	 * 单例
	 * 1.一个类只有一个实例
	 * 2.一个类里面的成员变量只有一个实例
	 */
	/**
	 * 提交任务&执行任务的区别?
	 * 1.是否有返回值的问题
	 * 2.Future可以通过里面定义的get方法得到执行的结果
	 * 3.Future可以通过里面定义的get方法还可以try到执行过程中的异常信息
	 */
	/**提交任务*/
	public Future<?> submit(Runnable task) {
		initThreadPoolExecutor();
		return mExecutor.submit(task);
	}

	/**执行任务*/
	public void execute(Runnable task) {
		initThreadPoolExecutor();
		mExecutor.execute(task);
	}

	/**移除任务*/
	public void remove(Runnable task) {
		initThreadPoolExecutor();
		mExecutor.remove(task);
	}
}
