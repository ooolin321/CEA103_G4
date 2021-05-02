package com.util;

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

import com.bookclub_regis_detail.model.BookClub_Regis_DetailService;
import com.bookclub_regis_detail.model.BookClub_Regis_DetailVO;


@ServerEndpoint("/NoticeWS/{mem_id}"

)
public class NoticeWS {
	private static final Map<String, Session> member = new ConcurrentHashMap<String, Session>();

	@OnOpen
	public void onOpen(@PathParam("mem_id") String mem_id, Session userSession) {

		System.out.println(mem_id + "已連線" );

		member.put(mem_id, userSession);// 加入成員
	}

	@OnMessage
	public void onMessage(@PathParam("mem_id") String mem_id, Session userSession, String message) throws JSONException {
		JSONObject obj = new JSONObject(message);
		
		if ("private".equals(obj.get("type"))) {
			Session receivedSession = member.get(obj.get("received"));

			if (receivedSession.isOpen())
				receivedSession.getAsyncRemote().sendText(message);
		}
		if("bookClub".equals(obj.get("type"))) {
			BookClub_Regis_DetailService bookClub_Regis_DetailSvc = new BookClub_Regis_DetailService();
			List<BookClub_Regis_DetailVO> list = bookClub_Regis_DetailSvc.getByBc_id((String)obj.get("bc_id"));
			for(BookClub_Regis_DetailVO b : list) {
				Session bookClubSession = member.get(b.getMem_id());
				if(bookClubSession.isOpen())
					bookClubSession.getAsyncRemote().sendText(message);
			}
		}
		System.out.println(message);
	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		e.printStackTrace();
	}

	@OnClose
	public void onClose(@PathParam("mem_id") String mem_id, Session userSession, CloseReason reason) throws JSONException {
		member.remove(mem_id);

		System.out.println(mem_id + "已離開");
	}
}
