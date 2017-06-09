package com.wjz.smart1.tpinewscenter;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.wjz.smart1.R;
import com.wjz.smart1.activity.MainActivity;
import com.wjz.smart1.domain.NewsCenterData.NewsData.ViewTagData;
import com.wjz.smart1.domain.TpiNewsCenterData;
import com.wjz.smart1.domain.TpiNewsCenterData.TpiNewsCenterData_Data.TpiNewsCenterData_Data_news;
import com.wjz.smart1.domain.TpiNewsCenterData.TpiNewsCenterData_Data.TpiNewsCenterData_Data_topnews;
import com.wjz.smart1.utils.DensityUtil;
import com.wjz.smart1.utils.MyContainer;
import com.wjz.smart1.utils.SpTools;
import com.wjz.smart1.view.RefreshListView;
import com.wjz.smart1.view.RefreshListView.OnRefreshDataListener;

/**
 * @author Wjz
 */
public class TpiNewsCenter {

	private MainActivity mainActivity;
	private View root;
	private ViewTagData viewTagData;
	private ViewPager vp_lunbotu;
	private TextView tv_tpi;
	private LinearLayout ll_tpi;

	private Gson gson;

	// 轮播图的数据容器
	private List<TpiNewsCenterData_Data_topnews> lunBoTuData = new ArrayList<TpiNewsCenterData_Data_topnews>();
	private LunBoTuAdapter lunBotuAdapter;
	private BitmapUtils bitmapUtils;
	private int picSelectIndex;
	private Handler handler;
	private LunBoTask lunBoTask;
	private boolean isRefresh = false;

	private ListViewAdapter listViewAdapter;
	private List<TpiNewsCenterData_Data_news> listNewsData = new ArrayList<TpiNewsCenterData_Data_news>();
	private RefreshListView lv_tpi;
	private static final int REFRESHING = 3;

	public TpiNewsCenter(MainActivity mainActivity, ViewTagData viewTagData) {
		this.mainActivity = mainActivity;
		this.viewTagData = viewTagData;
		// 初始化bitmapUtils
		bitmapUtils = new BitmapUtils(mainActivity);
		// 设置bitmapUtils的属性
		bitmapUtils.configDefaultBitmapConfig(Config.ARGB_4444);

		gson = new Gson();
		initView();
		initData();
		initEvent();
	}

	
	private void initView() {

		root = View
				.inflate(mainActivity, R.layout.tpi_newscenter_content, null);
		lv_tpi = (RefreshListView) root.findViewById(R.id.lv_tpi_newscenter);
		View lunbotu = View.inflate(mainActivity,
				R.layout.tpi_newcenter_lunbopic, null);

		vp_lunbotu = (ViewPager) lunbotu.findViewById(R.id.vp_tpi_newscenter);
		tv_tpi = (TextView) lunbotu.findViewById(R.id.tv_tpi_newscenter);
		ll_tpi = (LinearLayout) lunbotu.findViewById(R.id.ll_tpi_newscenter);

		//启用下拉刷新的功能
		lv_tpi.setIsRefreshHead(true);
		// 将轮播图加到ListView的头部
		lv_tpi.addHeaderView(lunbotu);

	}

	private void initData() {

		lunBoTask = new LunBoTask();
		// 初始化轮播图的适配器
		lunBotuAdapter = new LunBoTuAdapter();

		// 初始化ListView的适配器
		listViewAdapter = new ListViewAdapter();

		// 为lv_tpi设置适配器
		lv_tpi.setAdapter(listViewAdapter);

		// 给vp_tpi设置适配器
		vp_lunbotu.setAdapter(lunBotuAdapter);

		// 本地数据
		String jsonCache = SpTools.getString(mainActivity, viewTagData.url, "");
		if (!TextUtils.isEmpty(jsonCache)) {
			TpiNewsCenterData parseJsonData = parseJsonData(jsonCache);

			// 处理数据
			processData(parseJsonData);
		}

		// 获取网络数据
		getDataFromNet();
	}

