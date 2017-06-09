package com.example.fragment;

import java.util.Map;

import com.example.activity.RenewalActivity;
import com.example.activity.SetserviceActivity;
import com.example.activity.UpdateActivity;

import com.example.service.PreferencesService;
import com.example.test.R;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

//用户
public class UserFragment extends Fragment implements OnClickListener {
	private View view;
	RelativeLayout ib_updatepwd;
	RelativeLayout ib_renewal;
	TextView tv_uname;
	TextView tv_uid;
	private PreferencesService service;
	public static UserFragment instance = null;
	String u_name;
	String u_id;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.layout_user, container, false);
		ib_updatepwd = (RelativeLayout) view.findViewById(R.id.ib_updatepwd);
		ib_renewal = (RelativeLayout) view.findViewById(R.id.ib_renewal);
		tv_uname = (TextView) view.findViewById(R.id.tv_uname);
		tv_uid = (TextView) view.findViewById(R.id.tv_uid);

		service = new PreferencesService(getActivity());
		Map<String, String> params = service.getPreferences();

		u_name = params.get("u_name");
		u_id = params.get("u_id");
		tv_uname.setText(u_name);
		tv_uid.setText(u_id);
		// 修改密码界面跳转
		ib_updatepwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), UpdateActivity.class);
				getActivity().startActivity(intent);

			}
		});
		// 检查版本更新页面的跳转
		ib_renewal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), RenewalActivity.class);
				getActivity().startActivity(intent);

			}
		});

		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
