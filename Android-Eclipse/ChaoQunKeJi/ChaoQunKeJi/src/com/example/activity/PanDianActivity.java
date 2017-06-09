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
 * 具体的盘点页面，该页面是存放三个Fragment的容器 每个Fragment从容器中获得各自的数据，并显示
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
	 * 去除返回键
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (isUploaddd == 0) {// 未提交
				showDialog1(this);
			} else {
				finish();
			}
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 对话框
	 * 
	 * @param context
	 */
	public void showDialog1(Context context) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("提示")
				.setMessage("当前仍有 " + wCount + " 条权证未盘点到，是否 仍退出？")

				.setPositiveButton("是", new AlertDialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 关闭 activity
						finish();
					}
				}).setNegativeButton("否", new AlertDialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				})

				.show();// 显示对话框

	}

	private void initData() {
		// 初始化title页签的对象
		all = new PdAllFragmentForActivity();
		daipan = new PdDaipanFragmentForActivity();
		yipan = new PdYiPanFragmentForActivity();

		// 上个页面传来的数据
		Intent intent = getIntent();
		iid = intent.getStringExtra("iid");
		iCHECKVOUCH = intent.getIntExtra("iCHECKVOUCH", 0);
		yNum = intent.getIntExtra("yNum", 0);
		position = intent.getIntExtra("position", -1);
		isUploaddd = intent.getIntExtra("isUploaddd", -1);// 0未上传过 1上传过
		wNum = intent.getIntExtra("wNum", -1);
		kNum = intent.getIntExtra("kNum", -1);

		wCount = wNum;
		yCount = yNum;
		kCount = kNum;
		/*
		 * if (isUploaddd == 0) {// 未上传过
		 * 
		 * if (yNum == 0) { // 拉去网络的json数据
		 * System.out.println("未开始盘点未开始盘点未开始盘点"); getDataFromNet(iCHECKVOUCH,
		 * iid); } } else {// 上传过 // 使用缓存 getDataFromNet(iCHECKVOUCH, iid); }
		 */
		// 将页签对象加入Fragment容器中

		if (isUploaddd == 1) {// 已经提交过了
			// 如果已经提交盘点
			// 进入已经盘点和全部盘点页面
			fragmentList.add(all);
			fragmentList.add(yipan);
			titles = new String[] { "未盘数", "已盘数" };
			nums[0] = kCount;
			nums[1] = yCount;
			// 拉取数据
			getDataFromNet(iCHECKVOUCH, iid);
		} else {// 未提交过
				// 如果没有盘点进入未盘和已经盘点
			fragmentList.add(daipan);
			fragmentList.add(yipan);
			titles = new String[] { "待盘数", "已盘数" };
			nums[0] = wCount;
			nums[1] = yCount;
			if (yNum == 0) {
				// 拉取数据
				getDataFromNet(iCHECKVOUCH, iid);

			} else {
				System.out.println("使用缓存使用缓存使用缓存使用缓存");
			}

		}

		
		
		// fragmengList.add(all);
		// 获取Fragment的管理器
		FragmentManager fm = getSupportFragmentManager();

		// 设置adapter的数据，显示2个页签
		myAdapter = new MyAdapter(fm);
		// 为ViewPager设置adapter
		vp_pandian_container.setAdapter(myAdapter);

		titleBarTool = new TitleBarTool();

		tv = new TextView[] { title1, title2 };
		titleBarTool.createTitleBar(titles, tv, nums);

		// 设置默认选中 0 -->带盘
		titleBarTool.changeColor(0);
		myAdapter.notifyDataSetChanged();

	}

	/**
	 * ViewPager的Adapter的设置
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
	 * 拉取网络上的数据
	 * 
	 * @param iCHECKVOUCH
	 *            用户签名
	 * @param iid
	 *            用户id
	 */
	private void getDataFromNet(int iCHECKVOUCH, String iid) {

		// 请求网络，拉取数据
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

				// 1.组拼json数据
				String jsonData = "{result:" + responseInfo + "}";
				System.out.println(jsonData);
				// 2.解析json数据
				panDianData = parseJson(jsonData);
				// 3.处理数据实体
				processData(panDianData);
			}
		});
	}

	/**
	 * 2.解析具体盘点库的json数据
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
	 * 3.完成数据处理 存入数据库 ViewPager的数据集合
	 * 
	 * @param panDianData
	 */
	private void processData(PanDianData panDianData) {

		result = panDianData.result;// 解析后的数据集合

		for (int i = 0; i < result.size(); i++) {

			final int finali = i;

			// 存入数据库
			fixedThreadPool.execute(new Runnable() {

				@Override
				public void run() {
					saveAndUpdate(result, finali);

				}
			});

		}
	}

	/**
	 * 子线程中，向数据库更新保存数据
	 * 
	 * @param i
	 * @param result
	 * @param panDianData
	 */
	private void saveAndUpdate(List<Result> result, int i) {
		ContentValues values = new ContentValues();

		if (TextUtils.isEmpty(panDianData.result.get(i).CCLCODE)) {
			panDianData.result.get(i).CCLCODE = "空";
		}

		values.put(PanDianOpenHelper.PanDianTable.CCLCODE,
				panDianData.result.get(i).CCLCODE);
		//
		if (TextUtils.isEmpty(panDianData.result.get(i).CCLTYPE)) {
			panDianData.result.get(i).CCLTYPE = "空";
		}

		values.put(PanDianOpenHelper.PanDianTable.CCLTYPE,
				panDianData.result.get(i).CCLTYPE);
		//
		if (TextUtils.isEmpty(panDianData.result.get(i).CCUSTOMER)) {
			panDianData.result.get(i).CCUSTOMER = "空";
		}

		values.put(PanDianOpenHelper.PanDianTable.CCUSTOMER,
				panDianData.result.get(i).CCUSTOMER);

		if (TextUtils.isEmpty(panDianData.result.get(i).CPOSITION)) {
			panDianData.result.get(i).CPOSITION = "空";
		}
		//
		values.put(PanDianOpenHelper.PanDianTable.CPOSITION,
				panDianData.result.get(i).CPOSITION);

		if (TextUtils.isEmpty(panDianData.result.get(i).CRFID)) {
			panDianData.result.get(i).CRFID = "空";
		}
		//
		values.put(PanDianOpenHelper.PanDianTable.CRFID,
				panDianData.result.get(i).CRFID);

		if (TextUtils.isEmpty(panDianData.result.get(i).DEPARTMENT)) {
			panDianData.result.get(i).DEPARTMENT = "空";
		}
		
		values.put(PanDianOpenHelper.PanDianTable.DEPARTMENT,
				panDianData.result.get(i).DEPARTMENT);

		//修改-V2版本
		if (TextUtils.isEmpty(panDianData.result.get(i).CCODE)) {
			panDianData.result.get(i).CCODE = "空";
		}
		
		values.put(PanDianOpenHelper.PanDianTable.CCODE,
				panDianData.result.get(i).CCODE);
		
		if (TextUtils.isEmpty(panDianData.result.get(i).CCOLLATERAL)) {
			panDianData.result.get(i).CCOLLATERAL = "空";
		}
		
		values.put(PanDianOpenHelper.PanDianTable.CCOLLATERAL,
				panDianData.result.get(i).CCOLLATERAL);
		
		
		
		
		values.put(PanDianOpenHelper.PanDianTable.IID,
				panDianData.result.get(i).IID);

		values.put(PanDianOpenHelper.PanDianTable.ISTATE,
				panDianData.result.get(i).ISTATE);// 状态

		values.put(PanDianOpenHelper.PanDianTable.IWARRANTS,
				panDianData.result.get(i).IWARRANTS);

		values.put(PanDianOpenHelper.PanDianTable.ICHECKVOUCH, iCHECKVOUCH);

		switch (isUploaddd) {
		case 0:// 如果isupload==0，匹配URI_PANDIAN

			// 更新和插入
			int update = getContentResolver().update(
					PanDianPrivoder.URI_PANDIAN, values,
					PanDianOpenHelper.PanDianTable.IID + "=?",
					new String[] { panDianData.result.get(i).IID });
			// System.out.println("更新了" + update);// 更新了
			if (update <= 0) {// 更新数据
				Uri insert = getContentResolver().insert(
						PanDianPrivoder.URI_PANDIAN, values);
				// System.out.println(insert.getPath());// 插入 成功后的uri
			}

			break;
		case 1: // 如果isupload==1，匹配URI_YPANDIAN

			// 更新和插入
			int update2 = getContentResolver().update(
					PanDianPrivoder.URI_YPANDIAN, values,
					PanDianOpenHelper.PanDianTable.IID + "=?",
					new String[] { panDianData.result.get(i).IID });
			// System.out.println("更新了" + update);// 更新了
			if (update2 <= 0) {// 更新数据
				Uri insert = getContentResolver().insert(
						PanDianPrivoder.URI_YPANDIAN, values);
				// System.out.println(insert.getPath());// 插入 成功后的uri
			}

			break;

		default:
			break;
		}

	}

	/**
	 * 添加监听事件
	 */
	private void initEvent() {

		vp_pandian_container
				.setOnPageChangeListener(new OnPageChangeListener() {

					@Override
					public void onPageSelected(int position) {
						// 选中状态
						// 修改颜色
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

		// 返回按钮
		iv_pandian_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (isUploaddd == 0) {
					if (wCount != 0) {// 未盘点数
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
	 * 注册监听
	 */
	public void registerContentObserver() {

		getContentResolver().registerContentObserver(
				PanDianPrivoder.URI_PANDIAN, true, myContentObserver);

	}

	/**
	 * 反注册监听
	 */
	public void unRegisterContentObserver() {
		getContentResolver().unregisterContentObserver(myContentObserver);
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

			// 查询未盘点表，显示到TextView中
			fixedThreadPool.execute(new Runnable() {

				@Override
				public void run() {

					// 查询盘点表
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
