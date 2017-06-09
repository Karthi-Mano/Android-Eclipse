package com.example.fragment;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.xutils.x;

import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import com.baoyuan.view.HandyTextView;
import com.baozi.Zxing.CaptureActivity;

import com.example.baoziszxing.R;
import com.example.entity.ErweimaUtil;
import com.example.entity.Information;
import com.example.entity.PanDianBean;
import com.example.entity.SelectInformation;
import com.example.entity.User;

import com.example.service.PreferencesService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Intent;

import android.os.Bundle;

import java.lang.reflect.Type;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectFragment extends Fragment {
	// 扫描的请求码

	/**
	 * 显示扫描结果
	 */
	private TextView mTextView;
	PreferencesService service;
	private View view;
	private TextView ccode;// 资产编号
	private TextView cname;// 资产名称
	private TextView iclass_name;// 资产类别
	private TextView idepartment_name;// 机构网点
	private TextView dbegin;// 使用日期
	private TextView fvalue;// 资产原值
	private TextView icustomer_name;// 服务厂商
	private TextView ipcperson_name;// 联系人员
	private TextView cpctel;// 联系电话
	private EditText et_iid;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.layout_select, container, false);

		service = new PreferencesService(getActivity());
		init();

		return view;

	}

	// 初始化
	private void init() {
		// mTextView = (TextView) view.findViewById(R.id.result);
		ImageView btn_saoma = (ImageView) view.findViewById(R.id.btn_saoma);
		ImageView iv_search = (ImageView) view.findViewById(R.id.iv_search);
		et_iid = (EditText) view.findViewById(R.id.et_select);
		btn_saoma.setOnClickListener(button_Listener);
		iv_search.setOnClickListener(button_Listener);
		// 初始化TextView
		ccode = (TextView) view.findViewById(R.id.tv_number);
		cname = (TextView) view.findViewById(R.id.tv_name);
		iclass_name = (TextView) view.findViewById(R.id.tv_type);
		idepartment_name = (TextView) view.findViewById(R.id.tv_idepartment);
		dbegin = (TextView) view.findViewById(R.id.tv_date);
		fvalue = (TextView) view.findViewById(R.id.tv_fvalue);
		icustomer_name = (TextView) view.findViewById(R.id.tv_maker);
		ipcperson_name = (TextView) view.findViewById(R.id.tv_contact);
		cpctel = (TextView) view.findViewById(R.id.tv_telphone);

	}

	private OnClickListener button_Listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_saoma:

				Intent intent = new Intent(getActivity(), CaptureActivity.class);
				startActivityForResult(intent, 100);
				break;
			case R.id.iv_search:
				if(et_iid.getText().toString().equals("")){
					showCustomToast("请输入查询条件");
				
					
				}else{
				Map<String, String> param = service.getPreferences();
				String str_url = param.get("app_address");
				String url = str_url + "/bank_phone/assets/search";
				RequestParams params = new RequestParams(url);
				params.addBodyParameter("str", et_iid.getText().toString());
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
						
						Log.i("info", info);
						
					     Gson gson = new Gson();
					     Type listType = new TypeToken<List<SelectInformation>>(){}.getType(); 
					     List<SelectInformation> list = gson.fromJson(info, listType); 
					     
					    
						if(list.size()==0){
							showCustomToast("没有查询到数据");	
							
						}else{
							ccode.setText(list.get(0).getCCODE());
							cname.setText(list.get(0).getCNAME());
							iclass_name.setText(list.get(0).getICLASS_NAME());
							idepartment_name.setText(list.get(0).getIDEPARTMENT_NAME());
							dbegin.setText(list.get(0).getDBEGIN());
							fvalue.setText(list.get(0).getFVALUE());
						
							cpctel.setText(list.get(0).getCPCTEL());
						
							//private TextView icustomer_name;// 服务厂商
							//private TextView ipcperson_name;// 联系人员
							icustomer_name.setText(list.get(0).getICUSTOMER_NAME());
							ipcperson_name.setText(list.get(0).getIPCPERSON_NAME());
							
							
						}

					}
				});
				}
				break;
			}

		}

	};

	@Override
	public void onActivityResult(int arg0, int arg1, Intent data) {
		super.onActivityResult(arg0, arg1, data);

		/**
		 * 拿到解析完成的字符串
		 */
		if (data != null) {

			String result = data.getStringExtra("result");
			Log.i("erweima", result);
			Gson gson = new Gson();
			ErweimaUtil info = new ErweimaUtil();
			info = gson.fromJson(result, ErweimaUtil.class);
			//连接服务器传扫描到的iid查询结果
			Map<String, String> param = service.getPreferences();
			String str_url = param.get("app_address");
			String url = str_url + "/bank_phone/assets/search";
			RequestParams params = new RequestParams(url);
			params.addBodyParameter("iid", info.getIid());
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


					Log.i("info", info);
					
				     Gson gson = new Gson();
				     Type listType = new TypeToken<List<SelectInformation>>(){}.getType(); 
				     List<SelectInformation> list = gson.fromJson(info, listType); 
				  
					    
						if(list.size()==0){
							showCustomToast("没有查询到数据");	
							
						}else{
							ccode.setText(list.get(0).getCCODE());
							cname.setText(list.get(0).getCNAME());
							iclass_name.setText(list.get(0).getICLASS_NAME());
							idepartment_name.setText(list.get(0).getIDEPARTMENT_NAME());
							dbegin.setText(list.get(0).getDBEGIN());
							fvalue.setText(list.get(0).getFVALUE());
						
							cpctel.setText(list.get(0).getCPCTEL());
						
							//private TextView icustomer_name;// 服务厂商
							//private TextView ipcperson_name;// 联系人员
							icustomer_name.setText(list.get(0).getICUSTOMER_NAME());
							ipcperson_name.setText(list.get(0).getIPCPERSON_NAME());
						}

				}
			});

		

		}
	}

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
