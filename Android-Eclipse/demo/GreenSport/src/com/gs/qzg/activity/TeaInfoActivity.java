package com.gs.qzg.activity;

import java.util.ArrayList;
import java.util.List;

import com.gs.qzg.greensport.R;
import com.gs.qzg.greensport.R.layout;
import com.gs.qzg.greensport.R.menu;

import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class TeaInfoActivity extends Activity {

	private ViewPager viewPager;
	private int[] ids=new int[]{R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,R.drawable.five};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tea_info);
		
		TextView tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText("内景展示");
		
		ImageView tv_back = (ImageView)findViewById(R.id.tv_back);
		tv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		final ArrayList<View> listViews = new ArrayList<View>();
		LayoutInflater mInflater = getLayoutInflater();
		for(int i=0;i<ids.length;i++){
			listViews.add(mInflater.inflate(R.layout.fragment_layout, null));
		}
		
		viewPager=(ViewPager)findViewById(R.id.viewPager);
		viewPager.setAdapter(new MyPagerAdapter(listViews));
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	 public class MyPagerAdapter extends PagerAdapter {
	        public List<View> mListViews;

	        public MyPagerAdapter(List<View> mListViews) {
	            this.mListViews = mListViews;
	        }

	        @Override
	        public void destroyItem(View arg0, int arg1, Object arg2) {
	            ((ViewPager) arg0).removeView(mListViews.get(arg1));
	        }

	        @Override
	        public void finishUpdate(View arg0) {
	        }

	        @Override
	        public int getCount() {
	            return mListViews.size();
	        }

	        @Override
	        public Object instantiateItem(View view, int position) {
	        	View v=mListViews.get(position);
	        	ImageView iv=(ImageView)v.findViewById(R.id.iv);
	        	iv.setImageResource(ids[position]);
	            ((ViewPager) view).addView(v, 0);
	            return v;
	        }

	        @Override
	        public boolean isViewFromObject(View arg0, Object arg1) {
	            return arg0 == (arg1);
	        }

	        @Override
	        public void restoreState(Parcelable arg0, ClassLoader arg1) {
	        }

	        @Override
	        public Parcelable saveState() {
	            return null;
	        }

	        @Override
	        public void startUpdate(View arg0) {
	        }
	    }
	 

}
