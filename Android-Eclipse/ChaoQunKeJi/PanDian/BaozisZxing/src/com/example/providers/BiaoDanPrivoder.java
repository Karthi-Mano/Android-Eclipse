package com.example.providers;


import com.example.dbhelper.PanDianOpenHelper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class BiaoDanPrivoder extends ContentProvider {

	
	// 1.主机地址的常量-->当前类的完整路径-->得到一个类的完整路径
		public static final String AUTHORITIES = BiaoDanPrivoder.class
				.getCanonicalName();

		public static final int BIAODAN = 1;
		// 对应盘点表的一个uri常量
		public static Uri URI_BIAODAN = Uri.parse("content://"+AUTHORITIES+"/biaodan");
		private PanDianOpenHelper mHelper;

		// 2.地址匹配对象
		static UriMatcher mUriMatcher;

		// 3.匹配规则
		static {
			mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

			// 4.添加一个匹配规则
			mUriMatcher.addURI(AUTHORITIES,"biaodan", BIAODAN);

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
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		
		int code = mUriMatcher.match(uri);// PANDIAN
		Cursor cursor = null;
		switch (code) {
		case BIAODAN:
			SQLiteDatabase db = mHelper.getReadableDatabase();
			cursor = db.query(PanDianOpenHelper.M_BIAODAN, projection,
					selection, selectionArgs, null, null, sortOrder);
			System.out.println("查询成功");
			
		}

		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {


		int code = mUriMatcher.match(uri);// PANDIAN
		System.out.println("biaodan"+code);
		
		switch (code) {
			case BIAODAN:
				System.out.println("匹配成功");
				// 获得可写数据库
				SQLiteDatabase db = mHelper.getWritableDatabase();
				long id = db.insert(PanDianOpenHelper.M_BIAODAN, "", values);
				if (id != -1) {// 插入成功
					// 拼接最新的uri
					uri = ContentUris.withAppendedId(uri, id);
					System.out.println("插入成功");
					// 通知ContentObserver数据改变
					getContext().getContentResolver().notifyChange(
							PanDianPrivoder.URI_PANDIAN, null);
				}
				//db.close();
				break;
			default:
				break;
			}
			return uri;
		
		
	
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {

		int code = mUriMatcher.match(uri);// PANDIAN
		int deleteCount =  -1;
		switch (code) {
		case BIAODAN:
			SQLiteDatabase db = mHelper.getWritableDatabase();
			deleteCount = db.delete(PanDianOpenHelper.M_BIAODAN, selection,
					selectionArgs);
			if (deleteCount > 0) {
				System.out.println("删除成功");
				// 通知ContentObserver数据改变
				
				getContext().getContentResolver().notifyChange(
						PanDianPrivoder.URI_PANDIAN, null);
			}
			//db.close();
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
		switch (code ) {
		case BIAODAN:
			SQLiteDatabase db = mHelper.getWritableDatabase();
			// 更新的记录总数
			updateCount = db.update(PanDianOpenHelper.M_BIAODAN, values,
					selection, selectionArgs);
			if (updateCount > 0) {
				System.out.println("更新成功");
				// 通知ContentObserver数据改变
				getContext().getContentResolver().notifyChange(
						PanDianPrivoder.URI_PANDIAN, null);
			}
			//db.close();
		}
		return updateCount;
		
	}

}
