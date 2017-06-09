package com.itheima.googleplay_13.holder;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.googleplay_13.R;
import com.itheima.googleplay_13.base.BaseHolder;
import com.itheima.googleplay_13.bean.CategoryInfoBean;
import com.itheima.googleplay_13.conf.Constants.URlS;
import com.itheima.googleplay_13.utils.BitmapHelper;
import com.itheima.googleplay_13.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-24 上午10:16:35
 * @描述	     TODO
 *
 * @版本       $Rev: 42 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-24 10:44:37 +0800 (星期六, 24 十月 2015) $
 * @更新描述    TODO
 */
public class CategoryNormalHodler extends BaseHolder<CategoryInfoBean> {
	@ViewInject(R.id.item_category_item_1)
	LinearLayout	mContainerItem1;

	@ViewInject(R.id.item_category_item_2)
	LinearLayout	mContainerItem2;

	@ViewInject(R.id.item_category_item_3)
	LinearLayout	mContainerItem3;

	@ViewInject(R.id.item_category_icon_1)
	ImageView		mIvIcon1;

	@ViewInject(R.id.item_category_icon_2)
	ImageView		mIvIcon2;

	@ViewInject(R.id.item_category_icon_3)
	ImageView		mIvIcon3;

	@ViewInject(R.id.item_category_name_1)
	TextView		mTvName1;

	@ViewInject(R.id.item_category_name_2)
	TextView		mTvName2;

	@ViewInject(R.id.item_category_name_3)
	TextView		mTvName3;

	@Override
	protected View initHolderView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.item_category_normal, null);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	protected void refreshHolderView(CategoryInfoBean data) {

		setData(mTvName1, mIvIcon1, data.name1, data.url1);
		setData(mTvName2, mIvIcon2, data.name2, data.url2);
		setData(mTvName3, mIvIcon3, data.name3, data.url3);

	}

	public void setData(TextView tv, ImageView iv, final String name, String url) {
		if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(url)){
			tv.setText(name);
			
			iv.setImageResource(R.drawable.ic_default);
			BitmapHelper.display(iv, URlS.IMAGEBASEURL + url);
			
			ViewGroup parent = (ViewGroup) tv.getParent();
			parent.setVisibility(View.VISIBLE);
			
			parent.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(UIUtils.getContext(), name, 0).show();	
				}
			});
			
		}else{
			ViewGroup parent = (ViewGroup) tv.getParent();
			parent.setVisibility(View.INVISIBLE);
		}
	}

}
