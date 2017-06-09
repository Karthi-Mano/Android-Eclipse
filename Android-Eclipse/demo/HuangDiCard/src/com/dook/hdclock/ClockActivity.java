package com.dook.hdclock;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.dook.update.UpdateManager;



import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;


public class ClockActivity extends Activity {
    /** Called when the activity is first created. */
	//主界面
	private com.dook.hdclock.MainView mv;
	//提示信息的界面
	private ViewFlipper mViewFlipper;
	//弹出窗口
	private PopupWindow popup;
	//显示信息
	private TextView v_mess;
	// 本人的八字
	private  String bazi;
	// 本人的生日
	private  Calendar birthday;
	
	private void init(){
		SharedPreferences sp = getSharedPreferences("dook", ClockActivity.this.MODE_WORLD_READABLE);
		
		bazi = sp.getString("bazi", null);
		int year = sp.getInt("year", 2000);
		int month = sp.getInt("month", 2);
		int day = sp.getInt("day", 2);
		int hour = sp.getInt("hour", 2);
		int minute = sp.getInt("minute", 2);
		birthday = new GregorianCalendar(year, month, day, hour, minute);
	}
	private boolean isDian = false;
	//显示今日运程的handler
	private Handler handler = new Handler() {
		@Override
	  public void handleMessage(Message m) {
			v_mess.setText(Sizhu.todaywuxing(bazi));
			//mv.setRun(false);
			//v_mess.setText(Sizhu.getGua(cdate));
			popup.showAtLocation(mv, Gravity.CENTER_HORIZONTAL, 0, 0);
			new Thread(){
				public void run(){
					TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
					String uri = "http://112.124.15.112/add_phoneclient";//"http://223.4.86.22/add_phoneclient";
					HttpPost request = new HttpPost(uri);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					// 添加请求参数
					params.add(new BasicNameValuePair("clientPhone", tm.getLine1Number()));
					params.add(new BasicNameValuePair("clientType", "dookhdclock"));
					params.add(new BasicNameValuePair("clientName", "todayyuncheng"));
					params.add(new BasicNameValuePair("clientRemarks","今天运程:" + Sizhu.getDate(new GregorianCalendar())));
					UrlEncodedFormEntity entity=null;
					try {
						 entity =  new UrlEncodedFormEntity(params,HTTP.UTF_8);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					request.setEntity(entity);
					
					DefaultHttpClient client = new DefaultHttpClient();
					try {
						HttpResponse response = client.execute(request);
						
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();//写数据到服务器的线程
	  }
	};
	private Handler handlerjj = new Handler() {
		@Override
	  public void handleMessage(Message m) {
			v_mess.setText("度客命运导航是在皇帝时钟的基础上发展而来，由命理学图书学派创始人龙在飞先生精研中国传统文化三十年所得。由深圳市吾度客科技文化有限公司执行研发和营运。通过黄帝时钟能读解人生命理，每日运程和寻找贵人助运。子曰：不知命，无以成为君子。度客人生命运导航卡能很好地把中国传统文化和西方技术完美结合，为传承中国传统文化找到了最好的载体。");
			//mv.setRun(false);
			//v_mess.setText(Sizhu.getGua(cdate));
			popup.showAtLocation(mv, Gravity.CENTER_HORIZONTAL, 0, 0);
			new Thread(){
				public void run(){
					TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
					String uri = "http://112.124.15.112/add_phoneclient";//"http://223.4.86.22/add_phoneclient";
					HttpPost request = new HttpPost(uri);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					// 添加请求参数
					params.add(new BasicNameValuePair("clientPhone", tm.getLine1Number()));
					params.add(new BasicNameValuePair("clientType", "dookhdclock"));
					params.add(new BasicNameValuePair("clientName", "todayyuncheng"));
					params.add(new BasicNameValuePair("clientRemarks","度客简介"));
					UrlEncodedFormEntity entity=null;
					try {
						 entity =  new UrlEncodedFormEntity(params,HTTP.UTF_8);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					request.setEntity(entity);
					
					DefaultHttpClient client = new DefaultHttpClient();
					try {
						HttpResponse response = client.execute(request);
						
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();//写数据到服务器的线程
	  }
	};
	public   boolean isConnect(Context context) { 
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理） 
		    try { 
		        ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext() 
		                .getSystemService(Context.CONNECTIVITY_SERVICE); 
		        if (connectivity != null) { 
		        	State state = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		        	//System.out.println("11111111111" + state);
		            // 获取网络连接管理的对象 
		        	
		            NetworkInfo info = connectivity.getActiveNetworkInfo(); 
		           // System.out.println("info:" + info);
		            if (info != null&& info.isConnected()) { 
		                // 判断当前网络是否已经连接 
		            //	System.out.println("2222222222222222");
		                if (info.getState() == NetworkInfo.State.CONNECTED) { 
		                //	System.out.println("3333333333333333");
		                    return true; 
		                } 
		            } 
		        } 
		    } catch (Exception e) { 
		// TODO: handle exception 
		    	System.out.println("e:" + e.getStackTrace());
		    } 
		    return false;
	}
	private Handler handler2 = new Handler(){
		@Override
		public void handleMessage(Message m){
			
			if (isConnect(ClockActivity.this)) {//if (checkInternetConnection("http://223.4.86.22/","utf-8")){//
				//Toast.makeText(EastBaGuaClock.this, "connection", Toast.LENGTH_LONG).show();
	    		UpdateManager um = new UpdateManager(ClockActivity.this);
	    		um.checkUpdate();
			} else {
				Toast.makeText(ClockActivity.this, "最好开通网络，重新打开本软件，好获取服务器最新数据！", Toast.LENGTH_LONG).show();
			}   
		        
		}   
	};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
        setContentView(R.layout.main);
        init();
        /*new Thread(){
        	public void run(){
        		
        			handler2.sendMessage(new Message());
        		
        	}
        }.start();*/
		//主界面
        mv = (MainView) this.findViewById(R.id.mv);
        mv.setRun(true);
        
       // popup.showAtLocation(mv, Gravity.CENTER_HORIZONTAL, 0, 20);
        //启动主界面线程
        new Thread(mv).start();
        
        //初始化弹出窗口
        initPopupMenu();
        
	
        	
        //2秒后提示输入生日
        handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (bazi == null) { 
					Toast.makeText(ClockActivity.this, "请先点击菜单输入您的生日", Toast.LENGTH_LONG).show();
				} else {
					handler.handleMessage(null);
					
				}
			}
		}, 2000);
    }
    
    @Override
    public boolean  onTouchEvent(MotionEvent event)  {
    	if (isDian) {
    		mv.setToday();
    		mv.setRun(true);
            new Thread(mv).start();
            isDian = false;
    	}
    	return super.onTouchEvent(event);
    }
    //创建菜单
    public boolean onCreateOptionsMenu (Menu menu) {
    	
    	
    	//menu.add(1, 1, 2, "预测运程").setIcon(R.drawable.date);
    	
    	//menu.add(1, 2, 1, "撞撞好友").setIcon(R.drawable.date);
    	menu.add(1, 5, 3, "命中运程").setIcon(R.drawable.date);
    	
    	
    	SubMenu mi = menu.addSubMenu("输入生日");
    	mi.setIcon(R.drawable.date);
    	mi.add(1, 4, 3, "阳历生日").setIcon(R.drawable.date);
    	mi.add(1, 3, 4, "农历生日").setIcon(R.drawable.date);
    	
    	
    	SubMenu sm = menu.addSubMenu("撞撞好友").setIcon(R.drawable.date);    	
    	sm.add(1, 8, 3, "好友阳历生日").setIcon(R.drawable.date);
    	sm.add(1, 7, 4, "好友农历生日").setIcon(R.drawable.date);
    	
    	
    	
    	
    	menu.add(1, 9, 1, "软件简介").setIcon(R.drawable.date);
    	
    	SubMenu yc = menu.addSubMenu("预测运程");
    	yc.setIcon(R.drawable.date);
    	yc.add(1, 1, 3, "阳历日期").setIcon(R.drawable.date);
    	yc.add(1, 2, 4, "农历日期").setIcon(R.drawable.date);
    	menu.add(1, 10, 2, "我的八字").setIcon(R.drawable.date);
    	
    	return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected (MenuItem item) {
    	int id = item.getItemId();
    	switch (id) {
    	
    	case 9:{
    		handlerjj.sendMessage(new Message());
    		break;
    	}
    	case 10:{
    		mv.setDate(birthday);
    		mv.setRun(false);
    		isDian = true;
    		Toast.makeText(this, "点击界面继续显示当前时间", Toast.LENGTH_LONG).show();
			//mv.setRun(false);
			//v_mess.setText(Sizhu.getGua(cdate));
			//popup.showAtLocation(mv, Gravity.CENTER_HORIZONTAL, 200,720);
    		break;
    	}
    	case 1:{     //yc.add(1, 1, 3, "阳历日期").setIcon(R.drawable.date);
    		if (bazi == null) { 
    			Toast.makeText(this, "请先输入生日", Toast.LENGTH_SHORT).show();
    			return super.onOptionsItemSelected(item);
    		}
    		Calendar cdate = new GregorianCalendar();
    		DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					// TODO Auto-generated method stub
					if (year >= 2050){
						Toast.makeText(ClockActivity.this, "年不能超过2050年", Toast.LENGTH_SHORT).show();
						return;
					}
					
					final Calendar cdate = new GregorianCalendar(year, monthOfYear, dayOfMonth);
					cdate.set(Calendar.HOUR_OF_DAY, new GregorianCalendar().get(Calendar.HOUR_OF_DAY));
					mv.setDate(cdate);
					v_mess.setText(Sizhu.wuxing(bazi, cdate));
					popup.showAtLocation(mv, Gravity.CENTER_HORIZONTAL, 0, 0);
					new Thread(){
						public void run(){
							TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
							String uri = "http://112.124.15.112/add_phoneclient";
							HttpPost request = new HttpPost(uri);
							List<NameValuePair> params = new ArrayList<NameValuePair>();
							// 添加请求参数
							params.add(new BasicNameValuePair("clientPhone", tm.getLine1Number()));
							params.add(new BasicNameValuePair("clientType", "dookhdclock"));
							params.add(new BasicNameValuePair("clientName", "运程预测"));
							params.add(new BasicNameValuePair("clientRemarks","运程预测:" + Sizhu.getDate(cdate)));
							UrlEncodedFormEntity entity=null;
							try {
								 entity =  new UrlEncodedFormEntity(params,HTTP.UTF_8);
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}
							request.setEntity(entity);
							
							DefaultHttpClient client = new DefaultHttpClient();
							try {
								HttpResponse response = client.execute(request);
								
							} catch (ClientProtocolException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}.start();
					/*TimePickerDialog tpd = new TimePickerDialog(ClockActivity.this,
							new OnTimeSetListener() {
								
								@Override
								public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
									// TODO Auto-generated method stub
									cdate.set(Calendar.HOUR_OF_DAY, hourOfDay);
									mv.setDate(cdate);
									v_mess.setText(Sizhu.wuxing(bazi, cdate));
									popup.showAtLocation(mv, Gravity.CENTER_HORIZONTAL, 0, 0);
								}
							}, 12, 12, true);
					
					tpd.show();*/
					
				}
    			
    		}, cdate.get(Calendar.YEAR), cdate.get(Calendar.MONTH), cdate.get(Calendar.DAY_OF_MONTH)); 
    		dpd.show();
    	}
    		break;
    	case 2:{     //yc.add(1, 1, 3, "农历日期").setIcon(R.drawable.date);
    		if (bazi == null) { 
    			Toast.makeText(this, "请先输入生日", Toast.LENGTH_SHORT).show();
    			return super.onOptionsItemSelected(item);
    		}
    		
    		final Calendar cdate = new GregorianCalendar();
    		final NongLi2 nl2 = new NongLi2();
    		final NongLi nl = new NongLi();
			nl.setCalendar(cdate);
			
    		DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					// TODO Auto-generated method stub
					if (year >= 2050){
						Toast.makeText(ClockActivity.this, "年不能超过2050年", Toast.LENGTH_SHORT).show();
						return;
					} else if (year < nl.getYear()){
						Toast.makeText(ClockActivity.this, "年不能小于今年", Toast.LENGTH_SHORT).show();
						return;
					}
					
					final Calendar cdate = new GregorianCalendar(year, monthOfYear, dayOfMonth - 1);
					cdate.set(Calendar.HOUR_OF_DAY, new GregorianCalendar().get(Calendar.HOUR_OF_DAY));
					mv.setDate(cdate);
					
					v_mess.setText(Sizhu.wuxingN(bazi, cdate));
					popup.showAtLocation(mv, Gravity.CENTER_HORIZONTAL, 0, 0);
					new Thread(){
						public void run(){
							TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
							String uri = "http://223.4.86.22/add_phoneclient";
							HttpPost request = new HttpPost(uri);
							List<NameValuePair> params = new ArrayList<NameValuePair>();
							// 添加请求参数
							params.add(new BasicNameValuePair("clientPhone", tm.getLine1Number()));
							params.add(new BasicNameValuePair("clientType", "dookhdclock"));
							params.add(new BasicNameValuePair("clientName", "运程预测"));
							params.add(new BasicNameValuePair("clientRemarks","运程预测:" + Sizhu.getDate(cdate)));
							UrlEncodedFormEntity entity=null;
							try {
								 entity =  new UrlEncodedFormEntity(params,HTTP.UTF_8);
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}
							request.setEntity(entity);
							
							DefaultHttpClient client = new DefaultHttpClient();
							try {
								HttpResponse response = client.execute(request);
								
							} catch (ClientProtocolException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}.start();
					/*TimePickerDialog tpd = new TimePickerDialog(ClockActivity.this,
							new OnTimeSetListener() {
								
								@Override
								public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
									// TODO Auto-generated method stub
									cdate.set(Calendar.HOUR_OF_DAY, hourOfDay);
									mv.setDate(cdate);
									v_mess.setText(Sizhu.wuxing(bazi, cdate));
									popup.showAtLocation(mv, Gravity.CENTER_HORIZONTAL, 0, 0);
								}
							}, 12, 12, true);
					
					tpd.show();*/
					
				}
    			
    		}, nl.getYear(), nl.getMonth() - 1, nl.getDay()); 
    		dpd.show();
    	}
    		break;
    	case 7:{ //sm.add(1, 7, 4, "好友农历生日")
    		if (bazi == null) { 
    			Toast.makeText(this, "请先输入自己生日", Toast.LENGTH_SHORT).show();
    			return super.onOptionsItemSelected(item);
    		}
    		final Calendar cdate = new GregorianCalendar();
    		final NongLi2 nl2 = new NongLi2();
    		final NongLi nl = new NongLi();
			nl.setCalendar(cdate);

    		DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){
    			
				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					// TODO Auto-generated method stub
					if (year > nl.getYear() ){
						Toast.makeText(ClockActivity.this, "生日不能超过今天", Toast.LENGTH_SHORT).show();
						return;
					} else if (year == nl.getYear() && monthOfYear > nl.getMonth() - 1 ) {
						Toast.makeText(ClockActivity.this, "生日不能超过今天", Toast.LENGTH_SHORT).show();
						return;
					} else if (year == nl.getYear()&& monthOfYear == nl.getMonth() - 1
							&& dayOfMonth > nl.getDay()) {
						Toast.makeText(ClockActivity.this, "生日不能超过今天", Toast.LENGTH_SHORT).show();
						return;
					}
					
			       if (year >= 2012)
			    	   dayOfMonth = dayOfMonth - 1;
					String soldate  = nl2.getLundarToSolar(year, monthOfYear + 1, dayOfMonth);

					final Calendar cdate = new GregorianCalendar(
							Integer.parseInt(soldate.substring(0, 4)),
							Integer.parseInt(soldate.substring(4, 6))-1, 
							Integer.parseInt(soldate.substring(6)));
					TimePickerDialog tpd = new TimePickerDialog(ClockActivity.this,
							new OnTimeSetListener() {
								
								@Override
								public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
									// TODO Auto-generated method stub
									cdate.set(Calendar.HOUR_OF_DAY, hourOfDay);
									cdate.set(Calendar.MINUTE, minute);
									
									v_mess.setText(Sizhu.haoyou(bazi, cdate));
									popup.showAtLocation(mv, Gravity.CENTER_HORIZONTAL, 0, 0);
									new Thread(){
										public void run(){
											TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
											String uri = "http://223.4.86.22/add_phoneclient";
											HttpPost request = new HttpPost(uri);
											List<NameValuePair> params = new ArrayList<NameValuePair>();
											// 添加请求参数
											params.add(new BasicNameValuePair("clientPhone", tm.getLine1Number()));
											params.add(new BasicNameValuePair("clientType", "dookhdclock"));
											params.add(new BasicNameValuePair("clientName", "好友助运"));
											params.add(new BasicNameValuePair("clientRemarks","好友助运:" + Sizhu.getDate(cdate)));
											UrlEncodedFormEntity entity=null;
											try {
												 entity =  new UrlEncodedFormEntity(params,HTTP.UTF_8);
											} catch (UnsupportedEncodingException e) {
												e.printStackTrace();
											}
											request.setEntity(entity);
											
											DefaultHttpClient client = new DefaultHttpClient();
											try {
												HttpResponse response = client.execute(request);
												
											} catch (ClientProtocolException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										}
									}.start();
								}
							}, 12, 12, true);
					tpd.show();
					
				}
    			
    		}, nl.getYear(), nl.getMonth() - 1, nl.getDay()); 
    		dpd.show();
    	}
    		break;	
    	case 8:{  //sm.add(1, 8, 3, "好友阳历生日")
    		if (bazi == null) { 
    			Toast.makeText(this, "请先输入自己生日", Toast.LENGTH_SHORT).show();
    			return super.onOptionsItemSelected(item);
    		}
    		final Calendar cdate = new GregorianCalendar();
    		DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					// TODO Auto-generated method stub
					if (year > cdate.get(Calendar.YEAR) ){
						Toast.makeText(ClockActivity.this, "生日不能超过今天", Toast.LENGTH_SHORT).show();
						return;
					} else if (year == cdate.get(Calendar.YEAR) && monthOfYear > cdate.get(Calendar.MONTH) ) {
						Toast.makeText(ClockActivity.this, "生日不能超过今天", Toast.LENGTH_SHORT).show();
						return;
					} else if (year == cdate.get(Calendar.YEAR) && monthOfYear == cdate.get(Calendar.MONTH)
							&& dayOfMonth > cdate.get(Calendar.DAY_OF_MONTH)) {
						Toast.makeText(ClockActivity.this, "生日不能超过今天", Toast.LENGTH_SHORT).show();
						return;
					}
					final Calendar cdate = new GregorianCalendar(year, monthOfYear, dayOfMonth);
					TimePickerDialog tpd = new TimePickerDialog(ClockActivity.this,
							new OnTimeSetListener() {
								
								@Override
								public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
									// TODO Auto-generated method stub
									cdate.set(Calendar.HOUR_OF_DAY, hourOfDay);
									cdate.set(Calendar.MINUTE, minute);
									v_mess.setText(Sizhu.haoyou(bazi, cdate));
									popup.showAtLocation(mv, Gravity.CENTER_HORIZONTAL, 0, 0);
									new Thread(){
										public void run(){
											TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
											String uri = "http://223.4.86.22/add_phoneclient";
											HttpPost request = new HttpPost(uri);
											List<NameValuePair> params = new ArrayList<NameValuePair>();
											// 添加请求参数
											params.add(new BasicNameValuePair("clientPhone", tm.getLine1Number()));
											params.add(new BasicNameValuePair("clientType", "dookhdclock"));
											params.add(new BasicNameValuePair("clientName", "命中运程"));
											params.add(new BasicNameValuePair("clientRemarks","命中运程:" + Sizhu.getDate(cdate)));
											UrlEncodedFormEntity entity=null;
											try {
												 entity =  new UrlEncodedFormEntity(params,HTTP.UTF_8);
											} catch (UnsupportedEncodingException e) {
												e.printStackTrace();
											}
											request.setEntity(entity);
											
											DefaultHttpClient client = new DefaultHttpClient();
											try {
												HttpResponse response = client.execute(request);
												
											} catch (ClientProtocolException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										}
									}.start();
								}
							}, 12, 12, true);
					tpd.show();
					
				}
    			
    		}, cdate.get(Calendar.YEAR), cdate.get(Calendar.MONTH), cdate.get(Calendar.DAY_OF_MONTH)); 
    		dpd.show();
    	}
    		break;	
    	case 5:{//menu.add(1, 5, 3, "命中运程")
    		if (bazi == null) { 
    			Toast.makeText(this, "请先输入生日", Toast.LENGTH_SHORT).show();
    			return super.onOptionsItemSelected(item);
    		}
    		
			v_mess.setText(Sizhu.getGua(birthday,Sizhu.getNongLi(birthday)));
    		
    		//mv.setRun(false);
    		//v_mess.setText(Sizhu.getGua(cdate));
			popup.showAtLocation(mv, Gravity.CENTER_HORIZONTAL, 0, 0);
			new Thread(){
				public void run(){
					TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
					String uri = "http://223.4.86.22/add_phoneclient";
					HttpPost request = new HttpPost(uri);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					// 添加请求参数
					params.add(new BasicNameValuePair("clientPhone", tm.getLine1Number()));
					params.add(new BasicNameValuePair("clientType", "dookhdclock"));
					params.add(new BasicNameValuePair("clientName", "命中运程"));
					params.add(new BasicNameValuePair("clientRemarks","命中运程:" + Sizhu.getDate(birthday)));
					UrlEncodedFormEntity entity=null;
					try {
						 entity =  new UrlEncodedFormEntity(params,HTTP.UTF_8);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					request.setEntity(entity);
					
					DefaultHttpClient client = new DefaultHttpClient();
					try {
						HttpResponse response = client.execute(request);
						
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
    		
    	}
    		break;
    	/*case 2:{
    		mv.setToday();
    		mv.setRun(true);
            new Thread(mv).start();
    		if (bazi == null) { 
    			Toast.makeText(this, "请先输入自己生日", Toast.LENGTH_SHORT).show();
    			return super.onOptionsItemSelected(item);
    		}
    		
    		v_mess.setText(Sizhu.todaywuxing(bazi));
    		//mv.setRun(false);
    		//v_mess.setText(Sizhu.getGua(cdate));
			popup.showAtLocation(mv, Gravity.CENTER_HORIZONTAL, 0, 0);
    	} break;*/
    	case 3:{//mi.add(1, 3, 4, "农历生日")
    		final Calendar cdate = new GregorianCalendar();
    		final NongLi2 nl2 = new NongLi2();
    		final NongLi nl = new NongLi();
			nl.setCalendar(cdate);

    		DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){
    			
				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					// TODO Auto-generated method stub
					if (year > nl.getYear() ){
						Toast.makeText(ClockActivity.this, "生日不能超过今天", Toast.LENGTH_SHORT).show();
						return;
					} else if (year == nl.getYear() && monthOfYear > nl.getMonth() - 1 ) {
						Toast.makeText(ClockActivity.this, "生日不能超过今天", Toast.LENGTH_SHORT).show();
						return;
					} else if (year == nl.getYear()&& monthOfYear == nl.getMonth() - 1
							&& dayOfMonth > nl.getDay()) {
						Toast.makeText(ClockActivity.this, "生日不能超过今天", Toast.LENGTH_SHORT).show();
						return;
					}
					
			       if (year >= 2012)
			    	   dayOfMonth = dayOfMonth - 1;
					String soldate  = nl2.getLundarToSolar(year, monthOfYear + 1, dayOfMonth);

					final Calendar cdate = new GregorianCalendar(
							Integer.parseInt(soldate.substring(0, 4)),
							Integer.parseInt(soldate.substring(4, 6))-1, 
							Integer.parseInt(soldate.substring(6)));
					TimePickerDialog tpd = new TimePickerDialog(ClockActivity.this,
							new OnTimeSetListener() {
								
								@Override
								public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
									// TODO Auto-generated method stub
									cdate.set(Calendar.HOUR_OF_DAY, hourOfDay);
									cdate.set(Calendar.MINUTE, minute);
									mv.setDate(cdate);
									birthday = cdate;
									bazi=GetBaZi.get(cdate);
									
									SharedPreferences sp = ClockActivity.this.getSharedPreferences("dook", ClockActivity.this.MODE_WORLD_WRITEABLE);
									SharedPreferences.Editor ed = sp.edit();
									ed.putInt("year", cdate.get(Calendar.YEAR));
									ed.putInt("month", cdate.get(Calendar.MONTH));
									ed.putInt("day", cdate.get(Calendar.DAY_OF_MONTH));
									ed.putInt("hour", cdate.get(Calendar.HOUR_OF_DAY));
									ed.putInt("minute", cdate.get(Calendar.MINUTE));
									ed.putString("bazi", bazi);
									
									ed.commit();
									v_mess.setText(Sizhu.getGua(cdate,Sizhu.getNongLi(cdate)));
									
									popup.showAtLocation(mv, Gravity.CENTER_HORIZONTAL, 0, 0);
									new Thread(){
										public void run(){
											TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
											String uri = "http://223.4.86.22/add_phoneclient";
											HttpPost request = new HttpPost(uri);
											List<NameValuePair> params = new ArrayList<NameValuePair>();
											// 添加请求参数
											params.add(new BasicNameValuePair("clientPhone", tm.getLine1Number()));
											params.add(new BasicNameValuePair("clientType", "dookhdclock"));
											params.add(new BasicNameValuePair("clientName", "命中运程"));
											params.add(new BasicNameValuePair("clientRemarks","命中运程:" + Sizhu.getDate(cdate)));
											UrlEncodedFormEntity entity=null;
											try {
												 entity =  new UrlEncodedFormEntity(params,HTTP.UTF_8);
											} catch (UnsupportedEncodingException e) {
												e.printStackTrace();
											}
											request.setEntity(entity);
											
											DefaultHttpClient client = new DefaultHttpClient();
											try {
												HttpResponse response = client.execute(request);
												
											} catch (ClientProtocolException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										}
									}.start();
								}
							}, 12, 12, true);
					tpd.show();
					
				}
    			
    		}, nl.getYear(), nl.getMonth() - 1, nl.getDay()); 
    		dpd.show();
    	}break;
    	case 4:{//mi.add(1, 4, 3, "阳历生日")
    		final Calendar cdate = new GregorianCalendar();
    		DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					// TODO Auto-generated method stub
					if (year > cdate.get(Calendar.YEAR) ){
						Toast.makeText(ClockActivity.this, "生日不能超过今天", Toast.LENGTH_SHORT).show();
						return;
					} else if (year == cdate.get(Calendar.YEAR) && monthOfYear > cdate.get(Calendar.MONTH) ) {
						Toast.makeText(ClockActivity.this, "生日不能超过今天", Toast.LENGTH_SHORT).show();
						return;
					} else if (year == cdate.get(Calendar.YEAR) && monthOfYear == cdate.get(Calendar.MONTH)
							&& dayOfMonth > cdate.get(Calendar.DAY_OF_MONTH)) {
						Toast.makeText(ClockActivity.this, "生日不能超过今天", Toast.LENGTH_SHORT).show();
						return;
					}
					final Calendar cdate = new GregorianCalendar(year, monthOfYear, dayOfMonth);
					TimePickerDialog tpd = new TimePickerDialog(ClockActivity.this,
							new OnTimeSetListener() {
								
								@Override
								public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
									// TODO Auto-generated method stub
									cdate.set(Calendar.HOUR_OF_DAY, hourOfDay);
									cdate.set(Calendar.MINUTE, minute);
									mv.setDate(cdate);
									bazi=GetBaZi.get(cdate);
									birthday = cdate;
									bazi=GetBaZi.get(cdate);
									
									SharedPreferences sp = ClockActivity.this.getSharedPreferences("dook", ClockActivity.this.MODE_WORLD_WRITEABLE);
									SharedPreferences.Editor ed = sp.edit();
									ed.putInt("year", cdate.get(Calendar.YEAR));
									ed.putInt("month", cdate.get(Calendar.MONTH));
									ed.putInt("day", cdate.get(Calendar.DAY_OF_MONTH));
									ed.putInt("hour", cdate.get(Calendar.HOUR_OF_DAY));
									ed.putInt("minute", cdate.get(Calendar.MINUTE));
									ed.putString("bazi", bazi);
									ed.commit();
									v_mess.setText(Sizhu.getGua(cdate,Sizhu.getNongLi(cdate)));
									popup.showAtLocation(mv, Gravity.CENTER_HORIZONTAL, 0, 0);
									new Thread(){
										public void run(){
											TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
											String uri = "http://223.4.86.22/add_phoneclient";
											HttpPost request = new HttpPost(uri);
											List<NameValuePair> params = new ArrayList<NameValuePair>();
											// 添加请求参数
											params.add(new BasicNameValuePair("clientPhone", tm.getLine1Number()));
											params.add(new BasicNameValuePair("clientType", "dookhdclock"));
											params.add(new BasicNameValuePair("clientName", "命中运程"));
											params.add(new BasicNameValuePair("clientRemarks","命中运程:" + Sizhu.getDate(cdate)));
											UrlEncodedFormEntity entity=null;
											try {
												 entity =  new UrlEncodedFormEntity(params,HTTP.UTF_8);
											} catch (UnsupportedEncodingException e) {
												e.printStackTrace();
											}
											request.setEntity(entity);
											
											DefaultHttpClient client = new DefaultHttpClient();
											try {
												HttpResponse response = client.execute(request);
												
											} catch (ClientProtocolException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										}
									}.start();
								}
							}, 12, 12, true);
					tpd.show();
					
				}
    			
    		}, cdate.get(Calendar.YEAR), cdate.get(Calendar.MONTH), cdate.get(Calendar.DAY_OF_MONTH)); 
    		dpd.show();
    	}break;
    	
    	}
    	return super.onOptionsItemSelected(item);
    }
    
    //初起化设置窗口
    private void initPopupMenu() {
		mViewFlipper = new ViewFlipper(this);
		
	
		LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE); 
		/*ScrollView sv = new ScrollView(this);
		TextView vmess = new TextView(this);
		vmess.setBackgroundColor(Color.BLUE);
		vmess.setTextColor(Color.RED);
		vmess.setText("fdasfdsalfjldksaj\ndfsafdsafdsafdsa\ndsafdsafdsaffds111111111111111111111111111111111111111111111111111111111111111111111afdsafdsafdsafdsdsafsdafdsafdsa\nfdasfdsalfjldksaj\ndfsafdsafdsafdsa\ndsafdsafdsaffdsafdsafdsafdsafdsdsafsdafdsafdsa\nfdasfdsalfjldksaj\ndfsafdsafdsafdsa\ndsafdsafdsaffdsafdsafdsafdsafdsdsafsdafdsafdsa\n");
		sv.addView(vmess);
		Button button = new Button(this);
		button.setText("关闭");
		
		sv.addView(button);*/
		View sv = inflater.inflate(R.layout.pop, null);
		
		ImageButton button = (ImageButton) sv.findViewById(R.id.b_exit);
		button.setAlpha(100);
		v_mess = (TextView) sv.findViewById(R.id.v_mess);
		//v_mess.setText("dafdasfdsafaaaaaaaa\n111111111111111111111111111111111111111111\n222222222222222222222\ndafdasfdsafaaaaaaaa\n111111111111111111111111111111111111111111\n222222222222222222222\ndafdasfdsafaaaaaaaa\n111111111111111111111111111111111111111111\n222222222222222222222\ndafdasfdsafaaaaaaaa\n111111111111111111111111111111111111111111\n222222222222222222222\ndafdasfdsafaaaaaaaa\n111111111111111111111111111111111111111111\n222222222222222222222\ndafdasfdsafaaaaaaaa\n111111111111111111111111111111111111111111\n222222222222222222222\n");
		mViewFlipper.addView(sv);
		mViewFlipper.setFlipInterval(60000);
		WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
		popup = new PopupWindow(mViewFlipper, (int)(wm.getDefaultDisplay().getWidth() * 15.0 / 16),
				(int)(wm.getDefaultDisplay().getHeight() * 300.0 / 480));
		
		//System.out.println(mv.getMVWidth() + "*" + mv.getMVHeight());
		popup.setFocusable(true);
		popup.update();
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popup.dismiss();
				mv.setToday();
	    		mv.setRun(true);
	            new Thread(mv).start();
			}
		});
		
	}

    @Override
    public void onDestroy(){
    	super.onDestroy();
    	Toast.makeText(this, "度客人生导航已经退出", Toast.LENGTH_LONG).show();
    	mv.setRun(false);
    }
}