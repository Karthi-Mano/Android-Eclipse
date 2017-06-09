package com.wjz.smart1.newscenterpages;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import android.graphics.Bitmap.Config;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.wjz.smart1.domain.PhotosData;
import com.wjz.smart1.domain.PhotosData.PhotosData_Data.News;
import com.wjz.smart1.domain.PhotosData_2;
import com.wjz.smart1.utils.BitMapCacheUtils;
import com.wjz.smart1.utils.MyContainer;
import com.wjz.smart1.utils.SpTools;

public class PhotosBaseNewsCenterPage extends BaseNewsCenterPage {

	private ListView lv_photos;
	private GridView gv_photos;
	private List<News> newsPhotosList = new ArrayList<PhotosData.PhotosData_Data.News>();
	private MyAdapter myAdapter;
	private BitmapUtils bitmapUtils;
	private boolean isShowList = true;
	private BitMapCacheUtils bitMapCacheUtils;

	public PhotosBaseNewsCenterPage(MainActivity mainActivity) {
		super(mainActivity);

		bitmapUtils = new BitmapUtils(mainActivity);
		bitmapUtils.configDefaultBitmapConfig(Config.ARGB_4444);
		bitMapCacheUtils = new BitMapCacheUtils(mainActivity);
		
		
	}

	@Override
	public View initView() {

		View view = View
				.inflate(mainActivity, R.layout.newscenter_photos, null);
		lv_photos = (ListView) view.findViewById(R.id.lv_newscenter_photos);
		gv_photos = (GridView) view.findViewById(R.id.gv_newscenter_photos);

		return view;
	}

	public void switchListViewOrGridView(ImageButton listorgrid) {
		if (isShowList) {
			// 按钮的背景设置成list
			listorgrid.setImageResource(R.drawable.icon_pic_list_type);
			// 显示GridView
			lv_photos.setVisibility(View.GONE);
			// 隐藏listView
			gv_photos.setVisibility(View.VISIBLE);
		} else {
			// 按钮的背景设置成grid
			listorgrid.setImageResource(R.drawable.icon_pic_grid_type);
			// 显示GridView
			lv_photos.setVisibility(View.VISIBLE);
			// 隐藏listView
			gv_photos.setVisibility(View.GONE);
		}
		isShowList = !isShowList;
	}

	@Override
	public void initData() {
		if (myAdapter == null) {
			myAdapter = new MyAdapter();
			// 设置是适配器
			lv_photos.setAdapter(myAdapter);
			gv_photos.setAdapter(myAdapter);
		}
		if (isShowList) {
			lv_photos.setVisibility(View.VISIBLE);
			gv_photos.setVisibility(View.GONE);
		} else {
			lv_photos.setVisibility(View.GONE);
			gv_photos.setVisibility(View.VISIBLE);
		}

		// 1.从本地区数据（本地缓存）
		String photosJsonData = SpTools.getString(mainActivity,
				MyContainer.PHOTOSURL, "");
		if (TextUtils.isEmpty(photosJsonData)) {
			// 解析json数据
			PhotosData photosData = parsePhotosJson(photosJsonData);

			// 处理组图数据
			processPhotosData(photosData);

		}

		// 2.从网络去数据
		getDataFromNet();

		super.initData();
	}

	private void getDataFromNet() {

		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, MyContainer.PHOTOSURL,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {

						// 请求数据成功
						// 获取组图的json数据
						String jsonData = responseInfo.result;

						// 本地缓存
						SpTools.setString(mainActivity, MyContainer.PHOTOSURL,
								jsonData);

						// 解析json数据
						PhotosData photosData = parsePhotosJson(jsonData);

						// 处理组图数据
						processPhotosData(photosData);

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub

					}
				});

	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {

			return newsPhotosList.size();
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

			// 自定义View 显示
			ViewHolder viewHolder = null;

			// 判断是否存在view缓存
			if (convertView == null) {
				// 没有界面缓存
				convertView = View.inflate(mainActivity,
						R.layout.photoslist_item, null);
				viewHolder = new ViewHolder();

				viewHolder.iv_pic = (ImageView) convertView
						.findViewById(R.id.iv_photos_list_item_pic);
				viewHolder.tv_desc = (TextView) convertView
						.findViewById(R.id.tv_photos_list_item_desc);
				convertView.setTag(viewHolder);

			} else {
				// 有界面缓存
				viewHolder = (ViewHolder) convertView.getTag();
			}

			// 组件赋值
			// 取当前数据
			News news = newsPhotosList.get(position);
			// 设置名字
			viewHolder.tv_desc.setText(news.title);

			String photosUrl = news.listimage;
			photosUrl = MyContainer.PATH
					+ photosUrl.substring(photosUrl.lastIndexOf("zhbj") + 4);

			//System.out.println("photosUrl:   "+photosUrl);
			
			// 设置图片
			bitMapCacheUtils.display(viewHolder.iv_pic, photosUrl);
			
			
			return convertView;
		}
	}

	/**
	 * 组合控件
	 * 
	 * @author Wjz
	 * 
	 */
	private class ViewHolder {
		ImageView iv_pic;
		TextView tv_desc;
	}

	/**
	 * 处理组图数据
	 * 
	 * @param photosData
	 */
	protected void processPhotosData(PhotosData photosData) {

		newsPhotosList = photosData.data.news;
		myAdapter.notifyDataSetChanged();
	}

	/**
	 * 解析json数据
	 * 
	 * @param jsonData
	 *            photos的json数据
	 */
	protected PhotosData parsePhotosJson(String jsonData) {
		Gson gson = new Gson();
		/*
		 * PhotosData_2 photosData_2 = gson.fromJson(jsonData,
		 * PhotosData_2.class); int id =
		 * photosData_2.getData().getNews().get(0).getId();
		 * System.out.println(id);
		 */

		PhotosData photosData = gson.fromJson(jsonData, PhotosData.class);
		/*
		 * int id = photosData.data.news.get(0).id; System.out.println(id);
		 */
		return photosData;
	}
}