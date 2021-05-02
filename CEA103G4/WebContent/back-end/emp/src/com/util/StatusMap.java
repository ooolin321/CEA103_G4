package com.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StatusMap implements ServletContextListener{
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		
		Map<Integer, String> bcrStatus = new HashMap<Integer, String>();
		bcrStatus.put(new Integer(1), "審核中");
		bcrStatus.put(new Integer(2), "通過");
		bcrStatus.put(new Integer(3), "未通過");
		context.setAttribute("bcrStatus",bcrStatus);
		
		Map<Integer, String> bcStatus = new HashMap<Integer, String>();
		bcStatus.put(new Integer(1), "進行中");
		bcStatus.put(new Integer(2), "未進行中");
		bcStatus.put(new Integer(3), "已解散");
		context.setAttribute("bcStatus",bcStatus);
		
		Map<Integer, String> brdStatus = new HashMap<Integer, String>();
		brdStatus.put(new Integer(1), "審核中");
		brdStatus.put(new Integer(2), "通過");
		brdStatus.put(new Integer(3), "未通過");
		brdStatus.put(new Integer(4), "已退出");
		context.setAttribute("brdStatus", brdStatus);
	}
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
}
