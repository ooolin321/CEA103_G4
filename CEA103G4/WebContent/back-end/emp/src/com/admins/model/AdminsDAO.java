package com.admins.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class AdminsDAO implements AdminsDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bookshop");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO ADMINS (ADMIN_ID, ADMIN_NAME, ADMIN_ID_NO, ADMIN_PSWD, ADMIN_MOBILE, ADMIN_ADDRESS, ADMIN_DUTYDATE, ADMIN_JOBSTATE, ADMIN_PIC, ADMIN_MAIL)"
			+ "VALUES ('ADM' || LPAD(ADMIN_SEQ.NEXTVAL, 4, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE ADMINS SET ADMIN_NAME=?, ADMIN_MOBILE=?, ADMIN_ADDRESS=?, ADMIN_JOBSTATE=?, ADMIN_PIC=?, ADMIN_MAIL=? WHERE ADMIN_ID = ?";
	private static final String FIND_BY_ADMIN_ID = "SELECT * FROM ADMINS WHERE ADMIN_ID = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM ADMINS ORDER BY ADMIN_ID";
	// update password
	private static final String UPDATE_PSWD = "UPDATE ADMINS SET ADMIN_PSWD= ? WHERE ADMIN_ID = ?";

	public Object insert(AdminsVO adminsVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String generatedColumns[] = { "admin_id" };

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT, generatedColumns);

			pstmt.setString(1, adminsVO.getAdmin_name());
			pstmt.setString(2, adminsVO.getAdmin_id_no());
			pstmt.setString(3, adminsVO.getAdmin_pswd());
			pstmt.setString(4, adminsVO.getAdmin_mobile());
			pstmt.setString(5, adminsVO.getAdmin_address());
			pstmt.setDate(6, adminsVO.getAdmin_dutydate());
			pstmt.setInt(7, adminsVO.getAdmin_jobstate());
			pstmt.setBytes(8, adminsVO.getAdmin_pic());
			pstmt.setString(9, adminsVO.getAdmin_mail());

			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			String admin_id = rs.getString(1);

			adminsVO.setAdmin_id(admin_id);

//			System.out.println("@AdminsDAO新增成功"+admin_id);

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
		return adminsVO;
	}

	@Override
	public void update(AdminsVO adminsVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, adminsVO.getAdmin_name());
			pstmt.setString(2, adminsVO.getAdmin_mobile());
			pstmt.setString(3, adminsVO.getAdmin_address());
			pstmt.setInt(4, adminsVO.getAdmin_jobstate());
			pstmt.setBytes(5, adminsVO.getAdmin_pic());
			pstmt.setString(6, adminsVO.getAdmin_mail());
			pstmt.setString(7, adminsVO.getAdmin_id());

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
	public AdminsVO findByPrimaryKey(String admin_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AdminsVO adminsVO = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_ADMIN_ID);

			pstmt.setString(1, admin_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				adminsVO = new AdminsVO();

				adminsVO.setAdmin_id(admin_id);
				adminsVO.setAdmin_name(rs.getString("admin_name"));
				adminsVO.setAdmin_id_no(rs.getString("admin_id_no"));
				adminsVO.setAdmin_pswd(rs.getString("admin_pswd"));
				adminsVO.setAdmin_mobile(rs.getString("admin_mobile"));
				adminsVO.setAdmin_address(rs.getString("admin_address"));
				adminsVO.setAdmin_dutydate(rs.getDate("admin_dutydate"));
				adminsVO.setAdmin_jobstate(rs.getInt("admin_jobstate"));
				adminsVO.setAdmin_pic(rs.getBytes("admin_pic"));
				adminsVO.setAdmin_mail(rs.getString("admin_mail"));
//				System.out.println("(FIND_BY_ADMIN_ID ok)");
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
		return adminsVO;
	}

	@Override
	public List<AdminsVO> getAll() {
		List<AdminsVO> list = new ArrayList<AdminsVO>();
		AdminsVO adminsVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				adminsVO = new AdminsVO();
				adminsVO.setAdmin_id(rs.getString("admin_id"));
				adminsVO.setAdmin_name(rs.getString("admin_name"));
				adminsVO.setAdmin_id_no(rs.getString("admin_id_no"));
				adminsVO.setAdmin_pswd(rs.getString("admin_pswd"));
				adminsVO.setAdmin_mobile(rs.getString("admin_mobile"));
				adminsVO.setAdmin_address(rs.getString("admin_address"));
				adminsVO.setAdmin_dutydate(rs.getDate("admin_dutydate"));
				adminsVO.setAdmin_jobstate(rs.getInt("admin_jobstate"));
				adminsVO.setAdmin_pic(rs.getBytes("admin_pic"));
				adminsVO.setAdmin_mail(rs.getString("admin_mail"));
				list.add(adminsVO); // Store the row in the list
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

	@Override
	public Optional<AdminsVO> findAdminPicByAdminId(String admin_id) {
		AdminsVO adminsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_ADMIN_ID);
			pstmt.setString(1, admin_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				adminsVO = new AdminsVO();
				adminsVO.setAdmin_pic(rs.getBytes("admin_pic"));
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
		return Optional.ofNullable(adminsVO);
	}

	@Override
	public void updatePswd(AdminsVO adminsVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_PSWD);

			pstmt.setString(1, adminsVO.getAdmin_pswd());
			pstmt.setString(2, adminsVO.getAdmin_id());

			pstmt.executeUpdate();

			System.out.println("updatePswd ok");

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
}
