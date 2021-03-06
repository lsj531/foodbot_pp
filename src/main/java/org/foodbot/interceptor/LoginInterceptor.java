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

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	private static final String LOGIN = "login";
	private static final String CHAT = "chat";
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		HttpSession session = request.getSession();
		
		ModelMap modelMap = modelAndView.getModelMap();
		Object memberVO = modelMap.get("memberVO");
		List<ChatVO> chatList = (List<ChatVO>) modelMap.get("chatList");
//		for(int i=0 ; i<chatList.size() ; i++) {
//			System.out.println(chatList.get(i).getContent());
//		}
		if(memberVO != null) {
			logger.info("login postHandle ...");
				session.setAttribute(LOGIN, memberVO);
				session.setAttribute(CHAT, chatList);
				response.sendRedirect("/");	
				
		}
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		if(session.getAttribute(LOGIN) != null) {
			logger.info("login preHandle...");
			session.removeAttribute(LOGIN);
			
		}
		return true;
		
	}
}
