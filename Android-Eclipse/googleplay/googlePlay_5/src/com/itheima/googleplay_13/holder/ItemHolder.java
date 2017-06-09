package com.itheima.googleplay_13.holder;

import java.io.File;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.itheima.googleplay_13.R;
import com.itheima.googleplay_13.base.BaseHolder;
import com.itheima.googleplay_13.bean.AppInfoBean;
import com.itheima.googleplay_13.conf.Constants.URlS;
import com.itheima.googleplay_13.manager.DownLoadInfo;
import com.itheima.googleplay_13.manager.DownLoadManager;
import com.itheima.googleplay_13.manager.DownLoadManager.DownLoadinfoObserver;
import com.itheima.googleplay_13.utils.BitmapHelper;
import com.itheima.googleplay_13.utils.CommonUtils;
import com.itheima.googleplay_13.utils.PrintDownLoadInfo;
import com.itheima.googleplay_13.utils.StringUtils;
import com.itheima.googleplay_13.utils.UIUtils;
import com.itheima.googleplay_13.view.ProgressView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-21 上午11:02:09
 * @描述	     1.提供视图
 * @描述	     2.接收数据
 * @描述	     3.内部让  数据和视图绑定
 *
 * @版本       $Rev: 68 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-25 18:06:46 +0800 (星期日, 25 十月 2015) $
 * @更新描述    TODO
 */
public class ItemHolder extends BaseHolder<AppInfoBean> implements DownLoadinfoObserver, OnClickListener {
	@ViewInject(R.id.item_appinfo_iv_icon)
	ImageView			mIvIcon;

	@ViewInject(R.id.item_appinfo_rb_stars)
	RatingBar			mRbStars;

	@ViewInject(R.id.item_appinfo_tv_des)
	TextView			mTvDes;

	@ViewInject(R.id.item_appinfo_tv_size)
	TextView			mTvSize;

	@ViewInject(R.id.item_appinfo_tv_title)
	TextView			mTvTitle;

	@ViewInject(R.id.item_appinfo_pv)
	ProgressView		mProgressView;

	private AppInfoBean	mAppInfoBean;

	@Override
	protected View initHolderView() {// 视图是啥
		View rootView = View.inflate(UIUtils.getContext(), R.layout.item_home_info, null);
		// 找孩子
		ViewUtils.inject(this, rootView);

		mProgressView.setOnClickListener(this);
		return rootView;
	}

	@Override
	protected void refreshHolderView(AppInfoBean data) {// 有了数据,数据和视图如何绑定
		/**
		 因为只有在正在下载的时候,mProgressView才会设置
		 */
		// 重置
		mProgressView.setProgress(0);

		mAppInfoBean = data;

		mTvDes.setText(data.des);
		mTvSize.setText(StringUtils.formatFileSize(data.size));
		mTvTitle.setText(data.name);

		mRbStars.setRating(data.stars);

		// 默认图片
		mIvIcon.setImageResource(R.drawable.ic_default);
		// 图片加载
		// BitmapUtils bitmapUtils = new BitmapUtils(UIUtils.getContext());
		// http://localhost:8080/GooglePlayServer/image?name=app/com.itheima.www/icon.jpg
		BitmapHelper.display(mIvIcon, URlS.IMAGEBASEURL + data.iconUrl);

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
		refreshProgressView(downLoadInfo);

	}

	public void refreshProgressView(DownLoadInfo downLoadInfo) {

		switch (downLoadInfo.curState) {
		case DownLoadManager.STATE_UNDOWNLOAD:// 未下载
			mProgressView.setNote("下载");
			mProgressView.setIcon(R.drawable.ic_download);
			break;
		case DownLoadManager.STATE_DOWNLOADING:// 下载中

			mProgressView.setMax(downLoadInfo.max);
			mProgressView.setProgress(downLoadInfo.progress);
			int progress = (int) (downLoadInfo.progress * 100.0f / downLoadInfo.max + .5f);
			mProgressView.setNote(progress + "%");

			mProgressView.setIcon(R.drawable.ic_pause);
			break;
		case DownLoadManager.STATE_PAUSEDOWNLOAD:// 暂停下载
			mProgressView.setNote("继续下载");

			mProgressView.setIcon(R.drawable.ic_resume);
			break;
		case DownLoadManager.STATE_WAITINGDOWNLOAD:// 等待下载
			mProgressView.setNote("等待中....");

			mProgressView.setIcon(R.drawable.ic_pause);
			break;
		case DownLoadManager.STATE_DOWNLOADFAILED:// 下载失败
			mProgressView.setNote("重试");

			mProgressView.setIcon(R.drawable.ic_redownload);
			break;
		case DownLoadManager.STATE_DOWNLOADED:// 下载完成
			mProgressView.setProgressEnable(false);

			mProgressView.setNote("安装");

			mProgressView.setIcon(R.drawable.ic_install);
			break;
		case DownLoadManager.STATE_INSTALLED:// 已安装
			mProgressView.setNote("打开");

			mProgressView.setIcon(R.drawable.ic_install);
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
				// TODO刷新ProgressView
				refreshProgressView(downLoadInfo);
			}
		});
	}

}
