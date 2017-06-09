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
	private int isUpload;// 0δ�ύ��1�ύ

	private TextView tv_pd_commit;
	private ProgressBar pb_pd_commit;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		activity = (PanDianActivity) getActivity();
		isUpload = activity.isUploaddd;// �õ�activity�е�isUploaddd����
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
		 * // �ж�adpter�Ƿ���� if (myAdapter != null) { // ˢ��adapter
		 * myAdapter.getCursor().requery(); return; }
		 */

		// ��ѯ���ݿ���ʾ����

		new Thread(new Runnable() {

			@Override
			public void run() {

				// ��ѯ���ݿ⣬���̵��ѯ��Ӧ�ı�,�õ�Cursor
				final Cursor cursor = activity.getContentResolver().query(
						PanDianPrivoder.URI_YPANDIAN,
						null,
						PanDianOpenHelper.PanDianTable.ISTATE + "=?" + " and "
								+ PanDianOpenHelper.PanDianTable.ICHECKVOUCH
								+ "=?",
						new String[] { activity.kTYPE,
								activity.iCHECKVOUCH + "" }, null);
				// ˢ�½���
				activity.runOnUiThread(new Runnable() {

					@Override
					public void run() {

						myAdapter = new MyAdapter(activity, cursor);

						// ����adapter
						lv_pandian_all.setAdapter(myAdapter);
					}
				});

			}
		}).start();
	}

	/**
	 * CursorAdapter��ʾ����
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
					.getColumnIndex(PanDianOpenHelper.PanDianTable.CPOSITION));// ���λ��
			
			String jigoudian = c.getString(c
					.getColumnIndex(PanDianOpenHelper.PanDianTable.DEPARTMENT));// ��������
			
			String ccode = c.getString(c
					.getColumnIndex(PanDianOpenHelper.PanDianTable.CCODE));// ccode
			
			String jiekuanren = c.getString(c
					.getColumnIndex(PanDianOpenHelper.PanDianTable.CCUSTOMER));// �����
			
			

			String a = cunfangweizhi + " /" + jigoudian + " /"+ ccode + " /" + jiekuanren ;

			String leixing = c.getString(c
					.getColumnIndex(PanDianOpenHelper.PanDianTable.CCLTYPE));// Ȩ֤����
			
			String quanzhengbianhao = c.getString(c
					.getColumnIndex(PanDianOpenHelper.PanDianTable.CCLCODE));// Ȩ֤���
			
			String zhiquanren = c.getString(c
					.getColumnIndex(PanDianOpenHelper.PanDianTable.CCOLLATERAL));//zhiquanren
			
			String zhuangtai = c.getString(c
					.getColumnIndex(PanDianOpenHelper.PanDianTable.IWARRANTS));// ��ʶ

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
	 * ע�����
	 */
	public void registerContentObserver() {
		activity.getContentResolver().registerContentObserver(
				PanDianPrivoder.URI_YPANDIAN, true, myContentObserver);
	}

	/**
	 * ��ע�����
	 */
	public void unRegisterContentObserver() {
		activity.getContentResolver().unregisterContentObserver(
				myContentObserver);
	}

	/**
	 * �������ݿ�����ݸ���
	 */
	private class MyContentObserver extends ContentObserver {

		public MyContentObserver(Handler handler) {
			super(handler);

		}

		// ������ݿ�ı�����������֪ͨ
		@Override
		public void onChange(boolean selfChange, Uri uri) {

			super.onChange(selfChange, uri);

			queryDataAndRefresh();
		}
	}

}
