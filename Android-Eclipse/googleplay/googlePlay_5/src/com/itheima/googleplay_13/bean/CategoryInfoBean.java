package com.itheima.googleplay_13.bean;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-24 上午10:00:37
 * @描述	     TODO
 *
 * @版本       $Rev: 41 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-24 10:28:40 +0800 (星期六, 24 十月 2015) $
 * @更新描述    TODO
 */
public class CategoryInfoBean {
	public String	name1;		// 休闲
	public String	name2;		// 棋牌
	public String	name3;		// 益智
	public String	url1;		// image/category_game_0.jpg
	public String	url2;		// image/category_game_1.jpg
	public String	url3;		// image/category_game_2.jpg
	public String	title;		// 游戏

	public boolean	isTitle;	// 添加一个属性

	@Override
	public String toString() {
		return "CategoryInfoBean [name1=" + name1 + ", name2=" + name2 + ", name3=" + name3 + ", url1=" + url1
				+ ", url2=" + url2 + ", url3=" + url3 + ", title=" + title + ", isTitle=" + isTitle + "]";
	}
	
	

}
