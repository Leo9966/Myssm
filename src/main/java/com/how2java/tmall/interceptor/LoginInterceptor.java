package com.how2java.tmall.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.how2java.tmall.pojo.User;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	 /** 
     * 在业务处理器处理请求之前被调用 
     * 如果返回false 
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     * 如果返回true 
     *    执行下一个拦截器,直到所有的拦截器都执行完毕 
     *    再执行被拦截的Controller 
     *    然后进入拦截器链, 
     *    从最后一个拦截器往回执行所有的postHandle() 
     *    接着再从最后一个拦截器往回执行所有的afterCompletion() 
     */   
    public boolean preHandle(HttpServletRequest request,   
            HttpServletResponse response, Object handler) throws Exception {
    	
    	HttpSession session = request.getSession();
    	String uri = request.getRequestURI();
    	System.out.println(uri);
    	
    	String isfore = uri.substring(7, 11);
    	if(isfore.equals("fore")) {
    		String method = uri.substring(11);
    		if(method.equals("home")||method.equals("checkLogin")||method.equals("register")||
    		   method.equals("loginAjax")||method.equals("login")||method.equals("product")||
    		   method.equals("category")||method.equals("search")) {
    			
    		}
    		else {
    			User user = (User) session.getAttribute("user");
    			if(user == null) {
    				response.sendRedirect("loginPage");
    				return false;//这儿如果不返回，报500错
    			}
    		}
    	}
    	
        System.out.println("preHandle(), 在访问Controller之前被调用"); 
        return true;
         
    } 
 
    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作   
     * 可在modelAndView中加入数据，比如当前时间
     */ 
     
    public void postHandle(HttpServletRequest request,   
            HttpServletResponse response, Object handler,   
            ModelAndView modelAndView) throws Exception { 
        System.out.println("postHandle(), 在访问Controller之后，访问视图之前被调用,这里可以注入一个时间到modelAndView中，用于后续视图显示");
    } 
 
    /** 
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等  
     *  
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion() 
     */
     
    public void afterCompletion(HttpServletRequest request,   
            HttpServletResponse response, Object handler, Exception ex) 
    throws Exception { 
           
        System.out.println("afterCompletion(), 在访问视图之后被调用"); 
    } 
}
