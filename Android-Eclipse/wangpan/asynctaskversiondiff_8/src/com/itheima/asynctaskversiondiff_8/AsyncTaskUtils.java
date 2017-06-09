package com.itheima.asynctaskversiondiff_8;

import android.os.AsyncTask;

/**
 * @author  Administrator
 * @time 	2015-7-27 下午3:53:55
 * @des	TODO
 *
 * @version $Rev$
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes TODO
 */
public class AsyncTaskUtils {
	public static <T> void doAsync(final IDataCallBack<T> callBack) {
		new AsyncTask<Void, Void, T>() {

			protected void onPreExecute() {
				callBack.onTaskBefore();
			};

			@Override
			protected T doInBackground(Void... params) {
				// TODO
				return callBack.onTasking(params);
			}

			protected void onPostExecute(T result) {
				callBack.onTaskAfter(result);
			};

		}.execute();
	}
}
