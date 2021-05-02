package com.mem.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.mem.model.MemVO;

import redis.clients.jedis.Jedis;


@WebServlet("/mem/googleLogIn.do")
public class GoogleLogInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		
		String memId = req.getParameter("memId");
		String memName = req.getParameter("memName");
		String memEmail = req.getParameter("memEmail");
		String memImgURL = req.getParameter("memImgURL");
		System.out.println(memEmail);
		MemVO memVO = new MemVO();
		memVO.setMem_id(memId);
		memVO.setMem_name(memName);
		memVO.setMem_email(memEmail);
		
		String jsonStr = new JSONObject(memVO).toString();
		System.out.println(jsonStr);
		jedis.set(memId, jsonStr);
		
		req.getSession().setAttribute("memVO", memVO);
		
		String url = "/front-end/front-index.jsp";
		RequestDispatcher successView = req.getRequestDispatcher(url);
		successView.forward(req, res);
	}

}
