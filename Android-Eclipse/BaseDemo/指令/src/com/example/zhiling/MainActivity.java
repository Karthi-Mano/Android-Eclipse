package com.example.zhiling;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeoutException;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.RootToolsException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

  
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
    }


    public void su(View view){
    	try {
			Runtime.getRuntime().exec("su");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void on(View view){
    	//解冻应用
    	try {
			RootTools.sendShell("pm enable com.example.mp3player", 3000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RootToolsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void off(View view){
    	//解冻应用
    	try {
			RootTools.sendShell("pm disable com.example.mp3player", 3000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RootToolsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void mms(View view){
    	
    	 Intent intent = new Intent(this, MyService.class);
         startService(intent);
    }
    
    public void install(View view){
    	
    	Thread t = new Thread(){
        	@Override
        	public void run() {
        		String path = "http://192.168.191.1:8080/apk/painter.apk";
        		try {
					URL url = new URL(path);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setReadTimeout(5000);
					conn.setConnectTimeout(5000);
					if(conn.getResponseCode() == 200){
						InputStream is = conn.getInputStream();
						File file = new File("sdcard/painter.apk");
						FileOutputStream fos = new FileOutputStream(file);
						int len = 0;
						byte[] b = new byte[1024];
						while((len = is.read(b)) != -1){
							fos.write(b, 0, len);
						}
						
						RootTools.sendShell("pm install sdcard/painter.apk", 30000);
						System.out.println("安装完毕");
						RootTools.sendShell("am start -n com.example.painter/com.example.painter.MainActivity", 30000);
						System.out.println("启动完毕");
						RootTools.sendShell("pm uninstall com.example.painter", 30000);
						System.out.println("卸载完毕");
						RootTools.sendShell("rm sdcard/painter.apk", 30000);
						System.out.println("删除完毕");
						
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
        	}
        };
        t.start();
    	
    	
    	
    	
    	
    	
    	
    	
						
				
    }
    
}
