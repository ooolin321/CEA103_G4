package com.msg.model;

import java.util.*;
import java.sql.*;

public class MsgJDBCDAO implements MsgDAO_interface {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://database-1.canq3t4lp2za.ap-northeast-1.rds.amazonaws.com/CEA103_G4?serverTimezone=Asia/Taipei";
	String userid = "admin";
	String passwd = "12345678";

	private static final String INSERT_STMT = 
			"INSERT INTO `MESSAGE` (`USER_ID`,`CONTENT`,`SELLER_ID`,`MESSAGE_TIME`) VALUES (?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
			"SELECT `MESSAGE_ID`,`USER_ID`,`CONTENT`,`SELLER_ID`,`MESSAGE_TIME` FROM `MESSAGE` ORDER BY `MESSAGE_ID`";
	private static final String GET_ONE_STMT = 
			"SELECT `MESSAGE_ID`,`USER_ID`,`CONTENT`,`SELLER_ID`,`MESSAGE_TIME` FROM `MESSAGE` WHERE `MESSAGE_ID` = ?";
	private static final String DELETE = 
			"DELETE FROM `MESSAGE` WHERE `MESSAGE_ID` = ?";
	private static final String UPDATE = 
			"UPDATE `MESSAGE` SET `USER_ID`=?, `CONTENT`=?, `SELLER_ID`=?, `MESSAGE_TIME`=? WHERE `MESSAGE_ID` = ?";

	@Override
	public void insert(MsgVO msgVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, msgVO.getUser_id());
			pstmt.setString(2, msgVO.getContent());
			pstmt.setString(3, msgVO.getSeller_id());
			pstmt.setDate(4, msgVO.getMessage_time());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldnt load database driver. " + e.getMessage());
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
	}

	@Override
	public void update(MsgVO msgVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, msgVO.getUser_id());
			pstmt.setString(2, msgVO.getContent());
			pstmt.setString(3, msgVO.getSeller_id());
			pstmt.setDate(4, msgVO.getMessage_time());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldnt load database driver. " + e.getMessage());
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

	}

	@Override
	public void delete(Integer message_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, message_id);

			pstmt.executeUpdate();
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldnt load database driver. " + e.getMessage());
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
	}

	@Override
	public MsgVO findByPrimaryKey(Integer message_id) {
		MsgVO msgVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, message_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				//
				msgVO = new MsgVO();
				msgVO.setMessage_id(rs.getInt("message_id"));
				msgVO.setUser_id(rs.getString("user_id"));
				msgVO.setContent(rs.getString("content"));
				msgVO.setSeller_id(rs.getString("seller_id"));
				msgVO.setMessage_time(rs.getDate("message_time"));
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldnt load database driver. " + e.getMessage());
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
		return msgVO;
	}

	@Override
	public List<MsgVO> getAll() {
		List<MsgVO> list = new ArrayList<MsgVO>();
		MsgVO msgVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				//
				msgVO = new MsgVO();
				msgVO.setMessage_id(rs.getInt("message_id"));
				msgVO.setUser_id(rs.getString("user_id"));
				msgVO.setContent(rs.getString("content"));
				msgVO.setSeller_id(rs.getString("seller_id"));
				msgVO.setMessage_time(rs.getDate("message_time"));
				list.add(msgVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldnt load database driver. " + e.getMessage());
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

		MsgJDBCDAO dao = new MsgJDBCDAO();

		// 新增
		MsgVO msgVO1 = new MsgVO();
		msgVO1.setUser_id("2011");
		msgVO1.setContent("Yo");
		msgVO1.setSeller_id("4011");
		msgVO1.setMessage_time(java.sql.Date.valueOf("2021-04-10"));
		dao.insert(msgVO1);

		// 修改
		MsgVO msgVO2 = new MsgVO();
		msgVO2.setMessage_id(1011);
		msgVO2.setUser_id("2011");
		msgVO2.setContent("Yo");
		msgVO2.setSeller_id("4011");
		msgVO2.setMessage_time(java.sql.Date.valueOf("2021-04-10"));
		dao.update(msgVO2);

		// 刪除
		dao.delete(1008);

		// 查詢
		MsgVO msgVO3 = dao.findByPrimaryKey(1001);
		System.out.print(msgVO3.getMessage_id() + ",");
		System.out.print(msgVO3.getUser_id() + ",");
		System.out.print(msgVO3.getContent()+ ",");
		System.out.print(msgVO3.getSeller_id() + ",");
		System.out.print(msgVO3.getMessage_time() + ",");
		System.out.println("---------------------");

		// 查詢
		List<MsgVO> list = dao.getAll();
		for (MsgVO aMsg : list) {
			System.out.print(aMsg.getMessage_id() + ",");
			System.out.print(aMsg.getUser_id() + ",");
			System.out.print(aMsg.getContent() + ",");
			System.out.print(aMsg.getSeller_id() + ",");
			System.out.print(aMsg.getMessage_time() + ",");
			System.out.println();
		}
	}
}