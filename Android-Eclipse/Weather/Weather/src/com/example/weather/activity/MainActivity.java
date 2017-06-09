package com.example.weather.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;

import com.example.weather.R;
import com.example.weather.R.id;
import com.example.weather.R.layout;
import com.example.weather.application.MyApplication;
import com.example.weather.beans.MyBean;
import com.example.weather.beans.MyBean.WeatherBean;
import com.example.weather.beans.MyBean.WeatherBean.AqiBean.CityBean;
import com.example.weather.beans.MyBean.WeatherBean.BasicBean;
import com.example.weather.beans.MyBean.WeatherBean.DailyForecastBean;
import com.example.weather.beans.MyBean.WeatherBean.DailyForecastBean.TmpBean;
import com.example.weather.beans.MyBean.WeatherBean.HourlyForecastBean;
import com.example.weather.beans.MyBean.WeatherBean.NowBean;
import com.example.weather.beans.MyBean.WeatherBean.SuggestionBean;
import com.example.weather.utils.LocationService;
import com.example.weather.utils.MyContainer;
import com.example.weather.utils.PinyinUtil;
import com.example.weather.utils.SpTools;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

public class MainActivity extends Activity {

	private String url="http://m.pm25.com/";
	protected static final String TAG = "MainActivity";
	private WebView wv_pm;
	private WebSettings wv_settings;
	private TextView tv_main_pm;
	private TextView tv_city,// 城市
			tv_release,// 发布时间
			tv_now_weather,// 天气
			tv_today_temp,// 温度
			tv_now_temp,// 当前温度
			tv_aqi,// 空气质量指数
			tv_quality,// 空气质量

			tv_today_temp_a,// 今天温度a
			tv_today_temp_b,// 今天温度b
			tv_tommorrow,// 明天
			tv_tommorrow_temp_a,// 明天温度a
			tv_tommorrow_temp_b,// 明天温度b
			tv_thirdday,// 第三天
			tv_thirdday_temp_a,// 第三天温度a
			tv_thirdday_temp_b,// 第三天温度b
			tv_fourthday,// 第四天
			tv_fourthday_temp_a,// 第四天温度a
			tv_fourthday_temp_b,// 第四天温度b
			tv_felt_air_temp,// 体感温度
			tv_humidity,// 湿度
			tv_wind, tv_uv_index,// 紫外线指数
			tv_dressing_index

			;// 穿衣指数

	private ImageView iv_now_weather,// 现在
			iv_today_weather,// 今天
			iv_tommorrow_weather,// 明天
			iv_local,// 定位
			iv_thirdday_weather;// 后天

	private TextView tv_1;

	private TextView tv_2;

	private TextView tv_3;

	private TextView tv_4;

	private String city = MyContainer.CITY;
	private String cityName = MyContainer.CITYNAME;

	private ImageView iv_city_search;

