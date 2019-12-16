package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.User;

public interface OrderService {
    String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";
    
	List<Order> getuserorder(User user);
	void add(Order order);
	float add(Order order,List<OrderItem> ois);
	Order getorderbyid(int id);
	void updateorder(Order record);
}
