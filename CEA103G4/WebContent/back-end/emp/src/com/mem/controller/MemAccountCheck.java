package com.mem.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mem.model.MemService;


@WebServlet("/mem/AccountCheck.do")
public class MemAccountCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
   
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		
		String mem_account = req.getParameter("mem_account");
		MemService memSvc = new MemService();
//		System.out.println(mem_account);
		if(memSvc.checkAcc(mem_account)) {
			res.getWriter().print(true);
		} else {
			res.getWriter().print(false);
		}
	}
	

	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
