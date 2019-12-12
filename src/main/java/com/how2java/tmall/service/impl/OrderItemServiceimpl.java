package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.OrderItemMapper;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.OrderItemExample;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.ProductService;

@Service
public class OrderItemServiceimpl implements OrderItemService {

	@Autowired
	OrderItemMapper orderitemmapper;
	
	OrderItemExample orderitemexample = new OrderItemExample();
	
	//用到的service
	@Autowired
	ProductService productservice;
	
	public List<OrderItem> getallorderitembyproduct(Product product){
		orderitemexample.clear();
		OrderItemExample.Criteria cri3 = orderitemexample.createCriteria();
		cri3.andPidEqualTo(product.getId());
		List<OrderItem> tempitem = orderitemmapper.selectByExample(orderitemexample);
		return tempitem;
	}
	
	public List<OrderItem> getallorderitembyorder(Order order){
		orderitemexample.clear();
		OrderItemExample.Criteria cri3 = orderitemexample.createCriteria();
		cri3.andOidEqualTo(order.getId());
		List<OrderItem> tempitem = orderitemmapper.selectByExample(orderitemexample);
		
		for(OrderItem oi : tempitem) {
			oi.setProduct(productservice.getproduct(oi.getPid()));
		}
		return tempitem;
	}
	
	public List<OrderItem> getallcartitembyuser(User user){
		orderitemexample.clear();
		orderitemexample.createCriteria().andUidEqualTo(user.getId()).andOidIsNull();
		
		List<OrderItem> tempitem = orderitemmapper.selectByExample(orderitemexample);
		
		for(OrderItem oi : tempitem) {
			oi.setProduct(productservice.getproduct(oi.getPid()));
		}
		return tempitem;
	}
}
