package com.adminpermission.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminPermissionJDBCDAO implements AdminPermissionDAO_interface {

	String DRIVER = "oracle.jdbc.driver.OracleDriver";
	String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	String USER = "BOOKSHOP";
	String PASSWORD = "123456";

	private static final String INSERT_STMT = "INSERT INTO ADMIN_PERMISSION (ADMIN_ID, PER_ID) VALUES (?, ?)";
	private static final String DELETE = "DELETE FROM ADMIN_PERMISSION WHERE ADMIN_ID = ?";
	private static final String FIND_BY_ADMIN_ID = "SELECT * FROM ADMIN_PERMISSION WHERE ADMIN_ID = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM ADMIN_PERMISSION ORDER BY ADMIN_ID";

	public void insert(AdminPermissionVO adminpermissionVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, adminpermissionVO.getAdmin_id());
			pstmt.setString(2, adminpermissionVO.getPer_id());
			pstmt.executeUpdate();
//			System.out.println("權限新增成功");

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, admin_id);
			pstmt.executeUpdate();
//			System.out.println("用admin_id刪除成功");
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
		List<AdminPermissionVO> list = new ArrayList<>();
		AdminPermissionVO adminpermissionVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_ADMIN_ID);					
			pstmt.setString(1, admin_id);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// adminsVO 也稱為 Domain objects
				adminpermissionVO = new AdminPermissionVO();
				adminpermissionVO.setPer_id(rs.getString("per_id"));
				list.add(adminpermissionVO); // Store the row in the list
//				System.out.println("查詢成功(FIND_ONE_ADMINPER)");
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
	public List<AdminPermissionVO> getAll() {
		List<AdminPermissionVO> list = new ArrayList<AdminPermissionVO>();
		AdminPermissionVO adminpermissionVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
	
//	public static void main(String[] args){
//
//		AdminPermissionJDBCDAO dao = new AdminPermissionJDBCDAO();
		// 新增OK
//				AdminPermissionVO adminpermissionVO1 = new AdminPermissionVO();
//				adminpermissionVO1.setAdmin_id("ADM0004");
//				adminpermissionVO1.setPer_id("PER0008");
//				
//				dao.insert(adminpermissionVO1);
//				System.out.println("成功");
//		// 修改????只能修改一個會員一個權限的
//				AdminPermissionVO adminpermissionVO2 = new AdminPermissionVO();
//				adminpermissionVO2.setPer_id("PER0030");
//				adminpermissionVO2.setAdmin_id("ADM0001");
//				dao.update(adminpermissionVO2);	
//				System.out.println("修改成功");
//				
		// 刪除
//				dao.delete("ADM0004");
//				System.out.println("刪除成功");

//				
		// 查詢by admin_id OK 只能查一個會員的多個權限
//			List<AdminPermissionVO> list = dao.findByPrimaryKey("ADM0001");
//		for (AdminPermissionVO aadminpermissionVO : list) {
//			System.out.print(aadminpermissionVO.getPer_id());
//			System.out.println();
//		}

		// 查詢OK
//				List<AdminPermissionVO> list = dao.getAll();
//				for (AdminPermissionVO aadminpermissionVO : list) {
//					System.out.print(aadminpermissionVO.getAdmin_id() + ",");
//					System.out.print(aadminpermissionVO.getPer_id());
//					System.out.println();
//				}

