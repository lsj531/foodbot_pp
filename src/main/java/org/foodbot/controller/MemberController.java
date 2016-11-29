package org.foodbot.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.foodbot.config.Config;
import org.foodbot.domain.ChatVO;
import org.foodbot.domain.InitTrainDataVO;
import org.foodbot.domain.MLPWeightVO;
import org.foodbot.domain.MemberVO;
import org.foodbot.dto.LoginDTO;
import org.foodbot.mlp.Init;
import org.foodbot.nlp.KomoranService;
import org.foodbot.schedule.Scheduler;
import org.foodbot.service.ChatService;
import org.foodbot.service.FoodService;
import org.foodbot.service.InitTrainDataService;
import org.foodbot.service.MLPWeightService;
import org.foodbot.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/member/*")
public class MemberController {

	@Inject
	private MemberService service;
	@Inject 
	private ChatService chatService;


	@Inject
	private FoodService fservice;
	@Inject
	MLPWeightService mservice;
	@Inject
	InitTrainDataService iservice;

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public void loginPOST(@ModelAttribute("dto") LoginDTO dto, HttpSession session, Model model) throws Exception {
		logger.info("loginPOST ...");

		MemberVO vo = service.login(dto);
		List<ChatVO> chatList = (List<ChatVO>)chatService.read(dto.getUid());
		Scheduler.setUid(session.getId(),vo.getUid());
		logger.info(vo.getUid());

		if(vo == null) {
			return;
		}
		model.addAttribute("memberVO",vo);
		model.addAttribute("chatList",chatList);


		InitTrainDataVO ivo = iservice.read();
		String path = ivo.getPath();

		// user의 학습데이터정보를 추가한다.
		MLPWeightVO mvo = mservice.read(vo.getUid());
		if(mvo == null) {
			MLPWeightVO mlpVO = new MLPWeightVO();
			mlpVO.setUid(vo.getUid());
			mlpVO.setWeight_path("w_"+vo.getUid()+".txt"); 
			mlpVO.setAttribute_path("a_"+vo.getUid()+".txt");
			mlpVO.setTattribute_path("ta_"+vo.getUid()+".txt");
			mlpVO.setOutput_path("o_"+vo.getUid()+".txt");
			mlpVO.setToutput_path("to_"+vo.getUid()+".txt");
			mservice.create(mlpVO);

			// 기존 학습데이터를 삽입한다.
			Init init = new Init();
			init.loadWeight(Config.INIT_TRAIN_DATA+path);
			init.saveAttribute(Config.USER_TRAIN_WEIGHT+mlpVO.getAttribute_path()
			, init.loadAttribute(Config.INIT_TRAIN_DATA+"a_"+path));
			init.saveOutput(Config.USER_TRAIN_WEIGHT+mlpVO.getOutput_path()
			, init.loadOutput(Config.INIT_TRAIN_DATA+"o_"+path));	
			init.SaveWeight(Config.USER_TRAIN_WEIGHT+mlpVO.getWeight_path());
		}
	}

	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout( HttpSession session) throws Exception {
		logger.info("logout ...");

		session.invalidate();


		return "redirect:/";
	}

	@RequestMapping(value="/regist", method=RequestMethod.POST)
	public String regist(@ModelAttribute("vo") MemberVO vo,Model model) throws Exception {
		logger.info("regist ...");

		service.regist(vo);

		LoginDTO dto = new LoginDTO();
		dto.setUid(vo.getUid());
		dto.setUid(vo.getPassword());
		service.login(dto);

		if(vo == null) {
			return null;
		}
		model.addAttribute("memberVO",vo);

		return "redirect:/";
	}

}
