package com.dook.hdclock;

public class BaZi {
	//60¼××Ó
	public static final String[][] bzs = new String[][]{
		{"¼××Ó","±û×Ó","Îì×Ó","¸ý×Ó","ÈÉ×Ó"},
		{"ÒÒ³ó","¶¡³ó","¼º³ó","ÐÁ³ó","¹ï³ó"},
		{"±ûÒú","ÎìÒú","¸ýÒú","ÈÉÒú","¼×Òú"},
		{"¶¡Ã®","¼ºÃ®","ÐÁÃ®","¹ïÃ®","ÒÒÃ®"},
		{"Îì³½","¸ý³½","ÈÉ³½","¼×³½","±û³½"},
		{"¼ºËÈ","ÐÁËÈ","¹ïËÈ","ÒÒËÈ","¶¡ËÈ"},
		{"¸ýÎç","ÈÉÎç","¼×Îç","±ûÎç","ÎìÎç"},
		{"ÐÁÎ´","¹ïÎ´","ÒÒÎ´","¶¡Î´","¼ºÎ´"},
		{"ÈÉÉê","¼×Éê","±ûÉê","ÎìÉê","¸ýÉê"},
		{"¹ïÓÏ","ÒÒÓÏ","¶¡ÓÏ","¼ºÓÏ","ÐÁÓÏ"},
		{"¼×Ðç","±ûÐç","ÎìÐç","¸ýÐç","ÈÉÐç"},
		{"ÒÒº¥","¶¡º¥","¼ºº¥","ÐÁº¥","¹ïº¥"},
	};
	
	//
	public static String get(int hour,String tian){
		int r = getHour(hour);
		int l = getTian(tian);
		String bz = bzs[r][l];
		return bz;
	}
	
	//Ìì¸É
	public static int getTian(String tian){
		if (tian.equals("¼×") || tian.equals("¼º")) {
			return 0;
		} else if (tian.equals("ÒÒ") || tian.equals("¸ý")) {
			return 1;
		} else if (tian.equals("±û") || tian.equals("ÐÁ")) {
			return 2;
		} else if (tian.equals("¶¡") || tian.equals("ÈÉ")) {
			return 3;
		} else {
			return 4;
		}
	}
	
	// »ñµÃÊ±
	private static int getHour(int hour){
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
}
