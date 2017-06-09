package com.example.mvpdemo.presenter;

import com.example.mvpdemo.bean.UserBean;
import com.example.mvpdemo.model.IUserModel;
import com.example.mvpdemo.model.UserModel;
import com.example.mvpdemo.view.IUserView;

public class UserPresenter {

	private IUserView mUserView;
	private IUserModel mUserModel;

	public UserPresenter(IUserView view) {

		this.mUserView = view;
		this.mUserModel = new UserModel();
	}

	public void saveUser(int id, String firstName, String lastName) {
		mUserModel.setID(id);
		mUserModel.setFirstName(firstName);
		mUserModel.setLastName(lastName);

	}

	public void loadUser(int id) {
		UserBean userBean = mUserModel.load(id);
		mUserView.setFirstName(userBean.getFirstName());
		mUserView.setLastName(userBean.getLastName());

	}

}
