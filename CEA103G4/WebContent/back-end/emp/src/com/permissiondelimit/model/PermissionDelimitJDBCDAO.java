package com.permissiondelimit.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PermissionDelimitJDBCDAO implements PermissionDelimitDAO_interface {

	String DRIVER = "oracle.jdbc.driver.OracleDriver";
	String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	String USER = "BOOKSHOP";
	String PASSWORD = "123456";

	private static final String INSERT_STMT = "INSERT INTO PERMISSION_DELIMIT (PER_ID, PER_NAME) VALUES ('PER' || LPAD(PER_SEQ.NEXTVAL, 4, '0'), ?)";
	private static final String UPDATE = "UPDATE PERMISSION_DELIMIT SET PER_NAME = ? WHERE PER_ID = ?";
	private static final String DELETE = "DELETE FROM PERMISSION_DELIMIT WHERE PER_ID = ?";
	private static final String FIND_BY_PER_ID = "SELECT PER_NAME FROM PERMISSION_DELIMIT WHERE PER_ID = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM PERMISSION_DELIMIT ORDER BY PER_ID";
	private static final String FIND_BY_PER_URI = "SELECT * FROM PERMISSION_DELIMIT WHERE PER_URI = ?";

	@Override
	public void insert(PermissionDelimitVO permissiondelimitVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, permissiondelimitVO.getPer_name());

			pstmt.executeUpdate();
//			System.out.println("新增成功");

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
	public void update(PermissionDelimitVO permissiondelimitVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, permissiondelimitVO.getPer_name());
			pstmt.setString(2, permissiondelimitVO.getPer_id());
			pstmt.executeUpdate();
//			System.out.println("per_name update ok");

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
	public void delete(String per_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, per_id);
			pstmt.executeUpdate();
//			System.out.println("per_id delete ok");
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
	public PermissionDelimitVO findByPrimaryKey(String per_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PermissionDelimitVO permissiondelimitVO = null;

		try {
			
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PER_ID);

			pstmt.setString(1, per_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				permissiondelimitVO = new PermissionDelimitVO();

				permissiondelimitVO.setPer_id(per_id);
				permissiondelimitVO.setPer_name(rs.getString("per_name"));
				System.out.println("(FIND_BY_PER_ID ok)");
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
		return permissiondelimitVO;
	}

	@Override
	public List<PermissionDelimitVO> getAll() {
		List<PermissionDelimitVO> list = new ArrayList<PermissionDelimitVO>();
		PermissionDelimitVO permissiondelimitVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				permissiondelimitVO = new PermissionDelimitVO();
				permissiondelimitVO.setPer_id(rs.getString("per_id"));
				permissiondelimitVO.setPer_name(rs.getString("per_name"));
				list.add(permissiondelimitVO); // Store the row in the list
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
	public PermissionDelimitVO findByUri(String per_uri) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PermissionDelimitVO permissiondelimitVO = null;

		try {
			
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PER_URI);

			pstmt.setString(1, per_uri);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				permissiondelimitVO = new PermissionDelimitVO();

				permissiondelimitVO.setPer_uri(per_uri);
				permissiondelimitVO.setPer_id(rs.getString("per_id"));
				permissiondelimitVO.setPer_name(rs.getString("per_name"));

				System.out.println("(FIND_BY_PER_URI ok)");
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
		return permissiondelimitVO;
	}
//	public static void main(String[] args) throws IOException {
//
//		PermissionDelimitJDBCDAO dao = new PermissionDelimitJDBCDAO();
//
//		// add
//		PermissionDelimitVO permissiondelimitVO1 = new PermissionDelimitVO();
//		permissiondelimitVO1.setPer_name("測試管理");
//
//		dao.insert(permissiondelimitVO1);
//		System.out.println("add ok");
//		// update
//		PermissionDelimitVO permissiondelimitVO2 = new PermissionDelimitVO();
//		permissiondelimitVO2.setPer_name("測試測試管理");
//		permissiondelimitVO2.setPer_id("PER0003");
//
//		dao.update(permissiondelimitVO2);
//
//		System.out.println("update ok");
//		// delete
//		dao.delete("PER0005");
//		System.out.println("delete ok");
//
//		//select one
//		PermissionDelimitVO permissiondelimitVO3 = dao.findByPrimaryKey("PER0001");
//		System.out.print(permissiondelimitVO3.getPer_name());
//
//		System.out.println("===============================");
//
//		//select all
//		List<PermissionDelimitVO> list = dao.getAll();
//		for (PermissionDelimitVO apermissiondelimit : list) {
//			System.out.print(apermissiondelimit.getPer_id() + ",");
//			System.out.print(apermissiondelimit.getPer_name());
//			System.out.println();
//		}
//	}
}
