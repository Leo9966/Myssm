package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.User;

public interface OrderItemService {
	List<OrderItem> getallorderitembyproduct(Product product);
	List<OrderItem> getallorderitembyorder(Order order);
	List<OrderItem> getallcartitembyuser(User user);
	void updateitem(OrderItem record);
	void additem(OrderItem record);
	OrderItem getitem(int id);
}
