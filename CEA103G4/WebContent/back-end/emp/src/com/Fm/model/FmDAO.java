package com.Fm.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class FmDAO implements FmDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bookshop");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	//新增留言
	private static final String INSERT_STMT = 
			"INSERT INTO FORUM_MESSAGES(FMID,FAID,MEMID,FMCONTENT\r\n"
			+ ")VALUES('FM' || lpad(FORUM_MSG_SEQ.NEXTVAL,3,'0'),?,?,?)";
	//顯示出一則文章的正常留言
	private static final String GET_ONE_FA_ALLFM_STMT =
			"SELECT FMID,FAID,MEMID,FMCONTENT,FMSTATUS FROM FORUM_MESSAGES WHERE FAID = ? AND FMSTATUS = 0";
	//在一篇文章下留言
	private static final String GET_ONE_FA_ADD_FM =
			"SELECT FAID,MEMID FROM FORUM_ARTICLES WHERE FAID =? ";
	//修改文章
	private static final String UPDATE_STMT = 
			"UPDATE FORUM_MESSAGES SET FMCONTENT = ? WHERE FMID = ?";
	//下架文章
	private static final String LOGOUT = 
			"UPDATE FORUM_MESSAGES SET FMSTATUS = 1 WHERE FMID=?";
	//取得一篇留言
	private static final String GET_ONE_STMT = 
			"SELECT FMID,MEMID,FAID,FMCONTENT,FMDATE,FMSTATUS FROM FORUM_MESSAGES WHERE FMID = ?";
	//取得一篇文章的回應數
	private static final String GET_ONE_FA_RESPONSE = 
			"SELECT COUNT(1) FROM FORUM_MESSAGES WHERE FAID = ? AND FMSTATUS = 0";
	
	private static final String GET_ALL = 
			"SELECT * FROM FORUM_MESSAGES";
	//取得一個會員的留言(還沒做)
	private static final String GET_ONE_MEM_FM = 
			"SELECT FMID,MEMID,FAID,FMCONTENT,FMDATE,FMSTATUS FROM FORUM_MESSAGES WHERE MEMID = ?";
	
	@Override
	public void insert(FmVO fmVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, fmVO.getFaId());
			pstmt.setString(2, fmVO.getMemId());
			pstmt.setString(3, fmVO.getFmContent());
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources ( 關閉資源 )
		}finally {
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
	public void update(FmVO fmVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, fmVO.getFmContent());
			pstmt.setString(2, fmVO.getFmId());
			
			pstmt.executeUpdate();
		}  catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources ( 關閉資源 )
		}finally {
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
	public void delete(String fmId) {

		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(LOGOUT);
			
			pstmt.setString(1,fmId);

			pstmt.executeUpdate();
		}  catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources ( 關閉資源 )
		}finally {
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
	public List<FmVO> getOneFaFm(String faId) {
		List<FmVO> list = new ArrayList<FmVO>() ;
		FmVO fmVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_FA_ALLFM_STMT);
			
			pstmt.setString(1, faId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				fmVO = new FmVO();
				fmVO.setFmId(rs.getString("fmId"));
				fmVO.setFaId(rs.getString("faId"));
				fmVO.setMemId(rs.getString("memId"));
				fmVO.setFmContent(rs.getString("fmContent"));
				fmVO.setFmStatus(rs.getInt("fmStatus"));
				list.add(fmVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources ( 關閉資源 )
		}finally {
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
	public FmVO addFmByGetFa(String faId) {
		FmVO fmVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_FA_ADD_FM);
			
			pstmt.setString(1, faId);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				fmVO = new FmVO();
				fmVO.setFaId(rs.getString("faId"));
				fmVO.setMemId(rs.getString("memId"));
			}
			
			
		}  catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		}finally {
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
		
		
		
		return fmVO;
	}

	@Override
	public FmVO getOneFmByFmId(String fmId) {
		FmVO fmVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1,fmId);

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				fmVO = new FmVO();
				fmVO.setFmId(rs.getString("FMID"));
				fmVO.setFaId(rs.getString("FAID"));
				fmVO.setFmContent(rs.getString("FMCONTENT"));
				fmVO.setMemId(rs.getString("MEMID"));
				fmVO.setFmStatus(rs.getInt("FMSTATUS"));
				fmVO.setFmDate(rs.getTimestamp("FMDATE"));
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources ( 關閉資源 )
		}finally {
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
		
		return fmVO;
	}

	@Override
	public List<FmVO> getOneMemFm(String memId) {
		List<FmVO> list = new ArrayList<FmVO>() ;
		FmVO fmVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_MEM_FM);
			
			pstmt.setString(1, memId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				fmVO = new FmVO();
				fmVO.setFmId(rs.getString("fmId"));
				fmVO.setFaId(rs.getString("faId"));
				fmVO.setMemId(rs.getString("memId"));
				fmVO.setFmContent(rs.getString("fmContent"));
				fmVO.setFmStatus(rs.getInt("fmStatus"));
				list.add(fmVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources ( 關閉資源 )
		}finally {
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
	public Integer getFmResponsesByFaId(String faId) {
		Integer responses = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_FA_RESPONSE);
			pstmt.setString(1,faId);
			
			rs = pstmt.executeQuery();
			rs.next();
			responses = rs.getInt("COUNT(1)");
		}  catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources ( 關閉資源 )
		}finally {
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
		
		return responses;
	}

	@Override
	public List<FmVO> getAll() {
		List<FmVO> list = new ArrayList<FmVO>() ;
		FmVO fmVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				fmVO = new FmVO();
				fmVO.setFmId(rs.getString("fmId"));
				fmVO.setFaId(rs.getString("faId"));
				fmVO.setMemId(rs.getString("memId"));
				fmVO.setFmContent(rs.getString("fmContent"));
				fmVO.setFmStatus(rs.getInt("fmStatus"));
				list.add(fmVO);
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
	
	
}
