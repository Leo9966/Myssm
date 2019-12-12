package com.how2java.tmall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.pojo.*;
import com.how2java.tmall.service.*;
import com.how2java.tmall.mapper.*;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	CategoryMapper categorymapper;
	
	CategoryExample categoryexample = new CategoryExample();
	
	//用到的Service
	@Autowired
    ProductService productService;
	
	@Autowired
    ProductImageService productimageService;
	
	@Autowired
    OrderItemService orderitemService;
	
	@Autowired
    ReviewService reviewService;
	
	public List<Category> list(){
		List<Category> cs = categorymapper.selectByExample(categoryexample);
		for (Category category : cs){
			List<Product> products = productService.fillcategory(category);
			
			for (Product product : products) {
				List<ProductImage> tempimage = productimageService.getproductImage(product);
				product.setFirstProductImage(tempimage.get(0));
			}
			
			category.setProducts(products);
			
            List<List<Product>> productsByRow =  new ArrayList<>();
            for (int i = 0; i < products.size(); i+=8) {
                int size = i+8;
                size= size>products.size()?products.size():size;
                List<Product> productsOfEachRow =products.subList(i, size);
                productsByRow.add(productsOfEachRow);
            }
            category.setProductsByRow(productsByRow);
		}
		return cs;
	}
	
	public Category getcategorybyid(int id) {
		Category category = categorymapper.selectByPrimaryKey(id);
		
		List<Product> products = productService.fillcategory(category);
		
		for (Product product : products) {
			List<ProductImage> tempimage = productimageService.getproductImage(product);
			product.setFirstProductImage(tempimage.get(0));
			
			//获取product的订单数
			List<OrderItem> tempitem = orderitemService.getallorderitembyproduct(product);
			product.setsaleCount(tempitem.size());
			
			//获取product的评价数
			List<Review> tempreview = reviewService.getallreviewbyproduct(product);
			product.setreviewCount(tempreview.size());
		}
		
		category.setProducts(products);
		
        List<List<Product>> productsByRow =  new ArrayList<>();
        for (int i = 0; i < products.size(); i+=8) {
            int size = i+8;
            size= size>products.size()?products.size():size;
            List<Product> productsOfEachRow =products.subList(i, size);
            productsByRow.add(productsOfEachRow);
        }
        category.setProductsByRow(productsByRow);
        
		return category;
	}
}
