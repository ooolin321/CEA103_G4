package com.detail.model;

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

public class DetailDAO implements DetailDAO_Interface {

	private static DataSource ds =null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bookshop");
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	
	private static final String INS_ODT=
			"INSERT INTO ORDER_DETAILS VALUES(?,?,?,?,?,?)";
	private static final String FIND_ID=
			"SELECT * FROM ORDER_DETAILS WHERE ORDER_ID=?";
	
	
	
	@Override
	public void doCreate(DetailVO dtVO , Connection con) {
		PreparedStatement pstmt =null;
		try {
			pstmt = con.prepareStatement(INS_ODT);
			
			pstmt.setString(1, dtVO.getOrder_id());
			pstmt.setString(2, dtVO.getBook_id());
			pstmt.setString(3, dtVO.getItems_name());
			pstmt.setInt(4, dtVO.getComm_qty());
			pstmt.setDouble(5, dtVO.getComm_price());
			pstmt.setInt(6, dtVO.getComm_bonus());
			
			pstmt.executeUpdate();
			
		} catch (SQLException sqle) {
			if(con != null) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			}
			throw new RuntimeException("▲Error： [doCreateDT]" + sqle.getMessage());
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public List<DetailVO> findbyid(String order_id) {
		List<DetailVO> list = new ArrayList<DetailVO>();
		DetailVO dtVO = null;
		Connection con =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_ID);
			con.setAutoCommit(false);
			
			pstmt.setString(1,order_id);
			rs= pstmt.executeQuery();
			while(rs.next()) {
				dtVO = new DetailVO();
				dtVO.setOrder_id(rs.getString("order_id"));
				dtVO.setBook_id(rs.getString("book_id"));
				dtVO.setItems_name(rs.getString("items_name"));
				dtVO.setComm_qty(rs.getInt("comm_qty"));
				dtVO.setComm_price(rs.getDouble("comm_price"));
				dtVO.setComm_bonus(rs.getInt("comm_bonus"));
				list.add(dtVO);
			}
			con.commit();
		
		} catch (SQLException sqle) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("▲Error： [FindbyDT]" + sqle.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
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
