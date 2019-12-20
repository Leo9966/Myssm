package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;

public interface ProductService {
	List<Product> fillcategory(Category category);
	Product getproduct(int pid);
	List<Product> search(String keyword);
}
