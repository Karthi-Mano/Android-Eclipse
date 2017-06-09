package com.treaker.dbcrud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
	private static final int CODE = 1;

	public MySQLiteOpenHelper(Context context) {
		super(context, "treaker.db", null, CODE);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table info (_id integer primary key autoincrement,name text,phone text)";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
