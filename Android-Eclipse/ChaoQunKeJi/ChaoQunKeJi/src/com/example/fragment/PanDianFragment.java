package com.example.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.activity.PanDianActivity;
import com.example.activity.TabActivityty;
import com.example.dbhelper.PanDianOpenHelper;
import com.example.entity.PanDianBean;
import com.example.entity.PanDianBean.Result;
import com.example.entity.YpdBean;
import com.example.provider.BiaoDanPrivoder;
import com.example.provider.PanDianPrivoder;
import com.example.service.PreferencesService;
import com.example.test.R;
import com.google.gson.Gson;

/**
 * 盘点Fragment
 * 
 * @author Wjz
 * 
 */
public class PanDianFragment extends Fragment {

	private Gson gson;
	private PanDianBean panDianBean;
	private ListView lv_liebiao;
	private TabActivityty mainActivity;
	private List<Result> panDianListData = new ArrayList<PanDianBean.Result>();
	private MyAdapte myAdapte;
	public String nTYPE = "1";
	public String yTYPE = "3";
	private int yNum;
	private int selectIndex = -1;
	private ExecutorService fixedThreadPool;
	PreferencesService service;
	private List<String> listc = new ArrayList<String>();
	boolean isUpload = false;
	private ProgressBar pb_pd_commit;
	private ProgressDialog pd;
	private AlertDialog alertDialog;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case -1:
				alertDialog.dismiss();
				break;

