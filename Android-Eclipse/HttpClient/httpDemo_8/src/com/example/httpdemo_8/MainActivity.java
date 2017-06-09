package com.example.httpdemo_8;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/*=============== Gzip压缩 ===============*/
		initGzip();
		
		/*=============== post key_value ===============*/
		Map<String, String> map = new HashMap<String, String>();
		map.put("billy", "123");
		initPost_KeyValue(map);

		/*=============== post key_jsonString===============*/
		String jsonString = "{\"billy\":\"123\"}";
		initPost_JsonString(jsonString);

		/*=============== post key_File 单张图片===============*/
		File file = new File("");
		initPost_File(file);
		
		/*=============== post key_File 多张图片===============*/
		Map<String, File> fileMap = new HashMap<String, File>();
		File actimgFile = null;
		File listimgFile = null;
		fileMap.put("actimg", actimgFile);
		fileMap.put("listimg", listimgFile);
		initPost_File(fileMap);
	}

	/**
	 * post key_File 多张图片
	 * */
	private void initPost_File(final Map<String, File> fileMap) {

		findViewById(R.id.btn4).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							DefaultHttpClient httpClient = new DefaultHttpClient();
							HttpPost post = new HttpPost("http://httpbin.org/post");

							// 提交文件类型的参数
							MultipartEntity entity = new MultipartEntity();
							for (Map.Entry<String, File> info : fileMap.entrySet()) {
								File file = info.getValue();
								String key = info.getKey();
								
								ContentBody contentBody = new FileBody(file);
								entity.addPart(key, contentBody);
								
							}


							post.setEntity(entity);

							HttpResponse response = httpClient.execute(post);
							if (response.getStatusLine().getStatusCode() == 200) {
								HttpEntity resEntity = response.getEntity();
								String result = EntityUtils.toString(resEntity);
								System.out.println("result:" + result);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}).start();

			}
		});

	}
	/**
	 * ost key_File 单张图片
	 * */
	private void initPost_File(final File file) {
		findViewById(R.id.btn4).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							DefaultHttpClient httpClient = new DefaultHttpClient();
							HttpPost post = new HttpPost("http://httpbin.org/post");

							// 提交文件类型的参数
							MultipartEntity entity = new MultipartEntity();
							ContentBody contentBody = new FileBody(file);
							entity.addPart("actimg", contentBody);

							post.setEntity(entity);

							HttpResponse response = httpClient.execute(post);
							if (response.getStatusLine().getStatusCode() == 200) {
								HttpEntity resEntity = response.getEntity();
								String result = EntityUtils.toString(resEntity);
								System.out.println("result:" + result);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}).start();

			}
		});

	}
	/**
	 * post key_jsonString
	 * */
	private void initPost_JsonString(final String jsonString) {
		findViewById(R.id.btn3).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							DefaultHttpClient httpClient = new DefaultHttpClient();
							HttpPost post = new HttpPost("http://httpbin.org/post");

							// 提交JsonString类型的参数
		                    StringEntity entity = new StringEntity(jsonString);
		                    entity.setContentEncoding("UTF-8");
		                    entity.setContentType("application/json");//发送json数据需要设置contentType
		                    post.setEntity(entity);
							
							/*
							// 提交JsonString类型的参数
							post.setEntity(new StringEntity(jsonString));
							*/

							HttpResponse response = httpClient.execute(post);
							if (response.getStatusLine().getStatusCode() == 200) {
								HttpEntity resEntity = response.getEntity();
								String result = EntityUtils.toString(resEntity);
								System.out.println("result:" + result);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}).start();

			}
		});

	}
	/**
	 * post key_value
	 * */
	private void initPost_KeyValue(final Map<String, String> parmasMap) {
		findViewById(R.id.btn2).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							DefaultHttpClient httpClient = new DefaultHttpClient();
							HttpPost post = new HttpPost("http://httpbin.org/post");

							List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
							for (Map.Entry<String, String> info : parmasMap.entrySet()) {
								String name = info.getKey();
								String value = info.getValue();
								BasicNameValuePair basicNameValuePair = new BasicNameValuePair(name, value);
								parameters.add(basicNameValuePair);
							}

							UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(parameters);
							// 提交表单类型的参数
							post.setEntity(reqEntity);

							HttpResponse response = httpClient.execute(post);
							if (response.getStatusLine().getStatusCode() == 200) {
								HttpEntity resEntity = response.getEntity();
								String result = EntityUtils.toString(resEntity);
								System.out.println("result:" + result);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}).start();
			}
		});
	}
	/**
	 * Gzip压缩
	 * */
	private void initGzip() {
		findViewById(R.id.btn1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {

					@Override
					public void run() {

						try {
							boolean isGzip = false;

							// 初始化httpClient对象
							DefaultHttpClient httpClient = new DefaultHttpClient();

							// 初始化httpGe对象
							HttpGet get = new HttpGet("http://mobileif.maizuo.com/city");
							// 1.发送请求头:`Accept-Encoding:gzip`
							get.addHeader("Accept-Encoding", "gzip");

							// HttpGet get = new HttpGet("http://httpbin.org/gzip");

							// 发起请求
							HttpResponse response = httpClient.execute(get);
							if (response.getStatusLine().getStatusCode() == 200) {
								// 2. 取的响应头`Content-Encoding`,判断是否包含Content-Encoding:gzip
								Header[] headers = response.getHeaders("Content-Encoding");
								for (Header header : headers) {
									String value = header.getValue();
									if (value.equals("gzip")) {
										isGzip = true;
									}
								}

								// 3.相应的解压
								String result;
								HttpEntity entity = response.getEntity();
								if (isGzip) {// gzip解压
									InputStream in = entity.getContent();

									GZIPInputStream gzipIn = new GZIPInputStream(in);

									// inputStream-->string
									result = convertStreamToString(gzipIn);
								} else {// 标准解压

									// 打印响应结果
									result = EntityUtils.toString(entity);
								}
								System.out.println("result:" + result);
							}

						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}).start();
			}
		});

	}

	public static String convertStreamToString(InputStream is) throws IOException {
		try {
			if (is != null) {
				StringBuilder sb = new StringBuilder();
				String line;
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
					// BufferedReader reader = new BufferedReader(new InputStreamReader(is));
					while ((line = reader.readLine()) != null) {
						// sb.append(line);
						sb.append(line).append("\n");
					}
				} finally {
					is.close();
				}
				return sb.toString();
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}
}
