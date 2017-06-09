package com.example.picdemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private static final int NONE = 0;
	private static final int PHOTO_GRAPH = 1;// 拍照
	private static final int PHOTO_PICK = 2; // 选择
	private static final String IMAGE_UNSPECIFIED = "image/*";
	private ImageView imageView = null;
	private Button btnPhone = null;
	private Button btnTakePicture = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		imageView = (ImageView) findViewById(R.id.imageView);
		btnPhone = (Button) findViewById(R.id.btnPhone);
		btnPhone.setOnClickListener(onClickListener);
		btnTakePicture = (Button) findViewById(R.id.btnTakePicture);
		btnTakePicture.setOnClickListener(onClickListener);
	}

	private final View.OnClickListener onClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v == btnPhone) { // 从相册获取图片
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						IMAGE_UNSPECIFIED);
				startActivityForResult(intent, PHOTO_PICK);
			} else if (v == btnTakePicture) { // 从拍照获取图片
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
						Environment.getExternalStorageDirectory(), "temp.jpg")));
				startActivityForResult(intent, PHOTO_GRAPH);
			}

		}

	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == NONE)
			return;
		// 拍照
		if (requestCode == PHOTO_GRAPH) {
			// 设置文件保存路径
			File picture = new File(Environment.getExternalStorageDirectory()
					+ "/temp.jpg");
			Bitmap decodeStream;
			try {
				decodeStream = BitmapFactory.decodeStream(new FileInputStream(
						picture));
				imageView.setImageBitmap(decodeStream);
				
				//picture.delete();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		if (data == null)
			return;

		// 读取相册缩放图片
		if (requestCode == PHOTO_PICK) {
			Uri uri = data.getData();
			System.out.println("data:" + uri);

			ContentResolver cr = this.getContentResolver();
			try {
				Bitmap bitmap = BitmapFactory.decodeStream(cr
						.openInputStream(uri));
				/* 将Bitmap设定到ImageView */
				imageView.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}
}
