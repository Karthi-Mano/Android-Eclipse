package com.itheima.googleplay_13.holder;

import java.io.File;

import android.view.View;
import android.view.View.OnClickListener;

import com.itheima.googleplay_13.R;
import com.itheima.googleplay_13.base.BaseHolder;
import com.itheima.googleplay_13.bean.AppInfoBean;
import com.itheima.googleplay_13.manager.DownLoadInfo;
import com.itheima.googleplay_13.manager.DownLoadManager;
import com.itheima.googleplay_13.manager.DownLoadManager.DownLoadinfoObserver;
import com.itheima.googleplay_13.utils.CommonUtils;
import com.itheima.googleplay_13.utils.PrintDownLoadInfo;
import com.itheima.googleplay_13.utils.UIUtils;
import com.itheima.googleplay_13.view.ProgressButton;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-24 下午3:27:10
 * @描述	     TODO
 *
 * @版本       $Rev: 64 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-25 15:24:49 +0800 (星期日, 25 十月 2015) $
 * @更新描述    TODO
 */
public class AppDetailBottomHolder extends BaseHolder<AppInfoBean> implements OnClickListener, DownLoadinfoObserver {
	@ViewInject(R.id.app_detail_download_btn_download)
	ProgressButton		mProgressBtn;
	private AppInfoBean	mAppInfoBean;

