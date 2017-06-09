package com.treaker.dbcrud;

import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	public void insert(View view) {
		MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this);
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		Random rm = new Random();
		values.put("name", "大银"+rm.nextInt(100));
		values.put("phone", "123"+rm.nextInt(100));
		long id = db.insert("info", null, values );
		db.close();
		if(id!=-1){
			Toast.makeText(this, "添加成功，在第"+id+"行", Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(this, "添加失败", Toast.LENGTH_LONG).show();
		}
	}
	public void delete(View view) {
		MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this);
		SQLiteDatabase db = helper.getWritableDatabase();
		int result = db.delete("info", null, null);
		db.close();
		if (result==0) {
			Toast.makeText(this, "删除失败", Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(this, "删除了"+result+"行", Toast.LENGTH_LONG).show();
		}
	}
	public void update(View view) {
		MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this);
		SQLiteDatabase db = helper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("phone", "9999");
		int result = db.update("info", values , null, null);
		db.close();
		if (result==0) {
			Toast.makeText(this, "修改了0条记录", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "修改了"+result+"条记录", Toast.LENGTH_LONG).show();
		}
	}
	public void query(View view) {
		MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this);
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query("info", new String[]{"_id","name","phone"}, null, null, null, null, null);
		while(cursor.moveToNext()){
			String id = cursor.getString(0);
			String name = cursor.getString(1);
			String phone = cursor.getString(2);
			System.out.println("---id:"+id+"---name:"+name+"---phone:"+phone);
			System.out.println("--------------------------------------");
		}
		cursor.close();
		db.close();
	}

}
