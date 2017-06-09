package com.example.getwh;

import android.app.Application;

public class MyApplication extends Application {

	@Override
	public void onCreate() {

		soutPrivate();
		soutPublic();
		super.onCreate();
	}

	private void soutPrivate() {
		System.out.println("soutPrivate");
	}

	public void soutPublic() {
		System.out.println("soutPublic");
	}

}
