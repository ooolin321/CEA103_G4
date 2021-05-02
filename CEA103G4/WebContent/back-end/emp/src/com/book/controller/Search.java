package com.book.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import com.category.model.CategoryService;
import com.google.gson.Gson;
import com.publishers.model.PublisherService;
import tools.StrUtil;

@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final String SUCESS_URL = "/front-end/shopping/bookindex.jsp";
	private static final String ERROR_URL = "/front-end/bookshop-eshop/index.jsp";
	private static final String[] ADV_SEARCH_CONDITIONS = { "bookName", "author", "publisherName", "salePriceMin",
			"salePriceMax", "discountMin", "discountMax", "publicationDateMin", "publicationDateMax" };

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String categoryID = request.getParameter("categoryID");
		HttpSession session = request.getSession();

		String action = request.getParameter("action");
		List<String> errorMsgs = new ArrayList<String>();
		request.setAttribute("errorMsgs", errorMsgs);

		// 使用一般搜尋
		if ("search".equals(action)) {
			String bookName = StrUtil.tryToTrim(request.getParameter("bookName"));

			if ("".equals(bookName) || bookName == null) {
				errorMsgs.add("請輸入至少一項搜尋條件");
				request.getRequestDispatcher(ERROR_URL).forward(request, response);
				return;
			}

			BookService bookService = (BookService) getServletContext().getAttribute("bookService");
			List<Book> books = bookService.getByBookName(bookName, true);
			request.setAttribute("books", books);
			request.getRequestDispatcher(SUCESS_URL).forward(request, response);
		} else if ("advSearch".equals(action)) { // 使用進階搜尋
			String bookName = StrUtil.tryToTrim(request.getParameter("bookName"));
			String author = StrUtil.tryToTrim(request.getParameter("author"));
			String publisherName = StrUtil.tryToTrim(request.getParameter("publisherName"));
			String salePriceMinStr = request.getParameter("realPriceMin");
			String salePriceMaxStr = request.getParameter("realPriceMax");
			String discountMinStr = request.getParameter("discountMin");
			String discountMaxStr = request.getParameter("discountMax");
			String publicationDateMin = request.getParameter("publicationDateMin");
			String publicationDateMax = request.getParameter("publicationDateMax");
			String[] params = { bookName, author, publisherName, salePriceMinStr, salePriceMaxStr, discountMinStr,
					discountMaxStr, publicationDateMin, publicationDateMax };
			int paramCount = 0;

			for (String param : params) {
				if ("".equals(param) || param == null) {
					continue;
				} else {
					paramCount++;
					break;
				}
			}

			if (paramCount == 0) {
				errorMsgs.add("請輸入至少一項搜尋條件");
				request.getRequestDispatcher(ERROR_URL).forward(request, response);
				return;
			}

			BookService bookService = (BookService) getServletContext().getAttribute("bookService");
			Map<String, String> map = new HashMap<String, String>();

			try {
				// 這4項轉型不可失敗，但前端已有用number限制住，除非改網址才會發生
				for (int i = 3; i < 7; i++) {
					String numStr = params[i];
					if (!"".equals(numStr) && numStr != null)
						Double.parseDouble(numStr);
				}

			} catch (NumberFormatException e) {
				errorMsgs.add("數字格式不正確");
			}

			if (!errorMsgs.isEmpty()) {
				request.getRequestDispatcher(ERROR_URL).forward(request, response);
				return;// 程式中斷
			}

			// 嘗試查詢
			try {
				for (int i = 0; i < ADV_SEARCH_CONDITIONS.length; i++) {
					map.put(ADV_SEARCH_CONDITIONS[i], params[i]);
				}
				List<Book> books = bookService.getByAdvSearch(map, true);
				request.setAttribute("books", books);
			} catch (Exception ex) {
				errorMsgs.add("無法取得要查詢的資料: " + ex.getMessage());
			}

			if (!errorMsgs.isEmpty()) {
				response.sendRedirect(request.getContextPath() + ERROR_URL);
				return;// 程式中斷
			}

			request.getRequestDispatcher(SUCESS_URL).forward(request, response);

		} else if (categoryID != null && !"".equals(categoryID)) { // 從分類選單查詢
			BookService bookService = (BookService) getServletContext().getAttribute("bookService");
			CategoryService categoryService = (CategoryService) getServletContext().getAttribute("categoryService");
			List<Book> books = bookService.getByParentCategoryID(categoryID, categoryService, true);

			request.setAttribute("books", books);
			request.setAttribute("catLevelMap", categoryService.getCatLevelMap(categoryID));
			request.getRequestDispatcher(SUCESS_URL).forward(request, response);

		} else if (session != null) { // 換頁操作(session中保有前次的查詢結果和本次請求第幾頁)
			String whichPage = request.getParameter("whichPage");
			if (session.getAttribute("books") != null && (whichPage != null)) {
				request.setAttribute("whichPage", whichPage);
				request.setAttribute("books", session.getAttribute("books"));
				if (session.getAttribute("catLevelMap") != null) {
					request.setAttribute("catLevelMap", session.getAttribute("catLevelMap"));
				}
				request.getRequestDispatcher(SUCESS_URL).forward(request, response);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bookName = StrUtil.tryToTrim(request.getParameter("bookName"));
		String author = StrUtil.tryToTrim(request.getParameter("author"));
		String publisherName = StrUtil.tryToTrim(request.getParameter("publisherName"));

		// AJAX自動補字
		if (bookName != null) {
			response.setContentType("application/json");
			BookService bookService = (BookService) getServletContext().getAttribute("bookService");
			List<String> bookNames = bookService.getByBookNameLike(bookName);
			String searchList = new Gson().toJson(bookNames);
			response.getWriter().write(searchList);
		} else if (author != null) {
			response.setContentType("application/json");
			BookService bookService = (BookService) getServletContext().getAttribute("bookService");
			List<String> authors = bookService.getByAuthorLike(author);
			String searchList = new Gson().toJson(authors);
			response.getWriter().write(searchList);
		} else if (publisherName != null) {
			response.setContentType("application/json");
			PublisherService publisherService = (PublisherService) getServletContext().getAttribute("publisherService");
			List<String> publisherNames = publisherService.getByPublisherNameLike(publisherName);
			String searchList = new Gson().toJson(publisherNames);
			response.getWriter().write(searchList);
		}
	}
}
