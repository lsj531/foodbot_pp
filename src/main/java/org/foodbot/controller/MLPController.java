package org.foodbot.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.foodbot.config.Config;
import org.foodbot.domain.ChatVO;
import org.foodbot.domain.FoodVO;
import org.foodbot.domain.InitTrainDataVO;
import org.foodbot.domain.MLPWeightVO;
import org.foodbot.domain.MemberVO;
import org.foodbot.mlp.Classification;
import org.foodbot.mlp.Init;
import org.foodbot.mlp.TrainDataName;
import org.foodbot.nlp.KomoranService;
import org.foodbot.nlp.MorpSeparate;
import org.foodbot.nlp.PersonalRecipe;
import org.foodbot.service.ChatService;
import org.foodbot.service.FoodService;
import org.foodbot.service.InitTrainDataService;
import org.foodbot.service.MLPWeightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.JsonObject;


/*
 * 채팅 컨트롤러 
 * - 채팅 창
 */
@Controller
@RequestMapping("/mlp/*")
public class MLPController {

	@Inject
	private FoodService fservice;
	@Inject
	KomoranService komoranService;
	@Inject
	MLPWeightService mservice;
	@Inject
	InitTrainDataService iservice;
	@Inject
	private ChatService cservice;

	private String responseMessage;

	private static final Logger logger = LoggerFactory.getLogger(MLPController.class);

