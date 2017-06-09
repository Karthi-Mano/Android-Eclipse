package com.example.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
		public static final String CCODE="ccode";
		public static final String CNAME="cname";
		public static final String DBEGIN="dbegin";
		public static final String ICLASS_NAME="iclass_name";
		public static final String IDEPARTMENT_NAME="idepartment_name";
		public static final String IID="iid";
		public static final String IUSERTYPE_NAME="iusertype_name";
		public static final String ICHECKVOUCH="icheckvouch";
		public static final String ISTATE_NAME="istate_name";

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
				PanDianTable.CCODE               +" TEXT," +
				PanDianTable.CNAME               +" TEXT," +
				PanDianTable.DBEGIN              +" TEXT," +
				PanDianTable.ICLASS_NAME         +" TEXT," +
				PanDianTable.IDEPARTMENT_NAME    +" TEXT," +
				PanDianTable.IID                 +" TEXT," +
				PanDianTable.ICHECKVOUCH         +" TEXT," +
				PanDianTable.ISTATE_NAME         +" TEXT," +
				PanDianTable.IUSERTYPE_NAME      +" TEXT)";
		
		
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
				PanDianTable.CCODE               +" TEXT," +
				PanDianTable.CNAME               +" TEXT," +
				PanDianTable.DBEGIN              +" TEXT," +
				PanDianTable.ICLASS_NAME         +" TEXT," +
				PanDianTable.IDEPARTMENT_NAME    +" TEXT," +
				PanDianTable.IID                 +" TEXT," +
				PanDianTable.ICHECKVOUCH         +" TEXT," +
				PanDianTable.ISTATE_NAME         +" TEXT," +
				PanDianTable.IUSERTYPE_NAME      +" TEXT)";
		
		
		db.execSQL(sql3);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
}
