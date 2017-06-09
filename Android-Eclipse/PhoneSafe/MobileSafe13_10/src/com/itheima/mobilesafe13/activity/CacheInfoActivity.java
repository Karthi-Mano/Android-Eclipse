package com.itheima.mobilesafe13.activity;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.format.Formatter;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.itheima.mobilesafe13.R;
import com.itheima.mobilesafe13.domain.AppInfoBean;
import com.itheima.mobilesafe13.utils.AppInfoUtils;

/**
 * @author Administrator
 * @desc 显示所有app缓存信息
 * @date 2015-9-24
 * @Author $Author: goudan $ Id Rev URL
 * @Date $Date: 2015-09-24 17:31:35 +0800 (Thu, 24 Sep 2015) $
 * @Id $Id: CacheInfoActivity.java 101 2015-09-24 09:31:35Z goudan $
 * @Rev $Rev: 101 $
 * @URL $URL: https://188.188.4.100/svn/mobilesafeserver/trunk/MobileSafe13/src/com/itheima/mobilesafe13/activity/CacheInfoActivity.java $
 * 
 */
public class CacheInfoActivity extends Activity {
	protected static final int BEGINNING = 1;
	protected static final int SCANNING = 2;
	protected static final int FINISH = 3;
	private ImageView iv_scan;
	private ProgressBar pb_scanprogress;
	private TextView tv_appname;
	private LinearLayout ll_scanresult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		initView();
		
		initAnimation();
		
		scanCache();
	}
	
	private void initAnimation() {
		mRa = new RotateAnimation(0, 360, 
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		mRa.setDuration(2000);
		mRa.setRepeatCount(-1);
		mRa.setInterpolator(new Interpolator() {
			
			@Override
			public float getInterpolation(float x) {
				// TODO Auto-generated method stub
				return 2*x;
			}
		});
		
		
		
	}

	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case BEGINNING://开始扫描
				iv_scan.startAnimation(mRa);
				break;
			case SCANNING://扫描中
				ScanInfo info = (ScanInfo) msg.obj;
				tv_appname.setText("正在扫描:" + info.appName);
				pb_scanprogress.setMax(info.max);
				pb_scanprogress.setProgress(info.progress);
				break;
			case FINISH://扫描完成
				//停止动画
				iv_scan.clearAnimation();
				//显示结果
				if (infos.size() > 0) {
					//有缓存信息
					//读取显示缓存信息
					for (CacheInfo i :infos) {
						View v = View.inflate(getApplicationContext(), R.layout.item_cacheinfo_ll, null);
						ImageView iv_icon = (ImageView) v.findViewById(R.id.iv_item_cacheinfo_lv_icon);
						TextView tv_name = (TextView) v.findViewById(R.id.tv_item_cacheinfo_lv_name);
						TextView tv_size = (TextView) v.findViewById(R.id.tv_item_cacheinfo_cachesize);
						
						iv_icon.setImageDrawable(i.icon);
						tv_name.setText(i.name);
						tv_size.setText(Formatter.formatFileSize(getApplicationContext(), i.size));
						
						//添加ll中
						ll_scanresult.addView(v,0);
					}
				}
				break;
			default:
				break;
			}
		};
	};
	private RotateAnimation mRa;
	
	private void scanCache() {
		//耗时
		new Thread(){
			public void run() {
				//1.开始扫描的消息
				mHandler.obtainMessage(BEGINNING).sendToTarget();
				int progress = 0;
				
				//2. 获取锁apk
				List<AppInfoBean> allInstalledAppInfos = AppInfoUtils.getAllInstalledAppInfos(getApplicationContext());
				for (AppInfoBean appInfoBean : allInstalledAppInfos) {
					//每个apk
					getCacheInfo(appInfoBean);
					progress++;
					
					ScanInfo info = new ScanInfo();
					info.appName = appInfoBean.getAppName();
					info.progress = progress;
					info.max = allInstalledAppInfos.size();
					
					//发个消息
					Message msg = mHandler.obtainMessage(SCANNING);
					msg.obj = info;
					mHandler.sendMessage(msg);
					
					SystemClock.sleep(100);
				}
				
				//3. 发送扫描完成
				mHandler.obtainMessage(FINISH).sendToTarget();
			};
		}.start();
		
	}
	
	private class ScanInfo{
		String appName;
		int progress;
		int max;
		
	}

	private void initView() {
		setContentView(R.layout.activity_cacheinfo);
		//扇形的iv
		
		iv_scan = (ImageView) findViewById(R.id.iv_cacheinfo_scanning);
		
		ll_scanresult = (LinearLayout) findViewById(R.id.ll_cacheinfo_result);
		pb_scanprogress = (ProgressBar) findViewById(R.id.pb_cacheinof_pb);
		tv_appname = (TextView) findViewById(R.id.tv_cacheinfo_scanappname);
	}

	public void clearCache(View v){
		//清理缓存
	}
	private class CacheInfo{
		Drawable icon;
		String name;
		long size;
	}
	private List<CacheInfo> infos = new Vector<CacheInfo>(); 
	

	private void getCacheInfo(AppInfoBean bean) {
		// 1. mPm.getPackageSizeInfo(packageName,
		// mBackgroundHandler.mStatsObserver);
		// 2. final IPackageStatsObserver.Stub mStatsObserver = new
		// IPackageStatsObserver.Stub()
		PackageManager mPm = getPackageManager();
		// 反射

		try {
			class MyIPackageStatsObserver extends IPackageStatsObserver.Stub{
				private AppInfoBean bean ;
				public MyIPackageStatsObserver(AppInfoBean bean) {
					this.bean = bean;
				}
				@Override
				public void onGetStatsCompleted(PackageStats pStats,
						boolean succeeded) throws RemoteException {
					// 回调结果 子线程
					//System.out.println(Formatter.formatFileSize(getApplicationContext(),pStats.cacheSize));
					//缓存大小
					if (pStats.cacheSize > 0){
						//
						CacheInfo info = new CacheInfo();
						
						info.size = pStats.cacheSize;
						info.icon = bean.getIcon();
						info.name = bean.getAppName();
						infos.add(info);
						
					}
				}
				 
			 };
			// 1 . class
			Class type = mPm.getClass();
			// 2. method
			Method method = type.getDeclaredMethod("getPackageSizeInfo", String.class,
					IPackageStatsObserver.class);
			//3. mPm
			//4. invoke
			method.invoke(mPm, bean.getPackName(),new MyIPackageStatsObserver(bean));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
