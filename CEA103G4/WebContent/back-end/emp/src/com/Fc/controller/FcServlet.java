package com.Fc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Fc.model.*;

public class FcServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		// ajax 收藏
		if("collect".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String memId = req.getParameter("memId");
				String faId = req.getParameter("faId");
				FcService fcSvc = new FcService();
				fcSvc.collectFa(memId, faId);
				
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/forum/forumPage.jsp");
				failureView.forward(req, res);
			}
		}
		
		// ajax 取消收藏
		if("cancelCollect".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String memId = req.getParameter("memId");
				String faId = req.getParameter("faId");
				FcService fcSvc = new FcService();
				fcSvc.cancelCollectFa(memId, faId);
				String url = "/front-end/forum/memberCenter_forum_collections.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/forum/memberCenter_forum_collections.jsp");
				failureView.forward(req, res);
			}
		}
		//檢查是否有收藏
		if("checkCollection".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			PrintWriter pw = res.getWriter();
			try {
				String memId = req.getParameter("memId");
				String faId = req.getParameter("faId");
				FcService fcSvc = new FcService();
				pw.print(fcSvc.checkCollection(memId, faId));
				pw.flush();
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/forum/forumPage.jsp");
				failureView.forward(req, res);
			}finally {
				pw.close();
			}
		}
		
		
	}

}
