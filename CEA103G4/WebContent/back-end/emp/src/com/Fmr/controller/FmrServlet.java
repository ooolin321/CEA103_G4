package com.Fmr.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Fa.model.*;
import com.Fm.model.*;
import com.Fmr.model.*;

@WebServlet("/FmrServlet")
public class FmrServlet extends HttpServlet {
       

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		//轉交到新增留言檢舉頁面
		if("getOne_Fm_Report".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String faId = new String(req.getParameter("faId"));
				String fmId = new String(req.getParameter("fmId"));
				
				FmVO fmVO = new FmVO();
				fmVO.setFaId(faId);
				fmVO.setFmId(fmId);
				
				
				//轉交到新增留言檢舉頁面
				//req.setAttribute("fmVO", fmVO);
				HttpSession session = req.getSession();
				session.setAttribute("fmVO", fmVO);
				String url = "/front-end/forum/addFmrPage.jsp";
				RequestDispatcher successView = req
						.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/forum/forumPage.jsp");
				failureView.forward(req, res);
			}
		}
		
		//新增留言檢舉
		if("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String faId = req.getParameter("faId");
				String fmId = req.getParameter("fmId");
				String memId = req.getParameter("memId");
				String fmrContent = req.getParameter("fmrContent");
				if(fmrContent == null || fmrContent.trim().length()==0) {
					errorMsgs.add("檢舉內容請勿空白!");
				}
				
				FmrVO fmrVO = new FmrVO();
				fmrVO.setFmId(fmId);
				fmrVO.setMemId(memId);
				fmrVO.setFmrContent(fmrContent);
				
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("fmrVO", fmrVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/forum/addFmrPage.jsp");
					//錯誤處理轉交到 新增檢舉頁面
					failureView.forward(req, res);
					return; //中斷程式
				}
				
				 
				
				FmrService fmrSvc = new FmrService();
				fmrVO = fmrSvc.addFmr(fmId, memId, fmrContent);
				
				FaService faSvc = new FaService();
				FaVO faVO = faSvc.getOneFa(faId);
				FmService fmSvc = new FmService();
				List<FmVO> list = fmSvc.getOneFAFm(faId);
				
				
				req.setAttribute("faVO", faVO);
				req.setAttribute("list", list);
				
				String url = "/front-end/forum/forumPage.jsp"; // 轉交到原本觀看的文章頁面
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch(Exception e){
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/forum/addFmrPage.jsp");
				failureView.forward(req, res);
			}
			
		}
		//審核
		if("judge".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				
				String fmrId = req.getParameter("fmrId");
				String fmrStatus = req.getParameter("fmrStatus");
				String fmId = req.getParameter("fmId");
				String adminId = req.getParameter("adminId");
				FmrService fmrSvc = new FmrService();
				fmrSvc.judgeFmr(Integer.parseInt(fmrStatus),adminId, fmrId);
				
				FmService fmSvc = new FmService();
				if(Integer.parseInt(fmrStatus) == 1) {
					fmSvc.logout_Fm(fmId);
				}
				System.out.println(123);
				String url = "/back-end/forum/forumBack_messages.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch(Exception e){
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/forum/forumBack_messages.jsp");
				failureView.forward(req, res);
			}	
		}
		
		
	}

}
