package org.foodbot.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.foodbot.config.Config;
import org.foodbot.domain.MLPWeightVO;
import org.foodbot.listener.CountManager;
import org.foodbot.mlp.Init;
import org.foodbot.mlp.InitTrainData;
import org.foodbot.mlp.NameToValue;
import org.foodbot.mlp.OneLineLearning;
import org.foodbot.service.FoodService;
import org.foodbot.service.MLPWeightService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

	@Inject
	MLPWeightService mservice;
	@Inject 
	FoodService fservice;

	//기존 user list
	private static Map<String,String> uidList;
	//현재 user list
	private static Map<String,String> curruidList;
	//현재 session list - CountManager에서 현재 접속중인 session list 를 가져온다.
	private List<String> currSessionList;

	private Thread myThread;

	public Scheduler(){
		uidList = new HashMap<String,String>();
		curruidList = new HashMap<String,String>();
		currSessionList = new ArrayList<String>();
	}
	@Scheduled(cron="*/5 * * * * *")
	public void cronTest() throws Exception{
		currSessionList = CountManager.sessionList;

		Set key = uidList.keySet();
		for(int i=0 ; i<currSessionList.size() ; i++) {
			for (Iterator iterator = key.iterator(); iterator.hasNext();) {
				String keyName = (String) iterator.next();
				if(currSessionList.get(i).equals(keyName)) {
					System.out.println("현재 유저는!!! "+ uidList.get(keyName));
					curruidList.put(keyName, uidList.get(keyName));
					MLPWeightVO vo = mservice.read(uidList.get(keyName));
					if(vo != null && vo.getWeight_path() != null) {
						// 1이 가능한 상태임
						if(vo.getLearn_b() == 1 && vo.getLearn_curr() == 0 ) {
							myThread = new Thread(new ThreadRunnable(vo,mservice,fservice));
							myThread.start();
						}
					}
				}
			}
		}
	}

	public static void setUid(String sessionId,String uid) {
		uidList.put(sessionId,uid);
	}
}

class ThreadRunnable implements Runnable {
	private MLPWeightVO vo;
	private double[][] tattr;
	private int[] toutput;
	private MLPWeightService mservice;
	private FoodService fservice;
	private Init init = new Init();
	private OneLineLearning oll;
	
	ThreadRunnable(MLPWeightVO vo,MLPWeightService mservice, FoodService fservice) throws Exception {
		this.vo = vo;
		this.mservice = mservice;
		this.fservice = fservice;
		
		// 학습중이라는 것을 DB에 명시함
		vo.setLearn_b(0);
		mservice.updateLearningB(vo);
		vo.setLearn_curr(1); // 학습 중인 상태
		mservice.updateLearningCURR(vo);
		// 기존 학습데이터를 삽입한다.
		init.loadWeight(Config.USER_TRAIN_WEIGHT+vo.getWeight_path());
		tattr = init.loadTAttribute(Config.USER_TRAIN_WEIGHT+vo.getTattribute_path());
		toutput = init.loadTOutput(Config.USER_TRAIN_WEIGHT+vo.getToutput_path());
	}
	@Override
	public void run() {
		System.out.println("추가 학습 완료 "); // 학습완료라는 것을 DB에 명시함
		try {
			// 학습한 weight 저장 
			init.SaveWeight(Config.USER_TRAIN_WEIGHT+vo.getWeight_path());
			// 학습한 tmp 데이터 삭제
			init.deleteTAttribute(Config.USER_TRAIN_WEIGHT+vo.getTattribute_path());
			init.deleteTOutput(Config.USER_TRAIN_WEIGHT+vo.getToutput_path());
			// 학습한 tmp 를 attribute 파일에 추가 저장
			init.saveAttribute(Config.USER_TRAIN_WEIGHT+vo.getAttribute_path(), tattr);
			init.saveOutput(Config.USER_TRAIN_WEIGHT+vo.getOutput_path(), toutput);
			
			// 온라인 러닝시에 추가학습후 전체학습을 다시 
			tattr = init.loadAttribute(Config.USER_TRAIN_WEIGHT+vo.getAttribute_path());
			toutput = init.loadOutput(Config.USER_TRAIN_WEIGHT+vo.getOutput_path());
			System.out.println("전체 학습 데이터 갯수 " + tattr.length + " " + toutput.length);
			
			oll = new OneLineLearning(tattr,toutput,false,1000);
			System.out.println("추가 전체 학습 완료 ");
			
			// 학습이 끝난 것을 DB에 명시함
			vo.setLearn_curr(0);// 학습이 끝난 상태
			mservice.updateLearningCURR(vo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}