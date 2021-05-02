package com.bookclub.controller;


import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.*;
import javax.websocket.server.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.util.ChatMassage;

import redis.clients.jedis.Jedis;

@ServerEndpoint(
		"/BookClubServerEcho/{mem_id}/{bc_id}"
				
)
public class BookClubServerEcho {
	private static final Map<String, Set<Session>> allSessions = new ConcurrentHashMap<String, Set<Session>>();
	private static final Map<String, Session> member = new ConcurrentHashMap<String, Session>();
	
	@OnOpen
	public void onOpen(@PathParam("mem_id") String mem_id, @PathParam("bc_id") String bcRoom, Session userSession) throws JSONException {
		if(!allSessions.containsKey(bcRoom)) {
			allSessions.put(bcRoom, new  HashSet<Session>());
		}
		
		
//		設置大小限制
		int maxBufferSize = 500 * 1024 * 1024;
		userSession.setMaxTextMessageBufferSize(maxBufferSize);
		userSession.setMaxBinaryMessageBufferSize(maxBufferSize);

		Set<Session> roomSessions = allSessions.get(bcRoom);
		roomSessions.add(userSession);//加入揪團聊天
		member.put(mem_id, userSession);//加入成員
		Set<String> online = member.keySet();

		JSONArray onlineArr = new JSONArray(online);

		
//		Gson gson = new Gson();
		MemService svc = new MemService();
		MemVO mem = svc.getOneMem(mem_id);
		
		String name = mem.getMem_name();

		
		System.out.println(name + "已連線");
		
		JSONObject jobj = new JSONObject();
		jobj.put("online", name + "加入聊天");
		jobj.put("isOnline", onlineArr);
		for(Session session : roomSessions) {
			if(session.isOpen()) {
				session.getAsyncRemote().sendText(jobj.toString());
			}
		}
		
	}
	
	@OnMessage
	public void onMessage(@PathParam("mem_id") String mem_id, @PathParam("bc_id") String bcRoom, Session userSession, String message) throws JSONException {
		Set<Session> roomSessions = allSessions.get(bcRoom);
		JSONObject obj = new JSONObject(message);
		
		//發給全部
		if("all".equals(obj.get("type"))) {
			String chatRoom = bcRoom + "chat";
			ChatMassage.saveGroupMsg(chatRoom, message);
			for(Session session : roomSessions) {
				if(session.isOpen()) {
					session.getAsyncRemote().sendText(message);
				}
			}
		}
		
		//私訊
		if("private".equals(obj.get("type"))) {
			Session receivedSession = member.get(obj.get("received"));
			Session senderSession = member.get(mem_id);
			
			if(receivedSession.isOpen())
				receivedSession.getAsyncRemote().sendText(message);
			
			senderSession.getAsyncRemote().sendText(message);
		}
	}
	
	@OnError
	public void onError(Session userSession, Throwable e){
		e.printStackTrace();
	}
	
	@OnClose
	public void onClose(@PathParam("mem_id") String mem_id, @PathParam("bc_id") String bcRoom, Session userSession, CloseReason reason) throws JSONException {
		Set<Session> roomSessions = allSessions.get(bcRoom);
		roomSessions.remove(userSession);
		member.remove(mem_id);
		Set<String> online = member.keySet();
		
		JSONArray onlineArr = new JSONArray(online);
		
		MemService svc = new MemService();
		MemVO mem = svc.getOneMem(mem_id);
		
		String name = mem.getMem_name();
		
		JSONObject jobj = new JSONObject();
		jobj.put("online", name + "離開聊天");
		jobj.put("mem_id", mem_id);
		jobj.put("notOnline", onlineArr);
		for(Session session : roomSessions) {
			if(session.isOpen()) {
				session.getAsyncRemote().sendText(jobj.toString());
			}
		}
		
		System.out.println(name + "離線");
	}
}
