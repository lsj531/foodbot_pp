package org.foodbot.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class CountManager implements HttpSessionListener {
    public static int count;
    public static List<String> sessionList =  new ArrayList<String>();
    
    public CountManager() {
    }
    public static int getCount() {
        return count;
    }
 
    public void sessionCreated(HttpSessionEvent event) {
        //세션이 만들어질 때 호출
        HttpSession session = event.getSession(); //request에서 얻는 session과 동일한 객체
        
        // 세션 최대 만료시간 (단위 초) 시간 안에 이벤트 없으면 세션 자동 종료
        session.setMaxInactiveInterval(60*10);
        sessionList.add(session.getId());
        count++;
        
        session.getServletContext().log(session.getId() + " 세션생성 " + ", 접속자수 : " + count);
    }
 
    public void sessionDestroyed(HttpSessionEvent event) {
        //세션이 소멸될 때 호출
    	HttpSession session = event.getSession();
    	for(int i=0 ; i<sessionList.size() ; i++) {
    		if(sessionList.get(i).equals(session.getId())) {
    			sessionList.remove(i);
    			break;
    		}
    	}
        count--;
        if(count<0)
            count=0;
        
        session.getServletContext().log(session.getId() + " 세션소멸 " + ", 접속자수 : " + count);
    }
}
