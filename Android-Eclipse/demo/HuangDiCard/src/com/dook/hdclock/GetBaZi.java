package com.dook.hdclock;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class GetBaZi {
	
	//获得八字
	public static String get(Calendar cdate){
		String bazi = "";
		NongLi nl = new NongLi();
		nl.setCalendar(cdate);
		int ryear = nl.getYear();
		int rmonth = nl.getMonth();
		
		
		int rday =  cdate.get(Calendar.DAY_OF_YEAR);
		
		int	hour = cdate.get(Calendar.HOUR_OF_DAY);
		
		// 计算年月日的天干地支1900 1 1  公立1899 己亥 35 12 丙子12  1甲戌  10 
		int year = (ryear - 1899 + 35) % 60 ;
	
		int month = ((ryear - 1899) * 12 + rmonth + 1) % 60;
		int day =( (int)(Math.ceil((cdate.get(Calendar.YEAR) - 1900)  / 4.0)) + (cdate.get(Calendar.YEAR) - 1900) * 365 + rday + 8  ) % 60;
		String yz = JiaZi.jiazis[month];
		bazi = JiaZi.jiazis[year]  + yz + JiaZi.jiazis[day]  + BaZi.get(hour, JiaZi.jiazis[day].charAt(0) + "") ;
		
		
		
		
		return bazi;
	}
}
