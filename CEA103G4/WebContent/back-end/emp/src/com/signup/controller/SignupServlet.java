package com.signup.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lecture.model.LectureService;
import com.signup.model.*;

public class SignupServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("insert".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*********************** 1.接受請求參數 - 輸入格式的錯誤處理 *************************/
				/** mem_id **/
				String mem_id = req.getParameter("mem_id");
				if (mem_id == null || mem_id.trim().length() == 0) {
					errorMsgs.add("請先登入後再報名講座");
				}
				System.out.println(mem_id);
				/** lc_id **/
				String lc_id = req.getParameter("lc_id");
				System.out.println(lc_id);
				SignupVO signupVO = new SignupVO();
				signupVO.setMem_id(mem_id);
				signupVO.setLc_id(lc_id);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("signupVO", signupVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/lecture/lecture_index.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始新增資料 *****************************************/
				SignupService signupService = new SignupService();
				SignupVO signupVO2 = signupService.addSignup(mem_id, lc_id);
				String signup_id = signupVO2.getSignup_id();
				/*************************** 3.查詢完成，準備轉交(Send the Success view) *************/
				String url = "/front-end/signup/listMemSignup.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/lecture/lecture_index.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("delete".equals(action)) {
			try {
				/********* 1.接受請求參數******/
				String signup_id = req.getParameter("signup_id");
				/********* 2.開始刪除資料 ******/
				SignupService signupService = new SignupService();
				signupService.deleteSignup(signup_id);;
				/****************** 3.刪除完成，準備轉交(Send the Success view) **********/
				String url = "/front-end/signup/listMemSignup.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				System.out.println("刪除成功");
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/signup/listMemSignup.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
