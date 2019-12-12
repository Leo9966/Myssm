package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.User;

public interface OrderService {
	List<Order> getuserorder(User user);
}
