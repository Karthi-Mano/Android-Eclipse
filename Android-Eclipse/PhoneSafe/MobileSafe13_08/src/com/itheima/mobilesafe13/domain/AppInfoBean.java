package com.itheima.mobilesafe13.domain;

import android.graphics.drawable.Drawable;

/**
 * @author Administrator
 * @desc  app基本信息封装
 * @date 2015-9-21
 * @Author $Author: goudan $ Id Rev URL
 * @Date $Date: 2015-09-21 10:34:38 +0800 (Mon, 21 Sep 2015) $
 * @Id $Id: AppInfoBean.java 72 2015-09-21 02:34:38Z goudan $
 * @Rev $Rev: 72 $
 * @URL $URL: https://188.188.4.100/svn/mobilesafeserver/trunk/MobileSafe13/src/com/itheima/mobilesafe13/domain/AppInfoBean.java $
 *
 */
public class AppInfoBean {
	@Override
	public String toString() {
		return "AppInfoBean [icon=" + icon + ", appName=" + appName
				+ ", isSystem=" + isSystem + ", isSD=" + isSD + ", packName="
				+ packName + ", size=" + size + ", sourceDir=" + sourceDir
				+ "]";
	}
	private Drawable icon;//图标
	private String appName;//app名字
	private boolean isSystem;//是否是系统软件
	private boolean isSD;//是否安装在sd卡中
	private String packName;//app包名
	private long size;//占用的大小
	private String sourceDir;//安装路径
	public Drawable getIcon() {
		return icon;
	}
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public boolean isSystem() {
		return isSystem;
	}
	public void setSystem(boolean isSystem) {
		this.isSystem = isSystem;
	}
	public boolean isSD() {
		return isSD;
	}
	public void setSD(boolean isSD) {
		this.isSD = isSD;
	}
	public String getPackName() {
		return packName;
	}
	public void setPackName(String packName) {
		this.packName = packName;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getSourceDir() {
		return sourceDir;
	}
	public void setSourceDir(String sourceDir) {
		this.sourceDir = sourceDir;
	}
}
