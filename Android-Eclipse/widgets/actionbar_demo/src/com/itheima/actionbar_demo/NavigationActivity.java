package com.itheima.actionbar_demo;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-22 下午4:23:10
 * @描述	     TODO
 *
 * @版本       $Rev$
 * @更新者     $Author$
 * @更新时间    $Date$
 * @更新描述    TODO
 */
public class NavigationActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initActionBar();
	}

	private void initActionBar() {
		// 1.得到actionBar对象
		ActionBar actionBar = getSupportActionBar();

		// 设置标题
		actionBar.setTitle("主标题");
		// 设置副标题
		actionBar.setSubtitle("副标题");

		// 修改图标
		actionBar.setIcon(R.drawable.ic_launcher);
		actionBar.setLogo(R.drawable.ic_action_call);// 默认是logo优先

		// 修改logo/icon的优先级
		actionBar.setDisplayUseLogoEnabled(false);// 默认是true,默认显示logo

		// 显示/隐藏回退部分
		actionBar.setDisplayHomeAsUpEnabled(true);// 默认是false,默认隐藏回退部分

		// 显示/隐藏图标
		actionBar.setDisplayShowHomeEnabled(true);

		// 显示/隐藏标题
		actionBar.setDisplayShowTitleEnabled(true);

		// 图标,标题都隐藏,回退部分就自动隐藏了吧

		/*--------------- list模式 ---------------*/
		/**
		 NAVIGATION_MODE_STANDARD 默认
		 NAVIGATION_MODE_LIST 列表
		 NAVIGATION_MODE_TABS tabs页签
		 */
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		// 初始化数据
		final List<String> objects = new ArrayList<String>();
		objects.add("主页");
		objects.add("新闻");
		objects.add("娱乐");

		// 创建adapter
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, objects);
		actionBar.setListNavigationCallbacks(adapter, new OnNavigationListener() {
			@Override
			public boolean onNavigationItemSelected(int itemPosition, long itemId) {
				Toast.makeText(getApplicationContext(), objects.get(itemPosition), 0).show();
				return false;
			}
		});
		/*--------------- tabs模式 ---------------*/

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		for (int i = 0; i < 10; i++) {
			Tab tab = actionBar.newTab();
			tab.setText("item-" + i);
			tab.setIcon(R.drawable.ic_action_delete);
			tab.setTabListener(new TabListener() {

				@Override
				public void onTabUnselected(Tab tab, FragmentTransaction ft) {
					// TODO

				}

				@Override
				public void onTabSelected(Tab tab, FragmentTransaction ft) {
					Toast.makeText(getApplicationContext(), tab.getText(), 0).show();
				}

				@Override
				public void onTabReselected(Tab tab, FragmentTransaction ft) {
					// TODO

				}
			});
			actionBar.addTab(tab);
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
