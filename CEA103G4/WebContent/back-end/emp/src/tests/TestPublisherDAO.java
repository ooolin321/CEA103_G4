package tests;

import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import com.book.model.BookService;
import com.publishers.model.Publisher;
import com.publishers.model.PublisherDAO;
import com.publishers.model.PublisherDAO_interface;
import com.publishers.model.PublisherService;

import oracle.jdbc.pool.OracleDataSource;

public class TestPublisherDAO {

	public static void main(String[] args) throws NamingException, SQLException {
		OracleDataSource ds;
		try {
//			測試用dataSource
//			ds = new OracleDataSource();
//			ds.setDriverType("thin");
//			ds.setServerName("localhost");
//			ds.setPortNumber(1521);
//			ds.setDatabaseName("XE"); // Oracle SID
//			ds.setUser("BOOKSHOP");
//			ds.setPassword("123456");			
//			DataSource dataSource = (DataSource) ds;
//			PublisherDAO_interface promoDAOI = new PublisherDAO(dataSource);

			
//			我會設置監聽器，寫code時請寫
//			PublisherService publisherService = (PublisherService) getServletContext().getAttribute("publisherService");
//			List<Publisher> publishers = publisherService.getByPublisherName("資訊");

//			publishers.forEach(p -> System.out.println(p.getPublisher_Name()));
		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
