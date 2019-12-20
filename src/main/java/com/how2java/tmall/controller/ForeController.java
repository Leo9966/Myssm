package com.how2java.tmall.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import comparators.*;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

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
    
    @RequestMapping("/forecheckLogin")
    @ResponseBody
    public String checkLogin(Model model,HttpSession session) {
    	User user = (User) session.getAttribute("user");
    	if(user!=null) {
    		return "success";
    	}
    	return "fail";
    }
    
    @RequestMapping("/foreloginAjax")
    @ResponseBody
    public String loginAjax(String name, String password, Model model, HttpSession session) {
    	System.out.println(name+password);
    	User user = userservice.getUserbynameandpassword(name, password);
    	
    	if(user!=null) {
            session.setAttribute("user", user);
            return "success";
    	}
    	model.addAttribute("msg", "账号密码错误");
    	return "fail";
    }
    
    @RequestMapping("/foreaddCart")
    @ResponseBody
    public String addCart(int pid, int num,HttpSession session) {
    	User user = (User) session.getAttribute("user");

    	if(user!=null) {
            List<OrderItem> ois = orderitemservice.getallcartitembyuser(user);
            boolean isfound = false;
            
            for(OrderItem oi : ois) {
            	if(oi.getPid()==pid) {
            		isfound = true;
            		oi.setNumber(oi.getNumber()+num);
            		orderitemservice.updateitem(oi);
            	}
            }
            
            if(isfound == false) {
            	OrderItem oi = new OrderItem();
            	oi.setPid(pid);
            	oi.setUid(user.getId());
            	oi.setNumber(num);
            	System.out.println("??"+oi.getId());
            	orderitemservice.additem(oi);
            	System.out.println("??"+oi.getId());//发现会自动注入获得的id
            }
            
            return "success";
    	}
    	return "fail";
    }
    
    @RequestMapping("/forebuyone")
    public String buyone(int pid, int num , HttpSession session) {
    	User user = (User) session.getAttribute("user");
    	int oiid = 0;
    	
    	if(user!=null) {
            List<OrderItem> ois = orderitemservice.getallcartitembyuser(user);
            boolean isfound = false;
            
//            for(OrderItem oi : ois) {
//            	if(oi.getPid()==pid) {
//            		isfound = true;
//            		oi.setNumber(oi.getNumber()+num);
//            		orderitemservice.updateitem(oi);
//            		oiid = oi.getId();
//            	}
//            }
//            
            if(isfound == false) {
            	OrderItem oi = new OrderItem();
            	oi.setPid(pid);
            	oi.setUid(user.getId());
            	oi.setNumber(num);
            	orderitemservice.additem(oi);
            	oiid = oi.getId();
            }
    	}
    	return "redirect:forebuy?oiid="+oiid;
    }
    
    @RequestMapping("/forebuy")
    public String buy(Model model , String[] oiid , HttpSession session) {
    	List<OrderItem> ois = new ArrayList<>();
    	float total = 0;
    	
    	for(String strid : oiid) {
    		int id = Integer.parseInt(strid);
        	OrderItem oi = orderitemservice.getitem(id);
        	total = total + oi.getProduct().getPromotePrice() * oi.getNumber();
        	ois.add(oi);
    	}
    	
    	session.setAttribute("ois", ois);
    	model.addAttribute("total", total);
    	return "fore/buy";
    }
    
    @RequestMapping("/forecreateOrder")
    public String createOrder(Model model ,Order order, HttpSession session) {
    	User user = (User) session.getAttribute("user");
    	String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);
    	
    	order.setUid(user.getId());
    	order.setOrderCode(orderCode);
    	order.setCreateDate(new Date());
    	order.setStatus(OrderService.waitPay);
    	
    	List<OrderItem> ois= (List<OrderItem>)  session.getAttribute("ois");
    	
		float total =orderservice.add(order,ois);
	    return "redirect:forealipay?oid="+order.getId() +"&total="+total;
    }
    
    @RequestMapping("forepayed")
    public String payed(int oid, float total, Model model) {
        Order order = orderservice.getorderbyid(oid);
        order.setStatus(OrderService.waitDelivery);
        order.setPayDate(new Date());
        orderservice.updateorder(order);
        model.addAttribute("o", order);
        return "fore/payed";
    }
    
    @RequestMapping("foreconfirmPay")
    public String confirmPay(int oid, Model model) {
    	Order o = orderservice.getorderbyid(oid);
    	model.addAttribute("o", o);
    	return "fore/confirmPay";
    }
    
    @RequestMapping("foreorderConfirmed")
    public String orderConfirmed(int oid) {
        Order order = orderservice.getorderbyid(oid);
        order.setStatus(OrderService.waitReview);
        order.setConfirmDate(new Date());
        orderservice.updateorder(order);
    	return "fore/orderConfirmed";
    }
    
    @RequestMapping("forereview")
    public String review(int oid,Model model) {
    	Order order = orderservice.getorderbyid(oid);
    	Product p = order.getorderItems().get(0).getProduct();
    	List<Review> reviews = reviewService.getallreviewbyproduct(p);
        model.addAttribute("p", p);
        model.addAttribute("o", order);
        model.addAttribute("reviews", reviews);
    	return "fore/review";
    }
    
    @RequestMapping("foredoreview")
    public String doreview(Model model,HttpSession session,int oid,int pid,String content ) {
		Order o = orderservice.getorderbyid(oid);
	    o.setStatus(OrderService.finish);
	    orderservice.updateorder(o);
	    
        content = HtmlUtils.htmlEscape(content);

        User user =(User)  session.getAttribute("user");
        Review review = new Review();
        review.setContent(content);
        review.setPid(pid);
        review.setCreateDate(new Date());
        review.setUid(user.getId());
        reviewService.addReview(review);
    	return "redirect:forereview?oid="+oid+"&showonly=true";
    }
    
    @RequestMapping("foresearch")
    public String search(String keyword,Model model) {
    	List<Product> ps = productService.search(keyword);
    	model.addAttribute("ps",ps);
    	return "fore/searchResult";
    }
    
    @RequestMapping("foredeleteOrder")
    @ResponseBody
    public String deleteOrder(Model model,int oid) {
    	Order o = orderservice.getorderbyid(oid);
	    o.setStatus(OrderService.delete);
	    orderservice.updateorder(o);
    	return "success";
    }
}