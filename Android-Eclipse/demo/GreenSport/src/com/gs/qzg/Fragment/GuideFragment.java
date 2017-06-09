package com.gs.qzg.Fragment;

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

import com.gs.qzg.activity.SportTeacherActivity;
import com.gs.qzg.activity.SportTypeActivity;
import com.gs.qzg.greensport.R;



public class GuideFragment extends Fragment{

	boolean isChecked = true;
	private View guideLayout;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		guideLayout = inflater.inflate(R.layout.guide_fragment, container, false);
		
		ImageView tv_back = (ImageView)guideLayout.findViewById(R.id.tv_back);
		tv_back.setVisibility(View.GONE);
		
		TextView tv_title = (TextView)guideLayout.findViewById(R.id.tv_title);
		tv_title.setText("健身指导");
		
		Button sportProject = (Button)guideLayout.findViewById(R.id.sport_project);
		Button sportLead = (Button)guideLayout.findViewById(R.id.sport_lead);
		
		sportProject.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),SportTypeActivity.class);
				startActivity(intent);
			}
		});
		
		sportLead.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),SportTeacherActivity.class);
				startActivity(intent);
			}
		});
		
		return guideLayout;
	}
	
}
