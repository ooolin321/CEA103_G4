package com.book.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@WebServlet("/AddOneBook")
@MultipartConfig
public class AddOneBook extends HttpServlet {
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/back-end/jsp_BookManagement/AddOneBook.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BookService bookService = (BookService) getServletContext().getAttribute("bookService");
		PublisherService publisherService = (PublisherService) getServletContext().getAttribute("publisherService");
		List<String> errorMsgs = new ArrayList<String>();
		request.setAttribute("errorMsgs", errorMsgs);

		// 這4項不可為null，但目前只做前端驗證
		String bookName = StrUtil.tryToTrim(request.getParameter("bookName"));
		String isbn = request.getParameter("isbn").trim();
		String author = request.getParameter("author").trim();
		String categoryID = request.getParameter("category");

		// 檢查出版社名稱是否存在出版社清單
		String publisherName = request.getParameter("publisherName").trim();
		Publisher pulisher = publisherService.getOneByPublisherName(publisherName);
		if (pulisher == null) {
			errorMsgs.add("出版社尚未存在於出版社清單中，請先新增出版社");
		}
		if (!errorMsgs.isEmpty()) {
			request.getRequestDispatcher("/back-end/jsp_BookManagement/AddOneBook.jsp").forward(request, response);
			return;// 程式中斷
		}
		String publisherID = pulisher.getPublisher_ID();

		String bookNameOriginal = request.getParameter("bookNameOriginal").trim();
		Date publicationDate = null;
		try {
			// 日期不等於空字串
			if (!"".equals(request.getParameter("publicationDate"))) {
				publicationDate = new java.sql.Date(sdf.parse(request.getParameter("publicationDate")).getTime());
			}
		} catch (ParseException e) {
			errorMsgs.add("日期格式不正確");
		}
		if (!errorMsgs.isEmpty()) {
			request.getRequestDispatcher("/back-end/jsp_BookManagement/AddOneBook.jsp").forward(request, response);
			return;// 程式中斷
		}

		String languageID = request.getParameter("language");
		String bookIntro = request.getParameter("bookIntro");
		Double listPrice = null;
		Double salePrice = null;
		Double bookBP = null;
		Integer isSold = null;
		Integer stock = null;
		Integer safetyStock = null;

		try {
			// 這6項不可為null，但目前只做前端驗證
			listPrice = Double.parseDouble(request.getParameter("listPrice"));
			salePrice = Double.parseDouble(request.getParameter("salePrice"));
			bookBP = Double.parseDouble(request.getParameter("bookBP"));
			isSold = Integer.parseInt(request.getParameter("isSold"));
			stock = Integer.parseInt(request.getParameter("stock"));
			safetyStock = Integer.parseInt(request.getParameter("safetyStock"));
		} catch (NumberFormatException e) {
			errorMsgs.add("數字格式不正確");
			return;// 程式中斷
		}
		if (!errorMsgs.isEmpty()) {
			request.getRequestDispatcher("/back-end/jsp_BookManagement/AddOneBook.jsp").forward(request, response);
			return;// 程式中斷
		}

		String bookID = null;
		// 嘗試更新
		try {
			bookService.addBook(publisherID, languageID, categoryID, bookName, isbn, author, listPrice, salePrice,
					bookBP, isSold, publicationDate, stock, safetyStock, bookIntro, bookNameOriginal);
			// 查詢是否已在資料庫裡面
			Map<String, String> map = new HashMap<String, String>();
			map.put("bookName", bookName);
			map.put("isbn", isbn);
			List<Book> books = bookService.getByAdvSearch(map);
			if (books.size() != 1) {
				errorMsgs.add("書名與ISBN不能與既有書籍完全相同(但其中之一可相同)");
			}
			bookID = books.get(0).getBookID();
		} catch (Exception ex) {
			errorMsgs.add("無法取得要新增的資料: " + ex.getMessage());
		}

		// 新增成功->新增圖片，新增失敗->導回
		if (!errorMsgs.isEmpty()) {
			request.getRequestDispatcher("/back-end/jsp_BookManagement/AddOneBook.jsp").forward(request, response);
			return;// 程式中斷
		} else {
			request.getRequestDispatcher("/UpdateBookPic?bookID=" + bookID).forward(request, response);
		}
	}
}