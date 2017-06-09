package com.wjz.smart1.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wjz.smart1.R;

public class RefreshListView extends ListView {

	private final int PULLDOWN = 1;// 下拉刷新状态
	private final int RELEASE_STATE = 2; // 松开刷新
	private final int REFRESHING = 3; // 正在刷新

	private int current = PULLDOWN;
	private LinearLayout head;// listview的头部容器
	private View foot;// listview的尾部组件
	private int listViewOnScreen;
	private View lunbotu;
	private LinearLayout ll_listview_head_root_head;
	private LinearLayout ll_listview_foot_root;
	private float downY = -1;
	private int head_height;
	private int foot_heigth;
	private ImageView iv_arrow;

	private TextView tv_state_dec;
	private TextView tv_refresh_time;
	private RotateAnimation up_ra;
	private RotateAnimation down_ra;
	private ProgressBar pb_loading;
	// 2.定义接口变量
	private OnRefreshDataListener refreshListenre;
	private boolean isEnablePullRefresh;//默认值为false

	public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initView();
		initAnimation();
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public RefreshListView(Context context) {
		this(context, null, 0);
		// TODO Auto-generated constructor stub
	}

	public void initView() {
		initHead();
		initFoot();
	}

	/**
	 * 选择是否启用下拉刷新头的功能
	 */
	public void setIsRefreshHead(boolean isPullRefresh){
		isEnablePullRefresh = isPullRefresh;
	}
	
	/**
	 * 抽取方法，与父类方法同名
	 */
	public void addHeaderView(View view) {
		// 判断 如果使用下来刷新，把头布局加入下拉刷新的容器中，否则加载原生ListView中

		if (isEnablePullRefresh) {
			
			System.out.println("=-==--=-=-=-=启用下拉刷新");
			//启用下拉刷新
			//将view添加到下拉刷新的容器中
			lunbotu = view;
			head.addView(lunbotu);
		}else{
			//使用原生的ListView
			super.addHeaderView(view);
			
		}
		
		
	}

	/**
	 * 初始化头组件
	 */
	public void initHead() {
		// 初始化头部组件
		head = (LinearLayout) View.inflate(getContext(),
				R.layout.listview_head_container, null);

		ll_listview_head_root_head = (LinearLayout) head
				.findViewById(R.id.ll_listview_head_root);

		iv_arrow = (ImageView) head.findViewById(R.id.iv_listview_head_arrow);

		pb_loading = (ProgressBar) head
				.findViewById(R.id.pb_listview_head_loading);
		tv_state_dec = (TextView) head
				.findViewById(R.id.tv_listview_head_state_dec);
		tv_refresh_time = (TextView) head
				.findViewById(R.id.tv_listview_head_refresh_time);

		// 自动测量下拉刷新头布局的高度
		ll_listview_head_root_head.measure(0, 0);
		head_height = ll_listview_head_root_head.getMeasuredHeight();
		// 设置刷新头的隐藏
		ll_listview_head_root_head.setPadding(0, -head_height, 0, 0);

		// 加载到listview的头部
		addHeaderView(head);
	}

	/**
	 * 初始化尾组件
	 */
	public void initFoot() {
		foot = View.inflate(getContext(), R.layout.listview_refresh_foot, null);
		ll_listview_foot_root = (LinearLayout) foot
				.findViewById(R.id.ll_listview_foot_root);

		// 测量刷新尾的布局高度
		ll_listview_foot_root.measure(0, 0);
		foot_heigth = ll_listview_foot_root.getMeasuredHeight();

		// 设置刷新尾的隐藏
		ll_listview_foot_root.setPadding(0, -foot_heigth, 0, 0);

		// 将foot加载到listview的尾部
		addFooterView(foot);
	}

