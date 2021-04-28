package com.user.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.live_report.model.*;
import com.user.model.*;

public class UserServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("listLive_report_ByUser_id_A".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String user_id = new String(req.getParameter("user_id"));

				/*************************** 2.開始查詢資料 ****************************************/
				UserService userSvc = new UserService();
				Set<Live_reportVO> set = userSvc.getLive_reportByUser_id(user_id);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("listLive_report_ByUser_id", set);    // 資料庫取出的set物件,存入request

				String url = null;
				url = "/front-end/user/listAllUser.jsp";        // 成功轉交/front-end/user/listLive_reportByUser_id.jsp
//listLive_report_ByUser_id
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("user_id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入會員帳號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/user/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String user_id = null;
				try {
					user_id = new String(str);
				} catch (Exception e) {
					errorMsgs.add("會員帳號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/user/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				UserService userSvc = new UserService();
				UserVO userVO = userSvc.getOneUser(user_id);
				if (userVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/user/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("userVO", userVO); // 資料庫取出的userVO物件,存入req
				String url = "/front-end/user/listOneUser.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneUser.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/user/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllUser.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String user_id = new String(req.getParameter("user_id"));
				
				/***************************2.開始查詢資料****************************************/
				UserService userSvc = new UserService();
				UserVO userVO = userSvc.getOneUser(user_id);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("userVO", userVO);         // 資料庫取出的userVO物件,存入req
				String url = "/front-end/user/update_user_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_user_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/user/listAllUser.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_user_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
String user_id = new String(req.getParameter("user_id").trim());

String user_pwd = new String(req.getParameter("user_pwd").trim());
				
String user_name = req.getParameter("user_name");
				String user_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (user_name == null || user_name.trim().length() == 0) {
					errorMsgs.add("會員姓名: 請勿空白");
				} else if(!user_name.trim().matches(user_nameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				// 身分證驗證
				String id_card = req.getParameter("id_card");
				String input_id = id_card;
				String checkLetter = "ABCDEFGHJKLMNPQRSTUVWXYZIO"; // 首字身分證英文字母代表之意義排序(小到大)
				if (input_id.length() == 10){
					char[] uc_id = input_id.toUpperCase().toCharArray();	
					int[] idArray = new int [uc_id.length];		
					if (checkLetter.indexOf(uc_id[0]) == -1 || (uc_id[1] != '1' && uc_id[1] != '2'))
						errorMsgs.add("身分證格式不正確");
					else{
						int sum=0;
						idArray[0] = checkLetter.indexOf(uc_id[0]) + 10;	// 第一個英文字運算
						sum += idArray[0] / 10; // 將商數加總
						idArray[0] %= 10; // 取餘數放回 idArray[0]
						for (int i = 1; i < 10; i++) // 將身分證後9碼轉成整數(ASCII碼-48)
							idArray[i] = (int)uc_id[i] - 48;
						for (int i = 0; i < 9; i++){		
							idArray[i] *= (9 - i); // 總和 sum += (idArray[0])*9...+ idArray[9]*1)
							sum += idArray[i];
						}
						if ((10-sum%10)%10 != idArray[9]) // 檢查是否相等於檢查碼
							errorMsgs.add("身分證不正確，請重新輸入！");
					}
				}
				else
					errorMsgs.add("身分證長度不正確！");
				
//				String id_card = req.getParameter("id_card").trim();
//				if (id_card == null || id_card.trim().length() == 0) {
//					errorMsgs.add("身分証字號請勿空白");
//				}	
				
String user_gender = new String(req.getParameter("user_gender").trim());
				
				java.sql.Date user_dob = null;
				try {
user_dob = java.sql.Date.valueOf(req.getParameter("user_dob").trim());
				} catch (IllegalArgumentException e) {
					user_dob =new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

String user_mail = req.getParameter("user_mail").trim();
				if (user_mail == null || user_mail.trim().length() == 0) {
					errorMsgs.add("Email請勿空白");
				}			
				
				String user_phone = req.getParameter("user_phone").trim();
				if (user_phone == null || user_phone.trim().length() == 0) {
					errorMsgs.add("電話請勿空白");
				}
				
				String user_mobile = req.getParameter("user_mobile").trim();
				if (user_mobile == null || user_mobile.trim().length() == 0) {
					errorMsgs.add("手機號碼請勿空白");
				}
				
				String city = req.getParameter("city");
				String town = req.getParameter("town");
				
				Integer zipcode = null;
				try {
					zipcode = new Integer(req.getParameter("zipcode").trim());
				} catch (NumberFormatException e) {
					zipcode = 0;
					errorMsgs.add("請選擇郵遞區號");
				}
				
				String user_addr = req.getParameter("user_addr").trim();
				if (user_addr == null || user_addr.trim().length() == 0) {
					errorMsgs.add("地址請勿空白");
				}	
				
				
				java.sql.Date regdate = null;
				try {
regdate = java.sql.Date.valueOf(req.getParameter("regdate").trim());
				} catch (IllegalArgumentException e) {
					regdate =new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				Integer user_point = null;
				try {
user_point = new Integer(req.getParameter("user_point").trim());
				} catch (NumberFormatException e) {
					user_point = 0;
					errorMsgs.add("會員點數請填數字.");
				}
							
				Integer violation = null;
				try {
violation = new Integer(req.getParameter("violation").trim());
				} catch (NumberFormatException e) {
					violation = 0;
					errorMsgs.add("會員違約次數請填數字.");
				}
				
				Integer user_state = null;
				try {
user_state = new Integer(req.getParameter("user_state").trim());
				} catch (NumberFormatException e) {
					user_state = 0;
					errorMsgs.add("會員狀態請填數字.");
				}
				
				Integer user_comment = null;
				try {
user_comment = new Integer(req.getParameter("user_comment").trim());
				} catch (NumberFormatException e) {
					user_comment = 0;
					errorMsgs.add("賣家評價請填數字.");
				}
				
				Integer comment_total = null;
				try {
comment_total = new Integer(req.getParameter("comment_total").trim());
				} catch (NumberFormatException e) {
					comment_total = 0;
					errorMsgs.add("評價人數請填數字.");
				}
				
				Integer cash = null;
				try {
cash = new Integer(req.getParameter("cash").trim());
				} catch (NumberFormatException e) {
					cash = 0;
					errorMsgs.add("會員錢包請填數字.");
				}
//Integer user_id = new Integer(req.getParameter("user_id").trim());
				
				UserVO userVO = new UserVO();
				userVO.setUser_id(user_id);
				userVO.setUser_pwd(user_pwd);
				userVO.setUser_name(user_name);
				userVO.setId_card(id_card);
				userVO.setUser_gender(user_gender);
				userVO.setUser_dob(user_dob);
				userVO.setUser_mail(user_mail);
				userVO.setUser_phone(user_phone);
				userVO.setUser_mobile(user_mobile);
				userVO.setCity(city);
				userVO.setTown(town);
				userVO.setZipcode(zipcode);
				userVO.setUser_addr(user_addr);
				userVO.setRegdate(regdate);
				userVO.setUser_point(user_point);
				userVO.setViolation(violation);
				userVO.setUser_state(user_state);
				userVO.setUser_comment(user_comment);
				userVO.setComment_total(comment_total);
				userVO.setCash(cash);
				
//				System.out.println(user_id + user_pwd + user_name + id_card + user_gender + user_dob + user_mail + user_phone + user_mobile + user_addr + regdate + user_point + violation + user_state + user_comment + comment_total + cash);

				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("userVO", userVO); // 含有輸入格式錯誤的userVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/user/update_user_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				UserService userSvc = new UserService();
				
				userVO = userSvc.updateUser(user_id, user_pwd, user_name, id_card, user_gender,user_dob, user_mail, user_phone, user_mobile, city, town, zipcode, user_addr, regdate, user_point, violation, user_state, user_comment, comment_total, cash);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("userVO", userVO); // 資料庫update成功後,正確的的userVO物件,存入req
				String url = "/front-end/user/listOneUser.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneUser.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/user/update_user_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // 來自addUser.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String user_id = req.getParameter("user_id");
				
				String user_pwd = req.getParameter("user_pwd");
				
				String user_name = req.getParameter("user_name");
				String user_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (user_name == null || user_name.trim().length() == 0) { //user_name == null 防呆用,避免變數打錯
					errorMsgs.add("會員姓名: 請勿空白");
				} else if(!user_name.trim().matches(user_nameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				// 身分證驗證
				String id_card = req.getParameter("id_card");
				String input_id = id_card;
				String checkLetter = "ABCDEFGHJKLMNPQRSTUVWXYZIO"; // 首字身分證英文字母代表之意義排序(小到大)
				if (input_id.length() == 10){
					char[] uc_id = input_id.toUpperCase().toCharArray();	
					int[] idArray = new int [uc_id.length];		
					if (checkLetter.indexOf(uc_id[0]) == -1 || (uc_id[1] != '1' && uc_id[1] != '2'))
						errorMsgs.add("身分證格式不正確");
					else{
						int sum=0;
						idArray[0] = checkLetter.indexOf(uc_id[0]) + 10;	// 第一個英文字運算
						sum += idArray[0] / 10; // 將商數加總
						idArray[0] %= 10; // 取餘數放回 idArray[0]
						for (int i = 1; i < 10; i++) // 將身分證後9碼轉成整數(ASCII碼-48)
							idArray[i] = (int)uc_id[i] - 48;
						for (int i = 0; i < 9; i++){		
							idArray[i] *= (9 - i); // 總和 sum += (idArray[0])*9...+ idArray[9]*1)
							sum += idArray[i];
						}
						if ((10-sum%10)%10 != idArray[9]) // 檢查是否相等於檢查碼
							errorMsgs.add("身分證不正確，請重新輸入！");
					}
				}
				else
					errorMsgs.add("身分證長度不正確！");
			
//				String id_card = req.getParameter("id_card");
//				String id_cardReg = "^[a-zA-Z]{1}[1-2]{1}[0-9]{8}$";
//				if (id_card == null || id_card.trim().length() == 0) {
//					errorMsgs.add("身份證字號請勿空白");
//				} else if (!id_card.trim().matches(id_cardReg)) {
//					errorMsgs.add("身份證字號不正確");
//				}
				
				String user_gender = req.getParameter("user_gender").trim();
				if (user_gender == null || user_gender.trim().length() == 0) {
					errorMsgs.add("性別請勿空白");
				}
				
				java.sql.Date user_dob = null;
				try {
				user_dob = java.sql.Date.valueOf(req.getParameter("user_dob").trim());
				} catch (IllegalArgumentException e) {
					user_dob=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入生日!");
				}
				
				String user_mail = req.getParameter("user_mail");
				
				String user_phone = null;
				try {
				user_phone = new String(req.getParameter("user_phone").trim());
				} catch (NumberFormatException e) {
					user_phone = "034571124";
					errorMsgs.add("電話請填數字.");
				}
				
				String user_mobile = null;
				try {
				user_mobile = new String(req.getParameter("user_mobile").trim());
				} catch (NumberFormatException e) {
					user_mobile = "0988888888";
					errorMsgs.add("手機號碼請填數字.");
				}
				
				String city = req.getParameter("city");
				
				String town = req.getParameter("town");
				
				Integer zipcode = null;
				try {
					zipcode = new Integer(req.getParameter("zipcode").trim());
				} catch (NumberFormatException e) {
					zipcode = 0;
					errorMsgs.add("請選擇郵遞區號");
				}
				
				String user_addr = req.getParameter("user_addr");
				
				java.sql.Date regdate = null;
				try {
				regdate = java.sql.Date.valueOf(req.getParameter("regdate").trim());
				} catch (IllegalArgumentException e) {
					regdate=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				Integer user_point = new Integer(req.getParameter("user_point").trim());
				
				Integer violation = new Integer(req.getParameter("violation").trim());
				
				Integer user_state = new Integer(req.getParameter("user_state").trim());
				
				Integer user_comment = new Integer(req.getParameter("user_comment").trim());
				
				Integer comment_total = new Integer(req.getParameter("comment_total").trim());
				
				Integer cash = new Integer(req.getParameter("cash").trim());

				UserVO userVO = new UserVO();
				userVO.setUser_id(user_id);
				userVO.setUser_pwd(user_pwd);
				userVO.setUser_name(user_name);
				userVO.setId_card(id_card);
				userVO.setUser_gender(user_gender);
				userVO.setUser_dob(user_dob);
				userVO.setUser_mail(user_mail);
				userVO.setUser_phone(user_phone);
				userVO.setUser_mobile(user_mobile);
				userVO.setCity(city);
				userVO.setTown(town);
				userVO.setZipcode(zipcode);
				userVO.setUser_addr(user_addr);
				userVO.setRegdate(regdate);
				userVO.setUser_point(user_point);
				userVO.setViolation(violation);
				userVO.setUser_state(user_state);
				userVO.setUser_comment(user_comment);
				userVO.setComment_total(comment_total);
				userVO.setCash(cash);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("userVO", userVO); // 含有輸入格式錯誤的userVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/user/addUser.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
//1. random -->randomPassword
//2. randomPassword -->email --> employee
//3. randomPassword -->password2
		
UserService userSvc = new UserService();
userVO = userSvc.addUser(user_id, user_pwd, user_name, id_card, user_gender,user_dob, user_mail, user_phone, user_mobile, city, town, zipcode, user_addr, regdate, user_point, violation, user_state, user_comment, comment_total, cash);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/front-end/user/listAllUser.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllUser.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/user/addUser.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // 來自listAllUser.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String user_id = new String(req.getParameter("user_id"));
				
				/***************************2.開始刪除資料***************************************/
				UserService userSvc = new UserService();
				userSvc.deleteUser(user_id);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/front-end/user/listAllUser.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/user/listAllUser.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
