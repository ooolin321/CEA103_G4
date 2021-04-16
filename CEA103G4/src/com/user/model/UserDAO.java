package com.user.model;

import java.util.*;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class UserDAO implements UserDAO_interface {
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/admin");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = 
			"INSERT INTO `USER` (`USER_ID`,`USER_PWD`,`USER_NAME`,`ID_CARD`,`USER_GENDER`,`USER_DOB`,`USER_MAIL`,`USER_PHONE`,`USER_MOBILE`,`USER_ADDR`,`REGDATE`,`USER_POINT`,`VIOLATION`,`USER_STATE`,`USER_COMMENT`,`COMMENT_TOTAL`,`CASH`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
			"SELECT * FROM `USER` ORDER BY `USER_ID`";
	private static final String GET_ONE_STMT = 
			"SELECT `USER_ID`,`USER_PWD`,`USER_NAME`,`ID_CARD`,`USER_GENDER`,`USER_DOB`,`USER_MAIL`,`USER_PHONE`,`USER_MOBILE`,`USER_ADDR`,`REGDATE`,`USER_POINT`,`VIOLATION`,`USER_STATE`,`USER_COMMENT`,`COMMENT_TOTAL`,`CASH` FROM USER WHERE `USER_ID` = ?";
	private static final String DELETE = 
			"DELETE FROM USER where USER_ID = ?";
	private static final String UPDATE = 
			"UPDATE `USER` SET `USER_PWD`=?, `USER_NAME`=?, `ID_CARD`=?, `USER_GENDER`=?, `USER_DOB`=?, `USER_MAIL`=?, `USER_PHONE`=?, `USER_MOBILE`=?, `USER_ADDR`=?, `REGDATE`=?, `USER_POINT`=?, `VIOLATION`=?, `USER_STATE`=?, `USER_COMMENT`=?, `COMMENT_TOTAL`=?, `CASH`=? WHERE `USER_ID` = ?";


	@Override
	public void insert(UserVO userVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, userVO.getUser_id());
			pstmt.setString(2, userVO.getUser_pwd());
			pstmt.setString(3, userVO.getUser_name());
			pstmt.setString(4, userVO.getId_card());
			pstmt.setString(5, userVO.getUser_gender());
			pstmt.setDate(6, userVO.getUser_dob());
			pstmt.setString(7, userVO.getUser_mail());
			pstmt.setString(8, userVO.getUser_phone());
			pstmt.setString(9, userVO.getUser_mobile());
			pstmt.setString(10, userVO.getUser_addr());
			pstmt.setDate(11, userVO.getRegdate());
			pstmt.setInt(12, userVO.getUser_point());
			pstmt.setInt(13, userVO.getViolation());
			pstmt.setInt(14, userVO.getUser_state());
			pstmt.setInt(15, userVO.getUser_comment());
			pstmt.setInt(16, userVO.getComment_total());
			pstmt.setInt(17, userVO.getCash());

			pstmt.executeUpdate();

			// Handle any SQL errors
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
	public void update(UserVO userVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, userVO.getUser_pwd());
			pstmt.setString(2, userVO.getUser_name());
			pstmt.setString(3, userVO.getId_card());
			pstmt.setString(4, userVO.getUser_gender());
			pstmt.setDate(5, userVO.getUser_dob());
			pstmt.setString(6, userVO.getUser_mail());
			pstmt.setString(7, userVO.getUser_phone());
			pstmt.setString(8, userVO.getUser_mobile());
			pstmt.setString(9, userVO.getUser_addr());
			pstmt.setDate(10, userVO.getRegdate());
			pstmt.setInt(11, userVO.getUser_point());
			pstmt.setInt(12, userVO.getViolation());
			pstmt.setInt(13, userVO.getUser_state());
			pstmt.setInt(14, userVO.getUser_comment());
			pstmt.setInt(15, userVO.getComment_total());
			pstmt.setInt(16, userVO.getCash());
			pstmt.setString(17, userVO.getUser_id());

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public void delete(String user_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, user_id);

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public UserVO findByPrimaryKey(String user_id) {
		UserVO userVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, user_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// userVo 也稱為 Domain objects
				userVO = new UserVO();
				userVO.setUser_id(rs.getString("user_id"));
				userVO.setUser_pwd(rs.getString("user_pwd"));
				userVO.setUser_name(rs.getString("user_name"));
				userVO.setId_card(rs.getString("id_card"));
				userVO.setUser_gender(rs.getString("user_gender"));
				userVO.setUser_dob(rs.getDate("user_dob"));
				userVO.setUser_mail(rs.getString("user_mail"));
				userVO.setUser_phone(rs.getString("user_phone"));
				userVO.setUser_mobile(rs.getString("user_mobile"));
				userVO.setUser_addr(rs.getString("user_addr"));
				userVO.setRegdate(rs.getDate("regdate"));
				userVO.setUser_point(rs.getInt("user_point"));
				userVO.setViolation(rs.getInt("violation"));
				userVO.setUser_state(rs.getInt("user_state"));
				userVO.setUser_comment(rs.getInt("user_comment"));
				userVO.setComment_total(rs.getInt("comment_total"));
				userVO.setCash(rs.getInt("cash"));
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
		return userVO;
	}

	@Override
	public List<UserVO> getAll() {
		List<UserVO> list = new ArrayList<UserVO>();
		UserVO userVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// userVO 也稱為 Domain objects
				userVO = new UserVO();
				userVO.setUser_id(rs.getString("user_id"));
				userVO.setUser_pwd(rs.getString("user_pwd"));
				userVO.setUser_name(rs.getString("user_name"));
				userVO.setId_card(rs.getString("id_card"));
				userVO.setUser_gender(rs.getString("user_gender"));
				userVO.setUser_dob(rs.getDate("user_dob"));
				userVO.setUser_mail(rs.getString("user_mail"));
				userVO.setUser_phone(rs.getString("user_phone"));
				userVO.setUser_mobile(rs.getString("user_mobile"));
				userVO.setUser_addr(rs.getString("user_addr"));
				userVO.setRegdate(rs.getDate("regdate"));
				userVO.setUser_point(rs.getInt("user_point"));
				userVO.setViolation(rs.getInt("violation"));
				userVO.setUser_state(rs.getInt("user_state"));
				userVO.setUser_comment(rs.getInt("user_comment"));
				userVO.setComment_total(rs.getInt("comment_total"));
				userVO.setCash(rs.getInt("cash"));
				list.add(userVO); // Store the row in the list
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
		return list;
	}
}
