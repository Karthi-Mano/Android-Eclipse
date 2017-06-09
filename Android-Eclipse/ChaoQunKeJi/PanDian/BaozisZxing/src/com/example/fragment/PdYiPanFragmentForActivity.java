package com.example.fragment;


import com.baozi.activity.PanDianActivity;
import com.example.baoziszxing.R;
import com.example.dbhelper.PanDianOpenHelper;
import com.example.providers.PanDianPrivoder;



import android.content.Context;

import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PdYiPanFragmentForActivity extends Fragment {

	private PanDianActivity activity;
	private ListView lv_pandian_all;
	private MyAdapter myAdapter;
	private int isUpload;// 0未提交，1提交

	private TextView tv_pd_commit;
	private ProgressBar pb_pd_commit;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		activity = (PanDianActivity) getActivity();
		isUpload = activity.isUploaddd;// 得到activity中的isUploaddd数据
		registerContentObserver();
		super.onCreate(savedInstanceState);
	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = View.inflate(activity, R.layout.fragment_pandian_yipan,
				null);
		lv_pandian_all = (ListView) view.findViewById(R.id.lv_pandainfg_all);

		pb_pd_commit = (ProgressBar) view.findViewById(R.id.pb_pd_commit);
		tv_pd_commit = (TextView) view.findViewById(R.id.tv_pd_commit);

		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		initData();
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroy() {

		unRegisterContentObserver();
		if(myAdapter!=null && myAdapter.getCursor() != null){
			myAdapter.getCursor().close();  
		}
		super.onDestroy();
	}

	private void initData() {

		queryDataAndRefresh();

	}

	private void queryDataAndRefresh() {

		/*
		 * // 判断adpter是否存在 if (myAdapter != null) { // 刷新adapter
		 * myAdapter.getCursor().requery(); return; }
		 */

		// 查询数据库显示界面

		new Thread(new Runnable() {

			@Override
			public void run() {

				/*
				 * // 查询数据库，已盘点并且对应的盘点表,得到Cursor final Cursor cursor =
				 * activity.getContentResolver().query(
				 * PanDianPrivoder.URI_PANDIAN, null,
				 * PanDianOpenHelper.PanDianTable.ISTATE + "=?" + " and " +
				 * PanDianOpenHelper.PanDianTable.ICHECKVOUCH + "=?", new
				 * String[] { activity.yTYPE, activity.iCHECKVOUCH + "" },
				 * null);
				 */

				Cursor cursor = null;

				switch (isUpload) {
				case 0:// 如果isupload==0，匹配URI_PANDIAN

					// 查询数据库，未盘点查询对应的表,得到Cursor
					cursor = activity
							.getContentResolver()
							.query(PanDianPrivoder.URI_PANDIAN,
									null,
									PanDianOpenHelper.PanDianTable.ISTATE_NAME
											+ "=?"
											+ " and "
											+ PanDianOpenHelper.PanDianTable.ICHECKVOUCH
											+ "=?",
									new String[] { activity.yTYPE,
											activity.iCHECKVOUCH + "" }, null);

					System.out.println("匹配了" + PanDianPrivoder.URI_PANDIAN);
					break;
				case 1:// 如果isupload==1，匹配URI_YPANDIAN

					// 查询数据库，已盘点查询对应的表,得到Cursor
					cursor = activity
							.getContentResolver()
							.query(PanDianPrivoder.URI_YPANDIAN,
									null,
									PanDianOpenHelper.PanDianTable.ISTATE_NAME
											+ "=?"
											+ " and "
											+ PanDianOpenHelper.PanDianTable.ICHECKVOUCH
											+ "=?",
									new String[] { activity.yTYPE,
											activity.iCHECKVOUCH + "" }, null);

					System.out.println("匹配了" + PanDianPrivoder.URI_YPANDIAN);
					break;
				default:
					break;
				}

				final Cursor cursor2 = cursor;

				
			
				// 刷新界面
				activity.runOnUiThread(new Runnable() {

					@Override
					public void run() {

						pb_pd_commit.setVisibility(View.GONE);
						tv_pd_commit.setVisibility(View.GONE);

						myAdapter = new MyAdapter(activity, cursor2);

						// 设置adapter
						lv_pandian_all.setAdapter(myAdapter);
					}
				});

			}
		}).start();
	}

	/**
	 * CursorAdapter显示界面
	 * 
	 * @author Wjz
	 * 
	 */
	private class MyAdapter extends CursorAdapter {

		public MyAdapter(Context context, Cursor c) {
			super(context, c);

		}

		@Override
		public void bindView(View view, Context arg1, Cursor c) {
			TextView tv_ccode = (TextView) view.findViewById(R.id.tv_ccode);
			TextView cname = (TextView) view.findViewById(R.id.cname);
	     	TextView tv_iclass_name = (TextView) view.findViewById(R.id.tv_iclass_name);
			TextView tv_idepariment_name = (TextView) view.findViewById(R.id.tv_idepariment_name);

			String ccode = c.getString(c
					.getColumnIndex(PanDianOpenHelper.PanDianTable.CCODE));// ccode
			String name = c.getString(c
					.getColumnIndex(PanDianOpenHelper.PanDianTable.CNAME));// 名称
			String iclass_name = c.getString(c
					.getColumnIndex(PanDianOpenHelper.PanDianTable.ICLASS_NAME));// 类型
			String idepariment_name = c.getString(c
					.getColumnIndex(PanDianOpenHelper.PanDianTable.IDEPARTMENT_NAME));// 所有部门

			tv_ccode.setText(ccode);
			cname.setText(name);
			tv_iclass_name.setText(iclass_name);
			tv_idepariment_name.setText(idepariment_name);


		}

		@Override
		public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {

			View view = View.inflate(activity, R.layout.item_pf_pandian_all,
					null);

			return view;
		}
	}

	MyContentObserver myContentObserver = new MyContentObserver(new Handler());

	/**
	 * 注册监听
	 */
	public void registerContentObserver() {
		switch (isUpload) {
		case 0:// 如果isupload==0，匹配URI_PANDIAN
			activity.getContentResolver().registerContentObserver(
					PanDianPrivoder.URI_PANDIAN, true, myContentObserver);
			break;
		case 1:// 如果isupload==1，匹配URI_YPANDIAN
			activity.getContentResolver().registerContentObserver(
					PanDianPrivoder.URI_YPANDIAN, true, myContentObserver);
			break;
		default:
			break;
		}
	}

	/**
	 * 反注册监听
	 */
	public void unRegisterContentObserver() {
		activity.getContentResolver().unregisterContentObserver(
				myContentObserver);
	}

	/**
	 * 监听数据库的数据更新
	 */
	private class MyContentObserver extends ContentObserver {

		public MyContentObserver(Handler handler) {
			super(handler);

		}

		// 如果数据库改变会在这个方法通知
		@Override
		public void onChange(boolean selfChange, Uri uri) {

			super.onChange(selfChange, uri);

			queryDataAndRefresh();
		}
	}

}
