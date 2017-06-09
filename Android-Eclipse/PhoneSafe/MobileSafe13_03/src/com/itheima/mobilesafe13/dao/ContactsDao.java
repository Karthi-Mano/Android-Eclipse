package com.itheima.mobilesafe13.dao;

import java.util.ArrayList;
import java.util.List;

import com.itheima.mobilesafe13.domain.ContactBean;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * @author Administrator
 * @comp 黑马程序员
 * @date 2015-9-14
 * @desc 获取所有联系人的dao

 * @version $Rev: 24 $
 * @author $Author: goudan $
 * @Date  $Date: 2015-09-14 09:32:23 +0800 (Mon, 14 Sep 2015) $
 * @Id    $Id: ContactsDao.java 24 2015-09-14 01:32:23Z goudan $
 * @Url   $URL: https://188.188.4.100/svn/mobilesafeserver/trunk/MobileSafe13/src/com/itheima/mobilesafe13/dao/ContactsDao.java $
 * 
 */
public class ContactsDao {
	
	public static List<ContactBean> getContacts(Context context){
		List<ContactBean> datas = new ArrayList<ContactBean>();
		//uri :   content://contacts/raw_contacts
		Uri uri = Uri.parse("content://com.android.contacts/contacts");
		Uri uriData = Uri.parse("content://com.android.contacts/data");
		//内容提供者
		Cursor cursor = context.getContentResolver().query(uri, new String[]{"name_raw_contact_id"}, null, null, null);
		ContactBean bean = null;
		String r_c_id = null;
		Cursor cursor2 = null;
		String mimeType = null;
		String data = null;
		while (cursor.moveToNext()) {
			bean = new ContactBean();
			r_c_id = cursor.getString(0);
			cursor2 = context.getContentResolver().query(uriData, new String[]{"data1","mimetype"}, "raw_contact_id=?", new String[]{r_c_id}, null);
			//System.out.println();
			while (cursor2.moveToNext()) {
				//类型
				mimeType = cursor2.getString(1);
				//数据
				data = cursor2.getString(0);
				
				if (mimeType.equals("vnd.android.cursor.item/phone_v2")){
					//电话
					bean.setPhone(data);
				} else if (mimeType.equals("vnd.android.cursor.item/name")) {
					//名字
					bean.setName(data);
				}
				
			}
			
			//添加数据
			datas.add(bean);
			//关闭游标
			cursor2.close();
		}
		//关闭游标
		cursor.close();
		return datas;
	}
}
