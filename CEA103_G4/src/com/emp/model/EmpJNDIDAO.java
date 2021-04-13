package com.emp.model;

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

public class EmpJNDIDAO implements EmpDAO_interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CEA103_G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "INSERT INTO EMP (ENAME,JOB,ID,GENDER,DOB,ADDR,SAL,STATE,HIREDATE,EMP_PWD) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT EMPNO,ENAME,JOB,ID,GENDER,DOB,ADDR,SAL,STATE,HIREDATE,EMP_PWD FROM EMP ORDER BY EMPNO";
	private static final String GET_ONE_STMT = "SELECT EMPNO,ENAME,JOB,ID,GENDER,DOB,ADDR,SAL,STATE,HIREDATE,EMP_PWD  FROM EMP WHERE EMPNO = ?";
	private static final String DELETE = "DELETE FROM EMP WHERE EMPNO = ?";
	private static final String UPDATE = "UPDATE EMP SET ENAME=?, JOB=?, ID=?, GENDER=?, DOB=?, ADDR=?, SAL=?, STATE=?, HIREDATE=?, EMP_PWD=? WHERE EMPNO = ?";

	@Override
	public void insert(EmpVO empVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
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

		} catch (SQLException se) {
			throw new RuntimeException("database發生錯誤." + se.getMessage());
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
			con = ds.getConnection();
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

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("database發生錯誤." + se.getMessage());
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
	public void delete (Integer empno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, empno);

			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("database發生錯誤." + se.getMessage());
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, empno);

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
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("database發生錯誤."
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
	public List<EmpVO> getAll()  {
		List<EmpVO> list = new ArrayList<EmpVO>();
		EmpVO empVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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
		} catch (SQLException se) {
			throw new RuntimeException("database發生錯誤."
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
}
