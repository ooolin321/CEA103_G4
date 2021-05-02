package com.mem.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mem.model.MemService;
import com.mem.model.MemVO;

/**
 * Servlet implementation class MemPasswordCheck
 */
@WebServlet("/mem/PasswordCheck.do")
public class MemPasswordCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		
		String mem_password = req.getParameter("mem_password");
		String mem_id = req.getParameter("mem_id");
//		System.out.println(mem_id);
//		System.out.println(mem_password);
		MemService memSvc = new MemService();
		MemVO memVO = memSvc.getOneMem(mem_id);
		
		
		if(!(memVO.getMem_password().equals(mem_password))) {
			res.getWriter().print(true);
		} else {
			res.getWriter().print(false);
		}
	}
	

	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}
