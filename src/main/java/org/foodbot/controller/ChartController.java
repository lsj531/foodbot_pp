package org.foodbot.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.foodbot.config.Config;
import org.foodbot.domain.InitTrainDataVO;
import org.foodbot.domain.MLPWeightVO;
import org.foodbot.domain.MemberVO;
import org.foodbot.domain.TasteValueVO;
import org.foodbot.mlp.Init;
import org.foodbot.mlp.TrainDataName;
import org.foodbot.service.FoodService;
import org.foodbot.service.InitTrainDataService;
import org.foodbot.service.MLPWeightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/*
 * 채팅 컨트롤러 
 * - 채팅 창
 * 
 */
@Controller
@RequestMapping("/chart/*")
public class ChartController {

	@Inject
	FoodService fservice;
	@Inject
	MLPWeightService mlpservice;
	@Inject
	InitTrainDataService iservice;

	private static final Logger logger = LoggerFactory.getLogger(ChartController.class);

	@RequestMapping(value = "/chart", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public void chartGET(@ModelAttribute("result") String result, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("chartGET get...");
		HttpSession session = request.getSession();
		MemberVO vo = (MemberVO) session.getAttribute("login");
		MLPWeightVO mlpVO = mlpservice.read(vo.getUid());

		// 유저 학습 데이터가 없으면 최초 데이터를 생성한다.
		if (mlpVO == null) {
			// 유저가 사용할 최초의 학습데이터 경로를 불러온다.
			InitTrainDataVO ivo = iservice.read();
			String path = ivo.getPath();

			// user의 학습데이터정보를 추가한다.
			MLPWeightVO mlpVO2 = new MLPWeightVO();
			mlpVO2.setUid(vo.getUid());
			mlpVO2.setWeight_path("w_" + vo.getUid() + ".txt");
			mlpVO2.setAttribute_path("a_" + vo.getUid() + ".txt");
			mlpVO2.setTattribute_path("ta_" + vo.getUid() + ".txt");
			mlpVO2.setOutput_path("o_" + vo.getUid() + ".txt");
			mlpVO2.setToutput_path("to_" + vo.getUid() + ".txt");
			mlpservice.create(mlpVO2);

			// 기존 학습데이터를 삽입한다.
			Init init = new Init();
			init.loadWeight(Config.INIT_TRAIN_DATA + path);
			init.SaveWeight(Config.USER_TRAIN_WEIGHT + mlpVO2.getWeight_path());
		}

		Init init = new Init();
		double[][] attr = init.loadAttribute(Config.USER_TRAIN_WEIGHT + mlpVO.getAttribute_path());
		int[] output = init.loadOutput(Config.USER_TRAIN_WEIGHT + mlpVO.getOutput_path());

		// 1. 뜨거운 따뜻한 / 차가운 시원한 attribute 분류 2. 해당 attr의 모든 맛 속성 분류 3. {맛, 값} 생성
		System.out.println(result);
		TrainDataName td = new TrainDataName(fservice);
		List<String> tasteName = td.getTasteName();
		List<String> dataName = td.getDataName();
		int startTasteNum = dataName.size() - tasteName.size();
		double[] sumTaste = null;
		if (result.trim().equals("hot")) {
			List<double[]> tasteList = new ArrayList<double[]>();
			int idx1 = 0;
			int idx2 = 0;
			for (int i = startTasteNum; i < dataName.size(); i++) {
				for (int j = 0; j < attr.length; j++) {
					for (int k = startTasteNum; k < attr[j].length; k++) {
						if (attr[j][k] > 0.0) {
							if (dataName.get(i).equals("뜨거운") && i == k) {
								idx1 = i;
								tasteList.add(calc1(attr[j], startTasteNum));
							}
							if (dataName.get(i).equals("따뜻한") && i == k) {
								idx2 = i;
								tasteList.add(calc1(attr[j], startTasteNum));
							}
						}
					}
				}
			}
			sumTaste = sumTaste(tasteList, idx1 - startTasteNum, idx2 - startTasteNum);

		} else if (result.trim().equals("cold")) {
			List<double[]> tasteList = new ArrayList<double[]>();
			int idx1 = 0;
			int idx2 = 0;
			for (int i = startTasteNum; i < dataName.size(); i++) {
				for (int j = 0; j < attr.length; j++) {
					for (int k = startTasteNum; k < attr[j].length; k++) {
						if (attr[j][k] > 0.0) {
							if (dataName.get(i).equals("차가운") && i == k) {
								idx1 = i;
								tasteList.add(calc1(attr[j], startTasteNum));
							}
							if (dataName.get(i).equals("시원한") && i == k) {
								idx2 = i;
								tasteList.add(calc1(attr[j], startTasteNum));
							}
						}
					}
				}
			}
			sumTaste = sumTaste(tasteList, idx1 - startTasteNum, idx2 - startTasteNum);
		}
		JsonObject obj2 = new JsonObject();
		JsonArray arr = new JsonArray();
		for (int i = 0; i < sumTaste.length; i++) {
			JsonObject obj = new JsonObject();
			obj.addProperty("label", tasteName.get(i));
			obj.addProperty("value", sumTaste[i]);
			arr.add(obj);
		}
		obj2.add("content", arr);

		response.setContentType("application/x-json; charset=UTF-8");
		response.getWriter().print(obj2);
	}

	private double[] sumTaste(List<double[]> tasteList, int idx1, int idx2) {
		double[] sum = new double[tasteList.get(0).length];
		for (int i = 0; i < tasteList.size(); i++) {
			for (int j = 0; j < tasteList.get(i).length; j++) {
				if (j == idx1 || j == idx2)
					tasteList.get(i)[j] = 0;
				sum[j] = sum[j] + tasteList.get(i)[j];
			}
		}
		return sum;
	}

	private double[] calc1(double[] attr, int startNum) {
		// for(int i=startNum ; i<attr.length ; i++)
		// System.out.print(attr[i] + " ");
		// System.out.println();
		double[] result = new double[attr.length - startNum];
		for (int i = startNum; i < attr.length; i++) {
			result[i - startNum] = attr[i];
		}
		return result;
	}

}
