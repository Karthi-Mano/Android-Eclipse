package com.itheima.googleplay_13.activity;

import android.view.View;
import android.widget.FrameLayout;

import com.itheima.googleplay_13.R;
import com.itheima.googleplay_13.base.BaseActivity;
import com.itheima.googleplay_13.base.LoadingPager;
import com.itheima.googleplay_13.base.LoadingPager.LoadedResult;
import com.itheima.googleplay_13.bean.AppInfoBean;
import com.itheima.googleplay_13.holder.AppDetailBottomHolder;
import com.itheima.googleplay_13.holder.AppDetailDesHolder;
import com.itheima.googleplay_13.holder.AppDetailInfoHolder;
import com.itheima.googleplay_13.holder.AppDetailPicHolder;
import com.itheima.googleplay_13.holder.AppDetailSafeHolder;
import com.itheima.googleplay_13.manager.DownLoadManager;
import com.itheima.googleplay_13.protocol.DetailProtocol;
import com.itheima.googleplay_13.utils.LogUtils;
import com.itheima.googleplay_13.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-24 下午2:26:34
 * @描述	     TODO
 *
 * @版本       $Rev: 69 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-25 18:21:15 +0800 (星期日, 25 十月 2015) $
 * @更新描述    TODO
 */
public class DetailActivity extends BaseActivity {

	@ViewInject(R.id.detail_fl_bottom)
	FrameLayout						mContainerBottom;

	@ViewInject(R.id.detail_fl_des)
	FrameLayout						mContainerDes;

	@ViewInject(R.id.detail_fl_info)
	FrameLayout						mContainerInfo;

	@ViewInject(R.id.detail_fl_pic)
	FrameLayout						mContainerPic;

	@ViewInject(R.id.detail_fl_safe)
	FrameLayout						mContainerSafe;
	private String					mPackageName;
	private LoadingPager			mLoadingPager;
	private AppInfoBean				mAppInfoBean;

	private AppDetailBottomHolder	mAppDetailBottomHolder;

	/*	@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			init();
			initView();
			initData();
			initListener();
		}*/

	@Override
	protected void init() {
		mPackageName = getIntent().getStringExtra("packageName");
	}
	@Override
	protected void initView() {
		mLoadingPager = new LoadingPager(UIUtils.getContext()) {
			/**
			 * @des 真正开始加载数据了,在子线程中执行
			 * @call 外界调用了triggerLoadData()方法的时候
			 */
			@Override
			protected LoadedResult initData() {
				return DetailActivity.this.loadData();
			}

			/**
			 * @des 创建成功视图
			 * @call 触发加载数据,数据加载完成之后,得到数据加载结果,会重新刷新UI,而且数据加载成功了.而且成功视图还没有
			 */
			@Override
			protected View initSuccessView() {
				return DetailActivity.this.initSuccessView();
			}
		};
		setContentView(mLoadingPager);
	}

	@Override
	protected void initData() {
		// 触发加载数据
		mLoadingPager.triggerLoadData();
	}
	@Override
	protected void initListener() {
	
	}
	protected View initSuccessView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.item_detail, null);
		// 找出孩子
		ViewUtils.inject(this, view);

		// 接收holder提供的视图

		// 应用的信息部分

		AppDetailInfoHolder appDetailInfoHolder = new AppDetailInfoHolder();
		mContainerInfo.addView(appDetailInfoHolder.mHolderView);
		appDetailInfoHolder.setDataAndRefreshHolderView(mAppInfoBean);

		// 应用的安全部分
		AppDetailSafeHolder appDetailSafeHolder = new AppDetailSafeHolder();
		mContainerSafe.addView(appDetailSafeHolder.mHolderView);
		appDetailSafeHolder.setDataAndRefreshHolderView(mAppInfoBean);

		// 应用的截图部分
		AppDetailPicHolder appDetailPicHolder = new AppDetailPicHolder();
		mContainerPic.addView(appDetailPicHolder.mHolderView);
		appDetailPicHolder.setDataAndRefreshHolderView(mAppInfoBean);

		// 应用的描述部分
		AppDetailDesHolder appDetailDesHolder = new AppDetailDesHolder();
		mContainerDes.addView(appDetailDesHolder.mHolderView);
		appDetailDesHolder.setDataAndRefreshHolderView(mAppInfoBean);

		mAppDetailBottomHolder = new AppDetailBottomHolder();
		mContainerBottom.addView(mAppDetailBottomHolder.mHolderView);
		mAppDetailBottomHolder.setDataAndRefreshHolderView(mAppInfoBean);

		// 添加到观察者集合中
		DownLoadManager.getInstance().addObserver(mAppDetailBottomHolder);

		return view;
	}

	@Override
	protected void onPause() {
		// 移除观察者
		if (mAppDetailBottomHolder != null) {
			DownLoadManager.getInstance().deleteObserver(mAppDetailBottomHolder);
		}

		super.onPause();
	}

	@Override
	protected void onResume() {
		
		
		 LogUtils.s("第一次进入详细页面，注册观察者：    "+(mAppDetailBottomHolder==null));
		
		
		// 添加观察者
		// 手动的发布一下
		if (mAppDetailBottomHolder != null) {

			mAppDetailBottomHolder.addObserverAndNotify();

		}
		super.onResume();
	}

	/**
	 * @des 真正开始加载数据了,在子线程中执行
	 * @call 外界调用了triggerLoadData()方法的时候
	 */
	protected LoadedResult loadData() {
		DetailProtocol protocol = new DetailProtocol(mPackageName);
		try {
			mAppInfoBean = protocol.loadData(0);
			if (mAppInfoBean != null) {
				return LoadedResult.SUCCESS;
			} else {
				return LoadedResult.EMPTY;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return LoadedResult.ERROR;
		}
	}

}
