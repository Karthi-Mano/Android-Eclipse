package com.text.pullparser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.itheima.pullparser.R;
import com.text.pullparser.domain.City;

import android.os.Bundle;
import android.app.Activity;
import android.util.Xml;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	List<City> cityList;
	private LinearLayout ll;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ll = (LinearLayout) findViewById(R.id.ll);
	}

	public void click(View v){
		//获取到src文件夹下的资源文件
		InputStream is = getClassLoader().getResourceAsStream("weather.xml");
		
		//拿到pull解析器对象
		XmlPullParser xp = Xml.newPullParser();
		//初始化
		try {
			xp.setInput(is, "gbk");
			
			//获取当前节点的事件类型，通过事件类型的判断，我们可以知道当前节点是什么节点，从而确定我们应该做什么操作
			int type = xp.getEventType();
			City city = null;
			while(type != XmlPullParser.END_DOCUMENT){
				//根据节点的类型，要做不同的操作
				switch (type) {
				case XmlPullParser.START_TAG:
					//					获取当前节点的名字
					if("weather".equals(xp.getName())){
						//创建city集合对象，用于存放city的javabean
						cityList = new ArrayList<City>();
					}
					else if("city".equals(xp.getName())){
						//创建city的javabean对象
						city = new City();
					}
					else if("name".equals(xp.getName())){
						//				获取当前节点的下一个节点的文本
						String name = xp.nextText();
						city.setName(name);
					}
					else if("temp".equals(xp.getName())){
						//				获取当前节点的下一个节点的文本
						String temp = xp.nextText();
						city.setTemp(temp);
					}
					else if("pm".equals(xp.getName())){
						//				获取当前节点的下一个节点的文本
						String pm = xp.nextText();
						city.setPm(pm);
					}
					break;
				case XmlPullParser.END_TAG:
					if("city".equals(xp.getName())){
						//把city的javabean放入集合中
						cityList.add(city);
					}
					break;

				}
				
				//把指针移动到下一个节点，并返回该节点的事件类型
				type = xp.next();
			}
			
			for (City c : cityList) {
				System.out.println(c.toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StringBuilder sb = new StringBuilder();
		for (City c : cityList) {
			System.out.println(c.toString());
			sb.append(c.toString()+"\n");
		}
		TextView tv = new TextView(this);
		tv.setText(sb);
		ll.addView(tv);
	}
	
}