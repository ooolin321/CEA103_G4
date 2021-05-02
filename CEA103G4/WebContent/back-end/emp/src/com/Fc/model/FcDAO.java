package com.Fc.model;

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

import com.Fa.model.FaVO;

public class FcDAO implements FcDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bookshop");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, memId);
			pstmt.setString(2, faId);
			
			
			pstmt.executeUpdate();
			
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, memId);
			pstmt.setString(2, faId);
			

			pstmt.executeUpdate();

		}  catch (SQLException se) {
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
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(GET_ONE_MEM_COLLECTION_FA);
			pstmt.setString(1, memId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				faVO = new FaVO();
				faVO.setFaId(rs.getString("FAID"));
				list.add(faVO);
			}
			
			
		}  catch (SQLException se) {
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(CHECK_COLLECTION);
			pstmt.setString(1, memId);
			pstmt.setString(2, faId);
			rs = pstmt.executeQuery();
			rs.next();
			count = rs.getInt("COUNT(1)");
		}  catch (SQLException se) {
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

}
