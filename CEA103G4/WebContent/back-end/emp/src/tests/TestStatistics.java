package tests;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import com.book.model.Book;
import com.book.model.BookDAO;
import com.book.model.BookDAOImpl;
import com.book.model.BookService;

import oracle.jdbc.pool.OracleDataSource;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;
import tools.JedisUtil;

public class TestStatistics {
	private static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) throws SQLException {

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

		// 執行時間資訊
		long start = System.currentTimeMillis();
		Date d = new Date(start);
		String threadName = Thread.currentThread().getName();
		StringBuffer sb = new StringBuffer("\n" + threadName + "瀏覽/銷售統計執行時間:\t");
		System.out.print(sb.append(TIME_FORMATTER.format(d)).append("\t").append(start));

		// 取得Redis連線
		JedisPool pool = JedisUtil.getJedisPool();
		Jedis jedis = pool.getResource();
		jedis.auth("123456");

		// 統計當下所有keyName中有viewd的書籍瀏覽數據，23:59:59執行任務後，先等10秒到換日
		try {
			System.out.println("\t等候10秒後更新瀏覽統計數據");
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Map<String, Double> viewedCount = new HashMap<String, Double>();

		// 換日後，從昨天開始統計7天
		for (int i = 1; i < 8; i++) {
			Date date = new Date(new Date().getTime() - i * 24 * 60 * 60 * 1000);
			String keyName = DATE_FORMATTER.format(date) + "viewed";
			Set<Tuple> res = jedis.zrangeWithScores(keyName, 0, -1);

			res.forEach(tuple -> {
				String bookID = tuple.getElement();
				double count = viewedCount.containsKey(bookID) ? viewedCount.get(bookID) : 0.0;
				viewedCount.put(bookID, count + tuple.getScore());
			});
		}

		// 要有bookIDs才能一次大量select(連線不關閉的狀態下)，取得對應的categoryID
		List<String> bookIDs = new ArrayList<String>(viewedCount.keySet());
		List<Book> books = bookService.getByBookIDList(bookIDs);
		Map<String, Map<String, Double>> hotBooksInCategories = new HashMap<String, Map<String, Double>>(); // CategoryID:(BookID:count)

		books.forEach(book -> {
			String bookID = book.getBookID();
			String categoryID = book.getCategoryID();
			Map<String, Double> oldMap = hotBooksInCategories.get(categoryID);

			// 尚無該categoryID
			if (oldMap == null) {
				Map<String, Double> map = new HashMap<String, Double>();
				map.put(bookID, viewedCount.get(bookID));
				hotBooksInCategories.put(book.getCategoryID(), map);
			} else {
				// 有categoryID，但目前尚無此bookID
				if (oldMap.get(bookID) == null) {
					oldMap.put(bookID, viewedCount.get(bookID));
					hotBooksInCategories.put(categoryID, oldMap);
				}
			}
		});

		// 建置當天新的統計Zset，key為categoryID，score為viewedCount，value為bookID
		String today = LocalDate.now().toString();
		hotBooksInCategories.forEach((categoryID, map) -> {
			StringBuilder keyName = new StringBuilder(categoryID).append(",").append(today).append("viewed");
			jedis.zadd(keyName.toString(), map);
			jedis.expire(keyName.toString(), (int) (25 * 60 * 60)); // 25小時過期
//			jedis.del(keyName.toString());
		});

		// 歸還Redis連線資源
		jedis.close();
	}

}
