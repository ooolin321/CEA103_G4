package com.product.model;
import java.util.*;
import java.io.ByteArrayOutputStream;
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

import com.product.controller.CompositeQuery_Product;


public class ProductDAO implements ProductDAO_interface {

	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/admin");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	//新增商品 賣家上架功能
	private static final String INSERT_STMT = "INSERT INTO PRODUCT (product_name,product_info,product_price,product_remaining,product_state,product_photo,user_id,pdtype_no) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	//查詢所有商品(後台/賣家查詢使用)
	private static final String GET_ALL_STMT = "SELECT product_no,product_name,product_info,product_price,product_quantity,product_remaining,product_state,product_photo,user_id,pdtype_no,start_price,live_no FROM PRODUCT order by product_no";	
	private static final String GET_ONE_STMT = "SELECT product_no,product_name,product_info,product_price,product_quantity,product_remaining,product_state,product_photo,user_id,pdtype_no,start_price,live_no FROM PRODUCT where product_no = ?";
	//刪除商品 賣家使用
	private static final String DELETE = "DELETE FROM PRODUCT where product_no = ?";
	//修改商品 賣家使用
	private static final String UPDATE = "UPDATE PRODUCT set product_name=?, product_info=?, product_price=?, product_quantity=?, product_remaining=?, product_state=?, product_photo=?, user_id=?, pdtype_no=? where product_no = ?";
	private static final String GET_ALLJSON = "SELECT product_no,product_name,product_info,product_price,product_quantity,product_remaining,product_state,user_id,pdtype_no,start_price,live_no FROM PRODUCT order by product_no";
	
	/*--------------shop.jsp商品區------------*/
	//查詢所有商品狀態為直售的商品 (隨機排序)
	private static final String GET_ALL_SHOP = "SELECT product_no, product_name, product_info,product_price, product_quantity,product_remaining,product_state,user_id,pdtype_no FROM PRODUCT where product_state = 1 AND product_photo IS NOT NULL order by rand()";	
	//查詢價格區間的商品
	private static final String GET_MoneyRangeShop = "select product_no, product_name, product_info,product_price, product_quantity,product_remaining,product_state,user_id,pdtype_no from PRODUCT where product_photo IS NOT NULL AND product_state = 1 AND product_price between ? and ?";
	
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
			pstmt.setInt(4, productVO.getProduct_remaining());
			pstmt.setInt(5, productVO.getProduct_state());
			pstmt.setBytes(6, productVO.getProduct_photo());
			pstmt.setString(7, productVO.getUser_id());
			pstmt.setInt(8, productVO.getPdtype_no());

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

	
			pstmt.setString(1, productVO.getProduct_name());
			pstmt.setString(2, productVO.getProduct_info());
			pstmt.setInt(3, productVO.getProduct_price());
			pstmt.setInt(4, productVO.getProduct_quantity());
			pstmt.setInt(5, productVO.getProduct_remaining());
			pstmt.setInt(6, productVO.getProduct_state());
			pstmt.setBytes(7,productVO.getProduct_photo());
			pstmt.setString(8,productVO.getUser_id());
			pstmt.setInt(9, productVO.getPdtype_no());
			pstmt.setInt(10, productVO.getProduct_no());

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
	public List<ProductVO> getAllWithoutPhoto(){
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
		
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALLJSON);
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
				productVO.setUser_id(rs.getString("user_id"));
				productVO.setPdtype_no(rs.getInt("pdtype_no"));
				productVO.setStart_price(rs.getInt("start_price"));
				productVO.setLive_no(rs.getInt("live_no"));
				list.add(productVO); // Store the row in the list
			}
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
	public List<ProductVO> getAllShop(){
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {	
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_SHOP);
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
				productVO.setUser_id(rs.getString("user_id"));
				productVO.setPdtype_no(rs.getInt("pdtype_no"));
				list.add(productVO); // Store the row in the list
			}
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
	public List<ProductVO> getAllShop(Map<String, String[]> map) {
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			
			con = ds.getConnection();
			String finalSQL = "select product_no, product_name, product_info,product_price, product_quantity,product_remaining,product_state,user_id,pdtype_no from PRODUCT where product_photo IS NOT NULL AND product_state = 1 "
		          + CompositeQuery_Product.get_WhereCondition(map);

			pstmt = con.prepareStatement(finalSQL);
//			有執行到會印出
//			System.out.println("●●finalSQL(by DAO) = "+finalSQL);  
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
				productVO.setUser_id(rs.getString("user_id"));
				productVO.setPdtype_no(rs.getInt("pdtype_no"));
				list.add(productVO); // Store the row in the List
			}
	
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public List<ProductVO> getAdvSearchShop(String[] pdtypeNo, String priceType) {
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {	
			con = ds.getConnection();
			StringBuffer AdvSearchSQL = new StringBuffer("select product_no, product_name, product_info,product_price, product_quantity,product_remaining,product_state,user_id,pdtype_no from PRODUCT where product_photo IS NOT NULL AND product_state = 1 ");
			if(priceType == null) {
				priceType = "E";
			}
			switch(priceType) {
				case "A":
					AdvSearchSQL.append(" AND product_price < 301 ");
					break;
				case "B":
					AdvSearchSQL.append(" AND product_price > 300 AND product_price < 501 ");
					break;
				case "C":
					AdvSearchSQL.append(" AND product_price > 500 AND product_price < 1001 ");
					break;
				case "D":
					AdvSearchSQL.append(" AND product_price > 1000 ");
					break;
				case "E":
					AdvSearchSQL.append("");
					break;	
				default:
					break;
			}
			
			if (pdtypeNo == null || pdtypeNo.length < 1) {
				AdvSearchSQL.append("");
			}else if (pdtypeNo.length > 0) {
				AdvSearchSQL.append("AND (");
				for(int i = 0; i < pdtypeNo.length; i++) {
					if(i != pdtypeNo.length - 1)
						AdvSearchSQL.append(("pdtype_no = " + pdtypeNo[i] + " OR "));
					else
						AdvSearchSQL.append(("pdtype_no = " + pdtypeNo[i]));
				}
				AdvSearchSQL.append(")");
			}
	  
//			System.out.println(AdvSearchSQL);
			
			pstmt = con.prepareStatement(AdvSearchSQL.toString());
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
				productVO.setUser_id(rs.getString("user_id"));
				productVO.setPdtype_no(rs.getInt("pdtype_no"));
				list.add(productVO); // Store the row in the list
			}
	
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public List<ProductVO> getMoneyRangeShop(String minPrice, String maxPrice) {
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {	
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MoneyRangeShop);
			pstmt.setString(1, minPrice);
			pstmt.setString(2, maxPrice);
			
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
				productVO.setUser_id(rs.getString("user_id"));
				productVO.setPdtype_no(rs.getInt("pdtype_no"));
				list.add(productVO); // Store the row in the list
			}
	
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	


	//照片
//	public static byte[] getPictureByteArray(String path) throws IOException {
//		File file = new File(path);
//		FileInputStream fis = new FileInputStream(file);
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
//		byte[] buffer = new byte[8192];
//		int i;
//		while ((i = fis.read(buffer)) != -1) {
//			bos.write(buffer, 0, i);
//			bos.flush();
//		}
//		bos.close();
//		fis.close();
//
//		return bos.toByteArray();
//	}
	
	//獲取某商品編號的圖片
	@Override
	public Optional<ProductVO> findProductPic(Integer product_no) {
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
				productVO.setProduct_photo(rs.getBytes("product_photo"));
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
		return Optional.ofNullable(productVO);
	}
	
}

