package com.itheima.googleplay_13.base;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.itheima.googleplay_13.factory.ThreadPoolExecutorProxyFactory;
import com.itheima.googleplay_13.holder.LoadMoreHolder;
import com.itheima.googleplay_13.utils.LogUtils;
import com.itheima.googleplay_13.utils.UIUtils;

/**
 * @param <ITEMBEANTYPE>
 * @创建者	 Administrator
 * @创时间 	 2015-10-21 上午10:44:37
 * @描述	     TODO
 *
 * @版本       $Rev: 44 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-24 14:24:46 +0800 (星期六, 24 十月 2015) $
 * @更新描述    TODO
 */
public abstract class SuperBaseAdapter<ITEMBEANTYPE> extends BaseAdapter implements OnItemClickListener {
	private static final int	VIEWTYPE_LOADMORE	= 0;
	private static final int	VIEWTYPE_NORMAL		= 1;
	protected List<ITEMBEANTYPE>	mDataSource;
	private LoadMoreHolder		mLoadMoreHolder;
	private LoadMoreTask		mLoadMoreTask;
	private AbsListView			mAbsListView;

	public SuperBaseAdapter(AbsListView absListView, List<ITEMBEANTYPE> datas) {
		super();
		mAbsListView = absListView;
		mAbsListView.setOnItemClickListener(this);
		mDataSource = datas;
	}

