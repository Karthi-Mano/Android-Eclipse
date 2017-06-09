package com.itheima.googleplay_13.factory;

import com.itheima.googleplay_13.utils.UIUtils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ListView;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-23 上午11:09:15
 * @描述	     TODO
 *
 * @版本       $Rev: 28 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-23 11:35:52 +0800 (星期五, 23 十月 2015) $
 * @更新描述    TODO
 */
public class ListViewFactory {
	public static ListView createListView() {
		ListView listView = new ListView(UIUtils.getContext());
		// 常规的设置
		listView.setCacheColorHint(Color.TRANSPARENT);
		listView.setFastScrollEnabled(true);
		listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		return listView;
	}
}
