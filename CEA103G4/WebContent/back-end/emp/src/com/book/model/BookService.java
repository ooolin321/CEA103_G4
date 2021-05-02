package com.book.model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import com.category.model.Category;
import com.category.model.CategoryService;
import com.promo.model.Promo;
import com.promo.model.PromoService;
import com.promodetail.model.PromoDetailService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.exceptions.JedisException;
import tools.CountComparator;
import tools.JedisUtil;
import tools.RandomCollection;
import tools.StrUtil;

public class BookService {
	private final BookDAO bookDAO;
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

	public BookService(BookDAO bookDAO) {
		this.bookDAO = bookDAO;
	}

	public Optional<Book> addBook(String publisherID, String languageID, String categoryID, String bookName,
			String isbn, String author, Double listPrice, Double salePrice, Double bookBP, Integer isSold,
			Date publicationDate, Integer stock, Integer safetyStock, String bookIntro, String bookNameOriginal) {
		// insert時，salePricePromo, bookBPPromo, EffectivePromos皆為空(null)
		Book book = new Book(null, publisherID, languageID, categoryID, bookName, isbn, author, listPrice, salePrice,
				bookBP, isSold, publicationDate, stock, safetyStock, bookIntro, bookNameOriginal, Double.NaN,
				Double.NaN, null);
		bookDAO.insert(book);

		Map<String, String> map = new HashMap<String, String>();
		map.put("isbn", isbn);
		map.put("bookName", bookName);

		return Optional.ofNullable(bookDAO.advSearch(map).get(0));
	}

	// 傳入ID回傳Optional<null>或Optional<Book>，必須先用Optional<Book>
	// book.isPresent()確認裡面不是null後才能用Optional<Book> book.get()取出VO
	public Optional<Book> getByBookID(String bookID) {
		return getByBookID(bookID, false);
	}

	public Optional<Book> getByBookID(String bookID, boolean isFrontEnd) {
		Optional<Book> b = bookDAO.findByBookID(bookID);
		if (b.isPresent() && isFrontEnd) {
			// 若下架則改為Optional.ofNullable(null)
			if (b.get().getIsSold() == 0) {
				b = Optional.ofNullable(null);
			}
		}
		return b;
	}

	public List<Book> getByBookISBN(String isbn) {
		return getByBookISBN(isbn, false);
	}

