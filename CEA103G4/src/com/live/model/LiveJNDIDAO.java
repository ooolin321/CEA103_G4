package com.live.model;

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


public class LiveJNDIDAO implements LiveDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/admin");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO LIVE (LIVE_TYPE,LIVE_NAME,LIVE_TIME,LIVE_STATE,USER_ID,EMPNO,LIVE_PHOTO) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM LIVE ORDER BY LIVE_NO";
	private static final String GET_ONE_STMT = "SELECT * FROM LIVE WHERE LIVE_NO = ?";
	private static final String DELETE = "DELETE FROM LIVE WHERE LIVE_NO = ?";
	private static final String UPDATE = "UPDATE LIVE SET LIVE_TYPE=?, LIVE_NAME=?, LIVE_TIME=?, LIVE_STATE=? ,USER_ID=?,EMPNO=?,LIVE_PHOTO=? WHERE LIVE_NO = ?";
	private static final String GET_ALL_STATE1 = "SELECT * FROM LIVE ORDER BY LIVE_STATE DESC, LIVE_TIME DESC";
	@Override
	public void insert(LiveVO liveVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			
			pstmt.setString(1, liveVO.getLive_type());
			pstmt.setString(2, liveVO.getLive_name());
			pstmt.setTimestamp(3, liveVO.getLive_time());
			pstmt.setInt(4, liveVO.getLive_state());
			pstmt.setString(5, liveVO.getUser_id());
			pstmt.setInt(6, liveVO.getEmpno());
			pstmt.setBytes(7, liveVO.getLive_photo());
			pstmt.executeUpdate();

		
		} catch (SQLException se) {
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
	public void update(LiveVO liveVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, liveVO.getLive_type());
			pstmt.setString(2, liveVO.getLive_name());
			pstmt.setTimestamp(3, liveVO.getLive_time());
			pstmt.setInt(4, liveVO.getLive_state());
			pstmt.setString(5, liveVO.getUser_id());
			pstmt.setInt(6, liveVO.getEmpno());
			pstmt.setBytes(7, liveVO.getLive_photo());
			pstmt.setInt(8, liveVO.getLive_no());

			pstmt.executeUpdate();

			// Handle any driver errors
		
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
	public void delete(Integer live_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, live_no);

			pstmt.executeUpdate();

			// Handle any driver errors
		
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
	public LiveVO findByPrimaryKey(Integer live_no) {

		LiveVO liveVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, live_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				liveVO = new LiveVO();
				liveVO.setLive_no(rs.getInt("live_no"));
				liveVO.setLive_type(rs.getString("live_type"));
				liveVO.setLive_name(rs.getString("live_name"));
				liveVO.setLive_time(rs.getTimestamp("live_time"));
				liveVO.setLive_state(rs.getInt("live_state"));
				liveVO.setUser_id(rs.getString("user_id"));
				liveVO.setEmpno(rs.getInt("empno"));
				liveVO.setLive_photo(rs.getBytes("live_photo"));
			}

			// Handle any driver errors
		
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
		return liveVO;
	}

	@Override
	public List<LiveVO> getAll() {
		List<LiveVO> list = new ArrayList<LiveVO>();
		LiveVO liveVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				liveVO = new LiveVO();
				liveVO.setLive_no(rs.getInt("live_no"));
				liveVO.setLive_type(rs.getString("live_type"));
				liveVO.setLive_name(rs.getString("live_name"));
				liveVO.setLive_time(rs.getTimestamp("live_time"));
				liveVO.setLive_state(rs.getInt("live_state"));
				liveVO.setUser_id(rs.getString("user_id"));
				liveVO.setEmpno(rs.getInt("empno"));
				liveVO.setLive_photo(rs.getBytes("live_photo"));
				list.add(liveVO); // Store the row in the list
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
	public List<LiveVO> getAll1() {
		List<LiveVO> list = new ArrayList<LiveVO>();
		LiveVO liveVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STATE1);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				liveVO = new LiveVO();
				liveVO.setLive_no(rs.getInt("live_no"));
				liveVO.setLive_type(rs.getString("live_type"));
				liveVO.setLive_name(rs.getString("live_name"));
				liveVO.setLive_time(rs.getTimestamp("live_time"));
				liveVO.setLive_state(rs.getInt("live_state"));
				liveVO.setUser_id(rs.getString("user_id"));
				liveVO.setEmpno(rs.getInt("empno"));
				liveVO.setLive_photo(rs.getBytes("live_photo"));
				list.add(liveVO); // Store the row in the list
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
