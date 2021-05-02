package com.mem.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.json.JSONArray;
import org.json.JSONObject;

import com.mem.model.MemService;
import com.mem.model.MemVO;

@WebServlet("/mem/getMemData.do")
public class GetMemDataByPhone extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String phone = req.getParameter("phone");
		System.out.println(phone);
		
		MemService memSvc = new MemService();
		String[] str = {phone};
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("mem_tel", str);
		List<MemVO> list = memSvc.getAll(map);
		String mem_id = null;
		for(MemVO memVO : list)
			mem_id = memVO.getMem_id();
		
		System.out.println(mem_id);
		MemVO memVO = memSvc.getOneMem(mem_id);
		
		String obj = new JSONObject(memVO).toString();
		System.out.println(obj);
		PrintWriter out = res.getWriter();
		out.write(obj);
		out.flush();
		out.close();
	}

}