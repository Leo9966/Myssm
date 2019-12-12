package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductImage;

public interface ProductImageService {
	List<ProductImage> getproductImage(Product product);
	List<ProductImage> getproductSingleImage(Product product);
	List<ProductImage> getproductDetailImage(Product product);
}
