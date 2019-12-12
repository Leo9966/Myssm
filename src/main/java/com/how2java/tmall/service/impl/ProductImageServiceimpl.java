package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.ProductImageMapper;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.pojo.ProductImageExample;
import com.how2java.tmall.service.ProductImageService;

@Service
public class ProductImageServiceimpl implements ProductImageService{
	
	@Autowired
	ProductImageMapper productimagemapper;
	
	ProductImageExample productimageexample = new ProductImageExample();
	
	public List<ProductImage> getproductImage(Product product){
		productimageexample.clear();
		ProductImageExample.Criteria cri2 = productimageexample.createCriteria();
		cri2.andPidEqualTo(product.getId());
		List<ProductImage> tempimage = productimagemapper.selectByExample(productimageexample);
		return tempimage;
	}
	
	public List<ProductImage> getproductSingleImage(Product product){
		productimageexample.clear();
		ProductImageExample.Criteria cri2 = productimageexample.createCriteria();
		cri2.andPidEqualTo(product.getId());
		cri2.andTypeEqualTo("type_single");
		List<ProductImage> tempimage = productimagemapper.selectByExample(productimageexample);
		return tempimage;
	}
	
	public List<ProductImage> getproductDetailImage(Product product){
		productimageexample.clear();
		ProductImageExample.Criteria cri2 = productimageexample.createCriteria();
		cri2.andPidEqualTo(product.getId());
		cri2.andTypeEqualTo("type_detail");
		List<ProductImage> tempimage = productimagemapper.selectByExample(productimageexample);
		return tempimage;
	}
}