	//사용자가 요청한 메시지를 분석/분류한다.
	@RequestMapping(value="/message",method=RequestMethod.POST)
	public void messagePOST(@ModelAttribute("m") String m, 
			HttpServletResponse response, HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();

		String[] msgList = m.split(":");
		String uid = msgList[0];
		String msg = msgList[1];

		// 사용자가 입력한 내용을 분석하여 결과 음식명과 코드리스트를 갖는다.
		MorpSeparate ms = new MorpSeparate(fservice,komoranService);
		ms.setComoran(msg);

		System.out.println("handleTextMessage result : ");
		for(int i= 0; i<ms.getMp().getCodeList().size(); i++) {
			System.out.print(" "+ms.getMp().getCodeList().get(i));
			System.out.print(" "+ms.getMp().getWordList().get(i));
		}
		System.out.println();

		PersonalRecipe recipe = new PersonalRecipe(fservice, ms.getMp());
		recipe.setRAttrRecipe();
		List<String> RMajorAttrList = recipe.getMajorRecipe();
		//		List<String> RSubAttrList = recipe.getSubRecipe();
		List<String> RTasteAttrList = recipe.getTasteRecipe();

		System.out.println("주재료 갯수 : "+RMajorAttrList.size() +  " 맛 갯수 "+RTasteAttrList.size());
		//		for(int i=0 ; i<RAttrList.size() ; i++) {
		//			System.out.print(RAttrList.get(i)+" ");
		//		}

		TrainDataName td = new TrainDataName(fservice);
		List<String> dataName =td.getDataName();

		double[] attr = new double[dataName.size()];

		for(int i=0 ; i<attr.length ; i++) {

			// 입력값의 가중치를 따로 입력한다.
			for(int j=0 ; j<ms.getMp().getWordList().size() ; j++) {
				if(dataName.get(i).equals(ms.getMp().getWordList().get(j))) {
					attr[i] += 0.7;
				}
			}
			// 주 재료 가중치 결정
			for(int j=0 ; j<RMajorAttrList.size() ; j++) {
				if(dataName.get(i).equals(RMajorAttrList.get(j))) {
					attr[i] += 0.0;
				}
			}
			// 부 재료 입력값 결정 을 안함 
			/*
			for(int j=0 ; j<RSubAttrList.size() ; j++) {
				if(dataName.get(i).equals(RSubAttrList.get(j))) {
					attr[i] += 0.3;
				}
			}*/
			// 맛 입력값 결정
			for(int j=0 ; j<RTasteAttrList.size() ; j++) {
				if(dataName.get(i).equals(RTasteAttrList.get(j))) {
					attr[i] += 0.1;
				}
			}
		}
		System.out.println("**************속성은" + attr.length);
		for(int i=0 ; i<attr.length ; i++) {
			System.out.print(attr[i] +" ");
		}
		System.out.println();

		/*
		 * 사용자가 검색을 한 모든 속성을
		 * 지속적으로 업데이트한다.
		 * 이 세션 객체는 사용자가 음식선택을 하면 사라져야한다.
		/*
		double[] sattr = null;
		if(session.getAttribute("attr") !=null) {
			sattr = (double[]) session.getAttribute("attr");
			for(int i=0 ; i<sattr.length ; i++) {
				sattr[i] = sattr[i] + attr[i];
			}
			session.removeAttribute("attr");
			session.setAttribute("attr", sattr);
		} else {
			session.setAttribute("attr", attr);
		}
		*/
		double[][] sattr = null;
		double[][] tattr = null;
		if(session.getAttribute("attr") !=null) {
			sattr = (double[][]) session.getAttribute("attr");
			tattr = new double[sattr.length+1][sattr[0].length];
			for(int i=0 ; i<sattr.length ; i++) {
				for(int j=0 ; j<sattr[i].length ; j++) {
					tattr[i] = sattr[i];
				}
			}
			tattr[sattr.length] = attr;
			session.removeAttribute("attr");
			session.setAttribute("attr", tattr);
		} else {
			tattr = new double[1][attr.length];
			tattr[0] = attr;
			session.setAttribute("attr", tattr);
		}


		/*
		 * Nomalization 
		/*
		MLPFunction mlpFunc = new MLPFunction();
		attr = mlpFunc.setNomalization(attr);

		System.out.println("**************속성은" + attr.length);
		for(int i=0 ; i<attr.length ; i++) {
			System.out.print(attr[i] +" ");
		}System.out.println();
		 */
		// 입력할 속성이 없다면 답장 불가능 
		boolean flag = false;
		for(int i=0 ; i<attr.length; i++) {
			if(attr[i] == 0 ) {
				flag = false;
			} else {
				flag= true;
				break;
			}
		}

		if(flag == true) {
			// user의 개인 학습데이터db 가 없으면 생성한다.
			MLPWeightVO mvo = mservice.read(uid);
			if(mvo == null) {
				// 유저가 사용할 최초의 학습데이터 경로를 불러온다.
				InitTrainDataVO ivo = iservice.read();
				String path = ivo.getPath();

				// user의 학습데이터정보를 추가한다.
				MLPWeightVO vo = new MLPWeightVO();
				vo.setUid(uid);
				vo.setWeight_path("w_"+uid+".txt");
				vo.setAttribute_path("a_"+uid+".txt");
				vo.setTattribute_path("ta_"+uid+".txt");
				vo.setOutput_path("o_"+uid+".txt");
				vo.setToutput_path("to_"+uid+".txt");
				mservice.create(vo);

				// 기존 학습데이터를 삽입한다.
				Init init = new Init();
				init.loadWeight(Config.INIT_TRAIN_DATA+path);
				init.saveAttribute(Config.USER_TRAIN_WEIGHT+vo.getAttribute_path()
				, init.loadAttribute(Config.INIT_TRAIN_DATA+"a_"+path));
				init.saveOutput(Config.USER_TRAIN_WEIGHT+vo.getOutput_path()
				, init.loadOutput(Config.INIT_TRAIN_DATA+"o_"+path));	

				int[] dataSet = {1};
//				double[][] batchInputData = {attr};
				double[] iattr = new double[attr.length];
				System.out.println("총 속성 갯수");
				for(int i=0 ; i<tattr.length ; i++) {
					for(int j =0 ;j<tattr[i].length; j++) {
						System.out.print(tattr[i][j] + " ");
					}System.out.println();
				}
				for(int i=0 ; i<tattr.length ; i++) {
					for(int j =0 ;j<tattr[i].length; j++) {
						iattr[j] += tattr[i][j];
					}
				}
				System.out.println("**************검색할 속성은" + iattr.length);
				for(int i=0 ; i<iattr.length ; i++) {
					System.out.print(iattr[i] +" ");
				}System.out.println();
				
				double[][] batchInputData = {iattr};
				new Init().initBias(1);
				Classification cf = new Classification(dataSet, batchInputData);
				// 테스트를 했으니 사용자가 이 데이터를 맘에 들어하면 온라인학습/추가저장을 실시하여야 한다.

				double[] result = cf.getResult();
				TrainDataName tdn = new TrainDataName(fservice);
				List<String> outputName = tdn.getOutputName();

				responseMessage = outputName.get((int) result[0]);

				System.out.println(result[0] + " _ "+result[1]);
				System.out.println("입력에대한 결과 음식은 "+outputName.get((int) result[0]) + " 입니다");
				System.out.println("입력에대한 결과 음식은 "+outputName.get((int) result[1]) + " 입니다");

				// 데이터가 생겼으므로 추가학습 가능표시로 업데이트 한다.
				init.SaveWeight(Config.USER_TRAIN_WEIGHT+vo.getWeight_path());

			} else {
				String path = mvo.getWeight_path();

				// 기존 학습데이터를 삽입한다.
				Init init = new Init();
				init.loadWeight(Config.USER_TRAIN_WEIGHT+path);

				
				int[] dataSet = {1};
//				double[][] batchInputData = {attr};
				double[] iattr = new double[attr.length];
				System.out.println("총 속성 갯수");
				for(int i=0 ; i<tattr.length ; i++) {
					for(int j =0 ;j<tattr[i].length; j++) {
						System.out.print(tattr[i][j] + " ");
					}System.out.println();
				}
				for(int i=0 ; i<tattr.length ; i++) {
					for(int j =0 ;j<tattr[i].length; j++) {
						iattr[j] += tattr[i][j];
					}
				}
				System.out.println("**************검색할 속성은" + iattr.length);
				for(int i=0 ; i<iattr.length ; i++) {
					System.out.print(iattr[i] +" ");
				}System.out.println();
				
				double[][] batchInputData = {iattr};
				
				new Init().initBias(1);
				Classification cf = new Classification(dataSet, batchInputData);
				double[] result = cf.getResult();
				TrainDataName tdn = new TrainDataName(fservice);
				List<String> outputName = tdn.getOutputName();

				responseMessage = outputName.get((int) result[0]);

				System.out.println(result[0] + " _ "+result[1]);
				System.out.println("입력에대한 결과 음식은 "+outputName.get((int) result[0]) + " 입니다");
				System.out.println("입력에대한 결과 음식은 "+outputName.get((int) result[1]) + " 입니다");

				init.SaveWeight(Config.USER_TRAIN_WEIGHT+mvo.getWeight_path());
			}

			List<FoodVO> fvo = fservice.readFname(responseMessage);
			String food_path =fvo.get(0).getImage_path();
			
			// server의 응답을 디비에 저장
			ChatVO chatVO = new ChatVO();
			chatVO.setUid(uid);
			chatVO.setImage_path(food_path);
			chatVO.setSender("server");
			chatVO.setContent(responseMessage);
			cservice.create(chatVO);

			//m = "{\"msg\":\""+m+"\",\"sender\":\""+"server"+"\"}";
			JsonObject obj = new JsonObject();

			obj.addProperty("msg", responseMessage);
			obj.addProperty("sender", "server");
			obj.addProperty("path", food_path);
			response.setContentType("application/x-json; charset=UTF-8");
			response.getWriter().print(obj);
		} else {
			//server의 응답을 디비에 저장
			ChatVO chatVO = new ChatVO();
			chatVO.setUid(uid);
			chatVO.setSender("server");
			chatVO.setContent("음식에 대한 언어를 입력해 주세요");
			cservice.create(chatVO);

			JsonObject obj = new JsonObject();
			obj.addProperty("msg", "음식에 대한 언어를 입력해 주세요");
			obj.addProperty("sender", "server");
			obj.addProperty("path", "null");
			response.setContentType("application/x-json; charset=UTF-8");
			response.getWriter().print(obj);
		}

	}

