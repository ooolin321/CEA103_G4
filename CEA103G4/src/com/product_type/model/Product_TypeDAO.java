package com.product_type.model;

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

import org.json.JSONArray;
import org.json.JSONObject;

import com.product.model.ProductVO;
import com.seller_follow.model.Seller_FollowVO;

public class Product_TypeDAO  implements Product_TypeDAO_interface  {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/admin");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	//新增商品類別
	private static final String INSERT_STMT = 
		"INSERT INTO PRODUCT_TYPE (pdtype_name) VALUES (?)";
	//查詢所有商品類別
	private static final String GET_ALL_STMT = 
		"SELECT * FROM PRODUCT_TYPE";
	//查詢某一個商品類別資料
	private static final String GET_ONE_STMT = 
		"SELECT * FROM PRODUCT_TYPE where pdtype_no = ?";
	//刪除商品類別
	private static final String DELETE = 
		"DELETE FROM PRODUCT_TYPE where pdtype_no = ?";
	//刪除商品類別
	private static final String UPDATE = 
			"UPDATE PRODUCT_TYPE set pdtype_name=? where pdtype_no = ?";

	

	@Override
	public void insert(Product_TypeVO product_typeVO){

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, product_typeVO.getPdtype_name());
			
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
	public void update(Product_TypeVO product_typeVO){

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, product_typeVO.getPdtype_name());
			pstmt.setInt(2, product_typeVO.getPdtype_no());
			

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
	public void delete(Integer pdtype_no){

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, pdtype_no);

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
	public Product_TypeVO findByPrimaryKey(Integer pdtype_no){

		Product_TypeVO product_typeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, pdtype_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
		
				product_typeVO = new Product_TypeVO();
				product_typeVO.setPdtype_no(rs.getInt("pdtype_no"));
				product_typeVO.setPdtype_name(rs.getString("pdtype_name"));
				
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
		return product_typeVO;
	}

	@Override
	public List<Product_TypeVO> getAll(){
		List<Product_TypeVO> list = new ArrayList<Product_TypeVO>();
		Product_TypeVO product_typeVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {		
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				product_typeVO = new Product_TypeVO();
				product_typeVO.setPdtype_no(rs.getInt("pdtype_no"));
				product_typeVO.setPdtype_name(rs.getString("pdtype_name"));
				list.add(product_typeVO); // Store the row in the list
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