			default:
				break;
			}

			super.handleMessage(msg);
		}

	};
	private TextView tv_pd_commit;
	private String str_url;
	private RelativeLayout 		rl_pd_bg;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mainActivity = (TabActivityty) getActivity();

		fixedThreadPool = Executors.newFixedThreadPool(

		Runtime.getRuntime().availableProcessors()

		);

		/*
		 * System.out.println(selectIndex + "  " + selectIndex + "  " +
		 * selectIndex + "  " + selectIndex + "  ");
		 */

		service = new PreferencesService(mainActivity);
		Map<String, String> param = service.getPreferences();
		str_url = param.get("app_address");

		// 清空已盘表的数据
		mainActivity.getContentResolver().delete(PanDianPrivoder.URI_YPANDIAN,

		null, null

		);

		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.layout_pandian, container, false);
		lv_liebiao = (ListView) view.findViewById(R.id.lv_pf_pandian);
		pb_pd_commit = (ProgressBar) view.findViewById(R.id.pb_pd_commit);
		tv_pd_commit = (TextView) view.findViewById(R.id.tv_pd_commit);
		rl_pd_bg = (RelativeLayout) view.findViewById(R.id.rl_pd_bg);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		initData();
		initEvent();
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		System.out.println("显示的selectIndex" + "   " + selectIndex);
		// 刷新界面 -- selectIndex
		if (selectIndex != -1) {
			Result result = panDianListData.get(selectIndex);

			int iCHECKVOUCH = result.ICHECKVOUCH;
			int iSUPLOAD = result.ISUPLOAD;
			System.out.println("iSUPLOAD   " + iSUPLOAD);

			if (iSUPLOAD == 1) {
				super.onStart();
				return;
			}

			Cursor cursor = mainActivity.getContentResolver().query(
					PanDianPrivoder.URI_PANDIAN, null,
					PanDianOpenHelper.PanDianTable.ISTATE + "=?" + " and " +

					PanDianOpenHelper.PanDianTable.ICHECKVOUCH + "=?",
					new String[] { yTYPE, iCHECKVOUCH + "" }, null);

			int getCount = cursor.getCount();// 已经盘
			int allnum = panDianListData.get(selectIndex).ALLNUM;

			ContentValues values = new ContentValues();
			values.put(PanDianOpenHelper.BiaoDanTable.WNUM, (allnum - getCount)
					+ "");
			
			values.put(PanDianOpenHelper.BiaoDanTable.YNUM, getCount + "");
			getContext().getContentResolver().update(
					BiaoDanPrivoder.URI_BIAODAN, values,
					PanDianOpenHelper.BiaoDanTable.ICHECKVOUCH + "=?",
					new String[] { iCHECKVOUCH + "" });

			panDianListData.get(selectIndex).WNUM = allnum - getCount;
			panDianListData.get(selectIndex).YNUM = getCount;

			// 查询数据 刷新adapter

			myAdapte.notifyDataSetChanged();
			cursor.close();
		}
		super.onStart();
	}

	@Override
	public void onDestroy() {

		listc.clear();
		super.onDestroy();
	}

	/*
	 * 生命周期结束
	 */

	/**
	 * 初始化数据
	 */
	private void initData() {
		// 请求网络，拉去数据
		getDataFromNet();

		/*
		 * // 清空盘点表的数据
		 * 
		 * mainActivity.getContentResolver().delete(PanDianPrivoder.URI_PANDIAN,
		 * null, null);
		 */

		// 初始化适配器
		myAdapte = new MyAdapte();
		// 为listView设置适配器
		lv_liebiao.setAdapter(myAdapte);

	}

	private void getDataFromNet() {
		// 请求网络，拉去数据
		RequestParams requestParams = new RequestParams(str_url
				+ "/bank_phone/warrant/getCheckVouch");
		// System.out.println(requestParams);
		x.http().get(requestParams, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub
				System.out.println(arg0);
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				// TODO Auto-generated method stub
				System.out.println(arg0);
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub
				System.out.println("onFinished");
			}

			@Override
			public void onSuccess(String responseInfo) {

				if (pd !=null) {
					pd.dismiss();
				}
				
				pb_pd_commit.setVisibility(View.GONE);
				tv_pd_commit.setVisibility(View.GONE);
				rl_pd_bg.setVisibility(View.GONE);
				String jsonData = "{result:" + responseInfo + "}";
				System.out.println(jsonData);
				panDianBean = parseJson(jsonData);
				new Thread(

				new Runnable() {

					@Override
					public void run() {
						processData(panDianBean);

					}
				}

				).start();

			}

		});

	}

	/**
	 * 添加事件
	 */
	private void initEvent() {

		lv_liebiao.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Result result = panDianListData.get(position);
				int iCHECKVOUCH = result.ICHECKVOUCH;
				service = new PreferencesService(getActivity());
				Map<String, String> params = service.getPreferences();
				String iid = params.get("iid");
				int yNum = result.YNUM;
				int wNum = result.WNUM;
				int kNum = result.KNUM;
				int isUploaddd = result.ISUPLOAD;
				if (isUploaddd == 1) {

					Intent intent = new Intent(mainActivity,
							PanDianActivity.class);
					intent.putExtra("iCHECKVOUCH", iCHECKVOUCH);
					intent.putExtra("iid", iid);
					intent.putExtra("yNum", yNum);
					intent.putExtra("kNum", kNum);
					intent.putExtra("wNum", wNum);
					intent.putExtra("position", position);
					intent.putExtra("isUploaddd", isUploaddd);
					// startActivityForResult(intent, 0);

					mainActivity.startActivityForResult(intent, 0, null);

				} else {

					if (selectIndex == -1 || selectIndex == position) {

						selectIndex = position;
						System.out.println("点击的selectIndex" + "   "
								+ selectIndex);
						// 获取数据为item设置数据

						Intent intent = new Intent(mainActivity,
								PanDianActivity.class);
						intent.putExtra("iCHECKVOUCH", iCHECKVOUCH);
						intent.putExtra("iid", iid);
						intent.putExtra("yNum", yNum);
						intent.putExtra("wNum", wNum);
						intent.putExtra("position", position);
						intent.putExtra("isUploaddd", isUploaddd);
						// startActivityForResult(intent, 0);

						mainActivity.startActivityForResult(intent, 0, null);
					} else {// selectIndex!=-1

						// 不能进入其他界面
						Result resultIndex = panDianListData.get(selectIndex);
						String cNAME = resultIndex.CNAME;

						AlertDialog.Builder builder = new AlertDialog.Builder(
								mainActivity);
						builder.setTitle("提示").setMessage("请先提交:  " + cNAME);

						alertDialog = builder.create();

						alertDialog.show();

						Message msg = Message.obtain();
						msg.what = -1;
						handler.sendMessageDelayed(msg, 3000);

					}
				}

			}

		});

	}

	/**
	 * 解析Json
	 * 
	 * @param jsonData
	 * @return 返回解析后的Bean
	 */
	private PanDianBean parseJson(String jsonData) {

		if (gson == null) {
			gson = new Gson();
		}
		return gson.fromJson(jsonData, PanDianBean.class);
	}

	/**
	 * 数据处理 1.过滤数据 2.为数据做缓存
	 * 
	 * @param panDianBean
	 */
	private void processData(PanDianBean panDianBean) {
		panDianListData.clear();
		try {
		// 查询m_biaodan,放入集合
		Cursor c = getContext().getContentResolver().query(
				BiaoDanPrivoder.URI_BIAODAN,
				new String[] { PanDianOpenHelper.BiaoDanTable.ICHECKVOUCH },
				null, null, null);
		
		
		
		
		while (c.moveToNext()) {
			String icheckvouch = c.getString(0);
			listc.add(icheckvouch);
		}

		// 1.如果isupload==1已经提交过，不存入数据库

		List<Result> ress = panDianBean.result;

		for (final Result res : ress) {
			boolean isExist = false;// false 表中不存在 true 表中存在
			if (res.ISUPLOAD == 1) {// 已经提交

				mainActivity.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						addListViewData(res);

					}
				});

			} else {// 未提交

				// 2.如果isupload==0，未提交，去除重复插入数据库
				String iCHECKVOUCH = res.ICHECKVOUCH + "";

				if (c.getCount() == 0) {// 表中未存过数据
					ContentValues values = new ContentValues();
					values.put(PanDianOpenHelper.BiaoDanTable.ALLNUM,
							res.ALLNUM);
					values.put(PanDianOpenHelper.BiaoDanTable.CNAME, res.CNAME);
					values.put(PanDianOpenHelper.BiaoDanTable.ICHECKVOUCH,
							res.ICHECKVOUCH);
					values.put(PanDianOpenHelper.BiaoDanTable.ISUPLOAD,
							res.ISUPLOAD);
					values.put(PanDianOpenHelper.BiaoDanTable.WNUM, res.WNUM);
					values.put(PanDianOpenHelper.BiaoDanTable.YNUM, res.YNUM);
					values.put(PanDianOpenHelper.BiaoDanTable.KNUM, res.KNUM);
					values.put(PanDianOpenHelper.BiaoDanTable.CCODE, res.CCODE);
					getContext().getContentResolver().insert(
							BiaoDanPrivoder.URI_BIAODAN, values);

				} else {// 表中存有数据

					for (String strc : listc) {

						if (strc.equals(iCHECKVOUCH)) {// 与表中数据相同
							isExist = true;
						}
					}
					if (!isExist) {
						// 不同，将该条数据存入数据库
						ContentValues values = new ContentValues();
						values.put(PanDianOpenHelper.BiaoDanTable.ALLNUM,
								res.ALLNUM);
						values.put(PanDianOpenHelper.BiaoDanTable.CNAME,
								res.CNAME);
						values.put(PanDianOpenHelper.BiaoDanTable.ICHECKVOUCH,
								res.ICHECKVOUCH);
						values.put(PanDianOpenHelper.BiaoDanTable.ISUPLOAD,
								res.ISUPLOAD);
						values.put(PanDianOpenHelper.BiaoDanTable.WNUM,
								res.WNUM);
						values.put(PanDianOpenHelper.BiaoDanTable.YNUM,
								res.YNUM);
						values.put(PanDianOpenHelper.BiaoDanTable.KNUM,
								res.KNUM);
						values.put(PanDianOpenHelper.BiaoDanTable.CCODE,
								res.CCODE);
						getContext().getContentResolver().insert(
								BiaoDanPrivoder.URI_BIAODAN, values);

					}
				}

			}

		}
		c.close();
		// 列表的数据处理
		// addListViewData(panDianBean);

		// 从数据库取数据

		// 查询m_biaodan,放入集合
		Cursor cursor = getContext().getContentResolver().query(
				BiaoDanPrivoder.URI_BIAODAN,
				new String[] {

				PanDianOpenHelper.BiaoDanTable.ALLNUM,
						PanDianOpenHelper.BiaoDanTable.CNAME,
						PanDianOpenHelper.BiaoDanTable.ICHECKVOUCH,
						PanDianOpenHelper.BiaoDanTable.ISUPLOAD,
						PanDianOpenHelper.BiaoDanTable.WNUM,
						PanDianOpenHelper.BiaoDanTable.YNUM,
						PanDianOpenHelper.BiaoDanTable.KNUM,
						PanDianOpenHelper.BiaoDanTable.CCODE }, null, null,
				null);

		while (cursor.moveToNext()) {
			// 重新组织数据

			final Result result = panDianBean.new Result();

			result.ALLNUM = Integer.parseInt(cursor.getString(0));
			result.CNAME = cursor.getString(1);
			result.ICHECKVOUCH = Integer.parseInt(cursor.getString(2));
			result.ISUPLOAD = Integer.parseInt(cursor.getString(3));
			result.WNUM = Integer.parseInt(cursor.getString(4));
			result.YNUM = Integer.parseInt(cursor.getString(5));
			result.KNUM = Integer.parseInt(cursor.getString(6));
			result.CCODE = cursor.getString(7);
			mainActivity.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					addListViewData(result);

				}
			});

		}

		cursor.close();
		
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 向panDianListData里添加数据，adapter的集合
	 * 
	 * @param res
	 */
	private void addListViewData(Result res) {

		// 向panDianListData中追加数据

		panDianListData.add(res);
		// panDianListData = panDianBean.result;

		// 刷新界面
		myAdapte.notifyDataSetChanged();
	}

	/**
	 * MyAdapte
	 * 
	 * @author Wjz
	 * 
	 */
	public class MyAdapte extends BaseAdapter {

		@Override
		public int getCount() {
			System.out.println(panDianListData.size());
			if (panDianListData != null) {

				return panDianListData.size();
			}
			return 0;

		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = View.inflate(mainActivity,
						R.layout.item_pf_pandian, null);
				viewHolder = new ViewHolder();
				viewHolder.iv_icon = (ImageView) convertView
						.findViewById(R.id.iv_item_pf_icon);
				viewHolder.tv_bili = (TextView) convertView
						.findViewById(R.id.tv_item_pf_bili);
				viewHolder.tv_label = (TextView) convertView
						.findViewById(R.id.tv_item_pf_label);
				// viewHolder.tv_mubiao = (TextView)
				// convertView.findViewById(R.id.tv_item_pf_mubiao);
				viewHolder.tv_yipan = (TextView) convertView
						.findViewById(R.id.tv_item_pf_yipan);
				viewHolder.tv_weipan = (TextView) convertView
						.findViewById(R.id.tv_item_pf_weipan);
				viewHolder.tv_confirm = (TextView) convertView
						.findViewById(R.id.tv_pd_confirm);
				convertView.setTag(viewHolder);
			} else {

				viewHolder = (ViewHolder) convertView.getTag();
			}

			// 获取数据为item设置数据

			final Result result = panDianListData.get(position);

			final int mNum = result.WNUM;
			final int yNum = result.YNUM;

			int allNum = result.ALLNUM;

			int a = yNum * 100 / allNum;

			if (a > 66) {
				viewHolder.iv_icon
						.setImageResource(R.drawable.pandian_bili_high);
			} else if (a < 33) {
				viewHolder.iv_icon
						.setImageResource(R.drawable.pandian_bili_low);
			} else {
				viewHolder.iv_icon
						.setImageResource(R.drawable.pandian_bili_normal);
			}

			viewHolder.tv_bili.setText(a + "%");
			viewHolder.tv_label.setText(result.CNAME + " / " + result.CCODE);// 2016年3月盘点测试1
			// viewHolder.tv_mubiao.setText(result.ALLNUM+"");// 目标

			viewHolder.tv_weipan.setText((result.WNUM + result.KNUM) + "");// 未盘
			viewHolder.tv_yipan.setText(result.YNUM + "");// 已盘
			final int icheckvouch = result.ICHECKVOUCH;
			int iSUPLOAD = result.ISUPLOAD;// 0未上传过 1上传过

			if (iSUPLOAD == 0) {// 0未上传过 1上传过
				if (selectIndex == -1) {// 未点击条目
					isUpload = false;
				} else {
					if (selectIndex == position) {// 与点击条目相等
						isUpload = false;

					} else {
						isUpload = true;
					}
				}
			} else {
				isUpload = true;
			}

			viewHolder.tv_confirm.setEnabled(!isUpload);

			// 给tv添加提交的点击事件

			// 已盘为0时请求数据

			viewHolder.tv_confirm.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// System.out.println("提交按钮被点击了" + position);
					// commitDataForNet(icheckvouch,strJson);

					// 查询数据库
					Cursor cursor = mainActivity
							.getContentResolver()
							.query(PanDianPrivoder.URI_PANDIAN,
									new String[] { PanDianOpenHelper.PanDianTable.IID },
									PanDianOpenHelper.PanDianTable.ISTATE
											+ "=?"
											+ " and "
											+ PanDianOpenHelper.PanDianTable.ICHECKVOUCH
											+ "=?",
									new String[] { yTYPE,
											result.ICHECKVOUCH + "" }, null);

					YpdBean ypdBean = new YpdBean();
					service = new PreferencesService(getActivity());
					Map<String, String> params = service.getPreferences();
					ypdBean.userid = params.get("iid");
					ypdBean.icheckvouch = result.ICHECKVOUCH + "";
					while (cursor.moveToNext()) {

						ypdBean.iid.add(cursor.getString(0));
						// System.out.println("查询到的数据" +
						// cursor.getString(0));

					}

					final String json = gson.toJson(ypdBean);

					// System.out.println(json);

					cursor.close();

					AlertDialog.Builder builder = new AlertDialog.Builder(
							mainActivity);

					if (mNum == 0) {// 已经盘完
						builder.setTitle("提示");
						builder.setMessage("已全部完成盘点，可以提交！");
					} else {// 未经盘完
						builder.setTitle("警告");
						builder.setMessage("当前仍有 " + mNum
								+ " 条记录未完成盘点，提交后将无法再继续盘点，是否提交 ？");
					}

					builder.setPositiveButton("是",
							new AlertDialog.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									// 提交数据
									commitDataForNet(json);
									// 删除表单表对应的数据项
									mainActivity
											.getContentResolver()
											.delete(BiaoDanPrivoder.URI_BIAODAN,
													PanDianOpenHelper.BiaoDanTable.ICHECKVOUCH
															+ "=?",
													new String[] { result.ICHECKVOUCH
															+ ""

													});
									// 删除盘点表对应的数据

									mainActivity
											.getContentResolver()
											.delete(PanDianPrivoder.URI_PANDIAN,

													PanDianOpenHelper.BiaoDanTable.ICHECKVOUCH
															+ "=?",
													new String[] { result.ICHECKVOUCH
															+ "" }

											);

									new Thread(new Runnable() {

										@Override
										public void run() {

											mainActivity
													.runOnUiThread(new Runnable() {

													

														@Override
														public void run() {
																
															
															//TODO 显示进度条对话框
															
															/*pb_pd_commit
																	.setVisibility(View.VISIBLE);
															tv_pd_commit
																	.setVisibility(View.VISIBLE);
															rl_pd_bg.setVisibility(View.VISIBLE);*/
															
															pd = new ProgressDialog(mainActivity);
															pd.setTitle("提示：");
														    pd.setMessage("正在提交，请稍后...");
														  
														    pd.show();
														}
													});
										
											SystemClock.sleep(3000);
											getDataFromNet();
										}
									}).start();

								}
							})
							.setNegativeButton("否",
									new AlertDialog.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {

										}
									}).show();// 显示对话框

				}
			});

			return convertView;
		}
	}

	/**
	 * 提交数据
	 */
	public void commitDataForNet(String strJson) {

		System.out.println("已经盘点好的json数据" + strJson);
		RequestParams requestParams = new RequestParams(str_url
				+ "/bank_phone/warrant/checkOver");
		requestParams.addParameter("str", strJson);
		x.http().post(requestParams, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String arg0) {
				System.out.println(arg0);
			}
		});

	}

	/**
	 * ListView的组件
	 * 
	 * @author Wjz
	 */
	public class ViewHolder {
		ImageView iv_icon;
		TextView tv_bili;

		TextView tv_label;

		TextView tv_yipan;
		TextView tv_weipan;

		TextView tv_confirm;
	}

}