	private void getDataFromNet() {
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, MyContainer.PATH + viewTagData.url,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {

						String jsonData = responseInfo.result;

						SpTools.setString(mainActivity, viewTagData.url,
								jsonData);

						// 解析数据
						TpiNewsCenterData parseJsonData = parseJsonData(jsonData);

						// 处理数据
						processData(parseJsonData);

						if (isRefresh) {
							
							// 完成刷新，恢复ListView,隐藏刷新头
							lv_tpi.refreshStateFinish();
							Toast.makeText(mainActivity, "刷新成功", Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub
						System.out.println("获取数据失败" + error + msg);
						// 完成刷新，恢复ListView,隐藏刷新头
						if (isRefresh) {
							// 完成刷新，恢复ListView,隐藏刷新头
							lv_tpi.refreshStateFinish();
							Toast.makeText(mainActivity, "刷新失败", Toast.LENGTH_SHORT).show();
						}

					}
				});
	}

	/**
	 * 完成数据的处理
	 * 
	 * @param parseJsonData
	 */
	protected void processData(TpiNewsCenterData parseJsonData) {

		// 数据处理

		// 1.轮播图的数据处理
		setLunBoTu(parseJsonData);

		// 2.动态添加点
		initPoint();

		// 3.设置图片描述和点的选中效果
		setPicDescAndPointSelect(picSelectIndex);

		// 4.轮播图的自动切换
		// lunBoProcess();
		lunBoTask.startLunBo();

		// 5.新闻列表的数据处理
		setListViewNews(parseJsonData);
	}

	/**
	 * 设置新闻列表的数据
	 * 
	 * @param parseJsonData
	 */
	private void setListViewNews(TpiNewsCenterData parseJsonData) {

		listNewsData = parseJsonData.data.news;
		// 更新lv_tpi的数据
		listViewAdapter.notifyDataSetChanged();
	}

