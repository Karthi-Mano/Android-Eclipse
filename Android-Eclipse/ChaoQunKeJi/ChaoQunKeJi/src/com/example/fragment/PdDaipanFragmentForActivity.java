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
	M6eReader RFID;// RFID类
	M6eReader.OnEPCsListener listen;// RFID模块工作在触发模式下，触发以后需要对扫描到的数据的进行监听
	private MyAdapter myAdapter;
	private boolean isLinked = false;

	private TextView tv_pd_commit;
	private ProgressBar pb_pd_commit;
	private int isUpload;// 0未提交，1提交

	private long startTime;
	private long stopTime;

	private int qrCount;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		activity = (PanDianActivity) getActivity();
		isUpload = activity.isUploaddd;// 得到activity中的isUploaddd数据

		fixedThreadPool = Executors.newFixedThreadPool(

		Runtime.getRuntime().availableProcessors()

		);
		
		if (!TextUtils.isEmpty(InventorActivity.modelCode)
				&& InventorActivity.MODEL
						.equals(InventorActivity.modelCode)) {
			
		} else {

			RFID = M6eReader.GetUHFReader();//初始化扫描设备
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
		textView2.setText(String.format("停止扫描(%d)", qrCount));

		pb_pd_commit = (ProgressBar) view.findViewById(R.id.pb_pd_commit);
		tv_pd_commit = (TextView) view.findViewById(R.id.tv_pd_commit);

		switch (isUpload) {
		case 0:// 如果isupload==0，匹配URI_PANDIAN
			lv_pandainfg_daipan.setVisibility(View.VISIBLE);
			kaishi.setVisibility(View.VISIBLE);
			tingzhi.setVisibility(View.GONE);
			break;
		case 1:// 如果isupload==1，匹配URI_YPANDIAN
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

		// 清空冗余集合
		listString.clear();

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

				Cursor cursor = null;

				switch (isUpload) {
				case 0:// 如果isupload==0，匹配URI_PANDIAN

					// 查询数据库，未盘点查询对应的表,得到Cursor
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

					System.out.println("匹配了" + PanDianPrivoder.URI_PANDIAN);
					break;
				case 1:// 如果isupload==1，匹配URI_YPANDIAN

					/*
					 * // 查询数据库，已盘点查询对应的表,得到Cursor cursor = activity
					 * .getContentResolver()
					 * .query(PanDianPrivoder.URI_YPANDIAN, null,
					 * PanDianOpenHelper.PanDianTable.ISTATE + "=?" + " and " +
					 * PanDianOpenHelper.PanDianTable.ICHECKVOUCH + "=?", new
					 * String[] {activity.kTYPE, activity.iCHECKVOUCH + "" },
					 * null);
					 * 
					 * 
					 * System.out.println("匹配了" + PanDianPrivoder.URI_YPANDIAN);
					 * System.out.println("盘亏数量：     "+cursor.getCount());
					 * System.out.println("盘亏数量：     "+cursor.getCount());
					 * System.out.println("盘亏数量：     "+cursor.getCount());
					 * System.out.println("盘亏数量：     "+cursor.getCount());
					 * System.out.println("盘亏数量：     "+cursor.getCount());
					 * System.out.println("盘亏数量：     "+cursor.getCount());
					 */
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
						lv_pandainfg_daipan.setAdapter(myAdapter);
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

	@Override
	public void onPause() {
		listString.clear();
		if (isLinked) {// 连接 true
			
			
			if (!TextUtils.isEmpty(InventorActivity.modelCode)
					&& InventorActivity.MODEL
							.equals(InventorActivity.modelCode)) {
				
				activity.stop();//停止扫描
				
			} else {

				RFID.DisConnect();// 关闭连接
			}
			
			
			// 复原按钮
			kaishi.setVisibility(View.VISIBLE);

			tingzhi.setVisibility(View.GONE);

		}

		super.onPause();
	}

	private void initEvent() {

		// 开始盘点按钮的监听事件
		kaishi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				
				 if(activity.wCount<1){
					 showCustomToast("已盘点完成...");
					 return;
				 }
				 
				
				qrCount = 0;
				textView2.setText(String.format("停止扫描(%d)", qrCount));
				listString.clear();
				startTime = System.currentTimeMillis();

				kaishi.setVisibility(View.GONE);

				tingzhi.setVisibility(View.VISIBLE);
				showCustomToast("开始扫描...");
				// 开始扫描
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
									System.out.println("开始扫描");
								} else {
									Toast.makeText(getActivity(), "读取失败",
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
		// 停止盘点按钮的监听事件
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

							//清除过滤集合，标签可以重新扫描
							InventorActivity.epc_tid.clear();
							InventorActivity.epc_notid.clear();
							
							for (String str : listString) {
								System.out.println("扫描到的Tid:"+str);
							}

						} else {
							int r = RFID.TriggerStop();

							if (r == 0) {
								System.out.println("停止扫描停止扫描停止扫描停止扫描停止扫描停止扫描");

								getActivity().runOnUiThread(new Runnable() {

									@Override
									public void run() {

										showCustomToast("停止扫描...");
									}
								});

								for (String str : listString) {
									System.out.println(str);
								}

							} else {
								System.out.println("停止失败停止失败停止失败停止失败停止失败停止失败");
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
				System.out.println("扫描的tiddd："+str);
				
				final String tiddd = str.replace(" ", "");
				
				addContanerAndRefresh(tiddd);

			}
		});
		
		

		// 扫描取得TID
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
						isabc = true;// 存在
					}

				}
				if (!isabc) {
					if (tid.equals("1234")) {

					}
					
					
					else {// 去重后的结果
						
						
						
						addContanerAndRefresh(tid);

						
					}

				}
			}

			
		};

	}
	
	
	
	private void addContanerAndRefresh(String tid) {
		// 去重后的结果加入集合
		listString.add(tid);
		System.out.println("扫描到的tid："
				+ tid);
		
		
		System.out.println("集合中的数据长度：" + listString.size());

		// 处理扫描后的数据

		// 更新数据库，将未盘变为已经盘
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

		qrCount += updateCount; // 这是简写
		System.out.println("qrCountqrCountqrCount:" + qrCount);

		fixedThreadPool.execute(new Runnable() {

			@Override
			public void run() {

				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {

						textView2.setText(String.format(
								"停止扫描(%d)", qrCount));
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

	/** 显示自定义Toast提示(来自String) **/
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
