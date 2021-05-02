package tests;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.book.model.Book;
import com.book.model.BookDAO;
import com.book.model.BookDAOImpl;
import com.book.model.BookService;
import com.promodetail.model.PromoDetail;
import com.promodetail.model.PromoDetailDAO;
import com.promodetail.model.PromoDetailDAOImpl;
import com.promodetail.model.PromoDetailService;

import oracle.jdbc.pool.OracleDataSource;

public class TestPromoDetailDAO {

	public static void main(String[] args) {
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
			PromoDetailDAO promoDetailDAO = new PromoDetailDAOImpl(dataSource);
			PromoDetailService promoDetailService = new PromoDetailService(promoDetailDAO);
			BookDAO bookDAO = new BookDAOImpl(dataSource);
			BookService bookService = new BookService(bookDAO);
			testAddPDBatch(promoDetailService, bookService);
//			testGetByPromoIDAndBookID(promoDetailService);
//			testGetByPromoID(promoDetailService);
//			testGetByBookID(promoDetailService);
//			testUpdatePD(promoDetailService);
//			testDeletePDByPromoIDAndBookID(promoDetailService);
//			testDeletePDByPromoID(promoDetailService);
//			testDeletePDByBookID(promoDetailService);
//			testUpdatePDBatch(promoDetailService, bookService);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testUpdatePDBatch(PromoDetailService promoDetailService, BookService bookService) {
		List<Book> books = bookService.getByPublisherName("歐萊禮");
		List<PromoDetail> promoDetails = new ArrayList<PromoDetail>();

		for (Book book : books) {
			promoDetails.add(new PromoDetail("PROMO0000000021", book.getBookID(), 85, 10));
		}
		
		promoDetailService.updatePromoDetailBatch(promoDetails);
	}

	private static void testDeletePDByPromoID(PromoDetailService promoDetailService) {
		promoDetailService.deletePDByPromoID("PROMO0000000021");
		
	}

	private static void testDeletePDByBookID(PromoDetailService promoDetailService) {
		promoDetailService.deletePDByBookID("B00000061434");
		
	}

	private static void testDeletePDByPromoIDAndBookID(PromoDetailService promoDetailService) {
		promoDetailService.deletePDByPromoIDAndBookID("PROMO0000000021", "B00000061420");
		
	}

	private static void testUpdatePD(PromoDetailService promoDetailService) {
		promoDetailService.updatePromoDetail("PROMO0000000021", "B00000061420", 80, 10);
	}

	private static void testGetByBookID(PromoDetailService promoDetailService) {
		System.out.println(promoDetailService.getByBookID("B00000057687").size());
		System.out.println(promoDetailService.getByBookID("PROMO0000000022").size());

	}

	private static void testGetByPromoID(PromoDetailService promoDetailService) {
		System.out.println(promoDetailService.getByPromoID("PROMO0000000021").size());
		System.out.println(promoDetailService.getByPromoID("PROMO0000000022").size());
	}

	private static void testGetByPromoIDAndBookID(PromoDetailService promoDetailService) {
		System.out.println(promoDetailService.getByPromoIDAndBookID("PROMO0000000021", "B00000057687").get());
		System.out.println(promoDetailService.getByPromoIDAndBookID("PROMO0000000022", "B00000057687").isPresent());
		
	}

	private static void testAddPDBatch(PromoDetailService promoDetailService, BookService bookService) {
		List<Book> books = bookService.getByPublisherName("歐萊禮");
		List<PromoDetail> promoDetails = new ArrayList<PromoDetail>();

		for (Book book : books) {
			promoDetails.add(new PromoDetail("PROMO0000000021", book.getBookID(), 85, 10));
		}

		promoDetailService.addPromoDetailBatch(promoDetails).forEach(pd -> System.out.println(pd));
//		books.forEach(book -> System.out.println(promoDetailService
//				.addPromoDetail("PROMO0000000021", book.getBookID(), 80, 10).get()));
	}

}
