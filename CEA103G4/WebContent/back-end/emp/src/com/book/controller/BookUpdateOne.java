package com.book.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.model.Book;
import com.book.model.BookService;
import com.publishers.model.Publisher;
import com.publishers.model.PublisherService;

import tools.StrUtil;

@WebServlet("/BookUpdateOne")
@MultipartConfig
public class BookUpdateOne extends HttpServlet {
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BookService bookService = (BookService) getServletContext().getAttribute("bookService");
		String bookID = request.getParameter("bookID");
		Optional<Book> book = bookService.getByBookID(bookID);
		// 存取既有書籍修改頁面
		if (book.isPresent()) {
			request.setAttribute("book", book.get());
			request.getRequestDispatcher("/back-end/jsp_BookManagement/BookUpdateOne.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BookService bookService = (BookService) getServletContext().getAttribute("bookService");
		PublisherService publisherService = (PublisherService) getServletContext().getAttribute("publisherService");
		String bookID = request.getParameter("bookID");
		Optional<Book> book = bookService.getByBookID(bookID);
		request.setAttribute("book", book.get());

		List<String> errorMsgs = new ArrayList<String>();
		request.setAttribute("errorMsgs", errorMsgs);

		// 修改既有書籍
		String bookName = StrUtil.tryToTrim(request.getParameter("bookName"));
		String isbn = StrUtil.tryToTrim(request.getParameter("isbn"));
		String publisherName = StrUtil.tryToTrim(request.getParameter("publisherName"));
		Publisher pulisher = publisherService.getOneByPublisherName(publisherName);
		if (pulisher == null) {
			errorMsgs.add("出版社尚未存在於出版社清單中，請先新增出版社");
		}
		if (!errorMsgs.isEmpty()) {
			request.getRequestDispatcher("/back-end/jsp_BookManagement/BookUpdateOne.jsp").forward(request, response);
			return;// 程式中斷
		}

		String publisherID = pulisher.getPublisher_ID();
		String author = StrUtil.tryToTrim(request.getParameter("author"));
		String bookNameOriginal = StrUtil.tryToTrim(request.getParameter("bookNameOriginal"));

		Date publicationDate = null;
		try {
			publicationDate = new java.sql.Date(sdf.parse(request.getParameter("publicationDate")).getTime());
		} catch (ParseException e) {
			errorMsgs.add("日期格式不正確");
		}
		if (!errorMsgs.isEmpty()) {
			request.getRequestDispatcher("/back-end/jsp_BookManagement/BookUpdateOne.jsp").forward(request, response);
			return;// 程式中斷
		}

		String categoryID = request.getParameter("category");
		String languageID = request.getParameter("language");
		String bookIntro = request.getParameter("bookIntro");
		Double listPrice = null;
		Double salePrice = null;
		Double bookBP = null;
		Integer isSold = null;
		Integer stock = null;
		Integer safetyStock = null;

		try {
			listPrice = Double.parseDouble(request.getParameter("listPrice"));
			salePrice = Double.parseDouble(request.getParameter("salePrice"));
			bookBP = Double.parseDouble(request.getParameter("bookBP"));
			isSold = Integer.parseInt(request.getParameter("isSold"));
			stock = Integer.parseInt(request.getParameter("stock"));
			safetyStock = Integer.parseInt(request.getParameter("safetyStock"));
		} catch (NumberFormatException e) {
			errorMsgs.add("數字格式不正確");
			request.getRequestDispatcher("/back-end/jsp_BookManagement/BookUpdateOne.jsp").forward(request, response);
			return;// 程式中斷

		}

		// 嘗試更新
		try {
			bookService.updateBook(bookID, publisherID, languageID, categoryID, bookName, isbn, author, listPrice,
					salePrice, bookBP, isSold, publicationDate, stock, safetyStock, bookIntro, bookNameOriginal);
		} catch (Exception ex) {
			errorMsgs.add("無法取得要修改的資料:" + ex.getMessage());
			request.getRequestDispatcher("/back-end/jsp_BookManagement/BookUpdateOne.jsp").forward(request, response);
		}
		response.sendRedirect(request.getContextPath() + "/BookUpdateOne?bookID=" + bookID);
	}

}