package com.dook.hdclock;

public class JiaZi {
	public final static String[] jiazis = new String[60];
	public final static String[] jias = new String[]{
			"��","��","��","��","��","��","��","��","��","��"
	};
	public final static String[] zis = new String[] {
			"��","��","��","î","��","��","��","δ","��","��","��","��"
	};
	public final static String[] months = new String[] {
		"","��","î","��","��","��","δ","��","��","��","��","��","��"
	};
	public final static double[] dzis = new double[] {
		Math.PI * 1/6 * 9,Math.PI * 1/6 * 8,Math.PI * 1/6 * 7,Math.PI * 1/6 * 6
		,Math.PI * 1/6 * 5,Math.PI * 1/6 * 4,Math.PI * 1/6 * 3,Math.PI * 1/6 * 2
		,Math.PI * 1/6 * 1,Math.PI * 1/6 * 0,Math.PI * 1/6 * -1,Math.PI * 1/6 * -2
	};
	
	static {
		for (int i = 0; i < 60 ; i++) {		
			jiazis[i] = jias[i%10] + zis[i%12];	
		}
	}
	
}
