package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.ProductMapper;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductExample;
import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.pojo.Review;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.OrderService;
import com.how2java.tmall.service.ProductImageService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.service.ReviewService;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	ProductMapper productmapper;
	
	ProductExample productexample = new ProductExample();
	
	//用到的Service
	@Autowired
    ProductImageService productimageService;
	
	@Autowired
	OrderItemService orderitemService;
	
	@Autowired
	ReviewService reviewService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductImageService productimageservice;
	
	public List<Product> fillcategory(Category category) {
		productexample.clear();
		ProductExample.Criteria cri = productexample.createCriteria();
		cri.andCidEqualTo(category.getId());
		List<Product> products = productmapper.selectByExample(productexample);
		return products;
	}
	
	public Product getproduct(int pid) {
		productexample.clear();
		Product product = productmapper.selectByPrimaryKey(pid);
		
		List<ProductImage> tempimage = productimageService.getproductImage(product);
		product.setFirstProductImage(tempimage.get(0));
		
		//获取product的订单数
		List<OrderItem> tempitem = orderitemService.getallorderitembyproduct(product);
		product.setsaleCount(tempitem.size());
		
		//获取product的评价数
		List<Review> tempreview = reviewService.getallreviewbyproduct(product);
		product.setreviewCount(tempreview.size());
		
		//获取product的分区
		Category category = categoryService.getcategorybyid(product.getCid());
		product.setCategory(category);
		
		//获取product的两种图片
		List<ProductImage> thisproductsingleimage = productimageservice.getproductSingleImage(product);
		product.setproductSingleImages(thisproductsingleimage);
		
		List<ProductImage> thisproductdetailleimage = productimageservice.getproductDetailImage(product);
		product.setproductDetailImages(thisproductdetailleimage);
		
		return product;
	}
}
