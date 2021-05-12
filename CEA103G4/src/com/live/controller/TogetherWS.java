package com.live.controller;

import java.io.IOException;
import java.util.Collection;
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

import com.google.gson.Gson;
import com.liveBid.websocket.jedis.JedisHandleBid;
import com.liveBid.websocket.model.MaxVO;
import com.liveBid.websocket.model.State;
import com.liveBid.websocket.model.bidVO;

@ServerEndpoint("/TogetherWS/{userName}/{live_no}")
public class TogetherWS {
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
	Gson gson = new Gson();

	@OnOpen
	public void onOpen(@PathParam("userName") String userName, @PathParam("live_no") String live_no,
			Session userSession) throws IOException {
		System.out.println("AGDSFS"+userName);
		/* save the new user in the map */
		sessionsMap.put(userName, userSession);
		/* Sends all the connected users to the new user */
		Set<String> userNames = sessionsMap.keySet();
//		System.out.println("SSSS"+live_no);
//		System.out.println("SSSS"+userName);
		State stateMessage = new State("open", userName, userNames);

		String stateMessageJson = gson.toJson(stateMessage);

		Collection<Session> sessions = sessionsMap.values();
		for (Session session : sessions) {
			if (session.isOpen()) {
				session.getAsyncRemote().sendText(stateMessageJson);
			}
		}

		String text = String.format("Session ID = %s, connected; userName = %s%nusers: %s", userSession.getId(),
				userName, userNames);
		System.out.println(text);
	}

	@OnMessage
	public void onMessage(Session userSession, String message) {
//		message過來
//		1.history 拿最新資料 bidVO =>bidVO
//		2.getMax裡面包max      更新最高價格   bidVO 轉成maxVO 回傳maxVO 
//		3.chat    直接轉交  bidVO =>
		
		
		
		bidVO chatMessage = gson.fromJson(message, bidVO.class);
		
		String live_no = chatMessage.getLive_no();
		String type = chatMessage.getType();
		String sender = chatMessage.getSender();
		String product_no = chatMessage.getProduct_no();
		System.out.println("judge" + message);
		if ("history".equals(type)) {
			//抓取最高價格MaxVO
			String historyData = JedisHandleBid.getMaxPrice(live_no, product_no);
			System.out.println("FFFF"+historyData);
			//historyData=null要判斷
			if(historyData==null) {
				MaxVO max0 = new MaxVO("bid", sender, live_no, "", "0", product_no, "", "");
				String max0S = gson.toJson(max0);
				bidVO bid = new bidVO("history",sender,live_no,product_no,max0S);
				String currentBid = gson.toJson(bid);
				if (userSession != null && userSession.isOpen()) {
					userSession.getAsyncRemote().sendText(currentBid);
					return;
				}
			}else {
				if (userSession != null && userSession.isOpen()) {
					bidVO bid = new bidVO("history",sender,live_no,product_no,historyData);
					String currentBid = gson.toJson(bid);
					userSession.getAsyncRemote().sendText(currentBid);
					return;
				}
			}
			
		}
		
		
		Set<String> others = sessionsMap.keySet();

		System.out.println("I coming" + message);
		if ("getMax".equals(type)) {
			//64有包裝成BIDVO
			System.out.println("I coming222" + chatMessage.getMessage());
			MaxVO max = gson.fromJson(chatMessage.getMessage(), MaxVO.class);
			String finalMax = null;
			//我拿到前面傳來的maxJSON
			if(max.getTimeStart().equals("0")) {
				//直接存進rd
				JedisHandleBid.saveMaxPrice(max.getLive_no(), max.getProduct_no(), chatMessage.getMessage());
				finalMax = chatMessage.getMessage();
			}else if(max.getTimeStart().equals("1")) {
				//比較大小 存進rd
				String presentMax = JedisHandleBid.getMaxPrice(max.getLive_no(), max.getProduct_no());
				MaxVO presentMaxVO = gson.fromJson(presentMax, MaxVO.class);
				if (presentMaxVO == null) {//如果最大值空的
					System.out.println("不用回傳!!還沒競標開始");
					MaxVO bye = new MaxVO("max", sender, live_no, "", "0", product_no, "3", "");
					finalMax = gson.toJson(bye);
				} else {
					if ((Integer.parseInt(presentMaxVO.getMaxPrice()) < Integer.parseInt(max.getMaxPrice()))) {
						JedisHandleBid.saveMaxPrice(max.getLive_no(), max.getProduct_no(), chatMessage.getMessage());
					}
					finalMax = chatMessage.getMessage();//之後上面改寫 這行要移到else以外
				}
			}else if(max.getTimeStart().equals("2")) {
				//不用  
				finalMax = chatMessage.getMessage();
			}
			for (String other : others) {
				Session receiverSession = sessionsMap.get(other);
				if (userSession != null && userSession.isOpen()) {
					receiverSession.getAsyncRemote().sendText(finalMax);
				}
			}
			
			
		} else {
			for(String other : others) {
				Session receiverSession = sessionsMap.get(other);
				if (userSession != null && userSession.isOpen()) {
					receiverSession.getAsyncRemote().sendText(message);
			
				}
			}

		}

	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		String userNameClose = null;
		Set<String> userNames = sessionsMap.keySet();
		for (String userName : userNames) {
			if (sessionsMap.get(userName).equals(userSession)) {
				userNameClose = userName;
				sessionsMap.remove(userName);
				break;
			}
		}

		if (userNameClose != null) {
			State stateMessage = new State("close", userNameClose, userNames);
			String stateMessageJson = gson.toJson(stateMessage);
			Collection<Session> sessions = sessionsMap.values();
			for (Session session : sessions) {
				session.getAsyncRemote().sendText(stateMessageJson);
			}
		}

		String text = String.format("session ID = %s, disconnected; close code = %d%nusers: %s", userSession.getId(),
				reason.getCloseCode().getCode(), userNames);
		System.out.println(text);
	}

}
