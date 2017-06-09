package com.itheima.actionbar_demo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-22 下午5:00:29
 * @描述	     TODO
 *
 * @版本       $Rev$
 * @更新者     $Author$
 * @更新时间    $Date$
 * @更新描述    TODO
 */
public class ActionModeActivity extends ActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actionmode);
	}

	public void startActionMode(View v) {
		startSupportActionMode(new Callback() {

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				// TODO
				return true;
			}

			@Override
			public void onDestroyActionMode(ActionMode mode) {
				// TODO

			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				getMenuInflater().inflate(R.menu.menu_actionmode, menu);
				return true;
			}

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				// TODO
				return false;
			}
		});
	}
}
