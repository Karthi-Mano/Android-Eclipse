package com.itheima13.gpsdemo;

import java.util.List;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView tv_location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//定位信息
		
		tv_location = (TextView) findViewById(R.id.tv_location);
		
		//定位api
		LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		//获取定位的提供方式
		List<String> allProviders = lm.getAllProviders();
		for (String prov : allProviders) {
			System.out.println(prov);
		}
		//provider 定位方式
		// 0 时间和空间   如果位置变化 就自动监听
		lm.requestLocationUpdates("gps", 0, 0, new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			// location监听回调结果
			@Override
			public void onLocationChanged(Location location) {
				// 位置发生变化 监听
				float accuracy = location.getAccuracy();//精确度
				double latitude = location.getLatitude();//纬度
				double longitude = location.getLongitude();//经度
				double altitude = location.getAltitude();//海拔
				
				String mess = "accuracy:" + accuracy + "\n" + "latitude:" + latitude + "\n" +"longitude:" + 
				longitude + "\n" + "altitude:" + altitude + "\n";
				tv_location.setText(mess);
				
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
