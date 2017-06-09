package com.itheima.asynctaskversiondiff_8;

/**
 * @author  Administrator
 * @time 	2015-7-27 下午3:56:12
 * @des	TODO
 *
 * @version $Rev$
 * @param <T>
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes TODO
 */
public interface IDataCallBack<T> {
	/**任务执行之前*/
	void onTaskBefore();

	/**任务执行中...*/
	T onTasking(Void... params);

	/**任务执行之后*/
	void onTaskAfter(T result);

}
