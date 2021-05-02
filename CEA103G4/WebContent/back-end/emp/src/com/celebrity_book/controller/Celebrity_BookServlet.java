package com.celebrity_book.controller;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import com.celebrity_book.model.*;
import com.favorite_book.model.Favorite_BookService;

public class Celebrity_BookServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if("updateCelBook".equals(action)) {
			//蒐集新增成功資訊
			List<String> messages = new LinkedList<String>();
			req.setAttribute("messages", messages);
			//蒐集異常情況例外處理
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				
				String book_ID = req.getParameter("book_ID");
				String mem_ID = req.getParameter("mem_ID");
				Integer share_State = new Integer(req.getParameter("share_State"));
				if(share_State==0) {
					share_State= 1;
					messages.add("更改為分享中");
				}else {
					share_State=0;
					messages.add("更改為下架");
				}
				
				Celebrity_Book celebrity_Book = new Celebrity_Book();
				celebrity_Book.setBook_ID(book_ID);
				celebrity_Book.setMem_ID(mem_ID);
				celebrity_Book.setShare_State(share_State);
				
				/***************************2.開始修改資料*****************************************/
				Celebrity_BookService cbSvc = new Celebrity_BookService();
				celebrity_Book = cbSvc.updateCelBook(share_State,book_ID, mem_ID);
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				
				String url = "/front-end/celebrity_book/celebrityBook.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/celebrity_book/celebrityBook.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("deleteCelBook".equals(action)) { // 來自favorite_BookListAll.jsp

			//蒐集新增成功資訊
			List<String> messages = new LinkedList<String>();
			req.setAttribute("messages", messages);
			
			//蒐集異常情況例外處理
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數***************************************/
				String book_ID = req.getParameter("book_ID");
				String mem_ID = req.getParameter("mem_ID");
				
				/***************************2.開始刪除資料***************************************/
				Celebrity_BookService cbSvc = new Celebrity_BookService();
				cbSvc.deleteCelBook(book_ID, mem_ID);
				if(messages.isEmpty()) {
					messages.add("刪除資料成功");
				}
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/front-end/celebrity_book/celebrityBook.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/celebrity_book/celebrityBook.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("insertCelBook".equals(action)) { // 來自FavoriteBookListAll.jsp的請求  
			List<String> messages = new LinkedList<String>();
			req.setAttribute("messages", messages);
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String book_ID = req.getParameter("book_ID");
				String mem_ID = req.getParameter("mem_ID");
				Integer share_State  = new Integer(0);
				
				Celebrity_Book celebrity_Book = new Celebrity_Book();
				celebrity_Book.setBook_ID(book_ID);
				celebrity_Book.setMem_ID(mem_ID);
				celebrity_Book.setShare_State(share_State);		
				
				/***************************2.開始新增資料***************************************/
				Celebrity_BookService cbSvc = new Celebrity_BookService();
				celebrity_Book =cbSvc.addCelBook(book_ID, mem_ID,share_State);
				if(messages.isEmpty()) {
					messages.add("新增資料成功");
				}
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/front-end/favorite_book/favoriteBook.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
			} catch (RuntimeException e) {
				errorMsgs.add("名人收藏清單已有此書籍");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/favorite_book/favoriteBook.jsp");
				failureView.forward(req, res);
			}
		}

	}
}
