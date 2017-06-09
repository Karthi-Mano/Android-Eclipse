package com.itheima.volleydemo_8;

import java.io.UnsupportedEncodingException;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * @author  Administrator
 * @time 	2015-7-22 上午11:24:34
 * @des	TODO
 *
 * @version $Rev$
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes TODO
 */
public class GsonRequest<T> extends Request<T> {
	Class<T>			clazz;		// 具体bean的类型
	private Listener<T>	mListener;

	public GsonRequest(int method, String url, ErrorListener listener, Listener<T> mListener, Class<T> clazz) {
		super(method, url, listener);
		this.clazz = clazz;
		this.mListener = mListener;
	}

	/**
	 * 处理网络请求
	 */
	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		String jsonString;
		try {
			jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
		} catch (UnsupportedEncodingException e) {
			jsonString = new String(response.data);
		}
		// jsonString-->XXX.bean
		T obj;
		try {
			Gson gson = new Gson();
			obj = gson.fromJson(jsonString, clazz);
			// 返回结果
			return Response.success(obj, HttpHeaderParser.parseCacheHeaders(response));
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			// 返回结果
			return Response.error(new ParseError());
		}
	}

	/**
	 * 传递响应结果
	 */
	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);
	}
}
