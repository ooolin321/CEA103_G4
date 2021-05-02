package tests;

import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import com.promo.model.Promo;
import com.promo.model.PromoDAO;
import com.promo.model.PromoDAOImpl;
import com.promo.model.PromoService;

import oracle.jdbc.pool.OracleDataSource;

public class TestPromoDAO {

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
			PromoDAO promoDAO = new PromoDAOImpl(dataSource);
			PromoService promoService = new PromoService(promoDAO);
			
			String testTs = "2020-09-30 16:00:00";
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			ts = Timestamp.valueOf(testTs);
			System.out.println(ts);
//			testAddPromo(promoService);
//			testGetByPromoID(promoService);
//			testGetByPromoName(promoService);
//			testGetByPromoNameUnique(promoService);
//			testGetByPromoTimeRange(promoService);
//			testGetAll(promoService);
//			testUpdatePromo(promoService);
//			testIsPromoValid(promoService);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testUpdatePromo(PromoService promoService) {
		Timestamp ts1 = (new Timestamp(System.currentTimeMillis())).valueOf("2020-09-30 00:00:00");
		Timestamp ts2 = (new Timestamp(System.currentTimeMillis())).valueOf("2020-11-30 00:00:00");
		System.out.println(promoService.updatePromo("PROMO0000000021", "歐萊禮2020秋季特惠", ts1, ts2).get());
	}

	private static void testIsPromoValid(PromoService promoService) {
		List<Promo> listPromo = promoService.getAll();
		listPromo.forEach(promo -> System.out.println(promo + " - " + promoService.isPromoValid(promo.getPromoID())));
	}

	private static void testGetAll(PromoService promoService) {
		List<Promo> listPromo = promoService.getAll();
		listPromo.forEach(promo -> System.out.println(promo));
		
	}

	private static void testGetByPromoTimeRange(PromoService promoService) {
		Timestamp ts1 = (new Timestamp(System.currentTimeMillis())).valueOf("2020-01-30 00:00:00");
		Timestamp ts2 = (new Timestamp(System.currentTimeMillis())).valueOf("2020-12-30 00:00:00");
		Timestamp ts3 = (new Timestamp(System.currentTimeMillis())).valueOf("2019-01-01 00:00:00");
		Timestamp ts4 = (new Timestamp(System.currentTimeMillis())).valueOf("2019-12-31 00:00:00");
		Timestamp ts5 = (new Timestamp(System.currentTimeMillis())).valueOf("2018-01-01 00:00:00");
		Timestamp ts6 = (new Timestamp(System.currentTimeMillis())).valueOf("2018-12-31 00:00:00");
		System.out.println(ts1 + "\tTO\t" + ts2);
		List<Promo> listPromo = promoService.getByPromoTimeRange(ts1, ts2);
		listPromo.forEach(promo -> System.out.println(promo));
		System.out.println(ts3 + "\tTO\t" + ts2);
		List<Promo> listPromo2 = promoService.getByPromoTimeRange(ts3, ts2);
		listPromo2.forEach(promo -> System.out.println(promo));
		System.out.println(ts5 + "\tTO\t" + ts1);
		List<Promo> listPromo3 = promoService.getByPromoTimeRange(ts5, ts1);
		listPromo3.forEach(promo -> System.out.println(promo));
		System.out.println(ts1 + "\tTO\t" + ts1);
		List<Promo> listPromo4 = promoService.getByPromoTimeRange(ts1, ts1);
		listPromo4.forEach(promo -> System.out.println(promo));
	}

	private static void testGetByPromoNameUnique(PromoService promoService) {
		System.out.println(promoService.getByPromoNameUnique("測試促銷").isPresent());
		System.out.println(promoService.getByPromoNameUnique("測試促銷名稱1").get());
		
	}

	private static void testGetByPromoName(PromoService promoService) {
		List<Promo> listPromo = promoService.getByPromoName("測試促銷");
		listPromo.forEach(promo -> System.out.println(promo));
		
		List<Promo> listPromo2 = promoService.getByPromoName("測試促銷事件");
		listPromo2.forEach(promo -> System.out.println(promo));
	}
	
	private static void testGetByPromoID(PromoService promoService) {
		System.out.println(promoService.getByPromoID("PROMO0000000021").get());
		System.out.println(promoService.getByPromoID("PROMO000000002X").isPresent());
	}

	private static void testAddPromo(PromoService promoService) {
		String testDateTime = "2020-01-01T00:00";
		testDateTime = testDateTime.replace('T', ' ').replace('t', ' ') + ":00";
		Timestamp ts1 = (new Timestamp(System.currentTimeMillis())).valueOf(testDateTime);
		Timestamp ts2 = (new Timestamp(System.currentTimeMillis())).valueOf("2020-12-30 00:00:00");
		System.out.println(promoService.addPromo("測試促銷名稱1", ts1, ts2).get());
		
		Timestamp ts3 = (new Timestamp(System.currentTimeMillis())).valueOf("2019-01-01 00:00:00");
		Timestamp ts4 = (new Timestamp(System.currentTimeMillis())).valueOf("2019-12-31 00:00:00");
		System.out.println(promoService.addPromo("測試促銷名稱2", ts3, ts4).get());
		
		Timestamp ts5 = (new Timestamp(System.currentTimeMillis())).valueOf("2018-01-01 00:00:00");
		Timestamp ts6 = (new Timestamp(System.currentTimeMillis())).valueOf("2018-12-31 00:00:00");
		System.out.println(promoService.addPromo("測試促銷名稱3", ts5, ts6).get());

	}

}
