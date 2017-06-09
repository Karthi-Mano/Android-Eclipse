package com.example.sendsms;

import java.util.Timer;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {


	private PendingIntent mContentIntent;

	private SmsManager mSmsManager;
	private static final String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
	private static final String SENT_SMS_ACTION = "SENT_SMS_ACTION";
	
	private PendingIntent mDeliverPI;
	private PendingIntent mSentPI;
	
	private DeliveredBroadcastReceiver mDeliveredBroadcastReceiver;
	private SendBroadcastReceiver mSendBroadcastReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mSmsManager = SmsManager.getDefault();

		// register the Broadcast Receivers
		Intent sentIntent = new Intent(SENT_SMS_ACTION);
		sentIntent.putExtra("SEND_SMS_NUM", "13641536078");
		sentIntent.putExtra("SEND_SMS_CONTENT",
				"" + SystemClock.currentThreadTimeMillis());

		mSendBroadcastReceiver = new SendBroadcastReceiver();
		registerReceiver(mSendBroadcastReceiver, new IntentFilter(
				SENT_SMS_ACTION));
		mSentPI = PendingIntent.getBroadcast(MainActivity.this, 0, sentIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		// create the deilverIntent parameter
		Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);

		mDeliveredBroadcastReceiver = new DeliveredBroadcastReceiver();
		registerReceiver(mDeliveredBroadcastReceiver, new IntentFilter(
				DELIVERED_SMS_ACTION));
		
		mDeliverPI = PendingIntent.getBroadcast(MainActivity.this, 100,
				deliverIntent, 0);

	}

	public void click(View v) {

		// 获取短信管理器
		mSmsManager.sendTextMessage(
				"5558", null, "哈哈", mSentPI,
				mDeliverPI);
	}

	class DeliveredBroadcastReceiver extends BroadcastReceiver {
		private static final String TAG = "haha";

		@Override
		public void onReceive(Context context, Intent intent) {
			System.out.println("收信人已经成功接收");

			Toast.makeText(MainActivity.this, "收信人已经成功接收", Toast.LENGTH_SHORT)
					.show();

			switch (getResultCode()) {
			case Activity.RESULT_OK:
				Log.i(TAG, "短信接收成功");
				break;
			case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
				Log.i(TAG, "短信接收失败:GENERIC_FAILURE");
				break;
			case SmsManager.RESULT_ERROR_NO_SERVICE:
				Log.i(TAG, "短信接收失败:NO_SERVICE");
				
				break;
			case SmsManager.RESULT_ERROR_NULL_PDU:
				Log.i(TAG, "短信接收失败:NULL_PDU");
				break;
			case SmsManager.RESULT_ERROR_RADIO_OFF:
				Log.i(TAG, "短信接收失败:RADIO_OFF");
				break;
			}
		}
	}

	class SendBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			
			System.out.println("haha");
			
			switch (getResultCode()) {
			case Activity.RESULT_OK:

				String send_sms_code = intent.getStringExtra("SEND_SMS_CODE");
				String send_sms_num = intent.getStringExtra("SEND_SMS_NUM");
				String send_sms_content = intent
						.getStringExtra("SEND_SMS_CONTENT");

				System.out.println("短信发送成功");

				System.out.println("send_sms_num       :      " + send_sms_num);
				System.out.println("send_sms_content   :      "
						+ send_sms_content);

				Toast.makeText(MainActivity.this, "短信发送成功", Toast.LENGTH_SHORT)
						.show();
				break;
			case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
				break;
			case SmsManager.RESULT_ERROR_RADIO_OFF:
				break;
			case SmsManager.RESULT_ERROR_NULL_PDU:
				break;
			default:
				Toast.makeText(MainActivity.this, "发送失败", Toast.LENGTH_LONG)
						.show();
				break;
			}
		}
	}

}
