package com.bookclub.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.json.JSONArray;

import com.bookclub.model.BookClubJNDIDAO;
import com.bookclub.model.BookClubService;
import com.bookclub.model.BookClubVO;
import com.bookclub_regis_detail.model.BookClub_Regis_DetailService;
import com.bookclub_regis_detail.model.BookClub_Regis_DetailVO;
import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.question.model.QuestionService;
import com.question.model.QuestionVO;
import com.questionnair_answer.model.Questionnair_AnswerService;
import com.util.ChatMassage;
import com.util.MailService;
import com.util.ReadPic;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class BookClubServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
//		=========取得請求參數
		String action = req.getParameter("action");
//		===============================================圖片輸出部分(getImg)========================================================		
		String getImg = req.getParameter("getImg");
		if (getImg != null) {
			BookClubService bookClubService = new BookClubService();
			BookClubVO bookClubVO = bookClubService.getOneBookClub(getImg);
			com.util.ReadPic.outputImg(getServletContext(), res, bookClubVO.getBc_cover_pic());
//			ServletOutputStream out  =  res.getOutputStream(); //舊寫法
//			out.write(bookClubVO.getBc_cover_pic());
//			out.flush();
//			out.close();
		}
//		===============================================圖片輸出部分(getImg)========================================================
//		===============================================瀏覽全部(getAll_For_Display)========================================================
		if ("getAll_For_Display".equals(action)) {

			String requestURL = req.getParameter("requestURL");
			
			if ("/front-end/bookclub/bookclub_index.jsp".equals(requestURL)
					|| "/front-end/bookclub/bookclub_blurrySelect.jsp".equals(requestURL)) {
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/bookclub/bookclub_index.jsp");
				successView.forward(req, res);
				return;
			}

			res.sendRedirect("bookclub_index.jsp");
			return;
		}
//		===============================================瀏覽全部(getAll_For_Display)========================================================
//		===============================================瀏覽詳情部分(getOne_For_Dispaly)============================================		
		if ("getOne_For_Display".equals(action)) {
			try {
				String bc_id = req.getParameter("bc_id");
				BookClubService bookClubService = new BookClubService();
				BookClubVO bookClubVO = bookClubService.getOneBookClub(bc_id);
				System.out.println(bc_id);
				req.setAttribute("listOneBookClub", bookClubVO);
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/bookclub/listOneBookClub.jsp");
				successView.forward(req, res);
				
			} catch (Exception e) {
				System.out.println("瀏覽出問題啦");
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/bookclub/listAllBookClub.jsp");
				failureView.forward(req, res);
				return;
			}
		}
//		===============================================瀏覽詳情部分(getOne_For_Dispaly)============================================		
//		===============================================刪除========================================================
		if ("delete".equals(action)) {// 來自刪除按鈕
			try {
				/********* 1.接收請求參數 ******/
				String bc_id = req.getParameter("bc_id");
				/********* 2.取得資料 ******/
				BookClubService bookClubSvc = new BookClubService();
				bookClubSvc.deleteBookClub(bc_id);
				/****************** 3.新增完成,準備轉交(Send the Success view) **********/
				String url = "/back-end/bookclub/listAllBookClub.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				System.out.println("刪除出問題啦");
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/bookclub/listAllBookClub.jsp");
				failureView.forward(req, res);
			}
		}

