package com.example.fragment;

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

import com.example.activity.PanDianActivity;
import com.example.dbhelper.PanDianOpenHelper;
import com.example.provider.PanDianPrivoder;
import com.example.test.R;

public class PdAllFragmentForActivity extends Fragment {

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
		View view = View.inflate(activity, R.layout.fragment_pandian_all, null);
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
	public void onResume() {
		initData();
		super.onResume();
	}

	@Override
	public void onDestroy() {
		unRegisterContentObserver();
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

				// 查询数据库，已盘点查询对应的表,得到Cursor
				final Cursor cursor = activity.getContentResolver().query(
						PanDianPrivoder.URI_YPANDIAN,
						null,
						PanDianOpenHelper.PanDianTable.ISTATE + "=?" + " and "
								+ PanDianOpenHelper.PanDianTable.ICHECKVOUCH
								+ "=?",
						new String[] { activity.kTYPE,
								activity.iCHECKVOUCH + "" }, null);
				// 刷新界面
				activity.runOnUiThread(new Runnable() {

					@Override
					public void run() {

						myAdapter = new MyAdapter(activity, cursor);

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

			TextView all_1 = (TextView) view.findViewById(R.id.tv_item_all_1);
			TextView all_2 = (TextView) view.findViewById(R.id.tv_item_all_2);

			String cunfangweizhi = c.getString(c
					.getColumnIndex(PanDianOpenHelper.PanDianTable.CPOSITION));// 存放位置
			
			String jigoudian = c.getString(c
					.getColumnIndex(PanDianOpenHelper.PanDianTable.DEPARTMENT));// 机构网点
			
			String ccode = c.getString(c
					.getColumnIndex(PanDianOpenHelper.PanDianTable.CCODE));// ccode
			
			String jiekuanren = c.getString(c
					.getColumnIndex(PanDianOpenHelper.PanDianTable.CCUSTOMER));// 借款人
			
			

			String a = cunfangweizhi + " /" + jigoudian + " /"+ ccode + " /" + jiekuanren ;

			String leixing = c.getString(c
					.getColumnIndex(PanDianOpenHelper.PanDianTable.CCLTYPE));// 权证类型
			
			String quanzhengbianhao = c.getString(c
					.getColumnIndex(PanDianOpenHelper.PanDianTable.CCLCODE));// 权证编号
			
			String zhiquanren = c.getString(c
					.getColumnIndex(PanDianOpenHelper.PanDianTable.CCOLLATERAL));//zhiquanren
			
			String zhuangtai = c.getString(c
					.getColumnIndex(PanDianOpenHelper.PanDianTable.IWARRANTS));// 标识

			String b = leixing + " /" + quanzhengbianhao + " /" +zhiquanren+ " /" +zhuangtai;
			
			
			
			all_1.setText(a);
			all_2.setText(b);

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
		activity.getContentResolver().registerContentObserver(
				PanDianPrivoder.URI_YPANDIAN, true, myContentObserver);
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
