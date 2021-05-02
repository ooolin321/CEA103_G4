package com.adminpermission.model;

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


public class AdminPermissionDAO implements AdminPermissionDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bookshop");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO ADMIN_PERMISSION (ADMIN_ID, PER_ID) VALUES (?, ?)";
	private static final String DELETE = "DELETE FROM ADMIN_PERMISSION WHERE ADMIN_ID = ?";
	private static final String FIND_BY_ADMIN_ID = "SELECT * FROM ADMIN_PERMISSION WHERE ADMIN_ID = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM ADMIN_PERMISSION ORDER BY ADMIN_ID";

	@Override
	public void insert(AdminPermissionVO adminpermissionVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, adminpermissionVO.getAdmin_id());
			pstmt.setString(2, adminpermissionVO.getPer_id());
			pstmt.executeUpdate();
//			System.out.println("權限新增成功");

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
	public void delete(String admin_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, admin_id);
			pstmt.executeUpdate();
			//System.out.println("用admin_id刪除成功");
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
	public List<AdminPermissionVO> findByPrimaryKey(String admin_id) {
		List<AdminPermissionVO>  list= new ArrayList<>() ;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AdminPermissionVO adminpermissionVO = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_ADMIN_ID);// 用連線取得prepareStatement
			pstmt.setString(1, admin_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// 產生一個JavaBean,用來包裝『從資料庫』查詢到的部門資料
				adminpermissionVO = new AdminPermissionVO();
				// 這裡直接使用admin_id是因為代表就是我們直接查詢出來的部門編號了
				adminpermissionVO.setAdmin_id(admin_id);
				adminpermissionVO.setPer_id(rs.getString("per_id"));// 從欄位把資料取出再放進物件裡
				list.add(adminpermissionVO);
//				System.out.println("查詢成功(FIND_BY_ADMIN_ID)");
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
		return list;
	}

	
	@Override
	public List<AdminPermissionVO> getAll() {
		List<AdminPermissionVO> list = new ArrayList<AdminPermissionVO>();
		AdminPermissionVO adminpermissionVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// adminsVO 也稱為 Domain objects
				adminpermissionVO = new AdminPermissionVO();
				adminpermissionVO.setAdmin_id(rs.getString("admin_id"));
				adminpermissionVO.setPer_id(rs.getString("per_id"));
				list.add(adminpermissionVO); // Store the row in the list
//				System.out.println("查詢成功(GET_ALL_STMT)");
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
