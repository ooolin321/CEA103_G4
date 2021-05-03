package com.emp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public  class EmpJDBC implements EmpDAO_interface{
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/CEA103_G4?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "771414";
	
	private static final String SIGN_IN = "SELECT EMPNO,EMP_PWD,ENAME FROM EMP where EMPNO=? AND EMP_PWD=?";
	private static final String INSERT_STMT = "INSERT INTO EMP (EMPNO,ENAME,JOB,ID,GENDER,DOB,CITY,DIST,ADDR,EMAIL,SAL,STATE,HIREDATE,EMP_PWD) VALUES (null,? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
	@Override
	public EmpVO login(Integer empno, String empPwd) {
		EmpVO empVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SIGN_IN);
				
			pstmt.setInt(1, empno);
			pstmt.setString(2, empPwd);	
			
			rs = pstmt.executeQuery();
			while (rs.next()) {

				empVO = new EmpVO();
				empVO.setEmpno(rs.getInt("empno"));
				empVO.setEname(rs.getString("ename"));
				empVO.setEmp_pwd(rs.getString("emp_pwd"));
			}

		} catch (SQLException se) {
			throw new RuntimeException("database發生錯誤." + se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public static void main(String[] args) {
		EmpJDBC dao2 = new EmpJDBC();
		EmpVO empVO = dao2.login(14001, "a1111111");
		System.out.println(empVO.getEname());
//EMPNO,ENAME,JOB,ID,GENDER,DOB,CITY,DIST,ADDR,EMAIL,SAL,STATE,HIREDATE,EMP_PWD		
		EmpVO empVO2 = new EmpVO();
		empVO2.setEname("DaoEname");
		empVO2.setJob("DaoJob");
		empVO2.setGender(1);
		empVO2.setDob(java.sql.Date.valueOf("1999-09-09"));
		empVO2.setCity("桃園市");
		empVO2.setDist("中壢區");
		empVO2.setAddr("DaoAddr");
		empVO2.setEmail("feng.school@gmail.com");
		empVO2.setSal(new Double("666"));
		empVO2.setState(new Integer("1"));
		empVO2.setHiredate(java.sql.Date.valueOf("2000-02-02"));
		empVO2.setEmp_pwd("DadEmpwd");
		dao2.insert(empVO2);
		System.out.println(dao2);
	}

	@Override
	public Object insert(EmpVO empVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT,PreparedStatement.RETURN_GENERATED_KEYS);
			
//			pstmt.setString(2, empVO.getEname());
//			pstmt.setString(3, empVO.getJob());
//			pstmt.setString(4, empVO.getId());
//			pstmt.setInt(5, empVO.getGender());
//			pstmt.setDate(6, empVO.getDob());
//			pstmt.setString(7, empVO.getCity());
//			pstmt.setString(8, empVO.getDist());
//			pstmt.setString(9, empVO.getAddr());
//			pstmt.setString(10, empVO.getEmail());
//			pstmt.setDouble(11, empVO.getSal());
//			pstmt.setInt(12, empVO.getState());
//			pstmt.setDate(13, empVO.getHiredate());
//			pstmt.setString(14, empVO.getEmp_pwd());
			
			pstmt.setString(1, empVO.getEname());
			pstmt.setString(2, empVO.getJob());
			pstmt.setString(3, empVO.getId());
			pstmt.setInt(4, empVO.getGender());
			pstmt.setDate(5, empVO.getDob());
			pstmt.setString(6, empVO.getCity());
			pstmt.setString(7, empVO.getDist());
			pstmt.setString(8, empVO.getAddr());
			pstmt.setString(9, empVO.getEmail());
			pstmt.setDouble(10, empVO.getSal());
			pstmt.setInt(11, empVO.getState());
			pstmt.setDate(12, empVO.getHiredate());
			pstmt.setString(13, empVO.getEmp_pwd());

			pstmt.executeUpdate();
			
			 rs = pstmt.getGeneratedKeys();
			rs.next();
			Integer empno = rs.getInt(1);
			empVO.setEmpno(empno);
			System.out.println("JDBC empno = "+empno);

		} catch (SQLException se) {
			throw new RuntimeException("database發生錯誤." + se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		}return empVO;
		
	}

	@Override
	public void update(EmpVO empVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer empno) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EmpVO findByPrimaryKey(Integer empno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpVO> sendMail(EmpVO empVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String genAuthCode() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}

