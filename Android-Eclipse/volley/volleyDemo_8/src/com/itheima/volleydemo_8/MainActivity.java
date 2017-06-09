package com.itheima.volleydemo_8;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**

 */
public class MainActivity extends Activity {

	private ImageView			mIv;
	private NetworkImageView	mNiv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mIv = (ImageView) findViewById(R.id.iv);

		mNiv = (NetworkImageView) findViewById(R.id.niv);

		initStringRequest();
		initJsonObjectRequest();
		initJsonArrayRequest();
		initImageRequest();
		// initNetWorkImageView();
		initImageLoader();
		initGsonRequest();

		DefaultHttpClient httpClient = new DefaultHttpClient();
	}

	private void initGsonRequest() {
		findViewById(R.id.btn6).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String url = "http://192.168.1.100:8080/json/ip.json";
				GsonRequest<Ip> gsonRequest = new GsonRequest<Ip>(Request.Method.GET, url,
						new Response.ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError error) {
								System.out.println("error:" + error.getMessage());

							}
						}, new Response.Listener<Ip>() {

							@Override
							public void onResponse(Ip ip) {
								System.out.println(ip.origin);
							}
						}, Ip.class);

				RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
				queue.add(gsonRequest);
				/*				//网络请求的取消
								gsonRequest.cancel();//单个请求的取消
								//请求队列设置tag
								gsonRequest.setTag("TAG1");
								
								
								//多个请求的取消
								RequestQueue requestQueue = VolleyTools.getInstance(MainActivity.this).getQueue();
								requestQueue.cancelAll(null);//取消队列里面所有的请求
								requestQueue.cancelAll("TAG1");//取消指定请求
				*/
			}
		});
	}

	/**
	 * ImageLoader使用
	 */
	private void initImageLoader() {
		findViewById(R.id.btn5).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/*RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
				ImageCache imageCache = new MyImageCache();
				ImageLoader imageLoader = new ImageLoader(queue, imageCache);*/
				ImageLoader imageLoader = VolleyTools.getInstance(MainActivity.this).getImageLoader();
				String requestUrl = "http://192.168.1.100:8080/img/a6efce1b9d16fdfaa48071e9b68f8c5495ee7bcc.jpg";
				imageLoader.get(requestUrl, new ImageListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						System.out.println("error:" + error.getMessage());

					}

					@Override
					public void onResponse(ImageContainer response, boolean isImmediate) {
						Bitmap bitmap = response.getBitmap();
						mIv.setImageBitmap(bitmap);

					}
				}, 200, 100);

			}
		});
	}

	/**
	 * NetWorkImageView的学习
	 */
	private void initNetWorkImageView() {
		mNiv.setDefaultImageResId(R.drawable.ic_launcher);// 默认图片
		mNiv.setErrorImageResId(R.drawable.itheima_logo);// 出错时候显示的图片
		String url = "http://192.168.1.100:8080/img/a6efce1b9d16fdfaa48071e9b68f8c5495ee7bcc.jpg";
		RequestQueue queue = Volley.newRequestQueue(this);
		ImageCache imageCache = new MyImageCache();
		// ImageLoader imageLoader = new ImageLoader(请求队列, 自定义缓存);
		ImageLoader imageLoader = new ImageLoader(queue, imageCache);
		mNiv.setImageUrl(url, imageLoader);
	}

	/**
	 * ImageRequest的学习
	 */
	private void initImageRequest() {
		findViewById(R.id.btn4).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String url = "http://192.168.1.100:8080/img/a6efce1b9d16fdfaa48071e9b68f8c5495ee7bcc.jpg";
				ImageRequest imageRequest = new ImageRequest(url, new Listener<Bitmap>() {

					@SuppressLint("NewApi") @Override
					public void onResponse(Bitmap bitmap) {
						mIv.setImageBitmap(bitmap);
						// 07-22 01:59:00.240: I/System.out(3991): 图片大小:1050420
						// 07-22 01:59:43.508: I/System.out(4132): 图片大小:15800

						System.out.println("图片大小:" + bitmap.getByteCount());
					}
				}, 100, 50, Bitmap.Config.ARGB_4444, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						System.out.println("error:" + error.getMessage());
					}
				});
				RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
				queue.add(imageRequest);
			}
		});
	}

	/**
	 * JsonArrayRequest的学习
	 */
	private void initJsonArrayRequest() {
		findViewById(R.id.btn3).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 1.创建请求对象
				String url = "http://192.168.1.100:8080/json/jsonArray.json";
				JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {
						System.out.println("success: " + response.length());

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						System.out.println("error:" + error.getMessage());
					}
				});
				// 2.创建队列
				RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
				// 3.发起请求
				queue.add(jsonArrayRequest);

			}
		});

	}

	/**
	 * JsonObjectRequest的学习
	 */
	private void initJsonObjectRequest() {
		findViewById(R.id.btn2).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 1.创建jsonObjectRequest对象
				String url = "http://192.168.1.100:8080/json/ip.json";
				JSONObject jsonRequest = null;// 请求参数可以用jsonObject的方式发送
				// jsonString-->JSONObject
				// JSONObject jsonObject = new JSONObject(jsonString);
				JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, jsonRequest,
						new Listener<JSONObject>() {

							@Override
							public void onResponse(JSONObject response) {
								String ip = response.optString("origin");
								System.out.println("success" + ip);

							}
						}, new Response.ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError error) {
								System.out.println("error:" + error.getMessage());
							}
						});
				// 2.创建请求队列
				RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
				// 3.发起请求
				queue.add(jsonObjectRequest);
			}
		});
	}

	/**
	 * StringRequest的学习
	 */
	private void initStringRequest() {
		findViewById(R.id.btn1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 1. 创建StringRequest对象
				String url = "http://www.baidu.com";
				StringRequest stringRequest = new StringRequest(url, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						System.out.println("success: " + response);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						System.out.println("error:" + error.getMessage());
					}
				});
				// 2.创建requestQueue
				RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
				// 3.发起网络请求-->把request放到requestqueue中
				requestQueue.add(stringRequest);
			}
		});
	}

	@Override
	protected void onDestroy() {
/*
		StringRequest req1 = null;
		StringRequest req2 = null;
		StringRequest req3 = null;
		StringRequest req4 = null;
		StringRequest req5 = null;

		req1.setTag(this.getClass().getSimpleName());
		req2.setTag(this.getClass().getSimpleName());
		req3.setTag(this.getClass().getSimpleName());
		req4.setTag(this.getClass().getSimpleName());
		req5.setTag(this.getClass().getSimpleName());

		// 取消对应activity里面所有的请求
		RequestQueue queue = VolleyTools.getInstance(MainActivity.this).getQueue();
		queue.cancelAll(this.getClass().getSimpleName());// MainActivity
*/		super.onDestroy();
	}
}