	@Override
	protected View initHolderView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.item_detail_bottom, null);
		ViewUtils.inject(this, view);

		mProgressBtn.setOnClickListener(this);
		return view;
	}

	@Override
	protected void refreshHolderView(AppInfoBean data) {
		// 保存成成员变量
		mAppInfoBean = data;

		/*--------------- 得到当前的AppInfoBean对应的DownLoadInfo信息 ---------------*/
		// 状态在哪里?-->downLoadInfo里面
		DownLoadInfo downLoadInfo = DownLoadManager.getInstance().getDownLoadInfo(data);
		/**
		 状态(编程记录)  	|  给用户的提示(ui展现) 
		----------------|-----------------------	
		未下载			|下载				    	
		下载中			|显示进度条		   			
		暂停下载			|继续下载				
		等待下载			|等待中...				
		下载失败 			|重试						
		下载完成 			|安装						
		已安装 			|打开						

		 */
		/*--------------- 2.根据不同的状态给用户提示 ---------------*/
		refreshProgressBtn(downLoadInfo);
	}

	public void refreshProgressBtn(DownLoadInfo downLoadInfo) {
		// 还原
		mProgressBtn.setBackgroundResource(R.drawable.selector_app_detail_bottom_normal);

		switch (downLoadInfo.curState) {
		case DownLoadManager.STATE_UNDOWNLOAD:// 未下载
			mProgressBtn.setText("下载");
			break;
		case DownLoadManager.STATE_DOWNLOADING:// 下载中
			// 设置
			mProgressBtn.setBackgroundResource(R.drawable.selector_app_detail_bottom_downloading);

			mProgressBtn.setMax(downLoadInfo.max);
			mProgressBtn.setProgress(downLoadInfo.progress);
			int progress = (int) (downLoadInfo.progress * 100.0f / downLoadInfo.max + .5f);
			mProgressBtn.setText(progress + "%");
			break;
		case DownLoadManager.STATE_PAUSEDOWNLOAD:// 暂停下载
			mProgressBtn.setText("继续下载");
			break;
		case DownLoadManager.STATE_WAITINGDOWNLOAD:// 等待下载
			mProgressBtn.setText("等待中....");
			break;
		case DownLoadManager.STATE_DOWNLOADFAILED:// 下载失败
			mProgressBtn.setText("重试");
			break;
		case DownLoadManager.STATE_DOWNLOADED:// 下载完成
			mProgressBtn.setProgressEnable(false);

			mProgressBtn.setText("安装");
			break;
		case DownLoadManager.STATE_INSTALLED:// 已安装
			mProgressBtn.setText("打开");
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		/*--------------- 3.根据不同的状态触发不同的操作 ---------------*/
		DownLoadInfo downLoadInfo = DownLoadManager.getInstance().getDownLoadInfo(mAppInfoBean);
		/**
		 
		状态(编程记录)     | 用户行为(触发操作) 
		----------------| -----------------
		未下载			| 去下载
		下载中			| 暂停下载
		暂停下载			| 断点继续下载
		等待下载			| 取消下载
		下载失败 			| 重试下载
		下载完成 			| 安装应用
		已安装 			| 打开应用
				
		 */
		switch (downLoadInfo.curState) {
		case DownLoadManager.STATE_UNDOWNLOAD:// 未下载
			doDownLoad(downLoadInfo);
			break;
		case DownLoadManager.STATE_DOWNLOADING:// 下载中
			pauseDownLoad(downLoadInfo);
			break;
		case DownLoadManager.STATE_PAUSEDOWNLOAD:// 暂停下载
			doDownLoad(downLoadInfo);
			break;
		case DownLoadManager.STATE_WAITINGDOWNLOAD:// 等待下载
			cancelDownLoad(downLoadInfo);
			break;
		case DownLoadManager.STATE_DOWNLOADFAILED:// 下载失败
			doDownLoad(downLoadInfo);
			break;
		case DownLoadManager.STATE_DOWNLOADED:// 下载完成
			installApk(downLoadInfo);
			break;
		case DownLoadManager.STATE_INSTALLED:// 已安装
			openApk(downLoadInfo);
			break;

		default:
			break;
		}

		/*DownLoadInfo downLoadInfo = new DownLoadInfo();

		// apk存到什么目录
		String dir = FileUtils.getDir("apk");// sdcard/Android/data/包目录/apk
		// apk命名规则
		String fileName = mAppInfoBean.packageName + ".apk";

		File saveFile = new File(dir, fileName);

		downLoadInfo.downloadUrl = mAppInfoBean.downloadUrl;
		downLoadInfo.savePath = saveFile.getAbsolutePath();
		downLoadInfo.packageName = mAppInfoBean.packageName;

		DownLoadManager.getInstance().downLoad(downLoadInfo);*/
	}

	/**
	 * 暂停下载
	 * @param downLoadInfo
	 */
	private void pauseDownLoad(DownLoadInfo downLoadInfo) {
		DownLoadManager.getInstance().pauseDownLoad(downLoadInfo);
	}

	/**
	 * 取消下载
	 * @param downLoadInfo
	 */
	private void cancelDownLoad(DownLoadInfo downLoadInfo) {
		// TODO

	}

	/**
	 * 开始下载,断点继续下载,重试操作
	 * @param downLoadInfo
	 */
	private void doDownLoad(DownLoadInfo downLoadInfo) {
		DownLoadManager.getInstance().downLoad(downLoadInfo);
	}

	/**
	 * 安装apk
	 * @param downLoadInfo
	 */
	private void installApk(DownLoadInfo downLoadInfo) {
		File apkFile = new File(downLoadInfo.savePath);
		CommonUtils.installApp(UIUtils.getContext(), apkFile);
	}

	/**
	 * 打开apk
	 * @param downLoadInfo
	 */
	private void openApk(DownLoadInfo downLoadInfo) {
		CommonUtils.openApp(UIUtils.getContext(), downLoadInfo.packageName);
	}

	/*--------------- 收到DownloadInfo被修改的通知 ---------------*/
	@Override
	public void onDownloadInfoChange(final DownLoadInfo downLoadInfo) {

		// 过滤
		if (!downLoadInfo.packageName.equals(mAppInfoBean.packageName)) {
			return;
		}

		// 根据传递过来的最新的downLoadInfo,更新ui
		PrintDownLoadInfo.printDownLoadInfo(downLoadInfo);
		UIUtils.postTaskSafely(new Runnable() {

			@Override
			public void run() {
				refreshProgressBtn(downLoadInfo);
			}
		});
	}

	/**
	 	1.添加观察者
		2.手动的发布一下
	 */

	public void addObserverAndNotify() {
		// 1.添加观察者
		DownLoadManager.getInstance().addObserver(this);
		// 2.手动的发布一下
		DownLoadInfo downLoadInfo = DownLoadManager.getInstance().getDownLoadInfo(mAppInfoBean);
		DownLoadManager.getInstance().notifyObservers(downLoadInfo);
	}
}
