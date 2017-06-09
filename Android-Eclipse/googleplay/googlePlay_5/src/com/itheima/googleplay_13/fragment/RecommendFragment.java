package com.itheima.googleplay_13.fragment;

import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.itheima.googleplay_13.base.BaseFragment;
import com.itheima.googleplay_13.base.LoadingPager.LoadedResult;
import com.itheima.googleplay_13.protocol.RecommendProtocol;
import com.itheima.googleplay_13.utils.UIUtils;
import com.itheima.googleplay_13.view.flyinout.ShakeListener;
import com.itheima.googleplay_13.view.flyinout.ShakeListener.OnShakeListener;
import com.itheima.googleplay_13.view.flyinout.StellarMap;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-20 上午11:22:21
 * @描述	     TODO
 *
 * @版本       $Rev: 44 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-24 14:24:46 +0800 (星期六, 24 十月 2015) $
 * @更新描述    TODO
 */
public class RecommendFragment extends BaseFragment {
	private List<String>	mDatas;
	private ShakeListener	mShakeListener;

	/**
	 * @des 真正开始加载数据了,在子线程中执行
	 * @call 外界调用了triggerLoadData()方法的时候
	 */
	@Override
	protected LoadedResult initData() {
		RecommendProtocol protocol = new RecommendProtocol();
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
		final StellarMap stellarMap = new StellarMap(UIUtils.getContext());

		final RecommendAdapter adapter = new RecommendAdapter();
		stellarMap.setAdapter(adapter);

		// 设置拆分规则
		stellarMap.setRegularity(15, 20);

		// 设置首页选中
		stellarMap.setGroup(0, true);

		// 加入摇一摇
		mShakeListener = new ShakeListener(UIUtils.getContext());

		mShakeListener.setOnShakeListener(new OnShakeListener() {
			@Override
			public void onShake() {
				int currentGroup = stellarMap.getCurrentGroup();
				if (currentGroup == adapter.getGroupCount() - 1) {
					currentGroup = 0;
				} else {
					currentGroup++;
				}
				// 切换
				stellarMap.setGroup(currentGroup, true);
			}
		});
		return stellarMap;
	}

	@Override
	public void onPause() {
		if (mShakeListener != null) {
			mShakeListener.pause();
		}
		super.onPause();
	}

	@Override
	public void onResume() {
		if (mShakeListener != null) {
			mShakeListener.resume();
		}
		super.onResume();
	}

	class RecommendAdapter implements StellarMap.Adapter {

		private static final int	PAGESIZE	= 15;

		@Override
		public int getGroupCount() {// 返回有几组
			// 32/15 = 2
			if (mDatas.size() % PAGESIZE != 0) {// 有余数
				return mDatas.size() / PAGESIZE + 1;
			}
			return mDatas.size() / PAGESIZE;
		}

		@Override
		public int getCount(int group) {// 每组有多少个
			// 15 15 2
			if (group == getGroupCount() - 1) {// 最后一页
				// 最后一页
				if (mDatas.size() % PAGESIZE != 0) {
					return mDatas.size() % PAGESIZE;
				}
			}
			return PAGESIZE;
		}

		@Override
		public View getView(int group, int position, View convertView) {// 具体view
			int location = group * PAGESIZE + position;

			TextView tv = new TextView(UIUtils.getContext());
			tv.setText(mDatas.get(location));
			int padding = UIUtils.dip2Px(4);
			tv.setPadding(padding, padding, padding, padding);
			Random random = new Random();
			// 随机大小
			tv.setTextSize(random.nextInt(4) + 12);// 12-16
			// 随机颜色
			int alpha = 255;
			int red = random.nextInt(190) + 30;// 30-220
			int green = random.nextInt(190) + 30;// 30-220
			int blue = random.nextInt(190) + 30;// 30-220
			int color = Color.argb(alpha, red, green, blue);
			tv.setTextColor(color);
			return tv;
		}

		@Override
		public int getNextGroupOnPan(int group, float degree) {
			// TODO
			return 0;
		}

		@Override
		public int getNextGroupOnZoom(int group, boolean isZoomIn) {
			// TODO
			return 0;
		}

	}

}