	@Override
	public int getCount() {
		if (mDataSource != null) {
			return mDataSource.size() + 1;
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (mDataSource != null) {
			return mDataSource.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/*--------------- listView中显示几种ViewType begin ---------------*/

	/**
	 listView中显示几种ViewType
	 	1.覆写2个方法
	 	2.在getView方法中,返回不同视图,绑定不同数据
	 */

	/**
	 get	ViewType	Count(总数)==>得到ViewType的总数
	 */
	@Override
	public int getViewTypeCount() {
		// 默认是1
		return super.getViewTypeCount() + 1;// 1(加载更多的类型)+1(普通类型) = 2
	}

	/**
	 get	Item	ViewType(int position)==>得到指定position对应的item的ViewType类型
	 */
	/**
	  range
	   0 
	   to 
	   getViewTypeCount - 1   
	 */
	@Override
	public int getItemViewType(int position) {
		if (position == getCount() - 1) {
			return VIEWTYPE_LOADMORE;// 0
		} else {
			return getNormalItemViewType(position);
		}
	}

	/*--------------- listView中显示几种ViewType end ---------------*/
	/**
	 * @des 得到普通条目的viewType类型,默认返回的是1
	 * @des 子类可以覆写该方法,返回更多的类型
	 * @call 子类里面的ViewType类型超过2种的时候
	 */
	protected int getNormalItemViewType(int position) {
		return VIEWTYPE_NORMAL;// 1, 默认
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		/**
			系统返回converView之前,首先会判断即将显示的ItemView的ViewType是否存在缓存
		 */

		/*--------------- 决定根视图 ---------------*/
		BaseHolder<ITEMBEANTYPE> baseHolder = null;
		if (convertView == null) {
			if (getItemViewType(position) == VIEWTYPE_LOADMORE) {
				// 返回加载更多的holder
				baseHolder = (BaseHolder<ITEMBEANTYPE>) getLoadMoreHolder();
			} else {
				// 返回普通的holder
				baseHolder = getSpecialHolder(position);
			}

		} else {
			baseHolder = (BaseHolder) convertView.getTag();
		}
		/*--------------- 数据和视图的绑定 ---------------*/
		if (getItemViewType(position) == VIEWTYPE_LOADMORE) {// 加载更多的视图
			// 加载更多的数据
			if (hasLoadMore()) {
				// TODO 去加载更多的数据
				triggerLoadMoreData();
			} else {
				// 显示没有加载更多应有的视图(控制loadmore视图的显示)
				mLoadMoreHolder.setDataAndRefreshHolderView(LoadMoreHolder.LOADMORE_NONE);
			}
		} else {// 普通的视图
				// data
			ITEMBEANTYPE data = mDataSource.get(position);
			// 接收数据,绑定数据
			baseHolder.setDataAndRefreshHolderView(data);
		}
		// LogUtils.sf(baseHolder.mHolderView.toString());
		return baseHolder.mHolderView;
	}

	/*--------------- 加载更多相关的逻辑 begin ---------------*/
	/**
	 * @des 返回加载更多的视图
	 * @call (convertView == null)&&滑到底的时候
	 * @return
	 */
	private LoadMoreHolder getLoadMoreHolder() {
		if (mLoadMoreHolder == null) {
			mLoadMoreHolder = new LoadMoreHolder();
		}
		return mLoadMoreHolder;
	}

	/**
	 * @des 决定是否有更多,默认有加载更多,子类可以覆写该方法,修改返回值,决定子类是否有加载更多
	 * @call 滑到底的时候
	 * @call 如果子类不覆写,调用的是父类里面的hasLoadMore(),
	 * @call 如果子类覆写,调用的是子类里面的hasLoadMore(),
	 */
	protected boolean hasLoadMore() {
		return true;
	}

	/**
	 * @des 触发加载更多的数据
	 * @call 滑动底,而且有加载更多的情况
	 */
	private void triggerLoadMoreData() {
		if (mLoadMoreTask == null) {// 没有加载更多
			LogUtils.sf("###triggerLoadMoreData");
			// 初始化mLoadMoreHolder视图
			int state = LoadMoreHolder.LOADMORE_LOADING;
			mLoadMoreHolder.setDataAndRefreshHolderView(state);

			mLoadMoreTask = new LoadMoreTask();
			ThreadPoolExecutorProxyFactory.getNormalPoolExecutorProxy().execute(mLoadMoreTask);
		}
	}

	class LoadMoreTask implements Runnable {
		private static final int	PAGERSIZE	= 20;

		@Override
		public void run() {
			/*--------------- 定义了2个决定ui的变量 ---------------*/
			int state;
			List<ITEMBEANTYPE> loadMoreData = null;
			/*--------------- 数据加载,以及修改定义的2个变量 ---------------*/
			// 真正的在子线程中加载更多
			try {
				loadMoreData = onLoadMore();
				// 根据loadMoreData-->state
				if (loadMoreData == null) {
					state = LoadMoreHolder.LOADMORE_NONE;// 没有加载更多
				}
				if (loadMoreData.size() < PAGERSIZE) {// 没有加载更多
					state = LoadMoreHolder.LOADMORE_NONE;// 没有加载更多
				} else {
					// 想20条-->回来20条
					state = LoadMoreHolder.LOADMORE_LOADING;// 可能有加载更多
				}

			} catch (Exception e) {
				e.printStackTrace();
				state = LoadMoreHolder.LOADMORE_RETRY;
			}
			/*--------------- 定义临时变量 ---------------*/
			final int tempState = state;
			final List<ITEMBEANTYPE> tempLoadMoreData = loadMoreData;

			/*--------------- 根据当前的数据刷新ui ---------------*/
			UIUtils.postTaskSafely(new Runnable() {

				@Override
				public void run() {
					// 刷新ui
					// 1.listview-->更新数据集-->notifyDataSetChanged方法(希望完成加载更多之后返回一个 "集合值")
					if (tempLoadMoreData != null) {
						mDataSource.addAll(tempLoadMoreData);// 更新数据集
						notifyDataSetChanged();// adapter刷新操作
					}

					// 2.刷新mLoadMoreHolder(希望完成加载更多之后返回一个 "int值")
					mLoadMoreHolder.setDataAndRefreshHolderView(tempState);
				}
			});

			// 置空当前的loadMoreTask
			mLoadMoreTask = null;
		}
	}

	/**
	 方法定义的地方抛出异常,是什么含义?
	 	也就是方法体里面如果出现异常,会往外抛,抛到方法的调用处
	 什么时候方法定义的地方,需要抛异常?
	 	方法的调用处会根据方法体里面异常做相应的逻辑处理时候需要抛出来
	 */
	/**
	 * @des 真正在子线程中加载更多的数据
	 * @des 默认返回null,但是子类可以选择性的覆写该方法,放回具体的加载更多之后的数据
	 * @return
	 */
	protected List<ITEMBEANTYPE> onLoadMore() throws Exception {

		return null;// 默认返回为null
	}

	/*--------------- 加载更多相关的逻辑 end ---------------*/

	/**
	 * @param position 
	 * @des 返回一个BaseHolder的子类对象
	 * @des 必须实现,但是不知道具体实现,定义成为抽象方法,交给子类具体实现
	 * @call 来到getView方法中&&(convertView == null)
	 * @return
	 */
	protected abstract BaseHolder<ITEMBEANTYPE> getSpecialHolder(int position);

	/*--------------- 处理item的点击事件 ---------------*/
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		if (mAbsListView instanceof ListView) {
			position = position - ((ListView) mAbsListView).getHeaderViewsCount();
		}

		if (getItemViewType(position) == VIEWTYPE_LOADMORE) {
			// 重新去加载更多的数据
			// 触发加载更多
			triggerLoadMoreData();
		} else {
			// 普通条目
			onNormalItemClick(parent, view, position, id);
		}
	}

	/**
	 * @des 普通条目的点击事件
	 * @des 子类可以覆写该方法,处理相应的普通条目的点击事件
	 * @call 用户点击了普通条目的时候
	 */
	public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {

	}
}
