package com.example.pic_3;

import android.net.Uri;

public class Test {

	public void test(){
		
		//组拼url
		Uri.Builder builder = Uri.parse("www.baidu.com").buildUpon();
        builder.appendQueryParameter("key","value");
        Uri build = builder.build();
        build.toString();

      
	}
}
