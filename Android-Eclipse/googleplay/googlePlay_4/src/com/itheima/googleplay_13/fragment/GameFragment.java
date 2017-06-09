package com.itheima.googleplay_13.fragment;

import java.util.List;
import java.util.Random;

import com.itheima.googleplay_13.base.BaseFragment;
import com.itheima.googleplay_13.base.BaseHolder;
import com.itheima.googleplay_13.base.BaseItemAdapter;
import com.itheima.googleplay_13.base.LoadingPager.LoadedResult;
import com.itheima.googleplay_13.base.SuperBaseAdapter;
import com.itheima.googleplay_13.bean.AppInfoBean;
import com.itheima.googleplay_13.factory.ListViewFactory;
import com.itheima.googleplay_13.holder.ItemHolder;
import com.itheima.googleplay_13.protocol.GameProtocol;
import com.itheima.googleplay_13.utils.UIUtils;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-20 上午11:22:21
 * @描述	     TODO
 *
 * @版本       $Rev: 44 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-24 14:24:46 +0800 (星期六, 24 十月 2015) $
 * @更新描述    TODO
 */
public class GameFragment extends BaseFragment {
	private List<AppInfoBean>	mDatas;
	private GameProtocol		mProtocol;

	/**
	 * @des 真正开始加载数据了,在子线程中执行
	 * @call 外界调用了triggerLoadData()方法的时候
	 */
	@Override
	protected LoadedResult initData() {
		mProtocol = new GameProtocol();
		try {
			mDatas = mProtocol.loadData(0);
			return checkState(mDatas);
		} catch (Exception e) {
			e.printStackTrace();
			return LoadedResult.ERROR;
		}
	}

	/**
	 * @des 创建成功视图
	 * @call 触发加载数据,数据加载完成之后,得到数据加载结果,会重新刷新UI,而且数据加载成功了.而且成功视图还没有
	 */
	@Override
	protected View initSuccessView() {
		ListView listView = ListViewFactory.createListView();
		listView.setAdapter(new GameAdapter(listView, mDatas));
		return listView;
	}

	class GameAdapter extends BaseItemAdapter {

		public GameAdapter(AbsListView absListView, List<AppInfoBean> datas) {
			super(absListView, datas);
		}

		@Override
		protected List<AppInfoBean> onLoadMore() throws Exception {
			// TODO
			return mProtocol.loadData(mDatas.size());
		}
	}

}
