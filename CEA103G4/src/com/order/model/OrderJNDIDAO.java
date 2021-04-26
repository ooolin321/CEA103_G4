package com.order.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jdbc.util.CompositeQuery.jdbcUtil_CompositeQuery_Order;

public class OrderJNDIDAO implements OrderDAO_interface{

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
			"INSERT INTO `ORDER` (`ORDER_STATE`,`ORDER_SHIPPING`,`ORDER_PRICE`,`PAY_METHOD`,`PAY_DEADLINE`,`REC_NAME`,`ZIPCODE`,`CITY`,`TOWN`,`REC_ADDR`,`REC_PHONE`,`REC_CELLPHONE`,`LOGISTICS`,`LOGISTICSSTATE`,`DISCOUNT`,`USER_ID`,`SELLER_ID`,`SRATING`,`SRATING_CONTENT`,`POINT`) VALUES (?, ?, ?, ?, DATE_ADD(CURRENT_TIMESTAMP() , INTERVAL 3 HOUR), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
			"SELECT `ORDER_NO`,`ORDER_DATE`,`ORDER_STATE`,`ORDER_SHIPPING`,`ORDER_PRICE`,`PAY_METHOD`,`PAY_DEADLINE`,`REC_NAME`,`ZIPCODE`,`CITY`,`TOWN`,`REC_ADDR`,`REC_PHONE`,`REC_CELLPHONE`,`LOGISTICS`,`LOGISTICSSTATE`,`DISCOUNT`,`USER_ID`,`SELLER_ID`,`SRATING`,`SRATING_CONTENT`,`POINT` FROM `ORDER` ORDER BY `ORDER_NO`";
	private static final String GET_ONE_STMT = 
			"SELECT `ORDER_NO`,`ORDER_DATE`,`ORDER_STATE`,`ORDER_SHIPPING`,`ORDER_PRICE`,`PAY_METHOD`,`PAY_DEADLINE`,`REC_NAME`,`ZIPCODE`,`CITY`,`TOWN`,`REC_ADDR`,`REC_PHONE`,`REC_CELLPHONE`,`LOGISTICS`,`LOGISTICSSTATE`,`DISCOUNT`,`USER_ID`,`SELLER_ID`,`SRATING`,`SRATING_CONTENT`,`POINT` FROM `ORDER` WHERE `ORDER_NO` = ?";
	private static final String DELETE = 
			"DELETE FROM `ORDER` WHERE `ORDER_NO` = ?";
	private static final String UPDATE = 
			"UPDATE `ORDER` SET `ORDER_DATE`=?, `ORDER_STATE`=?, `ORDER_SHIPPING`=?,`ORDER_PRICE`=?,`PAY_METHOD`=?,`PAY_DEADLINE`=?,`REC_NAME`=?,`ZIPCODE`=?,`CITY`=?,`TOWN`=?,`REC_ADDR`=?,`REC_PHONE`=?,`REC_CELLPHONE`=?,`LOGISTICS`=?,`LOGISTICSSTATE`=?,`DISCOUNT`=?,`USER_ID`=?,`SELLER_ID`=?,`SRATING`=?,`SRATING_CONTENT`=?,`POINT`=? WHERE `ORDER_NO` = ?";
	
	
	
