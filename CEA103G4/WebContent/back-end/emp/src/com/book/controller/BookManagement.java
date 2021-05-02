package com.book.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.model.Book;
import com.book.model.BookService;
import com.category.model.Category;
import com.category.model.CategoryService;

import tools.StrUtil;

@WebServlet("/BookManagement")
public class BookManagement extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = "/back-end/jsp_BookManagement/BookManagement.jsp";
		String whichPage = request.getParameter("whichPage");
		String showAll = request.getParameter("showAll");
		String isSold = request.getParameter("isSold");
		HttpSession session = request.getSession();
		CategoryService categoryService = (CategoryService) getServletContext().getAttribute("categoryService");
		List<Category> categories = categoryService.getAll();
		request.setAttribute("categories", categories);

		// 使用搜尋
		if ("getAdvSearch".equals(request.getParameter("action"))) {
			BookService bookService = (BookService) getServletContext().getAttribute("bookService");
			List<Book> books = bookService.advSearchByRequest(request, categoryService, false);

			request.setAttribute("books", books);
		} else if ("0".equals(isSold) || "1".equals(isSold)) { // 上下架操作
			String bookList = request.getParameter("bookList");
			if (bookList != null) {
				List<String> bookIDs = Arrays.asList(bookList.split(","));
				BookService bookService = (BookService) getServletContext().getAttribute("bookService");
				bookService.updateBookIsSoldBatch(bookIDs, Integer.parseInt(isSold));
			}
		} else if (session != null) { // 換頁操作(session中保有前次的查詢結果和本次請求第幾頁)
			if (session.getAttribute("books") != null && (whichPage != null || showAll != null)) {
				request.setAttribute("books", session.getAttribute("books"));
				request.setAttribute("whichPage", whichPage);
				request.setAttribute("showAll", showAll);
			}
		}

		request.getRequestDispatcher(url).forward(request, response);
	}

}
