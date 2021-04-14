package com.seller_follow.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;



public class Seller_FollowJNDIDAO implements Seller_FollowDAO_interface {


	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/admin");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
    //關注賣家,買家可以關注賣家(新增)、可以查詢自己關注的清單(查詢)、可以取消關注的賣家(刪除)
	private static final String INSERT_STMT = 
		"INSERT INTO SELLER_FOLLOW (user_id, seller_id) VALUES (?, ?)";
	//查詢關注賣家table的所有資料
	private static final String GET_ALL_STMT = 
		"SELECT tracer_no, user_id, seller_id FROM SELLER_FOLLOW";
	//查詢某一個會員的所有追蹤賣家清單
	private static final String GET_ONE_STMT = 
		"SELECT tracer_no, user_id, seller_id FROM SELLER_FOLLOW where user_id = ?";
	//買家可以取消關注的賣家
	private static final String DELETE = 
		"DELETE FROM SELLER_FOLLOW where tracer_no = ?";

	@Override
	public void insert(Seller_FollowVO seller_followVO){

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, seller_followVO.getUser_id());
			pstmt.setString(2, seller_followVO.getSeller_id());
			
			pstmt.executeUpdate();
			

			// Handle any SQL errors
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
	public void delete(Integer tracer_no){

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, tracer_no);

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public List<Seller_FollowVO> getOneUser(String user_id){
		
		List<Seller_FollowVO> list = new ArrayList<Seller_FollowVO>();
		Seller_FollowVO seller_followVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ONE_STMT);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					
					seller_followVO = new Seller_FollowVO();
					seller_followVO.setTracer_no(rs.getInt("product_no"));
					seller_followVO.setUser_id(rs.getString("product_name"));
					seller_followVO.setSeller_id(rs.getString("product_info"));
					list.add(seller_followVO); // Store the row in the list
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

	@Override
	public List<Seller_FollowVO> getAll(){
		List<Seller_FollowVO> list = new ArrayList<Seller_FollowVO>();
		Seller_FollowVO seller_followVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {		
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				seller_followVO = new Seller_FollowVO();
				seller_followVO.setTracer_no(rs.getInt("product_no"));
				seller_followVO.setUser_id(rs.getString("product_name"));
				seller_followVO.setSeller_id(rs.getString("product_info"));
				list.add(seller_followVO); // Store the row in the list
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
