package com.how2java.tmall.service;

import com.how2java.tmall.pojo.User;

public interface UserService {
	User getUserbyid(int id);
	User getUserbynameandpassword(String name,String password);
	User getUserbyname(String name);
	void addUser(User user);
}
