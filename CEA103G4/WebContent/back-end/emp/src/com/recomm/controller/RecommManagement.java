package com.recomm.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.category.model.Category;
import com.category.model.CategoryService;
import com.redis.model.RedisKey;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;
import tools.JedisUtil;

@WebServlet("/RecommManagement")
public class RecommManagement extends HttpServlet {
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CategoryService categoryService = (CategoryService) getServletContext().getAttribute("categoryService");
		Jedis jedis = null;
		try {
			// 取得Redis連線
			JedisPool pool = JedisUtil.getJedisPool();
			jedis = pool.getResource();
			jedis.auth("123456");

			List<String> keyNames = new LinkedList<String>();

			// 近7天每天的統計keyNames
			for (int i = 0; i < 7; i++) {
				Date date = new Date(new Date().getTime() - i * 24 * 60 * 60 * 1000);
				String keyNameOfRecentViewCount = DATE_FORMATTER.format(date) + "viewed";
				keyNames.add(keyNameOfRecentViewCount);
			}

			// 近2天的熱門書keyNames
			String today = LocalDate.now().toString();
			String yesterday = DATE_FORMATTER.format(new Date(new java.util.Date().getTime() - 24 * 60 * 60 * 1000));
			keyNames.add("popularBooks" + today);
			keyNames.add("popularBooks" + yesterday);

			// 近2天每個類別的熱門書keyNames
			List<Category> categories = categoryService.getAll();
			categories.forEach(cat -> {
				StringBuffer keyNameOfCatViewedCountToday = new StringBuffer(cat.getCategoryID()).append(",")
						.append(today).append("viewed");
				StringBuffer keyNameOfCatViewedCountYesterday = new StringBuffer(cat.getCategoryID()).append(",")
						.append(yesterday).append("viewed");
				;
				keyNames.add(keyNameOfCatViewedCountToday.toString());
				keyNames.add(keyNameOfCatViewedCountYesterday.toString());
			});

			List<RedisKey> recommKeys = new LinkedList<RedisKey>();
			// 若key裡面無任何value，從List移除不顯示
			for (String keyName : keyNames) {
				long size = jedis.zcard(keyName);
				if (size > 0) {
					long ttl = jedis.ttl(keyName);
					recommKeys.add(new RedisKey(keyName, ttl, size));
				}
			}

			request.setAttribute("recommKeys", recommKeys);

			// 歸還Redis連線資源
			JedisUtil.closeJedis(jedis);
		} catch (JedisException e) {
			// 歸還Redis連線資源
			JedisUtil.closeJedis(jedis);
			e.printStackTrace();
		}

		request.getRequestDispatcher("/back-end/jsp_RecommManagement/RecommManagement.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
