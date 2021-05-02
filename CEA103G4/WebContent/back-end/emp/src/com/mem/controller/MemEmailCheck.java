package com.mem.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mem.model.MemService;


@WebServlet("/mem/EmailCheck.do")
public class MemEmailCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		
		String mem_email = req.getParameter("mem_email");
		MemService memSvc = new MemService();
//		System.out.println(mem_email);
		if(memSvc.checkEmail(mem_email)) {
			res.getWriter().print(true);
		} else {
			res.getWriter().print(false);
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}
