package com.product.model;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ProductJNDIDAO implements ProductDAO_interface {

	
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
			"INSERT INTO PRODUCT (product_name,product_info,product_price,product_quantity,product_remaining,product_state,product_photo,user_id,pdtype_no,start_price,live_no) "
			+ "VALUES (?, ?, ?, DEFAULT, ?, DEFAULT, ?, ?, ?, DEFAULT, DEFAULT)";
	private static final String GET_ALL_STMT = 
		"SELECT product_no,product_name,product_info,product_price,product_quantity,product_remaining,product_state,product_photo,user_id,pdtype_no,start_price,live_no"
		+ " FROM PRODUCT order by product_no";
	private static final String GET_ONE_STMT = 
		"SELECT product_no,product_name,product_info,product_price,product_quantity,product_remaining,product_state,product_photo,user_id,pdtype_no,start_price,live_no"
		+ " FROM PRODUCT where product_no = ?";
	private static final String DELETE = 
		"DELETE FROM PRODUCT where product_no = ?";
	private static final String UPDATE = 
		"UPDATE PRODUCT set product_name=?, product_info=?, product_price=?, product_quantity=?, product_remaining=?, product_state=?, product_photo=?, user_id=?, pdtype_no=?, start_price=?, live_no=? where product_no = ?";

	@Override
	public void insert(ProductVO productVO){

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			


			pstmt.setString(1, productVO.getProduct_name());
			pstmt.setString(2, productVO.getProduct_info());
			pstmt.setInt(3, productVO.getProduct_price());
			pstmt.setInt(4, productVO.getProduct_quantity());
			pstmt.setInt(5, productVO.getProduct_remaining());
			pstmt.setInt(6, productVO.getProduct_state());
			pstmt.setBytes(7,  productVO.getProduct_photo());
			pstmt.setString(8, productVO.getUser_id());
			pstmt.setInt(9, productVO.getPdtype_no());
			pstmt.setInt(10, productVO.getStart_price());
			pstmt.setInt(11, productVO.getLive_no());

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
	public void update(ProductVO productVO){

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);


			pstmt.setInt(1, productVO.getProduct_no());
			pstmt.setString(2, productVO.getProduct_name());
			pstmt.setString(3, productVO.getProduct_info());
			pstmt.setInt(4, productVO.getProduct_price());
			pstmt.setInt(5, productVO.getProduct_quantity());
			pstmt.setInt(6, productVO.getProduct_remaining());
			pstmt.setInt(7, productVO.getProduct_state());
			pstmt.setBytes(8,  productVO.getProduct_photo());
			pstmt.setString(9, productVO.getUser_id());
			pstmt.setInt(10, productVO.getPdtype_no());
			pstmt.setInt(11, productVO.getStart_price());
			pstmt.setInt(12, productVO.getLive_no());

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
	public void delete(Integer product_no){

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			


			pstmt.setInt(1, product_no);

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
	public ProductVO findByPrimaryKey(Integer product_no){

		ProductVO productVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			

			pstmt.setInt(1, product_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
		
				productVO = new ProductVO();
				productVO.setProduct_no(rs.getInt("product_no"));
				productVO.setProduct_name(rs.getString("product_name"));
				productVO.setProduct_info(rs.getString("product_info"));
				productVO.setProduct_price(rs.getInt("product_price"));
				productVO.setProduct_quantity(rs.getInt("product_quantity"));
				productVO.setProduct_remaining(rs.getInt("product_remaining"));
				productVO.setProduct_state(rs.getInt("product_state"));
				productVO.setProduct_photo(rs.getBytes("product_photo"));
				productVO.setUser_id(rs.getString("user_id"));
				productVO.setPdtype_no(rs.getInt("pdtype_no"));
				productVO.setStart_price(rs.getInt("start_price"));
				productVO.setLive_no(rs.getInt("live_no"));
				
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
		return productVO;
	}

	@Override
	public List<ProductVO> getAll(){
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				productVO = new ProductVO();
				productVO.setProduct_no(rs.getInt("product_no"));
				productVO.setProduct_name(rs.getString("product_name"));
				productVO.setProduct_info(rs.getString("product_info"));
				productVO.setProduct_price(rs.getInt("product_price"));
				productVO.setProduct_quantity(rs.getInt("product_quantity"));
				productVO.setProduct_remaining(rs.getInt("product_remaining"));
				productVO.setProduct_state(rs.getInt("product_state"));
				productVO.setProduct_photo(rs.getBytes("product_photo"));
				productVO.setUser_id(rs.getString("user_id"));
				productVO.setPdtype_no(rs.getInt("pdtype_no"));
				productVO.setStart_price(rs.getInt("start_price"));
				productVO.setLive_no(rs.getInt("live_no"));
				list.add(productVO); // Store the row in the list
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

