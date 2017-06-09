package com.dook.update;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;



/**
 * XML文档解析工具类 
 * 
 */
public class XMLParserUtil {

	/**
	 * 获取版本更新信息 
	 * 
	 * @param is
	 *            读取连接服务version.xml文档的输入流  

	 * @return
	 */
	public static VersionInfo getUpdateInfo(InputStream is) {
		VersionInfo info = new VersionInfo();
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			System.out.println(root.getName());
			Iterator<Element> ele = root.elementIterator();
			while (ele.hasNext()) {
				Element e = ele.next();
				System.out.println(e.getName() + ":" + e.getText());
				if ("version".equals(e.getName())) {
					info.setVersion(e.getText());
				} else if ("updateTime".equals(e.getName())) {
					info.setUpdateTime(e.getText());
				} else if ("updateTime".equals(e.getName())) {
					info.setUpdateTime(e.getText());
				} else if ("downloadURL".equals(e.getName())) {
					info.setDownloadURL(e.getText());
				} else if ("displayMessage".equals(e.getName())) {
					info.setDisplayMessage(parseTxtFormat(e.getText(), "##"));
				} else if ("apkName".equals(e.getName())) {
					info.setApkName(e.getText());
				} else if ("versionCode".equals(e.getName())) {
					info.setVersionCode(Integer.parseInt(e.getText()));
					System.out.println("versioncode:" + e.getStringValue());
				}
			}
			/*XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(is, "UTF-8");
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				System.out.println("......................");
				switch (eventType) {
				case XmlPullParser.START_TAG:
					System.out.println(parser.getName() + ">>>>>>>>>>>>>");
					if ("version".equals(parser.getName())) {
						info.setVersion(parser.nextText());
					} else if ("updateTime".equals(parser.getName())) {
						info.setUpdateTime(parser.nextText());
					} else if ("updateTime".equals(parser.getName())) {
						info.setUpdateTime(parser.nextText());
					} else if ("downloadURL".equals(parser.getName())) {
						info.setDownloadURL(parser.nextText());
					} else if ("displayMessage".equals(parser.getName())) {
						info.setDisplayMessage(parseTxtFormat(parser.nextText(), "##"));
					} else if ("apkName".equals(parser.getName())) {
						info.setApkName(parser.nextText());
					} else if ("versionCode".equals(parser.getName())) {
						info.setVersionCode(Integer.parseInt(parser.nextText()));
						System.out.println("versioncode:" + parser.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					break;
				}
				eventType = parser.next();
				System.out.println("eventType:" + eventType);
			}*/
		}  catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * 根据指定字符格式化字符串（换行）  

	 * 
	 * @param data
	 *            需要格式化的字符串
	 * @param formatChar
	 *            指定格式化字符
	 * @return
	 */
	public static String parseTxtFormat(String data, String formatChar) {
		StringBuffer backData = new StringBuffer();
		String[] txts = data.split(formatChar);
		for (int i = 0; i < txts.length; i++) {
			backData.append(txts[i]);
			backData.append("\n");
		}
		return backData.toString();
	}

}
