package com.example.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyuan.view.HandyTextView;
import com.cetc7.M6e.M6eReader;
import com.example.activity.PanDianActivity;
import com.example.dbhelper.PanDianOpenHelper;
import com.example.entity.SearchData1.Result;
import com.example.provider.PanDianPrivoder;
import com.example.test.R;
import com.example.testuhfapi.InventorActivity;
import com.example.testuhfapi.InventorActivity.TidCallBack;

public class PdDaipanFragmentForActivity extends Fragment {
	private List<String> listString = new ArrayList<String>();
	private PanDianActivity activity;
	private ListView lv_pandainfg_daipan;

	private ExecutorService fixedThreadPool;
	M6eReader RFID;// RFID��
	M6eReader.OnEPCsListener listen;// RFIDģ�鹤���ڴ���ģʽ�£������Ժ���Ҫ��ɨ�赽�����ݵĽ��м���
	private MyAdapter myAdapter;
	private boolean isLinked = false;

	private TextView tv_pd_commit;
	private ProgressBar pb_pd_commit;
	private int isUpload;// 0δ�ύ��1�ύ

	private long startTime;
	private long stopTime;

	private int qrCount;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		activity = (PanDianActivity) getActivity();
		isUpload = activity.isUploaddd;// �õ�activity�е�isUploaddd����

		fixedThreadPool = Executors.newFixedThreadPool(

		Runtime.getRuntime().availableProcessors()

		);
		
		if (!TextUtils.isEmpty(InventorActivity.modelCode)
				&& InventorActivity.MODEL
						.equals(InventorActivity.modelCode)) {
			
		} else {

			RFID = M6eReader.GetUHFReader();//��ʼ��ɨ���豸
		}

		registerContentObserver();
		super.onCreate(savedInstanceState);
	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = View.inflate(activity, R.layout.fragment_pandian_daipan,
				null);

		lv_pandainfg_daipan = (ListView) view
				.findViewById(R.id.lv_pandainfg_daipan);

		/*
		 * private Button kaishi; private Button tingzhi;
		 */

		kaishi = (RelativeLayout) view.findViewById(R.id.tv_kaishisaomiao);
		tingzhi = (RelativeLayout) view.findViewById(R.id.tv_tingshisaomiao);

		textView2 = (TextView) view.findViewById(R.id.textView2);
		textView2.setText(String.format("ֹͣɨ��(%d)", qrCount));

		pb_pd_commit = (ProgressBar) view.findViewById(R.id.pb_pd_commit);
		tv_pd_commit = (TextView) view.findViewById(R.id.tv_pd_commit);

