package com.permissiondelimit.model;

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

public class PermissionDelimitDAO implements PermissionDelimitDAO_interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bookshop");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO PERMISSION_DELIMIT (PER_ID, PER_NAME) VALUES ('PER' || LPAD(PER_SEQ.NEXTVAL, 4, '0'), ?)";
	private static final String UPDATE = "UPDATE PERMISSION_DELIMIT SET PER_NAME = ? WHERE PER_ID = ?";
	private static final String DELETE = "DELETE FROM PERMISSION_DELIMIT WHERE PER_ID = ?";
	private static final String FIND_BY_PER_ID = "SELECT * FROM PERMISSION_DELIMIT WHERE PER_ID = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM PERMISSION_DELIMIT ORDER BY PER_ID";
	private static final String FIND_BY_PER_URI = "SELECT * FROM PERMISSION_DELIMIT WHERE PER_URI = ?";

	@Override
	public void insert(PermissionDelimitVO permissiondelimitVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, permissiondelimitVO.getPer_name());
			pstmt.executeUpdate();
//			System.out.println("add ok");

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

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, permissiondelimitVO.getPer_name());
			pstmt.setString(2, permissiondelimitVO.getPer_id());
			pstmt.executeUpdate();
//			System.out.println("update ok");

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

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, per_id);
			pstmt.executeUpdate();
			System.out.println("delet ok");
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
	public PermissionDelimitVO findByPrimaryKey(String per_id) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PermissionDelimitVO permissiondelimitVO = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_PER_ID);
			pstmt.setString(1, per_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				permissiondelimitVO = new PermissionDelimitVO();
				
				permissiondelimitVO.setPer_id(per_id);
				permissiondelimitVO.setPer_name(rs.getString("per_name"));
//				System.out.println("(FIND_BY_PER_ID)");
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				permissiondelimitVO = new PermissionDelimitVO();
				permissiondelimitVO.setPer_id(rs.getString("per_id"));
				permissiondelimitVO.setPer_name(rs.getString("per_name"));
				list.add(permissiondelimitVO); // Store the row in the list
//				System.out.println("(GET_ALL_STMT)");
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
	public PermissionDelimitVO findByUri(String per_uri) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PermissionDelimitVO permissiondelimitVO = null;

		try {
						
			con = ds.getConnection();
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
		return permissiondelimitVO;
	}	
}

