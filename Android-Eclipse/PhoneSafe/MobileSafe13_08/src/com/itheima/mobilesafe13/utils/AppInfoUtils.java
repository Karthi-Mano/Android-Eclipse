package com.itheima.mobilesafe13.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.itheima.mobilesafe13.domain.AppInfoBean;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

/**
 * @author Administrator
 * @desc  软件管家的工具类  所有APP信息 sd可用内存 手机可用内存 
 * @date 2015-9-21
 * @Author $Author: goudan $ Id Rev URL
 * @Date $Date: 2015-09-21 14:19:20 +0800 (Mon, 21 Sep 2015) $
 * @Id $Id: AppInfoUtils.java 74 2015-09-21 06:19:20Z goudan $
 * @Rev $Rev: 74 $
 * @URL $URL: https://188.188.4.100/svn/mobilesafeserver/trunk/MobileSafe13/src/com/itheima/mobilesafe13/utils/AppInfoUtils.java $
 *
 */
public class AppInfoUtils {
	/**
	 * @return
	 *     手机可用内存
	 */
	public static long getPhoneTotalMem(){
		File dataDirectory = Environment.getDataDirectory();
		return dataDirectory.getTotalSpace();
	}
	/**
	 * @return
	 *     手机可用内存
	 */
	public static long getPhoneAvailMem(){
		File dataDirectory = Environment.getDataDirectory();
		return dataDirectory.getFreeSpace();
	}

	/**
	 * @return
	 *     sd可用内存
	 */
	public static long getSDTotalMem(){
		File dataDirectory = Environment.getExternalStorageDirectory();
		return dataDirectory.getTotalSpace();
	}
	
	/**
	 * @return
	 *     sd可用内存
	 */
	public static long getSDAvailMem(){
		File dataDirectory = Environment.getExternalStorageDirectory();
		return dataDirectory.getFreeSpace();
	}
	/**
	 * @return
	 *    获取所有安装的app信息 
	 */
	public static List<AppInfoBean> getAllInstalledAppInfos1(Context context){
		List<AppInfoBean> datas = new ArrayList<AppInfoBean>();
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
		AppInfoBean bean = null;
		for (PackageInfo packageInfo : installedPackages) {
			ApplicationInfo applicationInfo = packageInfo.applicationInfo;
			//组织数据
			bean = new AppInfoBean();
			//包名
			bean.setPackName(applicationInfo.packageName);
			//图标
			bean.setIcon(applicationInfo.loadIcon(pm));
			//名字
			bean.setAppName(applicationInfo.loadLabel(pm) + "");
			
			if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0){
				//系统app
				bean.setSystem(true);
			}
			
			if ((applicationInfo.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0){
				//安装在sd卡中
				bean.setSD(true);
			}
			
			//路径
			bean.setSourceDir(applicationInfo.sourceDir);
			
			//安装文件的大小
			bean.setSize(new File(applicationInfo.sourceDir).length());
			
			
			datas.add(bean);
		}
		
		return datas;
	}
	
	/**
	 * @return
	 *    获取所有安装的app信息 
	 */
	public static List<AppInfoBean> getAllInstalledAppInfos(Context context){
		List<AppInfoBean> datas = new ArrayList<AppInfoBean>();
		PackageManager pm = context.getPackageManager();
		List<ApplicationInfo> installedApplications = pm.getInstalledApplications(0);
		AppInfoBean bean = null;
		for (ApplicationInfo applicationInfo : installedApplications) {
			//组织数据
			bean = new AppInfoBean();
			//包名
			bean.setPackName(applicationInfo.packageName);
			//图标
			bean.setIcon(applicationInfo.loadIcon(pm));
			//名字
			bean.setAppName(applicationInfo.loadLabel(pm) + "");
			
			if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0){
				//系统app
				bean.setSystem(true);
			}
			
			if ((applicationInfo.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0){
				//安装在sd卡中
				bean.setSD(true);
			}
			
			//路径
			bean.setSourceDir(applicationInfo.sourceDir);
			
			//安装文件的大小
			bean.setSize(new File(applicationInfo.sourceDir).length());
			
			
			datas.add(bean);
		}
		
		return datas;
	}

}
