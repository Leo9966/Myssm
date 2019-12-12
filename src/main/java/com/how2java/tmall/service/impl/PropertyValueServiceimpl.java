package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.PropertyValueMapper;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.pojo.PropertyValue;
import com.how2java.tmall.pojo.PropertyValueExample;
import com.how2java.tmall.service.PropertyService;
import com.how2java.tmall.service.PropertyValueService;

@Service
public class PropertyValueServiceimpl implements PropertyValueService{
	
	@Autowired
	PropertyValueMapper propertyvaluemapper;
	
	PropertyValueExample propertyvalueexample = new PropertyValueExample();
	
	@Autowired
	PropertyService propertyservice;
	
	public List<PropertyValue> getPropertyValuebyProduct(Product p){
		propertyvalueexample.clear();
		PropertyValueExample.Criteria cri = propertyvalueexample.createCriteria();
		cri.andPidEqualTo(p.getId());
		List<PropertyValue> pvs = propertyvaluemapper.selectByExample(propertyvalueexample);
		
		for(PropertyValue pv : pvs) {
			Property property = propertyservice.getPropertybyid(pv.getPtid());
			pv.setProperty(property);
		}
		return pvs;
	}
}
