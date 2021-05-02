package com.cs.controller;

import java.io.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.bookclub.model.BookClubService;
import com.bookclub.model.BookClubVO;
import com.cs.model.CsService;
import com.cs.model.CsVO;


public class CsServlet extends HttpServlet {
	
 
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		if ("insertCs".equals(action)) {
			//收集錯誤訊息
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			//接收正確訊息回傳用
			List<String> messages = new LinkedList<String>();
			req.setAttribute("messages", messages);
			try {	
				//接收訊息信箱
				String cs_Email = req.getParameter("cs_Email");
				//接收訊息電話
				String cs_Tel = req.getParameter("cs_Tel");
				//接收訊息主旨
				String cs_Subject = req.getParameter("cs_Subject");
				//接收訊息內容
				String cs_Message = req.getParameter("cs_Message");
				//設定訊息狀態
				Integer cs_isSend = new Integer(0);
				
				//設定VO
				CsVO csVO = new CsVO();
				csVO.setCs_Email(cs_Email);
				csVO.setCs_Tel(cs_Tel);
				csVO.setCs_Subject(cs_Subject);
				csVO.setCs_Message(cs_Message);
				csVO.setCs_isSend(cs_isSend);
				
				//開始新增資料
				CsService csSvc = new CsService();
				csVO = csSvc.addCs(cs_Email, cs_Tel, cs_Subject, cs_Message, cs_isSend);
				messages.add("傳送成功");
				String url = "/front-end/cs/csSendback.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
			} catch (RuntimeException e) {
				errorMsgs.add("傳送失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/cs/csSendback.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if("deleteCs".equals(action)) {
			//收集錯誤訊息
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				//接收請求參數
				String cs_ID = req.getParameter("cs_ID");
				//開始刪除資料
				CsService csSvc = new CsService();
				csSvc.deleteCs(cs_ID);

			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/cs/csindex.jsp");
				successView.forward(req, res);		
			}			
		}
		
		
		if ("CSsearch".equals(action)) {
			String cssearch = req.getParameter("cssearch");
			Map<String, String> search_result = new HashMap<String, String>();
			req.setAttribute("search_result", search_result);
			CsService csSvc = new CsService();

			HttpSession session = req.getSession();
			List<CsVO> list = null;
			if (cssearch.trim() != "") {
				list = csSvc.getSearch(cssearch);
			} else {
				session.setAttribute("list", list);
				session.setAttribute("title", "查詢結果:");
				RequestDispatcher rd = req.getRequestDispatcher("/back-end/cs/csindex.jsp");
				rd.forward(req, res);
				return;
			}

			if (!list.isEmpty()) {
				session.setAttribute("list", list);
				session.setAttribute("title", "查詢結果:");
				RequestDispatcher rd = req.getRequestDispatcher("/back-end/cs/cssearch.jsp");
				rd.forward(req, res);
				return;
			} else {
				session.setAttribute("list", list);
				session.setAttribute("title", "查詢結果:");
				RequestDispatcher rd = req.getRequestDispatcher("/back-end/cs/cssearch.jsp");
				rd.forward(req, res);
				return;
			}
		}
		if("delAllCs".equals(action)){
			String delAll[] = req.getParameterValues("delAll[]");
			CsService csSvc = new CsService();
			
			if (delAll == null) {
				System.out.println("是空值");
			}else {
				for(int i=0;i<delAll.length;i++) {
					csSvc.deleteCs(delAll[i]);
				}
			} 
		}
		
	}

}
