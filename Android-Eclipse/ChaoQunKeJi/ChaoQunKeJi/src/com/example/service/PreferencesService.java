package com.example.service;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferencesService {
	private Context context;

	// 构造方法中传入上下文对象
	public PreferencesService(Context context) {
		super();
		this.context = context;
	}
    /**
     * 保存参数用户名称cusecode
     */
	public void saveUser(String username){
		
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"itcastPreference", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString("username", username);
		
		editor.commit(); 
	}
	/**
	 * 保存参数app_address
	 */
	public void save(String app_address ) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"itcastPreference", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString("app_address", app_address);
		// 
		editor.commit(); 
	}
	public void saveName(String u_name ) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"itcastPreference", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString("u_name", u_name);
		// 
		editor.commit(); 
	}
	public void saveID(String u_id ) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"itcastPreference", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString("u_id", u_id);
		// 
		editor.commit(); 
	}
	/**
	 * 记住密码
	 * @param cusecode
	 */
	public void pwd(String pwd) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"itcastPreference", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString("pwd", pwd);
		// 
		editor.commit(); 
	}
	public void cusecode(String cusecode) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"itcastPreference", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString("cusecode", cusecode);
		// 
		editor.commit(); 
	}
public void jizhuwo(String jizhuwo){
		
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"itcastPreference", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString("jizhuwo", jizhuwo);
		
		editor.commit(); 
	}
public void iid(String iid){
	
	SharedPreferences sharedPreferences = context.getSharedPreferences(
			"itcastPreference", Context.MODE_PRIVATE);
	Editor editor = sharedPreferences.edit();
	editor.putString("iid", iid);
	
	editor.commit(); 
}
public void check(String check){
	
	SharedPreferences sharedPreferences = context.getSharedPreferences(
			"itcastPreference", Context.MODE_PRIVATE);
	Editor editor = sharedPreferences.edit();
	editor.putString("check", check);
	
	editor.commit(); 
}

	/**
	 * 获取各项配置参数
	 * 
	 * @return params
	 */
	public Map<String, String> getPreferences() {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"itcastPreference", Context.MODE_PRIVATE);
		Map<String, String> params = new HashMap<String, String>();
		params.put("app_address",
				sharedPreferences.getString("app_address", ""));
		params.put("username",
				sharedPreferences.getString("username", ""));
		params.put("u_name",
				sharedPreferences.getString("u_name", ""));
		params.put("u_id",
				sharedPreferences.getString("u_id", ""));
		params.put("cusecode",
				sharedPreferences.getString("cusecode", ""));
		params.put("pwd",
				sharedPreferences.getString("pwd", ""));
		params.put("jizhuwo",
				sharedPreferences.getString("jizhuwo", ""));
		params.put("iid",
				sharedPreferences.getString("iid", ""));
		params.put("check",
				sharedPreferences.getString("check", ""));

		return params;
	}

}
