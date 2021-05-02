package com.bookclub_regis_detail.model;

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

import com.bookclub_regis_detail.model.BookClub_Regis_DetailVO;
import com.bookclub_report.model.BookClub_ReportJDBCDAO;

public class BookClub_Regis_DetailJDBCDAO implements BookClub_Regis_DetailDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "BOOKSHOP";
	String passwd = "123456";

	private static final String INSERT_STMT = 
			"INSERT INTO BOOKCLUB_REGIS_DETAIL (BRD_ID,MEM_ID,BC_ID,BRD_STATUS,BRD_DATE) VALUES ('BRD'||TO_CHAR(BRD_SEQ.NEXTVAL, 'FM0000'),?,?,DEFAULT,DEFAULT)";
	private static final String GET_ALL_STMT = 
			"SELECT * FROM BOOKCLUB_REGIS_DETAIL ORDER BY BRD_ID";
	private static final String SELECT_BY_PK = 
			"SELECT * FROM BOOKCLUB_REGIS_DETAIL WHERE BRD_ID = ?";
	private static final String DELETE = 
			"DELETE FROM BOOKCLUB_REGIS_DETAIL WHERE BRD_ID = ?";
	private static final String UPDATE = 
			"UPDATE BOOKCLUB_REGIS_DETAIL SET BRD_STATUS = ? WHERE BRD_ID = ?";
	@Override
	public String insert(BookClub_Regis_DetailVO bookClub_Regis_DetailVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String brd_id = "";
		String[] pk = {"BRD_ID"};

		try {
			System.out.println("新增中");
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, bookClub_Regis_DetailVO.getMem_id());
			pstmt.setString(2, bookClub_Regis_DetailVO.getBc_id());

			pstmt.executeUpdate();
			System.out.println("新增成功");
			
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				brd_id = rs.getString(1);
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		}catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
			se.printStackTrace();
			// Clean up JDBC resources
		} finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException se) {
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
		return brd_id;
	}

	@Override
	public void update_Status(BookClub_Regis_DetailVO bookClub_Regis_DetailVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, bookClub_Regis_DetailVO.getBrd_status());
			pstmt.setString(2, bookClub_Regis_DetailVO.getBrd_id());

			pstmt.executeUpdate();
			System.out.println("更新成功");

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		}catch (SQLException se) {
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
	public void delete(String brd_id) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, brd_id);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		}catch (SQLException se) {
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
	public BookClub_Regis_DetailVO findByPrimaryKey(String brd_id) {
		BookClub_Regis_DetailVO bookClub_Regis_DetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_BY_PK);
			pstmt.setString(1, brd_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				bookClub_Regis_DetailVO = new BookClub_Regis_DetailVO();
				bookClub_Regis_DetailVO.setBrd_id(rs.getString("brd_id"));
				bookClub_Regis_DetailVO.setMem_id(rs.getString("mem_id"));
				bookClub_Regis_DetailVO.setBc_id(rs.getString("bc_id"));
				bookClub_Regis_DetailVO.setBrd_status(rs.getInt("brd_status"));
				bookClub_Regis_DetailVO.setBrd_date(rs.getTimestamp("brd_date"));
				
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		}catch (SQLException e) {
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return bookClub_Regis_DetailVO;
	}

	@Override
	public List<BookClub_Regis_DetailVO> getAll() {
		List<BookClub_Regis_DetailVO> list = new ArrayList<BookClub_Regis_DetailVO>();
		BookClub_Regis_DetailVO bookClub_Regis_DetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				bookClub_Regis_DetailVO = new BookClub_Regis_DetailVO();
				bookClub_Regis_DetailVO.setBrd_id(rs.getString("brd_id"));
				bookClub_Regis_DetailVO.setMem_id(rs.getString("mem_id"));
				bookClub_Regis_DetailVO.setBc_id(rs.getString("bc_id"));
				bookClub_Regis_DetailVO.setBrd_status(rs.getInt("brd_status"));
				bookClub_Regis_DetailVO.setBrd_date(rs.getTimestamp("brd_date"));
				list.add(bookClub_Regis_DetailVO); 
			}
			

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
		BookClub_Regis_DetailJDBCDAO dao = new BookClub_Regis_DetailJDBCDAO();
		//新增
//		BookClub_Regis_DetailVO bookClub_Regis_DetailVO1 = new BookClub_Regis_DetailVO();
//		bookClub_Regis_DetailVO1.setMem_id("M0003");
//		bookClub_Regis_DetailVO1.setBc_id("BC0003");
//		dao.insert(bookClub_Regis_DetailVO1);
//		System.out.println("新增成功 main method");
		
		// 刪除
//		dao.delete("BRD0010");
//		System.out.println("刪除成功 main method");
		
		//查全部
//		List<BookClub_Regis_DetailVO> list = dao.getAll();
//		
//		for(BookClub_Regis_DetailVO allBookClub_Regis_DetailVO : list) {
//			System.out.println(allBookClub_Regis_DetailVO.getBrd_id() + ",");
//			System.out.println(allBookClub_Regis_DetailVO.getMem_id() + ",");
//			System.out.println(allBookClub_Regis_DetailVO.getBc_id() + ",");
//			System.out.println(allBookClub_Regis_DetailVO.getBrd_status() + ",");
//			System.out.println(allBookClub_Regis_DetailVO.getBrd_date());
//		}
		
		// 搜尋一筆
//		BookClub_Regis_DetailVO bookClub_Regis_DetailVO2 = dao.findByPrimaryKey("BRD0003");
//		
//		System.out.println(bookClub_Regis_DetailVO2.getBrd_id() + ",");
//		System.out.println(bookClub_Regis_DetailVO2.getMem_id() + ",");
//		System.out.println(bookClub_Regis_DetailVO2.getBc_id() + ",");
//		System.out.println(bookClub_Regis_DetailVO2.getBrd_status() + ",");
//		System.out.println(bookClub_Regis_DetailVO2.getBrd_date());
		
		//更新
//		BookClub_Regis_DetailVO bookClub_Regis_DetailVO3  = new BookClub_Regis_DetailVO();
//		bookClub_Regis_DetailVO3.setBrd_status(2);
//		bookClub_Regis_DetailVO3.setBrd_id("BRD0003");
//		dao.update(bookClub_Regis_DetailVO3);
	}

	@Override
	public List<BookClub_Regis_DetailVO> findByVerify(String bc_id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	@Override
	public List<BookClub_Regis_DetailVO> findByMyself(String mem_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookClub_Regis_DetailVO> findByBc_id(String bc_id) {
		// TODO Auto-generated method stub
		return null;
	}
}
