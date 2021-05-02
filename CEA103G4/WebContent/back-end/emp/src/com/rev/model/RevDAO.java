package com.rev.model;

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


public class RevDAO implements RevDAO_interface{
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bookshop");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = "INSERT INTO REVIEW_RECORD (REV_ID, REV_CONTENT, MEM_ID, BOOK_ID, RATING) VALUES"
			+ "('REV' || lpad(REV_SEQ.NEXTVAL, 4, '0'),?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT * FROM REVIEW_RECORD ORDER BY REV_ID";
	private static final String GET_ONE_STMT = "SELECT * FROM REVIEW_RECORD WHERE REV_ID = ?";
	private static final String GET_BY_MEMID = "SELECT * FROM REVIEW_RECORD WHERE MEM_ID = ?";
	private static final String DELETE = "DELETE FROM REVIEW_RECORD WHERE REV_ID = ?";
	private static final String UPDATE_STATUS = "UPDATE REVIEW_RECORD SET REV_STATUS=? WHERE REV_ID=?";
	private static final String GET_BY_BOOK_ID = "SELECT * FROM REVIEW_RECORD WHERE BOOK_ID = ? ORDER BY REV_ID DESC";
	private static final String GET_RATING_AVG = "select avg(rating)*0.2 from review_record where rating != 0 and book_id = ?";
	
	@Override
	public RevVO insert(RevVO revVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			String cols[] = {"REV_ID"};
			pstmt = con.prepareStatement(INSERT_STMT, cols);

			pstmt.setString(1, revVO.getRev_content());
			pstmt.setString(2, revVO.getMem_id());
			pstmt.setString(3, revVO.getBook_id());
			pstmt.setInt(4, revVO.getRating());
			
			pstmt.executeUpdate();
			
			String rev_id = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				rev_id = rs.getString(1);
				revVO.setRev_id(rev_id);
			} else {
				System.out.println("error:沒取到ID");
			}
			rs.close();

			// Handle any SQL errors
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
		return revVO;
	}

	public void updateStatus(RevVO revVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STATUS);

			pstmt.setInt(1, revVO.getRev_status());
			pstmt.setString(2, revVO.getRev_id());

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
	public void delete(String rev_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, rev_id);
			
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
	public RevVO findByPrimaryKey(String rev_id) {
		RevVO revVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, rev_id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				revVO = new RevVO();
				revVO.setRev_id(rev_id);
				revVO.setRev_content(rs.getString("rev_content"));
				revVO.setRev_date(rs.getTimestamp("rev_date"));
				revVO.setMem_id(rs.getString("mem_id"));
				revVO.setBook_id(rs.getString("book_id"));
				revVO.setRev_status(rs.getInt("rev_status"));
				revVO.setRating(rs.getInt("rating"));
			}
			
			// Handle any SQL errors
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
		return revVO;
	}

	@Override
	public List<RevVO> findByMemId(String mem_id) {
		List<RevVO> list = new ArrayList<RevVO>();
		RevVO revVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_MEMID);
			
			pstmt.setString(1, mem_id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				revVO = new RevVO();
				revVO.setRev_id(rs.getString("rev_id"));
				revVO.setMem_id(mem_id);
				revVO.setBook_id(rs.getString("book_id"));
				revVO.setRev_content(rs.getString("rev_content"));
				revVO.setRev_status(rs.getInt("rev_status"));
				revVO.setRev_date(rs.getTimestamp("rev_date"));
				revVO.setRating(rs.getInt("rating"));
				list.add(revVO);
			}
	
			// Handle any SQL errors
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
	public List<RevVO> getAll() {
		List<RevVO> list = new ArrayList<RevVO>();
		RevVO revVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				revVO = new RevVO();
				revVO.setRev_id(rs.getString("rev_id"));
				revVO.setMem_id(rs.getString("mem_id"));
				revVO.setBook_id(rs.getString("book_id"));
				revVO.setRev_content(rs.getString("rev_content"));
				revVO.setRev_status(rs.getInt("rev_status"));
				revVO.setRev_date(rs.getTimestamp("rev_date"));
				revVO.setRating(rs.getInt("rating"));
				list.add(revVO);
			}
		
			// Handle any SQL errors
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
	public List<RevVO> getByBookId(String book_id) {
		List<RevVO> list = new ArrayList<RevVO>();
		RevVO revVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_BOOK_ID);
			
			pstmt.setString(1, book_id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				revVO = new RevVO();
				revVO.setRev_id(rs.getString("rev_id"));
				revVO.setMem_id(rs.getString("mem_id"));
				revVO.setBook_id(rs.getString("book_id"));
				revVO.setRev_content(rs.getString("rev_content"));
				revVO.setRev_status(rs.getInt("rev_status"));
				revVO.setRev_date(rs.getTimestamp("rev_date"));
				revVO.setRating(rs.getInt("rating"));
				list.add(revVO);
			}
		
			// Handle any SQL errors
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
	public Double getRatingAvg(String book_id) {
		Double ratingAvg = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_RATING_AVG);
			
			pstmt.setString(1, book_id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ratingAvg = rs.getDouble(1);
			}
		
			// Handle any SQL errors
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
		return ratingAvg;
	}
}

