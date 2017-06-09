package com.gs.qzg.Fragment;

import com.gs.qzg.activity.TeaInfoActivity;
import com.gs.qzg.greensport.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;



public class InfoFragment extends Fragment{

	boolean isChecked = true;
	private View infoLayout;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		infoLayout = inflater.inflate(R.layout.info_fragment, container, false);
		
		ImageView tv_back = (ImageView)infoLayout.findViewById(R.id.tv_back);
		tv_back.setVisibility(View.GONE);
		
		TextView tv_title = (TextView)infoLayout.findViewById(R.id.tv_title);
		tv_title.setText("健身房信息");
		
		Button tea_infoButton = (Button)infoLayout.findViewById(R.id.tea_info);
		tea_infoButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),TeaInfoActivity.class);
				startActivity(intent);
			}
		});
		
		return infoLayout;
	}
	
}
