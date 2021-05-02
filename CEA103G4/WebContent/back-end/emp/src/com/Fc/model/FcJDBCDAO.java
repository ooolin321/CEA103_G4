package com.Fc.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Fa.model.FaVO;

public class FcJDBCDAO implements FcDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "BOOKSHOP";
	String passwd = "123456";
	
	private static final String INSERT_STMT = 
			"INSERT INTO FORUM_COLLECTIONS(MEMID,FAID) VALUES(?,?)";
	private static final String DELETE = 
			"DELETE FORUM_COLLECTIONS WHERE MEMID=? AND FAID=?";
	private static final String GET_ONE_MEM_COLLECTION_FA = 
			"SELECT MEMID,FAID FROM FORUM_COLLECTIONS WHERE MEMID=? AND FAID IN(SELECT FAID FROM FORUM_ARTICLES WHERE FASTATUS = 0)";
	private static final String CHECK_COLLECTION =
			"SELECT COUNT(1) FROM FORUM_COLLECTIONS WHERE MEMID=? AND FAID=?";
	
	
	@Override
	public void insert(String memId, String faId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, memId);
			pstmt.setString(2, faId);
			
			
			pstmt.executeUpdate();
			
			// Handle any driver errors ( JDBC 驅動 )
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors ( SQL 除錯 )
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources ( 關閉資源 )
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
	public void delete(String memId, String faId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, memId);
			pstmt.setString(2, faId);
			

			pstmt.executeUpdate();

			// Handle any driver errors ( JDBC 驅動 )
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors ( SQL 除錯 )
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources ( 關閉資源 )
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
	public List<FaVO> getOneMemColFa(String memId) {
		List<FaVO> list = new ArrayList<FaVO>();
		FaVO faVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_MEM_COLLECTION_FA);
			pstmt.setString(1, memId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				faVO = new FaVO();
				faVO.setFaId(rs.getString("FAID"));
				list.add(faVO);
			}
			
			
			// Handle any driver errors ( JDBC 驅動 )
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors ( SQL 除錯 )
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources ( 關閉資源 )
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
	public Integer checkCollect(String memId, String faId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer count = 0;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(CHECK_COLLECTION);
			pstmt.setString(1, memId);
			pstmt.setString(2, faId);
			rs = pstmt.executeQuery();
			rs.next();
			count = rs.getInt("COUNT(1)");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors ( SQL 除錯 )
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources ( 關閉資源 )
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
		return count;
	}
//	public static void main(String[] args) {
//		FcJDBCDAO dao = new FcJDBCDAO();
//		System.out.println(dao.checkCollect("M0002", "FA005"));
//		System.out.println(dao.checkCollect("M0001", "FA006"));
//		System.out.println(dao.checkCollect("M0001", "FA005"));
//	}
	
	
	
}
