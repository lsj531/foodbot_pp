package org.foodbot.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.foodbot.config.Config;
import org.foodbot.domain.FoodVO;
import org.foodbot.domain.InitTrainDataVO;
import org.foodbot.domain.MLPWeightVO;
import org.foodbot.domain.MemberVO;
import org.foodbot.mlp.Init;
import org.foodbot.mlp.InitRecipe;
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
@RequestMapping("/idle/*")
public class IdleController {

	@Inject
	FoodService fservice;	
	@Inject
	MLPWeightService mlpservice;
	@Inject
	InitTrainDataService iservice;

	private static final Logger logger = LoggerFactory.getLogger(IdleController.class);


	@RequestMapping(value="/istrain", method=RequestMethod.GET)
	public void istrainWolrdCup(@ModelAttribute("result") String result, HttpServletRequest request
								,HttpServletResponse response) throws Exception {
		logger.info("idleWolrdCup get...");

		HttpSession session = request.getSession();
		MemberVO vo = (MemberVO) session.getAttribute("login");
		MLPWeightVO mlpVO = mlpservice.read(vo.getUid());

		if(vo != null) {
			if(mlpVO.getLearn_curr() == 1) {
				JsonObject obj = new JsonObject();
				obj.addProperty("result",1);
				response.setContentType("application/x-json; charset=UTF-8");
				response.getWriter().print(obj);
			} else if(mlpVO.getLearn_curr() == 0){
				JsonObject obj = new JsonObject();
				obj.addProperty("result",0);
				response.setContentType("application/x-json; charset=UTF-8");
				response.getWriter().print(obj);
			}
		}



	}


