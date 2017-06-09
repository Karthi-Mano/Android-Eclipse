package com.gs.qzg.greensport;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PalmDB extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "Palm.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "Palm_table_1";
	
	//表里面的三个内容
	private static final String ID = "_id";
	private static final String NAME = "_username";
	private static final String RealID = "_realid";
	private static final String PASSWD = "_passwd";
	
	private static final String Tag = "PalmDB";



	public PalmDB(Context context) //, String name, CursorFactory factory,	int version
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER primary key autoincrement, "
				+ NAME  +" text,"+PASSWD+" text," + RealID + " INTEGER);"; //
		db.execSQL(sql);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}
	
	
	public Cursor select(){		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, RealID +" ASC");
		return cursor;
		
	}
	
	
	/*增加操作*/
	public long insert(String name,String passwd){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		int realid = getRealId();
		cv.put(NAME, name);
		cv.put(RealID, realid);
		cv.put(PASSWD, passwd);
		long row = db.insert(TABLE_NAME, null, cv);
		return realid;
		//return row;  //row是行的意思
		
	}
	
	/*得到一个真实的id*/
	public int getRealId(){
		Cursor cursor = select(); 
		int realid = 1;
		
		//如果cursor为空，返回id = 1
		if(!cursor.moveToFirst()){
			return realid;
		}
		else{
			while(true){
				if(realid != cursor.getInt(2))
				{
					return realid;
				}
				else
				{
					realid++;
					if(!cursor.moveToNext())
						return realid;
				}
			}
		}
	}
	
	/*删除操作*/
	public void delete(int id){
		SQLiteDatabase db = this.getWritableDatabase();
        String where = ID + "=?";
        String[] whereValue = { Integer.toString(id) };
        db.delete(TABLE_NAME, where, whereValue);
        
	}
	
	/*修改操作*/
	//id是你要修改的id号，name是新的名字
	public void update(int id, String name,String passwd){
		SQLiteDatabase db = this.getWritableDatabase();
        String where = ID + "=?";
        String[] whereValue = { Integer.toString(id) };

        ContentValues cv = new ContentValues();
        cv.put(NAME, name);
        cv.put(PASSWD, passwd);
        
        db.update(TABLE_NAME, cv, where, whereValue);
	}
	

}
