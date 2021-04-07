package com.pdc.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class PdcJNDIDAO implements PdcDAO_interface{

	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/admin");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = 
			"INSERT INTO `PRODUCT` (`PRODUCT_NAME`,`PRODUCT_INFO`,`PRODUCT_PRICE`,`PRODUCT_QUANTITY`,`PRODUCT_REMAINING`,`PRODUCT_STATE`,`PRODUCT_PHOTO`,`USER_ID`,`PDTYPE_ID`,`START_PRICE`,`LIVE_ID`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
			"SELECT `PRODUCT_ID`,`PRODUCT_NAME`,`PRODUCT_INFO`,`PRODUCT_PRICE`,`PRODUCT_QUANTITY`,`PRODUCT_REMAINING`,`PRODUCT_STATE`,`PRODUCT_PHOTO`,`USER_ID`,`PDTYPE_ID`,`START_PRICE`,`LIVE_ID` FROM `PRODUCT` ORDER BY `PRODUCT_ID`";
	private static final String GET_ONE_STMT = 
			"SELECT `PRODUCT_ID`,`PRODUCT_NAME`,`PRODUCT_INFO`,`PRODUCT_PRICE`,`PRODUCT_QUANTITY`,`PRODUCT_REMAINING`,`PRODUCT_STATE`,`PRODUCT_PHOTO`,`USER_ID`,`PDTYPE_ID`,`START_PRICE`,`LIVE_ID` FROM `PRODUCT` WHERE `PRODUCT_ID` = ?";
	private static final String DELETE = 
			"DELETE FROM `PRODUCT` WHERE `PRODUCT_ID` = ?";
	private static final String UPDATE = 
			"UPDATE `PRODUCT` SET `PRODUCT_NAME`=?, `PRODUCT_INFO`=?, `PRODUCT_PRICE`=?, `PRODUCT_QUANTITY`=?, `PRODUCT_REMAINING`=?, `PRODUCT_STATE`=?, `PRODUCT_PHOTO`=?, `USER_ID`=?,`PDTYPE_ID`=?,`START_PRICE`=?,`LIVE_ID`=? WHERE `PRODUCT_ID` = ?";
	
	
	
	@Override
	public void insert(PdcVO pdcVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, pdcVO.getProduct_name());
			pstmt.setString(2, pdcVO.getProduct_info());
			pstmt.setInt(3, pdcVO.getProduct_price());
			pstmt.setInt(4, pdcVO.getProduct_quantity());
			pstmt.setInt(5, pdcVO.getProduct_remaining());
			pstmt.setString(6, pdcVO.getProduct_state());
			pstmt.setByte(7, pdcVO.getProduct_photo());
			pstmt.setString(8, pdcVO.getUser_id());
			pstmt.setString(9, pdcVO.getPdtype_id());
			pstmt.setInt(10, pdcVO.getStart_price());
			pstmt.setInt(11, pdcVO.getLive_id());

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
	public void update(PdcVO pdcVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, pdcVO.getProduct_name());
			pstmt.setString(2, pdcVO.getProduct_info());
			pstmt.setInt(3, pdcVO.getProduct_price());
			pstmt.setInt(4, pdcVO.getProduct_quantity());
			pstmt.setInt(5, pdcVO.getProduct_remaining());
			pstmt.setString(6, pdcVO.getProduct_state());
			pstmt.setByte(7, pdcVO.getProduct_photo());
			pstmt.setString(8, pdcVO.getUser_id());
			pstmt.setString(9, pdcVO.getPdtype_id());
			pstmt.setInt(10, pdcVO.getStart_price());
			pstmt.setInt(11, pdcVO.getLive_id());

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
	public void delete(Integer product_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, product_id);

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
	public PdcVO findByPrimaryKey(Integer product_id) {

		PdcVO pdcVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, product_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				//
				pdcVO = new PdcVO();
				pdcVO.setProduct_id(rs.getInt("product_id"));
				pdcVO.setProduct_name(rs.getString("product_name"));
				pdcVO.setProduct_info(rs.getString("product_info"));
				pdcVO.setProduct_price(rs.getInt("product_price"));
				pdcVO.setProduct_quantity(rs.getInt("product_quantity"));
				pdcVO.setProduct_remaining(rs.getInt("product_remaining"));
				pdcVO.setProduct_state(rs.getString("product_state"));
				pdcVO.setProduct_photo(rs.getByte("product_photo"));
				pdcVO.setUser_id(rs.getString("user_id"));
				pdcVO.setPdtype_id(rs.getString("pdtype_id"));
				pdcVO.setStart_price(rs.getInt("start_price"));
				pdcVO.setLive_id(rs.getInt("live_id"));
				
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
		return pdcVO;
	}

	@Override
	public List<PdcVO> getAll() {
		List<PdcVO> list = new ArrayList<PdcVO>();
		PdcVO pdcVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				//
				pdcVO = new PdcVO();
				pdcVO.setProduct_id(rs.getInt("product_id"));
				pdcVO.setProduct_name(rs.getString("product_name"));
				pdcVO.setProduct_info(rs.getString("product_info"));
				pdcVO.setProduct_price(rs.getInt("product_price"));
				pdcVO.setProduct_quantity(rs.getInt("product_quantity"));
				pdcVO.setProduct_remaining(rs.getInt("product_remaining"));
				pdcVO.setProduct_state(rs.getString("product_state"));
				pdcVO.setProduct_photo(rs.getByte("product_photo"));
				pdcVO.setUser_id(rs.getString("user_id"));
				pdcVO.setPdtype_id(rs.getString("pdtype_id"));
				pdcVO.setStart_price(rs.getInt("start_price"));
				pdcVO.setLive_id(rs.getInt("live_id"));
				list.add(pdcVO); // Store the row in the list
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
}
