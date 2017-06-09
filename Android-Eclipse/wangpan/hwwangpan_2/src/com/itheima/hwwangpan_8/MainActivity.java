package com.itheima.hwwangpan_8;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
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
public class MainActivity extends Activity implements IDataCallBack {

	private PullToRefreshListView	mPullToRefreshListView;
	/**
	 * 使用VDiskAPI，可以调用所有的微盘接口，非常重要。
	 * 
	 * Use VDiskAPI for calling all the API of VDisk, IMPOPTANT.
	 */
	VDiskAPI<VDiskAuthSession>		mApi;
	List<Entry>						mEntries	= new ArrayList<VDiskAPI.Entry>();
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		initView();
		initData();
		initListener();
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
	}

	private void initData() {
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
		CloudEngine.getInstance(this).getFileList(this, CloudEngine.REQ_FILE_LIST);
		mAdapter = new FileListAdapter();
		mListView.setAdapter(mAdapter);

	}

	private void initListener() {
		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// 重新请求网络加载数据
				initData();

			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				position = position - mListView.getHeaderViewsCount();// 处理添加头之后的position问题
				Entry entry = mEntries.get(position);
				// 点击删除文件
				CloudEngine.getInstance(MainActivity.this).deleteFile(MainActivity.this, entry.path,
						CloudEngine.REQ_FILE_DELETE);
			}
		});
	}

	class FileListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (mEntries != null) {
				return mEntries.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			if (mEntries != null) {
				return mEntries.get(position);
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
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
			Entry entry = mEntries.get(position);
			holder.tvName.setText(entry.fileName());
			holder.tvSize.setText(entry.size);

			Date parseDate = RESTUtility.parseDate(entry.modified);
			holder.tvTime.setText(Utils.getFormateTime(parseDate));

			if (entry.isDir) {// 如果是文件夹
				holder.ivIcon.setImageResource(R.drawable.directory_icon);
				// 隐藏大小显示
				holder.tvSize.setVisibility(8);
			} else {
				holder.tvSize.setVisibility(0);
				// 根据文件类型做不同的展示
				Object[] mimeType = Utils.getMIMEType(entry.fileName());
				// type = "image/*";
				// res[1] = R.drawable.picture_icon;
				// mimeType[0]-->mimetype
				// mimeType[1]-->就是mimeType对应应该显示的图片
				holder.ivIcon.setImageResource((Integer) mimeType[1]);
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
			mEntries = (List<Entry>) data;
			// 刷新ui
			mAdapter.notifyDataSetChanged();
			break;
		case CloudEngine.REQ_FILE_DELETE:// 文件删除对应的结果
			Entry deletedEntry = (Entry) data;
			Toast.makeText(getApplicationContext(), "删除 " + deletedEntry.fileName() + " 成功", 0).show();
			break;

		default:
			break;
		}
	}
}