//		===============================================新增========================================================
		if ("insert".equals(action)) {
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			Map<String, String> situation = new LinkedHashMap<String, String>();
			req.setAttribute("situation", situation);
			
			try {
				/*********************** 從session取會員編號 *************************/
				HttpSession session = req.getSession();
				MemVO memVO = (MemVO) session.getAttribute("memVO");
				String mem_id = memVO.getMem_id();
				/*********************** 讀書會名稱 *************************/
				String bc_name = req.getParameter("bc_name");
				if (bc_name == null || bc_name.trim().length() == 0) {
					errorMsgs.put("bc_name", "讀書會名稱: 請勿空白");
				} 
				/*********************** 讀書會地點 *************************/
				String bc_place = req.getParameter("bc_place").trim();
				if (bc_place == null || bc_place.trim().length() == 0) {
					errorMsgs.put("bc_place", "地點請勿空白");
				}

				/*********************** 讀書會開始時間 ************************/
				java.sql.Timestamp bc_time_start = null;
				try {
					bc_time_start = java.sql.Timestamp.valueOf(req.getParameter("bc_time_start").trim() + ":00");

				} catch (IllegalArgumentException e) {
					errorMsgs.put("bc_time_start", "活動開始時間，請輸入日期");
				}
				/*********************** 讀書會結束時間 ************************/
				java.sql.Timestamp bc_time_end = null;
				try {
					bc_time_end = java.sql.Timestamp.valueOf(req.getParameter("bc_time_end").trim() + ":00");
				} catch (IllegalArgumentException e) {
					errorMsgs.put("bc_time_end", "活動結束時間，請輸入日期");
				}
				/*********************** 人數上限 ************************/
				Integer bc_peo_upper_limit = null;
				try {
					bc_peo_upper_limit = new Integer(req.getParameter("bc_peo_upper_limit").trim());
				} catch (NumberFormatException e) {
					errorMsgs.put("bc_peo_upper_limit", "人數上限，請填數字");
				}
				;
				/*********************** 人數下限 ************************/
				Integer bc_peo_lower_limit = null;
				try {
					bc_peo_lower_limit = new Integer(req.getParameter("bc_peo_lower_limit").trim());
				} catch (NumberFormatException e) {
					errorMsgs.put("bc_peo_lower_limit", "人數下限，請填數字");
				}
				;
				/*********************** 讀書會簡介 ***********************/
				String bc_intro = req.getParameter("bc_intro").trim();
				if (bc_intro == null || bc_intro.trim().length() == 0) {
					errorMsgs.put("bc_intro", "簡介請勿空白");
				}
				/*********************** 讀書會封面圖 ***********************/
				Part part = req.getPart("bc_cover_pic");
				String filename = ReadPic.getFileNameFromPart(part); // 取得前端獲取的part物件之檔名
				String ContentType = part.getContentType(); // 取得前端獲取的part物件之檔案格式

				if (filename == null) {
					errorMsgs.put("bc_cover_pic", "請選擇封面照片");
				} else if (!ReadPic.getMimeType().contains(ContentType)) { // 用取得之型態，以list.contains遍歷裡面所的字串，以達到比對檔案格式是否有誤
					errorMsgs.put("bc_cover_pic", "檔案格式有誤");
				}

				/*********************** 報名開始日期 ***********************/
				java.sql.Date bc_init = null;
				try {
					bc_init = java.sql.Date.valueOf(req.getParameter("bc_init").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.put("bc_init", "報名開始日期，請輸入日期");
				}
				/*********************** 報名截止日期 ***********************/
				java.sql.Date bc_deadline = null;
				try {
					bc_deadline = java.sql.Date.valueOf(req.getParameter("bc_deadline").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.put("bc_deadline", "報名結束日期，請輸入日期");
				}

				byte[] bc_cover_pic = ReadPic.getPartPicture(req.getPart("bc_cover_pic"));
				
				/*********************** 問卷 *************************/
				String[] q_data = req.getParameterValues("q_data");


				
				BookClubVO bookClubVO = new BookClubVO();
				QuestionVO questionVO = new QuestionVO();
				// 送出錯誤訊息
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("bookClubVO", bookClubVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/bookclub/addBookClub.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始新增資料 ***************************************/
				QuestionService questionSvc = new QuestionService();
				BookClubService bookClubSvc = new BookClubService();

				// 讀書會資料
				bookClubVO = bookClubSvc.addBookClub(mem_id,bc_name, bc_place, bc_time_start, bc_time_end, bc_peo_upper_limit,
						bc_peo_lower_limit, bc_intro, bc_cover_pic, bc_init, bc_deadline);
				// 問卷資料			
				String bc_id = bookClubVO.getBc_id(); //取得剛剛新增之讀書會編號(自增主鍵)
				for (int i = 0; i < q_data.length; i++) {
					questionVO = questionSvc.addQuestion(bc_id, q_data[i]);
				}

				/*******************
				 * 3.新增完成,準備轉交(Send the Success view)
				 ****************************/
				req.setAttribute("bookClubVO", bookClubVO);
				situation.put("creat", "創建成功");
				String url = "/front-end/bookclub/bookclub_index.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.put("Exception", e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/bookclub/addBookClub.jsp");
				failureView.forward(req, res);
			}
		}
		if ("getOne_For_Update".equals(action)) {
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*************************** 1.接收請求參數 ****************************************/
				String bc_id = req.getParameter("bc_id");
				/*************************** 2.開始查詢資料 ****************************************/
				BookClubService bookClubSvc = new BookClubService();
				BookClubVO bookClubVO = bookClubSvc.getOneBookClub(bc_id);
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("bookClubVO", bookClubVO);
				String url = "/front-end/bookclub/update_BookClub_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.put("Exception", e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/bookclub/update_BookClub_input.jsp");
				failureView.forward(req, res);
			}
		}
		if ("update".equals(action)) {
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			Map<String, String> situation = new LinkedHashMap<String, String>();
			req.setAttribute("situation", situation);
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				/*********************** 讀書會編號 *************************/
				String bc_id = req.getParameter("bc_id").trim();
				/*********************** 讀書會名稱 *************************/
				String bc_name = req.getParameter("bc_name");
				if (bc_name == null || bc_name.trim().length() == 0) {
					errorMsgs.put("bc_name", "讀書會名稱: 請勿空白");
				} 
				/*********************** 讀書會地點 *************************/
				String bc_place = req.getParameter("bc_place").trim();
				if (bc_place == null || bc_place.trim().length() == 0) {
					errorMsgs.put("bc_place", "地點請勿空白");
				}

				/*********************** 讀書會開始時間 ************************/
				java.sql.Timestamp bc_time_start = null;
				try {
					bc_time_start = java.sql.Timestamp.valueOf(req.getParameter("bc_time_start").trim() + ":00");
					System.out.println(bc_time_start);
				} catch (IllegalArgumentException e) {
					errorMsgs.put("bc_time_start", "活動開始時間，請輸入日期");
				}
				/*********************** 讀書會結束時間 ************************/
				java.sql.Timestamp bc_time_end = null;
				try {
					bc_time_end = java.sql.Timestamp.valueOf(req.getParameter("bc_time_end").trim() + ":00");
				} catch (IllegalArgumentException e) {
					errorMsgs.put("bc_time_end", "活動結束時間，請輸入日期");
				}
				/*********************** 人數上限 ************************/
				Integer bc_peo_upper_limit = null;
				try {
					bc_peo_upper_limit = new Integer(req.getParameter("bc_peo_upper_limit").trim());
				} catch (NumberFormatException e) {
					errorMsgs.put("bc_peo_upper_limit", "人數上限，請填數字");
				}
				;
				/*********************** 人數下限 ************************/
				Integer bc_peo_lower_limit = null;
				try {
					bc_peo_lower_limit = new Integer(req.getParameter("bc_peo_lower_limit").trim());
				} catch (NumberFormatException e) {
					errorMsgs.put("bc_peo_lower_limit", "人數下限，請填數字");
				}
				;
				/*********************** 讀書會簡介 ***********************/
				String bc_intro = req.getParameter("bc_intro").trim();
				if (bc_intro == null || bc_intro.trim().length() == 0) {
					errorMsgs.put("bc_intro", "簡介請勿空白");
				}
				/*********************** 讀書會封面圖 ***********************/
				Part part = req.getPart("bc_cover_pic");
				String filename = ReadPic.getFileNameFromPart(part);
				String ContentType = part.getContentType();
				byte[] bc_cover_pic = ReadPic.getPartPicture(req.getPart("bc_cover_pic"));
				if (filename == null) {
					BookClubService bookClubService = new BookClubService();
					BookClubVO bookClubVO = bookClubService.getOneBookClub(bc_id);
					bc_cover_pic = bookClubVO.getBc_cover_pic();
				} else if (!ReadPic.getMimeType().contains(ContentType)) {
					errorMsgs.put("bc_cover_pic", "檔案格式有誤");
				}

				/*********************** 報名開始日期 ***********************/
				java.sql.Date bc_init = null;
				try {
					bc_init = java.sql.Date.valueOf(req.getParameter("bc_init").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.put("bc_init", "報名開始日期，請輸入日期");
				}
				/*********************** 報名截止日期 ***********************/
				java.sql.Date bc_deadline = null;
				try {
					bc_deadline = java.sql.Date.valueOf(req.getParameter("bc_deadline").trim());

				} catch (IllegalArgumentException e) {
					errorMsgs.put("bc_deadline", "報名結束日期，請輸入日期");
				}

				

				BookClubVO bookClubVO = new BookClubVO();
				bookClubVO.setBc_id(bc_id);
				bookClubVO.setBc_name(bc_name);
				bookClubVO.setBc_place(bc_place);
				bookClubVO.setBc_time_start(bc_time_start);
				bookClubVO.setBc_time_end(bc_time_end);
				bookClubVO.setBc_peo_upper_limit(bc_peo_upper_limit);
				bookClubVO.setBc_peo_lower_limit(bc_peo_lower_limit);
				bookClubVO.setBc_intro(bc_intro);
				bookClubVO.setBc_init(bc_init);
				bookClubVO.setBc_deadline(bc_deadline);
				
				// 送出錯誤訊息
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("bookClubVO", bookClubVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/bookclub/update_BookClub_input.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始修改資料 ***************************************/
				BookClubService bookClubSvc = new BookClubService();
				
				
				// 讀書會資料
				bookClubVO = bookClubSvc.update(bc_id, bc_name, bc_place, bc_time_start, bc_time_end,
						bc_peo_upper_limit, bc_peo_lower_limit, bc_intro, bc_cover_pic, bc_init, bc_deadline);
				/*******************
				 * 3.修改完成,準備轉交(Send the Success view)
				 ****************************/
				bookClubVO = bookClubSvc.getOneBookClub(bc_id);
				req.setAttribute("listOneBookClub", bookClubVO);
				situation.put("update", "修改成功");
				String url = "/front-end/bookclub/listOneBookClub.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.put("Exception", e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/bookclub/update_BookClub_input.jsp");
				failureView.forward(req, res);

//				

			}

		}
