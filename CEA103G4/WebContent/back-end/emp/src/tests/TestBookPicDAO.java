package tests;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;

import com.book.model.*;
import com.bookpic.model.BookPicDAO;
import com.bookpic.model.BookPicDAOImpl;
import com.bookpic.model.BookPicService;
import com.bookpic.model.BookPicture;

import oracle.jdbc.pool.OracleDataSource;

public class TestBookPicDAO {

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
			BookPicDAO bookPicDAO = new BookPicDAOImpl(dataSource);
			BookPicService bookPicService = new BookPicService(bookPicDAO);

//			testAddBookPic(bookPicService);
//			testGetFirstPicByBookID(bookPicService);
//			testGetLastPicNameByBookID(bookPicService);
//			testGetByBookID(bookPicService);
//			testUpdateBookPicture(bookPicService);
//			testDeletePicsByBookID(bookPicService);
//			testDeletePicByBookIDAndPicName(bookPicService);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void testDeletePicByBookIDAndPicName(BookPicService bookPicService) {
		String bookID = "B00000000001";
		String bookPicName = "5.jpg";
		bookPicService.deletePicByBookIDAndPicName(bookID, bookPicName);
	}

	private static void testUpdateBookPicture(BookPicService bookPicService) throws IOException {
		File bookPic = new File("C:\\Users\\User\\Desktop\\AlbumArt_{24BCEC11-892D-4E78-85A6-F542D5F9402B}_Large.jpg");
		String bookID = "B00000000001";
		String bookPicName = "4.jpg";
		BookPicture bookPicture = bookPicService.updateBookPicture(bookID, bookPicName, FileUtils.readFileToByteArray(bookPic)).get();
		System.out.println(bookPicture);
	}

	private static void testGetByBookID(BookPicService bookPicService) {
		String bookID = "B00000000001";
		List<BookPicture> listBookPic = bookPicService.getByBookID(bookID);
		listBookPic.forEach(bookPic -> System.out.println(bookPic));
		
		String bookID2 = "B00000000002";
		List<BookPicture> listBookPic2 = bookPicService.getByBookID(bookID2);
		listBookPic2.forEach(bookPic -> System.out.println(bookPic));
	}

	private static void testGetLastPicNameByBookID(BookPicService bookPicService) {
		String bookID = "B00000000001";
		System.out.println(bookPicService.getLastPicNameByBookID(bookID));
		
		String bookID2 = "B00000000002";
		System.out.println(bookPicService.getLastPicNameByBookID(bookID2));
	}

	private static void testGetFirstPicByBookID(BookPicService bookPicService) {
		String bookID = "B00000000001";
		BookPicture bookPicture = bookPicService.getFirstPicByBookID(bookID).get();
		System.out.println(bookPicture);
		
		String bookID2 = "B00000000002";
		BookPicture bookPicture2 = bookPicService.getFirstPicByBookID(bookID2).get();
		System.out.println(bookPicture2);}

	private static void testDeletePicsByBookID(BookPicService bookPicService) {
		String bookID = "B00000000001";
		bookPicService.deletePicsByBookID(bookID);
	}

	private static void testAddBookPic(BookPicService bookPicService) throws IOException {
		File bookPic = new File("C:\\Users\\User\\Desktop\\images\\11100004754\\0.jpg");
		String bookID = "B00000000001";
		BookPicture bookPicture = bookPicService.addBookPic(bookID, bookPic.getName(), FileUtils.readFileToByteArray(bookPic)).get();
		System.out.println(bookPicture);
	}

}
