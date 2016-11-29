package org.foodbot.controller;

import javax.inject.Inject;

import org.foodbot.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/myinfo/*")
public class MyInfoController {
	private static final Logger logger = LoggerFactory.getLogger(MyInfoController.class);
	@Inject
	private BoardService service;
	
	@RequestMapping(value="/info", method=RequestMethod.GET)
	public void myInfo() throws Exception {
		logger.info("allInfo get ...");
	}

	@RequestMapping(value="/subinfo", method=RequestMethod.GET)
	public void subInfo() throws Exception {
		logger.info("subInfo");
	}
	
	@RequestMapping(value="/chart", method=RequestMethod.GET)
	public void chart() {
		logger.info("chart");
	}
	
	@RequestMapping(value="/idle", method=RequestMethod.GET)
	public void idle() {
		logger.info("idle worldcup");
	}
}
