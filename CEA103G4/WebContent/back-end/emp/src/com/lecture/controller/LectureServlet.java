package com.lecture.controller;

import java.io.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;


import com.categorie.model.*;
import com.lecture.model.LectureService;
import com.lecture.model.LectureVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class LectureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("insert".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接受請求參數 - 輸入格式的錯誤處理 *************************/
				/** 1categorie_id **/
				String lc_class_id = req.getParameter("lc_class_id");
				if (lc_class_id == null || lc_class_id.trim().length() == 0) {
					errorMsgs.add("class : do not be blank!");
				}
//				System.out.println("lc_class_id"+lc_class_id);

				/** 2admin **/
				String admin_id = req.getParameter("admin_id");
				if (admin_id == null || admin_id.trim().length() == 0) {
					errorMsgs.add("admin : do not be blank!");
				}
				System.out.println("admin_id"+admin_id);
				/** 3name **/
				String lc_name = req.getParameter("lc_name");
				String lc_nameReg = "^[(\u4e00-\u9fa5)]{2,10}$";
				if (lc_name == null || lc_name.trim().length() == 0) {
					errorMsgs.add("name : do not be blank");
				} else if (!lc_name.trim().matches(lc_nameReg)) {
					errorMsgs.add("name : your format do not correct");
				}
				System.out.println("lc_name"+lc_name);

				/** 4place **/
				String lc_place = req.getParameter("lc_place").trim();
				System.out.println("lc_place"+lc_place);

				/** 5time **/
				java.sql.Timestamp lc_time = java.sql.Timestamp.valueOf(req.getParameter("lc_time").trim() + ":00");
				System.out.println("lc_time"+req.getParameter("lc_time"));
//				java.sql.Timestamp lc_time = null;
				
				/** 6hour **/
				Integer lc_hr = new Integer(req.getParameter("lc_hr").trim());
				System.out.println("lc_hr"+lc_hr);

				/** 7deadline **/
				java.sql.Timestamp lc_deadline = java.sql.Timestamp.valueOf(req.getParameter("lc_deadline").trim() + ":00");
				System.out.println("lc_deadline"+lc_deadline);

				/** 8start **/
				java.sql.Timestamp lc_start_time = java.sql.Timestamp.valueOf(req.getParameter("lc_start_time").trim() + ":00");
				System.out.println("lc_start_time"+lc_start_time);

				/** 9limit **/
				Integer lc_peo_lim = new Integer(req.getParameter("lc_peo_lim").trim());
				System.out.println("lc_peo_lim"+lc_peo_lim);

				/** 10up **/
				Integer lc_peo_up = new Integer(req.getParameter("lc_peo_up").trim());
				System.out.println("lc_peo_up"+lc_peo_up);

				/** 11information **/
				String lc_info = req.getParameter("lc_info").trim();
				System.out.println("lc_info"+lc_info);

				/** 12image **/
				byte[] lc_pic = null;
				Part part = req.getPart("lc_pic");
				if (part == null || part.getSize() == 0) {
					errorMsgs.add("Please update this lecture's image");
				}
				InputStream in = part.getInputStream();
				lc_pic = new byte[in.available()];
				in.read(lc_pic);
				in.close();
				System.out.println("lc_pic"+lc_pic);

				/** 15state **/
				Integer lc_state = null;
				try {
					lc_state = new Integer(req.getParameter("lc_state").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("state : do not be blank!");
				}
				System.out.println("lc_state"+lc_state);

				LectureVO lectureVO = new LectureVO();
				lectureVO.setLc_class_id(lc_class_id);
				lectureVO.setAdmin_id(admin_id);
				lectureVO.setLc_name(lc_name);
				lectureVO.setLc_place(lc_place);
				lectureVO.setLc_time(lc_time);
				lectureVO.setLc_hr(lc_hr);
				lectureVO.setLc_deadline(lc_deadline);
				lectureVO.setLc_start_time(lc_start_time);
				lectureVO.setLc_peo_lim(lc_peo_lim);
				lectureVO.setLc_peo_up(lc_peo_up);
				lectureVO.setLc_info(lc_info);
				lectureVO.setLc_pic(lc_pic);
				lectureVO.setLc_state(lc_state);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("lectureVO", lectureVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/lecture/addLecture.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始新增資料 *****************************************/
				LectureService lectureService = new LectureService();
				LectureVO lectureVO2 = lectureService.addLecture(lc_class_id, admin_id, lc_name, lc_place, lc_time,
						lc_hr, lc_deadline, lc_start_time, lc_peo_lim, lc_peo_up, lc_info, lc_pic,lc_state);

				String lc_id = lectureVO2.getLc_id();
				/*************************** 3.查詢完成，準備轉交(Send the Success view) *************/
				String url = "/back-end/lecture/listAllLecture.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
//				System.out.println("@Lecture-insert OK!");
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/lecture/addLecture.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { 

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				
				String lc_id = req.getParameter("lc_id");
				String lc_class_id = req.getParameter("lc_class_id");
				System.out.println(req.getParameter("lc_class_id"));
				String admin_id = req.getParameter("admin_id");
				String lc_name = req.getParameter("lc_name");
				// display
				/** place **/
				String lc_place = req.getParameter("lc_place").trim();
				System.out.println(lc_id);
				
				/** time **/
				java.sql.Timestamp lc_time = null;
				try {
					lc_time = java.sql.Timestamp.valueOf(req.getParameter("lc_time").trim() + ":00");
				} catch (IllegalArgumentException e){
					errorMsgs.add("講座時間 : 請輸入日期");
				}
				

				/** hour **/
				Integer lc_hr = new Integer(req.getParameter("lc_hr").trim());

				/** deadline **/
				java.sql.Timestamp lc_deadline = null;
				try {
					lc_deadline = java.sql.Timestamp.valueOf(req.getParameter("lc_deadline").trim() + ":00");
				} catch(IllegalArgumentException e) {
					errorMsgs.add("報名截止日期 : 請輸入日期");
				}
				

				/** start time **/
				java.sql.Timestamp lc_start_time = null;
				try {
					lc_start_time = java.sql.Timestamp.valueOf(req.getParameter("lc_start_time").trim() + ":00");
				}catch(IllegalArgumentException e) {
					errorMsgs.add("報名開始日期 : 請輸入日期");
				}
				

				/** limit**/
				Integer lc_peo_lim = new Integer(req.getParameter("lc_peo_lim").trim());

				/** up **/
				Integer lc_peo_up = new Integer(req.getParameter("lc_peo_up").trim());

				/** information **/
				String lc_info = req.getParameter("lc_info").trim();

				/** state **/
				Integer lc_state = null;
				try {
					lc_state = new Integer(req.getParameter("lc_state").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("state : do not be blank!");
				}

				
				LectureVO lectureVO = new LectureVO();
				lectureVO.setLc_id(lc_id);
				lectureVO.setLc_place(lc_place);
				lectureVO.setLc_time(lc_time);
				lectureVO.setLc_hr(lc_hr);
				lectureVO.setLc_deadline(lc_deadline);
				lectureVO.setLc_start_time(lc_start_time);
				lectureVO.setLc_peo_lim(lc_peo_lim);
				lectureVO.setLc_peo_up(lc_peo_up);
				lectureVO.setLc_info(lc_info);
				lectureVO.setLc_state(lc_state);					
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("lectureVO", lectureVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/lecture/update_lecture.jsp");
					failureView.forward(req, res);
					return; 
				}
				/*************************** 2.開始查詢資料 *****************************************/
				LectureService lectureService = new LectureService();

				lectureVO = lectureService.update(lc_place, lc_time, lc_hr, lc_start_time, lc_deadline, lc_peo_lim, lc_peo_up, lc_info, lc_state, lc_id);
				
				lectureVO.setLc_class_id(lc_class_id);
				lectureVO.setAdmin_id(admin_id);
				lectureVO.setLc_name(lc_name);
				
				/*************************** 3.查詢完成，準備轉交(Send the Success view) *************/
				req.setAttribute("lectureVO", lectureVO);
				String url = "/back-end/lecture/listOneLecture.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneLecture.jsp
				successView.forward(req, res);
//				System.out.println("@Lecture-insert OK!");
				
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/lecture/update_lecture.jsp");
				failureView.forward(req, res);
			}
		}
		

		if ("getOne_For_Display".equals(action)) { 
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.接受請求參數 - 輸入格式的錯誤處理 **********************/
				String lc_id = req.getParameter("lc_id").trim();
				System.out.println(lc_id);
				String lc_idReg = "^L[0-9]{4}$";
				if (lc_id == null || (lc_id.trim()).length() == 0) {
					errorMsgs.add("講座編號 : 請勿空白");
				} else if (!lc_id.trim().matches(lc_idReg)) {
					errorMsgs.add("講座編號 : 格式錯誤，開頭必須為L並在後面加入4個數字");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/lecture/listAllLecture.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始查詢資料 *****************************************/
				LectureService lectureService = new LectureService();
				LectureVO lectureVO = lectureService.getOneLecture(lc_id);
				
				if (lectureVO == null) {
					errorMsgs.add("查無資料");
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/lecture/listAllLecture.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 3.查詢完成，準備轉交(Send the Success view) *************/
				req.setAttribute("lectureVO", lectureVO); 
				String url = "/back-end/lecture/listOneLecture.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成觀轉交listOneLecture.jsp
				successView.forward(req, res);
//				System.out.println("@Lecture-getOne_For_Display OK!");
			
			} catch (Exception e) {
				errorMsgs.add("查無資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/lecture/listAllLecture.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { 
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.接受請求參數 ****************************************/
				String lc_id = req.getParameter("lc_id");
				
				/*************************** 2.開始查詢資料****************************************/
				LectureService lectureService = new LectureService();
				LectureVO lectureVO = lectureService.getOneLecture(lc_id);
				/*************************** 3.查詢完成，準備轉交(Send the Success view) ************/
				req.getSession().setAttribute("lectureVO", lectureVO);
				String url = "/back-end/lecture/update_lecture.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得要修改資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/lecture/listAllLecture.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("delete".equals(action)) {
			try {
				/********* 1.接受請求參數******/
				String lc_id = req.getParameter("lc_id");
				/********* 2.開始刪除資料 ******/
				LectureService lectureService = new LectureService();
				lectureService.deleteLecture(lc_id);;
				/****************** 3.刪除完成，準備轉交(Send the Success view) **********/
				String url = "/back-end/lecture/listAllLecture.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				System.out.println("刪除成功");
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/lecture/listAllLecture.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_ForMem_Display".equals(action)) { 
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.接受請求參數 - 輸入格式的錯誤處理 **********************/
				String lc_id = req.getParameter("lc_id").trim();
				System.out.println(lc_id);
				String lc_idReg = "^L[0-9]{4}$";
				if (lc_id == null || (lc_id.trim()).length() == 0) {
					errorMsgs.add("講座編號 : 請勿空白");
				} else if (!lc_id.trim().matches(lc_idReg)) {
					errorMsgs.add("講座編號 : 格式錯誤，開頭必須為L並在後面加入4個數字");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/lecture/lecture_index.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始查詢資料 *****************************************/
				LectureService lectureService = new LectureService();
				LectureVO lectureVO = lectureService.getOneLecture(lc_id);
				
				if (lectureVO == null) {
					errorMsgs.add("查無資料");
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/lecture/lecture_index.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 3.查詢完成，準備轉交(Send the Success view) *************/
				req.setAttribute("lectureVO", lectureVO); 
				String url = "/front-end/lecture/listOneLecture.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成觀轉交listOneLecture.jsp
				successView.forward(req, res);
//				System.out.println("@Lecture-getOne_For_Display OK!");
			
			} catch (Exception e) {
				errorMsgs.add("查無資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/lecture/lecture_index.jsp");
				failureView.forward(req, res);
			}
		}
		
////		===============================�e�x�s������(getAll_For_Display)==============================	
//		if ("getAll_For_Display".equals(action)) {
//
//			String requestURL = req.getParameter("requestURL");
//			
//			if ("/front-end/lecture/lecture_index.jsp".equals(requestURL)) {
//				RequestDispatcher successView = req.getRequestDispatcher("/front-end/lecture/lecture_index.jsp");
//				successView.forward(req, res);
//				return;
//			}
//
//			res.sendRedirect("lecture_index.jsp");
//			return;
//		}
	
	
////	======================================================最新講座=========================================================
//	if ("new_Lecture".equals(action)) {
//		LectureService lectureService = new LectureService();
//		HttpSession session = req.getSession();
//		List<LectureVO> new_Lecture = new ArrayList<LectureVO>();
//
//		List<LectureVO> list = lectureService.getAll();
//		session.setAttribute("list", new_Lecture);
//		session.setAttribute("title", "最新講座");
//
//		for (LectureVO lectureVO : list) {
//			if (isNew(lectureVO.getBc_create_time())) {
//				new_BookClub.add(bookClubVO);
//			}
//		}
//
//		res.sendRedirect("lecture_index.jsp");
//		return;
//	}
//
////	======================================================最新讀書會=========================================================
////	======================================================報名即將截止=========================================================
//	if ("comingSoon".equals(action)) {
//		BookClubService bookClubSvc = new BookClubService();
//		HttpSession session = req.getSession();
//		List<BookClubVO> comingSoon = new ArrayList<BookClubVO>();
//
//		List<BookClubVO> list = bookClubSvc.getAll();
//		session.setAttribute("list", comingSoon);
//		session.setAttribute("title", "報名即將截止");
//		
//		for (BookClubVO bookClubVO : list) {
//			
//			if (comingSoon(bookClubVO.getBc_deadline())) {
//				comingSoon.add(bookClubVO);
//			}
//		}
//
//		res.sendRedirect("lecture_index.jsp");
//		return;
//	}
////	======================================================報名即將截止=========================================================
//
////	======================================================即將滿員開始=========================================================
//	if ("full".equals(action)) {
//		BookClubService bookClubSvc = new BookClubService();
//		HttpSession session = req.getSession();
//		List<BookClubVO> almostFull = new ArrayList<BookClubVO>();
//
//		List<BookClubVO> list = bookClubSvc.getByStatus();
//		session.setAttribute("list", almostFull);
//		session.setAttribute("title", "即將滿員");
//		for (BookClubVO bookClubVO : list) {
//			if (full(bookClubVO.getBc_peo_upper_limit(), bookClubVO.getBc_comfirm_peo())) {
//				almostFull.add(bookClubVO);
//			}
//		}
//
//		res.sendRedirect("lecture_index.jsp");
//		return;
//	}
////	======================================================即將滿員結束=========================================================
	}
}
