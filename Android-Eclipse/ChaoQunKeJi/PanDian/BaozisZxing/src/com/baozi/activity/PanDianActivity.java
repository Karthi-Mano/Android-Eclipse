package com.baozi.activity;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.xutils.x;

import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import com.baoyuan.view.HandyTextView;
import com.baozi.Zxing.CaptureActivity;

import com.example.baoziszxing.R;

import com.example.dbhelper.PanDianOpenHelper;
import com.example.entity.ErweimaSelectUtil;
import com.example.entity.ErweimaUtil;
import com.example.entity.PanDianData;
import com.example.entity.PanDianData.Result;
import com.example.fragment.PdAllFragmentForActivity;
import com.example.fragment.PdDaipanFragmentForActivity;
import com.example.fragment.PdYiPanFragmentForActivity;

import com.example.providers.PanDianPrivoder;
import com.example.service.PreferencesService;
import com.example.utils.TitleBarTool;
import com.example.utils.TitleBarTool.OnTitleBarClickListener;

import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Instrumentation;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;

import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class PanDianActivity extends FragmentActivity {
	// 扫描的请求码

	private static final String tag = "PanDianActivity";
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int PICK_IMAGE_ACTIVITY_REQUEST_CODE = 200;
	private static final int REQUEST_CODE = 234;
	public String codeid;
	private TextView show;
	private ImageView imageView;
	Button button;
	Button btn_erweima;
	// 盘点页面
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
	// 2015年末盘点
	private TextView tv_uname;
	private String cname;
	private List<Fragment> fragmentList = new ArrayList<Fragment>();
	private String[] titles;
	private TitleBarTool titleBarTool;
	public String nTYPE = "待盘";
	public String yTYPE = "已盘";
	public String kTYPE = "盘亏";
	private int yNum;
	public int timecount;// 标记，先执行扫码，得到数据后才能进行拍照功能
	private PdAllFragmentForActivity all;
	private PdDaipanFragmentForActivity daipan;
	private PdYiPanFragmentForActivity yipan;
	private int qrCount;
	private ExecutorService fixedThreadPool;
	public ArrayList<String> strIID = new ArrayList<String>();
	private int position;
	public int isUploaddd;
	private int wNum;
	PreferencesService service;
	private String str_url;
	private int[] nums = new int[2];;
	private int wCount;
	private int dCount;
	private int yCount;
	private int kCount;
	private TextView[] tv;
	private Button btn_sure;

	// 显示数据
	private EditText et_ccode;// 资产编码
	private TextView tv_cname;// 资产名称
	private TextView tv_iclass_name;// 资产类别
	private TextView idepartment_name;// 机构网点
	private TextView dbegin;// 使用日期
	private RadioButton btn_radio01;// 在用
	private RadioButton btn_radio02;// 闲置

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		timecount = 0;
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

	private OnClickListener button_Listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.open_camera:
				if (timecount == 0) {
					showCustomToast("请先扫描得到数据");

				} else {
					takePicture();
				}

				break;
			case R.id.btn_erweima:
				timecount = 1;
				Intent intent = new Intent(PanDianActivity.this,
						CaptureActivity.class);

				startActivityForResult(intent, REQUEST_CODE);
				break;
			case R.id.btn_sure:
				timecount = 0;
				String info = et_ccode.getText().toString();// 这是正确的吧
				// String info = "JZ2016040003";// 这是测试
				// 盘点是否执行过扫码操作
				if (info.equals("")) {
					showCustomToast("请先执行扫码");

				} else {
					// 判断资产是否在本次盘点范围
					Cursor cursor = PanDianActivity.this
							.getContentResolver()
							.query(PanDianPrivoder.URI_PANDIAN,
									null,
									PanDianOpenHelper.PanDianTable.CCODE
											+ "=?"
											+ " and "
											+ PanDianOpenHelper.PanDianTable.ICHECKVOUCH
											+ "=?",
									new String[] { info, iCHECKVOUCH + "" },
									null);
					// 如果该资产不在盘点范围内，给出提示
					if (cursor.getCount() == 0) {
						showCustomToast("该资产不在本次盘点范围内");
						Log.i("showcurso", "这是查询数据的值：" + cursor.getCount());

					} else {
						// 如果该资产在本次盘点范围内，进行update
						ContentValues values = new ContentValues();
						values.put(PanDianOpenHelper.PanDianTable.ISTATE_NAME,
								yTYPE);

						int updateCount = PanDianActivity.this
								.getContentResolver()
								.update

								(PanDianPrivoder.URI_PANDIAN,
										values,
										PanDianOpenHelper.PanDianTable.CCODE
												+ "=?"
												+ " and "
												+ PanDianOpenHelper.PanDianTable.ISTATE_NAME
												+ "=?", new

										String[] { info, nTYPE });
						// 判断update是否执行成功，如果不成功，给出提示该资产已经执行过盘点操作
						if (updateCount == 0) {
							showCustomToast("该资产已经执行过盘点操作");

						} else {
							showCustomToast("盘点操作成功");

						}

						System.out.println("updateCount:" + updateCount);

						qrCount += updateCount; // 这是简写
						System.out.println("qrCountqrCountqrCount:" + qrCount);

					}
				}

				Toast.makeText(getApplicationContext(), "点击了确定：" + info, 3000)
						.show();
				break;

			}

		}

	};
	private static String picFileFullName;

	// 拍照
	public void takePicture() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			File outDir = Environment
					.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
			if (!outDir.exists()) {
				outDir.mkdirs();
			}
			File outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
			picFileFullName = outFile.getAbsolutePath();
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
			intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
			startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		} else {
			Log.e(tag, "请确认已经插入SD卡");
		}
	}

	// 点击扫码 得到二维码的值
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case REQUEST_CODE:
			
				// Bundle bundle = data.getExtras();//
				// 这是从MipcaActivityCapture返回来的数据
				// 显示扫描到的内容
				String result = data.getStringExtra("result");

				// 显示

				Log.i("result", "结果：" + result);
				// 得到的二维码json数据解析
				Gson gson = new Gson();
				ErweimaUtil info = new ErweimaUtil();
				info = gson.fromJson(result, ErweimaUtil.class);
				// 取出iid查询数据
				Map<String, String> param = service.getPreferences();
				String str_url = param.get("app_address");
				String url = str_url + "/bank_phone/assets/getInfoByid";
				RequestParams params = new RequestParams(url);
				// params.addBodyParameter("iid", "81");
				params.addBodyParameter("iid", info.getIid());// 传值IID查询数据
				Log.i("iid", "iid=" + info.getIid());
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
						// 查询到数据
						Log.i("info", info);
						Gson gson = new Gson();
						ErweimaSelectUtil util = new ErweimaSelectUtil();
						util = gson.fromJson(info, ErweimaSelectUtil.class);
						et_ccode.setText(util.getCCODE());
						tv_cname.setText(util.getCNAME());
						tv_iclass_name.setText(util.getICLASS_NAME());
						idepartment_name.setText(util.getIDEPARTMENT_NAME());
						dbegin.setText(util.getDBEGIN());
						Log.i("info", util.getDBEGIN());

						String zhuangtai = util.getIUSERTYPE_NAME();
						Log.i("info", zhuangtai);
						if (zhuangtai.equals("在用")) {
							Log.i("info", "在用设置");
							btn_radio01.setChecked(true);

						} else {
							Log.i("info", "闲置设置");
							btn_radio02.setChecked(true);

						}

					}
				});

			
			break;

		case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
			if (resultCode == RESULT_OK) {
				// Bundle bundle = data.getExtras();//
				// 这是从MipcaActivityCapture返回来的数据
				// Log.e(tag, bundle.toString());
				Log.e(tag, "获取图片成功，path=" + picFileFullName);
				toast("获取图片成功，path=" + picFileFullName);
				setImageView(picFileFullName);
			} else if (resultCode == RESULT_CANCELED) {
				// 用户取消了图像捕获
			} else {
				// 图像捕获失败，提示用户
				Log.e(tag, "拍照失败");
			}
			break;
		case PICK_IMAGE_ACTIVITY_REQUEST_CODE:
			if (resultCode == RESULT_OK) {
				Uri uri = data.getData();
				if (uri != null) {
					String realPath = getRealPathFromURI(uri);
					Log.e(tag, "获取图片成功，path=" + realPath);
					toast("获取图片成功，path=" + realPath);
					setImageView(realPath);
				} else {
					Log.e(tag, "从相册获取图片失败");
				}
			}
			break;

		}

	}

	private void setImageView(String realPath) {
		Bitmap bmp = BitmapFactory.decodeFile(realPath);
		int degree = readPictureDegree(realPath);
		if (degree <= 0) {
			imageView.setImageBitmap(bmp);
		} else {
			Log.e(tag, "rotate:" + degree);
			// 创建操作图片是用的matrix对象
			Matrix matrix = new Matrix();
			// 旋转图片动作
			matrix.postRotate(degree);
			// 创建新图片
			Bitmap resizedBitmap = Bitmap.createBitmap(bmp, 0, 0,
					bmp.getWidth(), bmp.getHeight(), matrix, true);
			imageView.setImageBitmap(resizedBitmap);
		}
	}

	/**
	 * This method is used to get real path of file from from uri<br/>
	 * http://stackoverflow.com/questions/11591825/how-to-get-image-path-just-
	 * captured-from-camera
	 * 
	 * @param contentUri
	 * @return String
	 */
	public String getRealPathFromURI(Uri contentUri) {
		try {
			String[] proj = { MediaStore.Images.Media.DATA };
			// Do not call Cursor.close() on a cursor obtained using this
			// method,
			// because the activity will do that for you at the appropriate time
			Cursor cursor = this.managedQuery(contentUri, proj, null, null,
					null);
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} catch (Exception e) {
			return contentUri.getPath();
		}
	}

	/**
	 * 读取照片exif信息中的旋转角度<br/>
	 * http://www.eoeandroid.com/thread-196978-1-1.html
	 * 
	 * @param path
	 *            照片路径
	 * @return角度
	 * 
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	public void toast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

	}

	// 返回上一级
	public void onBack(View v) {

		new Thread() {
			public void run() {
				try {
					Instrumentation inst = new Instrumentation();
					inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
				} catch (Exception e) {
					Log.e("Exception when sendKeyDownUpSync", e.toString());
				}
			}
		}.start();
	}

	private void initView() {
		setContentView(R.layout.activity_pandian);
		tv_uname = (TextView) this.findViewById(R.id.tv_uname);

		et_ccode = (EditText) this.findViewById(R.id.et_ccode);// 资产编号
		tv_cname = (TextView) this.findViewById(R.id.tv_cname);// 资产名称
		tv_iclass_name = (TextView) this.findViewById(R.id.tv_iclass_name);// 类型
		dbegin = (TextView) this.findViewById(R.id.dbegin);// 使用日期
		btn_radio01 = (RadioButton) this.findViewById(R.id.radioButton1);
		btn_radio02 = (RadioButton) this.findViewById(R.id.radioButton2);
		idepartment_name = (TextView) this.findViewById(R.id.idepartment_name);// 机构网点
		imageView = (ImageView) this.findViewById(R.id.image_view);
		button = (Button) this.findViewById(R.id.open_camera);
		btn_erweima = (Button) findViewById(R.id.btn_erweima);
		btn_sure = (Button) findViewById(R.id.btn_sure);
		btn_sure.setOnClickListener(button_Listener);
		button.setOnClickListener(button_Listener);
		btn_erweima.setOnClickListener(button_Listener);
		button.setOnClickListener(button_Listener);

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
		// tv_unamefd
		cname = intent.getStringExtra("cname");
		tv_uname.setText(cname);
		Log.i("ynum", "这是从上次页面过来的数据：" + yNum);
		Log.i("wNum", "这是从上次页面过来的数据：" + wNum);
		Log.i("kNum", "这是从上次页面过来的数据：" + kNum);
		Log.i("iid", "这是从上次页面过来的数据：" + iid);
		Log.i("iCHECKVOUCH", "这是从上次页面过来的数据：" + iCHECKVOUCH);
		Log.i("isUploaddd", "这是从上次页面过来的数据：" + isUploaddd);
		Log.i("position", "这是从上次页面过来的数据：" + position);
		Log.i("cname", "这是从上次页面过来的数据：" + cname);
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
			getDataFromNet(iCHECKVOUCH);
		} else {// 未提交过
				// 如果没有盘点进入未盘和已经盘点
			fragmentList.add(daipan);
			fragmentList.add(yipan);
			titles = new String[] { "待盘数", "已盘数" };
			nums[0] = wCount;
			nums[1] = yCount;
			if (yNum == 0) {
				// 拉取数据
				getDataFromNet(iCHECKVOUCH);

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
	private void getDataFromNet(int iCHECKVOUCH) {

		// 请求网络，拉取数据
		RequestParams requestParams = new RequestParams(str_url
				+ "/bank_phone/assets/getVouchDetails");

		requestParams.addParameter("icheckvouch", iCHECKVOUCH);

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

		result = panDianData.result;// 解析后的数据集合\

		for (int i = 0; i < result.size(); i++) {

			final int finali = i;
			Log.i("istate:", "这是：" + result.get(i).ISTATE_NAME);

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

		values.put(PanDianOpenHelper.PanDianTable.CCODE,
				panDianData.result.get(i).CCODE);

		values.put(PanDianOpenHelper.PanDianTable.CNAME,
				panDianData.result.get(i).CNAME);

		values.put(PanDianOpenHelper.PanDianTable.DBEGIN,
				panDianData.result.get(i).DBEGIN);

		values.put(PanDianOpenHelper.PanDianTable.ICLASS_NAME,
				panDianData.result.get(i).ICLASS_NAME);

		values.put(PanDianOpenHelper.PanDianTable.IDEPARTMENT_NAME,
				panDianData.result.get(i).IDEPARTMENT_NAME);

		values.put(PanDianOpenHelper.PanDianTable.IID,
				panDianData.result.get(i).IID);
		values.put(PanDianOpenHelper.PanDianTable.IUSERTYPE_NAME,
				panDianData.result.get(i).IUSERTYPE_NAME);
		values.put(PanDianOpenHelper.PanDianTable.ISTATE_NAME,
				panDianData.result.get(i).ISTATE_NAME);

		values.put(PanDianOpenHelper.PanDianTable.ICHECKVOUCH, iCHECKVOUCH);

		Log.i("ss", "这是istate_name:" + panDianData.result.get(i).ISTATE_NAME);
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
	@SuppressLint("NewApi")
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
									PanDianOpenHelper.PanDianTable.ISTATE_NAME
											+ "=?"
											+ " and "
											+ PanDianOpenHelper.PanDianTable.ICHECKVOUCH
											+ "=?",
									new String[] { nTYPE, iCHECKVOUCH + "" },
									null);

					Cursor yCursor = getContentResolver()
							.query(PanDianPrivoder.URI_PANDIAN,
									null,
									PanDianOpenHelper.PanDianTable.ISTATE_NAME
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

	/** 显示自定义Toast提示(来自String) **/
	protected void showCustomToast(String text) {
		View toastRoot = LayoutInflater.from(PanDianActivity.this).inflate(
				R.layout.common_toast, null);
		((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(PanDianActivity.this);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}

	/*
	 * 将值恢复为原始，(备用)
	 */

}
