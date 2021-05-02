package tests;

import javax.sql.DataSource;

import com.language.model.LanguageDAO;
import com.language.model.LanguageDAO_interface;
import com.language.model.LanguageService;

import oracle.jdbc.pool.OracleDataSource;

public class TestLanguageDAO {

	public static void main(String[] args) {
		OracleDataSource ds;
		try {
//			測試用dataSource
			ds = new OracleDataSource();
			ds.setDriverType("thin");
			ds.setServerName("localhost");
			ds.setPortNumber(1521);
			ds.setDatabaseName("XE"); // Oracle SID
			ds.setUser("BOOKSHOP");
			ds.setPassword("123456");
			DataSource dataSource = (DataSource) ds;
			LanguageDAO_interface languagesDAOI = new LanguageDAO(dataSource);
			LanguageService languageService = new LanguageService(languagesDAOI);
			languageService.getAll().forEach(lan -> System.out.println(lan.getLanguage_Name()));

//			我會設置監聽器，寫code時請寫
//			LanguageService languageService = (LanguageService) getServletContext().getAttribute("languageService");
//			List<Language> languages = languageService.getAll();

//			languages.forEach(lan -> System.out.println(lan.getLanguage_Name()));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