//		======================================================解散讀書會開始=========================================================
		if ("disband".equals(action)) {
			Map<String, String> situation = new LinkedHashMap<String, String>();
			req.setAttribute("situation", situation);
			
			String bc_id = req.getParameter("bc_id");
			BookClubService bookClubSvc = new BookClubService();
			BookClubVO bookClubVO = bookClubSvc.getOneBookClub(bc_id);
			bookClubSvc.updateStatus(bc_id,3);
			
			BookClub_Regis_DetailService bookClub_Regis_DetailSvc = new BookClub_Regis_DetailService();
			List<BookClub_Regis_DetailVO> listDetail = bookClub_Regis_DetailSvc.getByBc_id(bc_id);
			MemVO memVO = new MemVO(); 
			MemService memSvc = new MemService();
			for(BookClub_Regis_DetailVO brdVO : listDetail) {
				memVO = memSvc.getOneMem(brdVO.getMem_id());
				
				String to = memVO.getMem_email();
			      
			      String subject = bookClubVO.getBc_name() + "解散通知";
			      
			      String ch_name = memVO.getMem_nickname();
			      String messageText = "Hello! " + ch_name + "\n您報名" + bookClubVO.getBc_name() + "讀書會，已經解散囉"; 
			       
			      MailService mailService = new MailService();
			      mailService.sendMail(to, subject, messageText);
			}
			
			situation.put("disband", "解散");
			String url = "/front-end/bookclub/myBookClub.jsp";
			RequestDispatcher view = req.getRequestDispatcher(url); 
			view.forward(req, res);
		}
