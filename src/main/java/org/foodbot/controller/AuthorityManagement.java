package org.foodbot.controller;

import javax.servlet.http.HttpSession;

import org.foodbot.dto.LoginDTO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface AuthorityManagement {
	
	public void loginPOST(@ModelAttribute("dto") LoginDTO dto, HttpSession session, Model model) throws Exception;
	public String logout( HttpSession session) throws Exception;
}
