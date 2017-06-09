package com.example.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.dbhelper.PanDianOpenHelper;

public class PanDianPrivoder extends ContentProvider {

	// 1.������ַ�ĳ���-->��ǰ�������·��-->�õ�һ���������·��
	public static final String AUTHORITIES = PanDianPrivoder.class
			.getCanonicalName();

	public static final int PANDIAN = 1;
	public static final int YPANDIAN = 2;
	// ��Ӧ�̵���һ��uri����
	public static Uri URI_PANDIAN = Uri.parse("content://" + AUTHORITIES
			+ "/pandian");// δ�̵��
	public static Uri URI_YPANDIAN = Uri.parse("content://" + AUTHORITIES
			+ "/ypandian");// ���̵��
	private PanDianOpenHelper mHelper;

	// 2.��ַƥ�����
	static UriMatcher mUriMatcher;

	// 3.ƥ�����
	static {
		mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

		// 4.���һ��ƥ�����
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

		// ���ݴ洢��sqlite -->����db�ļ�������-->sqliteOpenHelper
		int code = mUriMatcher.match(uri);// PANDIAN
		System.out.println(code);

		switch (code) {

		case PANDIAN:// δ�̵��
			System.out.println("δ�̵��ƥ��ɹ�");
			// ��ÿ�д���ݿ�
			SQLiteDatabase db = mHelper.getWritableDatabase();
			long id = db.insert(PanDianOpenHelper.M_PANDIAN, "", values);
			if (id != -1) {// ����ɹ�
				// ƴ�����µ�uri
				uri = ContentUris.withAppendedId(uri, id);
				System.out.println("����ɹ�");
				// ֪ͨContentObserver���ݸı�
				getContext().getContentResolver().notifyChange(
						PanDianPrivoder.URI_PANDIAN, null);
			}
			// db.close();
			break;
		case YPANDIAN:// ���̵��

			System.out.println("���̵��ƥ��ɹ�");
			// ��ÿ�д���ݿ�
			SQLiteDatabase db2 = mHelper.getWritableDatabase();
			long id2 = db2.insert(PanDianOpenHelper.Y_PANDIAN, "", values);
			if (id2 != -1) {// ����ɹ�
				// ƴ�����µ�uri
				uri = ContentUris.withAppendedId(uri, id2);
				System.out.println("����ɹ�");
				// ֪ͨContentObserver���ݸı�
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
				System.out.println("ɾ���ɹ�");
				// ֪ͨContentObserver���ݸı�

				getContext().getContentResolver().notifyChange(
						PanDianPrivoder.URI_PANDIAN, null);
			}
			// db.close();
			break;

		case YPANDIAN:// ��δ�̵��

			System.out.println("���̵��ƥ��ɹ�");
			SQLiteDatabase db2 = mHelper.getWritableDatabase();
			deleteCount = db2.delete(PanDianOpenHelper.Y_PANDIAN, selection,
					selectionArgs);
			if (deleteCount > 0) {
				System.out.println("ɾ���ɹ�");
				// ֪ͨContentObserver���ݸı�

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
			// ���µļ�¼����
			updateCount = db.update(PanDianOpenHelper.M_PANDIAN, values,
					selection, selectionArgs);
			if (updateCount > 0) {
				System.out.println("���³ɹ�");
				// ֪ͨContentObserver���ݸı�
				getContext().getContentResolver().notifyChange(
						PanDianPrivoder.URI_PANDIAN, null);
			}
			// db.close();
			break;

		case YPANDIAN:// ��δ�̵��

			System.out.println("���̵��ƥ��ɹ�");
			SQLiteDatabase db2 = mHelper.getWritableDatabase();
			// ���µļ�¼����
			updateCount = db2.update(PanDianOpenHelper.Y_PANDIAN, values,
					selection, selectionArgs);
			if (updateCount > 0) {
				System.out.println("���³ɹ�");
				// ֪ͨContentObserver���ݸı�
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
			System.out.println("��ѯ�ɹ�");

			break;

		case YPANDIAN:// ��δ�̵��

			SQLiteDatabase db2 = mHelper.getReadableDatabase();
			cursor = db2.query(PanDianOpenHelper.Y_PANDIAN, projection,
					selection, selectionArgs, null, null, sortOrder);
			System.out.println("��ѯ�ɹ�");

			break;

		default:
			break;

		}

		return cursor;
	}

}
