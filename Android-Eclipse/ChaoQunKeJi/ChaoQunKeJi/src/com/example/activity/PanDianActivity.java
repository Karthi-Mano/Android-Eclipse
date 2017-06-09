package com.example.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dbhelper.PanDianOpenHelper;
import com.example.entity.PanDianData;
import com.example.entity.PanDianData.Result;
import com.example.fragment.PdAllFragmentForActivity;
import com.example.fragment.PdDaipanFragmentForActivity;
import com.example.fragment.PdYiPanFragmentForActivity;
import com.example.provider.PanDianPrivoder;
import com.example.service.PreferencesService;
import com.example.test.R;
import com.example.testuhfapi.InventorActivity;
import com.example.utils.TitleBarTool;
import com.example.utils.TitleBarTool.OnTitleBarClickListener;
import com.google.gson.Gson;

/**
 * ������̵�ҳ�棬��ҳ���Ǵ������Fragment������ ÿ��Fragment�������л�ø��Ե����ݣ�����ʾ
 * 
 * @author Wjz
 * 
 */
public class PanDianActivity extends InventorActivity {

	private String iid;
	public int iCHECKVOUCH;
	private PanDianData panDianData;
	private Gson gson;
	private List<Result> result;
	private MyAdapter myAdapter;
	private ImageView iv_pandian_back;
	private TextView title1;
	private TextView title2;
	// private TextView title3;
	private ViewPager vp_pandian_container;
	private PdAllFragmentForActivity all;
	private PdDaipanFragmentForActivity daipan;
	private PdYiPanFragmentForActivity yipan;
	private List<Fragment> fragmentList = new ArrayList<Fragment>();
	private String[] titles;
	private TitleBarTool titleBarTool;
	public String nTYPE = "1";
	public String yTYPE = "3";
	public String kTYPE = "4";
	private int yNum;
	private ExecutorService fixedThreadPool;
	public ArrayList<String> strIID = new ArrayList<String>();
	private int position;
	public int isUploaddd;
	private int wNum;
	PreferencesService service;
	private String str_url;
	private int[] nums = new int[2];;
	public int wCount;
	private int dCount;
	private int yCount;
	private int kCount;
	private TextView[] tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		service = new PreferencesService(this);
		Map<String, String> param = service.getPreferences();
		str_url = param.get("app_address");

		fixedThreadPool = Executors.newFixedThreadPool(

		Runtime.getRuntime().availableProcessors()

		);

		initView();
		initData();
		initEvent();
		registerContentObserver();
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onDestroy() {

		
		
		
		
		super.onDestroy();
	}

	private void initView() {
		setContentView(R.layout.activity_pandian);

		iv_pandian_back = (ImageView) findViewById(R.id.iv_pandian_back);
		title1 = (TextView) findViewById(R.id.tv_pandian_title1);
		title2 = (TextView) findViewById(R.id.tv_pandian_title2);

		vp_pandian_container = (ViewPager) findViewById(R.id.vp_pandian_container);

	}

	/**
	 * ȥ�����ؼ�
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (isUploaddd == 0) {// δ�ύ
				showDialog1(this);
			} else {
				finish();
			}
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	/**
	 * �Ի���
	 * 
	 * @param context
	 */
	public void showDialog1(Context context) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("��ʾ")
				.setMessage("��ǰ���� " + wCount + " ��Ȩ֤δ�̵㵽���Ƿ� ���˳���")

