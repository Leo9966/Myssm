package com.how2java.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.PropertyMapper;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.pojo.PropertyExample;
import com.how2java.tmall.service.PropertyService;

@Service
public class PropertyServiceimpl implements PropertyService{
	
	@Autowired
	PropertyMapper propertymapper;
	
	PropertyExample propertyexample = new PropertyExample();
	
	public Property getPropertybyid(int id) {
		return propertymapper.selectByPrimaryKey(id);
	}
}
