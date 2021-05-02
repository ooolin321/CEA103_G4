package tests;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.book.model.Book;
import com.book.model.BookDAO;
import com.book.model.BookDAOImpl;
import com.book.model.BookService;
import com.category.model.CategoryDAO;
import com.category.model.CategoryDAOImpl;
import com.category.model.CategoryService;
import com.promo.model.PromoDAO;
import com.promo.model.PromoDAOImpl;
import com.promo.model.PromoService;
import com.promodetail.model.PromoDetailDAO;
import com.promodetail.model.PromoDetailDAOImpl;
import com.promodetail.model.PromoDetailService;

import oracle.jdbc.pool.OracleDataSource;

public class TestBookDAO {

	public static void main(String args[]) throws ParseException {
		OracleDataSource ds;
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
			BookService bookService = new BookService(bookDAO);

			CategoryDAO categoryDAO = new CategoryDAOImpl(dataSource);
			CategoryService categoryService = new CategoryService(categoryDAO);

			PromoDAO promoDAO = new PromoDAOImpl(dataSource);
			PromoService promoService = new PromoService(promoDAO);

			PromoDetailDAO promoDetailDAO = new PromoDetailDAOImpl(dataSource);
			PromoDetailService promoDetailService = new PromoDetailService(promoDetailDAO);

//			Double.parseDouble("a");
//			Integer.valueOf("a");
//			testAddBook(bookService);
//			testGetByBookID(bookService);
//			testGetByISBN(bookService);
//			testGetByBookName(bookService);
//			testGetByAuthor(bookService);
//			testGetByPublisherName(bookService);
//			testGetByCategoryID(bookService);
//			testGetByAdvSearch(bookService);
//			testGetAll(bookService);
//			testUpdateBook(bookService);
//			testUpdateBookSalePricePromo(bookService);
//			testUpdateBookSalePricePromoBatch(bookService);
//			testUpdateBookIsSoldBatch(bookService);
//			testgetByParentCategoryID(bookService, categoryService);
			testgetPromoBooks(bookService, 30, promoDetailService, promoService);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testgetPromoBooks(BookService bookService, int num, PromoDetailService promoDetailService,
			PromoService promoService) {
		bookService.getPromoBooks(num, promoDetailService, promoService);
	}

	private static void testgetByParentCategoryID(BookService bookService, CategoryService categoryService) {
		String parentCategoryID = "CAT0106001";
		bookService.getByParentCategoryID(parentCategoryID, categoryService);
	}

	private static void tsetUpdateBookIsSoldBatch(BookService bookService) {
		List<Book> books = bookService.getByBookName("java");
		List<String> bookIDs = new ArrayList<String>();

		for (int i = 0; i < books.size(); i++) {
			bookIDs.add(books.get(i).getBookID());
		}

		bookService.updateBookIsSoldBatch(bookIDs, 1).forEach(book -> System.out.println(book));
	}

	private static void testGetByISBN(BookService bookService) {
		List<Book> books = bookService.getByBookISBN("10237267");
		books.forEach(book -> System.out.println(book));
	}

	private static void testGetByBookID(BookService bookService) {
		System.out.println(bookService.getByBookID("B00000000001").get());
	}

	private static void testUpdateBookSalePricePromo(BookService bookService) {
		System.out.println(bookService.updateBookSalePricePromo("B00000000001", Double.NaN).get());
	}

	private static void testAddBook(BookService bookService) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			java.util.Date parsed;
			parsed = format.parse("20000920");
			java.sql.Date dateSQL = new java.sql.Date(parsed.getTime());
			System.out.println(bookService.addBook("P000000001", "LAN0000001", "CAT0101000", "測試書名11", "TEST_ISBN",
					"測試作者", 500.0, 400.0, 10.0, 1, dateSQL, 10, 5, "測試書籍簡介文章CLOB", "測試書名原文").get());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void testGetByBookName(BookService bookService) {
		long start = System.nanoTime();
		List<Book> books = bookService.getByBookName("JAva");
		books.forEach(book -> System.out.println(book));
		System.out.println("Cost " + (System.nanoTime() - start) + " ns");
		List<Book> books2 = bookService.getByBookName("測試書名");
		books2.forEach(book -> System.out.println(book));
	}

	private static void testGetByAuthor(BookService bookService) {
		List<Book> books = bookService.getByAuthor("名師");
		books.forEach(book -> System.out.println(book.getAuthor()));
	}

	private static void testGetByPublisherName(BookService bookService) {
		List<Book> books = bookService.getByPublisherName("資訊");
		books.forEach(book -> System.out.println(book.getPublisherID()));
	}

	private static void testGetByCategoryID(BookService bookService) {
		List<Book> books = bookService.getByCategoryID("CAT1103000");
		books.forEach(book -> System.out.println(book.getCategoryID() + " " + book.getBookName()));
	}

	private static void testGetByAdvSearch(BookService bookService) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("publisherName", "資訊");
		map.put("salePriceMax", "600");
		map.put("salePriceMin", "500");
		map.put("author", "黃");
		map.put("publicationDateMax", "2015-01-01");
		map.put("discountMax", "92");
		map.put("bookName", "JAva");
		map.put("isSold", "1");
		List<Book> books = bookService.getByAdvSearch(map);
		books.forEach(book -> System.out.println(book));
	}

	private static void testGetAll(BookService bookService) {
		List<Book> books = bookService.getAll();
		books.forEach(book -> System.out.println(book));
	}

	private static void testUpdateBook(BookService bookService) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			java.util.Date parsed;
			parsed = format.parse("20000920");
			java.sql.Date dateSQL = new java.sql.Date(parsed.getTime());
			bookService.updateBook("B00000096206", "P000000001", "LAN0000001", "CAT0101000", "測試書名4", "TEST_ISBN",
					"測試作者", 600.0, 400.0, 5.0, 0, dateSQL, 20, 10, "測試書籍簡介文章CLOB", "測試書名原文");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void testUpdateBookSalePricePromoBatch(BookService bookService) {
		List<Book> books = bookService.getByBookName("java");
		List<String> bookIDs = new ArrayList<String>();
		List<Double> salePricePromos = new ArrayList<Double>();

		for (int i = 0; i < books.size(); i++) {
			bookIDs.add(books.get(i).getBookID());
			salePricePromos.add(Double.NaN);
		}

		bookService.updateBookSalePricePromoBatch(bookIDs, salePricePromos).forEach(book -> System.out.println(book));
	}

}
