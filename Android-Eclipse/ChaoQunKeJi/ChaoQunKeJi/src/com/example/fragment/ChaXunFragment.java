package com.example.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyuan.view.HandyTextView;
import com.cetc7.M6e.M6eReader;
import com.example.activity.TabActivityty;
import com.example.entity.SearchData1;
import com.example.entity.SearchData1.Result;
import com.example.service.PreferencesService;
import com.example.test.R;
import com.example.testuhfapi.InventorActivity;
import com.example.testuhfapi.InventorActivity.TidCallBack;
import com.google.gson.Gson;

//��ѯ
public class ChaXunFragment extends Fragment {
	private View view;
	private EditText tpwd;
	private ImageView search;
	private ListView lv_search;

	private TabActivityty activity;
	private String searchQuestion;
	private Gson gson;
	private SearchData1 searchJson;
	private List<Result> lvSearchData = new ArrayList<Result>();
	private MyAdapter myAdapter;
	private List<String> listString = new ArrayList<String>();
	M6eReader RFID;// RFID��
	M6eReader.OnEPCsListener listen;// RFIDģ�鹤���ڴ���ģʽ�£������Ժ���Ҫ��ɨ�赽�����ݵĽ��м���
	private LinearLayout ll_container;
	private boolean isLinked = false;

	PreferencesService service;
	private String str_url;
	private RelativeLayout startSearch;
	private RelativeLayout stopSearch;
	private LinearLayout explain;
	private long startTime;
	private long stopTime;
	private ExecutorService fixedThreadPool;
	private SoundPool soundPool;
	private int soundid;
	private HashMap<String, String> hMap = new HashMap<String, String>();
	// private String labelSearch = "E20034120179FA00056A6F96";
	private TextView textView2;
	private int scrollStates = -1;

	private boolean isNetting;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		activity = (TabActivityty) getActivity();
		listString.clear();
		
		
		if (!TextUtils.isEmpty(InventorActivity.modelCode)
				&& InventorActivity.MODEL
						.equals(InventorActivity.modelCode)) {
			
		} else {

			RFID = M6eReader.GetUHFReader();//��ʼ��ɨ���豸
		}
		
		
		
		
		fixedThreadPool = Executors.newFixedThreadPool(

		Runtime.getRuntime().availableProcessors()

		);

		service = new PreferencesService(getActivity());
		Map<String, String> param = service.getPreferences();
		str_url = param.get("app_address");

		soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		soundid = soundPool.load(activity, R.raw.a, 1);
		super.onCreate(savedInstanceState);

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_chaxun, container, false);
		tpwd = (EditText) view.findViewById(R.id.et_tpwd);
		search = (ImageView) view.findViewById(R.id.imageView3);
		lv_search = (ListView) view.findViewById(R.id.listView1);
		/*
		 * private Button startSearch; private Button stopSearch;
		 */

		explain = (LinearLayout) view.findViewById(R.id.fl_chaxun_explain);
		startSearch = (RelativeLayout) view.findViewById(R.id.tv_kaishisaomiao);
		stopSearch = (RelativeLayout) view.findViewById(R.id.tv_tingshisaomiao);
		textView2 = (TextView) view.findViewById(R.id.textView2);
		textView2.setText(String.format("ֹͣɨ��(%d)", 0));
		ll_container = (LinearLayout) view.findViewById(R.id.LinearLayout1);
		ll_container.setVisibility(View.GONE);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		initDate();
		initEvent();
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume() {
		myAdapter.notifyDataSetChanged();
		super.onResume();
	}

	@Override
	public void onPause() {
		// listString.clear();

		if (isLinked) {// ���� true
			
			
			
			if (!TextUtils.isEmpty(InventorActivity.modelCode)
					&& InventorActivity.MODEL
							.equals(InventorActivity.modelCode)) {
				
				activity.stop();//ֹͣɨ��
				
			} else {

				RFID.DisConnect();// �ر�����
			}
			
			
			// ��ԭ��ť
			startSearch.setVisibility(View.VISIBLE);

			stopSearch.setVisibility(View.GONE);

		}

		super.onPause();
	}

	private void initEvent() {

		// ��ѯ��ť-->�������ֿ�ʼɨ���ֹͣɨ�谴ť
		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ��ȥ�����json����

				if (!isNetting) {// �����ٴ���ȥ����

					searchQuestion = tpwd.getText().toString().trim();
					if (TextUtils.isEmpty(searchQuestion)) {

						Toast.makeText(activity, "�������ѯ����!", 0).show();
						return;
					}

					isNetting = true;

					hMap.clear();
					lvSearchData.clear();
					listString.clear();

					textView2.setText(String.format("ֹͣɨ��(%d)", hMap.size()));
					getDataFromNet(searchQuestion);// �������ȡ����

				}

				if (isLinked) {// ���� true

					if (!TextUtils.isEmpty(InventorActivity.modelCode)
							&& InventorActivity.MODEL
									.equals(InventorActivity.modelCode)) {
						
						activity.stop();//ֹͣɨ��
						
					} else {

						RFID.DisConnect();// �ر�����
					}


					// ��ԭ��ť
					startSearch.setVisibility(View.VISIBLE);
					stopSearch.setVisibility(View.GONE);
				}

			}
		});

		// ��ʼ��ť-->�����ʧȥ�������-->����ֹͣɨ��
		startSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// listString.clear();
				System.out.println("startSearchʱ listString���ϵĳ��ȣ�"
						+ listString.size());
				startTime = System.currentTimeMillis();
				startSearch.setVisibility(View.GONE);
				stopSearch.setVisibility(View.VISIBLE);

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
		// ֹͣ��ť-->��ʼ��ʧȥ�������-->�����ʼɨ���-->���Ե��
		stopSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// listString.clear();
				stopTime = System.currentTimeMillis();

				long time = stopTime - startTime;
				if (time < 3000) {
					return;
				}

				stopSearch.setVisibility(View.GONE);
				startSearch.setVisibility(View.VISIBLE);

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

				// myAdapter.notifyDataSetChanged();
			}

		});

		activity.setCallBackListener(new TidCallBack() {

			@Override
			public void tidCallBack(String str) {
				System.out.println("ɨ���tiddd��"+str);
				
				final String tiddd = str.replace(" ", "");
				
				listString.add(tiddd);// ���ظ���tid
				fixedThreadPool.execute(new Runnable() {

					@Override
					public void run() {
						System.out.println("������tiddd��"+tiddd);

						int indexOf = -1;// list�е�����λ��
						
						for (Result result : lvSearchData) {
							if (tiddd.equals(result.CRFID)) {//���ɨ������hMap
								result.isSearched = true;
								soundPool.play(soundid, 1.0f, 1.0f, 0,
										0, 1.0f);// ƥ�䣬��������
								hMap.put(tiddd, tiddd);// ��¼Ψһ��tid
								System.out.println("hMap���ϵĳ��ȣ�"
										+ hMap.size());
								// �õ�����ֵ����ת����Ӧ��item
								indexOf = lvSearchData.indexOf(result);
							}
						}
						final int findex = indexOf;

						activity.runOnUiThread(new Runnable() {

							@Override
							public void run() {

								// ��ת����
								myAdapter.moveToIndex(findex);

							}
						});

					}
				});

			}
		});

		listen = new M6eReader.OnEPCsListener() {

			public void onEPCsRecv(ArrayList<String> EPCList) {

			}

			@Override
			public void onEPCsWithTIDRecv(String epc, String tid) {

				// System.out.println("!");
				final String tiddd = tid.substring(0, 24);
				
				
				boolean isabc = false;

				for (String str : listString) {
					if (str.equals(tiddd)) {
						isabc = true;
					}

				}
				if (!isabc) {
					if (tiddd.equals("1234")) {

					} else {
						System.out.println("ɨ�赽��tiddd��" + tiddd);
						listString.add(tiddd);// ���ظ���tid

						
						fixedThreadPool.execute(new Runnable() {

							@Override
							public void run() {


								int indexOf = -1;// list�е�����λ��
								for (Result result : lvSearchData) {
									if (tiddd.equals(result.CRFID)) {
										result.isSearched = true;
										soundPool.play(soundid, 1.0f, 1.0f, 0,
												0, 1.0f);// ƥ�䣬��������
										hMap.put(tiddd, tiddd);// ��¼Ψһ��tid
										System.out.println("hMap���ϵĳ��ȣ�"
												+ hMap.size());
										// �õ�����ֵ����ת����Ӧ��item
										indexOf = lvSearchData.indexOf(result);
									}
								}
								final int findex = indexOf;

								activity.runOnUiThread(new Runnable() {

									@Override
									public void run() {

										// ��ת����
										myAdapter.moveToIndex(findex);

									}
								});

							}
						});

					}

				}

			}

		};

	}

	private void initDate() {

		myAdapter = new MyAdapter();
		lv_search.setAdapter(myAdapter);
	}

	/**
	 * ��ȡ�����ϵ�����
	 * 
	 * @param iid
	 *            �û�id
	 */
	private void getDataFromNet(String iid) {

		// �������磬��ȡ����
		RequestParams requestParams = new RequestParams(str_url
				+ "/bank_phone/warrant/search");
		requestParams.addParameter("str", searchQuestion);

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

				// 1.��ƴjson����
				String jsonData = "{result:" + responseInfo + "}";
				System.out.println(jsonData);
				searchJson = parseJson(jsonData);
				// 3.��������ʵ��
				processData(searchJson);
			}
		});
	}

	/**
	 * 2.���������̵���json����
	 * 
	 * @param jsonData
	 * @return PanDianData
	 */
	private SearchData1 parseJson(String jsonData) {
		if (gson == null) {
			gson = new Gson();
		}
		return gson.fromJson(jsonData, SearchData1.class);
	}

	/**
	 * 3.������ݴ��� �������ݿ� ViewPager�����ݼ���
	 * 
	 * @param panDianData
	 */
	private void processData(SearchData1 searchJson) {

		lvSearchData = searchJson.result;

		myAdapter.notifyDataSetChanged();

		isNetting = false;

		explain.setVisibility(View.GONE);
		ll_container.setVisibility(View.VISIBLE);

	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			// System.out.println(lvSearchData.size());
			return lvSearchData.size();

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
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = View.inflate(activity, R.layout.item_pf_search,
						null);
				viewHolder = new ViewHolder();
				viewHolder.tv_1 = (TextView) convertView
						.findViewById(R.id.tv_item_search_1);
				viewHolder.tv_2 = (TextView) convertView
						.findViewById(R.id.tv_item_search_2);
				viewHolder.iv = (ImageView) convertView
						.findViewById(R.id.iv_item_search_location);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			String a1 = lvSearchData.get(position).CPOSITION;
			if (TextUtils.isEmpty(a1)) {
				a1 = "��";
			}
			
			String a = lvSearchData.get(position).DEPARTMENT;
			if (TextUtils.isEmpty(a)) {
				a = "��";
			}
			String b = lvSearchData.get(position).CCODE;
			if (TextUtils.isEmpty(b)) {
				b = "��";
			}
			String c = lvSearchData.get(position).CCUSTOMER;
			if (TextUtils.isEmpty(c)) {
				c = "��";
			}
			
			String all_1 = a1 + " /" +a + " /" + b + " /" + c ;

			String e = lvSearchData.get(position).CCLTYPE;
			if (TextUtils.isEmpty(e)) {
				e = "��";
			}
			String f = lvSearchData.get(position).CCLCODE;
			if (TextUtils.isEmpty(f)) {
				f = "��";
			}

			String g = lvSearchData.get(position).CCOLLATERAL;// ״̬
			if (TextUtils.isEmpty(g)) {
				g = "��";
			}
			
			String h = lvSearchData.get(position).IWHSTATE;// ״̬
			if (TextUtils.isEmpty(h)) {
				h = "��";
			}

			String all_2 = e + " /" + f + " /" + g+" /" + h;

			viewHolder.tv_1.setText(all_1);
			viewHolder.tv_2.setText(all_2);

			// listviewˢ�¿ɼ�item
			if (lvSearchData.get(position).isSearched) {// true:ɨ�赽 false:δɨ�赽
				viewHolder.iv.setVisibility(View.VISIBLE);
			} else {
				viewHolder.iv.setVisibility(View.INVISIBLE);
			}

			// ����ͼƬ�Ƿ���ʾ

			/*
			 * boolean isState = false; for (String str : listString) { if
			 * (f.equals(str)) { isState = true; } } if (isState) {
			 * viewHolder.iv.setVisibility(View.VISIBLE); } else {
			 * viewHolder.iv.setVisibility(View.INVISIBLE); }
			 */
			return convertView;
		}

		private void moveToIndex(int index) {

			lv_search.setSelection(index - 6);
			textView2.setText(String.format("ֹͣɨ��(%d)", hMap.size()));

			new Thread(new Runnable() {

				@Override
				public void run() {

					activity.runOnUiThread(new Runnable() {

						@Override
						public void run() {

							myAdapter.notifyDataSetChanged();

						}
					});

				}
			}).start();

		}

	}

	private class ViewHolder {
		TextView tv_1;
		TextView tv_2;
		ImageView iv;
	}

	@Override
	public void onDestroy() {
		listString.clear();
		hMap.clear();
		super.onDestroy();
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
