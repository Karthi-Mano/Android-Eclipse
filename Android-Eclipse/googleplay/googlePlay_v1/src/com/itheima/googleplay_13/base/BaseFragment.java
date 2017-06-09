package com.itheima.googleplay_13.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.itheima.googleplay_13.base.LoadingPager.LoadedResult;
import com.itheima.googleplay_13.utils.UIUtils;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-10-20 下午4:04:25
 * @描述	     TODO
 *
 * @版本       $Rev: 12 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-10-20 17:49:34 +0800 (星期二, 20 十月 2015) $
 * @更新描述    TODO
 */
public abstract class BaseFragment extends Fragment {

	private LoadingPager	mLoadingPager;

	public LoadingPager getLoadingPager() {
		return mLoadingPager;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mLoadingPager == null) {//第一次的走
			mLoadingPager = new LoadingPager(UIUtils.getContext()) {
				/**
				 * @des 真正开始加载数据了,在子线程中执行
				 * @des 必须实现,但是不知道具体实现,定义成为抽象方法,交给子类具体实现
				 * @call 外界调用了triggerLoadData()方法的时候
				 */
				@Override
				protected LoadedResult initData() {
					return BaseFragment.this.initData();
				}

				/**
				 * @des 创建成功视图
				 * @des 必须实现,但是不知道具体实现,定义成为抽象方法,交给子类具体实现
				 * @call 触发加载数据,数据加载完成之后,得到数据加载结果,会重新刷新UI,而且数据加载成功了.而且成功视图还没有
				 */
				@Override
				protected View initSuccessView() {
					return BaseFragment.this.initSuccessView();
				}

			};
		}else{//第二次进来的时候
			ViewParent parent = mLoadingPager.getParent();
			if(parent!=null&&parent instanceof ViewGroup){
				((ViewGroup) parent).removeView(mLoadingPager);
			}
		}
		return mLoadingPager;//-->ViewGroup
	}

	// 页面显示分析
	// Fragment/Activity共性-->页面共性-->视图的展示
	/**
	 任何应用其实就只有4种页面类型
	 ① 加载页面
	 ② 错误页面
	 ③ 空页面 				
	 ④ 成功页面 	
		①②③三种页面一个应用基本是固定的
		每一个fragment/activity对应的页面④就不一样
		进入应用的时候显示①,②③④需要加载数据之后才知道显示哪个		
	*/
	// mvc
	// data(数据)
	// view(视图)
	// data+view(数据绑定)

	/**
	 * @des 真正开始加载数据了,在子线程中执行
	 * @des BaseFragement必须实现,但是不知道具体实现,定义成为抽象方法,交给BaseFragment子类具体实现
	 * @des 它是和LoadingPager中定义的方法同名的
	 * @call 外界调用了triggerLoadData()方法的时候
	 */
	protected abstract LoadedResult initData();

	/**
	 * @des 创建成功视图
	 * @des BaseFragement必须实现,但是不知道具体实现,定义成为抽象方法,交给BaseFragement的子类具体实现
	 * @des 它是和LoadingPager中定义的方法同名的
	 * @call 触发加载数据,数据加载完成之后,得到数据加载结果,会重新刷新UI,而且数据加载成功了.而且成功视图还没有
	 */
	protected abstract View initSuccessView();

	// 数据加载的流程
	/**
	① 触发加载  	进入页面开始加载/点击某一个按钮的时候加载
	② 异步加载数据  -->显示加载视图
	③ 处理加载结果
		① 成功-->显示成功视图
		② 失败
			① 数据为空-->显示空视图
			② 数据加载失败-->显示加载失败的视图
	*/

}
