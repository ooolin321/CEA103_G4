package tests;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;

import com.book.model.Book;
import com.book.model.BookDAO;
import com.book.model.BookDAOImpl;
import com.book.model.BookService;

import oracle.jdbc.pool.OracleDataSource;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;
import tools.CountComparator;
import tools.JedisUtil;

public class TestRecomm {
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) {
		OracleDataSource ds;
		BookService bookService = null;
		try {
			ds = new OracleDataSource();
			ds.setDriverType("thin");
			ds.setServerName("localhost");
			ds.setPortNumber(1521);
			ds.setDatabaseName("XE"); // Oracle SID
			ds.setUser("BOOKSHOP");
			ds.setPassword("123456");
			DataSource dataSource = (DataSource) ds;
			BookDAO bookDAO = new BookDAOImpl(dataSource);
			bookService = new BookService(bookDAO);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 取得Redis連線
		JedisPool pool = JedisUtil.getJedisPool();
		Jedis jedis = pool.getResource();
		jedis.auth("123456");

		// 測試取資料寫法
		List<Book> recentViewedBooks = bookService.getByAuthor("黃");
		Collections.shuffle(recentViewedBooks);
		if (recentViewedBooks.size() > 30) {
			recentViewedBooks = recentViewedBooks.subList(0, 30);
		}
		if (Math.random() < 0.1) {
			System.out.println("這次是空陣列!");
			recentViewedBooks = recentViewedBooks.subList(0, 0);
		}
		String thisBookID = "B00000088174";
		String thisCategoryID = bookService.getByBookID(thisBookID).get().getCategoryID();

		// 從cookie計算近期瀏覽記錄中占比最高的5個書籍類別(因傳入的近期瀏覽書籍可能是0~30本，favoriteCategoryIDs的可能長度為0~5)
		Map<String, Integer> map = new HashMap<String, Integer>();
		recentViewedBooks.forEach(viwedBook -> {
			String categoryID = viwedBook.getCategoryID();
			int count = map.containsKey(categoryID) ? map.get(categoryID) : 0;
			map.put(categoryID, count + 1);
		});

		List<Map.Entry<String, Integer>> favoriteCategoryIDs = CountComparator.entriesSortedByValues(map);
		if (favoriteCategoryIDs.size() > 5) {
			favoriteCategoryIDs = favoriteCategoryIDs.subList(0, 5);
		}

		if (favoriteCategoryIDs.size() == 0) {
			// 從商品詳情頁面URL取的bookID，並加入此categoryID，令favoriteCategoryIDs長度在1~5之間。
			Map.Entry<String, Integer> entry = new AbstractMap.SimpleEntry<String, Integer>(thisCategoryID, 1);
			favoriteCategoryIDs.add(entry);
		}

		int sumToday = 0;
		int sumYesterday = 0;
		String today = LocalDate.now().toString();
		String yesterday = DATE_FORMATTER.format(new Date(new Date().getTime() - 24 * 60 * 60 * 1000));

		// 計算
		for (Map.Entry<String, Integer> entry : favoriteCategoryIDs) {
			String categoryID = entry.getKey();
			StringBuilder keyName = new StringBuilder(categoryID).append(",").append(today).append("viewed");
			StringBuilder keyNameYesterday = new StringBuilder(categoryID).append(",").append(yesterday)
					.append("viewed");
			sumToday += jedis.zcard(keyName.toString());
			sumYesterday += jedis.zcard(keyNameYesterday.toString());

			if (sumToday >= 30 || sumYesterday >= 30) {
				break;
			}
		}

		Set<Book> recommBooks = new HashSet<Book>();
		Set<String> recommBookIDs = new HashSet<String>();

		for (Map.Entry<String, Integer> entry : favoriteCategoryIDs) {
			String categoryID = entry.getKey();
			double weight = entry.getValue();
			StringBuilder keyName = new StringBuilder(categoryID).append(",");

			// 預設用today當key(書籍數量必須超過30)，其次以昨日當key(須超過30本)，不然直接以該商品的categoryID隨機取書並回傳
			if (sumToday >= 30) {
				keyName.append(today).append("viewed");
			} else if (sumYesterday >= 30) {
				keyName.append(yesterday).append("viewed");
			} else {
				List<Book> recommBookList = bookService.getByCategoryID(thisCategoryID);

				if (recommBookList.size() >= 30) {
					Random random = new Random();
					while (recommBooks.size() < 30) {
						recommBooks.add(recommBookList.get(random.nextInt(recommBookList.size())));
					}
				} else {
					recommBooks.addAll(recommBookList);
				}
//				return recommBooks;
			}

			// 查詢書籍統計資料並計算權重
			Set<Tuple> res = jedis.zrangeWithScores(keyName.toString(), 0, -1);

			List<Pair<String, Double>> bookWeights = new ArrayList<Pair<String, Double>>();

			res.forEach(tuple -> {
				String bookID = tuple.getElement();
				double viewedCount = tuple.getScore();
				bookWeights.add(new Pair<String, Double>(bookID, viewedCount * weight));
			});

			Object[] objs = new EnumeratedDistribution(bookWeights).sample(30);

			for (Object bookID : objs) {
				recommBookIDs.add(bookID.toString());
			}

			System.out.println(recommBookIDs.size());

			recommBooks.addAll(bookService.getByBookIDList(new ArrayList<String>(recommBookIDs)));
		}
		System.out.println(recommBooks.size());
//					return recommBooks;

//			return bookService.getByBookIDList(new ArrayList<String>(recommBookIDs));

// 歸還Redis連線資源
		jedis.close();
	}
}
