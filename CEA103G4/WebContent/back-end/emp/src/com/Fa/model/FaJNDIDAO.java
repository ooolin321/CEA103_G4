package com.Fa.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


import java.io.IOException;
import java.sql.*;

public class FaJNDIDAO implements FaDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bookshop");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO FORUM_ARTICLES (FAID,MEMID,FATOPIC,FACONTENT\r\n"
			+ ")VALUES('FA' || lpad(FORUM_ART_SEQ.NEXTVAL,3,'0'),?,?,?)";
	//取得狀態正常的文章
	private static final String GET_ALL_STMT = "SELECT * FROM FORUM_ARTICLES WHERE FASTATUS = 0 ORDER BY FAID DESC";
	//取得狀態正常的熱門文章(瀏覽數)
	private static final String GET_ALL_STMT_HOT = "SELECT * FROM FORUM_ARTICLES WHERE FASTATUS = 0 ORDER BY FAVIEWS DESC";
	//取得一個文章所有資訊(FAID)
	private static final String GET_ONE_STMT = "SELECT FAID,MEMID,FATOPIC,FACONTENT,FADATE,FASTATUS,FAVIEWS FROM FORUM_ARTICLES WHERE FAID = ?";
	//取得一個會員的所有文章
	private static final String GET_ONE_MEM_FA_STMT = "SELECT FAID,MEMID,FATOPIC,FACONTENT,FADATE,FASTATUS,FAVIEWS FROM FORUM_ARTICLES WHERE MEMID = ? AND FASTATUS = 0 ORDER BY FADATE DESC";
	//更改文章狀態 = 1(下架)
	private static final String UPDATE_LOGOUT = "UPDATE FORUM_ARTICLES SET FASTATUS = 1 WHERE FAID = ?";
	//會員修改自己的文章
	private static final String UPDATE_STMT = "UPDATE FORUM_ARTICLES SET FATOPIC = ? , FACONTENT = ? WHERE FAID=?";
	//文章瀏覽數++
	private static final String ADD_VIEWS = "UPDATE FORUM_ARTICLES SET FAVIEWS = ? WHERE FAID=?";
	//模糊
	private static final String RESEARCH_STMT = "SELECT * FROM FORUM_ARTICLES WHERE FATOPIC LIKE ? AND FASTATUS = 0";

	
	
	@Override
	public String insert(FaVO faVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String faId = null;
		try {
			con = ds.getConnection();
			String[] col = {"FAID"};
			pstmt = con.prepareStatement(INSERT_STMT , col);

			pstmt.setString(1, faVO.getMemId());
			pstmt.setString(2, faVO.getFaTopic());
			pstmt.setString(3, faVO.getFaContent());
			
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			
			rs.next();
			faId = rs.getString(1);
			System.out.println("FAID : " + faId);
			
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
		return faId;
	}

	@Override
	public void update(FaVO faVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, faVO.getFaTopic());
			pstmt.setString(2, faVO.getFaContent());
			pstmt.setString(3, faVO.getFaId());

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
	public void delete(String faId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_LOGOUT);

			pstmt.setString(1, faId);

			pstmt.executeUpdate();
			// Handle any driver errors ( JDBC 驅動 )
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
	public FaVO findOneFaByFaId(String faId) {
		FaVO faVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, faId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				faVO = new FaVO();
				faVO.setFaId(rs.getString("FAID"));
				faVO.setMemId(rs.getString("MEMID"));
				faVO.setFaTopic(rs.getString("FATOPIC"));
				faVO.setFaContent(rs.getString("FACONTENT"));
				faVO.setFaDate(rs.getTimestamp("FADATE"));
				faVO.setFaStatus(rs.getInt("FASTATUS"));
				faVO.setFaViews(rs.getInt("FAVIEWS"));

			}

			// Handle any driver errors ( JDBC 驅動 )
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

		return faVO;
	}
	
	@Override
	public List<FaVO> getAllIndex() {
		List<FaVO> list = new ArrayList<FaVO>();
		FaVO faVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
					faVO = new FaVO();
					faVO.setFaId(rs.getString("FAID"));
					faVO.setMemId(rs.getString("MEMID"));
					faVO.setFaTopic(rs.getString("FATOPIC"));
					faVO.setFaContent(rs.getString("FACONTENT"));
					faVO.setFaDate(rs.getTimestamp("FADATE"));
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
	public List<FaVO> findOneMemFa(String memId) {
		List<FaVO> list = new ArrayList<FaVO>();
		FaVO faVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_MEM_FA_STMT);
			pstmt.setString(1, memId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
					faVO = new FaVO();
					faVO.setFaId(rs.getString("FAID"));
					faVO.setMemId(rs.getString("MEMID"));
					faVO.setFaTopic(rs.getString("FATOPIC"));
					faVO.setFaContent(rs.getString("FACONTENT"));
					faVO.setFaDate(rs.getTimestamp("FADATE"));
					faVO.setFaStatus(rs.getInt("FASTATUS"));
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
	public List<FaVO> getAllHot() {
		List<FaVO> list = new ArrayList<FaVO>();
		FaVO faVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT_HOT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
					faVO = new FaVO();
					faVO.setFaId(rs.getString("FAID"));
					faVO.setMemId(rs.getString("MEMID"));
					faVO.setFaTopic(rs.getString("FATOPIC"));
					faVO.setFaContent(rs.getString("FACONTENT"));
					faVO.setFaDate(rs.getTimestamp("FADATE"));
					faVO.setFaStatus(rs.getInt("FASTATUS"));
					faVO.setFaViews(rs.getInt("FAVIEWS"));
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
	public void addFaViews(FaVO faVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(ADD_VIEWS);

			pstmt.setInt(1, faVO.getFaViews());
			pstmt.setString(2, faVO.getFaId());

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
	public List<FaVO> search(String faTopic) {
		List<FaVO> list = new ArrayList<FaVO>();
		FaVO faVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(RESEARCH_STMT);
			
			pstmt.setString(1,"%"+faTopic+"%");
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
					faVO = new FaVO();
					faVO.setFaId(rs.getString("FAID"));
					faVO.setMemId(rs.getString("MEMID"));
					faVO.setFaTopic(rs.getString("FATOPIC"));
					faVO.setFaContent(rs.getString("FACONTENT"));
					faVO.setFaDate(rs.getTimestamp("FADATE"));
					list.add(faVO);
			}
			
		} catch (SQLException se) {
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
