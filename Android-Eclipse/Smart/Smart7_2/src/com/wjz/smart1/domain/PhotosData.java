package com.wjz.smart1.domain;

import java.util.List;

public class PhotosData {

	public String retcode;// 200
public PhotosData_Data data;
	public class PhotosData_Data {
		public String countcommenturl;// http://zhbj.qianlong.com/client/content/countComment/
		public String more;// http://zhbj.qianlong.com/static/api/news/10003/list_2.json
		public String title;// 组图
		public List<News> news;

		public class News {
			public boolean comment;// true
			public String commentlist;// http://zhbj.qianlong.com/static/api/news/10003/66/69866/comment_1.json
			public String commenturl;// http://zhbj.qianlong.com/client/user/newComment/69866
			public int id;// 69866
			public String largeimage;// http://zhbj.qianlong.com/static/images/2014/09/15/64/47651877KF8W.jpg
			public String listimage;// http://10.0.2.2:8080/zhbj/photos/images/46728356YLZ2.jpg
			public String pubdate;// 2014-09-15 11:26
			public String smallimage;// http://zhbj.qianlong.com/static/images/2014/09/15/25/485753981N83.jpg
			public String title;// 南宁特警主题海报 炫似大片
			public String type;// news
			public String url;// http://zhbj.qianlong.com/static/html/2014/09/15/764C6F5C4862187F6825.html
		}
	}
}
