package com.itheima.googleplay_13.fragment;

import java.util.List;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.itheima.googleplay_13.base.BaseFragment;
import com.itheima.googleplay_13.base.BaseHolder;
import com.itheima.googleplay_13.base.LoadingPager.LoadedResult;
import com.itheima.googleplay_13.base.SuperBaseAdapter;
import com.itheima.googleplay_13.bean.CategoryInfoBean;
import com.itheima.googleplay_13.factory.ListViewFactory;
import com.itheima.googleplay_13.holder.CategoryNormalHodler;
import com.itheima.googleplay_13.holder.CategoryTitleHodler;
import com.itheima.googleplay_13.protocol.CategoryProtocol;
import com.itheima.googleplay_13.utils.LogUtils;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-20 上午11:22:21
 * @描述	     TODO
 *
 * @版本       $Rev: 42 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-24 10:44:37 +0800 (星期六, 24 十月 2015) $
 * @更新描述    TODO
 */
public class CategoryFragment extends BaseFragment {
	private List<CategoryInfoBean>	mDatas;

	/**
	 * @des 真正开始加载数据了,在子线程中执行
	 * @call 外界调用了triggerLoadData()方法的时候
	 */
	@Override
	protected LoadedResult initData() {
		CategoryProtocol protocol = new CategoryProtocol();
		try {
			mDatas = protocol.loadData(0);
			LogUtils.printList(mDatas);
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
		ListView lv = ListViewFactory.createListView();
		lv.setAdapter(new CategoryAdapter(lv, mDatas));
		return lv;
	}

	class CategoryAdapter extends SuperBaseAdapter<CategoryInfoBean> {

		public CategoryAdapter(AbsListView absListView, List<CategoryInfoBean> datas) {
			super(absListView, datas);
		}

		@Override
		protected BaseHolder<CategoryInfoBean> getSpecialHolder(int position) {
			CategoryInfoBean categoryInfoBean = mDatas.get(position);

			if (categoryInfoBean.isTitle) {
				// title的holder;
				return new CategoryTitleHodler();
			} else {
				// 普通的holder;
				return new CategoryNormalHodler();
			}
		}

		@Override
		protected boolean hasLoadMore() {
			return false;
		}

		@Override
		public int getViewTypeCount() {
			return super.getViewTypeCount() + 1;// 2+1 = 3;
		}

		@Override
		protected int getNormalItemViewType(int position) {// 0
			CategoryInfoBean categoryInfoBean = mDatas.get(position);

			if (categoryInfoBean.isTitle) {
				return 1;
			} else {
				return 2;
			}
		}
	}

}
