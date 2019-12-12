package com.how2java.tmall.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import java.util.Collections;

import comparators.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.how2java.tmall.service.*;
import com.how2java.tmall.pojo.*;

 
@Controller
@RequestMapping("")
public class ForeController {
	@Autowired
    CategoryService categoryService;
	
	@Autowired
    ProductService productService;
	
	@Autowired
	ReviewService reviewService;
	
	@Autowired
	PropertyValueService propertyvalueservice;
	
	@Autowired
	UserService userservice;
	
	@Autowired
	OrderService orderservice;
	
	@Autowired
	OrderItemService orderitemservice;
	
    @RequestMapping("/forehome")
    public String home(Model model) {
    	List<Category> cs = categoryService.list();
    	model.addAttribute("cs", cs);
    	return "fore/home";
    }
    
    @RequestMapping("/forecategory")
    public String category(int cid,String sort,Model model) {
    	Category c = categoryService.getcategorybyid(cid);
    	
    	if(sort==null) {//需要这一句，不然报错
    		sort="all";
    	}
    	
    	switch(sort) {
	    	case "all": Collections.sort(c.getProducts(),new ProductAllComparator());
	    		break;
	    	
	    	case "review":Collections.sort(c.getProducts(),new ProductReviewComparator());
	    		break;
	    		
	    	case "date":Collections.sort(c.getProducts(),new ProductDateComparator());
	    		break;
	    		
	    	case "saleCount":Collections.sort(c.getProducts(),new ProductSaleCountComparator());
	    		break;
	    		
	    	case "price":Collections.sort(c.getProducts(),new ProductPriceComparator());
	    		break;
	    	
	    	default:
    	}
    	model.addAttribute("c",c);
    	return "fore/category";
    }
    
    @RequestMapping("/foreproduct")
    public String product(int pid,Model model) {
    	Product p = productService.getproduct(pid);
		
		//获取product的评价
		List<Review> reviews = reviewService.getallreviewbyproduct(p);
		
		//获取product的属性值
		List<PropertyValue> pvs = propertyvalueservice.getPropertyValuebyProduct(p);
		
    	model.addAttribute("reviews", reviews);
        model.addAttribute("p", p);
        model.addAttribute("pvs", pvs);
    	return "fore/product";
    }
    
    @RequestMapping("/forelogin")
    public String login(String name, String password, Model model, HttpSession session) {
    	System.out.println(name+password);
    	User user = userservice.getUserbynameandpassword(name, password);
    	
    	if(user!=null) {
            session.setAttribute("user", user);
            return "redirect:forehome";
    	}
    	model.addAttribute("msg", "账号密码错误");
    	return "fore/login";
    }
    
    @RequestMapping("/forelogout")
    public String logout(Model model, HttpSession session) {
    	session.removeAttribute("user");
    	return "redirect:forehome";
    }
    
    @RequestMapping("/foreregister")
    public String register(User user,Model model) {
    	User tempuser = userservice.getUserbyname(user.getName());
    	if(tempuser!=null) {
    		model.addAttribute("msg", "该用户名已被使用！");
        	return "fore/register";
    	}
    	
    	userservice.addUser(user);
    	return "redirect:forehome";
    }
    
    @RequestMapping("/forebought")
    public String myorder(Model model,HttpSession session) {
    	User user = (User) session.getAttribute("user");
    	if(user!=null) {
    		List<Order> os = orderservice.getuserorder(user);
    		model.addAttribute("os",os);
    		return "fore/bought";
    	}
    	return null;
    }
    
    @RequestMapping("/forecart")
    public String mycart(Model model,HttpSession session) {
    	User user = (User) session.getAttribute("user");
    	if(user!=null) {
    		List<OrderItem> ois = orderitemservice.getallcartitembyuser(user);
    		model.addAttribute("ois",ois);
    		return "fore/cart";
    	}
    	return null;
    }
}