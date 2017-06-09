package com.example.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class PanDianOpenHelper extends SQLiteOpenHelper {

	public static final String M_PANDIAN = "m_pandian";
	public static final String Y_PANDIAN = "y_pandian";
	public static final String M_BIAODAN = "m_biaodan";
	public PanDianOpenHelper(Context context) {
		super(context, "pandian.db", null, 1);
		
	}
	
	public class PanDianTable implements BaseColumns{
		public static final String CCLCODE="cclcode";
		public static final String CCLTYPE="ccltype";
		public static final String CCUSTOMER="ccustomer";
		public static final String CPOSITION="cposition";
		public static final String CRFID="crfid";
		public static final String DEPARTMENT="department";
		public static final String IID="iid";
		public static final String ISTATE="istate";
		public static final String IWARRANTS="iwarrants";
		public static final String ICHECKVOUCH="icheckvouch";
		
		public static final String CCODE="ccode";
		public static final String CCOLLATERAL="ccollateral";
	}
	public class BiaoDanTable implements BaseColumns {

		public static final String ALLNUM = "allnum";// 27
		public static final String CNAME = "cname";// ceshi
		public static final String ICHECKVOUCH = "icheckvouch";// 47
		public static final String ISUPLOAD = "isupload";// 0
		public static final String WNUM = "wnum";// 27
		public static final String YNUM = "ynum";// 0
		public static final String KNUM = "knum";// 0
		public static final String CCODE = "ccode";// 0
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		

		String sql = 
				"CREATE TABLE "+ M_PANDIAN +"(_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
				PanDianTable.CCLCODE      +" TEXT," +
				PanDianTable.CCLTYPE      +" TEXT," +
				PanDianTable.CCUSTOMER    +" TEXT," +
				PanDianTable.CPOSITION    +" TEXT," +
				PanDianTable.CRFID        +" TEXT," +
				
				PanDianTable.DEPARTMENT   +" TEXT," +
				PanDianTable.IID          +" TEXT," +
				PanDianTable.ISTATE       +" TEXT," +
				PanDianTable.IWARRANTS    +" TEXT," +
				PanDianTable.ICHECKVOUCH  +" TEXT," +
				PanDianTable.CCODE        +" TEXT," +
				PanDianTable.CCOLLATERAL  +" TEXT)";
		
		
		db.execSQL(sql);
		
		
		String sql2 = 
				"CREATE TABLE "+ M_BIAODAN +"(_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
						BiaoDanTable.ALLNUM       +" TEXT," +
						BiaoDanTable.CNAME        +" TEXT," +
						BiaoDanTable.ICHECKVOUCH  +" TEXT," +
						BiaoDanTable.ISUPLOAD     +" TEXT," +
						BiaoDanTable.WNUM         +" TEXT," +
						BiaoDanTable.YNUM         +" TEXT,"+
						BiaoDanTable.KNUM         +" TEXT,"+
						BiaoDanTable.CCODE        +" TEXT)";
		
		db.execSQL(sql2);
		
		
		String sql3 = 
				"CREATE TABLE "+ Y_PANDIAN +"(_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
				PanDianTable.CCLCODE      +" TEXT," +
				PanDianTable.CCLTYPE      +" TEXT," +
				PanDianTable.CCUSTOMER    +" TEXT," +
				PanDianTable.CPOSITION    +" TEXT," +
				PanDianTable.CRFID        +" TEXT," +
				
				PanDianTable.DEPARTMENT   +" TEXT," +
				PanDianTable.IID          +" TEXT," +
				PanDianTable.ISTATE       +" TEXT," +
				PanDianTable.IWARRANTS    +" TEXT," +
				PanDianTable.ICHECKVOUCH  +" TEXT," +
				PanDianTable.CCODE        +" TEXT," +
				PanDianTable.CCOLLATERAL  +" TEXT)";
		
		
		db.execSQL(sql3);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
