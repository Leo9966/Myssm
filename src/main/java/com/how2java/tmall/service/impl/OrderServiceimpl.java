package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.OrderMapper;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderExample;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.OrderService;
import com.how2java.tmall.service.UserService;

@Service
public class OrderServiceimpl implements OrderService{
	
	@Autowired
	OrderMapper ordermapper;
	
	OrderExample orderexample = new OrderExample();
	
	//用到的Service
	@Autowired
	OrderItemService orderitemservice;
	
	@Autowired
	UserService userservice;
	
	public List<Order> getuserorder(User user){
		orderexample.clear();
		OrderExample.Criteria cri = orderexample.createCriteria();
		cri.andUidEqualTo(user.getId());
		cri.andStatusNotEqualTo("delete");
		List<Order> os = ordermapper.selectByExample(orderexample);
		
		for(Order o : os) {
			List<OrderItem> ois = orderitemservice.getallorderitembyorder(o);
			o.setorderItems(ois);
			
			int totalnumber = 0;
			float totalprice = 0;
			
			for(OrderItem oi : ois) {
				totalnumber = totalnumber + oi.getNumber();
				totalprice = totalprice + oi.getProduct().getPromotePrice() * oi.getNumber();
			}
			
			o.setTotalNumber(totalnumber);
			o.setTotal(totalprice);
			
//			if(ois.size()!=0)
			o.setUser(userservice.getUserbyid(ois.get(0).getUid()));
		}
		
		return os;
	}
	
	public void add(Order order) {
		ordermapper.insert(order);
	}
	
	public float add(Order order,List<OrderItem> ois) {
		float total = 0;
		add(order);
		
		for(OrderItem oi : ois) {
			oi.setOid(order.getId());
			orderitemservice.updateitem(oi);
			 total+=oi.getProduct().getPromotePrice()*oi.getNumber();
		}
		return total;
	}
	
	public Order getorderbyid(int id) {
		Order o = ordermapper.selectByPrimaryKey(id);
		return o;
	}
	
	public void updateorder(Order record) {
		ordermapper.updateByPrimaryKey(record);
	}
}
