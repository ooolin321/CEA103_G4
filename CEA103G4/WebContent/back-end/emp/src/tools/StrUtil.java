package tools;

import java.sql.Timestamp;

public class StrUtil {
	private StrUtil() {}
	
	public static Timestamp toTimestamp(String time) {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		ts = Timestamp.valueOf(time + ":00"); // 原始格式 2020-09-30 00:00需加:00
		return ts;
	}
	
	public static String tryToTrim(String str) {
		if (str != null) {
			return str.trim();
		} else {
			return str;
		}
	}
}
