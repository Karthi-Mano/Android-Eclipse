package com.itheima.googleplay_13.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itheima.googleplay_13.bean.AppInfoBean;
import com.itheima.googleplay_13.conf.Constants.URlS;
import com.itheima.googleplay_13.factory.ThreadPoolExecutorProxyFactory;
import com.itheima.googleplay_13.utils.CommonUtils;
import com.itheima.googleplay_13.utils.FileUtils;
import com.itheima.googleplay_13.utils.IOUtils;
import com.itheima.googleplay_13.utils.UIUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-25 上午8:58:15
 * @描述	     下载模块,负责下载相关的逻辑
 * @描述	   一个应用下载管理器只需要有一个即可
 * @描述	   1.需要时刻记录当前的状态
 * @描述	   2.根据AppInfoBean提供对应状态(DownLoadInfo)
 *
 * @版本       $Rev: 67 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-25 17:00:04 +0800 (星期日, 25 十月 2015) $
 * @更新描述    TODO
 */
public class DownLoadManager {
	private static DownLoadManager		instance;
	public static final int				STATE_UNDOWNLOAD		= 0;									// 未下载
	public static final int				STATE_DOWNLOADING		= 1;									// 下载中
	public static final int				STATE_PAUSEDOWNLOAD		= 2;									// 暂停下载
	public static final int				STATE_WAITINGDOWNLOAD	= 3;									// 等待下载
	public static final int				STATE_DOWNLOADFAILED	= 4;									// 下载失败
	public static final int				STATE_DOWNLOADED		= 5;									// 下载完成
	public static final int				STATE_INSTALLED			= 6;									// 已安装
	/**
	 * 保存DownLoadManager中的下载任务(downLoadInfo)
	 */
	private Map<String, DownLoadInfo>	mCacheDownLoadInfoMap	= new HashMap<String, DownLoadInfo>();

	private DownLoadManager() {

	}

	public static DownLoadManager getInstance() {
		if (instance == null) {
			synchronized (DownLoadManager.class) {
				if (instance == null) {
					instance = new DownLoadManager();
				}

			}
		}
		return instance;
	}

	/**
	 * @des 异步开始下载数据
	 * @call 用户点击了下载按钮的时候被调用
	 */
	public void downLoad(DownLoadInfo downLoadInfo) {
		mCacheDownLoadInfoMap.put(downLoadInfo.packageName, downLoadInfo);

		/*############### 当前状态: 未下载###############*/
		downLoadInfo.curState = STATE_UNDOWNLOAD;
		// downloadInfo改变的时候,进行通知(发布消息)
		notifyObservers(downLoadInfo);
		/*#######################################*/

		/*############### 当前状态: 等待状态###############*/
		downLoadInfo.curState = STATE_WAITINGDOWNLOAD;
		// downloadInfo改变的时候,进行通知(发布消息)
		notifyObservers(downLoadInfo);
		/*#######################################*/

		/**
		 * 预先把状态置为等待状态
		 * 1.假如正在执行的下载任务<3个,需要执行的"下载任务",应该会放到"工作线程"中--->把状态变为"下载中"的状态
		 * 2.假如正在执行的下载任务>=3个,需要执行的"下载任务",应该会放到"任务队列"中--->此时的状态还是"等待中"
		 */

		ThreadPoolExecutorProxyFactory.getDownLoadPoolExecutorProxy().execute(new DownLoadTask(downLoadInfo));

	}

	class DownLoadTask implements Runnable {
		private DownLoadInfo	mDownLoadInfo;

		public DownLoadTask(DownLoadInfo downLoadInfo) {
			mDownLoadInfo = downLoadInfo;
		}

