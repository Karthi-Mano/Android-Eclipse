package com.itheima.googleplay_13.fragment;

import java.util.List;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.itheima.googleplay_13.base.BaseFragment;
import com.itheima.googleplay_13.base.BaseHolder;
import com.itheima.googleplay_13.base.LoadingPager.LoadedResult;
import com.itheima.googleplay_13.base.SuperBaseAdapter;
import com.itheima.googleplay_13.bean.SubjectInfoBean;
import com.itheima.googleplay_13.factory.ListViewFactory;
import com.itheima.googleplay_13.holder.SubjectHolder;
import com.itheima.googleplay_13.protocol.SujectProtocol;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-20 上午11:22:21
 * @描述	     TODO
 *
 * @版本       $Rev: 33 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-23 16:43:11 +0800 (星期五, 23 十月 2015) $
 * @更新描述    TODO
 */
public class SubjectFragment extends BaseFragment {
	private List<SubjectInfoBean>	mDatas;

	/**
	 * @des 真正开始加载数据了,在子线程中执行
	 * @call 外界调用了triggerLoadData()方法的时候
	 */
	@Override
	protected LoadedResult initData() {
		SujectProtocol protocol = new SujectProtocol();
		try {
			mDatas = protocol.loadData(0);
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
		listView.setAdapter(new SubjectAdapter(listView, mDatas));
		return listView;
	}

	class SubjectAdapter extends SuperBaseAdapter<SubjectInfoBean> {

		public SubjectAdapter(AbsListView absListView, List<SubjectInfoBean> datas) {
			super(absListView, datas);
		}

		@Override
		protected BaseHolder<SubjectInfoBean> getSpecialHolder() {

			return new SubjectHolder();
		}
		@Override
		protected boolean hasLoadMore() {
			return false;
		}
	}
}
