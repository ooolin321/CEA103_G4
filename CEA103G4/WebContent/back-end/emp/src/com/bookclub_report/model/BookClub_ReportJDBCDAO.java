package com.bookclub_report.model;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.bookclub_report.model.BookClub_ReportVO;

public class BookClub_ReportJDBCDAO implements BookClub_ReportDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "BOOKSHOP";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO BOOKCLUB_REPORT (BCR_ID,MEM_ID,BC_ID,BCR_STATUS,BCR_REASON,BCR_PROOF,BCR_DATE) VALUES ('BCR'||TO_CHAR(BCR_SEQ.NEXTVAL, 'FM0000'),'M0001',?,DEFAULT,?,?,DEFAULT)";
	private static final String GET_ALL_STMT = "SELECT * FROM BOOKCLUB_REPORT ORDER BY BCR_ID";
	private static final String SELECT_BY_PK = "SELECT * FROM BOOKCLUB_REPORT WHERE BCR_ID = ?";
	private static final String DELETE = "DELETE FROM BOOKCLUB_REPORT WHERE BCR_ID = ?";
	private static final String UPDATE = "UPDATE BOOKCLUB_REPORT SET BCR_STATUS = ? WHERE BCR_ID = ?";

	@Override
	public void insert(BookClub_ReportVO bookClub_ReportVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

//			pstmt.setString(1, bookClub_ReportVO.getMem_id());
			pstmt.setString(1, bookClub_ReportVO.getBc_id());
			pstmt.setString(2, bookClub_ReportVO.getBcr_reason());
			pstmt.setBytes(3, bookClub_ReportVO.getBcr_proof());

			pstmt.executeUpdate();
			System.out.println("新增成功 insert method");

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void update(BookClub_ReportVO bookClub_ReportVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, bookClub_ReportVO.getBcr_status());
			pstmt.setString(2, bookClub_ReportVO.getBcr_id());
			
			pstmt.executeUpdate();
			System.out.println("更新成功");

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

	public void delete(String bcr_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, bcr_id);

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public BookClub_ReportVO findByPrimaryKey(String pk) {
		BookClub_ReportVO bookClub_ReportVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_BY_PK);
			pstmt.setString(1, pk);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bookClub_ReportVO = new BookClub_ReportVO();
				bookClub_ReportVO.setBcr_id(rs.getString("bcr_id"));
				bookClub_ReportVO.setMem_id(rs.getString("mem_id"));
				bookClub_ReportVO.setBc_id(rs.getString("bc_id"));
				bookClub_ReportVO.setBcr_reason(rs.getString("bcr_reason"));
				bookClub_ReportVO.setBcr_proof(rs.getBytes("bcr_proof"));
				bookClub_ReportVO.setBcr_status(rs.getInt("bcr_status"));
				bookClub_ReportVO.setBcr_date(rs.getTimestamp("bcr_date"));
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		}catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return bookClub_ReportVO;
	}

	@Override
	public List<BookClub_ReportVO> getAll() {
		List<BookClub_ReportVO> list = new ArrayList<BookClub_ReportVO>();
		BookClub_ReportVO bookClub_ReportVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				bookClub_ReportVO = new BookClub_ReportVO();
				bookClub_ReportVO.setBcr_id(rs.getString("bcr_id"));
				bookClub_ReportVO.setMem_id(rs.getString("mem_id"));
				bookClub_ReportVO.setBc_id(rs.getString("bc_id"));
				bookClub_ReportVO.setBcr_reason(rs.getString("bcr_reason"));
				bookClub_ReportVO.setBcr_proof(rs.getBytes("bcr_proof"));
				bookClub_ReportVO.setBcr_status(rs.getInt("bcr_status"));
				bookClub_ReportVO.setBcr_date(rs.getTimestamp("bcr_date"));
				list.add(bookClub_ReportVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		}catch (SQLException se) {
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
		BookClub_ReportJDBCDAO dao = new BookClub_ReportJDBCDAO();
		//新增
//		BookClub_ReportVO bookClub_ReportVO1 = new BookClub_ReportVO();
//		bookClub_ReportVO1.setMem_id("M0003");
//		bookClub_ReportVO1.setBc_id("BC0003");
//		bookClub_ReportVO1.setBcr_reason("很奇怪");
//		bookClub_ReportVO1.setBcr_proof(null);
//		dao.insert(bookClub_ReportVO1);
//		System.out.println("新增成功 main method");
		
		// 刪除
//		dao.delete("BCR0004");
//		System.out.println("刪除成功 main method");
		
		//查全部
//		List<BookClub_ReportVO> list = dao.getAll();
//		
//		for(BookClub_ReportVO allBookClub_ReportVO : list) {
//			System.out.println(allBookClub_ReportVO.getBcr_id() + ",");
//			System.out.println(allBookClub_ReportVO.getMem_id() + ",");
//			System.out.println(allBookClub_ReportVO.getBc_id() + ",");
//			System.out.println(allBookClub_ReportVO.getBcr_reason() + ",");
//			System.out.println(allBookClub_ReportVO.getBcr_proof());
//		}
		// 搜尋一筆
//		BookClub_ReportVO bookClub_ReportVO2 = dao.findByPrimaryKey("BCR0003");
//		
//		System.out.println(bookClub_ReportVO2.getBcr_id());
//		System.out.println(bookClub_ReportVO2.getMem_id());
//		System.out.println(bookClub_ReportVO2.getBc_id());
//		System.out.println(bookClub_ReportVO2.getBcr_status());
//		System.out.println(bookClub_ReportVO2.getBcr_reason());
//		System.out.println(bookClub_ReportVO2.getBcr_proof());
//		System.out.println(bookClub_ReportVO2.getBcr_date());
		
		//更新
//		BookClub_ReportVO bookClub_ReportVO3 = new BookClub_ReportVO();
//		bookClub_ReportVO3.setBcr_status(2);
//		bookClub_ReportVO3.setBcr_id("BCR0003");
//		dao.update(bookClub_ReportVO3);
		
	}

	@Override
	public void updateStatus(BookClub_ReportVO bookClub_ReportVO) {
		// TODO Auto-generated method stub
		
	}
}
