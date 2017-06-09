package com.example.mvpdemo.model;

import com.example.mvpdemo.bean.UserBean;

public interface IUserModel {

	void setID(int id);

	void setFirstName(String firstName);

	void setLastName(String lastName);

	UserBean load(int id);
}
