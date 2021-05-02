package com.Follow.model;

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

public class FollowJNDIDAO implements FollowDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bookshop");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	//新增追蹤
	private static final String INSERT_STMT = 
			"INSERT INTO FOLLOW_LIST (MEMID,AMEMID) VALUES (?,?)";
	//刪除追蹤
	private static final String DELETE_STMT =
			"DELETE FROM FOLLOW_LIST WHERE MEMID = ? AND AMEMID = ?";
	//追蹤清單
	private static final String GET_ALL_SUBSCRIBE =
			"SELECT * FROM FOLLOW_LIST WHERE AMEMID = ?";
	//確認是否已追蹤
	private static final String CHECK_SUBSCRIBE =
			"SELECT COUNT(1) FROM FOLLOW_LIST WHERE MEMID = ? AND AMEMID = ?";
	
	@Override
	public void insert(FollowVO followVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, followVO.getMemId());
			pstmt.setString(2, followVO.getaMemId());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException("Couldn't load database driver. " +
					e.getMessage());
			// Clean up JDBC resources ( 關閉資源 )
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}

	@Override
	public void delete(FollowVO followVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);
		
			pstmt.setString(1, followVO.getMemId());
			pstmt.setString(2, followVO.getaMemId());
		
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException("Couldn't load database driver. " +
					e.getMessage());
			// Clean up JDBC resources ( 關閉資源 )
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}

	@Override
	public List<FollowVO> followList(String aMemId) {
		List<FollowVO> list = new ArrayList<FollowVO>();
		FollowVO followVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_SUBSCRIBE);
		
			pstmt.setString(1, aMemId);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				followVO = new FollowVO();
				followVO.setMemId(rs.getString("MEMID"));
				followVO.setaMemId(rs.getString("AMEMID"));
				list.add(followVO);
			}
			
			
		} catch (SQLException e) {
			throw new RuntimeException("Couldn't load database driver. " +
					e.getMessage());
			// Clean up JDBC resources ( 關閉資源 )
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public Integer checkSubscribe(String memId, String aMemId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer count = 0;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CHECK_SUBSCRIBE);
			pstmt.setString(1, memId);
			pstmt.setString(2, aMemId);
			rs = pstmt.executeQuery();
			rs.next();
			count = rs.getInt("COUNT(1)");
		} catch (SQLException e) {
			throw new RuntimeException("Couldn't load database driver. " +
					e.getMessage());
			// Clean up JDBC resources ( 關閉資源 )
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
		return count;
	}
	

}
