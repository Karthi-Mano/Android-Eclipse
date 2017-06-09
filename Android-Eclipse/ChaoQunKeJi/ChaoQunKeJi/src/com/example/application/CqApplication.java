package com.example.application;


import org.xutils.x;

import android.app.Application;

public class CqApplication  extends Application{
	
	@Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);
       
    }

}
