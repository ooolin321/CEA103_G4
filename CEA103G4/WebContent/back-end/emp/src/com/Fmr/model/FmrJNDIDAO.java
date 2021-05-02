package com.Fmr.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.Far.model.FarVO;

import java.sql.*;

public class FmrJNDIDAO implements FmrDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bookshop");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	//新增留言檢舉
	private static final String INSERT_STMT = 
			"INSERT INTO FORUM_MESSAGE_REPORTS(FMRID,FMID,MEMID,FMRCONTENT)"
			+"VALUES('FMR'|| LPAD(FORUM_MES_REP_SEQ.nextval,3,'0'),?,?,?)";
	//更改留言檢舉狀態
	private static final String JUDGE_STMT = 
			"UPDATE FORUM_MESSAGE_REPORTS SET FMRSTATUS = ?,ADMINID = ? WHERE FMRID = ?";
	//所有留言檢舉資料
	private static final String GET_ALL_STMT = 
			"SELECT FMRID,FMID,MEMID,FMRSTATUS,FMRCONTENT,FMRDATE FROM FORUM_MESSAGE_REPORTS";
	//留言檢舉待審核資料
	private static final String GET_NORMAL_FMR_STMT = 
			"SELECT * FROM FORUM_MESSAGE_REPORTS WHERE FMRSTATUS = 0";
	
	
	@Override
	public void insert(FmrVO fmrVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1,fmrVO.getFmId());
			pstmt.setString(2, fmrVO.getMemId());
			pstmt.setString(3, fmrVO.getFmrContent());
			
			pstmt.executeUpdate();
			
		}  catch (SQLException e) {
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
	public void judge_Fmr(FmrVO fmrVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(JUDGE_STMT);
			
			pstmt.setInt(1, fmrVO.getFmrStatus());
			pstmt.setString(2, fmrVO.getAdminId());
			pstmt.setString(3, fmrVO.getFmrId());
			
			pstmt.executeUpdate();
			
			
			// Handle any driver errors  ( JDBC 驅動 )
		}  catch (SQLException e) {
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
	public List<FmrVO> getAll() {
		List<FmrVO> list = new ArrayList<FmrVO>();
		FmrVO fmrVO = null;
		
		Connection con  = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				fmrVO = new FmrVO();
				fmrVO.setFmrId(rs.getString("FMRID"));
				fmrVO.setFmId(rs.getString("FMID"));
				fmrVO.setMemId(rs.getString("MEMID"));
				fmrVO.setFmrContent(rs.getString("FMRCONTENT"));
				fmrVO.setFmrStatus(rs.getInt("FMRSTATUS"));
				fmrVO.setFmrDate(rs.getTimestamp("FMRDATE"));
				list.add(fmrVO);
			}
			
		}  catch (SQLException e) {
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
	public List<FmrVO> getAllJundge() {
		List<FmrVO> list = new ArrayList<FmrVO>();
		FmrVO fmrVO = null;
		
		Connection con  = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_NORMAL_FMR_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				fmrVO = new FmrVO();
				fmrVO.setFmrId(rs.getString("FMRID"));
				fmrVO.setFmId(rs.getString("FMID"));
				fmrVO.setMemId(rs.getString("MEMID"));
				fmrVO.setFmrContent(rs.getString("FMRCONTENT"));
				fmrVO.setFmrStatus(rs.getInt("FMRSTATUS"));
				fmrVO.setFmrDate(rs.getTimestamp("FMRDATE"));
				list.add(fmrVO);
			}
			
		}   catch (SQLException e) {
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
			
	
	
	
	
}
