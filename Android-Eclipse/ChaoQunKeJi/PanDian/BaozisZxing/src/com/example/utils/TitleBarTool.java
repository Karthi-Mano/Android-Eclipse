package com.example.utils;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class TitleBarTool {

	private List<TextView> textViews = new ArrayList<TextView>();
	public void createTitleBar(String[] titleBarArr ,TextView[] tv, int[] nums){
		for(int i =0;i<titleBarArr.length;i++){
			tv[i].setText(titleBarArr[i]+"("+nums[i]+")");
			
			textViews.add(tv[i]);
			final int finalI = i; 
			tv[i].setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					listener.changerTitleBar(finalI);
				}
			});
		}
	}
	/**
	 * 改变点击的颜色
	 * @param position
	 * 			点击的位置
	 */
	public void changeColor(int position){
		//还原所有的颜色
		for(TextView tv :textViews){
			tv.setSelected(false);
		}
		textViews.get(position).setSelected(true);
	}
	
	public interface OnTitleBarClickListener{
		void changerTitleBar(int position);
	}
	private OnTitleBarClickListener listener;
	
	public OnTitleBarClickListener getListener() {
		return listener;
	}
	public void setOnTitleBarClickListener(OnTitleBarClickListener listener) {
		this.listener = listener;
	}

}
