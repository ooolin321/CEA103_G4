package com.recomm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.model.BookService;
import com.google.gson.Gson;
import com.redis.model.RedisKey;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;
import tools.JedisUtil;

/**
 * Servlet implementation class UpdateRecommKey
 */
@WebServlet("/UpdateRecommKey")
public class UpdateRecommKey extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Jedis jedis = null;
		try {
			List<String> errorMsgs = new ArrayList<String>();
			request.setAttribute("errorMsgs", errorMsgs);

			String keyName = request.getParameter("keyName");
			String bookID = request.getParameter("bookID");

			// 取得Redis連線
			JedisPool pool = JedisUtil.getJedisPool();
			jedis = pool.getResource();
			jedis.auth("123456");
			long size = jedis.zcard(keyName);
			// 歸還Redis連線資源
			JedisUtil.closeJedis(jedis);
			if (size > 0) {
				request.setAttribute("recommKey", new RedisKey(keyName, jedis.ttl(keyName), size));
				request.setAttribute("bookID", bookID);

				if ("".equals(bookID) || bookID == null) {
					request.setAttribute("count", 0);
				} else {
					request.setAttribute("count", jedis.zscore(keyName, bookID));
				}
			} else {
				errorMsgs.add("keyName不存在Redis資料庫中");
			}
		} catch (JedisException e) {
			// 歸還Redis連線資源
			JedisUtil.closeJedis(jedis);
			e.printStackTrace();
		}

		request.getRequestDispatcher("/back-end/jsp_RecommManagement/UpdateRecommKey.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String term = request.getParameter("term");
		String checkBookID = request.getParameter("checkBookID");
		BookService bookService = (BookService) getServletContext().getAttribute("bookService");
		List<String> errorMsgs = new ArrayList<String>();
		request.setAttribute("errorMsgs", errorMsgs);

		// AJAX檢查bookID
		if (checkBookID != null && !"".equals(checkBookID)) {
			response.setContentType("text/html;charset=UTF-8");
			if (bookService.getByBookID(checkBookID).isPresent()) {
				response.getWriter().write("OK");
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getWriter().write("此商品ID不存在資料庫中");
			}
		}

		// AJAX自動補字
		if (term != null) {
			response.setContentType("application/json");
			try {
				List<String> bookIDs = bookService.getByBookIDLike(term);
				String searchList = new Gson().toJson(bookIDs);
				response.getWriter().write(searchList);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
			}
		} else {// 新增/更改POST

			String keyName = request.getParameter("keyName");
			String bookID = request.getParameter("bookID");

			if (bookService.getByBookID(bookID).isPresent()) {
				// 取得Redis連線
				JedisPool pool = JedisUtil.getJedisPool();
				Jedis jedis = pool.getResource();
				jedis.auth("123456");
				long size = jedis.zcard(keyName);
				// 歸還Redis連線資源
				JedisUtil.closeJedis(jedis);
				if (size > 0) {
					Double count = 0.0;
					try {
						count = Double.parseDouble(request.getParameter("count"));
					} catch (NumberFormatException e) {
						errorMsgs.add("數字格式不正確");
						return; // 中斷不新增
					}
					// 嘗試新增/更新
					jedis.zadd(keyName, count, bookID);
				} else {
					errorMsgs.add("keyName不存在Redis資料庫中");
				}
			} else {
				errorMsgs.add("bookID不存在Oracle資料庫中");
			}

			if (errorMsgs.size() == 0) {
				request.getRequestDispatcher("/ShowRecommKeyDetails?keyName=" + keyName).forward(request, response);
			}
		}
	}
}
