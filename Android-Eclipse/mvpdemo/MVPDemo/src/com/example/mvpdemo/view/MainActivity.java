package com.example.mvpdemo.view;

import com.example.mvpdemo.R;
import com.example.mvpdemo.R.layout;
import com.example.mvpdemo.R.menu;
import com.example.mvpdemo.presenter.UserPresenter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener,
		IUserView {

	private EditText mFirstNameEditText, mLastNameEditText, mIdEditText;
	private Button mSaveButton, mLoadButton;
	private UserPresenter mUserPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findWidgets();
		//初始化Presenter
		mUserPresenter = new UserPresenter(this);
		
	}

	/**
	 * 初始化view
	 */
	private void findWidgets() {
		mFirstNameEditText = (EditText) findViewById(R.id.first_name_edt);
		mLastNameEditText = (EditText) findViewById(R.id.last_name_edt);
		mIdEditText = (EditText) findViewById(R.id.id_edt);
		mSaveButton = (Button) findViewById(R.id.saveButton);
		mLoadButton = (Button) findViewById(R.id.loadButton);
		mSaveButton.setOnClickListener(this);
		mLoadButton.setOnClickListener(this);
	}

	/**
	 * 从edittext中获取ID
	 */
	@Override
	public int getID() {
		return Integer.parseInt(mIdEditText.getText().toString());
	}

	/**
	 * 从edittext中获取FirstName
	 */
	@Override
	public String getFirstName() {
		return mFirstNameEditText.getText().toString();
	}

	/**
	 * 从edittext中获取LastName
	 */
	@Override
	public String getLastName() {
		return mLastNameEditText.getText().toString();
	}

	/**
	 * 从UserBean中读取firstname并设置
	 */
	@Override
	public void setFirstName(String firstName) {
		mFirstNameEditText.setText(firstName);

	}

	/**
	 * 从UserBean中读取LastName并设置
	 */
	@Override
	public void setLastName(String lastName) {
		mLastNameEditText.setText(lastName);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.saveButton:
			//保存
			mUserPresenter.saveUser(getID(), getFirstName(), getLastName());
			break;
		case R.id.loadButton:
			//加载
			mUserPresenter.loadUser(getID());
			break;
		default:
			break;
		}
	}

}
