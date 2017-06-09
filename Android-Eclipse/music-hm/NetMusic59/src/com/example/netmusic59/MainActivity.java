package com.example.netmusic59;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity implements OnPreparedListener {

	private MediaPlayer mMediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void click(View v) {
		try {
			if (mMediaPlayer == null) {
				mMediaPlayer = new MediaPlayer();
			}
			mMediaPlayer.reset();
//			mMediaPlayer.setDataSource("http://192.168.1.100:8080/xpg.mp3");
			//mMediaPlayer.setDataSource("mms://mediasrv2.iptv.xmg.com.cn/yinyue");
			//mMediaPlayer.setDataSource("http://mp3.9ku.com/mp3/656/655838.mp3");
//			mMediaPlayer.setDataSource("http://song1.music.response.itmf.cn/42d6cbec6c8a4dcb0007ad4565a39a4c/573173f4/G014/M0B/1A/07/Tg0DAFUOHOCAedbyABC0f6JL9kA515.m4a");
			mMediaPlayer.prepareAsync();//网络音乐.应该异步准备
			mMediaPlayer.setOnPreparedListener(this);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onPrepared(MediaPlayer mp) {//如果使用prepareAsync,应该把start()方法放到这个地方
		Toast.makeText(getApplicationContext(), "准备好了", 0).show();
		mMediaPlayer.start();
	}

	@Override
	protected void onDestroy() {
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
		super.onDestroy();
	}
}
