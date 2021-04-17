package com.live_order.model;

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

public class Live_orderJNDIDAO implements Live_orderDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/admin");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = "INSERT INTO LIVE_ORDER (ORDER_STATE,ORDER_SHIPPING,ORDER_PRICE,PAY_METHOD,REC_NAME,REC_ADDR,REC_PHONE,REC_CELLPHONE,LOGISTICS,LOGISTICS_STATE,DISCOUNT,LIVE_NO,USER_ID,SELLER_ID,SRATING,SRATING_CONTENT,POINT,CITY,TOWN,ZIPCODE,PAY_DEADLINE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,? ,? ,? ,?,?,?,DATE_ADD(CURRENT_TIMESTAMP() , INTERVAL 3 HOUR))";
	private static final String GET_ALL_STMT = "SELECT * FROM LIVE_ORDER order by LIVE_ORDER_NO";
	private static final String GET_ONE_STMT = "SELECT * FROM LIVE_ORDER where LIVE_ORDER_NO = ?";
	private static final String DELETE = "DELETE FROM LIVE_ORDER where LIVE_ORDER_NO = ?";
	private static final String UPDATE = "UPDATE LIVE_ORDER SET ORDER_DATE=?,ORDER_STATE=?,ORDER_SHIPPING=?,ORDER_PRICE=?,PAY_METHOD=?,PAY_DEADLINE=?,REC_NAME=?,REC_ADDR=?,REC_PHONE=?,REC_CELLPHONE=?,LOGISTICS=?,LOGISTICS_STATE=?,DISCOUNT=?,LIVE_NO=?,USER_ID=?,SELLER_ID=?,SRATING=?,SRATING_CONTENT=?,POINT=? ,CITY=?,TOWN=?,ZIPCODE=? WHERE LIVE_ORDER_NO = ?";
	
	
	

	@Override
	public void insert(Live_orderVO live_orderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
//			pstmt.setTimestamp(1, live_orderVO.getOrder_date());
			pstmt.setInt(1, live_orderVO.getOrder_state());
			pstmt.setInt(2, live_orderVO.getOrder_shipping());
			pstmt.setInt(3, live_orderVO.getOrder_price());
			pstmt.setInt(4, live_orderVO.getPay_method());
//			pstmt.setTimestamp(6, live_orderVO.getPay_deadline());
			pstmt.setString(5, live_orderVO.getRec_name());
			pstmt.setString(6, live_orderVO.getRec_addr());
			pstmt.setString(7, live_orderVO.getRec_phone());
			pstmt.setString(8, live_orderVO.getRec_cellphone());
			pstmt.setInt(9, live_orderVO.getLogistics());
			pstmt.setInt(10, live_orderVO.getLogistics_state());
			pstmt.setInt(11, live_orderVO.getDiscount());
			pstmt.setInt(12, live_orderVO.getLive_no());
			pstmt.setString(13, live_orderVO.getUser_id());
			pstmt.setString(14, live_orderVO.getSeller_id());
			pstmt.setInt(15, live_orderVO.getSrating());
			pstmt.setString(16, live_orderVO.getSrating_content());
			pstmt.setInt(17, live_orderVO.getPoint());
			pstmt.setString(18, live_orderVO.getCity());
			pstmt.setString(19, live_orderVO.getTown());
			pstmt.setInt(20, live_orderVO.getZipcode());
			pstmt.executeUpdate();

		
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void update(Live_orderVO live_orderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setTimestamp(1, live_orderVO.getOrder_date());
			pstmt.setInt(2, live_orderVO.getOrder_state());
			pstmt.setInt(3, live_orderVO.getOrder_shipping());
			pstmt.setInt(4, live_orderVO.getOrder_price());
			pstmt.setInt(5, live_orderVO.getPay_method());
			pstmt.setTimestamp(6, live_orderVO.getPay_deadline());
			pstmt.setString(7, live_orderVO.getRec_name());
			pstmt.setString(8, live_orderVO.getRec_addr());
			pstmt.setString(9, live_orderVO.getRec_phone());
			pstmt.setString(10, live_orderVO.getRec_cellphone());
			pstmt.setInt(11, live_orderVO.getLogistics());
			pstmt.setInt(12, live_orderVO.getLogistics_state());
			pstmt.setInt(13, live_orderVO.getDiscount());
			pstmt.setInt(14, live_orderVO.getLive_no());
			pstmt.setString(15, live_orderVO.getUser_id());
			pstmt.setString(16, live_orderVO.getSeller_id());
			pstmt.setInt(17, live_orderVO.getSrating());
			pstmt.setString(18, live_orderVO.getSrating_content());
			pstmt.setInt(19, live_orderVO.getPoint());
			pstmt.setString(20, live_orderVO.getCity());
			pstmt.setString(21, live_orderVO.getTown());
			pstmt.setInt(22, live_orderVO.getZipcode());
			pstmt.setInt(23, live_orderVO.getLive_order_no());
			
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
	public void delete(Integer live_order_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, live_order_no);

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
	public Live_orderVO findByPrimaryKey(Integer live_order_no) {

		Live_orderVO live_orderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, live_order_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				live_orderVO = new Live_orderVO();
				live_orderVO.setLive_order_no(rs.getInt("live_order_no"));
				live_orderVO.setOrder_date(rs.getTimestamp("order_date"));
				live_orderVO.setOrder_state(rs.getInt("order_state"));
				live_orderVO.setOrder_shipping(rs.getInt("order_shipping"));
				live_orderVO.setOrder_price(rs.getInt("order_price"));
				live_orderVO.setPay_method(rs.getInt("pay_method"));
				live_orderVO.setPay_deadline(rs.getTimestamp("pay_deadline"));
				live_orderVO.setRec_name(rs.getString("rec_name"));
				live_orderVO.setRec_addr(rs.getString("rec_addr"));
				live_orderVO.setRec_phone(rs.getString("rec_phone"));
				live_orderVO.setRec_cellphone(rs.getString("rec_cellphone"));
				live_orderVO.setLogistics(rs.getInt("logistics"));
				live_orderVO.setLogistics_state(rs.getInt("logistics_state"));
				live_orderVO.setDiscount(rs.getInt("discount"));
				live_orderVO.setLive_no(rs.getInt("live_no"));
				live_orderVO.setUser_id(rs.getString("user_id"));
				live_orderVO.setSeller_id(rs.getString("seller_id"));
				live_orderVO.setSrating(rs.getInt("srating"));
				live_orderVO.setSrating_content(rs.getString("srating_content"));
				live_orderVO.setPoint(rs.getInt("point"));
				live_orderVO.setCity(rs.getString("City"));
				live_orderVO.setTown(rs.getString("Town"));
				live_orderVO.setZipcode(rs.getInt("zipcode"));
				
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
		return live_orderVO;
	}

	@Override
	public List<Live_orderVO> getAll() {
		List<Live_orderVO> list = new ArrayList<Live_orderVO>();
		Live_orderVO live_orderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				live_orderVO = new Live_orderVO();
				live_orderVO.setLive_order_no(rs.getInt("live_order_no"));
				live_orderVO.setOrder_date(rs.getTimestamp("order_date"));
				live_orderVO.setOrder_state(rs.getInt("order_state"));
				live_orderVO.setOrder_shipping(rs.getInt("order_shipping"));
				live_orderVO.setOrder_price(rs.getInt("order_price"));
				live_orderVO.setPay_method(rs.getInt("pay_method"));
				live_orderVO.setPay_deadline(rs.getTimestamp("pay_deadline"));
				live_orderVO.setRec_name(rs.getString("rec_name"));
				live_orderVO.setRec_addr(rs.getString("rec_addr"));
				live_orderVO.setRec_phone(rs.getString("rec_phone"));
				live_orderVO.setRec_cellphone(rs.getString("rec_cellphone"));
				live_orderVO.setLogistics(rs.getInt("logistics"));
				live_orderVO.setLogistics_state(rs.getInt("logistics_state"));
				live_orderVO.setDiscount(rs.getInt("discount"));
				live_orderVO.setLive_no(rs.getInt("live_no"));
				live_orderVO.setUser_id(rs.getString("user_id"));
				live_orderVO.setSeller_id(rs.getString("seller_id"));
				live_orderVO.setSrating(rs.getInt("srating"));
				live_orderVO.setSrating_content(rs.getString("srating_content"));
				live_orderVO.setPoint(rs.getInt("point"));
				live_orderVO.setCity(rs.getString("City"));
				live_orderVO.setTown(rs.getString("Town"));
				live_orderVO.setZipcode(rs.getInt("zipcode"));
				list.add(live_orderVO); // Store the row in the list
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
		return list;
	}

}