	@RequestMapping(value="/idle", method=RequestMethod.GET,produces="text/plain;charset=UTF-8")
	public void idleWolrdCup(@ModelAttribute("result") String result, HttpServletRequest request) throws Exception {
		logger.info("idleWolrdCup get...");
		System.out.println(result);
		HttpSession session = request.getSession();
		MemberVO vo = (MemberVO) session.getAttribute("login");
		MLPWeightVO mlpVO = mlpservice.read(vo.getUid());
		if(mlpVO == null) {
			//유저가 사용할 최초의 학습데이터 경로를 불러온다.
			InitTrainDataVO ivo = iservice.read();
			String path = ivo.getPath();

			// user의 학습데이터정보를 추가한다.
			MLPWeightVO mlpVO2 = new MLPWeightVO();
			mlpVO2.setUid(vo.getUid());
			mlpVO2.setWeight_path("w_"+vo.getUid()+".txt");
			mlpVO2.setAttribute_path("a_"+vo.getUid()+".txt");
			mlpVO2.setTattribute_path("ta_"+vo.getUid()+".txt");
			mlpVO2.setOutput_path("o_"+vo.getUid()+".txt");
			mlpVO2.setToutput_path("to_"+vo.getUid()+".txt");
			mlpservice.create(mlpVO2);

			// 기존 학습데이터를 삽입한다.
			Init init = new Init();
			init.loadWeight(Config.INIT_TRAIN_DATA+path);
			init.SaveWeight(Config.USER_TRAIN_WEIGHT+mlpVO2.getWeight_path());
		} 


		String[] idleSet = result.split("\\|");
		String[][] fcodeList = new String[idleSet.length][2];
		String[] fnameList = new String[idleSet.length];

		int[] selectedFoodList = new int[idleSet.length];
		for(int i=0 ;i<idleSet.length ; i++) {
			String[] fcode = idleSet[i].split(",");
			fcodeList[i] = fcode;
		}
		System.out.println("여기");
		System.out.println(result);
		System.out.println("d이름이름");
		for(int i =0; i<fcodeList.length ; i++) {
			for(int j=0 ; j<fcodeList[i].length ; j++) {
				System.out.print(fcodeList[i][j]+ " ");
			}System.out.println();
		}
		if(!result.isEmpty()) {



			TrainDataName td = new TrainDataName(fservice);
			List<String> dataName =td.getDataName();
			List<String> dataOutputName =td.getOutputName();

			double[][] attr = new double[fcodeList.length][dataName.size()];
			InitRecipe initRecipe = null;

			List<String> RMajorAttrList;
			List<String> RSubAttrList;
			List<String> RTasteAttrList;

			
			for(int i=0; i<fcodeList.length ; i++) {

				for(int s=0 ; s<fcodeList[i].length; s++) {
					List<FoodVO> list = new ArrayList<FoodVO>();		
					list.add(fservice.readFcode(fcodeList[i][s]));
					initRecipe = new InitRecipe(list);
					RMajorAttrList = initRecipe.getMajorRecipe();
					RSubAttrList = initRecipe.getSubRecipe();
					RTasteAttrList = initRecipe.getTasteRecipe();
					System.out.println("tt");
					for(int j=0 ; j<RMajorAttrList.size() ; j++) {
						System.out.print(RMajorAttrList.get(j) +" ");
					}
					for(int j=0 ; j<RTasteAttrList.size() ; j++) {
						System.out.print(RTasteAttrList.get(j) +" ");
					}System.out.println();

					// 이상형월드컵 선택된 음식은 가중치 업
					if(s==0) {

						// 이상형월드컵 결과 코드에 대한 음식명을 찾아온다
						for(int n=0 ; n<initRecipe.getRFoodNameList().size(); n++) {
							fnameList[i] = initRecipe.getRFoodNameList().get(n);
						}

						for(int j=0 ; j<attr[i].length ; j++) {
							// 주 재료 가중치 결정
							for(int k=0 ; k<RMajorAttrList.size() ; k++) {
								if(dataName.get(j).equals(RMajorAttrList.get(k))) {
									attr[i][j] += 0.7;
								}
							}
							// 부 재료 입력값 결정 
							for(int k=0 ; k<RSubAttrList.size() ; k++) {
								if(dataName.get(j).equals(RSubAttrList.get(k))) {
									attr[i][j] += 0.3;
								}
							}
							// 맛 입력값 결정
							for(int k=0 ; k<RTasteAttrList.size() ; k++) {
								if(dataName.get(j).equals(RTasteAttrList.get(k))) {
									attr[i][j] += 0.8;
								}
							}
						}
						for(int j=0 ; j<dataName.size() ; j++) {
							System.out.print(dataName.get(j)+" ");
						}
						System.out.println("속성");
						for(int j=0 ; j<attr.length ; j++) {
							for(int k=0 ; k<attr[j].length ; k++) {
								System.out.print(attr[j][k]+ " ");
							}System.out.println();
						}

						// 선택 안된 음식은 가중치 다운
					} else if(s ==1) {
						for(int j=0 ; j<attr.length ; j++) {
							// 주 재료 가중치 결정
							for(int k=0 ; k<RMajorAttrList.size() ; k++) {
								if(dataName.get(j).equals(RMajorAttrList.get(k))) {
									attr[i][j] -= 0.35;
								}
							}
							// 부 재료 입력값 결정 
							for(int k=0 ; k<RSubAttrList.size() ; k++) {
								if(dataName.get(j).equals(RSubAttrList.get(k))) {
									attr[i][j] -= 0.15;
								}
							}
							// 맛 입력값 결정
							for(int k=0 ; k<RTasteAttrList.size() ; k++) {
								if(dataName.get(j).equals(RTasteAttrList.get(k))) {
									attr[i][j] -= 0.4;
								}
							}
						}
					}
				}

			}
			// 선택된 이미지를 output num으로 치환해준다.
			for(int i=0 ; i<dataOutputName.size(); i++) {
				for(int j=0 ;j<fnameList.length ; j++) {
					if(dataOutputName.get(i).equals(fnameList[j])) {
						selectedFoodList[j] = i; 
					}
				}
			}

			System.out.println("이상형 속성");
			for(int i=0 ; i<attr.length ; i++) {
				for(int j=0  ; j<attr[i].length ;j++) {
					System.out.print(attr[i][j] + " ");
				}System.out.println();
			}
			Init init = new Init();

			init.saveTAttribute(Config.USER_TRAIN_WEIGHT+mlpVO.getTattribute_path(), attr);
			init.saveTOutput(Config.USER_TRAIN_WEIGHT+mlpVO.getToutput_path(), selectedFoodList);
			System.out.println("이상형 월드컵 결과 저장 완료");

			mlpVO.setLearn_b(1);
			mlpVO.setLearn_curr(0);
			mlpservice.updateLearningB(mlpVO);
			System.out.println("추가학습 가능");
		}

	}

}
