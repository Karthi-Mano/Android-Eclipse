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
		values.put("name", "����"+rm.nextInt(100));
		values.put("phone", "123"+rm.nextInt(100));
		long id = db.insert("info", null, values );
		db.close();
		if(id!=-1){
			Toast.makeText(this, "��ӳɹ����ڵ�"+id+"��", Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(this, "���ʧ��", Toast.LENGTH_LONG).show();
		}
	}
	public void delete(View view) {
		MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this);
		SQLiteDatabase db = helper.getWritableDatabase();
		int result = db.delete("info", null, null);
		db.close();
		if (result==0) {
			Toast.makeText(this, "ɾ��ʧ��", Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(this, "ɾ����"+result+"��", Toast.LENGTH_LONG).show();
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
			Toast.makeText(this, "�޸���0����¼", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "�޸���"+result+"����¼", Toast.LENGTH_LONG).show();
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
