package com.favorite_book.controller;

import java.io.IOException;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.favorite_book.model.*;

public class Favorite_BookServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		if ("deleteFavBook".equals(action)) { // 來自favorite_BookListAll.jsp

			// 蒐集新增成功資訊
			List<String> messages = new LinkedList<String>();
			req.setAttribute("messages", messages);
			// 蒐集異常情況例外處理
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String book_ID = req.getParameter("book_ID");
				String mem_ID = req.getParameter("mem_ID");

				/*************************** 2.開始刪除資料 ***************************************/
				Favorite_BookService fbSvc = new Favorite_BookService();
				fbSvc.deleteFavBook(book_ID, mem_ID);
				if (messages.isEmpty()) {
					messages.add("刪除資料成功");
				}
				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/front-end/favorite_book/favoriteBook.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:");
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/favorite_book/favoriteBook.jsp");
				failureView.forward(req, res);
			}
		}
		if ("insertFavBook".equals(action)) {
			
			try {
				/*************************** 1.接收請求參數 ***************************************/
				String book_ID = req.getParameter("book_ID");
				String mem_ID = req.getParameter("mem_ID");

				Favorite_Book favorite_Book = new Favorite_Book();
				favorite_Book.setBook_ID(book_ID);
				favorite_Book.setMem_ID(mem_ID);

				/*************************** 2.開始新增資料 ***************************************/
				Favorite_BookService fbSvc = new Favorite_BookService();
				favorite_Book = fbSvc.addFavBook(book_ID, mem_ID);
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			} catch (Exception e) {
				System.out.println("新增資料失敗");
			}
		}

	}

}