	/**
	 * 使用handler实现轮播图的自动播放
	 */
	private void lunBoProcess() {
		if (handler == null) {
			handler = new Handler();
		}
		handler.removeCallbacksAndMessages(null);
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {

				vp_lunbotu.setCurrentItem((vp_lunbotu.getCurrentItem() + 1)
						% vp_lunbotu.getAdapter().getCount());
				handler.postDelayed(this, 2000);
			}
		}, 2000);
	}

	private class LunBoTask extends Handler implements Runnable {

		private void startLunBo() {

			stopLunBo();
			postDelayed(this, 2000);
		}

		private void stopLunBo() {

			removeCallbacksAndMessages(null);
		}

		@Override
		public void run() {

			vp_lunbotu.setCurrentItem((vp_lunbotu.getCurrentItem() + 1)
					% vp_lunbotu.getAdapter().getCount());
			postDelayed(this, 2000);
		}
	}

	private void setPicDescAndPointSelect(int picSelectIndex) {

		// 设置描述信息
		tv_tpi.setText(lunBoTuData.get(picSelectIndex).title);

		// 设置选中的点
		for (int i = 0; i < lunBoTuData.size(); i++) {
			ll_tpi.getChildAt(i).setEnabled(i == picSelectIndex);
		}

	}

	private void initPoint() {

		// 清空之前绘制的点
		ll_tpi.removeAllViews();

		for (int i = 0; i < lunBoTuData.size(); i++) {

			View view = new View(mainActivity);

			view.setBackgroundResource(R.drawable.point_selector);
			view.setEnabled(false);

			// 设置点的大小
			LayoutParams params = new LayoutParams(DensityUtil.dip2px(
					mainActivity, 5), DensityUtil.dip2px(mainActivity, 5));
			// 设置点与点直接的间距
			params.leftMargin = DensityUtil.dip2px(mainActivity, 10);
			// 设置参数
			view.setLayoutParams(params);
			ll_tpi.addView(view);

		}

	}

	private void setLunBoTu(TpiNewsCenterData parseJsonData) {

		// 获取轮播图的数据，加入容器中
		lunBoTuData = parseJsonData.data.topnews;
		// System.out.println(lunBoTuData.get(2).title);
		// 更新vp_tpi的数据
		lunBotuAdapter.notifyDataSetChanged();

	}

	/*
	 * ListView的适配器
	 */
	private class ListViewAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listNewsData.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = View.inflate(mainActivity,
						R.layout.tpi_newscenter_listview_item, null);
				viewHolder = new ViewHolder();
				viewHolder.iv_listimage = (ImageView) convertView
						.findViewById(R.id.iv_listview_pic);
				viewHolder.tv_title = (TextView) convertView
						.findViewById(R.id.tv_listview_item);
				viewHolder.tv_pubdate = (TextView) convertView
						.findViewById(R.id.tv_time_listview_item);
				viewHolder.iv_comment = (ImageView) convertView
						.findViewById(R.id.iv_comment_listview_item);
				convertView.setTag(viewHolder);

			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			// 获取数据并为item设置数据
			TpiNewsCenterData_Data_news tpiNewsCenterData_Data_news = listNewsData
					.get(position);

			// 处理Url
			String listImageUrl = tpiNewsCenterData_Data_news.listimage;

			listImageUrl = MyContainer.PATH
					+ listImageUrl
							.substring(listImageUrl.lastIndexOf("zhbj") + 4);
			// 设置图片
			bitmapUtils.display(viewHolder.iv_listimage, listImageUrl);
			// 设置标题
			viewHolder.tv_title.setText(tpiNewsCenterData_Data_news.title);
			// 设置时间
			viewHolder.tv_pubdate.setText(tpiNewsCenterData_Data_news.pubdate);

			return convertView;
		}
	}

	/*
	 * ListView item的组件
	 */
	private class ViewHolder {

		ImageView iv_listimage;// http://10.0.2.2:8080/zhbj/10007/2078369924F9UO.jpg
		TextView tv_title;
		TextView tv_pubdate;
		ImageView iv_comment;
	}

	/*
	 * 轮播图的适配器
	 */
	private class LunBoTuAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub

			return lunBoTuData.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			ImageView iv_LunBoTu = new ImageView(mainActivity);
			iv_LunBoTu.setScaleType(ScaleType.FIT_XY);

			// 设置默认图片
			iv_LunBoTu.setImageResource(R.drawable.pic_item_list_default);

			TpiNewsCenterData_Data_topnews tpiNewsCenterData_Data_topnews = lunBoTuData
					.get(position);

			String topimageUrl = tpiNewsCenterData_Data_topnews.topimage;

			topimageUrl = MyContainer.PATH
					+ topimageUrl
							.substring(topimageUrl.lastIndexOf("zhbj") + 4);
			/*
			 * System.out.println(topimageUrl); System.out.println("++++++++");
			 */
			// 让iv_LunBoTu显示URL中的图片
			bitmapUtils.display(iv_LunBoTu, topimageUrl);

			// 装入容器之前设置图片的触摸事件
			iv_LunBoTu.setOnTouchListener(new OnTouchListener() {

				private float downX;
				private float downY;
				private long downTime;

				@Override
				public boolean onTouch(View v, MotionEvent event) {

					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:// 停止轮播

						downX = event.getX();
						downY = event.getY();

						downTime = System.currentTimeMillis();

						lunBoTask.stopLunBo();
						// System.out.println("按下");
						break;

					case MotionEvent.ACTION_CANCEL:// 开始轮播

						lunBoTask.startLunBo();
						// System.out.println("离开");
						break;
					case MotionEvent.ACTION_UP:// 开始轮播

						float upX = event.getX();
						float upY = event.getY();

						if (upX == downX && upY == downY) {
							long upTime = System.currentTimeMillis();
							if (upTime - downTime < 500) {

								// 处理图片的点击事件
								lunBoTuClick("被点击了");
							}
						}

						lunBoTask.startLunBo();
						// System.out.println("松开");
						break;
					default:
						break;
					}
					// 触摸事件自身消费
					return true;
				}

				private void lunBoTuClick(Object data) {
					// 处理图片的点击事件
					System.out.println(data);

				}
			});

			container.addView(iv_LunBoTu);

			return iv_LunBoTu;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView((View) object);
		}

	}

	protected TpiNewsCenterData parseJsonData(String jsonData) {

		TpiNewsCenterData tpiNewsCenterData_Data = gson.fromJson(jsonData,
				TpiNewsCenterData.class);
		return tpiNewsCenterData_Data;
	}

	private void initEvent() {

		// 为listView设置刷新完成的监听方法
		lv_tpi.SetOnRefreshDataListener(new OnRefreshDataListener() {

			@Override
			public void refreshData(int refreshing) {

				isRefresh = true;

				if (REFRESHING == refreshing) {
					// 刷新事件完成，刷新数据
					System.out.println("======================刷新数据啦啦啦啦啦啦啦啦啦");
				}

				getDataFromNet();

			}
		});

		vp_lunbotu.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {

				picSelectIndex = position;
				setPicDescAndPointSelect(picSelectIndex);

			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub

			}
		});
	}

	public View getView() {
		return root;
	}
}