//		======================================================解散讀書會結束=========================================================
//		======================================================模糊查詢開始=========================================================
		if ("search".equals(action)) {
			String search = req.getParameter("search");
			System.out.println(search);
			Map<String, String> search_result = new HashMap<String, String>();
			req.setAttribute("search_result", search_result);
			BookClubService bookClubSvc = new BookClubService();

			HttpSession session = req.getSession();
			List<BookClubVO> list = null;
			if (search.trim() != "") {
				list = bookClubSvc.getBlurry(search);
			} else {
				list = bookClubSvc.getBlurry("無輸入關鍵字");
				session.setAttribute("list", list);
				session.setAttribute("title", "查詢結果:");
				search_result.put("error", "請輸入關鍵字");
				RequestDispatcher rd = req.getRequestDispatcher("/front-end/bookclub/bookclub_blurrySelect.jsp");
				rd.forward(req, res);
				return;
			}

			if (!list.isEmpty()) {
				session.setAttribute("list", list);
				session.setAttribute("title", "查詢結果:");
				RequestDispatcher rd = req.getRequestDispatcher("/front-end/bookclub/bookclub_blurrySelect.jsp");
				rd.forward(req, res);
				return;
			} else {
				session.setAttribute("list", list);
				search_result.put("data_not_found", "查無資料");
				session.setAttribute("title", "查詢結果:");
				RequestDispatcher rd = req.getRequestDispatcher("/front-end/bookclub/bookclub_blurrySelect.jsp");
				rd.forward(req, res);
				return;
			}
		}
//		======================================================模糊查詢結束=========================================================
//		======================================================最新讀書會=========================================================
		if ("new_BookClub".equals(action)) {
			BookClubService bookClubSvc = new BookClubService();
			HttpSession session = req.getSession();
			List<BookClubVO> new_BookClub = new ArrayList<BookClubVO>();

			List<BookClubVO> list = bookClubSvc.getAll();
			session.setAttribute("list", new_BookClub);
			session.setAttribute("title", "最新讀書會");

			for (BookClubVO bookClubVO : list) {
				if (isNew(bookClubVO.getBc_create_time())) {
					new_BookClub.add(bookClubVO);
				}
			}

			res.sendRedirect("bookclub_blurrySelect.jsp");
			return;
		}

