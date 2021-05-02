package com.revreport.model;

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

public class RevReportDAO implements RevReportDAO_interface{
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bookshop");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = "INSERT INTO REVIEW_REPORT (REV_REP_ID, REV_REP_REASON, REV_REP_PROOF, REV_ID, MEM_ID) VALUES "
			+ "('REVREP'|| lpad(REV_REP_SEQ.NEXTVAL, 4, '0'), ?, null, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM REVIEW_REPORT ORDER BY REV_REP_ID";
	private static final String GET_ONE_STMT = "SELECT * FROM REVIEW_REPORT WHERE REV_REP_ID = ?";
	private static final String GET_BY_MEMID = "SELECT * FROM REVIEW_REPORT WHERE MEM_ID = ?";
	private static final String UPDATE_STATUS = "UPDATE REVIEW_REPORT SET REV_REP_STATUS=? WHERE REV_REP_ID=?";
	
	@Override
	public void insert(RevReportVO revReportVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, revReportVO.getRev_rep_reason());
			pstmt.setString(2, revReportVO.getRev_id());
			pstmt.setString(3, revReportVO.getMem_id());

			pstmt.executeUpdate();

			// Handle any SQL errors
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
	public void updateStatus(RevReportVO revReportVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STATUS);

			pstmt.setInt(1, revReportVO.getRev_rep_status());
			pstmt.setString(2, revReportVO.getRev_rep_id());

			pstmt.executeUpdate();

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
	public void delete(String rev_rep_id) {
		
	}

	@Override
	public RevReportVO findByPrimaryKey(String rev_rep_id) {
		RevReportVO revReportVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, rev_rep_id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				revReportVO = new RevReportVO();
				revReportVO.setRev_rep_id(rev_rep_id);
				revReportVO.setRev_id(rs.getString("rev_id"));
				revReportVO.setMem_id(rs.getString("mem_id"));
				revReportVO.setRev_rep_status(rs.getInt("rev_rep_status"));
				revReportVO.setRev_rep_reason(rs.getString("rev_rep_reason"));
				revReportVO.setRev_rep_proof(rs.getBytes("rev_rep_proof"));
				revReportVO.setRev_rep_date(rs.getTimestamp("rev_rep_date"));
			}
			
			// Handle any SQL errors
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
		return revReportVO;
	}

	@Override
	public List<RevReportVO> findByMemId(String mem_id) {
		List<RevReportVO> list = new ArrayList<RevReportVO>();
		RevReportVO revReportVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_MEMID);
			
			pstmt.setString(1, mem_id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				revReportVO = new RevReportVO();
				revReportVO.setRev_rep_id(rs.getString("rev_rep_id"));
				revReportVO.setRev_id(rs.getString("rev_id"));
				revReportVO.setMem_id(mem_id);
				revReportVO.setRev_rep_status(rs.getInt("rev_rep_status"));
				revReportVO.setRev_rep_reason(rs.getString("rev_rep_reason"));
				revReportVO.setRev_rep_proof(rs.getBytes("rev_rep_proof"));
				revReportVO.setRev_rep_date(rs.getTimestamp("rev_rep_date"));
				list.add(revReportVO);
			}
			
			// Handle any SQL errors
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
	public List<RevReportVO> getAll() {
		List<RevReportVO> list = new ArrayList<RevReportVO>();
		RevReportVO revReportVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {	
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				revReportVO = new RevReportVO();
				revReportVO.setRev_rep_id(rs.getString("rev_rep_id"));
				revReportVO.setRev_id(rs.getString("rev_id"));
				revReportVO.setMem_id(rs.getString("mem_id"));
				revReportVO.setRev_rep_status(rs.getInt("rev_rep_status"));
				revReportVO.setRev_rep_reason(rs.getString("rev_rep_reason"));
				revReportVO.setRev_rep_proof(rs.getBytes("rev_rep_proof"));
				revReportVO.setRev_rep_date(rs.getTimestamp("rev_rep_date"));
				list.add(revReportVO);
			}
			
			// Handle any SQL errors
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

