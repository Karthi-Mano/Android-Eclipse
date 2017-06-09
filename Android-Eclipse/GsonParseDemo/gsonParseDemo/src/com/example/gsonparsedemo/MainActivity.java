package com.example.gsonparsedemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		HashMap<String,String> hm = new HashMap<String, String>();
		
		List<Person> person = new ArrayList<Person>();
		
		List<HashMap<String,String>> a = new ArrayList<HashMap<String,String>>();
		
		Gson gson = new Gson();
		 String str="[\n" +
	                "    {\n" +
	                "        \"name\": \"Google\",\n" +
	                "        \"url\": \"1\"\n" +
	                "    },\n" +
	                "    {\n" +
	                "        \"name\": \"Baidu\",\n" +
	                "        \"url\": \"2\"\n" +
	                "    },\n" +
	                "    {\n" +
	                "        \"name\": \"SoSo\",\n" +
	                "        \"url\": \"3\"\n" +
	                "    }\n" +
	                "]";

		a = gson.fromJson(str, new TypeToken<List<HashMap<String,String>>>() {
		}.getType());

		for (HashMap<String, String> hashMap : a) {
			String string = hashMap.get("url");
			System.out.println("url   :"+string);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
