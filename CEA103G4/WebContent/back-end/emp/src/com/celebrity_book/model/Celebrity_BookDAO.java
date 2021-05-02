package com.celebrity_book.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class Celebrity_BookDAO implements Celebrity_BookDAO_interface{
	private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bookshop");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSER_CELBOOK =
			"INSERT INTO CELEBRITY_FAVORITE_BOOKS(BOOK_ID,MEM_ID,SHARE_STATE) VALUES (?,?,?)";
	private static final String GET_ALL_CELBOOK =
			"SELECT CELEBRITY_FAVORITE_BOOKS.book_id,celebrity_favorite_books.mem_id,celebrity_favorite_books.share_state,books.book_name \r\n" + 
			"from celebrity_favorite_books \r\n" + 
			"INNER JOIN BOOKS ON books.book_id = celebrity_favorite_books.book_id \r\n" + 
			"WHERE celebrity_favorite_books.mem_id= ?";
	private static final String UPDATE_CELBOOK =
			"UPDATE CELEBRITY_FAVORITE_BOOKS SET SHARE_STATE=? WHERE BOOK_ID=? AND MEM_ID=?";
	private static final String DELETE =
			"DELETE FROM CELEBRITY_FAVORITE_BOOKS WHERE BOOK_ID=? AND MEM_ID=?";
	private static final String GET_ONE_CELBOOK = 
			"SELECT * FROM CELEBRITY_FAVOIRTE_BOOKS WHERE BOOK_ID=? AND MEM_ID=?";
	@Override
	public void insert(Celebrity_Book celebrity_Book) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSER_CELBOOK);
			pstmt.setString(1, celebrity_Book.getBook_ID());
			pstmt.setString(2, celebrity_Book.getMem_ID());
			pstmt.setInt(3, celebrity_Book.getShare_State());
			pstmt.executeUpdate();
		}catch (SQLException se) {
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
	public void delete(String book_ID, String mem_ID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, book_ID);
			pstmt.setString(2, mem_ID);
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public List<Celebrity_Book> getAll(String mem) {
		List<Celebrity_Book> list = new ArrayList<Celebrity_Book>();
		Celebrity_Book celebrity_Book = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_CELBOOK );
			pstmt.setString(1, mem);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				celebrity_Book = new Celebrity_Book();
				celebrity_Book.setBook_ID(rs.getString("book_ID"));
				celebrity_Book.setMem_ID(rs.getString("mem_ID"));
				celebrity_Book.setShare_State(rs.getInt("share_State"));
				
				list.add(celebrity_Book);		
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
		return list;
	}
	@Override
	public void update(Celebrity_Book celebrity_Book) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_CELBOOK);
			pstmt.setInt(1, celebrity_Book.getShare_State());
			pstmt.setString(2, celebrity_Book.getBook_ID());
			pstmt.setString(3, celebrity_Book.getMem_ID());
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public Celebrity_Book findByPrimaryKey(String book_ID,String mem_ID) {
		
		Celebrity_Book celebrity_Book = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_CELBOOK);

			pstmt.setString(1, book_ID);
			pstmt.setString(2, mem_ID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				celebrity_Book = new Celebrity_Book();
				celebrity_Book.setBook_ID(rs.getString("book_ID"));
				celebrity_Book.setMem_ID(rs.getString("mem_ID"));
				celebrity_Book.setShare_State(rs.getInt("share_State"));
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return celebrity_Book;
	}
}
