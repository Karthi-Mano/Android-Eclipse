package com.wjz.smart1.tagpage;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wjz.smart1.R;
import com.wjz.smart1.activity.MainActivity;

public class BasePageTag {

	protected MainActivity mainActivity;
	protected View root;
	protected TextView base_tv;
	protected ImageButton base_ib;
	protected FrameLayout base_fl;
	 protected ImageButton listorgrid;
	public BasePageTag(MainActivity mainActivity) {
		this.mainActivity = mainActivity;

		initView();
		// initData(); 该方法应放在各个页面生成处调用
		initEvent();
	}

	protected void initView() {
		root = View
				.inflate(mainActivity, R.layout.fragment_base_page_tag, null);

		base_tv = (TextView) root.findViewById(R.id.tv_base_content_tag);
		base_ib = (ImageButton) root.findViewById(R.id.ib_base_content_tag);
		base_fl = (FrameLayout) root.findViewById(R.id.fl_base_content_tag);
		listorgrid = (ImageButton) root.findViewById(R.id.ib_base_content_listorgrid);
	}

	public View getView() {
		return root;
	};

	/**
	 * 子类复写此方法完成界面显示
	 */
	public void initData() {

	}
	//控制ViewPager选择哪个page显示
	public void swtichPage(int position) {
	}

	protected void initEvent() {
		// TODO Auto-generated method stub
		base_ib.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mainActivity.getSlidingMenu().toggle();

			}
		});
	}
}
