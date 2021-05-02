package com.rev.model;

import java.sql.*;
import java.util.*;

public class RevDAOJDBC implements RevDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "BOOKSHOP";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO REVIEW_RECORD (REV_ID, REV_CONTENT, MEM_ID, BOOK_ID) VALUES"
			+ "('REV' || lpad(REV_SEQ.NEXTVAL, 4, '0'),?,?,?)";
	private static final String GET_ALL_STMT = "SELECT * FROM REVIEW_RECORD ORDER BY REV_ID";
	private static final String GET_ONE_STMT = "SELECT * FROM REVIEW_RECORD WHERE REV_ID = ?";
	private static final String GET_BY_MEMID = "SELECT * FROM REVIEW_RECORD WHERE MEM_ID = ?";
	private static final String DELETE = "DELETE FROM REVIEW_RECORD WHERE REV_ID = ?";
	private static final String UPDATE_STATUS = "UPDATE REVIEW_RECORD SET REV_STATUS=? WHERE REV_ID=?";
	private static final String GET_RATING_AVG = "select avg(rating)*0.2 from review_record where rating != 0 and book_id = ?";

	@Override
	public RevVO insert(RevVO revVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, revVO.getRev_content());
			pstmt.setString(2, revVO.getMem_id());
			pstmt.setString(3, revVO.getBook_id());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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

	@Override
	public void updateStatus(RevVO revVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STATUS);

			pstmt.setInt(1, revVO.getRev_status());
			pstmt.setString(2, revVO.getRev_id());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, rev_id);

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, rev_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				revVO = new RevVO();
				revVO.setRev_id(rev_id);
				revVO.setRev_content(rs.getString("rev_content"));
				revVO.setRev_date(rs.getTimestamp("rev_date"));
				revVO.setMem_id(rs.getString("mem_id"));
				revVO.setBook_id(rs.getString("book_id"));
				revVO.setRev_status(rs.getInt("rev_status"));
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_BY_MEMID);

			pstmt.setString(1, mem_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				revVO = new RevVO();
				revVO.setRev_id(rs.getString("rev_id"));
				revVO.setMem_id(mem_id);
				revVO.setBook_id(rs.getString("book_id"));
				revVO.setRev_content(rs.getString("rev_content"));
				revVO.setRev_status(rs.getInt("rev_status"));
				revVO.setRev_date(rs.getTimestamp("rev_date"));
				list.add(revVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				revVO = new RevVO();
				revVO.setRev_id(rs.getString("rev_id"));
				revVO.setMem_id(rs.getString("mem_id"));
				revVO.setBook_id(rs.getString("book_id"));
				revVO.setRev_content(rs.getString("rev_content"));
				revVO.setRev_status(rs.getInt("rev_status"));
				revVO.setRev_date(rs.getTimestamp("rev_date"));
				list.add(revVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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

	public static void main(String[] args) {
		RevDAOJDBC dao = new RevDAOJDBC();
		
		System.out.println(dao.getRatingAvg("B00000000001"));

		// 新增
//		RevVO revVO1 = new RevVO();
//		revVO1.setRev_content("我覺得不怎麼樣");
//		revVO1.setMem_id("M0003");
//		revVO1.setBook_id("B00000000001");
//		
//		dao.insert(revVO1);
//		System.out.println("新增成功");

		// 修改狀態
//		RevVO revVO2 = new RevVO();
//		revVO2.setRev_status(1);
//		revVO2.setRev_id("REV0007");
//		dao.updateStatus(revVO2);
//		System.out.println("修改成功");

		// 刪除
//		dao.delete("REV0007");
//		System.out.println("刪除成功");

		// 查詢單筆
//		RevVO revVO3 = dao.findByPrimaryKey("REV0005");
//		System.out.println(revVO3.getRev_id());
//		System.out.println(revVO3.getRev_content());
//		System.out.println(revVO3.getRev_date());
//		System.out.println(revVO3.getMem_id());
//		System.out.println(revVO3.getBook_id());
//		System.out.println(revVO3.getRev_status());

		// 查詢全部
//		List<RevVO> list = dao.getAll();
//		for(RevVO aRev : list) {
//			System.out.print(aRev.getRev_id() + " ,");
//			System.out.print(aRev.getRev_content() + " ,");
//			System.out.print(aRev.getRev_date() + " ,");
//			System.out.print(aRev.getMem_id() + " ,");
//			System.out.print(aRev.getBook_id() + " ,");
//			System.out.print(aRev.getRev_status());
//			System.out.println();
//		}

//		List<RevVO> list = dao.findByMemId("M0001");
//		for (RevVO aRev : list) {
//			System.out.print(aRev.getRev_id() + " ,");
//			System.out.print(aRev.getRev_content() + " ,");
//			System.out.print(aRev.getRev_date() + " ,");
//			System.out.print(aRev.getMem_id() + " ,");
//			System.out.print(aRev.getBook_id() + " ,");
//			System.out.print(aRev.getRev_status());
//			System.out.println();
//		}
	}

	@Override
	public List<RevVO> getByBookId(String book_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getRatingAvg(String book_id) {
		Double ratingAvg = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_RATING_AVG);

			pstmt.setString(1, book_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ratingAvg = rs.getDouble(1);
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage()); // Handle any SQL errors
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
