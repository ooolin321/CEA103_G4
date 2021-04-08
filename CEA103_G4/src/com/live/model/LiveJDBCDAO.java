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


public class LiveJDBCDAO implements LiveDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/admin");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO LIVE (live_id,live_type,live_name,live_time,live_state,user_id,empno) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM LIVE order by live_id";
	private static final String GET_ONE_STMT = "SELECT * FROM LIVE where live_id = ?";
	private static final String DELETE = "DELETE FROM LIVE where live_id = ?";
	private static final String UPDATE = "UPDATE LIVE set live_type=?, live_name=?, live_time=?, live_state=? where live_id = ?";

	@Override
	public void insert(LiveVO liveVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, liveVO.getLive_id());
			pstmt.setString(2, liveVO.getLive_type());
			pstmt.setString(3, liveVO.getLive_name());
			pstmt.setDate(4, liveVO.getLive_time());
			pstmt.setString(5, liveVO.getLive_state());
			pstmt.setString(6, liveVO.getUser_id());
			pstmt.setInt(7, liveVO.getEmpno());

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
			pstmt.setDate(3, liveVO.getLive_time());
			pstmt.setString(4, liveVO.getLive_state());
			pstmt.setInt(5, liveVO.getLive_id());

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
	public void delete(Integer live_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, live_id);

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
	public LiveVO findByPrimaryKey(Integer live_id) {

		LiveVO liveVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, live_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				liveVO = new LiveVO();
				liveVO.setLive_id(rs.getInt("live_id"));
				liveVO.setLive_type(rs.getString("live_type"));
				liveVO.setLive_name(rs.getString("live_name"));
				liveVO.setLive_time(rs.getDate("live_time"));
				liveVO.setLive_state(rs.getString("live_state"));
				liveVO.setUser_id(rs.getString("user_id"));
				liveVO.setEmpno(rs.getInt("empno"));
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
				liveVO.setLive_id(rs.getInt("live_id"));
				liveVO.setLive_type(rs.getString("live_type"));
				liveVO.setLive_name(rs.getString("live_name"));
				liveVO.setLive_time(rs.getDate("live_time"));
				liveVO.setLive_state(rs.getString("live_state"));
				liveVO.setUser_id(rs.getString("user_id"));
				liveVO.setEmpno(rs.getInt("empno"));
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
