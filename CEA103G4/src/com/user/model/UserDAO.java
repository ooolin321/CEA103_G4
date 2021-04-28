package com.user.model;

import java.util.*;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.live_report.model.Live_reportVO;

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
			"INSERT INTO `USER` (`USER_ID`,`USER_PWD`,`USER_NAME`,`ID_CARD`,`USER_GENDER`,`USER_DOB`,`USER_MAIL`,`USER_PHONE`,`USER_MOBILE`,`CITY`,`TOWN`,`ZIPCODE`,`USER_ADDR`,`REGDATE`,`USER_POINT`,`VIOLATION`,`USER_STATE`,`USER_COMMENT`,`COMMENT_TOTAL`,`CASH`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
			"SELECT * FROM `USER` ORDER BY `USER_ID`";
	private static final String GET_ONE_STMT = 
			"SELECT `USER_ID`,`USER_PWD`,`USER_NAME`,`ID_CARD`,`USER_GENDER`,`USER_DOB`,`USER_MAIL`,`USER_PHONE`,`USER_MOBILE`,`CITY`,`TOWN`,`ZIPCODE`,`USER_ADDR`,`REGDATE`,`USER_POINT`,`VIOLATION`,`USER_STATE`,`USER_COMMENT`,`COMMENT_TOTAL`,`CASH` FROM USER WHERE `USER_ID` = ?";
	private static final String DELETE = 
			"DELETE FROM USER where USER_ID = ?";
	private static final String UPDATE = 
			"UPDATE `USER` SET `USER_PWD`=?, `USER_NAME`=?, `ID_CARD`=?, `USER_GENDER`=?, `USER_DOB`=?, `USER_MAIL`=?, `USER_PHONE`=?, `USER_MOBILE`=?, `CITY`=?, `TOWN`=?, `ZIPCODE`=?, `USER_ADDR`=?, `REGDATE`=?, `USER_POINT`=?, `VIOLATION`=?, `USER_STATE`=?, `USER_COMMENT`=?, `COMMENT_TOTAL`=?, `CASH`=? WHERE `USER_ID` = ?";
	private static final String GET_Live_reportByUser_id_STMT = 
			"SELECT LIVE_REPORT_NO,LIVE_REPORT_CONTENT,LIVE_NO,USER_ID,EMPNO,LIVE_REPORT_STATE,REPORT_DATE,PHOTO FROM LIVE_REPORT where USER_ID = ? ORDER BY LIVE_REPORT_NO";
	private static final String SIGN_IN = 
			"SELECT USER_ID,USER_PWD,USER_NAME FROM USER where USER_ID=? AND USER_PWD=?";
	
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
			pstmt.setString(10, userVO.getCity());
			pstmt.setString(11, userVO.getTown());
			pstmt.setInt(12, userVO.getZipcode());
			pstmt.setString(13, userVO.getUser_addr());
			pstmt.setDate(14, userVO.getRegdate());
			pstmt.setInt(15, userVO.getUser_point());
			pstmt.setInt(16, userVO.getViolation());
			pstmt.setInt(17, userVO.getUser_state());
			pstmt.setInt(18, userVO.getUser_comment());
			pstmt.setInt(19, userVO.getComment_total());
			pstmt.setInt(20, userVO.getCash());

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
			pstmt.setString(9, userVO.getCity());
			pstmt.setString(10, userVO.getTown());
			pstmt.setInt(11, userVO.getZipcode());
			pstmt.setString(12, userVO.getUser_addr());
			pstmt.setDate(13, userVO.getRegdate());
			pstmt.setInt(14, userVO.getUser_point());
			pstmt.setInt(15, userVO.getViolation());
			pstmt.setInt(16, userVO.getUser_state());
			pstmt.setInt(17, userVO.getUser_comment());
			pstmt.setInt(18, userVO.getComment_total());
			pstmt.setInt(19, userVO.getCash());
			pstmt.setString(20, userVO.getUser_id());

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
				userVO.setCity(rs.getString("city"));
				userVO.setTown(rs.getString("town"));
				userVO.setZipcode(rs.getInt("zipcode"));
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
				userVO.setCity(rs.getString("city"));
				userVO.setTown(rs.getString("town"));
				userVO.setZipcode(rs.getInt("zipcode"));
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

	@Override
	public Set<Live_reportVO> getLive_reportByUser_id(String user_id) {
		Set<Live_reportVO> set = new LinkedHashSet<Live_reportVO>();
		Live_reportVO live_reportVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Live_reportByUser_id_STMT);
			pstmt.setString(1, user_id);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				live_reportVO = new Live_reportVO();
				live_reportVO.setLive_report_no(rs.getInt("live_report_no"));
				live_reportVO.setLive_report_content(rs.getString("live_report_content"));
				live_reportVO.setLive_no(rs.getInt("live_no"));
				live_reportVO.setUser_id(rs.getString("user_id"));
				live_reportVO.setEmpno(rs.getInt("empno"));
				live_reportVO.setLive_report_state(rs.getInt("live_report_state"));
				live_reportVO.setReport_date(rs.getTimestamp("report_date"));
				live_reportVO.setPhoto(rs.getBytes("photo"));
				set.add(live_reportVO); // Store the row in the vector
			}
	
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return set;
	}

	@Override
	public UserVO login(String user_id, String user_pwd) {
		
		UserVO userVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SIGN_IN);
				
			pstmt.setString(1, user_id);
			pstmt.setString(2, user_pwd);	
			
			rs = pstmt.executeQuery();
			while (rs.next()) {

				userVO = new UserVO();
				userVO.setUser_id(rs.getString("user_id"));
				userVO.setUser_pwd(rs.getString("user_pwd"));
				userVO.setUser_name(rs.getString("user_name"));
			}

		} catch (SQLException se) {
			throw new RuntimeException("database發生錯誤." + se.getMessage());
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
}
