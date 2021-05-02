//package com.signup.model;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class SignupJDBCDAO implements SignupDAO_interface {
//
//	String driver = "oracle.jdbc.driver.OracleDriver";
//	String url = "jdbc:oracle:thin:@localhost:1521:XE";
//	String userid = "BOOKSHOPG4";
//	String passwd = "123456";
//
//	private static final String INSERT_STMT = "INSERT INTO SIGNUP_DETAIL (SIGNUP_ID, MEM_ID, LC_ID, SIGNUP_PAY, PAY_STATE, SIGNUP_TIME, PAY_TIME, SIGN_SEAT) VALUES ('S' || lpad(SIGNUP_SEQ.NEXTVAL, 4, '0'), ?, ?, ?, ?, DEFAULT, ?, ?)";
//	private static final String GET_ALL_STMT = "SELECT * FROM SIGNUP_DETAIL order by SIGNUP_ID";
//	private static final String GET_ONE_STMT = "SELECT * FROM SIGNUP_DETAIL WHERE SIGNUP_ID = ?";
//	private static final String GET_LECTURE_STMT = "SELECT * FROM SIGNUP_DETAIL WHERE LC_ID = ?";
//	private static final String GET_MEMBER_STMT = "SELECT * FROM SIGNUP_DETAIL WHERE MEM_ID = ?";
//	private static final String DELETE = "DELETE FROM SIGNUP_DETAIL where SIGNUP_ID = ?";
//	private static final String UPDATE = "UPDATE SIGNUP_DETAIL set SIGNUP_PAY=?, PAY_STATE=?, PAY_TIME=?, SIGN_SEAT=? WHERE SIGNUP_ID = ?";
//
//	@Override
//	public void insert(SignupVO signupVO) {
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(INSERT_STMT);
//
//			pstmt.setString(1, signupVO.getMem_id());
//			pstmt.setString(2, signupVO.getLc_id());
//			pstmt.setString(3, signupVO.getSignup_pay());
//			pstmt.setInt(4, signupVO.getPay_state());
////			pstmt.setTimestamp(5, signupVO.getSignup_time());
//			pstmt.setTimestamp(5, signupVO.getPay_time());
//			pstmt.setString(6, signupVO.getSign_seat());
//
//			pstmt.executeUpdate();
//
//			// Handle any SQL errors
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//	}
//
//	@Override
//	public void update(SignupVO signupVO) {
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(UPDATE);
//
//			pstmt.setString(1, signupVO.getSignup_pay());
//			pstmt.setInt(2, signupVO.getPay_state());
//			pstmt.setTimestamp(3, signupVO.getPay_time());
//			pstmt.setString(4, signupVO.getSign_seat());
//			pstmt.setString(5, signupVO.getSignup_id());
//
//			pstmt.executeUpdate();
//
//			// Handle any SQL errors
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//
//	}
//
//	@Override
//	public void delete(String signup_id) {
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(DELETE);
//
//			pstmt.setString(1, signup_id);
//
//			pstmt.executeUpdate();
//
//			// Handle any SQL errors
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
//		} catch (SQLException se) {
//			if (con != null) {
//				try {
//					// 3���]�w���exception�o�ͮɤ�catch�϶���
//					con.rollback();
//				} catch (SQLException excep) {
//					throw new RuntimeException("rollback error occured. " + excep.getMessage());
//				}
//			}
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//		} finally {
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//
//	}
//
//	@Override
//	public SignupVO findByPrimaryKey(String signup_id) {
//
//		SignupVO signupVO = null;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(GET_ONE_STMT);
//
//			pstmt.setString(1, signup_id);
//
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				// signupVO �]�٬� Domain objects
//				signupVO = new SignupVO();
//				signupVO.setSignup_id(rs.getString("signup_id"));
//				signupVO.setMem_id(rs.getString("mem_id"));
//				signupVO.setLc_id(rs.getString("lc_id"));
//				signupVO.setSignup_pay(rs.getString("signup_pay"));
//				signupVO.setPay_state(rs.getInt("pay_state"));
//				signupVO.setSignup_time(rs.getTimestamp("signup_time"));
//				signupVO.setPay_time(rs.getTimestamp("pay_time"));
//				signupVO.setSign_seat(rs.getString("sign_seat"));
//			}
//
//			// Handle any SQL errors
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return signupVO;
//	}
//
//	@Override
//	public List<SignupVO> findByLecture(String lc_id) {
//		List<SignupVO> list1 = new ArrayList<SignupVO>();
//		SignupVO signupVO = null;
//		
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(GET_LECTURE_STMT);
//
//			pstmt.setString(1, lc_id);
//
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				// signupVO �]�٬� Domain objects
//				signupVO = new SignupVO();
//				signupVO.setSignup_id(rs.getString("signup_id"));
//				signupVO.setMem_id(rs.getString("mem_id"));
//				signupVO.setLc_id(rs.getString("lc_id"));
//				signupVO.setSignup_pay(rs.getString("signup_pay"));
//				signupVO.setPay_state(rs.getInt("pay_state"));
//				signupVO.setSignup_time(rs.getTimestamp("signup_time"));
//				signupVO.setPay_time(rs.getTimestamp("pay_time"));
//				signupVO.setSign_seat(rs.getString("sign_seat"));
//				list1.add(signupVO);
//			}
//
//			// Handle any SQL errors
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return list1;
//	}
//
//	@Override
//	public List<SignupVO> findByMember(String mem_id) {
//		List<SignupVO> list2 = new ArrayList<SignupVO>();
//		SignupVO signupVO = null;
//		
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(GET_MEMBER_STMT);
//
//			pstmt.setString(1, mem_id);
//
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				// signupVO �]�٬� Domain objects
//				signupVO = new SignupVO();
//				signupVO.setSignup_id(rs.getString("signup_id"));
//				signupVO.setMem_id(rs.getString("mem_id"));
//				signupVO.setLc_id(rs.getString("lc_id"));
//				signupVO.setSignup_pay(rs.getString("signup_pay"));
//				signupVO.setPay_state(rs.getInt("pay_state"));
//				signupVO.setSignup_time(rs.getTimestamp("signup_time"));
//				signupVO.setPay_time(rs.getTimestamp("pay_time"));
//				signupVO.setSign_seat(rs.getString("sign_seat"));
//				list2.add(signupVO);
//			}
//
//			// Handle any SQL errors
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return list2;
//	}
//
//	@Override
//	public List<SignupVO> getAll() {
//		List<SignupVO> list3 = new ArrayList<SignupVO>();
//		SignupVO signupVO = null;
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(GET_ALL_STMT);
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				// signupVO �]�٬� Domain objects
//				signupVO = new SignupVO();
//				signupVO.setSignup_id(rs.getString("signup_id"));
//				signupVO.setMem_id(rs.getString("mem_id"));
//				signupVO.setLc_id(rs.getString("lc_id"));
//				signupVO.setSignup_pay(rs.getString("signup_pay"));
//				signupVO.setPay_state(rs.getInt("pay_state"));
//				signupVO.setSignup_time(rs.getTimestamp("signup_time"));
//				signupVO.setPay_time(rs.getTimestamp("pay_time"));
//				signupVO.setSign_seat(rs.getString("sign_seat"));
//				list3.add(signupVO); // Store the row in the list
//			}
//
//			// Handle any driver errors
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return list3;
//	}
//
//	public static void main(String[] args) {
//
//		SignupJDBCDAO dao = new SignupJDBCDAO();
//
//		// �s�W
////		SignupVO signupVO1 = new SignupVO();
////		signupVO1.setMem_id("M0001");
////		signupVO1.setLc_id("L0001");
////		signupVO1.setSignup_pay("822-552350236411");
////		signupVO1.setPay_state(new Integer(1));
////		signupVO1.setPay_time(java.sql.Timestamp.valueOf("2020-10-07 16:04:59"));
////		signupVO1.setSign_seat("5-5");
////		dao.insert(signupVO1);
//
////		// �ק�
////		SignupVO signupVO2 = new SignupVO();
////		signupVO2.setSignup_pay("822-552350236411");
////		signupVO2.setPay_state(new Integer(3));
////		signupVO2.setPay_time(java.sql.Timestamp.valueOf("2020-10-07 16:04:35"));
////		signupVO2.setSign_seat("5-5");
////		signupVO2.setSignup_id("S0003");
////		dao.update(signupVO2);
////
////		// �R��
////		dao.delete("S0002");
////
////		// �d�ߩ��ӽs��
//		SignupVO signupVO3 = dao.findByPrimaryKey("S0003");
//		System.out.print(signupVO3.getSignup_id() + ",");
//		System.out.print(signupVO3.getMem_id() + ",");
//		System.out.print(signupVO3.getLc_id() + ",");
//		System.out.print(signupVO3.getSignup_pay() + ",");
//		System.out.print(signupVO3.getPay_state() + ",");
//		System.out.print(signupVO3.getSignup_time() + ",");
//		System.out.print(signupVO3.getPay_time() + ",");
//		System.out.println(signupVO3.getSign_seat());
//		System.out.println("---------------------");
//
////		// �d�����y�s��
//		List<SignupVO> list1 = dao.findByLecture("L0001");
//		for(SignupVO aSignup : list1) {
//		System.out.print(aSignup.getSignup_id() + ",");
//		System.out.print(aSignup.getMem_id() + ",");
//		System.out.print(aSignup.getLc_id() + ",");
//		System.out.print(aSignup.getSignup_pay() + ",");
//		System.out.print(aSignup.getPay_state() + ",");
//		System.out.print(aSignup.getSignup_time() + ",");
//		System.out.print(aSignup.getPay_time() + ",");
//		System.out.println(aSignup.getSign_seat());
//		System.out.println("---------------------");
//		}
////		// �d�߷|���s��
//		List<SignupVO> list2 = dao.findByMember("M0001");
//		for(SignupVO aSignup : list2) {
//			System.out.print(aSignup.getSignup_id() + ",");
//			System.out.print(aSignup.getMem_id() + ",");
//			System.out.print(aSignup.getLc_id() + ",");
//			System.out.print(aSignup.getSignup_pay() + ",");
//			System.out.print(aSignup.getPay_state() + ",");
//			System.out.print(aSignup.getSignup_time() + ",");
//			System.out.print(aSignup.getPay_time() + ",");
//			System.out.println(aSignup.getSign_seat());
//		System.out.println("---------------------");
//		}
//
////		// �d��
//		List<SignupVO> list3 = dao.getAll();
//		for (SignupVO aSignup : list3) {
//			System.out.print(aSignup.getSignup_id() + ",");
//			System.out.print(aSignup.getMem_id() + ",");
//			System.out.print(aSignup.getLc_id() + ",");
//			System.out.print(aSignup.getSignup_pay() + ",");
//			System.out.print(aSignup.getPay_state() + ",");
//			System.out.print(aSignup.getSignup_time() + ",");
//			System.out.print(aSignup.getPay_time() + ",");
//			System.out.println(aSignup.getSign_seat());
//			System.out.println();
//		}
//
//		
//	}
//}
