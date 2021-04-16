package com.emp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class EmpDAO implements EmpDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CEA103_G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = 
			"INSERT INTO EMP (ENAME,JOB,ID,GENDER,DOB,ADDR,EMAIL,SAL,STATE,HIREDATE,EMP_PWD) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
			"SELECT EMPNO,ENAME,JOB,ID,GENDER,DOB,ADDR,EMAIL,SAL,STATE,HIREDATE,EMP_PWD FROM EMP ORDER BY EMPNO";
	private static final String GET_ONE_STMT = 
			"SELECT EMPNO,ENAME,JOB,ID,GENDER,DOB,ADDR,EMAIL,SAL,STATE,HIREDATE,EMP_PWD FROM EMP WHERE EMPNO = ?";
	private static final String DELETE = 
			"DELETE FROM EMP WHERE EMPNO = ?";
	private static final String UPDATE = 
			"UPDATE EMP SET ENAME=?, JOB=?, ID=?, GENDER=?, DOB=?, ADDR=?,EMAIL=?, SAL=?, STATE=?, HIREDATE=?, EMP_PWD=? WHERE EMPNO = ?";

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
			pstmt.setInt(4, empVO.getGender());
			pstmt.setDate(5, empVO.getDob());
			pstmt.setString(6, empVO.getAddr());
			pstmt.setString(7, empVO.getEmail());
			pstmt.setDouble(8, empVO.getSal());
			pstmt.setInt(9, empVO.getState());
			pstmt.setDate(10, empVO.getHiredate());
			pstmt.setString(11, empVO.getEmp_pwd());


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
			pstmt.setInt(4, empVO.getGender());
			pstmt.setDate(5, empVO.getDob());
			pstmt.setString(6, empVO.getAddr());
			pstmt.setString(7, empVO.getEmail());
			pstmt.setDouble(8, empVO.getSal());
			pstmt.setInt(9, empVO.getState());
			pstmt.setDate(10, empVO.getHiredate());
			pstmt.setString(11, empVO.getEmp_pwd());
			pstmt.setInt(12, empVO.getEmpno());
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
				empVO.setGender(rs.getInt("gender"));
				empVO.setDob(rs.getDate("dob"));
				empVO.setAddr(rs.getString("addr"));
				empVO.setEmail(rs.getString("email"));
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
				empVO.setGender(rs.getInt("gender"));
				empVO.setDob(rs.getDate("dob"));
				empVO.setAddr(rs.getString("addr"));
				empVO.setEmail(rs.getString("email"));
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
	
//	@Override
//	public String GenAuthCode() {
//		EmpVO empVO = new EmpVO();
//		String empPwd = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";//儲存數字0-9 和 大小寫字母
//		StringBuffer sb = new StringBuffer(); //宣告一個StringBuffer物件sb 儲存 驗證碼
//		for (int i = 0; i < 8; i++) {
//			Random random = new Random();//建立一個新的隨機數生成器
//			int index = random.nextInt(empPwd.length());//返回[0,string.length)範圍的int值    作用：儲存下標
//			char ch = empPwd.charAt(index);//charAt() : 返回指定索引處的 char 值   ==》賦值給char字元物件ch
//		 sb.append(ch);// append(char c) :將 char 引數的字串表示形式追加到此序列  ==》即將每次獲取的ch值作拼接
//		}return sb.toString();		
//	}
}
