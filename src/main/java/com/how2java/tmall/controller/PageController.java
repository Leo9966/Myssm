package com.how2java.tmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class PageController {
	
	@RequestMapping("/loginPage")
	public String login() {
		return "fore/login";
	}
	
	@RequestMapping("/registerPage")
	public String register() {
		return "fore/register";
	}
	
	@RequestMapping("/registerSuccessPage")
	public String registerSuccess() {
		return "fore/registerSuccess";
	}
}
