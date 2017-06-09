package com.example.weather.application;

import android.app.Application;

import com.baidu.apistore.sdk.ApiStoreSDK;
import com.example.weather.utils.LocationService;
import com.example.weather.utils.MyContainer;

// 请在AndroidManifest.xml中application标签下android:name中指定该类
public class MyApplication extends Application {
	public LocationService locationService;
    @Override
    public void onCreate() {
        super.onCreate();
        
        locationService = new LocationService(getApplicationContext());
        ApiStoreSDK.init(this, MyContainer.MYAPI);
    }
}
