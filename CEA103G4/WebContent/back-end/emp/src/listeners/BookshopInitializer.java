package listeners;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import com.book.model.BookDAO;
import com.book.model.BookDAOImpl;
import com.book.model.BookService;
import com.bookpic.model.BookPicDAO;
import com.bookpic.model.BookPicDAOImpl;
import com.bookpic.model.BookPicService;
import com.category.model.CategoryDAO;
import com.category.model.CategoryDAOImpl;
import com.category.model.CategoryService;
import com.language.model.LanguageDAO;
import com.language.model.LanguageDAO_interface;
import com.language.model.LanguageService;
import com.promo.model.PromoDAO;
import com.promo.model.PromoDAOImpl;
import com.promo.model.PromoService;
import com.promodetail.model.PromoDetailDAO;
import com.promodetail.model.PromoDetailDAOImpl;
import com.promodetail.model.PromoDetailService;
import com.publishers.model.PublisherDAO;
import com.publishers.model.PublisherDAO_interface;
import com.publishers.model.PublisherService;

import timers.PromoTimerTask;
import timers.StatisticsTimerTask;
import tools.JedisUtil;

@WebListener
public class BookshopInitializer implements ServletContextListener {
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
	private ScheduledExecutorService promoTimerService;
	private ScheduledExecutorService statisticsTimerService;

	public void contextInitialized(ServletContextEvent sce) {
		DataSource dataSource = getDataSource();
		ServletContext context = sce.getServletContext();

		BookDAO bookDAO = new BookDAOImpl(dataSource);
		BookService bookService = new BookService(bookDAO);
		context.setAttribute("bookService", bookService);

		CategoryDAO categoryDAO = new CategoryDAOImpl(dataSource);
		context.setAttribute("categoryService", new CategoryService(categoryDAO));

		BookPicDAO bookPicDAO = new BookPicDAOImpl(dataSource);
		context.setAttribute("bookPicService", new BookPicService(bookPicDAO));

		PromoDAO promoDAO = new PromoDAOImpl(dataSource);
		PromoService promoService = new PromoService(promoDAO);
		context.setAttribute("promoService", promoService);

		PromoDetailDAO promoDetailDAO = new PromoDetailDAOImpl(dataSource);
		PromoDetailService promoDetailService = new PromoDetailService(promoDetailDAO);
		context.setAttribute("promoDetailService", promoDetailService);

		PublisherDAO_interface publisherDAO_interface = new PublisherDAO(dataSource);
		context.setAttribute("publisherService", new PublisherService(publisherDAO_interface));

		LanguageDAO_interface languageDAO_interface = new LanguageDAO(dataSource);
		context.setAttribute("languageService", new LanguageService(languageDAO_interface));

		startPromoTimer(promoService, promoDetailService, bookService);
		startStatisticsTimer(bookService);
	}

	private DataSource getDataSource() {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			return (DataSource) envContext.lookup("jdbc/bookshop");
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}

	private void startPromoTimer(PromoService promoService, PromoDetailService promoDetailService,
			BookService bookService) {
		// 啟動當個小時的0分0秒
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		// 程式啟動起算到第一輪執行之間的延遲時間
		long initDelay = (c.getTimeInMillis() % 1800000 + 1800000) - (System.currentTimeMillis() % 1800000);

		// 測試用啟動初始更新(執行一次)
//		ScheduledExecutorService startUpService = Executors.newSingleThreadScheduledExecutor();
//		Date cur = new Date(System.currentTimeMillis());
//		System.out.println("促銷事件測試用初始啟動更新: " + FORMATTER.format(cur));
//		startUpService.schedule(new PromoTimerTask(promoService, promoDetailService, bookService), 0,
//				TimeUnit.MILLISECONDS);

		// 啟動起算下一輪時間開始每30分一次
		promoTimerService = Executors.newSingleThreadScheduledExecutor();
		Date nextRun = new Date(initDelay + System.currentTimeMillis());
		System.out.println("促銷事件更新器啟動時間: " + FORMATTER.format(nextRun));
		promoTimerService.scheduleAtFixedRate(new PromoTimerTask(promoService, promoDetailService, bookService),
				initDelay, 1000 * 60 * 30, TimeUnit.MILLISECONDS);
	}

	private void startStatisticsTimer(BookService bookService) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		long current = System.currentTimeMillis();// 當前時間毫秒數
		long zero = c.getTimeInMillis();// 今天零點零分零秒的毫秒數
		long twelve = zero + 24 * 60 * 60 * 1000 - 1;// 今天23點59分59秒的毫秒數

		// 程式啟動起算到第一輪執行之間的延遲時間
		long initDelay = twelve - current;

		// 測試用啟動初始更新(執行一次)
//		ScheduledExecutorService startUpService = Executors.newSingleThreadScheduledExecutor();
//		Date cur = new Date(System.currentTimeMillis());
//		System.out.println("瀏覽/銷售統計測試用初始啟動更新: " + FORMATTER.format(cur));
//		startUpService.schedule(new StatisticsTimerTask(bookService), 0,
//				TimeUnit.MILLISECONDS);

		// 啟動起算下一輪時間開始每天23:59:59執行一次
		statisticsTimerService = Executors.newSingleThreadScheduledExecutor();
		Date nextRun = new Date(initDelay + System.currentTimeMillis());
		System.out.println("瀏覽/銷售統計更新器下一輪執行時間: " + FORMATTER.format(nextRun));
		statisticsTimerService.scheduleAtFixedRate(new StatisticsTimerTask(bookService), initDelay, 1000 * 60 * 60 * 24,
				TimeUnit.MILLISECONDS);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// 關閉兩個Timer
		promoTimerService.shutdownNow();
		try {
			while (!promoTimerService.awaitTermination(2, TimeUnit.SECONDS)) {
				System.out.println("promoTimerService執行緒池未關閉");
			}
		} catch (InterruptedException e) {
			System.out.println("promoTimerService執行緒池awaitTermination出例外");
			e.printStackTrace();
		}
		System.out.println("promoTimerService執行緒池關閉");

		statisticsTimerService.shutdownNow();
		try {
			while (!statisticsTimerService.awaitTermination(2, TimeUnit.SECONDS)) {
				System.out.println("statisticsTimerService執行緒池未關閉");
			}
		} catch (InterruptedException e) {
			System.out.println("statisticsTimerService執行緒池awaitTermination出例外");
			e.printStackTrace();
		}
		System.out.println("statisticsTimerService執行緒池關閉");
		
		// 關閉Jedis連線池，避免commons-pool-evictor-thread不能關閉
		JedisUtil.shutdownJedisPool();
	}

}
