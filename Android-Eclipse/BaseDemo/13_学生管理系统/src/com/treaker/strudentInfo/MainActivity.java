package com.treaker.strudentInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.util.Xml;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TextView et_name;
	private TextView et_num;
	private RadioGroup am_rg;
	private LinearLayout am_ll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		et_name = (TextView) findViewById(R.id.am_et_name);
		et_num = (TextView) findViewById(R.id.am_et_num);
		am_rg = (RadioGroup) findViewById(R.id.am_rg);
		am_ll = (LinearLayout) findViewById(R.id.am_ll);

		getAssetsInfo();

		refreshData();
	}

	private void refreshData() {
		am_ll.removeAllViews();
		File files = this.getFilesDir();
		for (File file : files.listFiles()) {

			getFilesDirInfo(file);

		}
	}

	private void getFilesDirInfo(File file) {
		try {

			FileInputStream inputStream = new FileInputStream(file);
			StringBuilder sb = new StringBuilder();
			// 初始化解析器
			XmlPullParser pullParser = Xml.newPullParser();
			// 设置参数
			pullParser.setInput(inputStream, "utf-8");
			// 获得指针位置
			int type = pullParser.getEventType();
			while (type != XmlPullParser.END_DOCUMENT) {
				switch (type) {
				case XmlPullParser.START_TAG:
					if ("name".equals(pullParser.getName())) {
						String name = pullParser.nextText();
						System.out.print("+++name:"+name);
						sb.append("+++name:"+name);
					} else if ("num".equals(pullParser.getName())) {
						String num = pullParser.nextText();
						System.out.print("+++num:"+num);
						sb.append("+++num:"+num);
					} else if ("sex".equals(pullParser.getName())) {
						String sex = pullParser.nextText();
						System.out.print("+++sex:"+sex);
						sb.append("+++sex:"+sex);
					}
					break;

				}
				type = pullParser.next();
			}
			inputStream.close();
			String text = sb.toString();
			TextView tv = new TextView(this);
			tv.setText(text);
			am_ll.addView(tv);
		} catch (Exception e) {
			e.printStackTrace();
			TextView tv = new TextView(this);
			tv.setText("这条数据无效");
			am_ll.addView(tv);
		}
	}

	public void getAssetsInfo() {
		try {
			InputStream inputStream = this.getAssets().open("111.xml");
			// 1.初始化pull解析器
			XmlPullParser pullParser = Xml.newPullParser();
			// 2.设置解析器参数
			pullParser.setInput(inputStream, "utf-8");
			int type = pullParser.getEventType();
			while (type != XmlPullParser.END_DOCUMENT) {
				switch (type) {
				case XmlPullParser.START_TAG:
					if ("name".equals(pullParser.getName())) {
						String name = pullParser.nextText();
						System.out.println("---" + name);
					} else if ("num".equals(pullParser.getName())) {
						String num = pullParser.nextText();
						System.out.println("---" + num);
					} else if ("sex".equals(pullParser.getName())) {
						String sex = pullParser.nextText();
						System.out.println("---" + sex);
					}
					break;
				}
				type = pullParser.next();
			}
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void click(View view) {
		String name = et_name.getText().toString().trim();
		String num = et_num.getText().toString().trim();
		if (TextUtils.isEmpty(name) && TextUtils.isEmpty(num)) {
			Toast.makeText(this, "姓名学号不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		try {
			FileOutputStream os = openFileOutput(num + ".xml", MODE_PRIVATE);

			XmlSerializer serializer = Xml.newSerializer();
			serializer.setOutput(os, "utf-8");
			serializer.startDocument("utf-8", true);
			serializer.startTag(null, "student");

			serializer.startTag(null, "name");
			serializer.text(name);
			serializer.endTag(null, "name");

			serializer.startTag(null, "num");
			serializer.text(num);
			serializer.endTag(null, "num");

			serializer.startTag(null, "sex");
			if (am_rg.getCheckedRadioButtonId() == R.id.male) {
				serializer.text("男");
			} else {
				serializer.text("女");
			}

			serializer.endTag(null, "sex");

			serializer.endTag(null, "student");
			serializer.endDocument();

			os.close();
			Toast.makeText(this, "数据保存成功", Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			Toast.makeText(this, "数据保存失败", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

}
