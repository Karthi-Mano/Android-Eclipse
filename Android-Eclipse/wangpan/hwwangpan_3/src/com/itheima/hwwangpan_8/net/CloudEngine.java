package com.itheima.hwwangpan_8.net;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;

import com.itheima.hwwangpan_8.LoginActivity;
import com.itheima.hwwangpan_8.utils.ExceptionHandler;
import com.vdisk.android.VDiskAuthSession;
import com.vdisk.net.VDiskAPI;
import com.vdisk.net.VDiskAPI.Entry;
import com.vdisk.net.exception.VDiskException;
import com.vdisk.net.exception.VDiskFileNotFoundException;
import com.vdisk.net.exception.VDiskFileSizeException;
import com.vdisk.net.exception.VDiskIOException;
import com.vdisk.net.exception.VDiskLocalStorageFullException;
import com.vdisk.net.exception.VDiskParseException;
import com.vdisk.net.exception.VDiskPartialFileException;
import com.vdisk.net.exception.VDiskServerException;
import com.vdisk.net.exception.VDiskUnlinkedException;
import com.vdisk.net.session.AppKeyPair;
import com.vdisk.net.session.Session.AccessType;

/**
 * @author  Administrator
 * @time 	2015-7-27 下午2:24:46
 * @des	网络请求的管理类,统一管理所有的网络请求
 *
 * @version $Rev$
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes TODO
 */
public class CloudEngine {
	public static CloudEngine	instance;
	VDiskAPI<VDiskAuthSession>	mApi;
	public static final int		REQ_FILE_LIST	= 100;
	public static final int		REQ_FILE_DELETE	= 101;
	Context						ctx;

	private CloudEngine(Context context) {
		// 在构造方法里面初始化一些访问api需要的类
		ctx = context;
		init(context);
	}

	private void init(Context context) {
		AppKeyPair appKeyPair = new AppKeyPair(LoginActivity.CONSUMER_KEY, LoginActivity.CONSUMER_SECRET);
		/**
		   VDISK("basic"), APP_FOLDER("sandbox");
		   
		   //正式环境-->数据是真实
		   //测试环境-->数据也是真实-->支付(1分钱)
		 */
		VDiskAuthSession session = VDiskAuthSession.getInstance(context, appKeyPair, AccessType.VDISK);

		mApi = new VDiskAPI<VDiskAuthSession>(session);
	}

	public static CloudEngine getInstance(Context context) {
		if (instance == null) {
			synchronized (CloudEngine.class) {
				if (instance == null) {
					instance = new CloudEngine(context);
				}
			}
		}
		return instance;
	}

	/**得到文件夹列表
	 * @param path */
	public void getFileList(IDataCallBack dataCallBack, String path, int reqCode) {
		new FileListTask(dataCallBack, path,reqCode).execute();
	}

	/**删除文件*/
	public void deleteFile(IDataCallBack dataCallBack, String path, int reqCode) {
		new FileDeleteTask(dataCallBack, path, reqCode).execute();
	}

	class FileDeleteTask extends BaseTask {
		String	mPath;

		public FileDeleteTask(IDataCallBack dataCallBack, String path, int reqCode) {
			super(dataCallBack, reqCode);
			mPath = path;
		}

		@Override
		protected Void doInBackground(Void... params) {
			// 真正的发起请求删除文件
			try {
				Entry deletedEntry = mApi.delete(mPath);// 删除完成会返回当前删除的对象
				mEvent.data = deletedEntry;// 数据的赋值
			} catch (VDiskException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				updateEvent(ctx, e, mEvent);
			}
			return null;
		}
	}

	class FileListTask extends BaseTask {

		/**
		 * 通过构造方法初始化IDataCallBack
		 * @param dataCallBack
		 * @param path 
		 * @param reqCode 
		 */
		String mPath;
		public FileListTask(IDataCallBack dataCallBack, String path, int reqCode) {
			super(dataCallBack, reqCode);
			mEvent.reqCode = reqCode;// 请求码赋值
			mPath = path;
		}

		@Override
		protected Void doInBackground(Void... params) {// 子线程
			try {
				Entry metadata = mApi.metadata(mPath, null, true, false);
				List<Entry> contents = metadata.contents;
				mEvent.data = contents;// 对应的数据赋值
				// 传递数据
				// 请求码-->区分不同的请求-->请求的统一管理
				// 错误码-->区分不同错误信息-->错误的统一管理
			} catch (VDiskException e) {
				e.printStackTrace();
				updateEvent(ctx, e, mEvent);
			}
			return null;
		}

	}

	/*---------------  抽取asynctask的基类 ---------------*/
	abstract class BaseTask extends AsyncTask<Void, Void, Void> {

		IDataCallBack	mDataCallBack;
		Event			mEvent;

		/**
		 * 通过构造方法初始化IDataCallBack
		 * @param dataCallBack
		 * @param reqCode 
		 */
		public BaseTask(IDataCallBack dataCallBack, int reqCode) {
			super();
			mDataCallBack = dataCallBack;
			mEvent = new Event();
			mEvent.reqCode = reqCode;// 请求码赋值
		}

		@Override
		protected void onPostExecute(Void result) {
			// 需要用接口对象 传递数据
			mDataCallBack.handleServerData(mEvent.reqCode, mEvent.errCode, mEvent.data);
			super.onPostExecute(result);
		}

	}

	/*--------------- 异常信息具体化 ---------------*/
	/**
	 * 对event赋予不同的errcode
	 * @param ctx
	 * @param e 基类的异常
	 * @param event 
	 * @return
	 */
	public static Event updateEvent(Context ctx, VDiskException e, Event event) {
		if (event == null) {
			event = new Event();
		}
		if (e instanceof VDiskServerException) {
			return ExceptionHandler.getErrEvent(ctx, (VDiskServerException) e, event);
		} else if (e instanceof VDiskIOException) {
			event.errCode = ExceptionHandler.VdiskConnectionFailureErrorType;
		} else if (e instanceof VDiskParseException) {
			event.errCode = ExceptionHandler.kVdiskErrorInvalidResponse;
		} else if (e instanceof VDiskLocalStorageFullException) {
			event.errCode = ExceptionHandler.kVdiskErrorInsufficientDiskSpace;
		} else if (e instanceof VDiskUnlinkedException) {
			event.errCode = ExceptionHandler.UNLINKED_ERROR;
		} else if (e instanceof VDiskFileSizeException) {
			event.errCode = ExceptionHandler.FILE_TOO_BIG_ERROR;
		} else if (e instanceof VDiskPartialFileException) {
			event.errCode = ExceptionHandler.PARTIAL_FILE_ERROR;
		} else if (e instanceof VDiskFileNotFoundException) {
			event.errCode = ExceptionHandler.FILE_NOT_FOUND;
		} else {
			event.errCode = ExceptionHandler.OTHER_ERROR;
		}
		return event;
	}
}
