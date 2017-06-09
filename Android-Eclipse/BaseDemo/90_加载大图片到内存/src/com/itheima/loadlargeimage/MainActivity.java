package com.itheima.loadlargeimage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private ImageView iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		iv = (ImageView) findViewById(R.id.iv);
	}

	public void click(View view) {
		// 这种方式把所有的像素点都加载内存,vm虚拟机扛不住.
		// Bitmap bitmap = BitmapFactory.decodeFile("mnt/sdcard/hh.jpg");
		// iv.setImageBitmap(bitmap);
		// 用户识别的出来的图形,受到设备的分辨率的限制.
		// 只要我们显示的图形比手机的分辨率高,或者跟手机分辨率一致,用户就看不出来图形的质量的缩放了.
		BitmapFactory.Options opts = new Options();// 解码图片的配置选项
		opts.inJustDecodeBounds = true;// 不去真实的解析bitmap,而是查询bitmap的宽高信息
		BitmapFactory.decodeFile("mnt/sdcard/hh.jpg", opts);
		int width = opts.outWidth;
		int height = opts.outHeight;
		System.out.println("图片宽度width:" + width);
		System.out.println("图片高度height:" + height);
		// 得到手机屏幕的宽高
		WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
		int screenWidth = wm.getDefaultDisplay().getWidth();
		int screenHeight = wm.getDefaultDisplay().getHeight();
		System.out.println("屏幕宽度width:" + screenWidth);
		System.out.println("屏幕高度height:" + screenHeight);

		int dx = width / screenWidth;
		int dy = height / screenHeight;
		int scale = 1;
		if (dx > dy && dy > 1) {
			scale = dx;
		}
		if (dy > dx && dx > 1) {
			scale = dy;
		}
		System.out.println(scale);
		//缩放的方式把图片加载到手机内存
		opts.inSampleSize = scale;
		opts.inJustDecodeBounds = false;// 真实的解析bitmap
		Bitmap bitmap = BitmapFactory.decodeFile("mnt/sdcard/hh.jpg", opts);
		iv.setImageBitmap(bitmap);
	}

}
