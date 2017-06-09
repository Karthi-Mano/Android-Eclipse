package com.itheima.googleplay_13.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.itheima.googleplay_13.conf.Constants;
import com.itheima.googleplay_13.conf.Constants.URlS;
import com.itheima.googleplay_13.utils.FileUtils;
import com.itheima.googleplay_13.utils.IOUtils;
import com.itheima.googleplay_13.utils.LogUtils;
import com.itheima.googleplay_13.utils.UIUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * @param <T>
 * @创建者	 Administrator
 * @创时间 	 2015-10-23 上午9:14:08
 * @描述	     TODO
 *
 * @版本       $Rev: 71 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-25 18:39:31 +0800 (星期日, 25 十月 2015) $
 * @更新描述    TODO
 */
public abstract class BaseProtocol<T> {
	public T loadData(int index) throws Exception {

		T t = getDataFromMeory(index);
		if (t != null) {
			Map<String, String> extraParmas = getExtraParmas();
			if (extraParmas != null) {
				LogUtils.s("###从内存加载数据-->" + extraParmas.get("packageName"));
			} else {
				LogUtils.s("###从内存加载数据-->" + getInterceKey() + "." + index);
			}
			return t;
		}

		t = getDataFromLocal(index);
		// 假如t是null
		// 1.没有缓存或者缓存过期
		// 2.出现了异常

		if (t != null) {
			LogUtils.s("###从本地加载数据-->" + getCacheFile(index).getAbsolutePath());
			return t;
		}

		return getDataFromNet(index);
	}

	/**
	 * 从内测加载数据
	 * @param index
	 * @return
	 */
	private T getDataFromMeory(int index) {

		BaseApplication app = (BaseApplication) UIUtils.getContext();
		Map<String, String> cacheMap = app.getCacheMap();

		Map<String, String> extraParmas = getExtraParmas();
		String key;
		if (extraParmas != null) {
			key = getInterceKey() + "." + extraParmas.get("packageName");
		} else {
			key = getInterceKey() + "." + index;
		}
		String cacheJsonString = cacheMap.get(key);// null
		if (cacheJsonString != null) {
			return parseJsonString(cacheJsonString);
		}
		return null;
	}

	/**
	 * 从本地加载数据
	 * @param index
	 * @return
	 */
	private T getDataFromLocal(int index) {
		/**
		 if(文件存在){
				//读取插入时间
				//判断是否过期
				if(未过期){
					//读取缓存内容
					//Json解析解析内容
					if(不为null){
						//返回并结束
					}	
				}
			}
		 */
		/**
			1.file
			2.sdcard/Android/data/data包目录
			3.interfacekey+"."+index
			4.
			第一行:数据的生成时间(插入时间)
			第二行:协议的真正内容
		 */
		try {
			File cacheFile = getCacheFile(index);

			if (cacheFile.exists()) {
				BufferedReader reader = null;
				try {
					// 读取插入时间
					reader = new BufferedReader(new FileReader(cacheFile));
					// 读取第一行
					String insertTimeStr = reader.readLine();
					long insertTime = Long.parseLong(insertTimeStr);
					// 判断是否过期
					if (System.currentTimeMillis() - insertTime < Constants.PROTOCOLTIMEOUT) {
						// 读取有效的缓存数据
						String cacheJsonString = reader.readLine();
						/*--------------- 缓存协议内容到内存中 ---------------*/
						Map<String, String> extraParmas = getExtraParmas();
						BaseApplication app = (BaseApplication) UIUtils.getContext();
						Map<String, String> cacheMap = app.getCacheMap();
						String key = "";
						if (extraParmas != null) {
							key = getInterceKey() + "." + extraParmas.get("packageName");
						} else {
							key = getInterceKey() + "." + index;
						}
						cacheMap.put(key, cacheJsonString);
						/*--------------- json解析 ---------------*/
						T t = parseJsonString(cacheJsonString);
						return t;
					}
				} finally {
					IOUtils.close(reader);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 得到缓存的文件
	 * @param index
	 * @return
	 */
	public File getCacheFile(int index) {
		// 文件存到哪里
		String dir = FileUtils.getDir("json");// sdcard/Android/data/包目录/json
		// 文件名需要保证唯一索引性
		Map<String, String> extraParmas = getExtraParmas();
		String fileName;
		if (extraParmas != null) {// 详情协议
			fileName = getInterceKey() + "." + extraParmas.get("packageName");
		} else {
			fileName = getInterceKey() + "." + index;
		}

		File cacheFile = new File(dir, fileName);
		return cacheFile;
	}

	/**
	 * 从网络加载数据
	 * @param index
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public T getDataFromNet(int index) throws HttpException, IOException {
		/*--------------- 得到jsonString的过程 ---------------*/
		HttpUtils httpUtils = new HttpUtils();
		// "http://localhost:8080/GooglePlayServer/home?index=0"
		String url = URlS.BASEURL + getInterceKey();

		RequestParams params = new RequestParams();

		Map<String, String> extraParmas = getExtraParmas();

		if (extraParmas != null) {
			for (Map.Entry<String, String> info : extraParmas.entrySet()) {
				String key = info.getKey();// "packageName"
				String value = info.getValue();// 具体的包名
				params.addQueryStringParameter(key, value);
			}
		} else {
			params.addQueryStringParameter("index", index + "");
		}

		ResponseStream responseStream = httpUtils.sendSync(HttpMethod.GET, url, params);

		String jsonString = responseStream.readString();
		/*--------------- 缓存协议内容到内存中 ---------------*/
		BaseApplication app = (BaseApplication) UIUtils.getContext();
		Map<String, String> cacheMap = app.getCacheMap();
		String key = "";
		if (extraParmas != null) {
			key = getInterceKey() + "." + extraParmas.get("packageName");
		} else {
			key = getInterceKey() + "." + index;
		}
		cacheMap.put(key, jsonString);
		/*--------------- 缓存协议内容到file中 ---------------*/
		File cacheFile = getCacheFile(index);

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(cacheFile));
			// 写入第一行
			writer.write(System.currentTimeMillis() + "");
			// 换行
			writer.newLine();
			// 写入第二行
			writer.write(jsonString);
		} finally {
			IOUtils.close(writer);
		}

		/*--------------- json解析的过程 ---------------*/
		// bean
		// List<bean>
		T t = parseJsonString(jsonString);
		return t;
	}

	/**
	 * @des 传递额外的参数
	 * @des 子类可以覆写该方法,传递额外的参数过来
	 * @return
	 */
	protected Map<String, String> getExtraParmas() {
		// TODO
		return null;// 默认是null
	}

	/**
	 * @des 返回协议的关键字
	 * @des 必须实现,但是不知道具体实现,定义成为抽象方法,交给子类具体实现
	 * @call BaseProtocol作为基类,调用loadData方法的时候
	 */
	protected abstract String getInterceKey();

	/**
	 * @des 具体的json解析过程
	 * @des 必须实现,但是不知道具体实现,定义成为抽象方法,交给子类具体实现
	 * @call BaseProtocol作为基类,调用loadData方法的时候
	 */
	protected T parseJsonString(String jsonString) {
		// T-->Type
		Gson gson = new Gson();
		// getClass()得到字节码对象
		// getGenericSuperclass()返回本类的父类,包含泛型参数信息
		
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		Type type = actualTypeArguments[0];

		return gson.fromJson(jsonString, type);
	};

}
