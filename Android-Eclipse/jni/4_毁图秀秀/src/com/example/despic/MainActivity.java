package com.example.despic;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.mt.mtxx.image.JNI;

public class MainActivity extends Activity {

	static{
		System.loadLibrary("mtimage-jni");
	}
	
	private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.iv);
		iv.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.a));
    }

    public void click(View view){
		JNI jni = new JNI();
		
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a);
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int[] pixels = new int[width*height];
		
		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
		
		jni.StyleLomoC(pixels, width, height);
		
		Bitmap newbitmap = Bitmap.createBitmap(pixels, width, height, bitmap.getConfig());
		iv.setImageBitmap(newbitmap);
	}
   
    
}
