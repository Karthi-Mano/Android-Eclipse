package com.treaker.calc;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View btn4 = findViewById(R.id.btn_4);
        View btn5 = findViewById(R.id.btn_5);
        View btn6 = findViewById(R.id.btn_6);
        View btn7 = findViewById(R.id.btn_7);
        View btn8 = findViewById(R.id.btn_8);
        View btn9 = findViewById(R.id.btn_9);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_4:
			System.out.println("4");
			break;
		case R.id.btn_5:
			System.out.println("5");
			break;
		case R.id.btn_6:
			System.out.println("6");
			break;
		case R.id.btn_7:
			System.out.println("7");
			break;
		case R.id.btn_8:
			System.out.println("8");
			break;
		case R.id.btn_9:
			System.out.println("9");
			break;
		

		default:
			break;
		}
	}



    
}