	@Override
	public void insert(OrderVO orderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

//			pstmt.setDate(1, orderVO.getOrder_date());
			pstmt.setInt(1, orderVO.getOrder_state());
			pstmt.setInt(2, orderVO.getOrder_shipping());			
			pstmt.setInt(3, orderVO.getOrder_price());
			pstmt.setInt(4, orderVO.getPay_method());
//			pstmt.setTimestamp(5, orderVO.getPay_deadline());
			pstmt.setString(5, orderVO.getRec_name());
			pstmt.setString(6, orderVO.getZipcode());
			pstmt.setString(7, orderVO.getCity());
			pstmt.setString(8, orderVO.getTown());
			pstmt.setString(9, orderVO.getRec_addr());
			pstmt.setString(10, orderVO.getRec_phone());
			pstmt.setString(11, orderVO.getRec_cellphone());
			pstmt.setInt(12, orderVO.getLogistics());
			pstmt.setInt(13, orderVO.getLogisticsstate());
			pstmt.setInt(14, orderVO.getDiscount());
			pstmt.setString(15, orderVO.getUser_id());
			pstmt.setString(16, orderVO.getSeller_id());
			pstmt.setInt(17, orderVO.getSrating());
			pstmt.setString(18, orderVO.getSrating_content());
			pstmt.setInt(19, orderVO.getPoint());
		
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
	public void update(OrderVO orderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setTimestamp(1, orderVO.getOrder_date());
			pstmt.setInt(2, orderVO.getOrder_state());
			pstmt.setInt(3, orderVO.getOrder_shipping());			
			pstmt.setInt(4, orderVO.getOrder_price());
			pstmt.setInt(5, orderVO.getPay_method());
			pstmt.setTimestamp(6, orderVO.getPay_deadline());
			pstmt.setString(7, orderVO.getRec_name());
			pstmt.setString(8, orderVO.getZipcode());
			pstmt.setString(9, orderVO.getCity());
			pstmt.setString(10, orderVO.getTown());
			pstmt.setString(11, orderVO.getRec_addr());
			pstmt.setString(12, orderVO.getRec_phone());
			pstmt.setString(13, orderVO.getRec_cellphone());
			pstmt.setInt(14, orderVO.getLogistics());
			pstmt.setInt(15, orderVO.getLogisticsstate());
			pstmt.setInt(16, orderVO.getDiscount());
			pstmt.setString(17, orderVO.getUser_id());
			pstmt.setString(18, orderVO.getSeller_id());
			pstmt.setInt(19, orderVO.getSrating());
			pstmt.setString(20, orderVO.getSrating_content());
			pstmt.setInt(21, orderVO.getPoint());
			pstmt.setInt(22, orderVO.getOrder_no());
			

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
	public void delete(Integer order_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, order_no);

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
	public OrderVO findByPrimaryKey(Integer order_no) {

		OrderVO orderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, order_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				//
				orderVO = new OrderVO();
				orderVO.setOrder_no(rs.getInt("order_no"));
				orderVO.setOrder_date(rs.getTimestamp("order_date"));
				orderVO.setOrder_state(rs.getInt("order_state"));
				orderVO.setOrder_shipping(rs.getInt("order_shipping"));
				orderVO.setOrder_price(rs.getInt("order_price"));
				orderVO.setPay_method(rs.getInt("pay_method"));
				orderVO.setPay_deadline(rs.getTimestamp("pay_deadline"));
				orderVO.setRec_name(rs.getString("rec_name"));
				orderVO.setZipcode(rs.getString("zipcode"));
				orderVO.setCity(rs.getString("city"));
				orderVO.setTown(rs.getString("town"));
				orderVO.setRec_addr(rs.getString("rec_addr"));
				orderVO.setRec_phone(rs.getString("rec_phone"));
				orderVO.setRec_cellphone(rs.getString("rec_cellphone"));
				orderVO.setLogistics(rs.getInt("logistics"));
				orderVO.setLogisticsstate(rs.getInt("logisticsstate"));
				orderVO.setDiscount(rs.getInt("discount"));
				orderVO.setUser_id(rs.getString("user_id"));
				orderVO.setSeller_id(rs.getString("seller_id"));
				orderVO.setSrating(rs.getInt("srating"));
				orderVO.setSrating_content(rs.getString("srating_content"));
				orderVO.setPoint(rs.getInt("point"));
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
		return orderVO;
	}

	@Override
	public List<OrderVO> getAll() {
		List<OrderVO> list = new ArrayList<OrderVO>();
		OrderVO orderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				orderVO = new OrderVO();
				orderVO.setOrder_no(rs.getInt("order_no"));
				orderVO.setOrder_date(rs.getTimestamp("order_date"));
				orderVO.setOrder_state(rs.getInt("order_state"));
				orderVO.setOrder_shipping(rs.getInt("order_shipping"));
				orderVO.setOrder_price(rs.getInt("order_price"));
				orderVO.setPay_method(rs.getInt("pay_method"));
				orderVO.setPay_deadline(rs.getTimestamp("pay_deadline"));
				orderVO.setRec_name(rs.getString("rec_name"));
				orderVO.setZipcode(rs.getString("zipcode"));
				orderVO.setCity(rs.getString("city"));
				orderVO.setTown(rs.getString("town"));
				orderVO.setRec_addr(rs.getString("rec_addr"));
				orderVO.setRec_phone(rs.getString("rec_phone"));
				orderVO.setRec_cellphone(rs.getString("rec_cellphone"));
				orderVO.setLogistics(rs.getInt("logistics"));
				orderVO.setLogisticsstate(rs.getInt("logisticsstate"));
				orderVO.setDiscount(rs.getInt("discount"));
				orderVO.setUser_id(rs.getString("user_id"));
				orderVO.setSeller_id(rs.getString("seller_id"));
				orderVO.setSrating(rs.getInt("srating"));
				orderVO.setSrating_content(rs.getString("srating_content"));
				orderVO.setPoint(rs.getInt("point"));
				list.add(orderVO); // Store the row in the list
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
	public List<OrderVO> getAll(Map<String, String[]> map) {
		List<OrderVO> list = new ArrayList<OrderVO>();
		OrderVO orderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			String finalSQL = " select * from `order` "
					+ jdbcUtil_CompositeQuery_Order.get_WhereCondition(map)
					+ "order by `order_no`";
			pstmt = con.prepareStatement(finalSQL);
			System.out.println("finalSQL(by DAO) = "+finalSQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				orderVO = new OrderVO();
				orderVO.setOrder_no(rs.getInt("order_no"));
				orderVO.setOrder_date(rs.getTimestamp("order_date"));
				orderVO.setOrder_state(rs.getInt("order_state"));
				orderVO.setOrder_shipping(rs.getInt("order_shipping"));
				orderVO.setOrder_price(rs.getInt("order_price"));
				orderVO.setPay_method(rs.getInt("pay_method"));
				orderVO.setPay_deadline(rs.getTimestamp("pay_deadline"));
				orderVO.setRec_name(rs.getString("rec_name"));
				orderVO.setZipcode(rs.getString("zipcode"));
				orderVO.setCity(rs.getString("city"));
				orderVO.setTown(rs.getString("town"));
				orderVO.setRec_addr(rs.getString("rec_addr"));
				orderVO.setRec_phone(rs.getString("rec_phone"));
				orderVO.setRec_cellphone(rs.getString("rec_cellphone"));
				orderVO.setLogistics(rs.getInt("logistics"));
				orderVO.setLogisticsstate(rs.getInt("logisticsstate"));
				orderVO.setDiscount(rs.getInt("discount"));
				orderVO.setUser_id(rs.getString("user_id"));
				orderVO.setSeller_id(rs.getString("seller_id"));
				orderVO.setSrating(rs.getInt("srating"));
				orderVO.setSrating_content(rs.getString("srating_content"));
				orderVO.setPoint(rs.getInt("point"));
				list.add(orderVO); // Store the row in the list
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
