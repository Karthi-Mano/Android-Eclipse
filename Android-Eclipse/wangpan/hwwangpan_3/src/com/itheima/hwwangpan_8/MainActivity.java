package com.itheima.hwwangpan_8;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.itheima.hwwangpan_8.bean.EntryWrapper;
import com.itheima.hwwangpan_8.net.CloudEngine;
import com.itheima.hwwangpan_8.net.IDataCallBack;
import com.itheima.hwwangpan_8.utils.ExceptionHandler;
import com.itheima.hwwangpan_8.utils.Utils;
import com.vdisk.android.VDiskAuthSession;
import com.vdisk.net.RESTUtility;
import com.vdisk.net.VDiskAPI;
import com.vdisk.net.VDiskAPI.Entry;
import com.vdisk.net.session.AppKeyPair;
import com.vdisk.net.session.Session.AccessType;

/**
 * @author  Administrator
 * @time 	2015-7-27 上午8:57:48
 * @des	TODO
 *
 * @version $Rev$
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes TODO
 */
public class MainActivity extends SherlockActivity implements IDataCallBack {

	private PullToRefreshListView	mPullToRefreshListView;
	/**
	 * 使用VDiskAPI，可以调用所有的微盘接口，非常重要。
	 * 
	 * Use VDiskAPI for calling all the API of VDisk, IMPOPTANT.
	 */
	VDiskAPI<VDiskAuthSession>		mApi;
	List<EntryWrapper>				mEntryWrappers	= new ArrayList<EntryWrapper>();
	/*	private Handler					mHandler	= new Handler() {
														public void handleMessage(Message msg) {
															// 2. 加载完成,关闭加载效果
															mPullToRefreshListView.onRefreshComplete();
															mEntries = (List<Entry>) msg.obj;
															// 刷新ui
															mAdapter.notifyDataSetChanged();
														};
													};*/
	private FileListAdapter			mAdapter;
	private ListView				mListView;
	private String					mCurPath		= "/";								// 默认是根目录,记录当前的路径
	private long					mPreTime;
	private ActionBar				mActionBar;
	private PopupWindow				mOptionPopupWindow;
	private int						mClickPostion;

