package com.bookclub_regis_detail.model;

import java.io.*;
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

import com.bookclub_regis_detail.model.BookClub_Regis_DetailVO;
import com.bookclub_report.model.BookClub_ReportJDBCDAO;

public class BookClub_Regis_DetailJNDIDAO implements BookClub_Regis_DetailDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bookshop");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO BOOKCLUB_REGIS_DETAIL (BRD_ID,MEM_ID,BC_ID,BRD_STATUS,BRD_DATE) VALUES ('BRD'||TO_CHAR(BRD_SEQ.NEXTVAL, 'FM0000'),?,?,DEFAULT,DEFAULT)";
	private static final String GET_ALL_STMT = "SELECT * FROM BOOKCLUB_REGIS_DETAIL ORDER BY BRD_ID";
	private static final String SELECT_BY_PK = "SELECT * FROM BOOKCLUB_REGIS_DETAIL WHERE BRD_ID = ?";
	private static final String DELETE = "DELETE FROM BOOKCLUB_REGIS_DETAIL WHERE BRD_ID = ?";
	private static final String FIND_BY_VERIFY = "SELECT BRD_ID, BC_ID, MEM_ID, BRD_STATUS, BRD_DATE FROM BOOKCLUB_REGIS_DETAIL WHERE BC_ID = ? AND BRD_STATUS = 1";
	private static final String UPDATE_STATUS = "UPDATE BOOKCLUB_REGIS_DETAIL SET BRD_STATUS = ? WHERE BC_ID = ? AND MEM_ID = ?";
	private static final String FIND_BY_MYSELF = "SELECT BRD_ID, BC_ID, MEM_ID, BRD_STATUS, BRD_DATE FROM BOOKCLUB_REGIS_DETAIL WHERE MEM_ID = ? ORDER BY BRD_DATE DESC";
	private static final String FIND_BY_BC_ID = "SELECT BRD_ID, BC_ID, MEM_ID, BRD_STATUS, BRD_DATE FROM BOOKCLUB_REGIS_DETAIL WHERE BC_ID = ? AND BRD_STATUS = 2";
	
	@Override
	public String insert(BookClub_Regis_DetailVO bookClub_Regis_DetailVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String brd_id = "";
		String[] pk = { "BRD_ID" };

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT, pk);

			pstmt.setString(1, bookClub_Regis_DetailVO.getMem_id());
			pstmt.setString(2, bookClub_Regis_DetailVO.getBc_id());

			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				brd_id = rs.getString(1);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
//			se.printStackTrace();
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
		return brd_id;
	}

	@Override
	public void update_Status(BookClub_Regis_DetailVO bookClub_Regis_DetailVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STATUS);

			pstmt.setInt(1, bookClub_Regis_DetailVO.getBrd_status());
			pstmt.setString(2, bookClub_Regis_DetailVO.getBc_id());
			pstmt.setString(3, bookClub_Regis_DetailVO.getMem_id());

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
	public void delete(String brd_id) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, brd_id);

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
	public BookClub_Regis_DetailVO findByPrimaryKey(String brd_id) {
		BookClub_Regis_DetailVO bookClub_Regis_DetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SELECT_BY_PK);
			pstmt.setString(1, brd_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bookClub_Regis_DetailVO = new BookClub_Regis_DetailVO();
				bookClub_Regis_DetailVO.setBrd_id(rs.getString("brd_id"));
				bookClub_Regis_DetailVO.setMem_id(rs.getString("mem_id"));
				bookClub_Regis_DetailVO.setBc_id(rs.getString("bc_id"));
				bookClub_Regis_DetailVO.setBrd_status(rs.getInt("brd_status"));
				bookClub_Regis_DetailVO.setBrd_date(rs.getTimestamp("brd_date"));

			}
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return bookClub_Regis_DetailVO;
	}

	@Override
	public List<BookClub_Regis_DetailVO> getAll() {
		List<BookClub_Regis_DetailVO> list = new ArrayList<BookClub_Regis_DetailVO>();
		BookClub_Regis_DetailVO bookClub_Regis_DetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				bookClub_Regis_DetailVO = new BookClub_Regis_DetailVO();
				bookClub_Regis_DetailVO.setBrd_id(rs.getString("brd_id"));
				bookClub_Regis_DetailVO.setMem_id(rs.getString("mem_id"));
				bookClub_Regis_DetailVO.setBc_id(rs.getString("bc_id"));
				bookClub_Regis_DetailVO.setBrd_status(rs.getInt("brd_status"));
				bookClub_Regis_DetailVO.setBrd_date(rs.getTimestamp("brd_date"));
				list.add(bookClub_Regis_DetailVO);
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
		return list;
	}

	@Override
	public List<BookClub_Regis_DetailVO> findByVerify(String bc_id) {
		List<BookClub_Regis_DetailVO> list = new ArrayList<BookClub_Regis_DetailVO>();
		BookClub_Regis_DetailVO bookClub_Regis_DetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_VERIFY);
			pstmt.setString(1, bc_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				bookClub_Regis_DetailVO = new BookClub_Regis_DetailVO();
				bookClub_Regis_DetailVO.setBrd_id(rs.getString("brd_id"));
				bookClub_Regis_DetailVO.setMem_id(rs.getString("mem_id"));
				bookClub_Regis_DetailVO.setBc_id(rs.getString("bc_id"));
				bookClub_Regis_DetailVO.setBrd_status(rs.getInt("brd_status"));
				bookClub_Regis_DetailVO.setBrd_date(rs.getTimestamp("brd_date"));
				list.add(bookClub_Regis_DetailVO);
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

		return list;
	}

	@Override
	public List<BookClub_Regis_DetailVO> findByMyself(String mem_id) {
		List<BookClub_Regis_DetailVO> list = new ArrayList<BookClub_Regis_DetailVO>();
		BookClub_Regis_DetailVO bookClub_Regis_DetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_MYSELF);
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bookClub_Regis_DetailVO = new BookClub_Regis_DetailVO();
				bookClub_Regis_DetailVO.setBrd_id(rs.getString("brd_id"));
				bookClub_Regis_DetailVO.setMem_id(rs.getString("mem_id"));
				bookClub_Regis_DetailVO.setBc_id(rs.getString("bc_id"));
				bookClub_Regis_DetailVO.setBrd_status(rs.getInt("brd_status"));
				bookClub_Regis_DetailVO.setBrd_date(rs.getTimestamp("brd_date"));
				list.add(bookClub_Regis_DetailVO);
			}
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return list;
	}

	@Override
	public List<BookClub_Regis_DetailVO> findByBc_id(String bc_id) {
		List<BookClub_Regis_DetailVO> list = new ArrayList<BookClub_Regis_DetailVO>();
		BookClub_Regis_DetailVO bookClub_Regis_DetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_BC_ID);
			pstmt.setString(1, bc_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bookClub_Regis_DetailVO = new BookClub_Regis_DetailVO();
				bookClub_Regis_DetailVO.setBrd_id(rs.getString("brd_id"));
				bookClub_Regis_DetailVO.setMem_id(rs.getString("mem_id"));
				bookClub_Regis_DetailVO.setBc_id(rs.getString("bc_id"));
				bookClub_Regis_DetailVO.setBrd_status(rs.getInt("brd_status"));
				bookClub_Regis_DetailVO.setBrd_date(rs.getTimestamp("brd_date"));
				list.add(bookClub_Regis_DetailVO);
			}
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return list;
	}

}
