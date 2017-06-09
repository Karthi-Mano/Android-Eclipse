package com.itheima.asynctaskversiondiff_8;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		findViewById(R.id.btn1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new MyTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
				new MyTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
				new MyTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
				new MyTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
				new MyTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);

				new MyTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
				new MyTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
				new MyTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
				new MyTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
				new MyTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);

				new MyTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
				new MyTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
				new MyTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
				new MyTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
				new MyTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);

			}
		});

		findViewById(R.id.btn2).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MainActivity.this.doAsync(new IDataCallBack<String>() {

					@Override
					public void onTaskBefore() {

					}

					@Override
					public String onTasking(Void... params) {
						String result = "";
						try {
							DefaultHttpClient httpClient = new DefaultHttpClient();
							HttpGet get = new HttpGet("http://www.baidu.com");
							HttpResponse response = httpClient.execute(get);
							if (response.getStatusLine().getStatusCode() == 200) {
								result = EntityUtils.toString(response.getEntity());
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return result;
					}

					@Override
					public void onTaskAfter(String result) {
						System.out.println(result);
					}
				});
			}
		});

	}

	class MyTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			SystemClock.sleep(1000);
			System.out.println("===doInBackground===");
			return null;
		}

	}
}
