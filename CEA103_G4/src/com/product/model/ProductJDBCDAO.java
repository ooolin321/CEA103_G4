package com.product.model;
import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class ProductJDBCDAO implements Product_Interface {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://database-1.canq3t4lp2za.ap-northeast-1.rds.amazonaws.com:3306/CEA103_G4?serverTimezone=Asia/Taipei";
	String userid = "admin";
	String passwd = "12345678";
	

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
	public void insert(ProductVO productVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, productVO.getProduct_name());
			pstmt.setString(2, productVO.getProduct_info());
			pstmt.setInt(3, productVO.getProduct_price());
			pstmt.setInt(4, productVO.getProduct_quantity());
			pstmt.setInt(5, productVO.getProduct_remaining());
			pstmt.setString(6, productVO.getProduct_state());
			InputStream Product_photo = getPictureStream("WebContent/product/images/02.png");
			pstmt.setBlob(7, Product_photo);
			pstmt.setString(8, productVO.getUser_id());
			pstmt.setString(9, productVO.getPdtype_id());
			pstmt.setInt(10, productVO.getStart_price());
			pstmt.setInt(11, productVO.getLive_id());

			pstmt.executeUpdate();
			Product_photo.close();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} catch (IOException e) {
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
	public void update(ProductVO productVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, productVO.getProduct_id());
			pstmt.setString(2, productVO.getProduct_name());
			pstmt.setString(3, productVO.getProduct_info());
			pstmt.setInt(4, productVO.getProduct_price());
			pstmt.setInt(5, productVO.getProduct_quantity());
			pstmt.setInt(6, productVO.getProduct_remaining());
			pstmt.setString(7, productVO.getProduct_state());
			InputStream Product_photo = getPictureStream("WebContent/product/images/01.png");
			pstmt.setBlob(8, Product_photo);
			pstmt.setString(9, productVO.getUser_id());
			pstmt.setString(10, productVO.getPdtype_id());
			pstmt.setInt(11, productVO.getStart_price());
			pstmt.setInt(12, productVO.getLive_id());

			pstmt.executeUpdate();
			Product_photo.close();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} catch (IOException e) {
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
	public void delete(Integer product_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, product_id);

			pstmt.executeUpdate();

			// Handle any driver errors
					} catch (ClassNotFoundException e) {
						throw new RuntimeException("Couldn't load database driver. "
								+ e.getMessage());
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
	public ProductVO findByPrimaryKey(Integer product_id) {

		ProductVO productVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
	public List<ProductVO> getAll() {
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
				productVO.setProduct_photo(rs.getBlob("product_photo"));
				productVO.setUser_id(rs.getString("user_id"));
				productVO.setPdtype_id(rs.getString("pdtype_id"));
				productVO.setStart_price(rs.getInt("start_price"));
				productVO.setLive_id(rs.getInt("live_id"));
				list.add(productVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
	
	// 使用InputStream資料流方式
	public static InputStream getPictureStream(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		return fis;
	}
	
	public static void main(String[] args) {

		ProductJDBCDAO dao = new ProductJDBCDAO();

		// 新增
		ProductVO productVO1 = new ProductVO();
		productVO1.setProduct_name("蘋果");
		productVO1.setProduct_info("AAA");
		productVO1.setProduct_price(new Integer(50));
		productVO1.setProduct_quantity(new Integer(2));
		productVO1.setProduct_remaining(new Integer(2));
		productVO1.setProduct_state("未定");
//	productVO1.setProduct_photo("未定");
		productVO1.setUser_id("abc123");
		productVO1.setPdtype_id("10");
		productVO1.setStart_price(new Integer(100));
		productVO1.setLive_id(new Integer(10));

		
		dao.insert(productVO1);
//
//		// 修改
//		ProductVO productVO2 = new ProductVO();
//		productVO2.setProduct_id(2);
//		productVO2.setProduct_name("橘子");
//		productVO2.setProduct_info("bbb");
//		productVO2.setProduct_price(new Integer(100));
//		productVO2.setProduct_quantity(new Integer(1));
//		productVO2.setProduct_remaining(new Integer(1));
//		productVO2.setProduct_state("未定");
////		productVO2.setProduct_photo("未定");
//		productVO2.setUser_id("abc123");
//		productVO2.setPdtype_id("10");
//		productVO2.setStart_price(new Integer(100));
//		productVO2.setLive_id(new Integer(10));
//		dao.update(productVO2);
//
//		// 刪除
//		dao.delete(1);

		// 查詢
//		ProductVO empVO3 = dao.findByPrimaryKey(7001);
//		System.out.print(empVO3.getEmpno() + ",");
//		System.out.print(empVO3.getEname() + ",");
//		System.out.print(empVO3.getJob() + ",");
//		System.out.print(empVO3.getHiredate() + ",");
//		System.out.print(empVO3.getSal() + ",");
//		System.out.print(empVO3.getComm() + ",");
//		System.out.println(empVO3.getDeptno());
//		System.out.println("---------------------");

		// 查詢
		List<ProductVO> list = dao.getAll();
		for (ProductVO product : list) {
			System.out.print(product.getProduct_id() + ",");
			System.out.print(product.getProduct_name() + ",");
			System.out.print(product.getProduct_info() + ",");
			System.out.print(product.getProduct_price() + ",");
			System.out.print(product.getProduct_quantity() + ",");
			System.out.print(product.getProduct_remaining() + ",");
			System.out.print(product.getProduct_state() + ",");
			System.out.print(product.getProduct_photo() + ",");
			System.out.print(product.getUser_id() + ",");
			System.out.print(product.getPdtype_id() + ",");
			System.out.print(product.getStart_price() + ",");
			System.out.print(product.getLive_id());
			System.out.println();
		}
	}
	
}