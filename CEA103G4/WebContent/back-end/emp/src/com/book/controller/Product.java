package com.book.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;

import com.book.model.Book;
import com.book.model.BookService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;
import tools.CountComparator;
import tools.JedisUtil;

@WebServlet("/Product/*")
public class Product extends HttpServlet {

	/********************************此為測試用舊頁面**********************************/
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 測試Redis瀏覽紀錄系統
		response.setContentType("text/html; charset=utf-8");

		BookService bookService = (BookService) getServletContext().getAttribute("bookService");
		String requestURI = request.getRequestURI();
		String bookID = requestURI.substring(requestURI.lastIndexOf('/') + 1, requestURI.lastIndexOf('/') + 13);
		Optional<Book> b = bookService.getByBookID(bookID, true);

		if (b.isPresent()) {
			Book book = b.get();

			// 利用cookie取得/更新近期瀏覽書籍(30本)
			List<Book> recentViewedBooks = bookService
					.getByBookIDList(UpdateRecentViewedBooksCookie(bookID, request, response));

			// 利用近期瀏覽書籍(30本)計算出推薦書籍
			Set<Book> recommBooks = bookService.getRecommendedBooks(recentViewedBooks, bookID);

			// 瀏覽次數+1
			bookService.UpdateRedisViewCount(bookID);

			// ===測試用待刪
			PrintWriter out = response.getWriter();
			out.println("<h1>模擬商品頁面</h1>");
			out.println(requestURI);
			out.println("<h2>商品資訊</h2>");
			out.println(book);
			out.println("<h2>近期瀏覽書籍</h2>");
			recentViewedBooks.forEach(viewedBook -> out.println(viewedBook + "<br>"));
			out.println("<h2>推薦書籍</h2>");

			

			recommBooks.forEach(recommBook -> out.println(recommBook + "<br>"));
			// 加入收藏書單測試
			out.println("<form method='post' action='" + request.getContextPath()
			+ "/front-end/favorite_book/favorite_book.do' style='margin-bottom: 0px;'>");
			out.println("<input type='submit' value='新增至收藏書單'>");
			out.println("<input type='hidden' name='book_ID' value='" + bookID + "'>");
			out.println("<input type='hidden' name='mem_ID' value='M0001'>");
			out.println("<input type='hidden' name='action' value='insertFavBook'>");
			out.println("</form>");
			// ===測試用待刪
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private List<String> UpdateRecentViewedBooksCookie(String bookID, HttpServletRequest request,
			HttpServletResponse response) {
		Cookie recentViewedBooksCookie = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("recentViewedBookIDs".equals(cookie.getName())) {
					recentViewedBooksCookie = cookie;
				}
			}
		}

		String recentViewedBookIDs = UpdateRecentViewedBookIDs(bookID, recentViewedBooksCookie);
		List<String> bookIDs = Arrays.asList(recentViewedBookIDs.split("_"));

		if (recentViewedBooksCookie != null) {
			recentViewedBooksCookie.setValue(recentViewedBookIDs);
		} else {
			recentViewedBooksCookie = new Cookie("recentViewedBookIDs", recentViewedBookIDs);
		}
		recentViewedBooksCookie.setMaxAge(60 * 60 * 24 * 365); // 365天效期cookie
		response.addCookie(recentViewedBooksCookie);

		return bookIDs;
	}

	private String UpdateRecentViewedBookIDs(String bookID, Cookie cookie) {
		LinkedList<String> bookIDList = new LinkedList<String>();
		StringBuffer recentViewedBookIDs = new StringBuffer();

		if (cookie != null) {
			String bookIDs = cookie.getValue();
			if (bookIDs != null) {
				bookIDList = new LinkedList<String>(Arrays.asList(bookIDs.split("_")));
			}

			// 只保留30個近期瀏覽記錄
			if (bookIDList.size() < 30) {
				// 去除重複
				if (bookIDList.contains(bookID)) {
					bookIDList.remove(bookID);
				}
			} else {
				// 去除重複
				if (bookIDList.contains(bookID)) {
					bookIDList.remove(bookID);
				} else {
					bookIDList.removeLast();
				}
			}
		}

		bookIDList.addFirst(bookID);
		bookIDList.forEach(viewedBookID -> {
			recentViewedBookIDs.append(viewedBookID).append("_");
		});

		return recentViewedBookIDs.toString();
	}
}
