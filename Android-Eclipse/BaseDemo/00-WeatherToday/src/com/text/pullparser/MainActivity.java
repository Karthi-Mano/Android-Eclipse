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
		//��ȡ��src�ļ����µ���Դ�ļ�
		InputStream is = getClassLoader().getResourceAsStream("weather.xml");
		
		//�õ�pull����������
		XmlPullParser xp = Xml.newPullParser();
		//��ʼ��
		try {
			xp.setInput(is, "gbk");
			
			//��ȡ��ǰ�ڵ���¼����ͣ�ͨ���¼����͵��жϣ����ǿ���֪����ǰ�ڵ���ʲô�ڵ㣬�Ӷ�ȷ������Ӧ����ʲô����
			int type = xp.getEventType();
			City city = null;
			while(type != XmlPullParser.END_DOCUMENT){
				//���ݽڵ�����ͣ�Ҫ����ͬ�Ĳ���
				switch (type) {
				case XmlPullParser.START_TAG:
					//					��ȡ��ǰ�ڵ������
					if("weather".equals(xp.getName())){
						//����city���϶������ڴ��city��javabean
						cityList = new ArrayList<City>();
					}
					else if("city".equals(xp.getName())){
						//����city��javabean����
						city = new City();
					}
					else if("name".equals(xp.getName())){
						//				��ȡ��ǰ�ڵ����һ���ڵ���ı�
						String name = xp.nextText();
						city.setName(name);
					}
					else if("temp".equals(xp.getName())){
						//				��ȡ��ǰ�ڵ����һ���ڵ���ı�
						String temp = xp.nextText();
						city.setTemp(temp);
					}
					else if("pm".equals(xp.getName())){
						//				��ȡ��ǰ�ڵ����һ���ڵ���ı�
						String pm = xp.nextText();
						city.setPm(pm);
					}
					break;
				case XmlPullParser.END_TAG:
					if("city".equals(xp.getName())){
						//��city��javabean���뼯����
						cityList.add(city);
					}
					break;

				}
				
				//��ָ���ƶ�����һ���ڵ㣬�����ظýڵ���¼�����
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