package com.itheima.googleplay_13.factory;

import java.util.HashMap;
import java.util.Map;

import android.support.v4.app.Fragment;

import com.itheima.googleplay_13.base.BaseFragment;
import com.itheima.googleplay_13.fragment.AppFragment;
import com.itheima.googleplay_13.fragment.CategoryFragment;
import com.itheima.googleplay_13.fragment.GameFragment;
import com.itheima.googleplay_13.fragment.HomeFragment;
import com.itheima.googleplay_13.fragment.HotFragment;
import com.itheima.googleplay_13.fragment.RecommendFragment;
import com.itheima.googleplay_13.fragment.SubjectFragment;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-20 上午11:18:42
 * @描述	     TODO
 *
 * @版本       $Rev: 12 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-20 17:49:34 +0800 (星期二, 20 十月 2015) $
 * @更新描述    TODO
 */

/**
  <string-array name="main_titles">
        <item>首页</item>
        <item>应用</item>
        <item>游戏</item>
        <item>专题</item>
        <item>推荐</item>
        <item>分类</item>
        <item>排行</item>
    </string-array>
 */
public class FragmentFactory {
	public static final int						FRAGMENT_HOME		= 0;
	public static final int						FRAGMENT_APP		= 1;
	public static final int						FRAGMENT_GAME		= 2;
	public static final int						FRAGMENT_SUBJECT	= 3;
	public static final int						FRAGMENT_RECOMMEND	= 4;
	public static final int						FRAGMENT_CATEGORY	= 5;
	public static final int						FRAGMENT_HOT		= 6;
	// FragementPagerAdapter-->FragementManager
	// Map<Integer, Fragment>-->map-->取出来方面

	private static Map<Integer, BaseFragment>	mCacheFragmentMap	= new HashMap<Integer, BaseFragment>();

	public static BaseFragment createFragement(int index) {

		BaseFragment fragment = null;
		// 判断集合中是否存在
		if (mCacheFragmentMap.containsKey(index)) {
			fragment = mCacheFragmentMap.get(index);
			return fragment;
		}

		switch (index) {
		case FRAGMENT_HOME:// 首页
			fragment = new HomeFragment();
			break;
		case FRAGMENT_APP:// 应用
			fragment = new AppFragment();
			break;
		case FRAGMENT_GAME:// 游戏
			fragment = new GameFragment();
			break;
		case FRAGMENT_SUBJECT:// 专题
			fragment = new SubjectFragment();
			break;
		case FRAGMENT_RECOMMEND:// 推荐
			fragment = new RecommendFragment();
			break;
		case FRAGMENT_CATEGORY:// 分类
			fragment = new CategoryFragment();
			break;
		case FRAGMENT_HOT:// 排行
			fragment = new HotFragment();
			break;

		default:
			break;
		}
		// 保存到集合中
		mCacheFragmentMap.put(index, fragment);
		return fragment;
	}
}
