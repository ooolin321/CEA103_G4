package com.auth.model;

import java.util.*;
import java.sql.*;

public class AuthJDBCDAO implements AuthDAO_interface {

	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/CEA103_G4?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "771414";

	private static final String INSERT_STMT = "insert into Auth (FUNNO,EMPNO,AUTH_NO,STATE) values (?, ?, ?, ?)";
	private static final String UPDATE_STMT = "update Auth set AUTH_NO=?, STATE=? where EMPNO=? and FUNNO=?";
	private static final String DELETE_STMT = "delete from Auth where EMPNO=? and FUNNO=?";
	private static final String GET_ONE_BY_EMPNO_AND_FUNNO_STMT = "select * from Auth where EMPNO = ? and FUNNO=?";
	private static final String GET_ALL_BY_EMPNO_STMT = "select * from Auth order by EMPNO";

	@Override
	public void insert(AuthVO authVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, authVO.getFunno());
			pstmt.setInt(2, authVO.getEmpno());
			pstmt.setInt(3, authVO.getAuth_no());
			

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
	public void update(AuthVO authVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STMT);
			pstmt.setInt(1, authVO.getAuth_no());
			pstmt.setInt(2, authVO.getEmpno());
			pstmt.setInt(3, authVO.getFunno());

			pstmt.executeUpdate();
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
	public void delete(Integer empno, Integer funno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setInt(1, empno);
			pstmt.setInt(2, funno);

			pstmt.executeUpdate();
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
	public AuthVO findByPrimeKey(Integer empno, Integer funno) {
		AuthVO authVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			pstmt = con.prepareStatement(GET_ONE_BY_EMPNO_AND_FUNNO_STMT);

			pstmt.setInt(1, empno);
			pstmt.setInt(2, funno);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// authorityVo 也稱為 Domain objects
				authVO = new AuthVO();
				authVO.setAuth_no(rs.getInt("AUTH_NO"));
				authVO.setEmpno(rs.getInt("EMPNO"));
				authVO.setFunno(rs.getInt("FUNNO"));
			}
			
			
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

		return authVO;
	}

	@Override
	public List<AuthVO> getAll() {
		List<AuthVO> list = new ArrayList<AuthVO>();
		AuthVO authVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_BY_EMPNO_STMT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// authorityVO 也稱為 Domain objects
				authVO = new AuthVO();
				authVO.setEmpno(rs.getInt("EMPNO"));
				authVO.setFunno(rs.getInt("FUNNO"));
				authVO.setAuth_no(rs.getInt("AUTH_NO"));

				list.add(authVO); // Store the row in the list
			}
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

		return list;
	}
	public static void main(String[] args) {

		AuthJDBCDAO dao = new AuthJDBCDAO();

//		// 新增
//		AuthVO authVO1 = new AuthVO();
//		authVO1.setAuth_no(1);;
//		authVO1.setState(1);
//		authVO1.setFunno(14001);
//		authVO1.setEmpno(15001);
//		dao.insert(authVO1);
//
//		 //修改
//		AuthVO authVO2 = new AuthVO();
//		authVO2.setAuth_no(1);;
//		authVO2.setState(0);
//		authVO2.setFunno(14001);
//		authVO2.setEmpno(15001);
//		dao.update(authVO2);

		// 刪除
//		dao.delete(14001,15001);

//		// 查詢
		AuthVO authVO3 = dao.findByPrimeKey(14001,15003);
		System.out.println(authVO3.getFunno());
		System.out.println(authVO3.getEmpno());
		System.out.print(authVO3.getAuth_no() + ",");

		
	

//		// 查詢部門
//		List<DeptVO> list = dao.getAll();
//		for (DeptVO aDept : list) {
//			System.out.print(aDept.getDeptno() + ",");
//			System.out.print(aDept.getDname() + ",");
//			System.out.print(aDept.getLoc());
//			System.out.println();
//		}
//		
//		// 查詢某部門的員工
//		Set<EmpVO> set = dao.getEmpsByDeptno(10);
//		for (EmpVO aEmp : set) {
//			System.out.print(aEmp.getEmpno() + ",");
//			System.out.print(aEmp.getEname() + ",");
//			System.out.print(aEmp.getJob() + ",");
//			System.out.print(aEmp.getHiredate() + ",");
//			System.out.print(aEmp.getSal() + ",");
//			System.out.print(aEmp.getComm() + ",");
//			System.out.print(aEmp.getDeptno());
//			System.out.println();
//		}
	}
}