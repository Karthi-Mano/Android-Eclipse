package com.rounding.listviewfilter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.rounding.adapter.MyAdapter;

public class MainActivity extends Activity {

	private SearchView		inKey;		// API 11+
	private ListView		listview;
	private BaseAdapter		adapter;
	private List<String>	data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		inKey = (SearchView) findViewById(R.id.inkey);
		listview = (ListView) findViewById(R.id.listview);
		listview.setTextFilterEnabled(true);
		data = new ArrayList<String>();
		data.add("Hello");
		data.add("Hebe");
		data.add("nick");
		data.add("ahha");
		data.add("jack");
		data.add("hello");
		data.add("welcome");
		adapter = new MyAdapter(this, data);
		listview.setAdapter(adapter);

		inKey.setOnQueryTextListener(new OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				if (newText != null && newText.length() > 0) {
					listview.setFilterText(newText); // 设置ListView的过滤关键字
				} else {
					listview.clearTextFilter();
				}
				return true;
			}
		});

	}

}
