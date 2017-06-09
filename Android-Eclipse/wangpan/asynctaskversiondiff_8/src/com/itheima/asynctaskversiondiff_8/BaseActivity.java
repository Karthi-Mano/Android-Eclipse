package com.itheima.asynctaskversiondiff_8;

import android.app.Activity;

/**
 * @author  Administrator
 * @time 	2015-7-27 下午4:05:26
 * @des	TODO
 *
 * @version $Rev$
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes TODO
 */
public class BaseActivity extends Activity {

	public <T> void doAsync(IDataCallBack<T> callBack) {
		AsyncTaskUtils.doAsync(callBack);
	}

}
