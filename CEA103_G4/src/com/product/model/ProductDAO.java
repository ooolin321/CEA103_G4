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

public class ProductDAO implements Product_Interface {

	
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
		"INSERT INTO PRODUCT (product_name,product_info,product_price,product_quantity,product_remaining,product_state,product_photo,user_id,pdtype_id,start_price,live_id) "
		+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT product_id,product_name,product_info,product_price,product_quantity,product_remaining,product_state,product_photo,user_id,pdtype_id,start_price,live_id"
		+ " FROM PRODUCT order by product_id";
	private static final String GET_ONE_STMT = 
		"SELECT product_id,product_name,product_info,product_price,product_quantity,product_remaining,product_state,product_photo,user_id,pdtype_id,start_price,live_id"
		+ " FROM PRODUCT where product_id = ?";
	private static final String DELETE = 
		"DELETE FROM PRODUCT where product_id = ?";
	private static final String UPDATE = 
		"UPDATE PRODUCT set product_name=?, product_info=?, product_price=?, product_quantity=?, product_remaining=?, product_state=?, product_photo=?, user_id=?, pdtype_id=?, start_price=?, live_id=? where product_id = ?";

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
			pstmt.setString(6, productVO.getProduct_state());
			pstmt.setBlob(7, new FileInputStream(new File("WebContent/product/images/02.png")));
			pstmt.setString(8, productVO.getUser_id());
			pstmt.setString(9, productVO.getPdtype_id());
			pstmt.setInt(10, productVO.getStart_price());
			pstmt.setInt(11, productVO.getLive_id());

			pstmt.executeUpdate();
			

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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


			pstmt.setInt(1, productVO.getProduct_id());
			pstmt.setString(2, productVO.getProduct_name());
			pstmt.setString(3, productVO.getProduct_info());
			pstmt.setInt(4, productVO.getProduct_price());
			pstmt.setInt(5, productVO.getProduct_quantity());
			pstmt.setInt(6, productVO.getProduct_remaining());
			pstmt.setString(7, productVO.getProduct_state());
			pstmt.setBlob(8, new FileInputStream(new File("WebContent/product/images/02.png")));
			pstmt.setString(9, productVO.getUser_id());
			pstmt.setString(10, productVO.getPdtype_id());
			pstmt.setInt(11, productVO.getStart_price());
			pstmt.setInt(12, productVO.getLive_id());

			pstmt.executeUpdate();
			
			
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	// �ϥ�InputStream��Ƭy�覡
//	public static InputStream getPictureStream(String path) throws IOException {
//		FileInputStream fis = new FileInputStream(path);
//		return fis;
//	}

	@Override
	public void delete(Integer product_id){

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			


			pstmt.setInt(1, product_id);

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
	public ProductVO findByPrimaryKey(Integer product_id){

		ProductVO productVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			

			pstmt.setInt(1, product_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
		
				productVO = new ProductVO();
				productVO.setProduct_id(rs.getInt("product_id"));
				productVO.setProduct_name(rs.getString("product_name"));
				productVO.setProduct_info(rs.getString("product_info"));
				productVO.setProduct_price(rs.getInt("product_price"));
				productVO.setProduct_quantity(rs.getInt("product_quantity"));
				productVO.setProduct_remaining(rs.getInt("product_remaining"));
				productVO.setProduct_state(rs.getString("product_state"));
				productVO.setProduct_photo(rs.getBlob("product_photo"));
				productVO.setUser_id(rs.getString("user_id"));
				productVO.setPdtype_id(rs.getString("pdtype_id"));
				productVO.setStart_price(rs.getInt("start_price"));
				productVO.setLive_id(rs.getInt("live_id"));
				
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
				productVO.setProduct_id(rs.getInt("product_id"));
				productVO.setProduct_name(rs.getString("product_name"));
				productVO.setProduct_info(rs.getString("product_info"));
				productVO.setProduct_price(rs.getInt("product_price"));
				productVO.setProduct_quantity(rs.getInt("product_quantity"));
				productVO.setProduct_remaining(rs.getInt("product_remaining"));
				productVO.setProduct_state(rs.getString("product_state"));
				Blob blob = rs.getBlob("product_photo");
				productVO.setProduct_photo((readPicture(blob)));
				productVO.setUser_id(rs.getString("user_id"));
				productVO.setPdtype_id(rs.getString("pdtype_id"));
				productVO.setStart_price(rs.getInt("start_price"));
				productVO.setLive_id(rs.getInt("live_id"));
				list.add(productVO); // Store the row in the list
			}
			
			

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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


	public static  Blob readPicture(Blob blob) throws IOException, SQLException {
		InputStream is = blob.getBinaryStream();
		FileOutputStream fos = new FileOutputStream("product_photo");
		int i;
		while ((i = is.read()) != -1) {
			fos.write(i);
		}
		fos.flush();
		fos.close();
		return blob;
	}
	
}

