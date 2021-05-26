package com.customer_service.controller;

import java.io.IOException;
import java.util.Collection;
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

import org.json.JSONException;
import com.customer_service.model.ChatMessage;
import com.customer_service.model.State;
import com.google.gson.Gson;
import Jedis.JedisHandleMessage;

@ServerEndpoint("/CustomerWS/{userNameOrEmpno}")
public class CustomerWS {
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
	private static Map<String, Session> sessionsMapForMember = new ConcurrentHashMap<>();
	private static Map<String, Session> sessionsMapForEmp = new ConcurrentHashMap<>();
	Gson gson = new Gson();

	@OnOpen
	public void onOpen(@PathParam("userNameOrEmpno") String userName, Session userSession)
			throws IOException, JSONException {
		if (userName.startsWith("14")) {
			sessionsMapForEmp.put(userName, userSession);
			Set<String> memNames = sessionsMapForMember.keySet();
			Set<String> emps = sessionsMapForEmp.keySet();
			if (memNames.size() > 0) {
				State stateMessage = new State();
				stateMessage.setUsers(memNames);
				stateMessage.setType("openEmp");
				String stateMessageJson = gson.toJson(stateMessage);
				userSession.getAsyncRemote().sendText(stateMessageJson);
			} else {
				State stateMessage = new State();
				stateMessage.setUsers(emps);
				stateMessage.setType("noMems");
				String stateMessageJson = gson.toJson(stateMessage);
				userSession.getAsyncRemote().sendText(stateMessageJson);
			}
		} else {
			Set<String> empNames = sessionsMapForEmp.keySet();
			sessionsMapForMember.put(userName, userSession);
			if (empNames.size() > 0) {
				State stateMessage = new State();
				stateMessage.setUsers(empNames);
				stateMessage.setType("empAvailable");
				String stateMessageJson = gson.toJson(stateMessage);
				userSession.getAsyncRemote().sendText(stateMessageJson);
			} else {
				State stateMessage = new State();
				stateMessage.setType("empNotAvailable");
				String stateMessageJson = gson.toJson(stateMessage);
				userSession.getAsyncRemote().sendText(stateMessageJson);
			}

		}

	}

	@OnMessage
	public void onMessage(Session userSession, String message) {

		ChatMessage chatMessage = gson.fromJson(message, ChatMessage.class);
		String sender = chatMessage.getSender();
		String receiver = chatMessage.getReceiver();

		if ("history".equals(chatMessage.getType())) {
			List<String> historyData = JedisHandleMessage.getHistoryMsg(sender, receiver);
			String historyMsg = gson.toJson(historyData);
			ChatMessage cmHistory = new ChatMessage("history", sender, receiver, historyMsg);
			if (userSession != null && userSession.isOpen()) {
				userSession.getAsyncRemote().sendText(gson.toJson(cmHistory));
				System.out.println("history = " + gson.toJson(cmHistory));
				return;
			}
		}

		if (sender.startsWith("14")) {
			Session memSession = sessionsMapForMember.get(receiver);
			if (memSession != null && memSession.isOpen()) {
				memSession.getAsyncRemote().sendText(message);
				userSession.getAsyncRemote().sendText(message);
				JedisHandleMessage.saveChatMessage(sender, receiver, message);
			}
		} else {
			Session empSession = sessionsMapForEmp.get(receiver);
			if (empSession != null && empSession.isOpen()) {
				empSession.getAsyncRemote().sendText(message);
				userSession.getAsyncRemote().sendText(message);
				JedisHandleMessage.saveChatMessage(sender, receiver, message);
			}
		}

		Session receiverSession = sessionsMap.get(receiver);
		if (receiverSession != null && receiverSession.isOpen()) {
			receiverSession.getAsyncRemote().sendText(message);
			userSession.getAsyncRemote().sendText(message);
			JedisHandleMessage.saveChatMessage(sender, receiver, message);
		}
		System.out.println("Message received: " + message);
	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		String userNameClose = null;
		if (sessionsMapForMember.values().contains(userSession)) { // 使用者離線
			Set<String> userNames = sessionsMapForMember.keySet();
			for (String userName : userNames) {
				if (sessionsMapForMember.get(userName).equals(userSession)) {
					userNameClose = userName;
					sessionsMapForMember.remove(userName);
					break;
				}
			}

			if (userNameClose != null) {
				State stateMessage = new State("close", userNameClose, userNames);
				String stateMessageJson = gson.toJson(stateMessage);
				Collection<Session> empSessions = sessionsMapForEmp.values();
				for (Session session : empSessions) {
					session.getAsyncRemote().sendText(stateMessageJson);
				}
			}
		} else { // 會員離線
			Set<String> empNames = sessionsMapForEmp.keySet();
			for (String empno : empNames) {
				if (sessionsMapForEmp.get(empno).equals(userSession)) {
					sessionsMapForEmp.remove(empno);
					break;
				}
			}
		}
		String text = String.format("session ID = %s, disconnected; close code = %d%nusers: %s", userSession.getId(),
				reason.getCloseCode().getCode(), userNameClose);
		System.out.println(text);
	}
}
