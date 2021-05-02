package tools;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class SimpleRedisLogger {

	public void setInfo(Jedis jedis, String key, String msg, long time) {
		jedis.lpush(key, time + "_" + msg);
		// 只留最近的100則訊息
		jedis.ltrim(key, 0, 99);
	}

	public List<Map.Entry<String, Date>> getInfo(Jedis jedis, String key) {
		List<String> res = jedis.lrange(key, 0, -1);
		List<Map.Entry<String, Date>> msgs = new ArrayList<Map.Entry<String, Date>>();
		res.forEach(s -> {
			String[] temp = s.split("_");
			long time = Long.parseLong(temp[0]);
			String msg = temp[1];
			msgs.add(new AbstractMap.SimpleEntry<String,Date>(msg, new Date(time)));
		});
		return msgs;
	}

}
