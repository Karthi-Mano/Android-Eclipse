package com.itheima.mobilesafe13.unittest;

import java.util.List;

import com.itheima.mobilesafe13.dao.BlackDao;
import com.itheima.mobilesafe13.db.BlackDB;

import android.test.AndroidTestCase;

public class TestBlack extends AndroidTestCase {
	
	public void testTotal(){
		BlackDao dao = new BlackDao(getContext());
		System.out.println(dao.getPageData(1, 5));
	}
	public void testFindAll(){
		BlackDao dao = new BlackDao(getContext());
		System.out.println(dao.findAll());
	}
	public void testAdd(){
		//测试添加黑名单数据
		BlackDao dao = new BlackDao(getContext());
		for (int i = 0; i< 100; i++) {
			dao.add("110" + i, BlackDB.PHONE_MODE);
		}
	}
	

}