	// private Map<Integer, Boolean> mCheckMap = new HashMap<Integer, Boolean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		initView();
		initData(mCurPath);
		initListener();
		initActionBar();
	}

	private void initActionBar() {
		mActionBar = getSupportActionBar();
		// 隐藏图标
		mActionBar.setDisplayShowHomeEnabled(false);
		mActionBar.setTitle("黑马网盘");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 需要使用getSupportMenuInflater不是getMenuInflater()
		MenuInflater menuInflater = getSupportMenuInflater();
		menuInflater.inflate(R.menu.main, menu);
		// 找出所有的actionbutton
		mUploadedItem = menu.findItem(R.id.action_uploaded);
		mDownloadItem = menu.findItem(R.id.action_download);
		mMoreItem = menu.findItem(R.id.action_more);
		mSelectItem = menu.findItem(R.id.action_select);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// Toast.makeText(getApplicationContext(), "home", 0).show();
			back();
			break;
		case R.id.action_uploaded:
			Toast.makeText(getApplicationContext(), "action_uploaded", 0).show();

			break;
		case R.id.action_download:
			Toast.makeText(getApplicationContext(), "action_download", 0).show();

			break;
		case R.id.action_newfolder:
			Toast.makeText(getApplicationContext(), "action_newfolder", 0).show();

			break;
		case R.id.action_upload:
			Toast.makeText(getApplicationContext(), "action_upload", 0).show();

			break;
		case R.id.action_logout:
			Toast.makeText(getApplicationContext(), "action_logout", 0).show();

			break;
		case R.id.action_select:
			String title = mSelectItem.getTitle().toString();
			if ("全选".equals(title)) {
				selectAll();
				mSelectItem.setTitle("取消");
			} else {
				unSelectAll();
				mSelectItem.setTitle("全选");
			}
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**全选*/
	public void selectAll() {
		// 数据的改变
		for (EntryWrapper entryWrapper : mEntryWrappers) {
			entryWrapper.isCheck = true;
		}
		mSeletedCount = mEntryWrappers.size();

		// 重新刷新listview
		mAdapter.notifyDataSetChanged();
		// 重新更新actionBar
		mActionBar.setTitle(String.format("已选定%d个", mSeletedCount));
	}

	/**取消全选*/
	public void unSelectAll() {
		// 数据的改变
		for (EntryWrapper entryWrapper : mEntryWrappers) {
			entryWrapper.isCheck = false;
		}
		mSeletedCount = 0;

		// 重新刷新listview
		mAdapter.notifyDataSetChanged();
		// 重新更新actionBar
		mActionBar.setTitle(String.format("已选定%d个", mSeletedCount));
	}

	private void init() {
		AppKeyPair appKeyPair = new AppKeyPair(LoginActivity.CONSUMER_KEY, LoginActivity.CONSUMER_SECRET);
		/**
		   VDISK("basic"), APP_FOLDER("sandbox");
		   
		   //正式环境-->数据是真实
		   //测试环境-->数据也是真实-->支付(1分钱)
		 */
		VDiskAuthSession session = VDiskAuthSession.getInstance(this, appKeyPair, AccessType.VDISK);

		mApi = new VDiskAPI<VDiskAuthSession>(session);
	}

	private void initView() {
		mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.listView);
		mListView = mPullToRefreshListView.getRefreshableView();
		mMain_root = findViewById(R.id.main_root);
		mViewHolder = findViewById(R.id.viewHolder);
	}

	private void initData(String path) {
		// 加载数据的时候应该显示下载刷新中的视图
		mPullToRefreshListView.setRefreshing();// 1. 显示正在加载的一个效果
		// 拉取根目录文件信息
		/*new Thread(new Runnable() {

			@Override
			public void run() {
				SystemClock.sleep(2000);
				try {
					Entry metadata = mApi.metadata("/", null, true, false);
					List<Entry> contents = metadata.contents;

					Message msg = Message.obtain();
					msg.obj = contents;

					// 需要handler发送消息到主线程
					mHandler.sendMessage(msg);
				} catch (VDiskException e) {
					e.printStackTrace();
				}

			}
		}).start();*/
		CloudEngine.getInstance(this).getFileList(this, path, CloudEngine.REQ_FILE_LIST);
		mAdapter = new FileListAdapter();
		mListView.setAdapter(mAdapter);

	}

	private void initListener() {
		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// 重新请求网络加载数据
				// path = "/"-->刷新根目录
				// path = "/美女"-->刷新子目录
				initData(mCurPath);
			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				position = position - mListView.getHeaderViewsCount();// 处理添加头之后的position问题
				if (isEditMode) {
					/*		Boolean isCheck = mCheckMap.get(position);
							if (isCheck != null) {
								if (isCheck) {// 当前是选中效果
									mCheckMap.put(position, false);
								} else {
									mCheckMap.put(position, true);
								}
								mAdapter.notifyDataSetChanged();// 刷新
							} else {
								mCheckMap.put(position, true);
								mAdapter.notifyDataSetChanged();// 刷新
							}*/

					mAdapter.updateItemView(view, position);
				} else {
					EntryWrapper entryWrapper = mEntryWrappers.get(position);
					// 进入子目录
					if (entryWrapper.entry.isDir) {
						enterFolder(entryWrapper.entry);
					}
				}
			}

		});
		// 监听长按事件
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				position = position - mListView.getHeaderViewsCount();
				startEditMode();
				mAdapter.updateItemView(view, position);
				return true;
			}

		});
	}

	private boolean		isEditMode;
	private int			mSeletedCount;
	private MenuItem	mUploadedItem;
	private MenuItem	mDownloadItem;
	private MenuItem	mMoreItem;
	private MenuItem	mSelectItem;
	private PopupWindow	mBottomPopupWindow;
	private View		mMain_root;
	private View	mViewHolder;

	/**开启编辑模式*/
	private void startEditMode() {
		isEditMode = true;
		// 刷新listview
		mAdapter.notifyDataSetChanged();
		// 修改actionbar
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle(String.format("已选定%d个", mSeletedCount));

		mUploadedItem.setVisible(false);
		mDownloadItem.setVisible(false);
		mMoreItem.setVisible(false);
		mSelectItem.setVisible(true);
		// 显示popupwindows
		showBottomPopupWindow();
		mViewHolder.setVisibility(View.INVISIBLE);
	}

	private void showBottomPopupWindow() {
		if (mBottomPopupWindow == null) {
			View contentView = View.inflate(MainActivity.this, R.layout.bottom_edit_pop, null);
			int width = ViewGroup.LayoutParams.MATCH_PARENT;
			int height = ViewGroup.LayoutParams.WRAP_CONTENT;
			mBottomPopupWindow = new PopupWindow(contentView, width, height);
		}
		mBottomPopupWindow.showAtLocation(mMain_root, Gravity.BOTTOM, 0, 0);
	}

	/**结束编辑模式*/
	private void stopEditMode() {
		isEditMode = false;
		// 刷新listview
		mAdapter.notifyDataSetChanged();
		// 修改actionbar
		mActionBar.setDisplayHomeAsUpEnabled(false);
		mActionBar.setTitle("黑马网盘");

		mUploadedItem.setVisible(true);
		mDownloadItem.setVisible(true);
		mMoreItem.setVisible(true);
		mSelectItem.setVisible(false);
		// 还原已经选择的entryWrapper
		for (EntryWrapper entryWrapper : mEntryWrappers) {
			entryWrapper.isCheck = false;
		}
		mSeletedCount = 0;
		// 隐藏popupwindows
		if (mBottomPopupWindow != null) {
			mBottomPopupWindow.dismiss();
		}
		mViewHolder.setVisibility(View.GONE);
	}

	/**进入子目录*/
	private void enterFolder(Entry entry) {
		// path= /
		// path = /美女
		initData(entry.path);
		// 记录当前路径
		mCurPath = entry.path;
		System.out.println("####mCurPath: " + mCurPath);
		// 显示actionbar的返回按钮
		mActionBar.setDisplayHomeAsUpEnabled(true);
	}

	class FileListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (mEntryWrappers != null) {
				return mEntryWrappers.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			if (mEntryWrappers != null) {
				return mEntryWrappers.get(position);
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			System.out.println("========getView=========");
			ViewHolder holder;
			if (convertView == null) {
				convertView = MainActivity.this.getLayoutInflater().inflate(R.layout.file_item, null);

				holder = new ViewHolder();
				holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
				holder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
				holder.tvSize = (TextView) convertView.findViewById(R.id.tv_size);
				holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
				holder.ivOption = (ImageView) convertView.findViewById(R.id.iv_option);
				holder.cbCheck = (CheckBox) convertView.findViewById(R.id.cb_checkbox);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			// 得到数据
			// 设置数据
			EntryWrapper entryWrapper = mEntryWrappers.get(position);
			holder.tvName.setText(entryWrapper.entry.fileName());
			holder.tvSize.setText(entryWrapper.entry.size);

			Date parseDate = RESTUtility.parseDate(entryWrapper.entry.modified);
			holder.tvTime.setText(Utils.getFormateTime(parseDate));

			if (entryWrapper.entry.isDir) {// 如果是文件夹
				holder.ivIcon.setImageResource(R.drawable.directory_icon);
				// 隐藏大小显示
				holder.tvSize.setVisibility(8);
			} else {
				holder.tvSize.setVisibility(0);
				// 根据文件类型做不同的展示
				Object[] mimeType = Utils.getMIMEType(entryWrapper.entry.fileName());
				// type = "image/*";
				// res[1] = R.drawable.picture_icon;
				// mimeType[0]-->mimetype
				// mimeType[1]-->就是mimeType对应应该显示的图片
				holder.ivIcon.setImageResource((Integer) mimeType[1]);
			}
			/*--------------- 点击option图标 ---------------*/
			holder.ivOption.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View ivOption) {
					mClickPostion = position;
					showPopupWindow(ivOption);
				}

			});
			/*--------------- 处理编辑模式 ---------------*/
			if (isEditMode) {// 编辑模式
				holder.cbCheck.setVisibility(0);
				holder.ivOption.setVisibility(8);
				/*				Boolean isCheck = mCheckMap.get(position);
								if (isCheck != null) {
									if (isCheck) {
										holder.cbCheck.setChecked(true);
									} else {
										holder.cbCheck.setChecked(false);
									}
								}*/
				if (entryWrapper.isCheck) {
					holder.cbCheck.setChecked(true);
				} else {
					holder.cbCheck.setChecked(false);
				}

			} else {
				holder.cbCheck.setVisibility(8);
				holder.ivOption.setVisibility(0);
			}
			return convertView;
		}

		class ViewHolder {
			TextView	tvName;
			TextView	tvTime;
			TextView	tvSize;

			ImageView	ivIcon;
			ImageView	ivOption;

			CheckBox	cbCheck;
		}

		/*=============== 定义一个itemView局部刷新的方法 ===============*/
		public void updateItemView(View itemView, int position) {
			// 拿到当前itemView对应的数据
			EntryWrapper entryWrapper = mEntryWrappers.get(position);

			CheckBox cb = (CheckBox) itemView.findViewById(R.id.cb_checkbox);
			if (cb.isChecked()) {
				cb.setChecked(false);
				entryWrapper.isCheck = false;
				mSeletedCount--;
			} else {
				cb.setChecked(true);
				entryWrapper.isCheck = true;
				mSeletedCount++;
			}
			// 更新actionBar
			mActionBar.setTitle(String.format("已选定%d个", mSeletedCount));
			if (mSeletedCount < mEntryWrappers.size()) {
				mSelectItem.setTitle("全选");
			} else {
				mSelectItem.setTitle("取消");
			}

			List<EntryWrapper> seletedEntryWrappers = new ArrayList<EntryWrapper>();
			for (EntryWrapper info : mEntryWrappers) {
				if (info.isCheck == true) {// 当前是选中
					seletedEntryWrappers.add(info);
				}
			}
			for (EntryWrapper info : seletedEntryWrappers) {
				System.out.println(info.entry.fileName());
			}
		}
	}

	/**
	 * 点击ivOption的时候显示popupwindow
	 * @param clickPostion 
	 */
	private void showPopupWindow(View ivOption) {
		View itemView = (View) ivOption.getParent();// 条目
		if (mOptionPopupWindow == null) {
			View contentView = View.inflate(MainActivity.this, R.layout.file_item_pop, null);
			int width = ViewGroup.LayoutParams.MATCH_PARENT;
			int height = itemView.getHeight();
			mOptionPopupWindow = new PopupWindow(contentView, width, height, true);

			// 点击事件
			contentView.findViewById(R.id.ll_delete).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					mOptionPopupWindow.dismiss();
					String path = mEntryWrappers.get(mClickPostion).entry.path;
					// Toast.makeText(getApplicationContext(), path, 0).show();
					CloudEngine.getInstance(MainActivity.this).deleteFile(MainActivity.this, path,
							CloudEngine.REQ_FILE_DELETE);
				}
			});
		}
		// 点击popupwindows范围以外的地方,让其消失
		mOptionPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		mOptionPopupWindow.setOutsideTouchable(true);
		// 控制它放置的位置
		if (isShowBottom(itemView)) {// 显示在条目下边
			mOptionPopupWindow.showAsDropDown(itemView, 0, 0);
		} else {// 显示在条目的上边
			mOptionPopupWindow.showAsDropDown(itemView, 0, -2 * itemView.getHeight());
		}
	}

	private boolean isShowBottom(View itemView) {
		// int heightPixels = getResources().getDisplayMetrics().heightPixels;
		int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
		int[] location = new int[2];
		// int[0]-->x
		// int[1]-->y
		itemView.getLocationInWindow(location);
		int itemViewY = location[1];

		int distance = screenHeight - itemViewY - itemView.getHeight();

		if (distance > itemView.getHeight()) {// 下面可以显示下popupwindows
			return true;
		} else {// 下面不可以显示下popupwindows
			return false;
		}
	}

	/*===============  统一处理网络访问的结果,统一异常处理,异常细分化,自定义异常 ===============*/
	// 1.拉取文件信息列表的时候-->req_fileList
	// 2.删除文件的时候-->req_fileDelete
	@Override
	public void handleServerData(int reqCode, int errCode, Object data) {
		if (errCode != 0) {// 存在异常
			// 根据不同errcode做不同的提示
			ExceptionHandler.toastErrMessage(MainActivity.this, errCode);
			return;
		}
		switch (reqCode) {
		case CloudEngine.REQ_FILE_LIST:// 文件信息列表对应的结果
			// 2. 加载完成,关闭加载效果
			mPullToRefreshListView.onRefreshComplete();
			List<Entry> entris = (List<Entry>) data;

			for (Entry entry : entris) {
				EntryWrapper entryWrapper = new EntryWrapper();
				entryWrapper.entry = entry;
				mEntryWrappers.add(entryWrapper);
			}

			// 刷新ui
			mAdapter.notifyDataSetChanged();
			break;
		case CloudEngine.REQ_FILE_DELETE:// 文件删除对应的结果
			Entry deletedEntry = (Entry) data;

			EntryWrapper deletedEntryWrapper = new EntryWrapper();
			deletedEntryWrapper.entry = deletedEntry;

			// 修改数据源
			mEntryWrappers.remove(deletedEntryWrapper);

			// 遍历mEntries,判断如果path和deletedEntry的path相等,说明是同一个entry
			/*--------------- 这个方式会抛出异常java.util.ConcurrentModificationException---------------*/
			/*	for (Entry entry : mEntryWrappers) {
				if (entry.path.equals(deletedEntry.path)) {
					mEntryWrappers.remove(entry);
				}
			} */
			/*--------------- 这个方式可行---------------*/
			/*		Iterator<Entry> iterator = mEntries.iterator();
					while (iterator.hasNext()) {
						Entry entry = iterator.next();
						if (entry.path.equals(deletedEntry.path)) {
							iterator.remove();
						}
					}*/
			// 刷新listview
			mAdapter.notifyDataSetChanged();
			break;

		default:
			break;
		}
	}

	/**监听返回按钮的方式一*/
	/*	@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if(keyCode==KeyEvent.KEYCODE_BACK){//点击了返回按钮
				System.out.println("点击了返回按钮");
				return true;
			}
			return super.onKeyDown(keyCode, event);
		}*/
	/*
	 /--->点击退出
	 /种子-->""
	 /种子/种子a-->/种子
	 */
	@Override
	public void onBackPressed() {
		back();
	}

	/**
	 * 回退操作,对应点击了返回按钮和actionbar的返回按钮是一样的操作
	 */
	public void back() {

		if (isEditMode) {
			stopEditMode();
			return;
		}

		// 如果当前是根目录
		if ("/".equals(mCurPath)) {
			if (System.currentTimeMillis() - mPreTime > 2000) {// 时间间隔为2s以上
				// 提示
				Toast.makeText(getApplicationContext(), "再按一次,退出黑马网盘", 0).show();
				// 赋值
				mPreTime = System.currentTimeMillis();
				// 不做任何操作
				return;
			}
			finish();
		} else {
			int index = mCurPath.lastIndexOf("/");
			mCurPath = mCurPath.substring(0, index);

			if ("".equals(mCurPath)) {
				mCurPath = "/";
				// 隐藏actionbar对应的返回按钮
				mActionBar.setDisplayHomeAsUpEnabled(false);
			}
			initData(mCurPath);
		}
	}
}
