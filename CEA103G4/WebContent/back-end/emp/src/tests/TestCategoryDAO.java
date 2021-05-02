package tests;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import com.book.model.*;
import com.category.model.Category;
import com.category.model.CategoryDAO;
import com.category.model.CategoryDAOImpl;
import com.category.model.CategoryService;

import oracle.jdbc.pool.OracleDataSource;

public class TestCategoryDAO {

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
			CategoryDAO categoryDAO = new CategoryDAOImpl(dataSource);
			CategoryService categoryService = new CategoryService(categoryDAO);

			testAddCategory(categoryService);
//			testGetByCategoryID(categoryService);
//			testGetByCategoryName(categoryService);
//			testGetPareantCategoryByChildCategoryName(categoryService);
//			testGetByParentCategoryID(categoryService);
//			testGetAll(categoryService);
//			testGetCurrentLevelMaxCategoryID(categoryService);
//			testUpdateCategory(categoryService);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testAddCategory(CategoryService categoryService) {
		try {
			// Expected
			// parent - level
			// null - 0
			categoryService.addCategory("測試第一類別 ");
			// CAT2200000 - 1
			categoryService.addCategory("測試第一類別, 測試第二類別 ");
			// CAT1900000 - 1
			categoryService.addCategory("科學,測試第二類別 ");
			// CAT1902000 - 2
			categoryService.addCategory("科學,數學,測試類別1");
			// CAT1902000 - 2
			categoryService.addCategory("科學 , 數學 ,測 試類別2 ");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void testGetByCategoryID(CategoryService categoryService) {
		Optional<Category> category = categoryService.getByCategoryID("CAT1802000");
		System.out.println(category.get());
	}

	private static void testGetByCategoryName(CategoryService categoryService) {
		long start = System.nanoTime();
		Optional<Category> category = categoryService.getByCategoryName("科學");
		System.out.println(category.get());
		Optional<Category> category2 = categoryService.getByCategoryName("科學123");
		System.out.println(category2.orElse(null));
		System.out.println("Cost " + (System.nanoTime() - start) + " ns");
	}

	private static void testGetPareantCategoryByChildCategoryName(CategoryService categoryService) {
		Optional<Category> category1 = categoryService.getParentCategoryByChildCategoryName("科學");
		System.out.println(category1.isPresent());
		Optional<Category> category2 = categoryService.getParentCategoryByChildCategoryName("傳記,明星");
		System.out.println(category2.get());
		Optional<Category> category3 = categoryService.getParentCategoryByChildCategoryName("傳記,明星,abc");
		System.out.println(category3.get());
		Optional<Category> category4 = categoryService.getParentCategoryByChildCategoryName("");
		System.out.println(category4.isPresent());
		Optional<Category> category5 = categoryService.getParentCategoryByChildCategoryName("abv");
		System.out.println(category5.isPresent());

	}

	private static void testGetByParentCategoryID(CategoryService categoryService) {
		List<Category> categories = categoryService.getByParentCategoryID("CAT0100000");
		categories.forEach(category -> System.out.println(category));
		List<Category> categories2 = (categoryService.getByParentCategoryID(null));
		categories2.forEach(category -> System.out.println(category));
		List<Category> categories3 = (categoryService.getByParentCategoryID("abc"));
		categories3.forEach(category -> System.out.println(category));
	}

	private static void testGetAll(CategoryService categoryService) {
		List<Category> categories = categoryService.getAll();
		categories.forEach(category -> System.out.println(category));
	}

	private static void testGetCurrentLevelMaxCategoryID(CategoryService categoryService) {
		Optional<Category> category1 = categoryService.getCurrentLevelMaxCategoryID("CAT0100000", 1);
		System.out.println(category1.get());
		Optional<Category> category2 = categoryService.getCurrentLevelMaxCategoryID(null, 0);
		System.out.println(category2.get());
		Optional<Category> category3 = categoryService.getCurrentLevelMaxCategoryID("CAT0101000", 2);
		System.out.println(category3.isPresent());
	}
		
	private static void testUpdateCategory(CategoryService categoryService) {
		try {
			System.out.println(
					categoryService.updateCategory("CAT2105000", "傳記,文學家", "CAT2100000").get());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
