package com.payme.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class PayDAO implements PayDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bookshop");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO PAYMENT_METHOD VALUES (?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT * FROM PAYMENT_METHOD ORDER BY MEM_ID";
	private static final String GET_ONE_STMT = "SELECT * FROM PAYMENT_METHOD where MEM_ID = ?";
	private static final String DELETE = "DELETE FROM PAYMENT_METHOD WHERE CREDIT_CARD_NUM = ?";
	private static final String UPDATE = "UPDATE PAYMENT_METHOD SET CREDIT_CARD_NUM=?, CREDIT_CARD_YEAR=?, CREDIT_CARD_MON=?"
			+ "WHERE CREDIT_CARD_NUM=? AND MEM_ID=?";

	@Override
	public void insert(PayVO payVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, payVO.getCredit_card_num());
			pstmt.setInt(2, payVO.getCredit_card_year());
			pstmt.setInt(3, payVO.getCredit_card_mon());
			pstmt.setString(4, payVO.getMem_id());

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
	public void update(PayVO payVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, payVO.getCredit_card_num());
			pstmt.setInt(2, payVO.getCredit_card_year());
			pstmt.setInt(3, payVO.getCredit_card_mon());
			pstmt.setString(4, payVO.getCredit_card_num());
			pstmt.setString(5, payVO.getMem_id());

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
	public void delete(String credit_card_num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, credit_card_num);

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
	public List<PayVO> findByMemberId(String mem_id) {
		List<PayVO> list = new ArrayList<PayVO>();
		PayVO payVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, mem_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				payVO = new PayVO();
				payVO.setMem_id(rs.getString("mem_id"));
				payVO.setCredit_card_num(rs.getString("credit_card_num"));
				payVO.setCredit_card_year(rs.getInt("credit_card_year"));
				payVO.setCredit_card_mon(rs.getInt("credit_card_mon"));
				list.add(payVO);
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
	public List<PayVO> getAll() {
		List<PayVO> list = new ArrayList<PayVO>();

		PayVO payVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				payVO = new PayVO();
				payVO.setMem_id(rs.getString("mem_id"));
				payVO.setCredit_card_num(rs.getString("credit_card_num"));
				payVO.setCredit_card_year(rs.getInt("credit_card_year"));
				payVO.setCredit_card_mon(rs.getInt("credit_card_mon"));
				list.add(payVO);
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
