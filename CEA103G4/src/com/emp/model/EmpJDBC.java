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
	}

	@Override
	public void insert(EmpVO empVO) {
		// TODO Auto-generated method stub
		
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

