package com.recomm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.model.Book;
import com.book.model.BookService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.exceptions.JedisException;
import tools.JedisUtil;

@WebServlet("/ShowRecommKeyDetails")
public class ShowRecommKeyDetails extends HttpServlet {
	private static final String URL = "/back-end/jsp_RecommManagement/ShowRecommKeyDetails.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session != null) { // 換頁操作(session中保有前次的查詢結果和本次請求第幾頁)
			String whichPage = request.getParameter("whichPage");
			if (session.getAttribute("books") != null && session.getAttribute("viewedCount") != null
					&& (whichPage != null)) {
				request.setAttribute("whichPage", whichPage);
				request.setAttribute("books", session.getAttribute("books"));
				request.setAttribute("viewedCount", session.getAttribute("viewedCount"));
				request.setAttribute("keyName", session.getAttribute("keyName"));
				request.getRequestDispatcher(URL).forward(request, response);
			} else {
				fisrtVisitByGet(request, response);
			}
		} else { // 首次拜訪
			fisrtVisitByGet(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void fisrtVisitByGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BookService bookService = (BookService) getServletContext().getAttribute("bookService");
		Jedis jedis = null;
		try {

			// 取得Redis連線
			JedisPool pool = JedisUtil.getJedisPool();
			jedis = pool.getResource();
			jedis.auth("123456");

			String keyName = request.getParameter("keyName");
			Map<String, Double> viewedCount = new TreeMap<String, Double>(); // bookID : viewedCount
			Set<Tuple> res = jedis.zrangeWithScores(keyName, 0, -1);

			// 歸還Redis連線資源
			JedisUtil.closeJedis(jedis);

			res.forEach(tuple -> {
				String bookID = tuple.getElement();
				double count = tuple.getScore();
				viewedCount.put(bookID, count);
			});

			List<String> bookIDs = new ArrayList<String>(viewedCount.keySet());
			List<Book> books = bookService.getByBookIDList(bookIDs);

			books.sort((b1, b2) -> {
				double count1 = viewedCount.get(b1.getBookID());
				double count2 = viewedCount.get(b2.getBookID());

				// 由count多到少排序
				if (count1 == count2) {
					return 0;
				} else if (count1 > count2) {
					return -1;
				} else {
					return 1;
				}
			});

			request.setAttribute("viewedCount", viewedCount);
			request.setAttribute("books", books);
			request.setAttribute("keyName", keyName);
			request.getRequestDispatcher(URL).forward(request, response);
		} catch (JedisException e) {
			// 歸還Redis連線資源
			JedisUtil.closeJedis(jedis);
			e.printStackTrace();
		}
	}

}