				.setPositiveButton("��", new AlertDialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// �ر� activity
						finish();
					}
				}).setNegativeButton("��", new AlertDialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				})

				.show();// ��ʾ�Ի���

	}

	private void initData() {
		// ��ʼ��titleҳǩ�Ķ���
		all = new PdAllFragmentForActivity();
		daipan = new PdDaipanFragmentForActivity();
		yipan = new PdYiPanFragmentForActivity();

		// �ϸ�ҳ�洫��������
		Intent intent = getIntent();
		iid = intent.getStringExtra("iid");
		iCHECKVOUCH = intent.getIntExtra("iCHECKVOUCH", 0);
		yNum = intent.getIntExtra("yNum", 0);
		position = intent.getIntExtra("position", -1);
		isUploaddd = intent.getIntExtra("isUploaddd", -1);// 0δ�ϴ��� 1�ϴ���
		wNum = intent.getIntExtra("wNum", -1);
		kNum = intent.getIntExtra("kNum", -1);

		wCount = wNum;
		yCount = yNum;
		kCount = kNum;
		/*
		 * if (isUploaddd == 0) {// δ�ϴ���
		 * 
		 * if (yNum == 0) { // ��ȥ�����json����
		 * System.out.println("δ��ʼ�̵�δ��ʼ�̵�δ��ʼ�̵�"); getDataFromNet(iCHECKVOUCH,
		 * iid); } } else {// �ϴ��� // ʹ�û��� getDataFromNet(iCHECKVOUCH, iid); }
		 */
		// ��ҳǩ�������Fragment������

		if (isUploaddd == 1) {// �Ѿ��ύ����
			// ����Ѿ��ύ�̵�
			// �����Ѿ��̵��ȫ���̵�ҳ��
			fragmentList.add(all);
			fragmentList.add(yipan);
			titles = new String[] { "δ����", "������" };
			nums[0] = kCount;
			nums[1] = yCount;
			// ��ȡ����
			getDataFromNet(iCHECKVOUCH, iid);
		} else {// δ�ύ��
				// ���û���̵����δ�̺��Ѿ��̵�
			fragmentList.add(daipan);
			fragmentList.add(yipan);
			titles = new String[] { "������", "������" };
			nums[0] = wCount;
			nums[1] = yCount;
			if (yNum == 0) {
				// ��ȡ����
				getDataFromNet(iCHECKVOUCH, iid);

			} else {
				System.out.println("ʹ�û���ʹ�û���ʹ�û���ʹ�û���");
			}

		}

		
		
		// fragmengList.add(all);
		// ��ȡFragment�Ĺ�����
		FragmentManager fm = getSupportFragmentManager();

		// ����adapter�����ݣ���ʾ2��ҳǩ
		myAdapter = new MyAdapter(fm);
		// ΪViewPager����adapter
		vp_pandian_container.setAdapter(myAdapter);

		titleBarTool = new TitleBarTool();

		tv = new TextView[] { title1, title2 };
		titleBarTool.createTitleBar(titles, tv, nums);

		// ����Ĭ��ѡ�� 0 -->����
		titleBarTool.changeColor(0);
		myAdapter.notifyDataSetChanged();

	}

	/**
	 * ViewPager��Adapter������
	 * 
	 * @author Wjz
	 * 
	 */
	private class MyAdapter extends FragmentPagerAdapter {

		public MyAdapter(FragmentManager fm) {
			super(fm);

		}

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			return fragmentList.get(position);
		}

		@Override
		public int getCount() {
			if (titles != null) {
				return titles.length;
			}
			return 0;
		}

	}

	/**
	 * ��ȡ�����ϵ�����
	 * 
	 * @param iCHECKVOUCH
	 *            �û�ǩ��
	 * @param iid
	 *            �û�id
	 */
	private void getDataFromNet(int iCHECKVOUCH, String iid) {

		// �������磬��ȡ����
		RequestParams requestParams = new RequestParams(str_url
				+ "/bank_phone/warrant/getInfoToCheck");

		requestParams.addParameter("icheckvouch", iCHECKVOUCH);
		requestParams.addParameter("userid", iid);

		x.http().get(requestParams, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {

				System.out.println(arg0);
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {

				System.out.println(arg0);
			}

			@Override
			public void onFinished() {

				System.out.println("onFinished");
			}

			@Override
			public void onSuccess(String responseInfo) {

				// 1.��ƴjson����
				String jsonData = "{result:" + responseInfo + "}";
				System.out.println(jsonData);
				// 2.����json����
				panDianData = parseJson(jsonData);
				// 3.��������ʵ��
				processData(panDianData);
			}
		});
	}

	/**
	 * 2.���������̵���json����
	 * 
	 * @param jsonData
	 * @return PanDianData
	 */
	private PanDianData parseJson(String jsonData) {
		if (gson == null) {
			gson = new Gson();
		}
		return gson.fromJson(jsonData, PanDianData.class);
	}

	/**
	 * 3.������ݴ��� �������ݿ� ViewPager�����ݼ���
	 * 
	 * @param panDianData
	 */
	private void processData(PanDianData panDianData) {

		result = panDianData.result;// ����������ݼ���

		for (int i = 0; i < result.size(); i++) {

			final int finali = i;

			// �������ݿ�
			fixedThreadPool.execute(new Runnable() {

				@Override
				public void run() {
					saveAndUpdate(result, finali);

				}
			});

		}
	}

	/**
	 * ���߳��У������ݿ���±�������
	 * 
	 * @param i
	 * @param result
	 * @param panDianData
	 */
	private void saveAndUpdate(List<Result> result, int i) {
		ContentValues values = new ContentValues();

		if (TextUtils.isEmpty(panDianData.result.get(i).CCLCODE)) {
			panDianData.result.get(i).CCLCODE = "��";
		}

		values.put(PanDianOpenHelper.PanDianTable.CCLCODE,
				panDianData.result.get(i).CCLCODE);
		//
		if (TextUtils.isEmpty(panDianData.result.get(i).CCLTYPE)) {
			panDianData.result.get(i).CCLTYPE = "��";
		}

		values.put(PanDianOpenHelper.PanDianTable.CCLTYPE,
				panDianData.result.get(i).CCLTYPE);
		//
		if (TextUtils.isEmpty(panDianData.result.get(i).CCUSTOMER)) {
			panDianData.result.get(i).CCUSTOMER = "��";
		}

		values.put(PanDianOpenHelper.PanDianTable.CCUSTOMER,
				panDianData.result.get(i).CCUSTOMER);

		if (TextUtils.isEmpty(panDianData.result.get(i).CPOSITION)) {
			panDianData.result.get(i).CPOSITION = "��";
		}
		//
		values.put(PanDianOpenHelper.PanDianTable.CPOSITION,
				panDianData.result.get(i).CPOSITION);

		if (TextUtils.isEmpty(panDianData.result.get(i).CRFID)) {
			panDianData.result.get(i).CRFID = "��";
		}
		//
		values.put(PanDianOpenHelper.PanDianTable.CRFID,
				panDianData.result.get(i).CRFID);

		if (TextUtils.isEmpty(panDianData.result.get(i).DEPARTMENT)) {
			panDianData.result.get(i).DEPARTMENT = "��";
		}
		
		values.put(PanDianOpenHelper.PanDianTable.DEPARTMENT,
				panDianData.result.get(i).DEPARTMENT);

		//�޸�-V2�汾
		if (TextUtils.isEmpty(panDianData.result.get(i).CCODE)) {
			panDianData.result.get(i).CCODE = "��";
		}
		
		values.put(PanDianOpenHelper.PanDianTable.CCODE,
				panDianData.result.get(i).CCODE);
		
		if (TextUtils.isEmpty(panDianData.result.get(i).CCOLLATERAL)) {
			panDianData.result.get(i).CCOLLATERAL = "��";
		}
		
		values.put(PanDianOpenHelper.PanDianTable.CCOLLATERAL,
				panDianData.result.get(i).CCOLLATERAL);
		
		
		
		
		values.put(PanDianOpenHelper.PanDianTable.IID,
				panDianData.result.get(i).IID);

		values.put(PanDianOpenHelper.PanDianTable.ISTATE,
				panDianData.result.get(i).ISTATE);// ״̬

		values.put(PanDianOpenHelper.PanDianTable.IWARRANTS,
				panDianData.result.get(i).IWARRANTS);

		values.put(PanDianOpenHelper.PanDianTable.ICHECKVOUCH, iCHECKVOUCH);

		switch (isUploaddd) {
		case 0:// ���isupload==0��ƥ��URI_PANDIAN

			// ���ºͲ���
			int update = getContentResolver().update(
					PanDianPrivoder.URI_PANDIAN, values,
					PanDianOpenHelper.PanDianTable.IID + "=?",
					new String[] { panDianData.result.get(i).IID });
			// System.out.println("������" + update);// ������
			if (update <= 0) {// ��������
				Uri insert = getContentResolver().insert(
						PanDianPrivoder.URI_PANDIAN, values);
				// System.out.println(insert.getPath());// ���� �ɹ����uri
			}

			break;
		case 1: // ���isupload==1��ƥ��URI_YPANDIAN

			// ���ºͲ���
			int update2 = getContentResolver().update(
					PanDianPrivoder.URI_YPANDIAN, values,
					PanDianOpenHelper.PanDianTable.IID + "=?",
					new String[] { panDianData.result.get(i).IID });
			// System.out.println("������" + update);// ������
			if (update2 <= 0) {// ��������
				Uri insert = getContentResolver().insert(
						PanDianPrivoder.URI_YPANDIAN, values);
				// System.out.println(insert.getPath());// ���� �ɹ����uri
			}

			break;

		default:
			break;
		}

	}

	/**
	 * ��Ӽ����¼�
	 */
	private void initEvent() {

		vp_pandian_container
				.setOnPageChangeListener(new OnPageChangeListener() {

					@Override
					public void onPageSelected(int position) {
						// ѡ��״̬
						// �޸���ɫ
						titleBarTool.changeColor(position);
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onPageScrollStateChanged(int arg0) {
						// TODO Auto-generated method stub

					}
				});

		titleBarTool.setOnTitleBarClickListener(new OnTitleBarClickListener() {

			@Override
			public void changerTitleBar(int position) {
				vp_pandian_container.setCurrentItem(position);
			}
		});

		// ���ذ�ť
		iv_pandian_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (isUploaddd == 0) {
					if (wCount != 0) {// δ�̵���
						showDialog1(PanDianActivity.this);
					} else {
						finish();
					}
				} else {
					finish();
				}

			}
		});

	}

	MyContentObserver myContentObserver = new MyContentObserver(new Handler());
	private int kNum;

	/**
	 * ע�����
	 */
	public void registerContentObserver() {

		getContentResolver().registerContentObserver(
				PanDianPrivoder.URI_PANDIAN, true, myContentObserver);

	}

	/**
	 * ��ע�����
	 */
	public void unRegisterContentObserver() {
		getContentResolver().unregisterContentObserver(myContentObserver);
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

			// ��ѯδ�̵����ʾ��TextView��
			fixedThreadPool.execute(new Runnable() {

				@Override
				public void run() {

					// ��ѯ�̵��
					Cursor wCursor = getContentResolver()
							.query(PanDianPrivoder.URI_PANDIAN,
									null,
									PanDianOpenHelper.PanDianTable.ISTATE
											+ "=?"
											+ " and "
											+ PanDianOpenHelper.PanDianTable.ICHECKVOUCH
											+ "=?",
									new String[] { nTYPE, iCHECKVOUCH + "" },
									null);

					Cursor yCursor = getContentResolver()
							.query(PanDianPrivoder.URI_PANDIAN,
									null,
									PanDianOpenHelper.PanDianTable.ISTATE
											+ "=?"
											+ " and "
											+ PanDianOpenHelper.PanDianTable.ICHECKVOUCH
											+ "=?",
									new String[] { yTYPE, iCHECKVOUCH + "" },
									null);
					wCount = wCursor.getCount();

					yCount = yCursor.getCount();

					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {

							nums[0] = wCount;
							nums[1] = yCount;

							titleBarTool.createTitleBar(titles, tv, nums);
							
						}
					});
					
				}
			});

		}
	}

}
