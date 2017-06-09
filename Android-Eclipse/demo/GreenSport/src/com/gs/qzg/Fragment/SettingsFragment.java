package com.gs.qzg.Fragment;

import java.net.URL;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gs.qzg.activity.LoginActivity;
import com.gs.qzg.greensport.R;



public class SettingsFragment extends Fragment{

	boolean isChecked = true;
	private View settingLayout;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		settingLayout = inflater.inflate(R.layout.setting_fragment, container, false);
		
		ImageView tv_act = (ImageView)settingLayout.findViewById(R.id.tv_act);
		tv_act.setBackgroundResource(R.drawable.down);
		
		TextView tv_title = (TextView)settingLayout.findViewById(R.id.tv_title);
		tv_title.setText("设置");
		
		ImageView tv_back = (ImageView)settingLayout.findViewById(R.id.tv_back);
		tv_back.setVisibility(View.GONE);
		
		tv_act.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),LoginActivity.class);
				startActivity(intent);
			}
		});
		
		TextView tvTextView = (TextView)settingLayout.findViewById(R.id.yuyin);
		tvTextView.setText("退出");
		tvTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),LoginActivity.class);
				startActivity(intent);	
			}
		});
		
		LinearLayout homePage = (LinearLayout)settingLayout.findViewById(R.id.homePage);
		homePage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				http://www.114jianshen.com/company/member/club/index.jsp?id=1210
					Uri uri = Uri.parse("http://www.114jianshen.com/company/member/club/index.jsp?id=1210");
					Intent intent = new Intent(Intent.ACTION_VIEW,uri);
					startActivity(intent);
			}
		});
		
		
		return settingLayout;
	}
	
}
