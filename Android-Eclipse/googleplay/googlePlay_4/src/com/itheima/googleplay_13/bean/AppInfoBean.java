package com.itheima.googleplay_13.bean;

import java.util.List;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-21 下午2:44:58
 * @描述	     TODO
 *
 * @版本       $Rev: 50 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-24 16:09:32 +0800 (星期六, 24 十月 2015) $
 * @更新描述    TODO
 */
public class AppInfoBean {
	public String					des;			// 应用的描述
	public String					downloadUrl;	// 应用的下载地址
	public String					iconUrl;		// 应用的图片地址
	public long						id;			// 应用的id
	public String					name;			// 应用的名字
	public String					packageName;	// 应用的包名
	public long						size;			// 应用的大小
	public float					stars;			// 应用的评分
	/*--------------- 详情里面额外的字段 ---------------*/

	public String					author;		// 黑马程序员
	public String					date;			// 2015-06-10
	public String					downloadNum;	// 40万+
	public String					version;		// 1.1.0605.0

	public List<AppinfoSafeBean>	safe;			// Array
	public List<String>				screen;		// Array

	public class AppinfoSafeBean {
		public String	safeDes;		// 已通过安智市场安全检测，请放心使用
		public int	safeDesColor;	// 0
		public String	safeDesUrl;	// app/com.itheima.www/safeDesUrl0.jpg
		public String	safeUrl;		// app/com.itheima.www/safeIcon0.jpg
	}

	@Override
	public String toString() {
		return "AppInfoBean [des=" + des + ", downloadUrl=" + downloadUrl + ", iconUrl=" + iconUrl + ", id=" + id
				+ ", name=" + name + ", packageName=" + packageName + ", size=" + size + ", stars=" + stars
				+ ", author=" + author + ", date=" + date + ", downloadNum=" + downloadNum + ", version=" + version
				+ ", safe=" + safe + ", screen=" + screen + "]";
	}
	
	
}
