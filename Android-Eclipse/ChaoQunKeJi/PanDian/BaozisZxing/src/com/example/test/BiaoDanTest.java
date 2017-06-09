package com.example.test;

import com.example.dbhelper.PanDianOpenHelper;
import com.example.providers.BiaoDanPrivoder;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;

public class BiaoDanTest extends AndroidTestCase {

	
	/*
	public static final String ALLNUM = "allnum";// 27
	public static final String CNAME = "cname";// ceshi
	public static final String ICHECKVOUCH = "icheckvouch";// 47
	public static final String ISUPLOAD = "isupload";// 0
	public static final String WNUM = "wnum";// 27
	public static final String YNUM = "ynum";// 0
*/	
	
	
	public void testInsert() {
		
		ContentValues values = new ContentValues();
		values.put(PanDianOpenHelper.BiaoDanTable.ALLNUM, "2");
		values.put(PanDianOpenHelper.BiaoDanTable.CNAME, "ceshi2");
		values.put(PanDianOpenHelper.BiaoDanTable.ICHECKVOUCH, "2");
		values.put(PanDianOpenHelper.BiaoDanTable.ISUPLOAD, "0");
		values.put(PanDianOpenHelper.BiaoDanTable.WNUM, "2");
		values.put(PanDianOpenHelper.BiaoDanTable.YNUM, "2");
		getContext().getContentResolver().insert(BiaoDanPrivoder.URI_BIAODAN, values);

	}

	public void testDelete() {
		
		getContext().getContentResolver().delete(BiaoDanPrivoder.URI_BIAODAN,
				PanDianOpenHelper.BiaoDanTable.ICHECKVOUCH + "=?", null);
		
	}

	public void testUpdate() {
		
		
		ContentValues values = new ContentValues();
		values.put(PanDianOpenHelper.BiaoDanTable.ALLNUM, "2888");
		values.put(PanDianOpenHelper.BiaoDanTable.CNAME, "ceshi");
		values.put(PanDianOpenHelper.BiaoDanTable.ICHECKVOUCH, "47");
		values.put(PanDianOpenHelper.BiaoDanTable.ISUPLOAD, "0");
		values.put(PanDianOpenHelper.BiaoDanTable.WNUM, "8888");
		values.put(PanDianOpenHelper.BiaoDanTable.YNUM, "8888");
		getContext().getContentResolver().update(BiaoDanPrivoder.URI_BIAODAN, values,
				PanDianOpenHelper.BiaoDanTable.ICHECKVOUCH + "=?", new String[] { "47" });
		
		
	}

	public void testQuery() {
		
		Cursor c = getContext().getContentResolver().query(BiaoDanPrivoder.URI_BIAODAN, null, null, null, null);
		int columnCount = c.getColumnCount();// 一共多少列
		while (c.moveToNext()) {
			// 循环打印列
			for (int i = 0; i < columnCount; i++) {
				System.out.print(c.getString(i) + "    ");
			}
			System.out.println("");
		}
		
	}
}
