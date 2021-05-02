package com.bookpic.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

public class BookPicDAOImpl implements BookPicDAO {
	private DataSource ds;

	public BookPicDAOImpl(DataSource ds) {
		this.ds = ds;
	}

	private static final String INSERT_STMT = "INSERT INTO BOOK_PICTURES(BOOK_ID, BOOK_PIC_NAME, BOOK_PIC) VALUES (?, ?, ?)";
	private static final String FIND_FIRST_PIC_BY_BOOK_ID_STMT = "SELECT * FROM BOOK_PICTURES WHERE BOOK_ID = ? AND BOOK_PIC_NAME LIKE '0%'";
	private static final String FIND_LAST_PIC_NAME_BY_BOOK_ID_STMT = "SELECT max(to_number(SUBSTR(BOOK_PIC_NAME, 1, INSTR(BOOK_PIC_NAME, '.')-1))) FROM BOOK_PICTURES WHERE BOOK_ID = ?";
	private static final String FIND_BY_BOOK_ID_STMT = "SELECT * FROM BOOK_PICTURES WHERE BOOK_ID = ? ORDER BY to_number(SUBSTR(BOOK_PIC_NAME, 1, INSTR(BOOK_PIC_NAME, '.')-1))";
	private static final String UPDATE_STMT = "UPDATE BOOK_PICTURES SET BOOK_PIC =? WHERE BOOK_ID = ? AND BOOK_PIC_NAME = ?";
	private static final String DELETE_STMT = "DELETE FROM BOOK_PICTURES WHERE BOOK_ID = ?";
	private static final String DELETE_BY_BOOK_ID_AND_PIC_NAME_STMT = "DELETE FROM BOOK_PICTURES WHERE BOOK_ID = ? AND BOOK_PIC_NAME = ?";
	private static final String FIND_BY_BOOK_ID_AND_PIC_NAME_STMT = "SELECT * FROM BOOK_PICTURES WHERE BOOK_ID = ? AND BOOK_PIC_NAME = ?";

	@Override
	public void insert(BookPicture bookPic) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, bookPic.getBookID());
			pstmt.setString(2, bookPic.getBookPicName());
			pstmt.setBytes(3, bookPic.getBookPic());

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
	public Optional<BookPicture> findFirstPicByBookID(String bookID) {
		BookPicture fisrtBookPic = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_FIRST_PIC_BY_BOOK_ID_STMT);
			pstmt.setString(1, bookID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				fisrtBookPic = new BookPicture();
				fisrtBookPic.setBookID(rs.getString("BOOK_ID"));
				fisrtBookPic.setBookPicName(rs.getString("BOOK_PIC_NAME"));
				fisrtBookPic.setBookPic(rs.getBytes("BOOK_PIC"));
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
		return Optional.ofNullable(fisrtBookPic);
	}

	@Override
	public Optional<BookPicture> findByBookIDAndBookPicName(String bookID, String bookPicName) {
		BookPicture bookPic = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_BOOK_ID_AND_PIC_NAME_STMT);
			pstmt.setString(1, bookID);
			pstmt.setString(2, bookPicName);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				bookPic = new BookPicture();
				bookPic.setBookID(rs.getString("BOOK_ID"));
				bookPic.setBookPicName(rs.getString("BOOK_PIC_NAME"));
				bookPic.setBookPic(rs.getBytes("BOOK_PIC"));
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
		return Optional.ofNullable(bookPic);
	}

	@Override
	public Integer findLastPicNameByBookID(String bookID) {
		int lastPicNameNum = -1;
		String lastPicName;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_LAST_PIC_NAME_BY_BOOK_ID_STMT);
			pstmt.setString(1, bookID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				lastPicName = rs.getString(1);
				if (lastPicName != null) {
					lastPicNameNum = Integer.valueOf(lastPicName);
				}
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
		return lastPicNameNum;
	}

	@Override
	public List<BookPicture> findByBookID(String bookID) {
		List<BookPicture> listBookPic = new ArrayList<BookPicture>();
		BookPicture bookPic = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_BOOK_ID_STMT);
			pstmt.setString(1, bookID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				bookPic = new BookPicture();
				bookPic.setBookID(rs.getString("BOOK_ID"));
				bookPic.setBookPicName(rs.getString("BOOK_PIC_NAME"));
				bookPic.setBookPic(rs.getBytes("BOOK_PIC"));
				listBookPic.add(bookPic);
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
		return listBookPic;
	}

	@Override
	public void update(BookPicture bookPic) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setBytes(1, bookPic.getBookPic());
			pstmt.setString(2, bookPic.getBookID());
			pstmt.setString(3, bookPic.getBookPicName());

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
	public void deleteByBookID(String bookID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, bookID);

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
	public void deleteByBookIDAndPicName(String bookID, String bookPicName) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_BY_BOOK_ID_AND_PIC_NAME_STMT);

			pstmt.setString(1, bookID);
			pstmt.setString(2, bookPicName);

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

}
