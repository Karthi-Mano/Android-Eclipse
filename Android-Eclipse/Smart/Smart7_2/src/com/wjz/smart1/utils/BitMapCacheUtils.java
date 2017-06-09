package com.wjz.smart1.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

import com.wjz.smart1.activity.MainActivity;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

public class BitMapCacheUtils {

	// 动态获取jvm的内存
	private int maxSize = (int) (Runtime.getRuntime().freeMemory());
	// 图片缓存的容器
	private LruCache<String, Bitmap> memCache = new LruCache<String, Bitmap>(
			maxSize) {

		@Override
		protected int sizeOf(String key, Bitmap value) {
			// 计算图片的大小
			return value.getByteCount();
		}

	};

	private MainActivity mainActivity;
	private File cacheDir;
	private ExecutorService fixedThreadPool;

	// 保留最后一次访问的url的信息,获取最新的数据，防止加载错乱
	// HashMap：关键字相同，值只做覆盖
	// 以当前图片对象为key，当前图片url为value
	private Map<ImageView, String> urlImageViewDatas = new HashMap<ImageView, String>();

	public BitMapCacheUtils(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
		// 获取当前app的cache目录
		cacheDir = mainActivity.getCacheDir();
		fixedThreadPool = Executors.newFixedThreadPool(6);
	}

	public void display(ImageView iv, String ivUrl) {
		// 1.先从内存取

		Bitmap bitmap = memCache.get(ivUrl);
		if (bitmap != null) {
			System.out.println("从内存获取数据");
			// 缓存中有图片
			iv.setImageBitmap(bitmap);
			return;
		}
		// 2.再从本地文件中,app(data/data/包名/cache)
		bitmap = getCacheFile(ivUrl);
		if (bitmap != null) {
			System.out.println("从文件获取数据");
			// 本地文件中有图片
			iv.setImageBitmap(bitmap);
			return;
		}
		// 3.从网络去
		// 保留最后一次访问的url
		urlImageViewDatas.put(iv, ivUrl);
		getBitmapFromNet(iv, ivUrl);
	}

	private void getBitmapFromNet(ImageView iv, String ivUrl) {
		// new Thread(new DownLoadUrl(iv, ivUrl)).start();
		// 线程池
		fixedThreadPool.submit(new DownLoadUrl(iv, ivUrl));
	}

	private class DownLoadUrl implements Runnable {

		private ImageView iv;
		private String ivUrl;

		public DownLoadUrl(ImageView iv, String ivUrl) {
			this.iv = iv;
			this.ivUrl = ivUrl;
		}

		@Override
		public void run() {
			// 访问网络

			try {
				URL url = new URL(ivUrl);
				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();
				con.setConnectTimeout(6000);// 设置超时时间
				con.setRequestMethod("GET");
				int code = con.getResponseCode();
				if (code == 200) {
					// 请求成功
					InputStream is = con.getInputStream();
					final Bitmap bitmap = BitmapFactory.decodeStream(is);
					// 1.往内存中添加
					memCache.put(ivUrl, bitmap);
					// 2.往本地文件中添加
					saveBitmapToCacheFile(bitmap, ivUrl);
					// 3.显示数据
					mainActivity.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							System.out.println("从网络获取数据");
							// 显示图片
							// 判断url是不是最新的
							// 是最新的 自己的数据
							if (ivUrl.equals(urlImageViewDatas.get(iv))) {
								//自己的数据
								iv.setImageBitmap(bitmap);
								System.out.println("自己的数据");
							}else{
								
								System.out.println("不是自己的数据");
							}
							
						}
					});
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * 保存bitmap到cache目录的文件中
	 * 
	 * @param bitmap
	 * @param ivUrl
	 */
	public void saveBitmapToCacheFile(Bitmap bitmap, String ivUrl) {
		File file = new File(cacheDir, Md5Utils.md5(ivUrl));

		try {
			bitmap.compress(CompressFormat.PNG, 100, new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param ivUrl
	 *            当作缓存图片的名字
	 * @return
	 */
	public Bitmap getCacheFile(String ivUrl) {
		// 把ivUrl转换MD5值，在吧md5 作为文件名
		File file = new File(cacheDir, Md5Utils.md5(ivUrl));

		if (file != null && file.exists()) {
			// 文件存在
			// 把文件转换成bitmap
			Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

			// 再往内存写
			memCache.put(ivUrl, bitmap);
			return bitmap;
		} else {
			// 不存在
			return null;
		}
	}
}
