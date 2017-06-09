package com.itheima.googleplay_13.conf;

import com.itheima.googleplay_13.utils.LogUtils;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-20 上午10:21:37
 * @描述	     常量类
 *
 * @版本       $Rev: 20 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-21 15:26:19 +0800 (星期三, 21 十月 2015) $
 * @更新描述    TODO
 */
public class Constants {
	/**
	 * 日志级别
	 * LogUtils.LEVEL_ALL:显示所有日志
	 * LogUtils.LEVEL_OFF:不显示所有日志(关闭日志)
	 */
	public static final int	DEBUGLEVEL	= LogUtils.LEVEL_ALL;

	public static final class URlS {
		public static final String	BASEURL			= "http://188.188.4.100:8080/GooglePlayServer/";
		public static final String	IMAGEBASEURL	= BASEURL + "image?name=";
	}

	public static final class REQ {

	}

	public static final class RESPONSE {

	}

	public static final class PAY {
		public static final int	PAYTYPE_ZHIFUBAO	= 1;
		public static final int	PAYTYPE_UUPAY		= 2;
		public static final int	PAYTYPE_WEIXIN		= 3;
	}
}
