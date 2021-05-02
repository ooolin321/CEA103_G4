package com.mem.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import tools.jdbcUtil_CompositeQuery_Member;

public class MemDAO implements MemDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bookshop");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO MEMBER (MEM_ID, MEM_ACCOUNT, MEM_PASSWORD, MEM_NAME, MEM_EMAIL, MEM_NICKNAME, "
			+ "MEM_SEX, MEM_BIRTH, MEM_ADDR, MEM_TEL, MEM_PIC) VALUES ('M' || lpad(MEM_SEQ.NEXTVAL, 4, '0'),?,?,?,?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT * FROM MEMBER ORDER BY MEM_ID";
	private static final String GET_ONE_STMT = "SELECT * FROM MEMBER WHERE MEM_ID = ?";
	private static final String DELETE = "DELETE FROM MEMBER WHERE MEM_ID = ?";
	private static final String UPDATE = "UPDATE MEMBER SET MEM_ACCOUNT=?, MEM_PASSWORD=?, MEM_NAME=?, MEM_EMAIL=?, MEM_NICKNAME=?,"
			+ "MEM_SEX=?, MEM_BIRTH=?, MEM_ADDR=?, MEM_TEL=?, MEM_BONUS=?, MEM_PIC=?, MEM_ISKOL=?, MEM_EXP=?, MEM_STATUS=?"
			+ "WHERE MEM_ID=?";
	private static final String UPDATE_PSW = "UPDATE MEMBER SET MEM_PASSWORD=?, MEM_STATUS=0 WHERE MEM_ID=?";
	private static final String SIGNIN = "SELECT mem_id FROM MEMBER WHERE MEM_ACCOUNT = ? and MEM_PASSWORD = ?";
	private static final String CHECK_ACC = "SELECT mem_id FROM MEMBER WHERE MEM_ACCOUNT = ?";
	private static final String CHECK_EMAIL = "SELECT mem_id FROM MEMBER WHERE MEM_EMAIL = ?";
	private static final String UPDATE_BONUS = "UPDATE MEMBER SET MEM_BONUS=? WHERE MEM_ID=?";
	private static final String UPDATE_STATUS_BY_ACCOUNT = "UPDATE MEMBER SET MEM_STATUS=2 WHERE MEM_ACCOUNT=?";//鎖帳號用
	
	@Override
	public MemVO insert(MemVO memVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			
			String cols[] = {"MEM_ID"};
			pstmt = con.prepareStatement(INSERT_STMT, cols);

			pstmt.setString(1, memVO.getMem_account());
			pstmt.setString(2, memVO.getMem_password());
			pstmt.setString(3, memVO.getMem_name());
			pstmt.setString(4, memVO.getMem_email());
			pstmt.setString(5, memVO.getMem_nickname());
			pstmt.setInt(6, memVO.getMem_sex());
			pstmt.setDate(7, memVO.getMem_birth());
			pstmt.setString(8, memVO.getMem_addr());
			pstmt.setString(9, memVO.getMem_tel());
			pstmt.setBytes(10, memVO.getMem_pic());

			pstmt.executeUpdate();
			
			String mem_id = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				mem_id = rs.getString(1);
				memVO.setMem_id(mem_id);
			} else {
				System.out.println("error:沒取到ID");
			}
			rs.close();
			
			

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
		return memVO;
	}

	public void updatePwd(MemVO memVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {	
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_PSW);
			
			pstmt.setString(1, memVO.getMem_password());
			pstmt.setString(2, memVO.getMem_id());

			pstmt.executeUpdate();
			
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
	public void update(MemVO memVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			con.setAutoCommit(false); //可能有交易問題所以 修改預設
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, memVO.getMem_account());
			pstmt.setString(2, memVO.getMem_password());
			pstmt.setString(3, memVO.getMem_name());
			pstmt.setString(4, memVO.getMem_email());
			pstmt.setString(5, memVO.getMem_nickname());
			pstmt.setInt(6, memVO.getMem_sex());
			pstmt.setDate(7, memVO.getMem_birth());
			pstmt.setString(8, memVO.getMem_addr());
			pstmt.setString(9, memVO.getMem_tel());
			pstmt.setDouble(10, memVO.getMem_bonus());
			pstmt.setBytes(11, memVO.getMem_pic());
			pstmt.setInt(12, memVO.getMem_iskol());
			pstmt.setDouble(13, memVO.getMem_exp());
			pstmt.setInt(14, memVO.getMem_status());
			pstmt.setString(15, memVO.getMem_id());

			pstmt.executeUpdate();
			
			con.commit();
			con.setAutoCommit(true);
			
		} catch (SQLException se) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
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
	public void delete(String mem_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, mem_id);
			
			pstmt.executeUpdate();
			
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
	public MemVO findByPrimaryKey(String mem_id) {
		MemVO memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, mem_id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				memVO = new MemVO();
				memVO.setMem_id(rs.getString("mem_id"));
				memVO.setMem_account(rs.getString("mem_account"));
				memVO.setMem_password(rs.getString("mem_password"));
				memVO.setMem_name(rs.getString("mem_name"));
				memVO.setMem_email(rs.getString("mem_email"));
				memVO.setMem_nickname(rs.getString("mem_nickname"));
				memVO.setMem_sex(rs.getInt("mem_sex"));
				memVO.setMem_birth(rs.getDate("mem_birth"));
				memVO.setMem_addr(rs.getString("mem_addr"));
				memVO.setMem_tel(rs.getString("mem_tel"));
				memVO.setMem_bonus(rs.getDouble("mem_bonus"));
				memVO.setMem_pic(rs.getBytes("mem_pic"));
				memVO.setMem_iskol(rs.getInt("mem_iskol"));
				memVO.setMem_exp(rs.getDouble("mem_exp"));
				memVO.setMem_status(rs.getInt("mem_status"));
				memVO.setMem_cretime(rs.getTimestamp("mem_cretime"));
			}
			
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
		return memVO;
	}

	@Override
	public List<MemVO> getAll() {
		List<MemVO> list = new ArrayList<MemVO>();
		MemVO memVO = null;
		
		Connection con =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				memVO = new MemVO();
				memVO.setMem_id(rs.getString("mem_id"));
				memVO.setMem_account(rs.getString("mem_account"));
				memVO.setMem_password(rs.getString("mem_password"));
				memVO.setMem_name(rs.getString("mem_name"));
				memVO.setMem_email(rs.getString("mem_email"));
				memVO.setMem_nickname(rs.getString("mem_nickname"));
				memVO.setMem_sex(rs.getInt("mem_sex"));
				memVO.setMem_birth(rs.getDate("mem_birth"));
				memVO.setMem_addr(rs.getString("mem_addr"));
				memVO.setMem_tel(rs.getString("mem_tel"));
				memVO.setMem_bonus(rs.getDouble("mem_bonus"));
				memVO.setMem_pic(rs.getBytes("mem_pic"));
				memVO.setMem_iskol(rs.getInt("mem_iskol"));
				memVO.setMem_exp(rs.getDouble("mem_exp"));
				memVO.setMem_status(rs.getInt("mem_status"));
				memVO.setMem_cretime(rs.getTimestamp("mem_cretime"));
				list.add(memVO);
			}
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

	public MemVO signIn(String mem_account, String mem_password) {
		MemVO memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SIGNIN);

			pstmt.setString(1, mem_account);
			pstmt.setString(2, mem_password);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				memVO = new MemVO();
				memVO.setMem_id(rs.getString("mem_id"));
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
		return memVO;
	}

	@Override
	public boolean checkAcc(String mem_account) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CHECK_ACC);

			pstmt.setString(1, mem_account);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				flag = true;
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
		return flag;
	}

	@Override
	public boolean checkEmail(String mem_email) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CHECK_EMAIL);

			pstmt.setString(1, mem_email);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				flag = true;
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
		return flag;
	}

	@Override
	public String getMemIdByMail(String mem_email) {
		MemVO memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CHECK_EMAIL);

			pstmt.setString(1, mem_email);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				memVO = new MemVO();
				memVO.setMem_id(rs.getString("mem_id"));
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
		return memVO.getMem_id();
	}

	@Override
	public List<MemVO> getAll(Map<String, String[]> map) {
		List<MemVO> list = new ArrayList<MemVO>();
		MemVO memVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			String finalSQL = "select * from member "
			          + jdbcUtil_CompositeQuery_Member.get_WhereCondition(map)
			          + "order by mem_id";
			pstmt = con.prepareStatement(finalSQL);	
//			System.out.println(finalSQL);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				memVO = new MemVO();
				memVO.setMem_id(rs.getString("mem_id"));
				memVO.setMem_account(rs.getString("mem_account"));
				memVO.setMem_password(rs.getString("mem_password"));
				memVO.setMem_name(rs.getString("mem_name"));
				memVO.setMem_email(rs.getString("mem_email"));
				memVO.setMem_nickname(rs.getString("mem_nickname"));
				memVO.setMem_sex(rs.getInt("mem_sex"));
				memVO.setMem_birth(rs.getDate("mem_birth"));
				memVO.setMem_addr(rs.getString("mem_addr"));
				memVO.setMem_tel(rs.getString("mem_tel"));
				memVO.setMem_bonus(rs.getDouble("mem_bonus"));
				memVO.setMem_pic(rs.getBytes("mem_pic"));
				memVO.setMem_iskol(rs.getInt("mem_iskol"));
				memVO.setMem_exp(rs.getDouble("mem_exp"));
				memVO.setMem_status(rs.getInt("mem_status"));
				memVO.setMem_cretime(rs.getTimestamp("mem_cretime"));
				list.add(memVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void updateBonus(String mem_id, Double mem_bonus, Connection con) { //訂單或取消時產生時，同時觸發的方法
		
		PreparedStatement pstmt = null;

		try {

     		pstmt = con.prepareStatement(UPDATE_BONUS);

			pstmt.setDouble(1, mem_bonus);
			pstmt.setString(2, mem_id);
			

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void updateStatusByAccoutn(String mem_account) { //登入頁面時使用
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(UPDATE_STATUS_BY_ACCOUNT);

			pstmt.setString(1, mem_account);
		
			pstmt.executeUpdate();	

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
}