		switch (isUpload) {
		case 0:// ���isupload==0��ƥ��URI_PANDIAN
			lv_pandainfg_daipan.setVisibility(View.VISIBLE);
			kaishi.setVisibility(View.VISIBLE);
			tingzhi.setVisibility(View.GONE);
			break;
		case 1:// ���isupload==1��ƥ��URI_YPANDIAN
			lv_pandainfg_daipan.setVisibility(View.GONE);
			kaishi.setVisibility(View.GONE);
			tingzhi.setVisibility(View.GONE);
			break;
		default:
			break;
		}

		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		initData();
		initEvent();
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroy() {

		// ������༯��
		listString.clear();

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

				Cursor cursor = null;

				switch (isUpload) {
				case 0:// ���isupload==0��ƥ��URI_PANDIAN

					// ��ѯ���ݿ⣬δ�̵��ѯ��Ӧ�ı�,�õ�Cursor
					cursor = activity
							.getContentResolver()
							.query(PanDianPrivoder.URI_PANDIAN,
									null,
									PanDianOpenHelper.PanDianTable.ISTATE
											+ "=?"
											+ " and "
											+ PanDianOpenHelper.PanDianTable.ICHECKVOUCH
											+ "=?",
									new String[] { activity.nTYPE,
											activity.iCHECKVOUCH + "" }, PanDianOpenHelper.PanDianTable.CCLCODE+" ASC");

					System.out.println("ƥ����" + PanDianPrivoder.URI_PANDIAN);
					break;
				case 1:// ���isupload==1��ƥ��URI_YPANDIAN

					/*
					 * // ��ѯ���ݿ⣬���̵��ѯ��Ӧ�ı�,�õ�Cursor cursor = activity
					 * .getContentResolver()
					 * .query(PanDianPrivoder.URI_YPANDIAN, null,
					 * PanDianOpenHelper.PanDianTable.ISTATE + "=?" + " and " +
					 * PanDianOpenHelper.PanDianTable.ICHECKVOUCH + "=?", new
					 * String[] {activity.kTYPE, activity.iCHECKVOUCH + "" },
					 * null);
					 * 
					 * 
					 * System.out.println("ƥ����" + PanDianPrivoder.URI_YPANDIAN);
					 * System.out.println("�̿�������     "+cursor.getCount());
					 * System.out.println("�̿�������     "+cursor.getCount());
					 * System.out.println("�̿�������     "+cursor.getCount());
					 * System.out.println("�̿�������     "+cursor.getCount());
					 * System.out.println("�̿�������     "+cursor.getCount());
					 * System.out.println("�̿�������     "+cursor.getCount());
					 */
					break;
				default:
					break;
				}

				final Cursor cursor2 = cursor;

				// ˢ�½���
				activity.runOnUiThread(new Runnable() {

					@Override
					public void run() {

						pb_pd_commit.setVisibility(View.GONE);
						tv_pd_commit.setVisibility(View.GONE);

						myAdapter = new MyAdapter(activity, cursor2);

						// ����adapter
						lv_pandainfg_daipan.setAdapter(myAdapter);
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

	@Override
	public void onPause() {
		listString.clear();
		if (isLinked) {// ���� true
			
			
			if (!TextUtils.isEmpty(InventorActivity.modelCode)
					&& InventorActivity.MODEL
							.equals(InventorActivity.modelCode)) {
				
				activity.stop();//ֹͣɨ��
				
			} else {

				RFID.DisConnect();// �ر�����
			}
			
			
			// ��ԭ��ť
			kaishi.setVisibility(View.VISIBLE);

			tingzhi.setVisibility(View.GONE);

		}

		super.onPause();
	}

	private void initEvent() {

		// ��ʼ�̵㰴ť�ļ����¼�
		kaishi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				
				 if(activity.wCount<1){
					 showCustomToast("���̵����...");
					 return;
				 }
				 
				
				qrCount = 0;
				textView2.setText(String.format("ֹͣɨ��(%d)", qrCount));
				listString.clear();
				startTime = System.currentTimeMillis();

				kaishi.setVisibility(View.GONE);

				tingzhi.setVisibility(View.VISIBLE);
				showCustomToast("��ʼɨ��...");
				// ��ʼɨ��
				new Thread(

				new Runnable() {
					public void run() {

						if (!TextUtils.isEmpty(InventorActivity.modelCode)
								&& InventorActivity.MODEL
								.equals(InventorActivity.modelCode)) {

							activity.multiQueryEpc();
							isLinked = true;
						} else {

							try {
								RFID.Connect();
								isLinked = true;
								int i = RFID.TirggerStart(listen, true);
								if (i == 0) {
									System.out.println("��ʼɨ��");
								} else {
									Toast.makeText(getActivity(), "��ȡʧ��",
											Toast.LENGTH_SHORT).show();
								}

							} catch (InterruptedException e) {
								isLinked = false;
								e.printStackTrace();
							}

						}
					}
				}

				).start();

			}
		});
		// ֹͣ�̵㰴ť�ļ����¼�
		tingzhi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				listString.clear();
				stopTime = System.currentTimeMillis();

				long time = stopTime - startTime;

				if (time < 3000) {
					return;
				}

				kaishi.setVisibility(View.VISIBLE);

				tingzhi.setVisibility(View.GONE);

				new Thread(

				new Runnable() {

					@Override
					public void run() {
						if (!TextUtils.isEmpty(InventorActivity.modelCode)
								&& InventorActivity.MODEL
								.equals(InventorActivity.modelCode)) {

							activity.stop();

							//������˼��ϣ���ǩ��������ɨ��
							InventorActivity.epc_tid.clear();
							InventorActivity.epc_notid.clear();
							
							for (String str : listString) {
								System.out.println("ɨ�赽��Tid:"+str);
							}

						} else {
							int r = RFID.TriggerStop();

							if (r == 0) {
								System.out.println("ֹͣɨ��ֹͣɨ��ֹͣɨ��ֹͣɨ��ֹͣɨ��ֹͣɨ��");

								getActivity().runOnUiThread(new Runnable() {

									@Override
									public void run() {

										showCustomToast("ֹͣɨ��...");
									}
								});

								for (String str : listString) {
									System.out.println(str);
								}

							} else {
								System.out.println("ֹͣʧ��ֹͣʧ��ֹͣʧ��ֹͣʧ��ֹͣʧ��ֹͣʧ��");
							}
						}

					}
				}

				).start();
			}
		});
		
		activity.setCallBackListener(new TidCallBack() {

			@Override
			public void tidCallBack(String str) {
				System.out.println("ɨ���tiddd��"+str);
				
				final String tiddd = str.replace(" ", "");
				
				addContanerAndRefresh(tiddd);

			}
		});
		
		

		// ɨ��ȡ��TID
		listen = new M6eReader.OnEPCsListener() {

			public void onEPCsRecv(ArrayList<String> EPCList) {

			}

			@Override
			public void onEPCsWithTIDRecv(String arg0, String tiddd) {
				// System.out.println("!");
				String tid = tiddd.substring(0, 24);
				boolean isabc = false;

				for (String str : listString) {
					if (str.equals(tid)) {
						isabc = true;// ����
					}

				}
				if (!isabc) {
					if (tid.equals("1234")) {

					}
					
					
					else {// ȥ�غ�Ľ��
						
						
						
						addContanerAndRefresh(tid);

						
					}

				}
			}

			
		};

	}
	
	
	
	private void addContanerAndRefresh(String tid) {
		// ȥ�غ�Ľ�����뼯��
		listString.add(tid);
		System.out.println("ɨ�赽��tid��"
				+ tid);
		
		
		System.out.println("�����е����ݳ��ȣ�" + listString.size());

		// ����ɨ��������

		// �������ݿ⣬��δ�̱�Ϊ�Ѿ���
		ContentValues values = new ContentValues();

		values.put(PanDianOpenHelper.PanDianTable.ISTATE,
				activity.yTYPE);

		int updateCount = getContext()
				.getContentResolver()
				.update

				(PanDianPrivoder.URI_PANDIAN,
						values,
						PanDianOpenHelper.PanDianTable.CRFID
								+ "=?"
								+ " and "
								+ PanDianOpenHelper.PanDianTable.ISTATE
								+ "=?", new

						String[] { tid, activity.nTYPE });

		System.out.println("updateCount:" + updateCount);

		qrCount += updateCount; // ���Ǽ�д
		System.out.println("qrCountqrCountqrCount:" + qrCount);

		fixedThreadPool.execute(new Runnable() {

			@Override
			public void run() {

				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {

						textView2.setText(String.format(
								"ֹͣɨ��(%d)", qrCount));
					}
				});

			}
		});

		queryDataAndRefresh();
	}

	

	MyContentObserver myContentObserver = new MyContentObserver(new Handler());
	private RelativeLayout kaishi;
	private RelativeLayout tingzhi;
	private TextView textView2;

	/**
	 * ע�����
	 */
	public void registerContentObserver() {

		switch (isUpload) {
		case 0:// ���isupload==0��ƥ��URI_PANDIAN
			activity.getContentResolver().registerContentObserver(
					PanDianPrivoder.URI_PANDIAN, true, myContentObserver);
			break;
		case 1:// ���isupload==1��ƥ��URI_YPANDIAN
			activity.getContentResolver().registerContentObserver(
					PanDianPrivoder.URI_YPANDIAN, true, myContentObserver);
			break;
		default:
			break;
		}

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

	/** ��ʾ�Զ���Toast��ʾ(����String) **/
	protected void showCustomToast(String text) {
		View toastRoot = LayoutInflater.from(getActivity()).inflate(
				R.layout.common_toast, null);
		((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(getActivity());
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}

}
