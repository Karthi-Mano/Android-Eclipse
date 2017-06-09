package com.dook.hdclock;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class MainView extends View implements Runnable{
	//屏幕宽度
	private int width;
	
	public int getMVWidth(){
		return width;
	}
	
	//屏幕高度
	private int height;
	
	public int getMVHeight(){
		return height;
	}
	//把屏幕宽度20等分 width/20
	private int dwidth;
	
	//秒线程运行控制逻辑变量
	private boolean isRun = false;
	public boolean isRun() {
		return isRun;
	}
	public void setRun(boolean isRun) {
		this.isRun = isRun;
	}
	//12时辰的点坐标
	private Point[] ps12 = new Point[12];
	
	//每个圈上的点坐标容器（天干）
	private Point[] qps1 = new Point[12];
	private Point[] qps2 = new Point[12];
	private Point[] qps3 = new Point[12];
	private Point[] qps4 = new Point[12];
	private Point[] qps5 = new Point[12];
	
	//5个圈，每圈12个点 共60个点坐标（天干）
	private Point[][] qps = new Point[][]{qps1,qps2,qps3,qps4,qps5};
	
	
	//每个圈上的点坐标容器（数字）
	private Point[] nqps1 = new Point[12];
	private Point[] nqps2 = new Point[12];
	private Point[] nqps3 = new Point[12];
	private Point[] nqps4 = new Point[12];
	private Point[] nqps5 = new Point[12];
	//5个圈，每圈12个点 共60个点坐标（数字）
	private Point[][] nqps = new Point[][]{nqps1,nqps2,nqps3,nqps4,nqps5};
	
	//12个地支 从0度开始画
	private final String[] time12 = new String[]{"酉","申","未","午",
			"巳","辰","卯","寅","丑","子","亥","戌"};
	
	//表圈上的数字
	private final String[] num24 = new String[] {
			"17","15","13","11","09","07","05","03","01","23","21","19"
	};
	
	//表圈上的数字的点坐标 
	private final Point[] pnum24 = new Point[12] ;
	
	//第一圈上的天干 从0度开始画
	private final String[] quan1 = new String[]{"癸","壬","辛","庚",
			"己","戊","丁","丙","乙","甲","乙","甲"};
	//第二圈上的天干 从0度开始画
	private final String[] quan2 = new String[]{"乙","甲","癸","壬",
			"辛","庚","己","戊","丁","丙","丁","丙"};
	//第三圈上的天干 从0度开始画
	private final String[] quan3 = new String[]{"丁","丙","乙","甲","癸","壬",
			"辛","庚","己","戊","己","戊"};
	//第四圈上的天干 从0度开始画
	private final String[] quan4 = new String[]{"己","戊","丁","丙","乙","甲",
			"癸","壬","辛","庚","辛","庚"};
	//第五圈上的天干 从0度开始画
	private final String[] quan5 = new String[]{"辛","庚","己","戊","丁","丙",
			"乙","甲","癸","壬","癸","壬"};
	//第一圈上的数字 从0度开始画
	private final String[] num1 = new String[]{"09","08","07","06",
			"05","04","03","02","01","00","11","10"};
	//第二圈上的天干 从0度开始画
	private final String[] num2 = new String[]{"21","20","19","18",
			"17","16","15","14","13","12","23","22"};
	//第三圈上的天干 从0度开始画
	private final String[] num3 = new String[]{"33","32","31","30",
			"29","28","27","26","25","24","35","34"};
	//第四圈上的天干 从0度开始画
	private final String[] num4 = new String[]{"45","44","43","42",
			"41","40","39","38","37","36","47","46"};
	//第五圈上的天干 从0度开始画
	private final String[] num5 = new String[]{"57","56","55","54",
			"53","52","51","50","49","48","59","58"};
	//五个圈上的天干 从0度开始画
	private final String[][] quans = new String[][]{quan1,quan2,quan3,quan4,quan5};
	//五个圈上的数字 从0度开始画
	private final String[][] nums = new String[][]{num1,num2,num3,num4,num5};
	
	//一圈12个角度，从子开始
	private final float[]  dus = new float[12];
	{
		for (int i = 0; i < 12 ; i++) {
			dus[i] = 255 - i * 30;
		}
	}
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private int second;
	private Calendar cdate;
	private Calendar ctime;
	private boolean isToday = true;
	
	// 5个圈的数组
	private final int[] daos = new int[] {
			4,3,2,1,0
	};
	
	public MainView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
	}
	public MainView(Context context) {
		super(context);
		
		// TODO Auto-generated constructor stub
	}
	
	//每个圈上的12个点坐标，用来写天干和数字
	private void initQuan(int type){
		
		double dj = Math.PI/6;
		
		for (int i = 0; i < 12; i++) {
			Point p = new Point();
			p.x = (float)(width/2 + width/20 * (5 + type) * Math.cos(dj*i));
			p.y = (float)(width/2 - width/20 * (5 + type) * Math.sin(dj*i));
			qps[type - 1][i] = p;
			Point p2 = new Point();
			p2.x = (float)(width/2 + width/20 * (5 + type) * Math.cos(dj*i - Math.PI/20));
			p2.y = (float)(width/2 - width/20 * (5 + type) * Math.sin(dj*i - Math.PI/20));
			nqps[type-1][i] = p2;
		}
	}
	//表圈上的数字坐标，已经不用
	private void initPnum24(){
		double dj = Math.PI/6;
		for (int i = 0; i < 12; i++) {
			Point p = new Point();
			p.x = (float)(width/2 + width/4 * Math.cos(dj*i + Math.PI/12));
			p.y = (float)(width/2 - width/4 * Math.sin(dj*i + Math.PI/12));
			pnum24[i] = p;
		}
	}
	//表圈上12个点坐标初始化 水平方向开始
	private void initP12(){
		double dj = Math.PI/6;
		for (int i = 0; i < 12; i++) {
			Point p = new Point();
			p.x = (float)(width/2 + width/4 * Math.cos(dj*i));
			p.y = (float)(width/2 - width/4 * Math.sin(dj*i));
			ps12[i] = p;
		}
	}
	
	@Override
	public void draw (final Canvas canvas) {
		Paint p = new Paint();
		p.setColor(Color.GREEN);
		width = this.getWidth();
	    height = this.getBottom();
	    initP12();
	    initPnum24();
	    //圈上的12个点坐标初始化，用来写天干和数字
	    for (int i = 1; i < 6 ;i++) {
	    	initQuan(i);
	    }
	    //画绿色背景
		canvas.drawRect(0, 0, width ,height , p);
		
		//宽度20等分
		dwidth = width/2/10;
		
		
		/*
		 *   1900己亥  35   1 丙子月  12   1甲戌日 10
		 */
		if (isToday) {
			cdate = new GregorianCalendar(); 		
		}
		//cdate.set(2018, 6, 25);
		
		int ryear = 0;
		int rmonth = 0;
		int rday =  0;
		
		NongLi nl = new NongLi();
		nl.setCalendar(cdate);
		ryear = nl.getYear();
		rmonth = nl.getMonth();
		
		
		rday =  cdate.get(Calendar.DAY_OF_YEAR);
		
		ctime = new GregorianCalendar(); 
		if (isToday) {
			hour = ctime.get(Calendar.HOUR_OF_DAY);
			minute = ctime.get(Calendar.MINUTE);
		} else {
			hour = cdate.get(Calendar.HOUR_OF_DAY);
			minute = cdate.get(Calendar.MINUTE);
		}
		
		
		second = ctime.get(Calendar.SECOND);
		// 计算年月日的天干地支1900 1 1  公立1899 己亥 35 12 丙子12  1甲戌  10 
		year = (ryear - 1899 + 35) % 60 ;
	
		month = ((ryear - 1899) * 12 + rmonth + 1) % 60;
		day =( (int)(Math.ceil((cdate.get(Calendar.YEAR) - 1900)  / 4.0)) + (cdate.get(Calendar.YEAR) - 1900) * 365 + rday + 8  ) % 60;
		
		drawYMDHMS(canvas,p,year,month,day,hour,minute,second);
		
		
		
		
		// 背景
		p.setColor(Color.RED);
		p.setStyle(Style.STROKE);
		p.setStrokeWidth(2);
		//0道
		drawHuan(canvas, p, dwidth * 0);
		//1道
		drawHuan(canvas, p, dwidth * 1);
		drawHuan(canvas, p, dwidth * 2);
		drawHuan(canvas, p, dwidth * 3);
		drawHuan(canvas, p, dwidth * 4);
		drawHuan(canvas, p, dwidth * 5);
		
	
		//画圆心
		canvas.drawCircle(width/2, width/2, 1, p);
		//画底线
		canvas.drawLine(0, width, width, width, p);
		//画竖线
		
		// 画六条斜线
		canvas.drawLine(
				(float)(width/2 + width/2 * Math.cos(Math.PI/6 * 3 - Math.PI/12)),
				(float)(width/2 - width/2 * Math.sin(Math.PI/6 * 3 - Math.PI/12)), 
				(float)(width/2 - width/2 * Math.cos(Math.PI/6 * 3 - Math.PI/12)),
				(float)(width/2 + width/2 * Math.sin(Math.PI/6 * 3 - Math.PI/12)), p);
		canvas.drawLine(
				(float)(width/2 + width/2 * Math.cos(Math.PI/6 * 0 - Math.PI/12)),
				(float)(width/2 - width/2 * Math.sin(Math.PI/6 * 0 - Math.PI/12)), 
				(float)(width/2 - width/2 * Math.cos(Math.PI/6 * 0 - Math.PI/12)),
				(float)(width/2 + width/2 * Math.sin(Math.PI/6 * 0 - Math.PI/12)), p);
		canvas.drawLine(
				(float)(width/2 + width/2 * Math.cos(Math.PI/6 * 1 - Math.PI/12)),
				(float)(width/2 - width/2 * Math.sin(Math.PI/6 * 1 - Math.PI/12)), 
				(float)(width/2 - width/2 * Math.cos(Math.PI/6 * 1 - Math.PI/12)),
				(float)(width/2 + width/2 * Math.sin(Math.PI/6 * 1 - Math.PI/12)), p);
		canvas.drawLine(
				(float)(width/2 + width/2 * Math.cos(Math.PI/6 * 2 - Math.PI/12)),
				(float)(width/2 - width/2 * Math.sin(Math.PI/6 * 2 - Math.PI/12)), 
				(float)(width/2 - width/2 * Math.cos(Math.PI/6 * 2 - Math.PI/12)),
				(float)(width/2 + width/2 * Math.sin(Math.PI/6 * 2 - Math.PI/12)), p);
		canvas.drawLine(
				(float)(width/2 + width/2 * Math.cos(Math.PI/6 * 4 - Math.PI/12)),
				(float)(width/2 - width/2 * Math.sin(Math.PI/6 * 4 - Math.PI/12)), 
				(float)(width/2 - width/2 * Math.cos(Math.PI/6 * 4 - Math.PI/12)),
				(float)(width/2 + width/2 * Math.sin(Math.PI/6 * 4 - Math.PI/12)), p);
		canvas.drawLine(
				(float)(width/2 + width/2 * Math.cos(Math.PI/6 * 5 - Math.PI/12)),
				(float)(width/2 - width/2 * Math.sin(Math.PI/6 * 5 - Math.PI/12)), 
				(float)(width/2 - width/2 * Math.cos(Math.PI/6 * 5 - Math.PI/12)),
				(float)(width/2 + width/2 * Math.sin(Math.PI/6 * 5 - Math.PI/12)), p);
		
		
		p.setColor(Color.GREEN);
		p.setStyle(Style.FILL);
		//挡住中间部分的圈
		canvas.drawCircle(width/2, width/2, 5 * dwidth , p);
		
		// 画表圈上的红点
		p.setColor(Color.RED);
		p.setStyle(Style.FILL);
		for (int i = 0; i < 12; i++) {
			canvas.drawCircle(ps12[i].x, ps12[i].y, 2, p);
		}
		
		//画圆心
		canvas.drawCircle(width/2, width/2, 2, p);
		// 画12个地支
		p.setColor(Color.BLUE);
		p.setTextSize(width/25);
		for (int i = 0; i < 12; i++) {
			if (i == 0) {
				canvas.drawText(time12[i], ps12[i].x - width/25, ps12[i].y, p);
			} else if (i > 0 && i < 3) {
				canvas.drawText(time12[i], ps12[i].x - width/25, ps12[i].y + width/25, p);
			}else if (i == 3) {
				canvas.drawText(time12[i], ps12[i].x - width/50, ps12[i].y + width/25, p);
			}else if (i == 4 || i == 5) {
				canvas.drawText(time12[i], ps12[i].x , ps12[i].y + width/25, p);
			} else if (i == 9) {
				canvas.drawText(time12[i], ps12[i].x  - width/50, ps12[i].y, p);
			} else if (i > 9) {
				canvas.drawText(time12[i], ps12[i].x  - width/25, ps12[i].y, p);
			}
			else {
				canvas.drawText(time12[i], ps12[i].x, ps12[i].y, p);
			}
		}
		
		//画圈上天干和数字
		
		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < 12;i++){
				if (i == 0) {
					canvas.drawText(quans[j][i], qps[j][i].x - width/25, qps[j][i].y, p);
					canvas.drawText(nums[j][i], nqps[j][i].x - width/25, nqps[j][i].y, p);
				} else if (i > 0 && i < 3) {
					canvas.drawText(quans[j][i], qps[j][i].x - width/25, qps[j][i].y + width/25, p);
					canvas.drawText(nums[j][i], nqps[j][i].x - width/25, nqps[j][i].y+ width/25, p);
				}else if (i == 3) {
					canvas.drawText(quans[j][i], qps[j][i].x - width/50, qps[j][i].y + width/25, p);
					canvas.drawText(nums[j][i], nqps[j][i].x - width/50, nqps[j][i].y+ width/25, p);
				}else if (i == 4 || i == 5) {
					canvas.drawText(quans[j][i], qps[j][i].x , qps[j][i].y + width/25, p);
					canvas.drawText(nums[j][i], nqps[j][i].x , nqps[j][i].y+ width/25, p);
				} else if (i == 9) {
					canvas.drawText(quans[j][i], qps[j][i].x  - width/50, qps[j][i].y, p);
					canvas.drawText(nums[j][i], nqps[j][i].x - width/50, nqps[j][i].y, p);
				} else if (i > 9) {
					canvas.drawText(quans[j][i], qps[j][i].x  - width/25, qps[j][i].y, p);
					canvas.drawText(nums[j][i], nqps[j][i].x - width/25, nqps[j][i].y, p);
				}
				else {
					canvas.drawText(quans[j][i], qps[j][i].x, qps[j][i].y, p);
					canvas.drawText(nums[j][i], nqps[j][i].x , nqps[j][i].y, p);
				}
			}
		}
		
		// 画年的颜色标识
		RectF rf = new RectF();
		rf.left = width/12;
		rf.top = width + width/22;
		rf.bottom =  width + width/11;
		rf.right = width*2/12;
		p.setColor(Color.YELLOW);
		p.setStyle(Style.FILL);
		canvas.drawRect(rf, p);
		p.setTextSize(width/20);
		canvas.drawText("年", width*2/12,width + width/11, p);
		
		// 画月的颜色标识
		rf.left = width*3/12;
		rf.top = width + width/22;
		rf.bottom =  width + width/11;
		rf.right = width*4/12;
		p.setColor(Color.argb(255, 51, 111, 115));
		p.setStyle(Style.FILL);
		canvas.drawRect(rf, p);
		p.setTextSize(width/20);
		canvas.drawText("月", width*4/12,width + width/11, p);
		
		// 画日的颜色标识
		rf.left = width*5/12;
		rf.top = width + width/22;
		rf.bottom =  width + width/11;
		rf.right = width*6/12;
		p.setColor(Color.argb(255, 212, 129, 10));
		p.setStyle(Style.FILL);
		canvas.drawRect(rf, p);
		p.setTextSize(width/20);
		canvas.drawText("日", width*6/12,width + width/11, p);
		
		// 画时的颜色标识
		rf.left = width*7/12;
		rf.top = width + width/22;
		rf.bottom =  width + width/11;
		rf.right = width*8/12;
		p.setColor(Color.MAGENTA);
		p.setStyle(Style.FILL);
		canvas.drawRect(rf, p);
		p.setTextSize(width/20);
		canvas.drawText("时", width*8/12,width + width/11, p);
		
		// 画分的颜色标识
		rf.left = width*9/12;
		rf.top = width + width/22;
		rf.bottom =  width + width/11;
		rf.right = width*10/12;
		p.setColor(Color.argb(255, 133, 75, 150));
		p.setStyle(Style.FILL);
		canvas.drawRect(rf, p);
		p.setTextSize(width/20);
		canvas.drawText("分", width*10/12,width + width/11, p);
		
		// 画年月日的天干地支
		p.setColor(Color.BLUE);
		int vmonth = cdate.get(Calendar.MONTH) + 1;
		int vday = cdate.get(Calendar.DAY_OF_MONTH);
		if (vmonth < 10) {
			if (vday < 10)
				canvas.drawText(cdate.get(Calendar.YEAR) + "年0" + vmonth + "月0" + 
						vday + "日(" + 
						nl.toString() + ")",
						width/7,width + width/7, p);
			else
				canvas.drawText(cdate.get(Calendar.YEAR) + "年0" + vmonth + "月" + 
						vday + "日(" + 
						nl.toString() + ")",
						width/7,width + width/7, p);
		} else {
			if (vday < 10)
				canvas.drawText(cdate.get(Calendar.YEAR) + "年" + vmonth + "月0" + 
						vday + "日(" + 
						nl.toString() + ")",
						width/7,width + width/7, p);
			else
				canvas.drawText(cdate.get(Calendar.YEAR) + "年" + vmonth + "月" + 
						vday + "日(" + 
						nl.toString() + ")",
						width/7,width + width/7, p);
		}
		
		// 画时分秒的天干地支
		p.setColor(Color.BLUE);
		if (hour < 10) {
			if (minute < 10 && second < 10 ) {
				canvas.drawText("0" + hour + ":0" + minute + ":0" + 
						second + "(" + BaZi.get(hour, JiaZi.jiazis[day].charAt(0) + "") +  "时" + JiaZi.jiazis[minute] +  "分" + JiaZi.jiazis[second] + "秒)" ,
						width/7,width + width * 5/19, p);
			} else if (second < 10) {
				canvas.drawText("0" + hour + ":" + minute + ":0" + 
						second + "(" + BaZi.get(hour, JiaZi.jiazis[day].charAt(0) + "") +  "时" + JiaZi.jiazis[minute] +  "分" + JiaZi.jiazis[second] + "秒)" ,
						width/7,width + width * 5/19, p);
			} else if (minute < 10 ) {
				canvas.drawText("0" + hour + ":0" + minute + ":" + 
						second + "(" + BaZi.get(hour, JiaZi.jiazis[day].charAt(0) + "") +  "时" + JiaZi.jiazis[minute] +  "分" + JiaZi.jiazis[second] + "秒)" ,
						width/7,width + width * 5/19, p);
			} else {
				canvas.drawText("0" + hour + ":" + minute + ":" + 
						second + "(" + BaZi.get(hour, JiaZi.jiazis[day].charAt(0) + "") + "时" + JiaZi.jiazis[minute] +  "分" + JiaZi.jiazis[second] + "秒)",
						width/7,width + width * 5/19, p);
			}
		}else {
			if (minute < 10 && second < 10 ) {
				canvas.drawText(hour + ":0" + minute + ":0" + 
						second + "(" + BaZi.get(hour, JiaZi.jiazis[day].charAt(0) + "") +  "时" + JiaZi.jiazis[minute] +  "分" + JiaZi.jiazis[second] + "秒)" ,
						width/7,width + width * 5/19, p);
			} else if (second < 10) {
				canvas.drawText(hour + ":" + minute + ":0" + 
						second + "(" + BaZi.get(hour, JiaZi.jiazis[day].charAt(0) + "") +  "时" + JiaZi.jiazis[minute] +  "分" + JiaZi.jiazis[second] + "秒)" ,
						width/7,width + width * 5/19, p);
			} else if (minute < 10 ) {
				canvas.drawText(hour + ":0" + minute + ":" + 
						second + "(" + BaZi.get(hour, JiaZi.jiazis[day].charAt(0) + "") +  "时" + JiaZi.jiazis[minute] +  "分" + JiaZi.jiazis[second] + "秒)" ,
						width/7,width + width * 5/19, p);
			} else {
				canvas.drawText(hour + ":" + minute + ":" + 
						second + "(" + BaZi.get(hour, JiaZi.jiazis[day].charAt(0) + "") + "时" + JiaZi.jiazis[minute] +  "分" + JiaZi.jiazis[second] + "秒)" ,
						width/7,width + width * 5/19, p);
			}
		}
		
		String yz = JiaZi.jiazis[month];
