package com.Far.model;

import java.util.*;
import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.Fa.model.*;

public class FarJDBCDAO implements FarDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "BOOKSHOP";
	String passwd = "123456";

	// 新增文章檢舉
	private static final String INSERT_STMT = "INSERT INTO FORUM_ARTICLE_REPORTS (FARID,FAID,MEMID,FARCONTENT)\r\n"
			+ "VALUES('FAR' || LPAD(FORUM_ART_REP_SEQ.nextval,3,'0'),?,?,?)";
	// 審核文章
	private static final String JUDGE_STMT = "UPDATE FORUM_ARTICLE_REPORTS SET FARSTATUS = ?,ADMINID = ? WHERE FARID = ?";
	// 所有檢舉資料
	private static final String GET_ALL_STMT = "SELECT * FROM FORUM_ARTICLE_REPORTS";
	// 顯示待審核的文章檢舉
	private static final String GET_NORMAL_FAR_STMT = "SELECT * FROM FORUM_ARTICLE_REPORTS WHERE FARSTATUS = 0";

	@Override
	public void insert(FarVO farVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, farVO.getFaId());
			pstmt.setString(2, farVO.getMemId());
			pstmt.setString(3, farVO.getFarContent());

			pstmt.executeUpdate();

			// Handle any driver errors ( JDBC 驅動 )
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors ( SQL 除錯 )
		} catch (SQLException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	public void judge(FarVO farVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(JUDGE_STMT);

			pstmt.setInt(1, farVO.getFarStatus());
			pstmt.setString(2, farVO.getAdminId());
			pstmt.setString(3, farVO.getFarId());
			
			pstmt.executeUpdate();
			
			// Handle any driver errors ( JDBC 驅動 )
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors ( SQL 除錯 )
		} catch (SQLException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	public List<FarVO> getAll() {
		List<FarVO> list = new ArrayList<FarVO>();
		FarVO farVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				farVO = new FarVO();
				farVO.setFarId(rs.getString("FARID"));
				farVO.setFaId(rs.getString("FAID"));
				farVO.setMemId(rs.getString("MEMID"));
				farVO.setFarContent(rs.getString("FARCONTENT"));
				farVO.setFarStatus(rs.getInt("FARSTATUS"));
				farVO.setFarDate(rs.getTimestamp("FARDATE"));
				list.add(farVO);
			}
			// Handle any driver errors ( JDBC 驅動 )
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors ( SQL 除錯 )
		} catch (SQLException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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

		return list;
	}

	@Override
	public List<FarVO> getAllJundge() {
		List<FarVO> list = new ArrayList<FarVO>();
		FarVO farVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_NORMAL_FAR_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
					farVO = new FarVO();
					farVO.setFarId(rs.getString("FARID"));
					farVO.setFaId(rs.getString("FAID"));
					farVO.setMemId(rs.getString("MEMID"));
					farVO.setFarContent(rs.getString("FARCONTENT"));
					farVO.setFarStatus(rs.getInt("FARSTATUS"));
					farVO.setFarDate(rs.getTimestamp("FARDATE"));
					list.add(farVO);
				
			}
			// Handle any driver errors ( JDBC 驅動 )
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors ( SQL 除錯 )
		} catch (SQLException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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

		return list;
	}

}
