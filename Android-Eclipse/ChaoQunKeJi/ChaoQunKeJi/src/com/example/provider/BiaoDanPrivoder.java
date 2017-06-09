package com.example.provider;


import com.example.dbhelper.PanDianOpenHelper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class BiaoDanPrivoder extends ContentProvider {

	
	// 1.������ַ�ĳ���-->��ǰ�������·��-->�õ�һ���������·��
		public static final String AUTHORITIES = BiaoDanPrivoder.class
				.getCanonicalName();

		public static final int BIAODAN = 1;
		// ��Ӧ�̵���һ��uri����
		public static Uri URI_BIAODAN = Uri.parse("content://"+AUTHORITIES+"/biaodan");
		private PanDianOpenHelper mHelper;

		// 2.��ַƥ�����
		static UriMatcher mUriMatcher;

		// 3.ƥ�����
		static {
			mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

			// 4.���һ��ƥ�����
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
			System.out.println("��ѯ�ɹ�");
			
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
				System.out.println("ƥ��ɹ�");
				// ��ÿ�д���ݿ�
				SQLiteDatabase db = mHelper.getWritableDatabase();
				long id = db.insert(PanDianOpenHelper.M_BIAODAN, "", values);
				if (id != -1) {// ����ɹ�
					// ƴ�����µ�uri
					uri = ContentUris.withAppendedId(uri, id);
					System.out.println("����ɹ�");
					// ֪ͨContentObserver���ݸı�
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
				System.out.println("ɾ���ɹ�");
				// ֪ͨContentObserver���ݸı�
				
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
			// ���µļ�¼����
			updateCount = db.update(PanDianOpenHelper.M_BIAODAN, values,
					selection, selectionArgs);
			if (updateCount > 0) {
				System.out.println("���³ɹ�");
				// ֪ͨContentObserver���ݸı�
				getContext().getContentResolver().notifyChange(
						PanDianPrivoder.URI_PANDIAN, null);
			}
			//db.close();
		}
		return updateCount;
		
	}

}