	private String citySearch;
	private PullToRefreshScrollView mPullRefreshScrollView;
	private LocationService locationService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
		initEvent();
	}

	@Override
	protected void onStart() {

		super.onStart();

		// 获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
		locationService = ((MyApplication) getApplication()).locationService;

		// 注册监听
		locationService.registerListener(mListener);

	}

	/***
	 * Stop location service
	 */
	@Override
	protected void onStop() {

		locationService.unregisterListener(mListener); // 注销掉监听
		locationService.stop(); // 停止定位服务

		super.onStop();
	}

	/*****
	 * @see copy funtion to you project
	 *      定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
	 * 
	 */
	private BDLocationListener mListener = new BDLocationListener() {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			if (null != location
					&& location.getLocType() != BDLocation.TypeServerError) {

				// 截取字符串
				String localCity = location.getCity();

				String substring = localCity.substring(0,
						localCity.indexOf("市"));

				cityName = substring;
			}
		}

	};

	
	private void initView() {

		mPullRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);
		mPullRefreshScrollView.setMode(Mode.PULL_FROM_START);
		tv_city = (TextView) findViewById(R.id.tv_city);
		tv_release = (TextView) findViewById(R.id.tv_release);
		tv_now_weather = (TextView) findViewById(R.id.tv_now_weather);
		tv_today_temp = (TextView) findViewById(R.id.tv_today_temp);
		tv_now_temp = (TextView) findViewById(R.id.tv_now_temp);
		tv_aqi = (TextView) findViewById(R.id.tv_aqi);
		tv_quality = (TextView) findViewById(R.id.tv_quality);
		tv_main_pm = (TextView) findViewById(R.id.tv_main_pm);

		tv_today_temp_a = (TextView) findViewById(R.id.tv_today_temp_a);
		tv_today_temp_b = (TextView) findViewById(R.id.tv_today_temp_b);
		tv_tommorrow = (TextView) findViewById(R.id.tv_tommorrow);
		tv_tommorrow_temp_a = (TextView) findViewById(R.id.tv_tommorrow_temp_a);
		tv_tommorrow_temp_b = (TextView) findViewById(R.id.tv_tommorrow_temp_b);
		tv_thirdday = (TextView) findViewById(R.id.tv_thirdday);
		tv_thirdday_temp_a = (TextView) findViewById(R.id.tv_thirdday_temp_a);
		tv_thirdday_temp_b = (TextView) findViewById(R.id.tv_thirdday_temp_b);
		tv_1 = (TextView) findViewById(R.id.tv_1);
		tv_2 = (TextView) findViewById(R.id.tv_2);
		tv_3 = (TextView) findViewById(R.id.tv_3);
		tv_4 = (TextView) findViewById(R.id.tv_4);

		tv_felt_air_temp = (TextView) findViewById(R.id.tv_felt_air_temp);

		tv_humidity = (TextView) findViewById(R.id.tv_humidity);
		tv_wind = (TextView) findViewById(R.id.tv_wind);
		tv_uv_index = (TextView) findViewById(R.id.tv_uv_index);
		tv_dressing_index = (TextView) findViewById(R.id.tv_dressing_index);

		iv_now_weather = (ImageView) findViewById(R.id.iv_now_weather);

		iv_today_weather = (ImageView) findViewById(R.id.iv_today_weather);
		iv_tommorrow_weather = (ImageView) findViewById(R.id.iv_tommorrow_weather);
		iv_thirdday_weather = (ImageView) findViewById(R.id.iv_thirdday_weather);
		iv_city_search = (ImageView) findViewById(R.id.iv_city_search);

		iv_local = (ImageView) findViewById(R.id.iv_local);
	}

	private void initData() {
		
		
		wv_pm = new WebView(MainActivity.this);
		// 控制WebView的显示设置
		wv_settings = wv_pm.getSettings();

		// 双击放大缩小
		wv_settings.setUseWideViewPort(true);


		String cityStr = SpTools.getString(MainActivity.this, "city", "");
		System.out.println("cityStr   " + cityStr);
		if (!TextUtils.isEmpty(cityStr)) {
			cityName = cityStr;
		}

		// 1.从sp取数据
		getDataFromLocal();
		// 2.从网络取数据
		getDataFromNet(city, cityName);
	}

	/**
	 * 从本地sp获取数据
	 */
	private void getDataFromLocal() {

		String jsonCache = SpTools.getString(MainActivity.this,
				MyContainer.URL, "");
		if (!TextUtils.isEmpty(jsonCache)) {
			System.out.println("从本地sp获取数据");

			processBeanData(parseJson(jsonCache));
		}

	}

	/**
	 * 从网络获取数据
	 * 
	 * @param cityName
	 * @param city
	 */
	private void getDataFromNet(String city, String cityName) {

		System.out.println("city   " + city);
		System.out.println("cityName   " + cityName);
		Parameters para = new Parameters();

		para.put(city, cityName);
		ApiStoreSDK.execute(MyContainer.URL, ApiStoreSDK.GET, para,
				new ApiCallBack() {

					@Override
					public void onSuccess(int status, String responseString) {
						Log.i(TAG, "onSuccess");
						String oldChar = "HeWeather data service 3.0";
						String newChar = "weather";
						String jsonData = responseString.replace(oldChar,
								newChar);
						System.out.println(jsonData);

						if (jsonData.contains("unknown city"))
							return;

						// 保存json到sp
						SpTools.setString(MainActivity.this, MyContainer.URL,
								jsonData);
						System.out.println("从网络获取数据");

						processBeanData(parseJson(jsonData));
					}

					@Override
					public void onComplete() {
						Log.i("TAG", "onComplete");
					}

					@Override
					public void onError(int status, String responseString,
							Exception e) {
						Log.i(TAG, "onError, status: " + status);
						Log.i(TAG,
								"errMsg: " + (e == null ? "" : e.getMessage()));

					}

				});

	}

	/**
	 * 解析json数据
	 * 
	 * @param jsonData
	 *            从网络获取的json数据
	 * @return 返回接完的bean
	 */
	private MyBean parseJson(String jsonData) {
		Gson gson = new Gson();
		MyBean myBean = gson.fromJson(jsonData, MyBean.class);
		return myBean;
	}

	protected void processBeanData(MyBean myBean) {

		try {

			WeatherBean weatherBean = myBean.getWeather().get(0);

			List<WeatherBean> weather = myBean.getWeather();
			for (WeatherBean w : weather) {
				String netName = w.getBasic().getCity();
				if (netName.equals(citySearch)) {
					weatherBean = w;
				}

			}

			BasicBean basic = weatherBean.getBasic();// 城市

			NowBean now = weatherBean.getNow();// 实况天气

			TmpBean tmp = weatherBean.getDaily_forecast().get(0).getTmp();// max--min
			List<DailyForecastBean> daily_forecast = weatherBean
					.getDaily_forecast();// 7日天气

			/*
			 * System.out.println("==================");
			 * System.out.println(weatherBean);
			 * System.out.println(myBean.getWeather().get(0));
			 * System.out.println(weatherBean.getAqi());
			 * System.out.println("==================");
			 */
			String pm25 = "空";
			String qlty = "空";
			if (weatherBean.getAqi() == null) {// false

			} else {
				CityBean city = weatherBean.getAqi().getCity();// pm
				pm25 = city.getPm25();
				qlty = city.getQlty();
			}

			SuggestionBean suggestion = weatherBean.getSuggestion();

			HourlyForecastBean hourlyForecastBean = weatherBean
					.getHourly_forecast().get(0);

			iv_now_weather.setImageResource(getResources().getIdentifier(
					"w" + now.getCond().getCode(), "drawable",
					"com.example.weather"));

			iv_today_weather.setImageResource(getResources().getIdentifier(
					"w" + daily_forecast.get(0).getCond().getCode_d(),
					"drawable", "com.example.weather"));
			iv_tommorrow_weather.setImageResource(getResources().getIdentifier(
					"w" + daily_forecast.get(1).getCond().getCode_d(),
					"drawable", "com.example.weather"));
			iv_thirdday_weather.setImageResource(getResources().getIdentifier(
					"w" + daily_forecast.get(2).getCond().getCode_d(),
					"drawable", "com.example.weather"));

			tv_city.setText(basic.getCity());// ,// 城市
			tv_release.setText(basic.getUpdate().getLoc());// // 发布时间
			tv_now_weather.setText(now.getCond().getTxt());// ,// 天气
			tv_today_temp.setText("↑ " + tmp.getMax() + "°" + "     " + "↓ "
					+ tmp.getMin() + "°");// ,// 温度
			tv_now_temp.setText(now.getTmp() + "°");// ,// 当前温度
			tv_aqi.setText(pm25);// ,// 空气质量指数
			tv_quality.setText(qlty);// ,// 空气质量

			tv_1.setText(hourlyForecastBean.getHum() + "%");// 相对湿度
			tv_2.setText(hourlyForecastBean.getPop() + "%");// 降雨
			tv_3.setText(hourlyForecastBean.getTmp() + "°");// 体感
			tv_4.setText(hourlyForecastBean.getWind().getDir()
					+ hourlyForecastBean.getWind().getSc());// 风力

			DailyForecastBean t0 = daily_forecast.get(0);
			DailyForecastBean t1 = daily_forecast.get(1);
			DailyForecastBean t2 = daily_forecast.get(2);
			DailyForecastBean t3 = daily_forecast.get(3);

			tv_today_temp_a.setText(t0.getTmp().getMax());// ,// 今天温度a
			tv_today_temp_b.setText(t0.getTmp().getMin());// ,// 今天温度b
			tv_tommorrow.setText("明天");// ,// 明天
			tv_tommorrow_temp_a.setText(t1.getTmp().getMax());// ,// 明天温度a
			tv_tommorrow_temp_b.setText(t1.getTmp().getMin());// ,// 明天温度b
			tv_thirdday.setText("后天");// ,// 第三天
			tv_thirdday_temp_a.setText(t2.getTmp().getMax());// ,// 第三天温度a
			tv_thirdday_temp_b.setText(t2.getTmp().getMin());// ,// 第三天温度b

			tv_felt_air_temp.setText(now.getFl() + "°");// 体感温度
			tv_humidity.setText(now.getHum() + "%");// ,// 湿度
			tv_wind.setText(now.getWind().getDir() + now.getWind().getSc());// 风力
			tv_uv_index.setText(suggestion.getUv().getBrf());// ,// 紫外线指数
			tv_dressing_index.setText(suggestion.getDrsg().getBrf());// 穿衣指数

		} catch (Exception e) {
			Toast.makeText(MainActivity.this, "输入的城市异常，请重新输入", 0).show();
		}
	}

	private void initEvent() {

		tv_main_pm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 设置webView加载的URL
				wv_pm.loadUrl(url);
			}
		});
		

		iv_city_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(MainActivity.this,
						CityActivity.class);
				startActivityForResult(intent, 0);
			}
		});
		mPullRefreshScrollView
				.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ScrollView> refreshView) {
						System.out.println("refreshView");
						new MyTask().execute();
					}
				});

		iv_local.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// 播放动画
				startAnimation(iv_local);

				locationService.start();// 定位SDK,开始定位服务

			}
		});

	}

	private void startAnimation(ImageView iv) {

		RotateAnimation ra = new RotateAnimation(0, 720,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		ra.setDuration(2000);
		ra.setRepeatCount(2);
		ra.setRepeatMode(Animation.REVERSE);
		iv.startAnimation(ra);

		// 动画的监听
		ra.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				locationService.stop();
				String cityName1 = PinyinUtil.getPinyin(cityName);
				getDataFromNet(city, cityName1);

			}
		});
	}

	private class MyTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			System.out.println("完成");
			mPullRefreshScrollView.onRefreshComplete();

			initData();
			super.onPostExecute(result);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (data == null) {
			System.out.println("data  " + data + "");
			return;
		}

		if (requestCode == 0) {
			citySearch = data.getStringExtra("city");
			String cityName = PinyinUtil.getPinyin(citySearch);
			System.out.println("cityName  " + cityName);
			getDataFromNet(city, cityName);

			// 记录上次查询的城市，下次开启app查询记录的城市
			SpTools.setString(MainActivity.this, "city", cityName);

		}

		super.onActivityResult(requestCode, resultCode, data);
	}

}