	/*
	 * 사용자가 음식을 선택하면
	 * 지금까지 쌓아온 선택속성과 선택된 음식을  tattr 에 저장한다
	 */
	@RequestMapping(value="/mlp",method=RequestMethod.POST)
	public void viewPaintPage(@ModelAttribute("food") String food
			,HttpServletResponse response,HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		MemberVO vo = (MemberVO) session.getAttribute("login");

		if(vo != null) {
			MLPWeightVO mlpVO = mservice.read(vo.getUid());

			if(mlpVO != null) {
				if(session.getAttribute("attr")!=null ) {
					double[][] attr = (double[][])session.getAttribute("attr");
					int[] output= new int[attr.length];
					TrainDataName tdn = new TrainDataName(fservice);
					List<String> outputName = tdn.getOutputName();
					for(int i=0 ; i<outputName.size() ; i++) {
						if(outputName.get(i).equals(food.trim())) {
							for(int j=0 ; j<attr.length ; j++)
								output[j] = i;
						}
					}

					// 입력할 속성이 없다면 답장 불가능 
					boolean flag = false;
					for(int i=0 ; i<attr.length; i++) {
						for(int j=0 ; j<attr[i].length ; j++) {
							if(attr[i][j] == 0 ) {
								flag = false;
							} else {
								flag= true;
								break;
							}
						}
					}
					if(flag == true) {
						// 데이터가 생겼으므로 추가학습 가능표시로 업데이트 한다 .
						mlpVO.setLearn_b(1);
						mservice.updateLearningB(mlpVO);
						
						Init init = new Init();
						init.saveTAttribute(Config.USER_TRAIN_WEIGHT+mlpVO.getTattribute_path(),attr);
						init.saveTOutput(Config.USER_TRAIN_WEIGHT+mlpVO.getToutput_path(), output);

						session.removeAttribute("attr");

						JsonObject obj = new JsonObject();
						obj.addProperty("msg", "success");
						response.setContentType("application/x-json; charset=UTF-8");
						response.getWriter().print(obj);
					} else {
						JsonObject obj = new JsonObject();
						obj.addProperty("msg", "fail");
						response.setContentType("application/x-json; charset=UTF-8");
						response.getWriter().print(obj);
					}
				} else {
					JsonObject obj = new JsonObject();
					obj.addProperty("msg", "fail");
					response.setContentType("application/x-json; charset=UTF-8");
					response.getWriter().print(obj);
				}
			}
		}
	}
}