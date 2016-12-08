package org.foodbot.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.foodbot.config.Config;
import org.foodbot.domain.FoodVO;
import org.foodbot.domain.IngredVO;
import org.foodbot.domain.InitTrainDataVO;
import org.foodbot.domain.ManagerVO;
import org.foodbot.domain.MorpVO;
import org.foodbot.domain.TasteVO;
import org.foodbot.dto.LoginDTO;
import org.foodbot.mlp.HyperParameter;
import org.foodbot.mlp.InitTrain;
import org.foodbot.mlp.InitTrainData;
import org.foodbot.service.FoodService;
import org.foodbot.service.InitTrainDataService;
import org.foodbot.service.ManagerService;
import org.foodbot.time.FTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/*
 * 관리자 전용
 * 초기 DB 음식 데이터  처리 컨트롤러
 */
@Controller
@RequestMapping("/manage/")
public class ProjectManagerController implements AuthorityManagement {
	private static final Logger logger = LoggerFactory.getLogger(ProjectManagerController.class);

	@Inject
	private FoodService service;
	@Inject
	private ManagerService mService;
	@Inject
	private InitTrainDataService tService;

	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public void insertPageGET() throws Exception {
		logger.info("insertPageGET get ...");
	}

	@RequestMapping(value = "/ingred", method = RequestMethod.GET)
	public void insertIngredGET() throws Exception {
		logger.info("insertIngred get ...");
	}

	@RequestMapping(value = "/ingred", method = RequestMethod.POST)
	public void insertIngredPOST(@ModelAttribute("ingred") String str) throws Exception {
		logger.info("insertIngred POST ...");

		IngredVO in = null;
		List<IngredVO> ingredList = new ArrayList<IngredVO>();
		String[] k = str.split("\n");
		for (int i = 0; i < k.length; i++) {
			String[] k2 = k[i].split("\t");
			in = new IngredVO();
			in.setIngred(k2[0]);
			in.setIngred_code(k2[1]);
			ingredList.add(in);
			in = null;

		}
		System.out.println("재료 갯수 : " + k.length);

		for (int i = 0; i < ingredList.size(); i++) {
			service.createIngred(ingredList.get(i));
			// System.out.println(ingredList.get(i).getIngred() +"
			// "+ingredList.get(i).getIngred_code() );
		}
	}

	@RequestMapping(value = "/food", method = RequestMethod.GET)
	public void insertFoodGET() throws Exception {
		logger.info("insertFoodGET get ...");
	}

	@RequestMapping(value = "/food", method = RequestMethod.POST)
	public void insertFoodPOST(@ModelAttribute("food") String str) throws Exception {
		logger.info("insertFoodPOST POST ...");

		FoodVO in = null;
		List<FoodVO> foodList = new ArrayList<FoodVO>();
		String[] k = str.split("\n");
		for (int i = 0; i < k.length; i++) {
			String[] k2 = k[i].split("\t");
			in = new FoodVO();
			in.setFname(k2[0]);
			in.setFcode(k2[1]);
			in.setIngredset(k2[2]);
			in.setTasteset(k2[3]);
			in.setImage_path(k2[4]);
			foodList.add(in);
			in = null;

		}
		System.out.println("음식 갯수 : " + k.length);

		for (int i = 0; i < foodList.size(); i++) {
			service.create(foodList.get(i));
			// System.out.println(foodList.get(i).getFcode() +"
			// "+foodList.get(i).getFname() + foodList.get(i).getIngredset());
		}
	}

	@RequestMapping(value = "/morp", method = RequestMethod.GET)
	public void insertMorpGET() throws Exception {
		logger.info("insertMorpGET get ...");
	}

	@RequestMapping(value = "/morp", method = RequestMethod.POST)
	public void insertMorpPOST(@ModelAttribute("morp") String str) throws Exception {
		logger.info("insertMorpPOST POST ...");

		MorpVO in = null;
		List<MorpVO> morpList = new ArrayList<MorpVO>();
		String[] k = str.split("\n");
		for (int i = 0; i < k.length; i++) {
			String[] k2 = k[i].split("\t");
			in = new MorpVO();
			in.setMorp(k2[0]);
			in.setMorp_code(k2[1]);
			morpList.add(in);
			in = null;

		}
		System.out.println("형용사 갯수 : " + k.length);

		for (int i = 0; i < morpList.size(); i++) {
			service.createMorp(morpList.get(i));
			// System.out.println(foodList.get(i).getFcode() +"
			// "+foodList.get(i).getFname() + foodList.get(i).getIngredset());

		}
	}

	@RequestMapping(value = "/taste", method = RequestMethod.GET)
	public void insertTasteGET() throws Exception {
		logger.info("insertTasteGET get ...");
	}

