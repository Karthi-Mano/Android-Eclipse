package com.example.utils;

import com.example.fragment.PanDianFragment;
import com.example.fragment.PanDianFragment.MyAdapte;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class ShowDialog {

	/**
	 * 对话框
	 * 
	 * @param context
	 * @param json
	 * @param myAdapte
	 * @param panDianFragment
	 * @param myAdapte
	 * @param tv_confirm
	 */

	public void showDialog1(Context context, final String json,
			final PanDianFragment panDianFragment) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("提醒").setMessage("提交数据")

		.setPositiveButton("是", new AlertDialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				panDianFragment.commitDataForNet(json);
				listener.onUploadFinished(true);

			}
		}).setNegativeButton("否", new AlertDialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		}).show();// 显示对话框
	}

	public interface OnUploadFinished {
		void onUploadFinished(boolean isUpload);
	}

	OnUploadFinished listener;

	public void setListener(OnUploadFinished listener) {
		this.listener = listener;
	}

}
