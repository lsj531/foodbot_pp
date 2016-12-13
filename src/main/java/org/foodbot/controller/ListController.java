package org.foodbot.controller;

import javax.inject.Inject;
import org.foodbot.service.FoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/list/*")
public class ListController {
	private static final Logger logger = LoggerFactory.getLogger(ListController.class);
	@Inject
	private FoodService fservice;
	
	@RequestMapping(value="/all", method=RequestMethod.GET)
	public void all() throws Exception {
		logger.info("allInfo");
	}

	@RequestMapping(value="/kr", method=RequestMethod.GET)
	public void kr() throws Exception {
		logger.info("korean");
	}
	
	@RequestMapping(value="/jp", method=RequestMethod.GET)
	public void jp() {
		logger.info("japanese");
	}
	
	@RequestMapping(value="/ch", method=RequestMethod.GET)
	public void ch() {
		logger.info("chinese");
	}
	
	@RequestMapping(value="/ws", method=RequestMethod.GET)
	public void ws() {
		logger.info("western");
	}
}
