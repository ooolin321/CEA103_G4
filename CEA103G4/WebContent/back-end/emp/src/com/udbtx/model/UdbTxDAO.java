package com.udbtx.model;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class UdbTxDAO implements UdbTxDAO_interface {

	private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bookshop");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO USEDBOOK_TRANSACTION (PO_NO, BOOK_ID, SELLER_MEM_ID, BUYER_MEM_ID, BOOK_STATE, BOOK_STATE_PIC, UDB_SALE_PRICE, RELEASE_DATE, UDB_ORDER_STATE_NO, UDB_ORDER_DATE, PROD_STATE, PAYMENT_STATE) "
			+ "VALUES ('PO' || LPAD(PO_SEQ.NEXTVAL, 4, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE USEDBOOK_TRANSACTION SET BOOK_STATE = ?,  BOOK_STATE_PIC = ? , UDB_SALE_PRICE = ? ,UDB_ORDER_STATE_NO = ?, PROD_STATE = ?, PAYMENT_STATE = ? WHERE PO_NO = ?";
	private static final String DELETE_BY_PO_NO = "DELETE FROM USEDBOOK_TRANSACTION WHERE PO_NO = ?";
	private static final String FIND_BY_PO_NO = "SELECT * FROM USEDBOOK_TRANSACTION WHERE PO_NO = ?";
	private static final String FIND_BY_SELLER_MEM_ID = "SELECT PO_NO, BOOK_ID, BUYER_MEM_ID, BOOK_STATE, BOOK_STATE_PIC, UDB_SALE_PRICE, RELEASE_DATE FROM USEDBOOK_TRANSACTION WHERE SELLER_MEM_ID = ?";
	private static final String FIND_BY_BUYER_MEM_ID = "SELECT PO_NO, BOOK_ID, SELLER_MEM_ID, BOOK_STATE, BOOK_STATE_PIC, UDB_SALE_PRICE, RELEASE_DATE FROM USEDBOOK_TRANSACTION WHERE BUYER_MEM_ID = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM USEDBOOK_TRANSACTION ORDER BY BOOK_ID";

	@Override
	public void insert(UdbTxVO udbtxVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, udbtxVO.getBook_id());
			pstmt.setString(2, udbtxVO.getSeller_mem_id());
			pstmt.setString(3, udbtxVO.getBuyer_mem_id());
			pstmt.setString(4, udbtxVO.getBook_state());
			pstmt.setBytes(5, udbtxVO.getBook_state_pic());
			pstmt.setInt(6, udbtxVO.getUdb_sale_price());
			pstmt.setTimestamp(7, udbtxVO.getRelease_date());
			pstmt.setInt(8, udbtxVO.getUdb_order_state_no());
			pstmt.setTimestamp(9, udbtxVO.getUdb_order_date());
			pstmt.setInt(10, udbtxVO.getProd_state());
			pstmt.setInt(11, udbtxVO.getPayment_state());

			pstmt.executeUpdate();
			System.out.println("新增成功");

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
//			se.printStackTrace();
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
	public void update(UdbTxVO udbtxVO) {
		// BOOK_STATE = ?, BOOK_STATE_PIC = ? , UDB_SALE_PRICE = ? ,UDB_ORDER_STATE_NO =
		// ?,
		// PROD_STATE = ?, PAYMENT_STATEWHERE = ? WHERE PO_NO = ?
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, udbtxVO.getBook_state());
			pstmt.setBytes(2, udbtxVO.getBook_state_pic());
			pstmt.setInt(3, udbtxVO.getUdb_sale_price());
			pstmt.setInt(4, udbtxVO.getUdb_order_state_no());
			pstmt.setInt(5, udbtxVO.getProd_state());
			pstmt.setInt(6, udbtxVO.getPayment_state());
			pstmt.setString(7, udbtxVO.getPo_no());
			pstmt.executeUpdate();
			System.out.println("新增成功");

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
//			se.printStackTrace();
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
	public void delete(String po_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_BY_PO_NO);

			pstmt.setString(1, po_no);
			pstmt.executeUpdate();
			System.out.println("delete ok");
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
	public UdbTxVO findByPoNo(String po_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UdbTxVO udbtxVO = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_PO_NO);

			pstmt.setString(1, po_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				udbtxVO = new UdbTxVO();

				udbtxVO.setPo_no(po_no);
				udbtxVO.setBook_id(rs.getString("book_id"));
				udbtxVO.setSeller_mem_id(rs.getString("seller_mem_id"));
				udbtxVO.setBuyer_mem_id(rs.getString("buyer_mem_id"));
				udbtxVO.setBook_state(rs.getString("book_state"));
				udbtxVO.setBook_state_pic(rs.getBytes("book_state_pic"));
				udbtxVO.setUdb_sale_price(rs.getInt("udb_sale_price"));
				udbtxVO.setRelease_date(rs.getTimestamp("release_date"));
				udbtxVO.setUdb_order_state_no(rs.getInt("udb_order_state_no"));
				udbtxVO.setUdb_order_date(rs.getTimestamp("udb_order_date"));
				udbtxVO.setProd_state(rs.getInt("prod_state"));
				udbtxVO.setPayment_state(rs.getInt("payment_state"));
				System.out.println("(FIND_BY_PO_NO ok)");
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
		return udbtxVO;
	}

	@Override
	public List<UdbTxVO> getAll() {
		List<UdbTxVO> list = new ArrayList<UdbTxVO>();
		UdbTxVO udbtxVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				udbtxVO = new UdbTxVO();
				udbtxVO.setPo_no(rs.getString("po_no"));
				udbtxVO.setBook_id(rs.getString("book_id"));
				udbtxVO.setSeller_mem_id(rs.getString("seller_mem_id"));
				udbtxVO.setBuyer_mem_id(rs.getString("buyer_mem_id"));
				udbtxVO.setBook_state(rs.getString("book_state"));
				udbtxVO.setBook_state_pic(rs.getBytes("book_state_pic"));
				udbtxVO.setUdb_sale_price(rs.getInt("udb_sale_price"));
				udbtxVO.setRelease_date(rs.getTimestamp("release_date"));
				udbtxVO.setUdb_order_state_no(rs.getInt("udb_order_state_no"));
				udbtxVO.setUdb_order_date(rs.getTimestamp("udb_order_date"));
				udbtxVO.setProd_state(rs.getInt("prod_state"));
				udbtxVO.setPayment_state(rs.getInt("payment_state"));
				list.add(udbtxVO); // Store the row in the list
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
	public List<UdbTxVO> findBySellerId(String seller_mem_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<UdbTxVO> list = new ArrayList<>();
		UdbTxVO udbtxVO = null;

		try {
			
			con = ds.getConnection();

			pstmt = con.prepareStatement(FIND_BY_SELLER_MEM_ID);

			pstmt.setString(1, seller_mem_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				udbtxVO = new UdbTxVO();

				udbtxVO.setSeller_mem_id(seller_mem_id);
				udbtxVO.setPo_no(rs.getString("po_no"));
				udbtxVO.setBook_id(rs.getString("book_id"));
				udbtxVO.setBuyer_mem_id(rs.getString("buyer_mem_id"));
				udbtxVO.setBook_state(rs.getString("book_state"));
				udbtxVO.setBook_state_pic(rs.getBytes("book_state_pic"));
				udbtxVO.setUdb_sale_price(rs.getInt("udb_sale_price"));
				udbtxVO.setRelease_date(rs.getTimestamp("release_date"));
				list.add(udbtxVO);
				System.out.println("(FIND_BY_SELLER_MEM_ID ok)");
			}
		}  catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public List<UdbTxVO> findByBuyerId(String buyer_mem_id) {
		List<UdbTxVO> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UdbTxVO udbtxVO = null;

		try {

			
			con = ds.getConnection();

			pstmt = con.prepareStatement(FIND_BY_BUYER_MEM_ID);

			pstmt.setString(1, buyer_mem_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				udbtxVO = new UdbTxVO();
				udbtxVO.setBuyer_mem_id(buyer_mem_id);

				udbtxVO.setPo_no(rs.getString("po_no"));
				udbtxVO.setBook_id(rs.getString("book_id"));
				udbtxVO.setBuyer_mem_id(rs.getString("seller_mem_id"));
				udbtxVO.setBook_state(rs.getString("book_state"));
				udbtxVO.setBook_state_pic(rs.getBytes("book_state_pic"));
				udbtxVO.setUdb_sale_price(rs.getInt("udb_sale_price"));
				udbtxVO.setRelease_date(rs.getTimestamp("release_date"));
				list.add(udbtxVO);
				
				System.out.println("(FIND_BY_buyER_MEM_ID ok)");
			}
		}  catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
		return list;

	}
	
	public Optional<UdbTxVO> findUdbPicByPoNo(String po_no){
		UdbTxVO udbtxVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_PO_NO);
			pstmt.setString(1, po_no);
			

			rs = pstmt.executeQuery();

			while (rs.next()) {
				udbtxVO = new UdbTxVO();
				udbtxVO.setBook_state_pic(rs.getBytes("book_state_pic"));
			}
		}  catch (SQLException se) {
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
		return Optional.ofNullable(udbtxVO);
	}

	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
			baos.flush();
		}
		baos.close();
		fis.close();

		return baos.toByteArray();

	}
}
