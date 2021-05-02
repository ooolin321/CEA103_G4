package com.cs.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class CsDAO implements CsDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bookshop");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_CS = "INSERT INTO CUSTOMER_SERVICE (CS_ID,CS_EMAIL,CS_TEL,CS_SUBJECT,CS_MESSAGE,CS_ISSEND) "
			+ "VALUES ('CS'||lpad(CUSTOMER_SERVICE_SEQ.NEXTVAL,6,'0'),?,?,?,?,?)";
	private static final String GET_ALL_CS = "SELECT * FROM CUSTOMER_SERVICE ORDER BY  CS_ID DESC";
	private static final String GET_ONE_CS = "SELECT * FROM MEMBER WHERE CS_ID = ?";
	private static final String DELETE_CS = "DELETE FROM CUSTOMER_SERVICE WHERE CS_ID = ?";
	private static final String UPDATE_CS = "UPDATE CUSTOMER_SERVICE SET CS_ISSEND=? WHERE CS_ID=?";
	private static final String GET_SEARCH = "SELECT * FROM CUSTOMER_SERVICE WHERE CS_EMAIL LIKE '%' || ? || '%' ORDER BY  CS_ID DESC ";
	@Override
	public void insert(CsVO csVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_CS);
			pstmt.setString(1, csVO.getCs_Email());
			pstmt.setString(2, csVO.getCs_Tel());
			pstmt.setString(3, csVO.getCs_Subject());
			pstmt.setString(4, csVO.getCs_Message());
			pstmt.setInt(5, csVO.getCs_isSend());
			pstmt.executeUpdate();
		}catch (SQLException se) {
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
	public void delete(String cs_ID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_CS);			
			pstmt.setString(1, cs_ID);
			pstmt.executeUpdate();
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
	public CsVO findByPrimaryKey(String cs_ID) {
		CsVO csVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_CS);
			pstmt.setString(1, cs_ID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				csVO = new CsVO();
				csVO.setCs_ID(rs.getString("cs_ID"));
				csVO.setCs_Email(rs.getString("cs_Email"));
				csVO.setCs_Tel(rs.getString("cs_Tel"));
				csVO.setCs_Subject(rs.getString("cs_Subject"));
				csVO.setCs_Message(rs.getString("cs_Message"));
				csVO.setCs_isSend(rs.getInt("cs_isSend"));
				csVO.setCs_Time(rs.getTimestamp("cs_Time"));
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
		return csVO;
	}

	@Override
	public List<CsVO> getAll() {
		List<CsVO> list = new ArrayList<CsVO>();
		CsVO csVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_CS);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				csVO = new CsVO();
				csVO.setCs_ID(rs.getString("cs_ID"));
				csVO.setCs_Email(rs.getString("cs_Email"));
				csVO.setCs_Tel(rs.getString("cs_Tel"));
				csVO.setCs_Subject(rs.getString("cs_Subject"));
				csVO.setCs_Message(rs.getString("cs_Message"));
				csVO.setCs_isSend(rs.getInt("cs_isSend"));
				csVO.setCs_Time(rs.getTimestamp("cs_Time"));
				
				list.add(csVO);		
			}
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

	@Override
	public void update(CsVO csVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_CS);

			pstmt.setInt(1, csVO.getCs_isSend());
			pstmt.setString(2, csVO.getCs_ID());
			pstmt.executeUpdate();
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
	public List<CsVO> findBySearch(String cssearch) {
		List<CsVO> list = new ArrayList<CsVO>();
		CsVO csVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_SEARCH);
			pstmt.setString(1,cssearch);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				csVO = new CsVO();
				csVO.setCs_ID(rs.getString("cs_ID"));
				csVO.setCs_Email(rs.getString("cs_Email"));
				csVO.setCs_Tel(rs.getString("cs_Tel"));
				csVO.setCs_Subject(rs.getString("cs_Subject"));
				csVO.setCs_Message(rs.getString("cs_Message"));
				csVO.setCs_isSend(rs.getInt("cs_isSend"));
				csVO.setCs_Time(rs.getTimestamp("cs_Time"));
				
				list.add(csVO);		
			}
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

}
