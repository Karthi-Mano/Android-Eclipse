package com.example.fragment;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.xutils.x;

import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import com.baoyuan.view.HandyTextView;
import com.cetc7.M6e.M6eReader;

import com.example.activity.TabActivityty;
import com.example.entity.Information;
import com.example.entity.TAGITEM;
import com.example.fragment.RukuFragment.Chukuadapter;
import com.example.fragment.RukuFragment.ViewHolder;

import com.example.service.PreferencesService;
import com.example.test.R;
import com.example.testuhfapi.InventorActivity;
import com.example.testuhfapi.InventorActivity.TidCallBack;
import com.google.gson.Gson;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

import android.widget.Button;

import android.widget.ListView;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//入库
@SuppressLint("ShowToast")
public class ChuKuFragment extends Fragment implements OnClickListener {
	List<Information> l = new ArrayList<Information>();
	List<String> strlist = new ArrayList<String>();
	Chukuadapter chukuadapter;
	int count = 0;
	private View view;
	Button btn_clean;
	private RelativeLayout relativeLayout;
	private RelativeLayout tv_kaishisaomiao;
	private RelativeLayout tv_tingshisaomiao;
	private Button btn_sure;
	private String str_url;
	M6eReader RFID;// RFID类
	private long startTime;
	private long stopTime;
	private TabActivityty activity;
	ListView lv;
	PreferencesService service;
	private int show = 0;
	private ExecutorService fixedThreadPool;
	M6eReader.OnEPCsListener listen;// RFID模块工作在触发模式下，触发以后需要对扫描到的数据的进行监听

	@Override
	public void onDestroy() {
		if (!TextUtils.isEmpty(InventorActivity.modelCode)
				&& InventorActivity.MODEL.equals(InventorActivity.modelCode)) {

			InventorActivity.epc_tid.clear();
			InventorActivity.epc_notid.clear();
		}

		strlist.clear();
		RFID.DisConnect();
		super.onDestroy();
	}

	boolean NottoDisConnect = false;

	@Override
	public void onPause() {
		strlist.clear();
		if (!TextUtils.isEmpty(InventorActivity.modelCode)
				&& InventorActivity.MODEL.equals(InventorActivity.modelCode)) {

			InventorActivity.epc_tid.clear();
			InventorActivity.epc_notid.clear();
		}

		if (!NottoDisConnect) {
			RFID.DisConnect();
		}

		super.onPause();
	}

	@Override
	public void onResume() {
		if (!TextUtils.isEmpty(InventorActivity.modelCode)
				&& InventorActivity.MODEL.equals(InventorActivity.modelCode)) {

			InventorActivity.epc_tid.clear();
			InventorActivity.epc_notid.clear();
		}

		strlist.clear();
		NottoDisConnect = false;
		super.onResume();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		activity = (TabActivityty) getActivity();
		service = new PreferencesService(getActivity());
		Map<String, String> param = service.getPreferences();
		str_url = param.get("app_address");
		strlist.clear();
		RFID = M6eReader.GetUHFReader();
		fixedThreadPool = Executors.newFixedThreadPool(

		Runtime.getRuntime().availableProcessors()

		);
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_chuku, container, false);

		service = new PreferencesService(getActivity().getApplicationContext());
		relativeLayout = (RelativeLayout) view
				.findViewById(R.id.relativelayout);

		service = new PreferencesService(getActivity().getApplicationContext());

		tv_kaishisaomiao = (RelativeLayout) view
				.findViewById(R.id.tv_kaishisaomiao);
		tv_tingshisaomiao = (RelativeLayout) view
				.findViewById(R.id.tv_tingshisaomiao);
		btn_sure = (Button) view.findViewById(R.id.btn_sure);

		btn_clean = (Button) view.findViewById(R.id.btn_clean);
		btn_clean.setOnClickListener(button_Listener);

