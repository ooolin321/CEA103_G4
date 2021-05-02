package com.book.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

public class BookDAOImpl implements BookDAO {
	private DataSource ds;

	public BookDAOImpl(DataSource dataSource) {
		this.ds = dataSource;
	}

	private static final String INSERT_STMT = "INSERT INTO BOOKS(BOOK_ID,PUBLISHER_ID,LANGUAGE_ID,CATEGORY_ID,BOOK_NAME,ISBN,AUTHOR,LIST_PRICE,SALE_PRICE,BOOK_BP,IS_SOLD,PUBLICATION_DATE,STOCK,SAFETY_STOCK,BOOK_INTRO,BOOK_NAME_ORIGINAL) VALUES ('B' || lpad(BOOK_ID_SEQ.NEXTVAL, 11, '0'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String FIND_BY_BOOK_ID_STMT = "SELECT * FROM BOOKS WHERE BOOK_ID = ?";
	private static final String FIND_BY_BOOK_ID_STMT_FRONT = "SELECT * FROM BOOKS WHERE BOOK_ID = ? AND IS_SOLD = 1";
	private static final List<String> ADV_SEARCH_CONDITIONS = Arrays.asList("publisherName", "bookName", "author",
			"categoryID", "publicationDateMin", "publicationDateMax", "salePriceMin", "salePriceMax", "discountMin",
			"discountMax", "isbn", "isSold");
	private static final String ADV_SEARCH_STMT = "SELECT * FROM BOOKS B WHERE (? IS NULL OR EXISTS (SELECT 1 FROM PUBLISHERS P WHERE P.PUBLISHER_ID = B. PUBLISHER_ID AND upper(PUBLISHER_NAME) LIKE '%'|| upper(?) || '%'))"
			+ "AND (? IS NULL OR upper(BOOK_NAME) LIKE '%'|| upper(?) || '%')"
			+ "AND (? IS NULL OR upper(AUTHOR) LIKE '%'|| upper(?) || '%')" + "AND (? IS NULL OR CATEGORY_ID = ?)"
			+ "AND (? IS NULL OR PUBLICATION_DATE >= to_date(?, 'yyyy-mm-dd'))"
			+ "AND (? IS NULL OR PUBLICATION_DATE <= to_date(?, 'yyyy-mm-dd'))"
			+ "AND (? IS NULL OR LEAST(SALE_PRICE, NVL(SALE_PRICE_PROMO,SALE_PRICE)) >= ?)"
			+ "AND (? IS NULL OR LEAST(SALE_PRICE, NVL(SALE_PRICE_PROMO,SALE_PRICE)) <= ?)"
			+ "AND (? IS NULL OR LEAST(SALE_PRICE, NVL(SALE_PRICE_PROMO,SALE_PRICE))/LIST_PRICE*100 >= ?)"
			+ "AND (? IS NULL OR LEAST(SALE_PRICE, NVL(SALE_PRICE_PROMO,SALE_PRICE))/LIST_PRICE*100 <= ?)"
			+ "AND (? IS NULL OR ISBN = ?)" + "AND (? IS NULL OR IS_SOLD = ?)";
	private static final String UPDATE_STMT = "UPDATE BOOKS SET PUBLISHER_ID = ?, LANGUAGE_ID = ?, CATEGORY_ID = ?, BOOK_NAME = ?, ISBN = ?, AUTHOR = ?, LIST_PRICE = ?, SALE_PRICE = ?, BOOK_BP = ?, IS_SOLD = ?, PUBLICATION_DATE = ?, STOCK = ?, SAFETY_STOCK = ?, BOOK_INTRO = ?, BOOK_NAME_ORIGINAL = ? WHERE BOOK_ID = ?";
	private static final String UPDATE_SALE_PRICE_PROMO_STMT = "UPDATE BOOKS SET SALE_PRICE_PROMO = ? WHERE BOOK_ID = ?";
	private static final String UPDATE_BOOK_BP_PROMO_STMT = "UPDATE BOOKS SET BOOK_BP_PROMO = ? WHERE BOOK_ID = ?";
	private static final String UPDATE_IS_SOLD_STMT = "UPDATE BOOKS SET IS_SOLD = ? WHERE BOOK_ID = ?";
	private static final String UPDATE_EFFECTIVE_PROMOS = "UPDATE BOOKS SET EFFECTIVE_PROMOS = ?　WHERE BOOK_ID = ?";
	private static final String UPDATE_BATCH_STMT = "UPDATE BOOKS SET PUBLISHER_ID = ?, LANGUAGE_ID = ?, CATEGORY_ID = ?, BOOK_NAME = ?, ISBN = ?, AUTHOR = ?, LIST_PRICE = ?, SALE_PRICE = ?, BOOK_BP = ?, IS_SOLD = ?, PUBLICATION_DATE = ?, STOCK = ?, SAFETY_STOCK = ?, BOOK_INTRO = ?, BOOK_NAME_ORIGINAL = ?, SALE_PRICE_PROMO = ?, BOOK_BP_PROMO = ?, EFFECTIVE_PROMOS = ? WHERE BOOK_ID = ?";
	private static final String FIND_BY_RANDOM_STMT = "SELECT * FROM(SELECT * FROM BOOKS ORDER BY dbms_random.value) WHERE ROWNUM <= ?";
	private static final String FIND_BY_RANDOM_STMT_FRONT = "SELECT * FROM(SELECT * FROM BOOKS WHERE IS_SOLD = 1 ORDER BY dbms_random.value) WHERE ROWNUM <= ?";
	private static final String FIND_NEW_STMT = "SELECT * FROM(SELECT * FROM BOOKS ORDER BY PUBLICATION_DATE DESC NULLS LAST) WHERE ROWNUM <= ?";
	private static final String GET_COUNT_STMT = "SELECT COUNT(1) FROM BOOKS";
	private static final String FIND_BY_BOOK_ID_LIKE_STMT = "SELECT BOOK_ID FROM BOOKS WHERE BOOK_ID LIKE (upper(?) || '%') AND ROWNUM <= 20";
	private static final String FIND_BY_BOOK_NAME_LIKE_STMT = "SELECT BOOK_NAME FROM BOOKS WHERE UPPER(BOOK_NAME) LIKE (UPPER(?) || '%') AND ROWNUM <= 10";
	private static final String FIND_BY_AUTHOR_LIKE_STMT = "SELECT AUTHOR FROM BOOKS WHERE UPPER(AUTHOR) LIKE (UPPER(?) || '%') AND ROWNUM <= 10";
	private static final String FIND_BY_PROMO_ID_STMT = "SELECT * FROM BOOKS WHERE BOOK_ID IN (SELECT BOOK_ID FROM PROMOTION_DETAILS WHERE PROMO_ID = ?)";
	private static final String FIND_BY_PROMO_ID_STMT_FRONT = "SELECT * FROM BOOKS WHERE BOOK_ID IN (SELECT BOOK_ID FROM PROMOTION_DETAILS WHERE PROMO_ID = ?) AND IS_SOLD = 1";

