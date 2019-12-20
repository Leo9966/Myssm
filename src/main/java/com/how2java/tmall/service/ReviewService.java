package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.Review;

public interface ReviewService {
	List<Review> getallreviewbyproduct(Product product);
	void addReview(Review review);
}