	public List<Book> getByBookISBN(String isbn, boolean isFrontEnd) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("isbn", isbn);
		if (isFrontEnd) {
			map.put("isSold", "1");
		}
		return bookDAO.advSearch(map);
	}

	public List<Book> getByBookName(String bookName) {
		return getByBookName(bookName, false);
	}

	public List<Book> getByBookName(String bookName, boolean isFrontEnd) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bookName", bookName);
		if (isFrontEnd) {
			map.put("isSold", "1");
		}
		return bookDAO.advSearch(map);
	}

	public List<Book> getByAuthor(String author) {
		return getByAuthor(author, false);
	}

	public List<Book> getByAuthor(String author, boolean isFrontEnd) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("author", author);
		if (isFrontEnd) {
			map.put("isSold", "1");
		}
		return bookDAO.advSearch(map);
	}

	public List<Book> getByPublisherName(String publisherName) {
		return getByPublisherName(publisherName, false);
	}

	public List<Book> getByPublisherName(String publisherName, boolean isFrontEnd) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("publisherName", publisherName);
		if (isFrontEnd) {
			map.put("isSold", "1");
		}
		return bookDAO.advSearch(map);
	}

	public List<Book> getByCategoryID(String categoryID) {
		return getByCategoryID(categoryID, false);
	}

	// 僅查詢此categoryID
	public List<Book> getByCategoryID(String categoryID, boolean isFrontEnd) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("categoryID", categoryID);
		if (isFrontEnd) {
			map.put("isSold", "1");
		}
		return bookDAO.advSearch(map);
	}

	public List<Book> getByParentCategoryID(String parentCategoryID, CategoryService categoryService) {
		return getByParentCategoryID(parentCategoryID, categoryService, false);
	}

	// 查詢此categoryID與其下所有子類別
	public List<Book> getByParentCategoryID(String parentCategoryID, CategoryService categoryService,
			boolean isFrontEnd) {
		List<Book> books = new ArrayList<Book>();

		if (parentCategoryID == null) {
			return books;
		}

		// 將本身加入books
		books.addAll(getByCategoryID(parentCategoryID, isFrontEnd));

		List<Category> childCategories = categoryService.getByParentCategoryID(parentCategoryID);

		childCategories.forEach(childCategory -> {
			String childCategoryID = childCategory.getCategoryID();

			List<Category> grandChildCategories = categoryService.getByParentCategoryID(childCategoryID);
			// 有子類別且該子類別下有其他子類別
			grandChildCategories.forEach(grandChildCategory -> {
				books.addAll(getByCategoryID(grandChildCategory.getCategoryID(), isFrontEnd));
			});

			books.addAll(getByCategoryID(childCategoryID, isFrontEnd));
		});

		return books;
	}

	public List<Book> getByAdvSearch(Map<String, String> map) {
		return getByAdvSearch(map, false);
	}

	public List<Book> getByAdvSearch(Map<String, String> map, boolean isFrontEnd) {
		if (isFrontEnd) {
			map.put("isSold", "1");
		}
		return bookDAO.advSearch(map);
	}

	public List<Book> getAll() {
		return getAll(false);
	}

	public List<Book> getAll(boolean isFrontEnd) {
		Map<String, String> map = new HashMap<String, String>();
		if (isFrontEnd) {
			map.put("isSold", "1");
		}
		return getByAdvSearch(map);
	}

	public Optional<Book> updateBook(String bookID, String publisherID, String languageID, String categoryID,
			String bookName, String isbn, String author, Double listPrice, Double salePrice, Double bookBP,
			Integer isSold, Date publicationDate, Integer stock, Integer safetyStock, String bookIntro,
			String bookNameOriginal) {
		// 一般update時，無法修改salePricePromo, bookBPPromo, promoEndTime
		// effectivePromos，但建構需傳入參數，暫時傳入NaN和null，但實際update方法並不會更新這幾格
		Book book = new Book(bookID, publisherID, languageID, categoryID, bookName, isbn, author, listPrice, salePrice,
				bookBP, isSold, publicationDate, stock, safetyStock, bookIntro, bookNameOriginal, Double.NaN,
				Double.NaN, null);
		bookDAO.update(book);

		return getByBookID(bookID);
	}

	public Optional<Book> updateBookSalePricePromo(String bookID, Double salePricePromo) {
		bookDAO.updateSalePricePromo(bookID, salePricePromo);
		return getByBookID(bookID);
	}

	public List<Book> getByBookIDList(List<String> bookIDs) {
		return getByBookIDList(bookIDs, false);
	}

	public List<Book> getByBookIDList(List<String> bookIDs, boolean isFrontEnd) {
		return bookDAO.findByBookIDList(bookIDs, isFrontEnd);
	}

	public List<Book> updateBookSalePricePromoBatch(List<String> bookIDs, List<Double> salePricePromos) {
		if (bookIDs.size() == salePricePromos.size()) {
			bookDAO.updateSalePricePromoBatch(bookIDs, salePricePromos);
		}
		return getByBookIDList(bookIDs);
	}

	public List<Book> updateBookBPPromoBatch(List<String> bookIDs, List<Double> bookBPPromos) {
		if (bookIDs.size() == bookBPPromos.size()) {
			bookDAO.updateBPPromoBatch(bookIDs, bookBPPromos);
		}
		return getByBookIDList(bookIDs);
	}

	public List<Book> updateBookIsSoldBatch(List<String> bookIDs, Integer isSold) {
		bookDAO.updateIsSoldBatch(bookIDs, isSold);
		return getByBookIDList(bookIDs);
	}

	public List<Book> updateEffPromoBatch(List<String> bookIDs, List<String> effectivePromos) {
		bookDAO.updateEffPromoBatch(bookIDs, effectivePromos);
		return getByBookIDList(bookIDs);
	}

	public String updateEffPromos(String effectivePromos) {
		bookDAO.updateEffPromos(effectivePromos);
		return effectivePromos;
	}

	public List<Book> updateBooks(List<Book> books) {
		bookDAO.updateBatch(books);
		List<String> bookIDs = new ArrayList<String>();
		books.forEach(book -> bookIDs.add(book.getBookID()));
		return getByBookIDList(bookIDs);
	}

	// 前端專用方法
	public List<Book> getPopularBooks(int bookNum, int sampleNum) {
		// 取得Jedis連線資源
		Jedis jedis = null;
		List<Book> books = new ArrayList<Book>();
		try {
			JedisPool pool = JedisUtil.getJedisPool();
			jedis = pool.getResource();
			jedis.auth("123456");

			Set<String> bookIDs = new HashSet<String>();

			String keyName;
			String keyNameToday = "popularBooks" + LocalDate.now().toString();
			String keyNameYesterday = "popularBooks"
					+ DATE_FORMATTER.format(new Date(new java.util.Date().getTime() - 24 * 60 * 60 * 1000));

			// 計算今天或昨天的統計是否超過30筆
			long sumToday = jedis.zcard(keyNameToday.toString());
			long sumYesterday = jedis.zcard(keyNameYesterday.toString());

			// 預設用today當key(書籍數量必須超過30)，其次以昨日當key(須超過30本)，不然直接隨機取書並回傳
			if (sumToday >= bookNum) {
				keyName = keyNameToday;
			} else if (sumYesterday >= bookNum) {
				keyName = keyNameYesterday;
			} else {
				books.addAll(getByRandom(bookNum, true));
				// 歸還連線資源到Jedis連線池
				JedisUtil.closeJedis(jedis);
				return books;
			}

			// 查詢書籍統計資料並計算權重(只取總站熱門前sampleNum本)
			Set<Tuple> res;
			if (jedis.zcard(keyName.toString()) >= sampleNum) {
				res = jedis.zrangeWithScores(keyName.toString(), -sampleNum, -1);
			} else {
				res = jedis.zrangeWithScores(keyName.toString(), 0, -1);
			}

			// 歸還連線資源到Jedis連線池
			JedisUtil.closeJedis(jedis);

			RandomCollection rc = new RandomCollection();

			res.forEach(tuple -> {
				String bookID = tuple.getElement();
				double viewedCount = tuple.getScore();
				rc.add(viewedCount, bookID);
			});
			
			

			String bookID;
			while ((bookID = rc.next()) != null && bookIDs.size() < bookNum) {
				bookIDs.add(bookID);
			}

			books.addAll(getByBookIDList(new ArrayList<String>(bookIDs)));
			for (Iterator<Book> iterator = books.iterator(); iterator.hasNext();) {
				Book b = iterator.next();
				if (b.getIsSold() == 0) {
					iterator.remove();
				}
			}
		} catch (JedisException e) {
			// 歸還連線資源到Jedis連線池
			JedisUtil.closeJedis(jedis);
			e.printStackTrace();
		}

		return books;
	}

	// 前端專用方法
	public Set<Book> getRecommendedBooks(List<Book> recentViewedBooks, String thisBookID) {
		Jedis jedis = null;
		Set<Book> recommBooks = new HashSet<Book>();
		try {

			// 取得Jedis連線資源
			JedisPool pool = JedisUtil.getJedisPool();
			jedis = pool.getResource();
			jedis.auth("123456");

			String thisCategoryID = getByBookID(thisBookID).get().getCategoryID();

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
			String yesterday = DATE_FORMATTER.format(new Date(new java.util.Date().getTime() - 24 * 60 * 60 * 1000));

			// 計算今天或昨天的統計是否超過30筆
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

			Set<String> recommBookIDs = new HashSet<String>();
			RandomCollection rc = new RandomCollection();

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
					// 歸還連線資源到Jedis連線池
					JedisUtil.closeJedis(jedis);
					List<Book> recommBookList = getByCategoryID(thisCategoryID);

					if (recommBookList.size() >= 30) {
						Random random = new Random();
						while (recommBooks.size() < 30) {
							recommBooks.add(recommBookList.get(random.nextInt(recommBookList.size())));
						}
					} else {
						recommBooks.addAll(recommBookList);
					}

					for (Iterator<Book> iterator = recommBooks.iterator(); iterator.hasNext();) {
						Book b = iterator.next();
						if (b.getIsSold() == 0) {
							iterator.remove();
						}
					}

					return recommBooks;
				}

				// 查詢書籍統計資料並計算權重(只取該類別熱門前200本)
				Set<Tuple> res;
				if (jedis.zcard(keyName.toString()) >= 200) {
					res = jedis.zrangeWithScores(keyName.toString(), -200, -1);
				} else {
					res = jedis.zrangeWithScores(keyName.toString(), 0, -1);
				}

				// 歸還連線資源到Jedis連線池
				JedisUtil.closeJedis(jedis);

				res.forEach(tuple -> {
					String bookID = tuple.getElement();
					double viewedCount = tuple.getScore();
					rc.add(viewedCount * weight, bookID);
				});
			}

			String bookID;
			while ((bookID = rc.next()) != null && recommBookIDs.size() < 30) {
				recommBookIDs.add(bookID);
			}

			recommBooks.addAll(getByBookIDList(new ArrayList<String>(recommBookIDs)));
			for (Iterator<Book> iterator = recommBooks.iterator(); iterator.hasNext();) {
				Book b = iterator.next();
				if (b.getIsSold() == 0) {
					iterator.remove();
				}
			}
		} catch (JedisException e) {
			// 歸還連線資源到Jedis連線池
			JedisUtil.closeJedis(jedis);
			e.printStackTrace();
		}
		return recommBooks;
	}

	public void UpdateRedisViewCount(String bookID) {
		Jedis jedis = null;
		try {
			// 取得Jedis連線資源
			JedisPool pool = JedisUtil.getJedisPool();
			jedis = pool.getResource();
			jedis.auth("123456");

			// 本書當日瀏覽次數+1，key為當天日期 + "viwed"，score為當天被瀏覽次數，value為bookID。
			// Redis過期是以key為單位移除資料，為實現動態的7天統計，key設定和當天日期有關，而另一邊需設計排程器，每天計算仍有效的(7天內)瀏覽次數。
			LocalDate date = LocalDate.now();
			long current = System.currentTimeMillis();// 當前時間毫秒數
			long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();// 今天零點零分零秒的毫秒數
			jedis.zincrby(date + "viewed", 1, bookID);
			jedis.expire(date + "viewed", 7 * 24 * 60 * 60 - (int) ((current - zero) / 1000)); // 7天過期

			// 歸還連線資源到Jedis連線池
			JedisUtil.closeJedis(jedis);
		} catch (JedisException e) {
			// 歸還連線資源到Jedis連線池
			JedisUtil.closeJedis(jedis);
			e.printStackTrace();
		}
	}

	public List<Book> getByRandom(int num, boolean isFront) {
		return bookDAO.findByRandom(num, isFront);
	}

	// 前端專用方法
	public List<Book> getPromoBooks(int num, PromoDetailService promoDetailService, PromoService promoService) {
		Set<String> bookIDSet = new HashSet<String>();
		List<String> bookIDs = new ArrayList<String>();
		List<Promo> effPromos = new ArrayList<Promo>();

		promoService.getValidPromos().forEach(promo -> {
			effPromos.add(promo);
		});

		Promo randPromo = null;
		// 隨機選一個促銷事件，並從effPromos移除。但當選擇的randPromo對應promoDetails數量 <
		// num，持續選擇別的Promo並加入bookIDs
		while (bookIDSet.size() < num && (randPromo = getRandPromo(effPromos)) != null) {
			promoDetailService.getByPromoID(randPromo.getPromoID()).forEach(pd -> {
				bookIDSet.add(pd.getBookID());
			});
		}

		bookIDs.addAll(bookIDSet);
		// 脫離迴圈可能是bookIDs足夠或沒有effPromos了，故需判斷bookIDs長度
		if (bookIDs.size() > num) {
			bookIDs = bookIDs.subList(0, num);
		}

		return getByBookIDList(new ArrayList<String>(bookIDs), true);
	}

	private Promo getRandPromo(List<Promo> promos) {
		Promo randPromo = null;

		if (promos.size() > 0) {
			int randNum = new Random().nextInt(promos.size());
			randPromo = promos.get(randNum);
			promos.remove(randNum);
		}

		return randPromo;
	}

	// 前端專用方法
	public List<Book> getNewBooks(int num) {
		return bookDAO.findNewBooks(num, true);
	}

	public int getBookNum() {
		return bookDAO.findBookNum();
	}

	public List<String> getByBookIDLike(String bookID) {
		return bookDAO.findByBookIDLike(bookID);
	}

	public List<String> getByBookNameLike(String bookName) {
		return bookDAO.findByBookNameLike(bookName);
	}

	public List<String> getByAuthorLike(String author) {
		return bookDAO.findByAuthorLike(author);
	}

	public List<Book> getByPromoID(String promoID, boolean isFront) {
		return bookDAO.findByPromoID(promoID, isFront);
	}

	public List<Book> advSearchByRequest(HttpServletRequest request, CategoryService categoryService, boolean isFront) {
		String categoryID = request.getParameter("categoryID");
		Map<String, String> map = new HashMap<String, String>();
		map.put("categoryID", categoryID);
		map.put("bookName", StrUtil.tryToTrim(request.getParameter("bookName")));
		map.put("author", StrUtil.tryToTrim(request.getParameter("author")));
		map.put("publisherName", StrUtil.tryToTrim(request.getParameter("publisherName")));
		map.put("isbn", StrUtil.tryToTrim(request.getParameter("isbn")));
		map.put("salePriceMin", request.getParameter("realPriceMin"));
		map.put("salePriceMax", request.getParameter("realPriceMax"));
		map.put("discountMin", request.getParameter("discountMin"));
		map.put("discountMax", request.getParameter("discountMax"));
		map.put("isSold", request.getParameter("isSold"));
		map.put("publicationDateMin", request.getParameter("publicationDateMin"));
		map.put("publicationDateMax", request.getParameter("publicationDateMax"));

		// 首次查詢
		List<Book> books = getByAdvSearch(map, isFront);

		// 對子類別查詢
		if (categoryID != null) {
			Set<String> childCategoryIDs = categoryService.getAllChildCatIDs(categoryID);
			childCategoryIDs.forEach(childCategoryID -> {
				map.put("categoryID", childCategoryID);
				books.addAll(getByAdvSearch(map, isFront));
			});
		}

		return books;
	}
}
