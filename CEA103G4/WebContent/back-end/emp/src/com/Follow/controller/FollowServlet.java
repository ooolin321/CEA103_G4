package com.Follow.controller;

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

import com.Follow.model.*;


@WebServlet("/FollowServlet")
public class FollowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		//開啟通知
		if("Subscribe".equals(action)) {
			//使用者會員
			String memId = req.getParameter("memId");
			//追蹤的作者會員
			String aMemId = req.getParameter("aMemId");
			FollowService followSvc = new FollowService();
			followSvc.subscribe(memId, aMemId);
		}
		//關閉通知
		if("unSubscribe".equals(action)) {
			String memId = req.getParameter("memId");
			String aMemId = req.getParameter("aMemId");
			FollowService followSvc = new FollowService();
			followSvc.unSubscribe(memId, aMemId);
		}
		//檢查是否有通知
		if("checkSubscribe".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			PrintWriter pw = res.getWriter();
			try {
			String memId = req.getParameter("memId");
			String aMemId = req.getParameter("aMemId");
			
			FollowService followSvc = new FollowService();
			pw.print(followSvc.checkSubscribe(memId, aMemId));
			pw.flush();
			} catch(Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/forum/forumPage.jsp");
				failureView.forward(req, res);
			} finally {
				if(pw != null)
					pw.close();
			}
			
		}
		
	}

}
