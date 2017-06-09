package com.itheima.googleplay_13.fragment;

import java.util.Random;

import com.itheima.googleplay_13.base.BaseFragment;
import com.itheima.googleplay_13.base.LoadingPager.LoadedResult;
import com.itheima.googleplay_13.utils.UIUtils;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-20 上午11:22:21
 * @描述	     TODO
 *
 * @版本       $Rev: 11 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-20 17:13:37 +0800 (星期二, 20 十月 2015) $
 * @更新描述    TODO
 */
public class GameFragment extends BaseFragment {
	/**
	 * @des 真正开始加载数据了,在子线程中执行
	 * @call 外界调用了triggerLoadData()方法的时候
	 */
	@Override
	protected LoadedResult initData() {
		SystemClock.sleep(2000);
		// 成功
		// 失败(空,错误)
		// public static final int STATE_EMPTY = 1; // 空
		// public static final int STATE_ERROR = 2; // 错误
		// public static final int STATE_SUCCESS = 3; // 成功

		// 随机返回3种状态
		LoadedResult[] loadedResultArr = { LoadedResult.SUCCESS, LoadedResult.EMPTY, LoadedResult.ERROR };

		Random random = new Random();
		int index = random.nextInt(loadedResultArr.length);// 0 1 2

		return loadedResultArr[index];// 数据加载之后的状态,应该有几种
	}

	/**
	 * @des 创建成功视图
	 * @call 触发加载数据,数据加载完成之后,得到数据加载结果,会重新刷新UI,而且数据加载成功了.而且成功视图还没有
	 */
	@Override
	protected View initSuccessView() {
		TextView tv = new TextView(UIUtils.getContext());
		tv.setText(this.getClass().getSimpleName());
		tv.setGravity(Gravity.CENTER);
		return tv;
	}

}
