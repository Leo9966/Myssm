package com.how2java.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.how2java.tmall.mapper.UserMapper;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.pojo.UserExample;
import com.how2java.tmall.service.UserService;

@Service
public class UserServiceimpl implements UserService{

	@Autowired
	UserMapper usermapper;
	
	UserExample userexample = new UserExample();
	
	public User getUserbyid(int id) {
		return usermapper.selectByPrimaryKey(id);
	}
	
	public User getUserbynameandpassword(String name,String password) {
		userexample.clear();
		
		UserExample.Criteria cri = userexample.createCriteria();
		cri.andNameEqualTo(name);
		cri.andPasswordEqualTo(password);
		
		List<User> user =  usermapper.selectByExample(userexample);
		
		if(user.size()==0) {
			return null;
		}
		
		return user.get(0);
	}
	
	public User getUserbyname(String name) {
		userexample.clear();
		
		UserExample.Criteria cri = userexample.createCriteria();
		cri.andNameEqualTo(name);
		
		List<User> user =  usermapper.selectByExample(userexample);
		
		if(user.size()==0) {
			return null;
		}
		
		return user.get(0);
	}
	
	public void addUser(User user) {
		usermapper.insert(user);	
	}
}
