package com.favorite_book.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Favorite_BookDAO implements Favorite_BookDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bookshop");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	private static final String INSER_FAVBOOK =
			"INSERT INTO FAVORITE_BOOKS(BOOK_ID,MEM_ID) VALUES (?,?)";
	private static final String GET_ALL_FAVBOOK =
			"SELECT favorite_books.book_id ,favorite_books.fav_time ,favorite_books.mem_id,"
			+ "books.book_name FROM FAVORITE_BOOKS "
			+ "INNER JOIN BOOKS ON books.book_id = favorite_books.book_id "
			+ "WHERE favorite_books.mem_id= ? ORDER BY  FAV_TIME DESC";
	private static final String DELETE =
			"DELETE FROM FAVORITE_BOOKS WHERE BOOK_ID=? AND MEM_ID=?";
	

	@Override
	public void insert(Favorite_Book favorite_Book) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSER_FAVBOOK);
			pstmt.setString(1, favorite_Book.getBook_ID());
			pstmt.setString(2, favorite_Book.getMem_ID());
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
	public void delete(String book_ID,String mem_ID) {
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
	public List<Favorite_Book> getAll(String memID) {
		List<Favorite_Book> list = new ArrayList<Favorite_Book>();
		Favorite_Book favorite_Books = null;
		
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_FAVBOOK);
			pstmt.setString(1,memID);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				favorite_Books = new Favorite_Book();
				favorite_Books.setBook_ID(rs.getString("book_ID"));
				favorite_Books.setMem_ID(rs.getString("mem_ID"));
				favorite_Books.setFav_Time(rs.getDate("fav_Time"));
				list.add(favorite_Books);		
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
}

