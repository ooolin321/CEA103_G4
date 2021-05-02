package com.admins.model;

import java.util.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class AdminsJDBCDAO implements AdminsDAO_interface {

	String DRIVER = "oracle.jdbc.driver.OracleDriver";
	String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	String USER = "BOOKSHOP";
	String PASSWORD = "123456";

	private static final String INSERT_STMT = "INSERT INTO ADMINS (ADMIN_ID, ADMIN_NAME, ADMIN_ID_NO, ADMIN_PSWD, ADMIN_MOBILE, ADMIN_ADDRESS, ADMIN_DUTYDATE, ADMIN_JOBSTATE, ADMIN_PIC, ADMIN_MAIL)"
			+ "VALUES ('ADM' || LPAD(ADMIN_SEQ.NEXTVAL, 4, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE ADMINS SET ADMIN_NAME=?, ADMIN_MOBILE=?, ADMIN_ADDRESS=?, ADMIN_JOBSTATE=?, ADMIN_PIC=?, ADMIN_MAIL=? WHERE ADMIN_ID = ?";
	private static final String FIND_BY_ADMIN_ID = "SELECT * FROM ADMINS WHERE ADMIN_ID = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM ADMINS ORDER BY ADMIN_ID";
	//update password
	private static final String UPDATE_PSWD = "UPDATE ADMINS SET ADMIN_PSWD= ? WHERE ADMIN_ID = ?";


	public Object insert(AdminsVO adminsVO){
		Connection con = null;
		PreparedStatement pstmt = null;
		String[] generatedColumns = {"admin_id"};

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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
			
//			System.out.println("新增成功"+rs);
//			System.out.println(admin_id);

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
//			se.printStackTrace();
			// Clean up JDBC resources
		}finally {
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

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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
			

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources		
		}finally {
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
	public AdminsVO findByPrimaryKey(String admin_id) { //先將係向全寫出 不想顯示再刪除
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AdminsVO adminsVO = null;

		try {
			
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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
				System.out.println("(FIND_BY_ADMIN_ID ok)");
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

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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
	public Optional<AdminsVO> findAdminPicByAdminId(String admin_id){
		AdminsVO adminsVO = null;
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
				AdminsVO adminVO = new AdminsVO();
				adminVO.setAdmin_pic(rs.getBytes("admin_pic"));
			}
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
		return Optional.ofNullable(adminsVO);
	}
	
	public void updatePswd(AdminsVO adminsVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_PSWD);
			
			pstmt.setString(1, adminsVO.getAdmin_pswd());
			pstmt.setString(2, adminsVO.getAdmin_id());
			
			pstmt.executeUpdate();
			
//			System.out.println("updatePswd ok");
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources		
		}finally {
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
	

//		public static void main(String[] args) throws IOException {
//
//			AdminsJDBCDAO dao = new AdminsJDBCDAO();

	// add
//			AdminsVO adminsVO1 = new AdminsVO();
//			adminsVO1.setAdmin_name("林嗯嗯");
//			adminsVO1.setAdmin_id_no("M223344556");
//			adminsVO1.setAdmin_pswd("123456");
//			adminsVO1.setAdmin_mobile("0922334556");
//			adminsVO1.setAdmin_address("哈哈");
//			adminsVO1.setAdmin_dutydate(java.sql.Date.valueOf("2005-01-01"));
//			adminsVO1.setAdmin_jobstate(new Integer(0));
//			byte[] pic = getPictureByteArray("WebContent/images/admins/ADM0001.png");
//			adminsVO1.setAdmin_pic(pic);
//			adminsVO1.setAdmin_mail("asd@gmail.com");
//			adminsVO1.getAdmin_id();
//			dao.insert(adminsVO1);
//			System.out.println(adminsVO1);
////	// update
//			AdminsVO adminsVO2 = new AdminsVO();
//			adminsVO2.setAdmin_name("摳門王");
//			adminsVO2.setAdmin_acnt("kingking");
//			adminsVO2.setAdmin_mobile("0922334556");
//			adminsVO2.setAdmin_address("金門");
//			adminsVO2.setAdmin_jobstate(new Integer(0));
//			adminsVO2.setAdmin_resigndate(java.sql.Date.valueOf("2020-06-03"));
//			byte[] pic = getPictureByteArray("WebContent/images/admins/ADM0001.png");
//			System.out.println(pic);
//			adminsVO2.setAdmin_pic(pic);
//			adminsVO2.setAdmin_mail("asd@gmail.com");		
//			adminsVO2.setAdmin_id("ADM0008");
//			dao.update(adminsVO2);
//			System.out.println("");
////			// delete 刪除OK
//			dao.delete("ADM0008");
//			System.out.println("delet ok");
//
//	// select one
//			AdminsVO adminsVO3 = dao.findByPrimaryKey("ADM0008");
//			System.out.print(adminsVO3.getAdmin_name() + ",");
//			System.out.print(adminsVO3.getAdmin_id_no() + ",");
//			System.out.print(adminsVO3.getAdmin_acnt() + ",");
//			System.out.print(adminsVO3.getAdmin_mobile()+ ",");
//			System.out.print(adminsVO3.getAdmin_address() + ",");
//			System.out.print(adminsVO3.getAdmin_dutydate() + ",");
//			System.out.print(adminsVO3.getAdmin_jobstate() + ",");
//			System.out.print(adminsVO3.getAdmin_mail() + ",");
//			System.out.println(adminsVO3.getAdmin_pic());
//			System.out.println("---------------------");
//
//	// select all OK
//			List<AdminsVO> list = dao.getAll();
//			for (AdminsVO aAdmins : list) {
//				System.out.print(aAdmins.getAdmin_id() + ",");
//				System.out.print(aAdmins.getAdmin_name() + ",");
//				System.out.print(aAdmins.getAdmin_id_no() + ",");
//				System.out.print(aAdmins.getAdmin_acnt() + ",");
//				System.out.print(aAdmins.getAdmin_mobile() + ",");
//				System.out.print(aAdmins.getAdmin_address() + ",");
//				System.out.print(aAdmins.getAdmin_dutydate() + ",");
//				System.out.print(aAdmins.getAdmin_jobstate() + ",");
//			    System.out.print(aAdmins.getAdmin_mail() + ",");
//				System.out.print(aAdmins.getAdmin_pic() + ",");
//				System.out.println();
//			}
	//select pic by admin_id
//	AdminsVO adminsVO4 = dao.findAdminPicByAdminId("ADM0008");	
//	System.out.println(adminsVO4.getAdmin_pic());
			
			//FIND_ADMIN_ACNT_PSWD_BY_ADMIN_ID
//			AdminsVO adminsVO4  = dao.findOneAdminAcntPswd("ADM0010");
//			System.out.print(adminsVO4.getAdmin_acnt() + ",");
//			System.out.print(adminsVO4.getAdmin_pswd());
//		}
	
	// update_pswd
//	AdminsVO adminsVO6 = new AdminsVO();
//	adminsVO6.setAdmin_pswd("000000");
//	adminsVO6.setAdmin_id("ADM0071");
//	dao.updatePswd(adminsVO6);
//	System.out.println("adminsVO6.getAdmin_pswd"+ adminsVO6.getAdmin_pswd());
//		}

}

		
