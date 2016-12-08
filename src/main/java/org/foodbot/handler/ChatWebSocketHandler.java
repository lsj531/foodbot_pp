package org.foodbot.handler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.foodbot.domain.ChatVO;
import org.foodbot.mlp.TrainDataName;
import org.foodbot.nlp.KomoranService;
import org.foodbot.nlp.MorpSeparate;
import org.foodbot.nlp.PersonalRecipe;
import org.foodbot.service.ChatService;
import org.foodbot.service.FoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatWebSocketHandler extends TextWebSocketHandler{

	private Logger logger = LoggerFactory.getLogger(ChatWebSocketHandler.class);
	private Map<String, WebSocketSession> users = new ConcurrentHashMap<String, WebSocketSession>();
	private Map<String,String> session2id = new ConcurrentHashMap<String, String>();

	private ChatVO vo;
	
	@Inject
	ChatService service;
	
	@Inject
	FoodService fservice;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("afterConnectionEstablished 연결되었습니다.");
//		users.put(session.getId(), session);
//		System.out.println("  총 인원 : " + users.size());
		
//		TrainData td = new TrainData(fservice);
		
//        Map<String, Object> map = session.getAttributes();
//        MemberVO memberVO = (MemberVO) map.get("user");
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		logger.info(session.getId() + "연결 종료됨");

		for(WebSocketSession s : users.values()) {
			// map 의 값중에서 id와 요청된 session의 id 값을 비교 ... 
			if(s.getId().equals(session.getId())) {
				
				users.remove(session.getId());
				session2id.remove(session.getId());
				
				System.out.println("  총 인원 : " + users.size() + " " + session2id.size());
				System.out.println("연결 종료");
				return;
			}
		}

	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("message : "+message);
		logger.info(session.getId() + "로부터 메시지 수신 : "+ message.getPayload());

		String[] splitStr = message.getPayload().split(":");
		String userId = splitStr[1];
		String content = splitStr[2];
		
		
		// 위의 재료와 디비재료를 모두 비교하여 mlp의 순서와 값을 정하고 메시지로 내려보낸다
		// 그 후 에이젝스로 테스트를 요청한다...
		for(String str : session2id.values()) {
			// map 의 값중에서 id와 요청된 session의 id 값을 비교 ... 
			if(str.equals(userId)) {
				for(WebSocketSession s : users.values()) {
					// map 의 값중에서 id와 요청된 session의 id 값을 비교 ... 
					if(s.getId().equals(session.getId())) {
						s.sendMessage(message);
						vo = new ChatVO();
						vo.setUid(userId);
						vo.setSender(userId);
						vo.setContent(content);
						service.create(vo);
						
						System.out.println("  총 인원 : " + users.size() + " " + session2id.size());
						return;
					}
				}
			}
		}
		
		users.put(session.getId(), session);
		session2id.put(session.getId(), splitStr[1]);
		
		System.out.println("  총 인원 : " + users.size()+ " "+session2id.size());

		session.sendMessage(message);
		vo = new ChatVO();
		vo.setUid(userId);
		vo.setSender(userId);
		vo.setContent(content);
		service.create(vo);
		
	
	}
	

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		logger.info(session.getId() + "익셉션 발생 " + exception.getMessage());
	}
}