//System.out.println("month:" + month);
/*
		if (yz.indexOf(JiaZi.months[month%12]) == -1) {
			if (JiaZi.jiazis[month - 1].indexOf(JiaZi.months[month%12]) != -1){
				yz = JiaZi.jiazis[month - 1];
			} else if (JiaZi.jiazis[month + 1].indexOf(JiaZi.months[month%12]) != -1){
				yz = JiaZi.jiazis[month + 1];
			}else if (JiaZi.jiazis[month + 2].indexOf(JiaZi.months[month%12]) != -1){
				yz = JiaZi.jiazis[month + 2];
			}else if (JiaZi.jiazis[month - 2].indexOf(JiaZi.months[month%12]) != -1){
				yz = JiaZi.jiazis[month - 2];
			}
		}
		*/
		String bazi = JiaZi.jiazis[year] + "年" + yz + "月"
		+ JiaZi.jiazis[day] + "日" + BaZi.get(hour, JiaZi.jiazis[day].charAt(0) + "") +  "时";
		canvas.drawText( bazi + "  星期" + getWeek(cdate.get(Calendar.DAY_OF_WEEK)),
				width/10,width + width*2/10, p);//width/16,width + width*2/10 width/16,width + width * 5/19
		bazi = JiaZi.jiazis[year]  + yz + JiaZi.jiazis[day]  + BaZi.get(hour, JiaZi.jiazis[day].charAt(0) + "") ;
		
		//年表针和字
		p.setColor(Color.YELLOW);
		p.setStyle(Style.STROKE);
		p.setTextSize(width/23);
		p.setStrokeWidth(3);
		canvas.drawLine(width/2, width/2, 
				(float)(width/2 + dwidth * 3 * Math.cos(JiaZi.dzis[year % 12])), 
				(float)(width/2 - dwidth * 3 * Math.sin(JiaZi.dzis[year % 12])), p);
		
		p.setStrokeWidth(1);
		if (year % 12 < 3)
			canvas.drawText("年",(float)(width/2 + dwidth * 3  * Math.cos(JiaZi.dzis[year % 12])), 
				(float)(width/2 - dwidth * 3  * Math.sin(JiaZi.dzis[year % 12]) + dwidth * 2/3), p);
		else
			canvas.drawText("年",(float)(width/2 + dwidth * 3  * Math.cos(JiaZi.dzis[year % 12])), 
					(float)(width/2 - dwidth * 3  * Math.sin(JiaZi.dzis[year % 12])), p);
		
		//月表针和字
		p.setColor(Color.argb(255, 51, 111, 115));
		p.setStrokeWidth(3);
		canvas.drawLine(width/2, width/2, 
				(float)(width/2 + dwidth * 3 * Math.cos(JiaZi.dzis[month % 12])), 
				(float)(width/2 - dwidth * 3 * Math.sin(JiaZi.dzis[month % 12])), p);
		p.setStrokeWidth(1);
		if (month % 12 < 3)
			canvas.drawText("月",(float)(width/2 + dwidth * 3  * Math.cos(JiaZi.dzis[month % 12])), 
				(float)(width/2 - dwidth * 3  * Math.sin(JiaZi.dzis[month % 12]) + dwidth * 2/3), p);
		else
			canvas.drawText("月",(float)(width/2 + dwidth * 3  * Math.cos(JiaZi.dzis[month % 12])), 
					(float)(width/2 - dwidth * 3  * Math.sin(JiaZi.dzis[month % 12])), p);
		//日表针和字
		p.setColor(Color.argb(255, 212, 129, 10));
		p.setStrokeWidth(3);
		canvas.drawLine(width/2, width/2, 
				(float)(width/2 + dwidth * 3 * Math.cos(JiaZi.dzis[(day) % 12])), 
				(float)(width/2 - dwidth * 3 * Math.sin(JiaZi.dzis[(day) % 12])), p);
		p.setStrokeWidth(1);
		if (day % 12 < 3)
			canvas.drawText("日",(float)(width/2 + dwidth * 3   * Math.cos(JiaZi.dzis[(day) % 12])), 
				(float)(width/2 - dwidth * 3  * Math.sin(JiaZi.dzis[(day) % 12]) + dwidth * 2/3), p);
		else
			canvas.drawText("日",(float)(width/2 + dwidth * 3   * Math.cos(JiaZi.dzis[(day) % 12])), 
					(float)(width/2 - dwidth * 3  * Math.sin(JiaZi.dzis[(day) % 12])), p);
		
		
		//时表针和字
		p.setColor(Color.MAGENTA);
		p.setStrokeWidth(3);
		double hdu = JiaZi.dzis[getHour(hour)];
		//double hdu = - Math.PI/2 -  ((hour * 1.0) / 24  * Math.PI * 2 + (minute * 1.0)/60 * Math.PI * 2 / 24);
		canvas.drawLine(width/2, width/2, 
				(float)(width/2 + dwidth * 3 * Math.cos(hdu)), 
				(float)(width/2 - dwidth * 3 * Math.sin(hdu)), p);
		p.setStrokeWidth(1);
		if (getHour(hour) < 3)
			canvas.drawText("时",(float)(width/2 + dwidth * 3 * Math.cos(hdu)), 
				(float)(width/2 - dwidth * 3 * Math.sin(hdu) + dwidth * 2/3), p);
		else
			canvas.drawText("时",(float)(width/2 + dwidth * 3 * Math.cos(hdu)), 
					(float)(width/2 - dwidth * 3 * Math.sin(hdu)), p);
		
		//分表针和字
		p.setColor(Color.argb(255, 133, 75, 150));
		p.setStrokeWidth(3);
		double mdu = JiaZi.dzis[minute % 12];
		//double mdu = Math.PI/2 -  ((minute * 1.0)/60 * Math.PI * 2 + second * 1.0/60 * Math.PI * 2 / 60) ;
		canvas.drawLine(width/2, width/2, 
				(float)(width/2 + dwidth * 3 * Math.cos(mdu)), 
				(float)(width/2 - dwidth * 3 * Math.sin(mdu)), p);
		p.setStrokeWidth(1);
		if (minute % 12 < 3)
			canvas.drawText("分",(float)(width/2 + dwidth * 3 * Math.cos(mdu)), 
				(float)(width/2 - dwidth * 3 * Math.sin(mdu) + dwidth * 2/3), p);
		else 
			canvas.drawText("分",(float)(width/2 + dwidth * 3 * Math.cos(mdu)), 
					(float)(width/2 - dwidth * 3 * Math.sin(mdu) ), p);
		//秒表针和字
		p.setColor(Color.RED);
		p.setStrokeWidth(3);
		double sdu = JiaZi.dzis[second % 12];
		//double sdu = Math.PI/2 -  second * 1.0 / 60 * Math.PI * 2;
		canvas.drawLine(width/2, width/2, 
				(float)(width/2 + dwidth * 3 * Math.cos(sdu)), 
				(float)(width/2 - dwidth * 3 * Math.sin(sdu)), p);
		p.setStrokeWidth(1);
		if (second % 12 < 3) {
			canvas.drawText("秒",(float)(width/2 + dwidth * 3 * Math.cos(sdu)), 
				(float)(width/2 - dwidth * 3  * Math.sin(sdu) + dwidth * 2/3), p);
		} else {
			canvas.drawText("秒",(float)(width/2 + dwidth * 3 * Math.cos(sdu)), 
					(float)(width/2 - dwidth * 3  * Math.sin(sdu)), p);
		}
		//画公司logo
		p.setColor(Color.RED);
		p.setTextSize(width/16);
	
		p.setStrokeWidth(1);
		canvas.drawText("DOOK", width/2 - width/11, width/2 - width/7, p);
		
	}
	public void setToday()
	{
		
		isToday = true;
		
	}
	public void setDate(Calendar c){
		cdate = c;
		isToday = false;
		isRun = false;
		postInvalidate();
	}
	private String getWeek(int w){
		String z = "";
		w--;
		switch(w) {
		case 0: z = "日";break;
		case 1: z = "一";break;
		case 2: z = "二";break;
		case 3: z = "三";break;
		case 4: z = "四";break;
		case 5: z = "五";break;
		case 6: z = "六";break;
		}
		return z;
	}
	
	private int getHour(int hour){
		int shi = 0;
		if (hour >= 23 || hour < 1) {
			shi = 0;
		} else if (hour >= 1 && hour < 3) {
			shi = 1;
		}else if (hour >= 3 && hour < 5) {
			shi = 2;
		}else if (hour >= 5 && hour < 7) {
			shi = 3;
		}else if (hour >= 7 && hour < 9) {
			shi = 4;
		}else if (hour >= 9 && hour < 11) {
			shi = 5;
		}else if (hour >= 11 && hour < 13) {
			shi = 6;
		}else if (hour >= 13 && hour < 15) {
			shi = 7;
		}else if (hour >= 15 && hour < 17) {
			shi = 8;
		}else if (hour >= 17 && hour < 19) {
			shi = 9;
		}else if (hour >= 19 && hour < 21) {
			shi = 10;
		}else if (hour >= 21 && hour < 23) {
			shi = 11;
		}
		return shi;
	}
	
	private void drawHuan(Canvas canvas,Paint p,int dh){
		RectF rf = new RectF();
		rf.left = dh;
		rf.top =  dh;
		rf.bottom =width -  dh;
		rf.right = width -  dh;
		p.setStyle(Style.STROKE);
		p.setStrokeWidth(2);
		canvas.drawOval(rf, p);
	}
	private class Dao implements Comparable<Dao>{
		public int dao;
		public int ymdhms;
		
		public Dao(int dao ,int ymdhms){
			this.dao = dao;
			this.ymdhms = ymdhms;
		}
		@Override
		public int compareTo(Dao another) {
			// TODO Auto-generated method stub]
			if (this.dao > another.dao) {
				return -3;
			} 
			return 4;
		}
		
		
	}
	private void drawYMDHMS(Canvas canvas,Paint p,int year,int month,int day,int hour,int minute,int second){
		int yd = year / 12;
		int md = month / 12;
		int dd = day / 12;
		int hd = BaZi.getTian(JiaZi.jiazis[day].charAt(0) + "");
		int mid = minute / 12;
		int sd = second / 12;
		
		Dao[] daocoms = new Dao[6];
		
		daocoms[0] = new Dao(yd,1);
		daocoms[1] = new Dao(md,2);
		daocoms[2] = new Dao(dd,3);
		daocoms[3] = new Dao(hd,4);
		daocoms[4] = new Dao(mid,5);
		daocoms[5] = new Dao(sd,6);
		Arrays.sort(daocoms);
		for (int i = 0; i < 6 ;i++) {
			switch (daocoms[i].ymdhms) {
			case 1:{
				p.setColor(Color.YELLOW);
				drawHu(canvas,p,daos[year / 12],dus[year%12]);
				
			}break;
			case 2:{
				p.setColor(Color.argb(255, 51, 111, 115));
				drawHu(canvas,p,daos[month / 12],dus[month%12]);
				
			}break;
			case 3:{
				p.setColor(Color.argb(255, 212, 129, 10)); 
				drawHu(canvas,p,daos[day / 12],dus[day%12]);
				
			}break;
			case 4:{
				p.setColor(Color.MAGENTA);
				drawHu(canvas,p,daos[BaZi.getTian(JiaZi.jiazis[day].charAt(0) + "")],dus[getHour(hour)]);
				
			}break;
			case 5:{
				p.setColor(Color.argb(255, 133, 75, 150));
				drawHu(canvas,p,daos[minute / 12],dus[minute%12]);
				
			}break;
			case 6:{
				//画秒的跳动红色圆弧
				p.setColor(Color.RED);
				drawHu(canvas,p,daos[second / 12],dus[second%12]);
			
			}break;
			}
			
			
			
			
			
			}
		
	}
	
	private void drawHu(Canvas canvas,Paint p,int num,float degree){
		//p.setColor(Color.CYAN);
		RectF rf  = new RectF();
		rf.left = dwidth * num;
		rf.top = dwidth * num ;
		rf.bottom =width - dwidth * num ;
		rf.right = width - dwidth * num;
		canvas.drawArc(rf, -degree, -30, true, p); 
		p.setColor(Color.GREEN);
		rf.left = dwidth * (num + 1) ;
		rf.top = dwidth * (num + 1);
		rf.bottom =width - (num + 1) * dwidth;
		rf.right = width - (num + 1) * dwidth;
		canvas.drawArc(rf, -degree, -30, true, p); 
	}

	/*public boolean onTouchEvent (MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		if (Math.sqrt((x - width/2) * (x - width/2) + (y - width/2) * (y - width/2)) >= width/4 &&
				Math.sqrt((x - width/2) * (x - width/2) + (y - width/2) * (y - width/2)) <= width/2	) {
			
		
			float dy = (width/2 - y) ;
			float dx = x - width/2;
			double dr = Math.sqrt(dy * dy  + dx * dx);
			double hd  = Math.asin(dy/dr);
			double de = Math.toDegrees(hd);
	
			if (dx < 0 ) {
				de = 180 - de;	
			}
			if (de < 0 ) {
				de = 360 + de;
			}
	
			int d = (int)((de + 15)/30) * 30;
			degree = d;
		    degree = degree + 15 - 30;
			float zy = Math.abs(dy);
			float[] daos = get(dx,(float)de);
			for (int i = 0; i < daos.length - 1; i++) {
				
				if (zy<= daos[i] && zy > daos[i + 1]){
					dao = i;
					break;
				}
			}
			invalidate();
		}
		return super.onTouchEvent(event);
	}
	*/
	private float[] get(float x,float du){
		float[] ys = new float[6];
		for (int i = 0; i < 6; i++) {
			float dr = width/2 - dwidth * i; 
			ys[i] = Math.abs((float)(dr * Math.sin(Math.toRadians(du))));
		}
		return ys;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (isRun) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.postInvalidate();
		}
	}


}
