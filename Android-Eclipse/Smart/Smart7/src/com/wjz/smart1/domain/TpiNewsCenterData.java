package com.wjz.smart1.domain;

import java.util.List;

public class TpiNewsCenterData {
	public int retcode;
	public TpiNewsCenterData_Data data;

	public class TpiNewsCenterData_Data {
		public String countcommenturl;// http://zhbj.qianlong.com/client/content/countComment/
		public String more;// /10006/list_2.json
		public String title;// 中国
		public List<TpiNewsCenterData_Data_news> news;// Array
		public List<TpiNewsCenterData_Data_topic> topic;// Array
		public List<TpiNewsCenterData_Data_topnews> topnews;// Array

		public class TpiNewsCenterData_Data_news {
			public String commentlist;// http://10.0.2.2:8080/zhbj/10006/comment_1.json
			public String commenturl;// http://zhbj.qianlong.com/client/user/newComment/35319
			public String id;// 35311
			public String listimage;// http://10.0.2.2:8080/zhbj/10006/1452327318UU91.jpg
			public String pubdate;// 2014-04-08 14:58
			public String title;// 中国
			public String type;// news
			public String url;// http://10.0.2.2:8080/zhbj/10006/724D6A55496A11726628.html
		}

		public class TpiNewsCenterData_Data_topic {

			public String description;// 11111111
			public String id;// 10101
			public String listimage;// http://10.0.2.2:8080/zhbj/10006/1452327318UU91.jpg
			public String sort;// 1
			public String title;// 中国
			public String url;// http://10.0.2.2:8080/zhbj/10006/list_1.json
		}

		public class TpiNewsCenterData_Data_topnews {
			public String comment;//	true
			public String commentlist;// http://10.0.2.2:8080/zhbj/10006/comment_1.json
			public String commenturl;// http://zhbj.qianlong.com/client/user/newComment/35301
			public String id;// 35301
			public String pubdate;// 2014-04-08 14:24
			public String title;// 中国1
			public String topimage;// http://10.0.2.2:8080/zhbj/10006/1452327318UU91.jpg
			public String type;// news
			public String url;// http://10.0.2.2:8080/zhbj/10006/724D6A55496A11726628.html

		}

	}
}
