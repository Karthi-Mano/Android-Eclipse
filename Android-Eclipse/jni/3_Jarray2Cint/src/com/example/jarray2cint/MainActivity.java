package com.example.jarray2cint;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	
	static{
		System.loadLibrary("Jarray2Cint");
	}

	int[] colorarray = {0,1,2,3,4,5,6,7,8,9};
	
	/**
	 * 定义一个本地方法,修改数组里面的每个元素的值
	 * @param colorarray
	 */
	public native void changeArray(int[] colorarray);
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        changeArray(colorarray);
		
    }  
    
    public void click(View v){
    	
    	System.out.println("-----------------------------------");
    	
    	for(int i:colorarray){
			System.out.println(i);
		}
		
    }
}