	/**
	 * 复写父类ListView的onTouchEvent事件
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		// return true;//自身消费触摸事件，父控件ListView失去滑动能力

		// 屏蔽掉父类的touch事件

		switch (ev.getAction()) {

		case MotionEvent.ACTION_DOWN:// 按下
			//downY = ev.getY();// 获取按下的y轴坐标，无法获得，被轮播图的onTouchEvent消费掉

			break;

		case MotionEvent.ACTION_MOVE:// 滑动
			
			//1.判断是否启用下拉刷新
			if(!isEnablePullRefresh){
				//没有启用下拉刷新
				break;
			}
			
			//2.判断现在是否处于刷新状态
			if(current == REFRESHING){
				//正在刷新
				break;
			}

			//3. 轮播图完全显示 false:父控件 true:自身
			if (!isLunBoTuFullShow()) {
				// 轮播图没有完全显示
				break;
			}

			//判断是否得到按下位置的坐标
			if (downY == -1) {
				downY = ev.getY();// 只执行一次,重新获得按下的位置
			}

			float moveY = ev.getY();// 获取滑动后的y值

			// 移动的距离
			float disY = moveY - downY;

			// 下拉拖动（当listview显示第一条数据）处理自己的事件,
			// 不让listview原生的拖动事件生效
			if (disY > 0 && getFirstVisiblePosition() == 0) {
				// 当前padding的值
				float slidingYDis = -head_height + disY;

				// 刷新头没有完全显示

				if (slidingYDis < 0 && current != PULLDOWN) {
					// 下拉刷新状态
					current = PULLDOWN;// 只执行一次
					refreshState();
				} else if (slidingYDis >= 0 && current != RELEASE_STATE) {
					// 松开刷新
					current = RELEASE_STATE;
					refreshState();
				}
				ll_listview_head_root_head.setPadding(0, (int) slidingYDis, 0,
						0);
				return true;
			}

			break;

		case MotionEvent.ACTION_UP:// 松开
			downY = -1;
			// 判断状态
			// 1.如果是PULLDOWN状态，松开恢复原状
			// 2.如果是RELEASE_STATE，松开刷新
			if (current == PULLDOWN) {
				ll_listview_head_root_head.setPadding(0, -head_height, 0, 0);
			} else if (current == RELEASE_STATE) {
				// 2.如果是RELEASE_STATE，松开刷新数据
				ll_listview_head_root_head.setPadding(0, 0, 0, 0);// 刷新头显示
				// 改变刷新状态
				current = REFRESHING;
				refreshState();// 刷新界面

				if (refreshListenre != null) {
					// 3.需要传值的地方，用接口对象调用接口方法,相当于通知
					refreshListenre.refreshData(current);
				}
			}

			break;

		default:
			break;
		}

		return super.onTouchEvent(ev);
	}

	/**
	 * 接口回调完成刷新
	 */
	// 1.创建接口和接口方法
	public interface OnRefreshDataListener {
		void refreshData(int refreshing);
	}

	// 4.暴露一个公共的方法
	public void SetOnRefreshDataListener(
			OnRefreshDataListener refreshDataListener) {
		this.refreshListenre = refreshDataListener;
	}

	/**
	 * 刷新数据成功,还原标记值
	 */
	public void refreshStateFinish() {
		// 下拉刷新
		// 刷新完成，将组件恢复初始状态
		// 改变tv的内容
		tv_state_dec.setText("下拉刷新");
		// 显示箭头
		iv_arrow.setVisibility(View.VISIBLE);
		// 隐藏进度条
		pb_loading.setVisibility(View.INVISIBLE);
		// 设置刷新时间为当前时间
		tv_refresh_time.setText(getCurrentFormatDate());
		// 隐藏刷新头的布局
		ll_listview_head_root_head.setPadding(0, -head_height, 0, 0);
		//还原current的值为下拉刷新的状态
		current = PULLDOWN;
	}

	private String getCurrentFormatDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}

	/**
	 * 
	 * @return 轮播图是否完全显示 false:父控件 true:自身
	 */
	private boolean isLunBoTuFullShow() {

		int[] location = new int[2];

		// 判断轮播图是否完全显示
		// 如果轮播图没有完全显示，相应的是Listview的事件
		// 取listview在屏幕中坐标 和 轮播图在屏幕中的坐标 判断
		// 1.取listview在屏幕中坐标
		if (listViewOnScreen == 0) {// 位置不变，只去一次
			this.getLocationOnScreen(location);
			listViewOnScreen = location[1];
		}
		// 2.轮播图在屏幕中的坐标
		lunbotu.getLocationOnScreen(location);

		if (location[1] < listViewOnScreen) {
			// 轮播图没有完全显示
			// 继续相应listView事件
			return false;
		}
		return true;
	}

	/**
	 * 刷新动画
	 */
	private void initAnimation() {
		up_ra = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		up_ra.setDuration(500);
		up_ra.setFillAfter(true);// 停留在动画结束的状态

		down_ra = new RotateAnimation(-180, -360, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		down_ra.setDuration(500);
		down_ra.setFillAfter(true);// 停留在动画结束的状态
	}

	private void refreshState() {

		switch (current) {
		case PULLDOWN:
			System.out.println("下拉刷新");
			// 改变tv的内容
			tv_state_dec.setText("下拉刷新");
			iv_arrow.startAnimation(down_ra);
			break;
		case RELEASE_STATE:
			System.out.println("松开刷新");
			tv_state_dec.setText("松开刷新");
			iv_arrow.startAnimation(up_ra);
			break;
		case REFRESHING:
			System.out.println("正在刷新");
			iv_arrow.clearAnimation();// 清除所有动画
			iv_arrow.setVisibility(View.INVISIBLE);// 隐藏箭头
			pb_loading.setVisibility(View.VISIBLE);// 显示进度
			tv_state_dec.setText("正在刷新数据");
			break;

		default:
			break;
		}

	}

}