	@RequestMapping(value = "/taste", method = RequestMethod.POST)
	public void insertTastePOST(@ModelAttribute("taste") String str) throws Exception {
		logger.info("insertTastePOST POST ...");

		TasteVO in = null;
		List<TasteVO> tasteList = new ArrayList<TasteVO>();
		String[] k = str.split("\n");
		for (int i = 0; i < k.length; i++) {
			String[] k2 = k[i].split("\t");
			in = new TasteVO();
			in.setTaste(k2[0]);
			in.setTaste_code(k2[1]);
			tasteList.add(in);
			in = null;

		}
		System.out.println("맛 갯수 : " + k.length);

		for (int i = 0; i < tasteList.size(); i++) {
			service.createTaste(tasteList.get(i));
			// System.out.println(foodList.get(i).getFcode() +"
			// "+foodList.get(i).getFname() + foodList.get(i).getIngredset());

		}
	}

	@RequestMapping(value = "/online", method = RequestMethod.GET)
	public void onlineGET() throws Exception {
		logger.info("onlineGET get ...");
	}

	@RequestMapping(value = "/train", method = RequestMethod.GET)
	public void insertTrainGET() throws Exception {
		logger.info("insertTrainGET get ...");
	}

	@RequestMapping(value = "/trainCost", method = RequestMethod.GET)
	public void TrainCostGET(HttpServletResponse response) throws Exception {
		logger.info("TrainCostGET get ...");
		JsonObject obj = new JsonObject();
		JsonObject obj2 = new JsonObject();
		JsonArray arr = new JsonArray();
		obj.addProperty("cost", A.get100cost());
		obj.addProperty("epoch", A.getEpoch());
		arr.add(obj);
		obj2.add("set", arr);
		response.setContentType("application/x-json; charset=UTF-8");

		response.getWriter().print(obj2);
	}

	@RequestMapping(value = "/train", method = RequestMethod.POST)
	public void insertTrainPOST(@ModelAttribute("train") InitTrainDataVO idata) throws Exception {
		logger.info("insertTrainPOST POST ...");
		new A(service);
		// InitTrain it = new InitTrain(service);

		System.out.println("학습 완료..");

		// data insert
		tService.create(idata);
		InitTrainDataVO itd = tService.read();
		// path insert
		itd.setPath(itd.getTid() + ".txt");
		tService.update(itd);

		// trained data save
		A.getIt().SaveWeight(Config.INIT_TRAIN_DATA + itd.getPath());
		A.getIt().saveAttribute(Config.INIT_TRAIN_DATA + "a_" + itd.getPath(), A.getIt().getTrainInputData());
		A.getIt().saveOutput(Config.INIT_TRAIN_DATA + "o_" + itd.getPath(), A.getIt().getTrainOutput());

	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String ManageHomeGET() throws Exception {
		logger.info("ManageHomeGET get ...");
		return "manage/home";
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public void ManageHomePOST() throws Exception {
		logger.info("ManageHomePOST POST ...");
	}

	@Override
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void loginPOST(LoginDTO dto, HttpSession session, Model model) throws Exception {
		logger.info("loginPOST POST ...");

		ManagerVO vo = mService.login(dto);
		logger.info(vo.getUid() + "  " + vo.getUid());

		if (vo == null) {
			logger.info(" manager vo 를 못받음");
			return;
		}
		model.addAttribute("managerVO", vo);
	}

	@Override
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(HttpSession session) throws Exception {
		logger.info("logout POST ...");
		return null;
	}
}

// 임시 학습 클래스
class A {
	private static FoodService service;
	private static double cost;
	private static double epoch;
	private static List<Double> epochList;
	private static InitTrain it;

	public A(FoodService service) throws Exception {
		this.service = service;
		epochList = new ArrayList<Double>();

		it = new InitTrain(service);

		FTime.startTime();
		for (int j = 0; j <= HyperParameter.EPOCH; j++) {
			it.setBatch();
			it.calcNet();
			epoch = j;
			// System.out.println("epoch = " + j );
			if (epoch % 100 == 0) {
				epochList.add(it.cost());
			}
			cost = it.cost();
			// System.out.println(j + "th cost is "+it.cost());
		}

		FTime.endTime();
		System.out.println("학습 시간은 : " + FTime.getStart2EndTime());
		it.displayResults();
	}

	public static InitTrain getIt() {
		return it;
	}

	public static double getCost() {
		return cost;
	}

	public static double getEpoch() {
		return epoch;
	}

	public static double get100cost() {
		double cost = 0.0;
		if (epochList.size() == 0) {
			cost = -1;
		} else {
			cost = epochList.remove(0);
		}
		return cost;
	}
}