//		======================================================最新讀書會=========================================================
//		======================================================報名即將截止=========================================================
		if ("comingSoon".equals(action)) {
			BookClubService bookClubSvc = new BookClubService();
			HttpSession session = req.getSession();
			List<BookClubVO> comingSoon = new ArrayList<BookClubVO>();

			List<BookClubVO> list = bookClubSvc.getAll();
			session.setAttribute("list", comingSoon);
			session.setAttribute("title", "報名即將截止");
			
			for (BookClubVO bookClubVO : list) {
				
				if (comingSoon(bookClubVO.getBc_deadline())) {
					comingSoon.add(bookClubVO);
				}
			}

			res.sendRedirect("bookclub_blurrySelect.jsp");
			return;
		}
//		======================================================報名即將截止=========================================================

//		======================================================即將滿員開始=========================================================
		if ("full".equals(action)) {
			BookClubService bookClubSvc = new BookClubService();
			HttpSession session = req.getSession();
			List<BookClubVO> almostFull = new ArrayList<BookClubVO>();

			List<BookClubVO> list = bookClubSvc.getByStatus();
			session.setAttribute("list", almostFull);
			session.setAttribute("title", "即將滿員");
			for (BookClubVO bookClubVO : list) {
				if (full(bookClubVO.getBc_peo_upper_limit(), bookClubVO.getBc_comfirm_peo())) {
					almostFull.add(bookClubVO);
				}
			}

			res.sendRedirect("bookclub_blurrySelect.jsp");
			return;
		}
//		======================================================即將滿員結束=========================================================
//		=======================================================聊天室開始==========================================================
		
		if("bookClubChat".equals(action)) {
			String bc_id = req.getParameter("bc_id");
			String mem_id = req.getParameter("mem_id");
			
			MemService memSvc = new MemService();
			MemVO memVO = memSvc.getOneMem(mem_id);
			
			BookClubService bookClubSvc = new BookClubService();
			BookClubVO bookClubVO = bookClubSvc.getOneBookClub(bc_id);
			MemVO MyMemVO = memSvc.getOneMem(bookClubVO.getMem_id());
			
			String memName = memVO.getMem_name();
			req.setAttribute("name", memName);
			req.setAttribute("MyMemVO",MyMemVO);
			
			RequestDispatcher rd = req.getRequestDispatcher("/front-end/bookclub/bookclub_chat.jsp");
			rd.forward(req, res);
			return;
		}
		
//		=======================================================聊天室開始==========================================================
//		=====================================================歷史訊息部份==============================================================
				if("chat".equals(action)) {
					String chatRoom = req.getParameter("bc_id") + "chat";
					List<String> msg = ChatMassage.getBookClubMsg(chatRoom);
					JSONArray arr = new JSONArray(msg);
					
					res.setContentType("text/plan; charset=utf-8");
					PrintWriter out = res.getWriter();
					out.println(arr.toString());
					return;
				}
//		=====================================================歷史訊息部份==============================================================		
	}

//	======================================================計算三天前新開的讀書會工具=========================================================	
	public boolean isNew(Timestamp createTime) {
		long ThreeDaysLater = (createTime.getTime() + (1 * 24 * 60 * 60 * 1000));
		long currentTime = System.currentTimeMillis();
		if (currentTime <= ThreeDaysLater)
			return true;
		else
			return false;
	}

//	======================================================計算三天前新開的讀書會工具=========================================================
//	======================================================算報名時間即將截止工具=============================================================
	public boolean comingSoon(Date time) {
		final long Day = 7;
		
		
		long assemble = time.getTime();
		long daysMilli = assemble - System.currentTimeMillis();
		int ttl = (int) (daysMilli / 1000 / 60 / 60 / 24);
		if (ttl > 0 && ttl < Day)
			return true;
		else
			return false;
	}

//	======================================================算報名時間即將截止工具=============================================================
//	======================================================算即將額滿工具=============================================================
	public boolean full(Integer peo_limit, Integer comfirm) {
		int peo = peo_limit - comfirm;
		if (peo > 0 && peo <= 5)
			return true;
		else
			return false;
	}
//	======================================================算即將額滿工具=============================================================
}
