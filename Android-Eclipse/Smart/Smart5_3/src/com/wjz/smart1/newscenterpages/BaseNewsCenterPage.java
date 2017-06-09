package com.wjz.smart1.newscenterpages;

import android.view.View;

import com.wjz.smart1.activity.MainActivity;

public abstract class BaseNewsCenterPage {

	
	protected MainActivity mainActivity;
	protected View root;
	public BaseNewsCenterPage(MainActivity mainActivity){
	
		this.mainActivity=mainActivity;
		
		root = initView();
		//initData();//不应该在此处调用
		initEvent();
	}
	protected void initEvent() {
		// TODO Auto-generated method stub
		
	}
	public void initData() {
		// TODO Auto-generated method stub
		
	}
	public abstract View initView();
	
	public View getView(){
		return root;
	}
	
}
