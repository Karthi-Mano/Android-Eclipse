package com.dream.searchviewdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

public class MainActivity extends Activity implements SearchView.OnQueryTextListener {

	private ListView				lv;
	private ArrayAdapter<String>	adapter;
	private ArrayList<String>		mDataList;
	private SearchView				searchView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		initActionBar();
		lv = (ListView) findViewById(R.id.lv_main_infolist);
		initAdapter();
		
		lv.setTextFilterEnabled(true);
		searchView.setOnQueryTextListener(this);
	}

	private void initAdapter() {
		mDataList = new ArrayList<String>();
		mDataList.add("aa");
		mDataList.add("ab");
		mDataList.add("bb");
		mDataList.add("bc");
		mDataList.add("fd");
		mDataList.add("afd");
		mDataList.add("cvdf");
		mDataList.add("fdidfa");

		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDataList);
		lv.setAdapter(adapter);

	}

	private void initActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowCustomEnabled(true);
		View view = getLayoutInflater().inflate(R.layout.custom_searchview, null);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		//actionBar设置自定义的searchView
		getActionBar().setCustomView(view, layoutParams);
		searchView = (SearchView) view.findViewById(R.id.sv_searchview);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onQueryTextChange(String newText) {

		if (TextUtils.isEmpty(newText)) {
			lv.clearTextFilter();
		} else {
			lv.setFilterText(newText);
		}

		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		return false;
	}
}
