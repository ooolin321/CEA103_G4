package com.Fa.controller;

import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.Fa.model.*;
import com.Fm.model.*;
import com.mem.model.*;

import redis.clients.jedis.Jedis;

public class FaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		// 單個文章的頁面
		if("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************1.接收請求參數**********************/
				String faId = req.getParameter("faId");
				/***************************2.取得文章資訊**********************/
				FaService faSvc = new FaService();
				FaVO faVO = faSvc.getOneFa(faId);
				/***************************3.增加觀看次數**********************/
				int faViews = faVO.getFaViews() + 1;
				faSvc.addFaViews(faId, faViews);
				/***************************4.取得文章下面所有留言****************/
			 	FmService fmSvc = new FmService();
			 	List<FmVO> list = fmSvc.getOneFAFm(faVO.getFaId());
			 	/***************************5.取得文章做者會員資訊****************/
			 	MemService memSvc = new MemService();
			 	MemVO memVO = memSvc.getOneMem(faVO.getMemId());
			 	
			 	HttpSession session = req.getSession();
			 	session.setAttribute("faVO", faVO);
			 	req.setAttribute("list", list);
				req.setAttribute("memVO", memVO);
				
				/***************************6.轉交網頁*************************/
				String url = "/front-end/forum/forumPage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/forum/forumIndex.jsp");
				failureView.forward(req, res);
			} 
			
		}
		
		//新增文章
		if ("insert".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String memId = req.getParameter("memId");
				if (memId == null || memId.trim().length() == 0) {
					errorMsgs.add("會員勿空白!!");
				}
				String faTopic = req.getParameter("faTopic").trim();
				if (faTopic == null || faTopic.trim().length() == 0) {
					errorMsgs.add("主題勿空白!!");
				}
				String faContent = req.getParameter("faContent");
				if (faContent == null || faContent.trim().length() == 0) {
					errorMsgs.add("內容勿空白!!");
				}

				
				FaVO faVO = new FaVO();
				faVO.setMemId(memId);
				faVO.setFaTopic(faTopic);
				faVO.setFaContent(faContent);
				/***************************2.錯誤處理，並回傳輸入正確格式的資訊**********************/
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("faVO", faVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/forum/addFaPage.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 3.新增資料 ***************************************/
				FaService faSvc = new FaService();
				faVO = faSvc.addFa(memId, faTopic, faContent);
				
				MemService memSvc = new MemService();
				MemVO memVO = memSvc.getOneMem(memId);
				memVO.setMem_exp(memVO.getMem_exp()+50);
				memSvc.updateExp(memVO);
				/*************************** 4.更新session.memVO經驗值資訊**********************/
				HttpSession session = req.getSession();
				session.setAttribute("memVO", memVO);
				req.setAttribute("faVO", faVO);
				req.setAttribute("exp", "經驗值 + 50");
				String url = "/front-end/forum/forumIndex.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法新增文章"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/forum/addFaPage.jsp");
				failureView.forward(req, res);
			}
		}
		
		//取得文章去修改
		if("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************1.接收請求參數****************************************/
				String faId = new String(req.getParameter("faId"));
				
				/***************************2.開始查詢資料****************************************/
				FaService faSvc = new FaService();
				FaVO faVO = faSvc.getOneFa(faId);
				
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("faVO", faVO);
				String url = "/front-end/forum/updateFaPage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/forum/memberCenter_forum.jsp");
				failureView.forward(req, res);
			}
		}

		//修改文章
		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String faId = new String(req.getParameter("faId"));
				
				String faTopic = req.getParameter("faTopic").trim();
				if (faTopic == null || faTopic.trim().length() == 0) {
					errorMsgs.add("主題勿空白!!");
				}
				
				String faContent = req.getParameter("faContent").trim();
				if (faContent == null || faContent.trim().length() == 0) {
					errorMsgs.add("內容勿空白!!");
				}
			
				FaVO faVO = new FaVO();

				faVO.setFaTopic(faTopic);
				faVO.setFaContent(faContent);
				faVO.setFaId(faId);
			
				/***************************2.錯誤處理，並回傳輸入正確格式的資訊**********************/
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("faVO", faVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/forum/memberCenter_forum.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*************************** 3.修改資料 ***************************************/
				FaService faSvc = new FaService();
				faVO = faSvc.updateFa(faTopic, faContent,faId);
				
				req.setAttribute("faVO", faVO);
				req.setAttribute("updateSuccess", "修改完成");
				String url = "/front-end/forum/memberCenter_forum.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改文章失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/forum/memberCenter_forum.jsp");
				failureView.forward(req, res);
			}

		}
		
		//下架文章
		if("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 **********************/
				String faId = new String(req.getParameter("faId"));
				/***************************2.修改資料 ***************************************/
				FaService faSvc = new FaService();
				faSvc.deleteFa(faId);
				
				String url = "/front-end/forum/memberCenter_forum.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/***************************其他可能的錯誤處理*************************************/
			}catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/forum/memberCenter_forum.jsp");
				failureView.forward(req, res);
			}
		}
		
		//模糊搜尋
		if("search".equals(action)) {
			List<FaVO> list = new ArrayList<FaVO>();
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			Jedis jedis = null;
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String faTopic = new String(req.getParameter("faTopic").trim());
				if (faTopic == null || faTopic.length() == 0) {
					errorMsgs.add("請輸入搜尋內容!");
				}
				/***************************2.錯誤處理，並回傳輸入正確格式的資訊**********************/
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/forum/forumIndex.jsp");
					failureView.forward(req, res);
					return;
				}
				
				FaService faSvc = new FaService();
				
				/**************************3.存到session**************************************/
				HttpSession session = req.getSession();
				list = faSvc.search(faTopic);
				session.setAttribute("list", list);
				
				//關鍵字存到 redis
				jedis = new Jedis("localhost", 6379);
				jedis.auth("123456");
				
				
				if (jedis.zrange("searchKeywords", 0 , -1).stream().anyMatch(key -> key.equals(faTopic))) {
					jedis.zincrby("searchKeywords", 1, faTopic);
				} else {
					Map<String, Double> searchKeywords = new HashMap<>();
					searchKeywords.put(faTopic, new Double(0));
					jedis.zadd("searchKeywords", searchKeywords);
				}
				
				req.setAttribute("faTopic", faTopic);
				String url = "/front-end/forum/forumIndex_search.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("無法取得要搜尋的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/forum/forumIndex.jsp");
				failureView.forward(req, res);
			}finally {
				if(jedis != null)
					jedis.close();
			}
		}
		
		
	
		
	}



}
