package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.ReviewMapper;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.Review;
import com.how2java.tmall.pojo.ReviewExample;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.ReviewService;
import com.how2java.tmall.service.UserService;

@Service
public class ReviewServiceimpl implements ReviewService{
	
	@Autowired
	ReviewMapper reviewmapper;
	
	ReviewExample reviewexample = new ReviewExample();
	
	@Autowired
	UserService userservice;
	
	public List<Review> getallreviewbyproduct(Product product){
		reviewexample.clear();
		ReviewExample.Criteria cri4 = reviewexample.createCriteria();
		cri4.andPidEqualTo(product.getId());
		List<Review> tempreview = reviewmapper.selectByExample(reviewexample);
		
		for(Review re : tempreview) {
			User user = userservice.getUserbyid(re.getUid());
			re.setUser(user);
		}
		
		return tempreview;
	}
	
	public void addReview(Review review){
		reviewmapper.insert(review);
	}
}
