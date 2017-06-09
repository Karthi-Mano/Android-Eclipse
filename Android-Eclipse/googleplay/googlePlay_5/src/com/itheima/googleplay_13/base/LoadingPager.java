package com.itheima.googleplay_13.base;

import com.itheima.googleplay_13.R;
import com.itheima.googleplay_13.factory.ThreadPoolExecutorProxyFactory;
import com.itheima.googleplay_13.utils.LogUtils;
import com.itheima.googleplay_13.utils.UIUtils;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-20 下午4:16:43
 * @描述	     1.放置4个常见的页面
 *
 * @版本       $Rev: 14 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-21 10:25:51 +0800 (星期三, 21 十月 2015) $
 * @更新描述    TODO
 */
public abstract class LoadingPager extends FrameLayout {

	private View			mLoadingView;
	private View			mErrorView;
	private View			mEmptyView;
	private View			mSuccessView;

	public static final int	STATE_LOADING	= 0;				// 加载中
	public static final int	STATE_EMPTY		= 1;				// 空
	public static final int	STATE_ERROR		= 2;				// 错误
	public static final int	STATE_SUCCESS	= 3;				// 成功
	public static final int	STATE_NONE		= 4;				// 初始化状态

	public int				mCurState		= STATE_NONE;

	public LoadingPager(Context context) {
		super(context);
		initCommonViews();
	}

	// 页面显示分析
	// Fragment/Activity共性-->页面共性-->视图的展示
	/**
	 任何应用其实就只有4种页面类型
	 ① 加载页面
	 ② 错误页面
	 ③ 空页面 				
	 ④ 成功页面 	
		①②③三种页面一个应用基本是固定的
		每一个fragment/activity对应的页面④就不一样
		进入应用的时候显示①,②③④需要加载数据之后才知道显示哪个		
	*/
	/**
	 * @des 初始化常规视图(加载页面,错误页面,空页面 	)
	 * @call LoadingPager初始化的时候
	 */
	private void initCommonViews() {
		// ① 加载页面
		mLoadingView = View.inflate(UIUtils.getContext(), R.layout.pager_loading, null);
		this.addView(mLoadingView);

		// ② 错误页面
		mErrorView = View.inflate(UIUtils.getContext(), R.layout.pager_error, null);
		this.addView(mErrorView);

		// ③ 空页面
		mEmptyView = View.inflate(UIUtils.getContext(), R.layout.pager_empty, null);
		this.addView(mEmptyView);

		refreshUIByState();
	}

	/**
	 * @des 根据当前状态,显示不同的ui
	 * @call 1.LoadingPager初始化的时候
	 * @call 2.触发加载数据之前,状态被重置
	 * @call 3.触发加载数据,数据加载完成之后,得到数据加载结果,会重新刷新UI
	 */
	private void refreshUIByState() {
		// 控制空视图的显示/隐藏
		mEmptyView.setVisibility((mCurState == STATE_EMPTY) ? 0 : 8);

		// 控制错误视图的显示/隐藏
		mErrorView.setVisibility((mCurState == STATE_ERROR) ? 0 : 8);
		
		mErrorView.findViewById(R.id.error_btn_retry).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//重新加载数据
				triggerLoadData();
			}
		});

		// 控制加载视图的显示/隐藏
		mLoadingView.setVisibility((mCurState == STATE_LOADING)||(mCurState == STATE_NONE) ? 0 : 8);

		// 数据加载成功了.而且成功视图还没有
		if (mCurState == STATE_SUCCESS && mSuccessView == null) {
			mSuccessView = initSuccessView();
			this.addView(mSuccessView);
		}

		if (mSuccessView != null) {
			// 控制加载视图的显示/隐藏
			mSuccessView.setVisibility((mCurState == STATE_SUCCESS) ? 0 : 8);
		}
	}

	// 数据加载的流程
	/**
	① 触发加载  	进入页面开始加载/点击某一个按钮的时候加载
	② 异步加载数据  -->显示加载视图
	③ 处理加载结果
		① 成功-->显示成功视图
		② 失败
			① 数据为空-->显示空视图
			② 数据加载失败-->显示加载失败的视图
	*/
	/**
	 * @des 触发异步加载数据
	 * @call 外界需要触发加载数据的时候调用该方法
	 */
	public void triggerLoadData() {
		// 没有成功&&没有正在加载
		if (mCurState != STATE_SUCCESS && mCurState != STATE_LOADING) {
			// 重置当前的状态
			mCurState = STATE_LOADING;
			refreshUIByState();

			// ② 异步加载数据
//			new Thread(new LoadDataTask()).start();
			ThreadPoolExecutorProxyFactory.getNormalPoolExecutorProxy().execute(new LoadDataTask());
		}
	}

	class LoadDataTask implements Runnable {
		@Override
		public void run() {
			// 正在开始加载数据了
			// 加载成功后返回一个临死的状态
			LoadedResult tempState = initData();
			// 赋值临时状态给当前的状态
			mCurState = tempState.state;

			UIUtils.postTaskSafely(new Runnable() {

				@Override
				public void run() {
					// 刷新ui
					refreshUIByState();
				}
			});
		}
	}

	/**
	 * @des 真正开始加载数据了,在子线程中执行
	 * @des 必须实现,但是不知道具体实现,定义成为抽象方法,交给子类具体实现
	 * @call 外界调用了triggerLoadData()方法的时候
	 */
	protected abstract LoadedResult initData();

	/**
	 * @des 创建成功视图
	 * @des 必须实现,但是不知道具体实现,定义成为抽象方法,交给子类具体实现
	 * @call 触发加载数据,数据加载完成之后,得到数据加载结果,会重新刷新UI,而且数据加载成功了.而且成功视图还没有
	 */
	protected abstract View initSuccessView();

	public enum LoadedResult {
		SUCCESS(STATE_SUCCESS), EMPTY(STATE_EMPTY), ERROR(STATE_ERROR);
		int	state;

		private LoadedResult(int state) {
			this.state = state;
		}
	}
}
