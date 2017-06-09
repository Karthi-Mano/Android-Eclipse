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
		// ���ַ�ʽ�����е����ص㶼�����ڴ�,vm���������ס.
		// Bitmap bitmap = BitmapFactory.decodeFile("mnt/sdcard/hh.jpg");
		// iv.setImageBitmap(bitmap);
		// �û�ʶ��ĳ�����ͼ��,�ܵ��豸�ķֱ��ʵ�����.
		// ֻҪ������ʾ��ͼ�α��ֻ��ķֱ��ʸ�,���߸��ֻ��ֱ���һ��,�û��Ϳ�������ͼ�ε�������������.
		BitmapFactory.Options opts = new Options();// ����ͼƬ������ѡ��
		opts.inJustDecodeBounds = true;// ��ȥ��ʵ�Ľ���bitmap,���ǲ�ѯbitmap�Ŀ����Ϣ
		BitmapFactory.decodeFile("mnt/sdcard/hh.jpg", opts);
		int width = opts.outWidth;
		int height = opts.outHeight;
		System.out.println("ͼƬ���width:" + width);
		System.out.println("ͼƬ�߶�height:" + height);
		// �õ��ֻ���Ļ�Ŀ��
		WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
		int screenWidth = wm.getDefaultDisplay().getWidth();
		int screenHeight = wm.getDefaultDisplay().getHeight();
		System.out.println("��Ļ���width:" + screenWidth);
		System.out.println("��Ļ�߶�height:" + screenHeight);

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
		//���ŵķ�ʽ��ͼƬ���ص��ֻ��ڴ�
		opts.inSampleSize = scale;
		opts.inJustDecodeBounds = false;// ��ʵ�Ľ���bitmap
		Bitmap bitmap = BitmapFactory.decodeFile("mnt/sdcard/hh.jpg", opts);
		iv.setImageBitmap(bitmap);
	}

}
