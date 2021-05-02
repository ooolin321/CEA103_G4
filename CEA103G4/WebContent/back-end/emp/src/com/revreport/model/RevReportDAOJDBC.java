package com.revreport.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rev.model.RevVO;

public class RevReportDAOJDBC implements RevReportDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "JOEL";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO REVIEW_REPORT (REV_REP_ID, REV_REP_REASON, REV_REP_PROOF, REV_ID, MEM_ID) VALUES "
			+ "('REVREP'|| lpad(REV_REP_SEQ.NEXTVAL, 4, '0'), ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM REVIEW_REPORT ORDER BY REV_REP_ID";
	private static final String GET_ONE_STMT = "SELECT * FROM REVIEW_REPORT WHERE REV_REP_ID = ?";
	private static final String GET_BY_MEMID = "SELECT * FROM REVIEW_REPORT WHERE MEM_ID = ?";
	private static final String UPDATE_STATUS = "UPDATE REVIEW_REPORT SET REV_REP_STATUS=? WHERE REV_REP_ID=?";
	
	@Override
	public void insert(RevReportVO revReportVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, revReportVO.getRev_rep_reason());
			pstmt.setBytes(2, revReportVO.getRev_rep_proof());
			pstmt.setString(3, revReportVO.getRev_id());
			pstmt.setString(4, revReportVO.getMem_id());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STATUS);

			pstmt.setInt(1, revReportVO.getRev_rep_status());
			pstmt.setString(2, revReportVO.getRev_rep_id());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
			
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
			
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	
	public static void main(String[] args) {
		RevReportDAOJDBC revReportDAOJDBC = new RevReportDAOJDBC();
		
		//新增
//		RevReportVO revReportVO1 = new RevReportVO();
//		revReportVO1.setRev_rep_reason("不雅文字");
//		revReportVO1.setRev_rep_proof(null);
//		revReportVO1.setRev_id("REV0001");
//		revReportVO1.setMem_id("M0002");
//		revReportDAOJDBC.insert(revReportVO1);
//		System.out.println("新增成功");
		
		//修改狀態
//		RevReportVO revReportVO2 = new RevReportVO();
//		revReportVO2.setRev_rep_status(2);
//		revReportVO2.setRev_rep_id("REVREP0008");
//		revReportDAOJDBC.updateStatus(revReportVO2);
//		System.out.println("修改成功");
		
		//查詢單筆
//		RevReportVO revReportVO3 = revReportDAOJDBC.findByPrimaryKey("REVREP0008");
//		System.out.println(revReportVO3.getRev_rep_id() + " ");
//		System.out.println(revReportVO3.getRev_rep_reason() + " ");
//		System.out.println(revReportVO3.getRev_rep_proof() + " ");
//		System.out.println(revReportVO3.getMem_id() + " ");
//		System.out.println(revReportVO3.getRev_rep_status() + " ");
//		System.out.println(revReportVO3.getRev_id() + " ");
//		System.out.println(revReportVO3.getRev_rep_date() + " ");
		
		//查詢全部
//		List<RevReportVO> list = revReportDAOJDBC.getAll();
//		for(RevReportVO revReportVO3 : list) {
//			System.out.print(revReportVO3.getRev_rep_id() + " ");
//			System.out.print(revReportVO3.getRev_rep_reason() + " ");
//			System.out.print(revReportVO3.getRev_rep_proof() + " ");
//			System.out.print(revReportVO3.getMem_id() + " ");
//			System.out.print(revReportVO3.getRev_rep_status() + " ");
//			System.out.print(revReportVO3.getRev_id() + " ");
//			System.out.print(revReportVO3.getRev_rep_date() + " ");
//			System.out.println();
//		}
		
		List<RevReportVO> list = revReportDAOJDBC.findByMemId("M0003");
		for(RevReportVO revReportVO3 : list) {
			System.out.print(revReportVO3.getRev_rep_id() + " ");
			System.out.print(revReportVO3.getRev_rep_reason() + " ");
			System.out.print(revReportVO3.getRev_rep_proof() + " ");
			System.out.print(revReportVO3.getMem_id() + " ");
			System.out.print(revReportVO3.getRev_rep_status() + " ");
			System.out.print(revReportVO3.getRev_id() + " ");
			System.out.print(revReportVO3.getRev_rep_date() + " ");
			System.out.println();
		}
	}
}
