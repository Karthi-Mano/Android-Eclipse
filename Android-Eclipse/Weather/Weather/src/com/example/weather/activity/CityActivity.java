package com.example.weather.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.example.weather.R;
import com.example.weather.beans.CityBean;
import com.example.weather.beans.CityBean.RetDataBean;
import com.google.gson.Gson;

public class CityActivity extends Activity {

	protected static final String TAG = "CityActivity";

	private String[] city = { "北京","上海", "深圳", "广州", "武汉", "南京", "杭州", "天津",
			"重庆", "西安", "苏州", "徐州", "郑州", "大连" };

	private List<String> cityData = new ArrayList<String>();

	private Button btn_city;

	private EditText et_city;

	private ListView lv_city;

	private MyAdapter myAdapter;

	private String cityName;

	private List<RetDataBean> retData;

	private boolean isSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_city);
		initView();
		initData();
		initEvent();
	}

	private void initView() {

		btn_city = (Button) findViewById(R.id.btn_city);
		et_city = (EditText) findViewById(R.id.et_city);
		lv_city = (ListView) findViewById(R.id.lv_city);

		myAdapter = new MyAdapter();
		lv_city.setAdapter(myAdapter);

	}

	private void initData() {

		
		
		for (int i = 0; i < city.length; i++) {

			cityData.add(city[i]);
		}

	}

	/**
	 * 从网络获取数据
	 * 
	 * @param cityName
	 * @param city
	 */
	private void getDataFromNet(String cityName) {

		// String str = "徐州";// 徐州
		String cityName1 = null;
		try {
			cityName1 = URLEncoder.encode(cityName, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Parameters para = new Parameters();

		para.put("cityname", cityName1);
		ApiStoreSDK.execute(
				"http://apis.baidu.com/apistore/weatherservice/citylist",
				ApiStoreSDK.GET, para,

				new ApiCallBack() {

					@Override
					public void onSuccess(int status, String responseString) {
						Log.i(TAG, "onSuccess");

						if (responseString.contains("\"errNum\":-1")) {
							Toast.makeText(CityActivity.this, "输入错误", 0).show();
							return;
						}
						System.out.println(responseString);

						processBeanData(parseJson(responseString));
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
	private CityBean parseJson(String jsonData) {
		Gson gson = new Gson();
		CityBean cityBean = gson.fromJson(jsonData, CityBean.class);
		return cityBean;
	}

	protected void processBeanData(CityBean cityBean) {

		cityData.clear();
		retData = cityBean.getRetData();

		for (RetDataBean retDataBean : retData) {
			retDataBean.getDistrict_cn();
			retDataBean.getName_cn();
			String city = retDataBean.getDistrict_cn() + "    "
					+ retDataBean.getName_cn();
			cityData.add(city);
		}
		myAdapter.notifyDataSetChanged();

	}

	private void initEvent() {

		lv_city.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				

				Intent data = new Intent();
				if (isSearch) {
					data.putExtra("city", retData.get(position)
							.getDistrict_cn());
				} else {
					data.putExtra("city", cityData.get(position)
							);
				}
				setResult(1, data);
				finish();
			}
		});

		btn_city.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				String cityName = et_city.getText().toString().trim();
				if (TextUtils.isEmpty(cityName)) {
					et_city.setError("请输入要查询的城市");
				} else {
					isSearch = true;
					getDataFromNet(cityName);
				}
			}
		});

	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (cityData != null) {
				return cityData.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			if (cityData != null) {
				return cityData.get(position);
			}
			return null;
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(CityActivity.this,
						R.layout.item_city_list, null);
				holder = new ViewHolder();
				holder.tv = (TextView) convertView.findViewById(R.id.tv_city);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			String string = cityData.get(position);
			holder.tv.setText(string);

			return convertView;
		}

	}

	private class ViewHolder {
		TextView tv;
	}

}
