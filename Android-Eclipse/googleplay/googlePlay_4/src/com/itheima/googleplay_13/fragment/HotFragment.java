package com.itheima.googleplay_13.fragment;

import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.googleplay_13.R;
import com.itheima.googleplay_13.base.BaseFragment;
import com.itheima.googleplay_13.base.LoadingPager.LoadedResult;
import com.itheima.googleplay_13.protocol.HotProtocol;
import com.itheima.googleplay_13.utils.UIUtils;
import com.itheima.googleplay_13.view.FlowLayout;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-20 上午11:22:21
 * @描述	     TODO
 *
 * @版本       $Rev: 39 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-24 10:11:06 +0800 (星期六, 24 十月 2015) $
 * @更新描述    TODO
 */
public class HotFragment extends BaseFragment {
	private List<String>	mDatas;

	/**
	 * @des 真正开始加载数据了,在子线程中执行
	 * @call 外界调用了triggerLoadData()方法的时候
	 */
	@Override
	protected LoadedResult initData() {
		HotProtocol protocol = new HotProtocol();
		try {
			mDatas = protocol.loadData(0);
			return checkState(mDatas);
		} catch (Exception e) {
			e.printStackTrace();
			return LoadedResult.ERROR;
		}
	}

	/**
	 * @des 创建成功视图
	 * @call 触发加载数据,数据加载完成之后,得到数据加载结果,会重新刷新UI,而且数据加载成功了.而且成功视图还没有
	 */
	@Override
	protected View initSuccessView() {
		ScrollView sv = new ScrollView(UIUtils.getContext());

		FlowLayout fl = new FlowLayout(UIUtils.getContext());

		for (final String info : mDatas) {
			TextView tv = new TextView(UIUtils.getContext());
			tv.setText(info);
			int padding = UIUtils.dip2Px(5);
			tv.setPadding(padding, padding, padding, padding);
			tv.setGravity(Gravity.CENTER);
			tv.setTextColor(Color.WHITE);
			// 设置圆角的背景
			tv.setBackgroundResource(R.drawable.shape_hot_tv);

			// 默认效果
			GradientDrawable normalBg = new GradientDrawable();
			normalBg.setCornerRadius(10);

			Random random = new Random();
			int alpha = 255;
			int red = random.nextInt(170) + 30;// 30-200
			int green = random.nextInt(170) + 30;// 30-200
			int blue = random.nextInt(170) + 30;// 30-200
			int argb = Color.argb(alpha, red, green, blue);
			normalBg.setColor(argb);

			// 按下效果
			GradientDrawable pressedBg = new GradientDrawable();
			pressedBg.setCornerRadius(10);
			pressedBg.setColor(Color.DKGRAY);

			StateListDrawable selectorBg = new StateListDrawable();
			selectorBg.addState(new int[] { android.R.attr.state_pressed }, pressedBg);// 按下的状态
			selectorBg.addState(new int[] {}, normalBg);// 默认状态

			tv.setBackground(selectorBg);
			
			tv.setClickable(true);
			
			tv.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Toast.makeText(UIUtils.getContext(), info, 0).show();
				}
			});

			fl.addView(tv);
		}

		sv.addView(fl);

		return sv;
	}

}
