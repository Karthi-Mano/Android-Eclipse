package com.wjz.smart1.tpinewscenter;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap.Config;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
import com.wjz.smart1.domain.TpiNewsCenterData.TpiNewsCenterData_Data.TpiNewsCenterData_Data_topnews;
import com.wjz.smart1.utils.MyContainer;
import com.wjz.smart1.utils.SpTools;

/**
 * @author Wjz
 */
public class TpiNewsCenter {

	private MainActivity mainActivity;
	private View root;
	private ViewTagData viewTagData;
	private ViewPager vp_tpi;
	private TextView tv_tpi_;
	private LinearLayout ll_tpi;
	private ListView lv_tpi;
	private Gson gson;

	// 轮播图的数据容器
	private List<TpiNewsCenterData_Data_topnews> lunBoTuData = new ArrayList<TpiNewsCenterData_Data_topnews>();
	private LunBoTuAdapter lunBotuAdapter;
	private BitmapUtils bitmapUtils;

	public TpiNewsCenter(MainActivity mainActivity, ViewTagData viewTagData) {
		this.mainActivity = mainActivity;
		this.viewTagData = viewTagData;
		// 初始化bitmapUtils
		bitmapUtils = new BitmapUtils(mainActivity);
		// 设置bitmapUtils的属性
		bitmapUtils.configDefaultBitmapConfig(Config.ARGB_4444);

		gson = new Gson();

		// 初始化轮播图的适配器
		lunBotuAdapter = new LunBoTuAdapter();
		initView();
		initData();
		initEvent();
	}

	private void initView() {

		root = View
				.inflate(mainActivity, R.layout.tpi_newscenter_content, null);

		vp_tpi = (ViewPager) root.findViewById(R.id.vp_tpi_newscenter);
		tv_tpi_ = (TextView) root.findViewById(R.id.tv_tpi_newscenter);
		ll_tpi = (LinearLayout) root.findViewById(R.id.ll_tpi_newscenter);
		lv_tpi = (ListView) root.findViewById(R.id.lv_tpi_newscenter);

	}

	private void initData() {

		// 给vp_tpi设置适配器
		vp_tpi.setAdapter(lunBotuAdapter);

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

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub
						System.out.println("获取数据失败" + error + msg);

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

		// 1.轮播图的处理
		setLunBoTu(parseJsonData);

	}

	private void setLunBoTu(TpiNewsCenterData parseJsonData) {

		// 获取轮播图的数据，加入容器中
		lunBoTuData = parseJsonData.data.topnews;
		// System.out.println(lunBoTuData.get(2).title);
		// 更新vp_tpi的数据
		lunBotuAdapter.notifyDataSetChanged();

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
		// TODO Auto-generated method stub

	}

	public View getView() {
		return root;
	}
}
