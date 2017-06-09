package com.example.verticalviewpager;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	private VerticalViewPager mViewPager;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_main);

		mViewPager = (VerticalViewPager) findViewById(R.id.viewpager);
//		mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
//		mViewPager.setPageMargin(-20);
//		mViewPager.setOffscreenPageLimit(3);
		mViewPager.setAdapter(new MyPagerAdapter());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
