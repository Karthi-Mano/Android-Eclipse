package com.example.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.dbhelper.PanDianOpenHelper;

public class PanDianPrivoder extends ContentProvider {

	// 1.主机地址的常量-->当前类的完整路径-->得到一个类的完整路径
	public static final String AUTHORITIES = PanDianPrivoder.class
			.getCanonicalName();

	public static final int PANDIAN = 1;
	public static final int YPANDIAN = 2;
	// 对应盘点表的一个uri常量
	public static Uri URI_PANDIAN = Uri.parse("content://" + AUTHORITIES
			+ "/pandian");// 未盘点表
	public static Uri URI_YPANDIAN = Uri.parse("content://" + AUTHORITIES
			+ "/ypandian");// 已盘点表
	private PanDianOpenHelper mHelper;

	// 2.地址匹配对象
	static UriMatcher mUriMatcher;

	// 3.匹配规则
	static {
		mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

		// 4.添加一个匹配规则
		mUriMatcher.addURI(AUTHORITIES, "pandian", PANDIAN);
		mUriMatcher.addURI(AUTHORITIES, "ypandian", YPANDIAN);

	}

	public static UriMatcher getUriMatcher() {
		return mUriMatcher;
	}

	@Override
	public boolean onCreate() {
		mHelper = new PanDianOpenHelper(getContext());
		if (mHelper != null) {
			return true;
		}
		return false;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {

		// 数据存储到sqlite -->创建db文件，建表-->sqliteOpenHelper
		int code = mUriMatcher.match(uri);// PANDIAN
		System.out.println(code);

		switch (code) {

		case PANDIAN:// 未盘点表
			System.out.println("未盘点表匹配成功");
			// 获得可写数据库
			SQLiteDatabase db = mHelper.getWritableDatabase();
			long id = db.insert(PanDianOpenHelper.M_PANDIAN, "", values);
			if (id != -1) {// 插入成功
				// 拼接最新的uri
				uri = ContentUris.withAppendedId(uri, id);
				System.out.println("插入成功");
				// 通知ContentObserver数据改变
				getContext().getContentResolver().notifyChange(
						PanDianPrivoder.URI_PANDIAN, null);
			}
			// db.close();
			break;
		case YPANDIAN:// 已盘点表

			System.out.println("已盘点表匹配成功");
			// 获得可写数据库
			SQLiteDatabase db2 = mHelper.getWritableDatabase();
			long id2 = db2.insert(PanDianOpenHelper.Y_PANDIAN, "", values);
			if (id2 != -1) {// 插入成功
				// 拼接最新的uri
				uri = ContentUris.withAppendedId(uri, id2);
				System.out.println("插入成功");
				// 通知ContentObserver数据改变
				getContext().getContentResolver().notifyChange(
						PanDianPrivoder.URI_YPANDIAN, null);
			}
			// db.close();

			break;

		default:
			break;
		}
		return uri;

	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {

		int code = mUriMatcher.match(uri);// PANDIAN
		int deleteCount = -1;
		switch (code) {
		case PANDIAN:
			SQLiteDatabase db = mHelper.getWritableDatabase();
			deleteCount = db.delete(PanDianOpenHelper.M_PANDIAN, selection,
					selectionArgs);
			if (deleteCount > 0) {
				System.out.println("删除成功");
				// 通知ContentObserver数据改变

				getContext().getContentResolver().notifyChange(
						PanDianPrivoder.URI_PANDIAN, null);
			}
			// db.close();
			break;

		case YPANDIAN:// 已未盘点表

			System.out.println("已盘点表匹配成功");
			SQLiteDatabase db2 = mHelper.getWritableDatabase();
			deleteCount = db2.delete(PanDianOpenHelper.Y_PANDIAN, selection,
					selectionArgs);
			if (deleteCount > 0) {
				System.out.println("删除成功");
				// 通知ContentObserver数据改变

				getContext().getContentResolver().notifyChange(
						PanDianPrivoder.URI_YPANDIAN, null);
			}
			// db.close();

			break;

		default:
			break;
		}

		return deleteCount;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		int code = mUriMatcher.match(uri);// PANDIAN
		int updateCount = -1;
		switch (code) {
		case PANDIAN:
			SQLiteDatabase db = mHelper.getWritableDatabase();
			// 更新的记录总数
			updateCount = db.update(PanDianOpenHelper.M_PANDIAN, values,
					selection, selectionArgs);
			if (updateCount > 0) {
				System.out.println("更新成功");
				// 通知ContentObserver数据改变
				getContext().getContentResolver().notifyChange(
						PanDianPrivoder.URI_PANDIAN, null);
			}
			// db.close();
			break;

		case YPANDIAN:// 已未盘点表

			System.out.println("已盘点表匹配成功");
			SQLiteDatabase db2 = mHelper.getWritableDatabase();
			// 更新的记录总数
			updateCount = db2.update(PanDianOpenHelper.Y_PANDIAN, values,
					selection, selectionArgs);
			if (updateCount > 0) {
				System.out.println("更新成功");
				// 通知ContentObserver数据改变
				getContext().getContentResolver().notifyChange(
						PanDianPrivoder.URI_YPANDIAN, null);
			}
			// db.close();

			break;
		default:
			break;
		}

		return updateCount;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		int code = mUriMatcher.match(uri);// PANDIAN
		Cursor cursor = null;
		switch (code) {
		case PANDIAN:
			SQLiteDatabase db = mHelper.getReadableDatabase();
			cursor = db.query(PanDianOpenHelper.M_PANDIAN, projection,
					selection, selectionArgs, null, null, sortOrder);
			System.out.println("查询成功");

			break;

		case YPANDIAN:// 已未盘点表

			SQLiteDatabase db2 = mHelper.getReadableDatabase();
			cursor = db2.query(PanDianOpenHelper.Y_PANDIAN, projection,
					selection, selectionArgs, null, null, sortOrder);
			System.out.println("查询成功");

			break;

		default:
			break;

		}

		return cursor;
	}

}