	@Override
	public void insert(Book book) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, book.getPublisherID());
			pstmt.setString(2, book.getLanguageID());
			pstmt.setString(3, book.getCategoryID());
			pstmt.setString(4, book.getBookName());
			pstmt.setString(5, book.getIsbn());
			pstmt.setString(6, book.getAuthor());
			pstmt.setDouble(7, book.getListPrice());
			pstmt.setDouble(8, book.getSalePrice());
			pstmt.setDouble(9, book.getBookBP());
			pstmt.setInt(10, book.getIsSold());
			pstmt.setDate(11, book.getPublicationDate());
			pstmt.setInt(12, book.getStock());
			pstmt.setInt(13, book.getSafetyStock());
			pstmt.setString(14, book.getBookIntro());
			pstmt.setString(15, book.getBookNameOriginal());

			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public Optional<Book> findByBookID(String bookID) {
		Book book = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_BOOK_ID_STMT);
			pstmt.setString(1, bookID);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				book = setBook(rs);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return Optional.ofNullable(book);
	}

	@Override
	public List<Book> advSearch(Map<String, String> map) {
		List<Book> listBook = new ArrayList<Book>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] conditions = new String[ADV_SEARCH_CONDITIONS.size()];

		for (int i = 0; i < conditions.length; i++) {
			conditions[i] = map.get(ADV_SEARCH_CONDITIONS.get(i));
		}

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(ADV_SEARCH_STMT);

			for (int i = 1; i < conditions.length * 2 + 1; i += 2) {
				pstmt.setString(i, conditions[i / 2]);
				pstmt.setString(i + 1, conditions[i / 2]);
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Book book = setBook(rs);
				listBook.add(book);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return listBook;
	}

	@Override
	public void update(Book book) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, book.getPublisherID());
			pstmt.setString(2, book.getLanguageID());
			pstmt.setString(3, book.getCategoryID());
			pstmt.setString(4, book.getBookName());
			pstmt.setString(5, book.getIsbn());
			pstmt.setString(6, book.getAuthor());
			pstmt.setDouble(7, book.getListPrice());
			pstmt.setDouble(8, book.getSalePrice());
			pstmt.setDouble(9, book.getBookBP());
			pstmt.setInt(10, book.getIsSold());
			pstmt.setDate(11, book.getPublicationDate());
			pstmt.setInt(12, book.getStock());
			pstmt.setInt(13, book.getSafetyStock());
			pstmt.setString(14, book.getBookIntro());
			pstmt.setString(15, book.getBookNameOriginal());
			pstmt.setString(16, book.getBookID());

			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void updateSalePricePromo(String bookID, Double salePricePromo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_SALE_PRICE_PROMO_STMT);
			if (salePricePromo.isNaN()) {
				pstmt.setString(1, null);
			} else {
				pstmt.setDouble(1, salePricePromo);
			}
			pstmt.setString(2, bookID);

			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void updateSalePricePromoBatch(List<String> bookIDs, List<Double> salePricePromos) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE_SALE_PRICE_PROMO_STMT);

			for (int i = 0; i < bookIDs.size(); i++) {
				Double salePricePromo = salePricePromos.get(i);
				String bookID = bookIDs.get(i);

				if (salePricePromo.isNaN()) {
					pstmt.setString(1, null);
				} else {
					pstmt.setDouble(1, salePricePromo);
				}
				pstmt.setString(2, bookID);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			con.commit();
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void updateIsSoldBatch(List<String> bookIDs, Integer isSold) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE_IS_SOLD_STMT);

			for (int i = 0; i < bookIDs.size(); i++) {
				String bookID = bookIDs.get(i);
				pstmt.setInt(1, isSold);
				pstmt.setString(2, bookID);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			con.commit();
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public List<Book> findByBookIDList(List<String> bookIDs, boolean isFront) {
		List<Book> listBook = new ArrayList<Book>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			if (isFront) {
				pstmt = con.prepareStatement(FIND_BY_BOOK_ID_STMT_FRONT);
			} else {
				pstmt = con.prepareStatement(FIND_BY_BOOK_ID_STMT);
			}

			for (String bookID : bookIDs) {
				pstmt.setString(1, bookID);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					Book book = setBook(rs);
					listBook.add(book);
				}
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return listBook;
	}

	@Override
	public void updateBPPromoBatch(List<String> bookIDs, List<Double> bookBPPromos) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE_BOOK_BP_PROMO_STMT);

			for (int i = 0; i < bookIDs.size(); i++) {
				Double bookBPPromo = bookBPPromos.get(i);
				String bookID = bookIDs.get(i);

				if (bookBPPromo.isNaN()) {
					pstmt.setString(1, null);
				} else {
					pstmt.setDouble(1, bookBPPromo);
				}
				pstmt.setString(2, bookID);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			con.commit();
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	public void updateEffPromoBatch(List<String> bookIDs, List<String> effectivePromos) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE_EFFECTIVE_PROMOS);

			for (int i = 0; i < bookIDs.size(); i++) {
				String effPromo = effectivePromos.get(i);
				String bookID = bookIDs.get(i);

				// 目前有效的促銷事件都記錄在這本書上，故不可被批量更新，只能被促銷事件排程器存取
				if ("B00000000001".equals(bookID)) {
					continue;
				}

				pstmt.setString(1, effPromo);
				pstmt.setString(2, bookID);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			con.commit();
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void updateBatch(List<Book> books) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE_BATCH_STMT);

			for (int i = 0; i < books.size(); i++) {
				Book book = books.get(i);
				Double salePricePromo = book.getSalePricePromo();
				Double bookBPPromo = book.getBookBPPromo();

				// 目前有效的促銷事件都記錄在這本書上，故不可被批量更新，只能被促銷事件排程器存取
				if ("B00000000001".equals(book.getBookID())) {
					continue;
				}

				pstmt.setString(1, book.getPublisherID());
				pstmt.setString(2, book.getLanguageID());
				pstmt.setString(3, book.getCategoryID());
				pstmt.setString(4, book.getBookName());
				pstmt.setString(5, book.getIsbn());
				pstmt.setString(6, book.getAuthor());
				pstmt.setDouble(7, book.getListPrice());
				pstmt.setDouble(8, book.getSalePrice());
				pstmt.setDouble(9, book.getBookBP());
				pstmt.setInt(10, book.getIsSold());
				pstmt.setDate(11, book.getPublicationDate());
				pstmt.setInt(12, book.getStock());
				pstmt.setInt(13, book.getSafetyStock());
				pstmt.setString(14, book.getBookIntro());
				pstmt.setString(15, book.getBookNameOriginal());

				if (salePricePromo.isNaN()) {
					pstmt.setString(16, null);
				} else {
					pstmt.setDouble(16, salePricePromo);
				}

				if (bookBPPromo.isNaN()) {
					pstmt.setString(17, null);
				} else {
					pstmt.setDouble(17, bookBPPromo);
				}
				pstmt.setString(18, book.getEffectivePromos());
				pstmt.setString(19, book.getBookID());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			con.commit();
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	// B00000000001專屬方法
	public void updateEffPromos(String effectivePromos) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_EFFECTIVE_PROMOS);
			pstmt.setString(1, effectivePromos);
			pstmt.setString(2, "B00000000001");
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public List<Book> findByRandom(int num, boolean isFront) {
		List<Book> listBook = new ArrayList<Book>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_RANDOM_STMT);

			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Book book = setBook(rs);
				listBook.add(book);
			}

		} catch (

		SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return listBook;
	}

	@Override
	public List<Book> findNewBooks(int num, boolean isFront) {
		List<Book> listBook = new ArrayList<Book>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();

			if (isFront) {
				pstmt = con.prepareStatement(FIND_BY_RANDOM_STMT_FRONT);
			} else {
				pstmt = con.prepareStatement(FIND_NEW_STMT);
			}

			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Book book = setBook(rs);
				listBook.add(book);
			}

		} catch (

		SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return listBook;
	}

	@Override
	public int findBookNum() {
		int num = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_COUNT_STMT);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				num = rs.getInt(1);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return num;
	}

	@Override
	public List<String> findByBookIDLike(String bookID) {
		List<String> bookIDs = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_BOOK_ID_LIKE_STMT);
			pstmt.setString(1, bookID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bookIDs.add(rs.getString(1));
			}

		} catch (

		SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return bookIDs;
	}

	@Override
	public List<String> findByBookNameLike(String bookName) {
		List<String> bookNames = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_BOOK_NAME_LIKE_STMT);
			pstmt.setString(1, bookName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bookNames.add(rs.getString(1));
			}

		} catch (

		SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return bookNames;
	}

	@Override
	public List<String> findByAuthorLike(String author) {
		List<String> authors = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_AUTHOR_LIKE_STMT);
			pstmt.setString(1, author);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				authors.add(rs.getString(1));
			}

		} catch (

		SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return authors;
	}

	@Override
	public List<Book> findByPromoID(String promoID, boolean isFront) {
		List<Book> books = new ArrayList<Book>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();

			if (isFront) {
				pstmt = con.prepareStatement(FIND_BY_PROMO_ID_STMT_FRONT);
			} else {
				pstmt = con.prepareStatement(FIND_BY_PROMO_ID_STMT);
			}

			pstmt.setString(1, promoID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Book book = setBook(rs);
				books.add(book);
			}

		} catch (

		SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return books;
	}

	// setter工具方法
	private Book setBook(ResultSet rs) throws SQLException {
		Book book = new Book();
		book.setBookID(rs.getString("BOOK_ID"));
		book.setPublisherID(rs.getString("PUBLISHER_ID"));
		book.setLanguageID(rs.getString("LANGUAGE_ID"));
		book.setCategoryID(rs.getString("CATEGORY_ID"));
		book.setBookName(rs.getString("BOOK_NAME"));
		book.setIsbn(rs.getString("ISBN"));
		book.setAuthor(rs.getString("AUTHOR"));
		book.setListPrice(rs.getDouble("LIST_PRICE"));
		book.setSalePrice(rs.getDouble("SALE_PRICE"));
		book.setBookBP(rs.getDouble("BOOK_BP"));
		book.setIsSold(rs.getInt("IS_SOLD"));
		book.setPublicationDate(rs.getDate("PUBLICATION_DATE"));
		book.setStock(rs.getInt("STOCK"));
		book.setSafetyStock(rs.getInt("SAFETY_STOCK"));
		book.setBookIntro(rs.getString("BOOK_INTRO"));
		book.setBookNameOriginal(rs.getString("BOOK_NAME_ORIGINAL"));
		if (rs.getObject("SALE_PRICE_PROMO") == null) {
			book.setSalePricePromo(Double.NaN);
		} else {
			book.setSalePricePromo(rs.getDouble("SALE_PRICE_PROMO"));
		}
		if (rs.getObject("BOOK_BP_PROMO") == null) {
			book.setBookBPPromo(Double.NaN);
		} else {
			book.setBookBPPromo(rs.getDouble("BOOK_BP_PROMO"));
		}
		book.setEffectivePromos(rs.getString("EFFECTIVE_PROMOS"));
		return book;
	}
}