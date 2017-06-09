package com.itheima.googleplay_13.manager;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-25 上午9:00:31
 * @描述	     把下载相关的参数组合成一个类,统一管理
 * @描述	     把下载相关的信息
 *
 * @版本       $Rev: 68 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-25 18:06:46 +0800 (星期日, 25 十月 2015) $
 * @更新描述    TODO
 */
public class DownLoadInfo {
	public String	downloadUrl;	// 下载地址
	public String	savePath;		// 保存路径
	public int		curState;		// 当前的下载状态
	public String	packageName;	// apk对应的包名信息
	public long		max;			// 进度的最大值
	public long		progress;		// 进度的当前值
	@Override
	public String toString() {
		return "DownLoadInfo [downloadUrl=" + downloadUrl + ", savePath=" + savePath + ", curState=" + curState
				+ ", packageName=" + packageName + ", max=" + max + ", progress=" + progress + "]";
	}
	
}
