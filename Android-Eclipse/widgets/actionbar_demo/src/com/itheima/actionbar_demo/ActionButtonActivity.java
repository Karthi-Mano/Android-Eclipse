package com.itheima.actionbar_demo;

import java.lang.reflect.Field;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-22 下午4:43:49
 * @描述	     TODO
 *
 * @版本       $Rev$
 * @更新者     $Author$
 * @更新时间    $Date$
 * @更新描述    TODO
 */
public class ActionButtonActivity extends ActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getOverflowMenu();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO
		return super.onOptionsItemSelected(item);
	}
    private void getOverflowMenu() {  
    try {  
       ViewConfiguration config = ViewConfiguration.get(this);  
       Field menuKeyField = ViewConfiguration.class.getDeclaredField    ("sHasPermanentMenuKey");  
       if(menuKeyField != null) {  
           menuKeyField.setAccessible(true);  
           menuKeyField.setBoolean(config, false);  
       }  
    } catch (Exception e) {  
        e.printStackTrace();  
    }  
    }  
}
