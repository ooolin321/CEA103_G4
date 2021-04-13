package com.emp.model;

import java.util.*;
import java.sql.*;

public class EmpJDBCDAO implements EmpDAO_interface {
//	String driver = "com.mysql.cj.jdbc.Driver";
//	String url = "jdbc:mysql://localhost:3306/CEA103_G4?serverTimezone=Asia/Taipei";
//	String userid = "root";
//	String passwd = "771414";
	
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://database-1.canq3t4lp2za.ap-northeast-1.rds.amazonaws.com:3306/CEA103_G4?serverTimezone=Asia/Taipei";
	String userid = "admin";
	String passwd = "12345678";

	private static final String INSERT_STMT = 
			"INSERT INTO EMP (ENAME,JOB,ID,GENDER,DOB,ADDR,SAL,STATE,HIREDATE,EMP_PWD) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
			"SELECT EMPNO,ENAME,JOB,ID,GENDER,DOB,ADDR,SAL,STATE,HIREDATE,EMP_PWD FROM EMP ORDER BY EMPNO";
	private static final String GET_ONE_STMT = 
			"SELECT * FROM EMP WHERE EMPNO = ?";
	private static final String DELETE = 
			"DELETE FROM EMP WHERE EMPNO = ?";
	private static final String UPDATE = 
			"UPDATE EMP SET ENAME=?, JOB=?, ID=?, GENDER=?, DOB=?, ADDR=?, SAL=?, STATE=?, HIREDATE=?, EMP_PWD=? WHERE EMPNO = ?";

	@Override
	public void insert(EmpVO empVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, empVO.getEname());
			pstmt.setString(2, empVO.getJob());
			pstmt.setString(3, empVO.getId());
			pstmt.setString(4, empVO.getGender());
			pstmt.setDate(5, empVO.getDob());
			pstmt.setString(6, empVO.getAddr());
			pstmt.setDouble(7, empVO.getSal());
			pstmt.setInt(8, empVO.getState());
			pstmt.setDate(9, empVO.getHiredate());
			pstmt.setString(10, empVO.getEmp_pwd());

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
	public void update(EmpVO empVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, empVO.getEname());
			pstmt.setString(2, empVO.getJob());
			pstmt.setString(3, empVO.getId());
			pstmt.setString(4, empVO.getGender());
			pstmt.setDate(5, empVO.getDob());
			pstmt.setString(6, empVO.getAddr());
			pstmt.setDouble(7, empVO.getSal());
			pstmt.setInt(8, empVO.getState());
			pstmt.setDate(9, empVO.getHiredate());
			pstmt.setString(10, empVO.getEmp_pwd());
			pstmt.setInt(11, empVO.getEmpno());
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
	public void delete(Integer empno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, empno);

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
	public EmpVO findByPrimaryKey(Integer empno) {

		EmpVO empVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, empno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				empVO = new EmpVO();
				empVO.setEmpno(rs.getInt("empno"));
				empVO.setEname(rs.getString("ename"));
				empVO.setJob(rs.getString("job"));
				empVO.setId(rs.getString("id"));
				empVO.setGender(rs.getString("gender"));
				empVO.setDob(rs.getDate("dob"));
				empVO.setAddr(rs.getString("addr"));
				empVO.setSal(rs.getDouble("sal"));
				empVO.setState(rs.getInt("state"));
				empVO.setHiredate(rs.getDate("hiredate"));
				empVO.setEmp_pwd(rs.getString("emp_pwd"));
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
		return empVO;
	}

	@Override
	public List<EmpVO> getAll() {
		List<EmpVO> list = new ArrayList<EmpVO>();
		EmpVO empVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				empVO = new EmpVO();
				empVO.setEmpno(rs.getInt("empno"));
				empVO.setEname(rs.getString("ename"));
				empVO.setJob(rs.getString("job"));
				empVO.setId(rs.getString("id"));
				empVO.setGender(rs.getString("gender"));
				empVO.setDob(rs.getDate("dob"));
				empVO.setAddr(rs.getString("addr"));
				empVO.setSal(rs.getDouble("sal"));
				empVO.setState(rs.getInt("state"));
				empVO.setHiredate(rs.getDate("hiredate"));
				empVO.setEmp_pwd(rs.getString("emp_pwd"));
				list.add(empVO); // Store the row in the list
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

	public static void main(String[] args) {

		EmpJDBCDAO dao = new EmpJDBCDAO();

		// �s�W
		EmpVO empVO1 = new EmpVO();
		empVO1.setEname("PETER1");
		empVO1.setJob("MANAGER");
		empVO1.setId("A12345678");
		empVO1.setGender("M");
		empVO1.setDob(java.sql.Date.valueOf("1999-01-01"));
		empVO1.setAddr("台中市台灣大道");
		empVO1.setSal(new Double(50000));
		empVO1.setState(1);
		empVO1.setHiredate(java.sql.Date.valueOf("2005-01-01"));
		empVO1.setEmp_pwd("a123456");
		dao.insert(empVO1);

		// �ק�
		EmpVO empVO2 = new EmpVO();
		empVO2.setEmpno(14006);
		empVO2.setEname("PETER1");
		empVO2.setJob("MANAGER");
		empVO2.setId("A12345678");
		empVO2.setGender("M");
		empVO2.setDob(java.sql.Date.valueOf("1999-01-01"));
		empVO2.setAddr("台中市台灣大道");
		empVO2.setSal(new Double(500));
		empVO2.setState(1);
		empVO2.setHiredate(java.sql.Date.valueOf("2005-01-01"));
		empVO2.setEmp_pwd("a123456");
		dao.update(empVO2);

		// �R��
		dao.delete(14007);

		// �d��
		EmpVO empVO3 = dao.findByPrimaryKey(14001);
		System.out.print(empVO3.getEmpno() + ",");
		System.out.print(empVO3.getEname() + ",");
		System.out.print(empVO3.getJob() + ",");
		System.out.println(empVO3.getId() +",");
		System.out.println(empVO3.getGender()+",");
		System.out.println(empVO3.getDob()+",");
		System.out.print(empVO3.getAddr() + ",");
		System.out.print(empVO3.getSal() + ",");
		System.out.println(empVO3.getState()+",");
		System.out.print(empVO3.getHiredate() + ",");
		System.out.println(empVO3.getEmp_pwd());
		System.out.println("---------------------");

		// �d��
		List<EmpVO> list = dao.getAll();
		for (EmpVO aEmp : list) {
			System.out.print(aEmp.getEmpno() + ",");
			System.out.print(aEmp.getEname() + ",");
			System.out.print(aEmp.getJob() + ",");
			System.out.println(aEmp.getId() +",");
			System.out.println(aEmp.getGender()+",");
			System.out.println(aEmp.getDob()+",");
			System.out.print(aEmp.getAddr() + ",");
			System.out.print(aEmp.getSal() + ",");
			System.out.println(aEmp.getState()+",");
			System.out.print(aEmp.getHiredate() + ",");
			System.out.println(aEmp.getEmp_pwd());
			System.out.println();
		}
	}
}