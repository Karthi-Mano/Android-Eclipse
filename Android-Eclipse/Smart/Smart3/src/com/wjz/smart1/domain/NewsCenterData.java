package com.wjz.smart1.domain;

import java.util.List;

public class NewsCenterData {
	public int retcode;
	public List<NewsData> data;

	public class NewsData {
		public List<ViewTagData> children;

		public class ViewTagData {
			public String id;// 10009
			public String title;// 军事
			public int type;// 1
			public String url;// /10009/list_1.json
		}

		public String id;
		public String title;
		int type;
		public String url;// /10006/list_1.json
		public String url1;// /10007/list1_1.json

		public String dayurl;
		public String excurl;

		public String weekurl;
	}

	public List<String> extend;
}
