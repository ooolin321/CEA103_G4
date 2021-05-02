package com.util.notifyWs;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.Follow.model.*;
import com.google.gson.Gson;



@ServerEndpoint("/SubscribeNotifyWs/{memId}")
public class SubscribeNotifyWs {
	private static final Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
	Gson gson = new Gson();
	
	@OnOpen
	public void onOpen(@PathParam("memId") String memId, Session userSession) throws IOException {
		sessionsMap.put(memId, userSession);
		Set<String> members = sessionsMap.keySet();
		
		System.out.println("members : " + members);
		
	}
	
	@OnMessage
	public void onMessage(Session userSession, String message) {
		AddFaNotify Notify = gson.fromJson(message, AddFaNotify.class);
		if(Notify.getType().equals("addFaNotify")) {
			notifyAddFa(Notify,message);
		}else {
			beSubscribeNotify(Notify,message);
		}
//		String amemId = addFaNotify.getMemId();
//		FollowService folSvc = new FollowService();
//		List<FollowVO> list = folSvc.getFollowList(amemId);
//		for(FollowVO followVO : list) {
//			System.out.println("reveivers :" + followVO.getMemId());
//			Session receiverSession = sessionsMap.get(followVO.getMemId());
//			if(receiverSession != null && receiverSession.isOpen()) {
//				receiverSession.getAsyncRemote().sendText(message);
//			}
//		}
//		System.out.println("Message received: " + message);
	}
	
	@OnClose
	public void onClose(@PathParam("memId")String memId,Session userSession, CloseReason reason) {
		sessionsMap.remove(memId);
	}
	
	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}
	
	public void notifyAddFa(AddFaNotify addFaNotify,String message) {
		String amemId = addFaNotify.getMemId();
		FollowService folSvc = new FollowService();
		List<FollowVO> list = folSvc.getFollowList(amemId);
		for(FollowVO followVO : list) {
			System.out.println("reveivers :" + followVO.getMemId());
			Session receiverSession = sessionsMap.get(followVO.getMemId());
			if(receiverSession != null && receiverSession.isOpen()) {
				receiverSession.getAsyncRemote().sendText(message);
			}
		}
		System.out.println("Message received: " + message);
	}
	
	public void beSubscribeNotify(AddFaNotify addFaNotify,String message) {
		String amemId = addFaNotify.getMemId();
		Session authorSession = sessionsMap.get(amemId);
		if(authorSession != null && authorSession.isOpen()) {
			authorSession.getAsyncRemote().sendText(message);
		}
		System.out.println("Author receives message :" + message);
	}
}
