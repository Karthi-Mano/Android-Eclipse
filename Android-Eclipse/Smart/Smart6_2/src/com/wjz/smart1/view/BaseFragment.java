package com.wjz.smart1.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wjz.smart1.activity.MainActivity;

public abstract class BaseFragment extends Fragment {

	protected MainActivity mainActivity;////获取fragment所在Activity的上下文;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mainActivity = (MainActivity) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = initView();
		return root;
	}
	
	
	/**
	 * 子类必须覆盖此方法完成界面显示
	 */
	public abstract View initView();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
		
		initEvent();
		initData();
	}

	public void initEvent() {
		// TODO Auto-generated method stub
		
	}

	public void initData() {
		// TODO Auto-generated method stub
		
	}

	
	
}