		@Override
		public void run() {
			InputStream in= null;
			FileOutputStream out = null;
			try {

				long initRange = 0;

				File apkFile = new File(mDownLoadInfo.savePath);

				if (apkFile.exists()) {
					initRange = apkFile.length();
				}

				// ②初始化当前的进度
				mDownLoadInfo.progress = initRange;

				/*############### 当前状态: 下载中###############*/
				mDownLoadInfo.curState = STATE_DOWNLOADING;
				// downloadInfo改变的时候,进行通知(发布消息)
				notifyObservers(mDownLoadInfo);
				/*#######################################*/

				// 正在的开始下载数据
				HttpUtils httpUtils = new HttpUtils();
				// http://localhost:8080/GooglePlayServer/download
				// ?name=app/com.itheima.www/com.itheima.www.apk&range=0
				String url = URlS.BASEURL + "download";
				RequestParams params = new RequestParams();
				params.addQueryStringParameter("name", mDownLoadInfo.downloadUrl);
				params.addQueryStringParameter("range", "" + initRange);// ①修改初始化的range信息
				ResponseStream responseStream = httpUtils.sendSync(HttpMethod.GET, url, params);

				boolean isPause = false;
				if (responseStream.getStatusCode() == 200) {
					// 输入流
					in = responseStream.getBaseStream();

					String savePath = mDownLoadInfo.savePath;
					File saveFile = new File(savePath);
					
					out = new FileOutputStream(saveFile, true);// ③ 以追加的形式进行文件的写入

					byte[] buffer = new byte[1024];
					int len = -1;

					while ((len = in.read(buffer)) != -1) {
						if (mDownLoadInfo.curState == STATE_PAUSEDOWNLOAD) {
							isPause = true;
							break;
						}

						out.write(buffer, 0, len);
						
						out.flush();

						mDownLoadInfo.progress += len;

						/*############### 当前状态: 下载中###############*/
						mDownLoadInfo.curState = STATE_DOWNLOADING;
						// downloadInfo改变的时候,进行通知(发布消息)
						notifyObservers(mDownLoadInfo);
						/*#######################################*/
					}
					if (isPause) {// 因为用户暂停下载来到这个地方

					} else {// 因为下载完成来到这个地方
						// 下载完成了
						/*############### 当前状态: 下载完成###############*/
						mDownLoadInfo.curState = STATE_DOWNLOADED;
						// downloadInfo改变的时候,进行通知(发布消息)
						notifyObservers(mDownLoadInfo);
						/*#######################################*/

						mCacheDownLoadInfoMap.remove(mDownLoadInfo.packageName);
					}

				} else {
					/*############### 当前状态: 下载失败###############*/
					mDownLoadInfo.curState = STATE_DOWNLOADFAILED;
					// downloadInfo改变的时候,进行通知(发布消息)
					notifyObservers(mDownLoadInfo);
					/*#######################################*/

				}
			} catch (Exception e) {
				e.printStackTrace();
				/*############### 当前状态: 下载失败###############*/
				mDownLoadInfo.curState = STATE_DOWNLOADFAILED;
				// downloadInfo改变的时候,进行通知(发布消息)
				notifyObservers(mDownLoadInfo);
				/*#######################################*/

			}finally{
				IOUtils.close(out);
				IOUtils.close(in);
			}

		}
	}

	/**
	 * @return 
	 * @des 得到AppDetailBottomHolder里面对应的DownLoadInfo信息
	 * @call 1.AppDetailBottomHolder需要更新下载按钮ui的时候
	 */
	public DownLoadInfo getDownLoadInfo(AppInfoBean data) {
		/*--------------- 常规赋值 ---------------*/
		DownLoadInfo downLoadInfo = new DownLoadInfo();
		downLoadInfo.downloadUrl = data.downloadUrl;

		// apk存到什么目录
		String dir = FileUtils.getDir("apk");// sdcard/Android/data/包目录/apk
		// apk命名规则
		String fileName = data.packageName + ".apk";

		File saveFile = new File(dir, fileName);

		downLoadInfo.savePath = saveFile.getAbsolutePath();

		downLoadInfo.packageName = data.packageName;

		downLoadInfo.max = data.size;

		downLoadInfo.progress = 0;

		/*--------------- 状态 ---------------*/

		/*
		下载中	
		暂停下载
		等待下载
		下载失败 
		 */
		if (mCacheDownLoadInfoMap.containsKey(data.packageName)) {
			return mCacheDownLoadInfoMap.get(data.packageName);
		}

		// 已安装
		if (CommonUtils.isInstalled(UIUtils.getContext(), data.packageName)) {
			downLoadInfo.curState = STATE_INSTALLED;
			return downLoadInfo;
		}
		// 下载完成
		File apkFile = new File(downLoadInfo.savePath);
		if (apkFile.exists() && apkFile.length() == data.size) {
			downLoadInfo.curState = STATE_DOWNLOADED;
			return downLoadInfo;
		}

		// 未下载
		downLoadInfo.curState = STATE_UNDOWNLOAD;

		return downLoadInfo;
	}

	/*=============== 自己实现观察者设计模式 ===============*/
	/**
	 需要如下的3个步骤
	 1.AppDetailBottomHolder需要变成观察者
	 2.把观察者加到观察者集合中
	 3.数据(DownLoadInfo)改变的时候需要发布消息
	 */
	/**定义接口以及接口方法*/
	public interface DownLoadinfoObserver {
		void onDownloadInfoChange(DownLoadInfo downLoadInfo);
	}

	/**定义观察者的集合*/
	List<DownLoadinfoObserver>	observers	= new ArrayList<DownLoadinfoObserver>();

	/** 添加观察者*/
	public void addObserver(DownLoadinfoObserver o) {
		observers.add(o);
	}

	/** 移除观察者*/
	public void deleteObserver(DownLoadinfoObserver o) {
		observers.remove(o);
	}

	/** 通知所有的观察者(发布)*/
	public void notifyObservers(DownLoadInfo downLoadInfo) {
		for (DownLoadinfoObserver o : observers) {
			o.onDownloadInfoChange(downLoadInfo);
		}
	}

	/**
	 * @des 暂停下载
	 * @call 正在下载的时候,用户点击了下载按钮
	 * @param downLoadInfo
	 */
	public void pauseDownLoad(DownLoadInfo downLoadInfo) {
		/*############### 当前状态: 暂停###############*/
		downLoadInfo.curState = STATE_PAUSEDOWNLOAD;
		// downloadInfo改变的时候,进行通知(发布消息)
		notifyObservers(downLoadInfo);
		/*#######################################*/
	}
}
