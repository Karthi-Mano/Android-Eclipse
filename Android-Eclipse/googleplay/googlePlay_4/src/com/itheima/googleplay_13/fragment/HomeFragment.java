package com.itheima.googleplay_13.fragment;

import java.io.IOException;
import java.util.List;

import android.os.SystemClock;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.itheima.googleplay_13.base.BaseFragment;
import com.itheima.googleplay_13.base.BaseItemAdapter;
import com.itheima.googleplay_13.base.LoadingPager.LoadedResult;
import com.itheima.googleplay_13.bean.AppInfoBean;
import com.itheima.googleplay_13.bean.HomeBean;
import com.itheima.googleplay_13.factory.ListViewFactory;
import com.itheima.googleplay_13.holder.HomePictureHolder;
import com.itheima.googleplay_13.protocol.HomeProtocol;
import com.lidroid.xutils.exception.HttpException;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-20 上午11:22:21
 * @描述	     TODO
 *
 * @版本       $Rev: 46 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-24 14:55:38 +0800 (星期六, 24 十月 2015) $
 * @更新描述    TODO
 */
public class HomeFragment extends BaseFragment {
	private List<AppInfoBean>	mAppInfoBeans;
	private List<String>		mPictures;
	private HomeProtocol		mProtocol;

	/**
	 * @des 真正开始加载数据了,在子线程中执行
	 * @call 外界调用了triggerLoadData()方法的时候
	 */
	@Override
	protected LoadedResult initData() {
		try {
			mProtocol = new HomeProtocol();
			HomeBean homeBean = mProtocol.loadData(0);
			LoadedResult state = checkState(homeBean);
			if (state != LoadedResult.SUCCESS) {// homeBean有问题了,homeBean==null
				return state;
			}

			state = checkState(homeBean.list);
			if (state != LoadedResult.SUCCESS) {// homeBean.list有问题-->homeBean.list.size==0
				return state;
			}
			// 走到这里来了
			// 赋值操作
			mAppInfoBeans = homeBean.list;
			mPictures = homeBean.picture;
			return state;
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
		
		HomePictureHolder homePictureHolder = new HomePictureHolder();
		listView.addHeaderView(homePictureHolder.mHolderView);
		homePictureHolder.setDataAndRefreshHolderView(mPictures);
		
		listView.setAdapter(new HomeAdapter(listView, mAppInfoBeans));
		return listView;
	}

	class HomeAdapter extends BaseItemAdapter {
		 
		public HomeAdapter(AbsListView absListView, List<AppInfoBean> datas) {
			super(absListView, datas);
		}
		/**
		 * @des 真正在子线程中加载更多的数据
		 * @des 默认返回null,但是子类可以选择性的覆写该方法,放回具体的加载更多之后的数据
		 * @return
		 */
		@Override
		protected List<AppInfoBean> onLoadMore() throws Exception {
			SystemClock.sleep(2000);
			return performLoadMore();
		}
	}
	public List<AppInfoBean> performLoadMore() throws HttpException, IOException, Exception {
		/*	HttpUtils httpUtils = new HttpUtils();
			// "http://localhost:8080/GooglePlayServer/home?index=0"
			String url = URlS.BASEURL + "home";
			RequestParams params = new RequestParams();
			params.addQueryStringParameter("index", mAppInfoBeans.size() + "");
			ResponseStream responseStream = httpUtils.sendSync(HttpMethod.GET, url, params);

			String jsonString = responseStream.readString();

			Gson gson = new Gson();
			HomeBean homeBean = gson.fromJson(jsonString, HomeBean.class);
			if (homeBean != null) {
				return homeBean.list;
			}
			*/
		/*--------------- 协议简单封装之后 ---------------*/
		HomeBean homeBean = mProtocol.loadData(mAppInfoBeans.size());
		if (homeBean != null) {
			return homeBean.list;
		}
		return null;
	}
	/*class HomeAdapter extends SuperBaseAdapter<String> {
		public HomeAdapter(List<String> datas) {
			super(datas);
		}

		*//**
		* @des 返回一个BaseHolder的子类对象
		* @call 来到getView方法中&&(convertView == null)
		* @return
		*/
	/*
	@Override
	protected BaseHolder<String> getSpecialHolder() {
	return new HomeHolder();
	}
	}*/

	/*class HomeAdapter extends MyBaseAdapter<String> {
	 	public HomeAdapter(List<String> datas) {
	 		super(datas);
	 	}

	 	@Override
	 	public View getView(int position, View convertView, ViewGroup parent) {
	 		--------------- 决定根视图 ---------------
			HomeHolder homeHolder = null;
			if (convertView == null) {
				homeHolder = new HomeHolder();
			} else {
				homeHolder = (HomeHolder) convertView.getTag();
			}
			//data
			String data = mDatas.get(position);
			//接收数据,绑定数据
			homeHolder.setDataAndRefreshHolderView(data);
			return homeHolder.mHolderView;
		}

	}*/
	/*class HomeAdapter extends MyBaseAdapter<String> {
		public HomeAdapter(List<String> datas) {
			super(datas);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			---------------   决定根布局 ---------------
			ViewHolder holder = null;
			if (convertView == null) {
				//1.初始化holder
				holder = new ViewHolder();
				//2.初始化根视图
				convertView = View.inflate(UIUtils.getContext(), R.layout.item_temp, null);
				//3.初始化孩子对象
				holder.mTvTmp1 = (TextView) convertView.findViewById(R.id.tmp_tv_1);
				holder.mTvTmp2 = (TextView) convertView.findViewById(R.id.tmp_tv_2);
				//4.根视图,找到一个Holder,然后把它绑定在自己身上
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			--------------- 得到数据 ---------------
			// data
			String data = mDatas.get(position);
			
			--------------- 数据视图绑定 ---------------
			
			// 数据和view的绑定
			holder.mTvTmp1.setText("我是头-" + data);
			holder.mTvTmp2.setText("我是尾巴-" + data);
			return convertView;
		}
		*//**
		* 做ViewHolder需要什么条件
		* 1.持有根布局对应的孩子对象
		* 2.持有根布局(可以通过findviewById找出孩子,然后变成成员变量)
		*/
	/*
	class ViewHolder {
	TextView	mTvTmp1;
	TextView	mTvTmp2;
	//方法
	//成员变量
	}
	}*/
}
