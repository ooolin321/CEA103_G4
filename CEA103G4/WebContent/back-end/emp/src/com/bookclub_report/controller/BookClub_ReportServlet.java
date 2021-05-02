package com.bookclub_report.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.bookclub.model.BookClubService;
import com.bookclub.model.BookClubVO;
import com.bookclub_report.model.*;
import com.util.ReadPic;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class BookClub_ReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
//		=========================================取得請求參數======================================
		String action = req.getParameter("action");
//		=========================================讀圖======================================
		String getImg = req.getParameter("getImg");
		if (getImg != null) {
			BookClub_ReportService bookClub_ReportSvc = new BookClub_ReportService();
			com.util.ReadPic.outputImg(getServletContext(), res, bookClub_ReportSvc.getOneBookClub_Report(getImg).getBcr_proof());
		}	
//		=========================================讀圖======================================		
//		===============================================拿到全部資料========================================================
		if ("getAll".equals(action)) {
			BookClub_ReportJNDIDAO dao = new BookClub_ReportJNDIDAO();
			List<BookClub_ReportVO> list = dao.getAll();
		}
//		===============================================拿到全部資料========================================================

//		=========================================新增======================================
		if ("insert".equals(action)) {
			Map<String, String> situation = new LinkedHashMap<String, String>();
			req.setAttribute("situation", situation);
			
			try {

				/*********************** 會員編號*************************/
				String mem_id = req.getParameter("mem_id");
								
				/*********************** 讀書會編號 *************************/
				String bc_id = req.getParameter("bc_id").trim();
				
				/*********************** 檢舉理由 ************************/
				String bcr_reason = req.getParameter("bcr_reason").trim();
				
				/*********************** 檢舉圖片 ************************/
				Part part = req.getPart("bcr_proof");
				String ContentType = part.getContentType(); // 取得前端獲取的part物件之檔案格式

				if (!ReadPic.getMimeType().contains(ContentType)) {// 用取得之型態，以list.contains遍歷裡面所的字串，以達到比對檔案格式是否有誤
					situation.put("bc_cover_pic", "檔案格式有誤");
				} 
				
				byte[] bcr_proof = com.util.ReadPic.getPartPicture(part);
								
				
				BookClubService bookClubService = new BookClubService();
				BookClubVO bookClubVO = bookClubService.getOneBookClub(bc_id);
				
				/*************************** 2.開始新增資料 ***************************************/
				situation.put("report", "通過");
				BookClub_ReportService bookClub_ReportSvc = new BookClub_ReportService();
				// 讀書會資料
				bookClub_ReportSvc.addBookClub_Report(mem_id,bc_id,bcr_reason,bcr_proof );			
				/******************* 3.新增完成,準備轉交(Send the Success view)****************************/
				req.setAttribute("listOneBookClub", bookClubVO);
				String url = "/front-end/bookclub/listOneBookClub.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				situation.put("Exception", e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/bookclub/listOneBookClub.jsp");
				failureView.forward(req, res);
			}
		}
		
		
//		=========================================審核通過=============================================		
		if("pass".equals(action)) {
//		===============================前台SweetAlert判斷用==============================================
			Map<String, String> situation = new LinkedHashMap<String, String>();
			req.setAttribute("situation", situation);
			
			String bcr_id = req.getParameter("bcr_id");
			String bc_id = req.getParameter("bc_id");
			
//		===============================更新讀書會狀態==============================================			
			BookClubService bookClubSvc = new BookClubService();
			bookClubSvc.updateStatus(bc_id,3);
//		===============================更新讀書會檢舉狀態===========================================			
			BookClub_ReportService bookClub_ReportSvc = new BookClub_ReportService();
			bookClub_ReportSvc.update_Status(2, bcr_id);
			
			situation.put("report", "審核通過");
			String url = "/back-end/bookclub_report/listAllBookClub_Report.jsp";
			RequestDispatcher view = req.getRequestDispatcher(url); 
			view.forward(req, res);
			
		}
		
//		=========================================審核不通過======================================	
		if("fail".equals(action)) {
//		===============================前台SweetAlert判斷用==============================================
			Map<String, String> situation = new LinkedHashMap<String, String>();
			req.setAttribute("situation", situation);
			
			String bcr_id = req.getParameter("bcr_id");
			String bc_id = req.getParameter("bc_id");
			
//		===============================更新讀書會狀態==============================================			
			BookClubService bookClubSvc = new BookClubService();
			bookClubSvc.updateStatus(bc_id,1);			
//		===============================更新讀書會檢舉狀態===========================================	
			BookClub_ReportService bookClub_ReportSvc = new BookClub_ReportService();
			bookClub_ReportSvc.update_Status(3, bcr_id);

			situation.put("report", "審核不通過");
			String url = "/back-end/bookclub_report/listAllBookClub_Report.jsp";
			RequestDispatcher view = req.getRequestDispatcher(url); 
			view.forward(req, res);
		}
		
	}

}
