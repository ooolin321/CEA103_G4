package com.util;

import redis.clients.jedis.Jedis;

import java.util.List;

public class ChatMassage {
	
//	=============================================單人聊天訊息..copy老師的===============================================
	public static List<String> getHistoryMsg(String sender_mem_id, String receiver_mem_id) {
		String key = new StringBuilder(sender_mem_id).append(":").append(receiver_mem_id).toString();
		Jedis jedis = new Jedis("localhost", 6379);

		jedis.auth("123456");
		List<String> bcHistoryData = jedis.lrange(key, 0, jedis.llen(key) - 1);
		jedis.close();
		return bcHistoryData;
	}

	public static void saveChatMessage(String sender_mem_id, String receiver_mem_id, String message) {
		String senderKey = new StringBuilder(sender_mem_id).append(":").append(receiver_mem_id).toString();
		String receiverKey = new StringBuilder(receiver_mem_id).append(":").append(sender_mem_id).toString();

		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		jedis.rpush(senderKey, message);
		jedis.rpush(receiverKey, message);

		jedis.close();
	}
//	=============================================單人聊天訊息..copy老師的==============================================
	
//	=======================================================揪團聊天存取====================================================
	public static List<String> getBookClubMsg(String chatRoom){
		
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		
		List<String> bookClubMsg = jedis.lrange(chatRoom, jedis.llen(chatRoom)-6, jedis.llen(chatRoom) - 1);
		
		if(jedis != null)
			jedis.close();
		
		return bookClubMsg;
	}
	
	public static void saveGroupMsg(String chatRoom, String message) {
		
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		jedis.rpush(chatRoom, message);
		
		if(jedis != null)
			jedis.close();
	}
//	=======================================================揪團聊天存取====================================================
}
