package org.foodbot.interceptor;



import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.foodbot.controller.HomeController;
import org.foodbot.domain.ChatVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ManagerLoginInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(ManagerLoginInterceptor.class);
	private static final String LOGIN = "manager_login";
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HttpSession session = request.getSession();
		
		ModelMap modelMap = modelAndView.getModelMap();
		Object vo = modelMap.get("managerVO");
		if(vo != null) {
			logger.info("manager login postHandle ...");
				session.setAttribute(LOGIN, vo);
				response.sendRedirect("/manage");	
				
		}
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		if(session.getAttribute(LOGIN) != null) {
			logger.info("manager login preHandle...");
			session.removeAttribute(LOGIN);
			
		}
		return true;
		
	}
}