		lv = (ListView) view.findViewById(R.id.listView1);
		lv.deferNotifyDataSetChanged();

		tv_kaishisaomiao.setOnClickListener(button_Listener);
		tv_tingshisaomiao.setOnClickListener(button_Listener);
		btn_sure.setOnClickListener(button_Listener);

		init();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				l.get(position).setIsclick(true);
				chukuadapter.notifyDataSetChanged();

			}
		});
		// 扫描到数据，传值到后台
		activity.setCallBackListener(new TidCallBack() {

			@Override
			public void tidCallBack(String str) {
				System.out.println("Tid:" + str);
				System.out.println("扫描的tiddd：" + str);

				final String tiddd = str.replace(" ", "");
				fixedThreadPool.execute(new Runnable() {

					@Override
					public void run() {
						String url = str_url
								+ "/bank_phone/warrant/getInfoByTid";
						RequestParams params = new RequestParams(url);
						params.addBodyParameter("PID", tiddd);
						x.http().post(params, new CommonCallback<String>() {

							@Override
							public void onCancelled(CancelledException arg0) {

							}

							@Override
							public void onError(Throwable arg0, boolean arg1) {

							}

							@Override
							public void onFinished() {

							}

							@Override
							public void onSuccess(String info) {

								Log.i("msg", info);
								if (info.length() > 0) {
									Information information = new Gson()
											.fromJson(info, Information.class);
									l.add(information);
									btn_sure.setText("保存出库单(" + l.size() + ")");
									chukuadapter = new Chukuadapter(
											getActivity()
													.getApplicationContext(),
											R.layout.layout_chuku_item, l);
									lv.setAdapter(chukuadapter);

								} else {

								}

							}
						});
						System.out.println(tiddd);
						strlist.add(tiddd);

					}
				});

			}
		});
		return view;

	}

	private void init() {
		listen = new M6eReader.OnEPCsListener() {

			@Override
			public void onEPCsWithTIDRecv(String pec, String tid) {

				String tidd = tid.substring(0, 24);
				boolean isabc = false;

				for (String str : strlist) {
					if (str.equals(tidd)) {
						isabc = true;

					}
				}
				if (!isabc) {
					if (tidd.equals("1234")) {

					} else {

						String url = str_url
								+ "/bank_phone/warrant/getInfoByTid";
						RequestParams params = new RequestParams(url);
						params.addBodyParameter("PID", tidd);
						x.http().post(params, new CommonCallback<String>() {

							@Override
							public void onCancelled(CancelledException arg0) {

							}

							@Override
							public void onError(Throwable arg0, boolean arg1) {

							}

							@Override
							public void onFinished() {

							}

							@Override
							public void onSuccess(String info) {

								Log.i("msg", info);
								if (info.length() > 0) {
									Information information = new Gson()
											.fromJson(info, Information.class);
									l.add(information);
									btn_sure.setText("生成押品权证出库单(" + l.size()
											+ ")");
									chukuadapter = new Chukuadapter(
											getActivity()
													.getApplicationContext(),
											R.layout.layout_chuku_item, l);
									lv.setAdapter(chukuadapter);

								} else {

								}

							}
						});
						System.out.println(tidd);
						strlist.add(tidd);
					}
				}

			}

			@Override
			public void onEPCsRecv(ArrayList<String> arg0) {

			}
		};
	}

	private OnClickListener button_Listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_kaishisaomiao:
				show = 0;
				startTime = System.currentTimeMillis();
				showCustomToast("正在扫描....");
				relativeLayout.setVisibility(relativeLayout.INVISIBLE);
				lv.setVisibility(lv.VISIBLE);

				tv_kaishisaomiao.setVisibility(tv_kaishisaomiao.INVISIBLE);
				tv_tingshisaomiao.setVisibility(tv_tingshisaomiao.VISIBLE);
				btn_clean.setBackgroundResource(R.drawable.btnshapr);
				new Thread(

				new Runnable() {
					public void run() {

						if (!TextUtils.isEmpty(InventorActivity.modelCode)
								&& InventorActivity.MODEL
										.equals(InventorActivity.modelCode)) {

							activity.multiQueryEpc();

						}

						else {

							try {
								RFID.Connect();

								int i = RFID.TirggerStart(listen, true);
								if (i == 0) {

								} else {
									showCustomToast("扫描失败....");
								}

							} catch (InterruptedException e) {

								e.printStackTrace();
							}
						}

					}
				}

				).start();

				break;
			case R.id.tv_tingshisaomiao:
				show = 1;

				tv_kaishisaomiao.setVisibility(tv_kaishisaomiao.VISIBLE);
				tv_tingshisaomiao.setVisibility(tv_tingshisaomiao.INVISIBLE);
				btn_clean.setBackgroundResource(R.drawable.btnorgleshapr);

				btn_sure.setText("生成押品权证出库单(" + l.size() + ")");

				new Thread(

				new Runnable() {

					@Override
					public void run() {
						if (!TextUtils.isEmpty(InventorActivity.modelCode)
								&& InventorActivity.MODEL
										.equals(InventorActivity.modelCode)) {

							activity.stop();

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
								System.out.println("clean一下值");
								for (String str : strlist) {
									System.out.println(str);

								}

							} else {
								System.out.println("停止失败停止失败停止失败停止失败停止失败停止失败");
							}

						}
					}
				}

				).start();

				break;
			case R.id.btn_sure:
				if (show != 1) {

					tv_kaishisaomiao.setVisibility(tv_kaishisaomiao.VISIBLE);
					tv_tingshisaomiao
							.setVisibility(tv_tingshisaomiao.INVISIBLE);

					new Thread(

					new Runnable() {

						@Override
						public void run() {
							int r = RFID.TriggerStop();

							if (r == 0) {
								show = 1;
								if (!TextUtils
										.isEmpty(InventorActivity.modelCode)
										&& InventorActivity.MODEL
												.equals(InventorActivity.modelCode)) {

									activity.stop();
								}
								System.out.println("停止扫描停止扫描停止扫描停止扫描停止扫描停止扫描");

								getActivity().runOnUiThread(new Runnable() {

									@Override
									public void run() {

										service = new PreferencesService(
												getActivity()
														.getApplicationContext());
										Map<String, String> params = service
												.getPreferences();
										ArrayList<String> alist = new ArrayList<String>();
										for (Information information : l) {
											if (information.getCRFID() != null) {
												alist.add(information
														.getCRFID());
											}

										}
										Log.i("informatin", alist.toString());

										if (alist.size() > 0) {

											String url = str_url
													+ "/bank_phone/warrant/topush";

											TAGITEM tagitem = new TAGITEM();
											tagitem.setCRFID(alist);
											tagitem.setUserid(params.get("iid"));

											tagitem.setWarrants_iwhstate("6");
											tagitem.setIifuncregedit("1857");
											String str = new Gson()
													.toJson(tagitem);

											RequestParams params1 = new RequestParams(
													url);
											params1.addBodyParameter("str", str);
											x.http()
													.post(params1,
															new CommonCallback<String>() {

																@Override
																public void onCancelled(
																		CancelledException arg0) {

																}

																@Override
																public void onError(
																		Throwable arg0,
																		boolean arg1) {

																}

																@Override
																public void onFinished() {

																}

																@Override
																public void onSuccess(
																		String ss) {
																	showCustomToast(ss);
																	if (ss.length() < 9) {
																		if (!TextUtils
																				.isEmpty(InventorActivity.modelCode)
																				&& InventorActivity.MODEL
																						.equals(InventorActivity.modelCode)) {

																			InventorActivity.epc_tid
																					.clear();
																			InventorActivity.epc_notid
																					.clear();
																		}

																		l.clear();
																		strlist.clear();
																		chukuadapter = new Chukuadapter(
																				getActivity()
																						.getApplicationContext(),
																				R.layout.layout_chuku_item,
																				l);
																		lv.setAdapter(chukuadapter);
																		btn_sure.setText("生成押品权证出库单(0)");

																	}
																	Log.i("sss",
																			ss);

																}
															});

										} else {
											showCustomToast("不可以存在空值");

										}

									}
								});
								System.out.println("clean一下值");
								for (String str : strlist) {
									System.out.println(str);

								}

							} else {
								System.out.println("停止失败停止失败停止失败停止失败停止失败停止失败");
							}

						}
					}

					).start();

				} else {

					Map<String, String> params = service.getPreferences();
					ArrayList<String> alist = new ArrayList<String>();
					for (Information information : l) {
						if (information.getCRFID() != null) {
							alist.add(information.getCRFID());
						}

					}
					Log.i("informatin", alist.toString());

					if (alist.size() > 0) {

						String url = str_url + "/bank_phone/warrant/topush";

						TAGITEM tagitem = new TAGITEM();
						tagitem.setCRFID(alist);
						tagitem.setUserid(params.get("iid"));

						tagitem.setWarrants_iwhstate("6");
						tagitem.setIifuncregedit("1857");
						String str = new Gson().toJson(tagitem);

						RequestParams params1 = new RequestParams(url);
						params1.addBodyParameter("str", str);
						x.http().post(params1, new CommonCallback<String>() {

							@Override
							public void onCancelled(CancelledException arg0) {

							}

							@Override
							public void onError(Throwable arg0, boolean arg1) {

							}

							@Override
							public void onFinished() {

							}

							@Override
							public void onSuccess(String ss) {
								showCustomToast(ss);
								if (ss.length() < 9) {
									if (!TextUtils
											.isEmpty(InventorActivity.modelCode)
											&& InventorActivity.MODEL
													.equals(InventorActivity.modelCode)) {

										InventorActivity.epc_tid.clear();
										InventorActivity.epc_notid.clear();
									}

									l.clear();
									strlist.clear();
									chukuadapter = new Chukuadapter(
											getActivity()
													.getApplicationContext(),
											R.layout.layout_chuku_item, l);
									lv.setAdapter(chukuadapter);
									btn_sure.setText("生成押品权证出库单(0)");

								}
								Log.i("sss", ss);

							}
						});

					} else {
						showCustomToast("不可以存在空值");

					}

				}
				break;
			case R.id.btn_clean:
				if (!TextUtils.isEmpty(InventorActivity.modelCode)
						&& InventorActivity.MODEL
								.equals(InventorActivity.modelCode)) {

					InventorActivity.epc_tid.clear();
					InventorActivity.epc_notid.clear();
				}

				l.clear();
				strlist.clear();
				chukuadapter = new Chukuadapter(getActivity()
						.getApplicationContext(), R.layout.layout_chuku_item, l);
				lv.setAdapter(chukuadapter);
				btn_sure.setText("生成押品权证出库单(0)");
				break;

			}

		}

	};

	/** 显示自定义Toast提示(来自String) **/
	protected void showCustomToast(String text) {
		View toastRoot = LayoutInflater.from(
				getActivity().getApplicationContext()).inflate(
				R.layout.common_toast, null);
		((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(getActivity().getApplicationContext());
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(toastRoot);
		toast.show();
	}

	@Override
	public void onClick(View v) {

	}

	class Chukuadapter extends ArrayAdapter<Information> {
		private int resourceId;
		List<Information> list;

		public Chukuadapter(Context context, int resource,
				List<Information> list) {
			super(context, resource, list);
			this.resourceId = resource;
			this.list = list;

		}

		@Override
		public Information getItem(int position) {
			// TODO Auto-generated method stub
			return super.getItem(position);
		}

		@Override
		public View getView(final int position, View view, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (view == null) {
				viewHolder = new ViewHolder();
				view = LayoutInflater.from(getContext()).inflate(
						R.layout.layout_chuku_item, null);
				viewHolder.tv_1 = (TextView) view
						.findViewById(R.id.tv_item_all_1);
				viewHolder.tv_2 = (TextView) view
						.findViewById(R.id.tv_item_all_2);
				viewHolder.iv_del = (ImageView) view.findViewById(R.id.iv_del);

				view.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) view.getTag();
			}

			final Information mViewEpc = getItem(position);

			String department = mViewEpc.getDEPARTMENT();// 机构网点
			if (TextUtils.isEmpty(department)) {
				department = "空";
			}
			String cwhstate = mViewEpc.getCWHSTATE();
			if (TextUtils.isEmpty(cwhstate)) {
				cwhstate = "空";

			}

			String t_ccustomer = mViewEpc.getCCUSTOMER();// 借款人
			if (TextUtils.isEmpty(t_ccustomer)) {
				t_ccustomer = "空";
			}

			String tv_cclcode = (mViewEpc.getCCLCODE());// 权证编号
			if (TextUtils.isEmpty(tv_cclcode)) {
				tv_cclcode = "空";
			}
			String tv_ccltype = mViewEpc.getCCLTYPE();// 权证类型
			if (TextUtils.isEmpty(tv_ccltype)) {
				tv_ccltype = "空";
			}

			String tv_ccode = mViewEpc.getCCODE();// 入账单号
			if (TextUtils.isEmpty(tv_ccode)) {
				tv_ccode = "空";
			}
			String tv_ccollateral = mViewEpc.getCCOLLATERAL();// 质权人
			if (TextUtils.isEmpty(tv_ccollateral)) {
				tv_ccollateral = "空";
			}
			String line_one = department + "/" + tv_ccode + "/" + t_ccustomer;
			String line_two = tv_ccltype + "/" + tv_cclcode + "/"
					+ tv_ccollateral + "/" + cwhstate;
			viewHolder.tv_1.setText(line_one);
			viewHolder.tv_2.setText(line_two);
			// 点击view，将viewHolder的iv_del(图片)设置为显示
			if (mViewEpc.isIsclick()) {
				viewHolder.iv_del.setVisibility(viewHolder.iv_del.VISIBLE);

			} else {
				viewHolder.iv_del.setVisibility(viewHolder.iv_del.GONE);
			}

			viewHolder.iv_del.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					notifyDataSetChanged();// 更新适配器
					Log.i("log", "strlist" + strlist.size());

					Log.i("log", "position" + mViewEpc.getCRFID());
					Log.i("log", "boolean" + mViewEpc.getCRFID());
					if (!TextUtils.isEmpty(InventorActivity.modelCode)
							&& InventorActivity.MODEL
									.equals(InventorActivity.modelCode)) {
						ArrayList arr = valueGetKey(InventorActivity.epc_tid,
								strlist.get(position));
						if (!arr.isEmpty()) {
							for (int i = 0; i < arr.size(); i++) {

								String str = (String) arr.get(i);
								InventorActivity.epc_tid.remove(str);
								System.out.println("---------删除了：" + str);
							}
						}
					}
					strlist.remove(position);
					btn_sure.setText("生成押品权证出库单(" + (l.size() - 1) + ")");
					for (String str : strlist) {
						System.out.println(str);

					}

					list.remove(position);

				}
			});

			return view;
		}
	}

	class ViewHolder {
		TextView tv_1;
		TextView tv_2;
		ImageView iv_del;

	}

	private static ArrayList valueGetKey(Map map, String value) {

		String regex = "(.{2})";
		value = value.replaceAll(regex, "$1 ");
		Set<String> set = map.entrySet();
		ArrayList<String> arr = new ArrayList<String>();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			if (entry.getValue().equals(value)) {
				String s = (String) entry.getKey();
				arr.add(s);
			}
		}
		return arr;
	}
}