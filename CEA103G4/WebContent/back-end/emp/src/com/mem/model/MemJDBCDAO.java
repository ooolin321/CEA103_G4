package com.mem.model;

import java.util.*;
import java.sql.*;
import java.text.Format;
import java.text.SimpleDateFormat;

public class MemJDBCDAO implements MemDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "BOOKSHOP";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO MEMBER (MEM_ID, MEM_ACCOUNT, MEM_PASSWORD, MEM_NAME, MEM_EMAIL, MEM_NICKNAME, "
			+ "MEM_SEX, MEM_BIRTH, MEM_ADDR, MEM_TEL, MEM_PIC) VALUES ('M' || lpad(MEM_SEQ.NEXTVAL, 4, '0'),?,?,?,?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT * FROM MEMBER ORDER BY MEM_ID";
	private static final String GET_ONE_STMT = "SELECT * FROM MEMBER WHERE MEM_ID = ?";
	private static final String DELETE = "DELETE FROM MEMBER WHERE MEM_ID = ?";
	private static final String UPDATE = "UPDATE MEMBER SET MEM_ACCOUNT=?, MEM_PASSWORD=?, MEM_NAME=?, MEM_EMAIL=?, MEM_NICKNAME=?,"
			+ "MEM_SEX=?, MEM_BIRTH=?, MEM_ADDR=?, MEM_TEL=?, MEM_bonus=?, MEM_PIC=?, MEM_ISKOL=?, MEM_EXP=?, MEM_STATUS=?"
			+ "WHERE MEM_ID=?";
	private static final String UPDATE_PSW = "UPDATE MEMBER SET MEM_PASSWORD=? WHERE MEM_ID=?";
	private static final String SIGNIN = "SELECT mem_id FROM MEMBER WHERE MEM_ACCOUNT = ? and MEM_PASSWORD = ?";
	private static final String CHECK_ACC = "SELECT mem_id FROM MEMBER WHERE MEM_ACCOUNT = ?";
	private static final String CHECK_EMAIL = "SELECT mem_id FROM MEMBER WHERE MEM_EMAIL = ?";
	
	@Override
	public MemVO insert(MemVO memVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		return memVO;
	}

	@Override
	public void update(MemVO memVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	public void delete(String mem_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, mem_id);

			pstmt.executeUpdate();

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
	public MemVO findByPrimaryKey(String mem_id) {
		MemVO memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, mem_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				memVO = new MemVO();
				memVO.setMem_id(mem_id);
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
		return memVO;
	}

	@Override
	public List<MemVO> getAll() {
		List<MemVO> list = new ArrayList<MemVO>();
		MemVO memVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
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

	@Override
	public void updatePwd(MemVO memVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_PSW);

			pstmt.setString(1, memVO.getMem_password());
			pstmt.setString(2, memVO.getMem_id());

			pstmt.executeUpdate();
			
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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

	public MemVO signIn(String mem_account, String mem_password) {
		MemVO memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SIGNIN);

			pstmt.setString(1, mem_account);
			pstmt.setString(2, mem_password);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				memVO = new MemVO();
				memVO.setMem_id(rs.getString("mem_id"));
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
		return memVO;
	}
	
	public static void main(String[] args) {
		MemJDBCDAO dao = new MemJDBCDAO();

//		MemVO memVO1 = new MemVO();
//		memVO1.setMem_account("hello7788");
//		memVO1.setMem_password("000000");
//		memVO1.setMem_name("涂書館");
//		memVO1.setMem_email("maomao@gmail.com");
//		memVO1.setMem_nickname("阿涂");
//		memVO1.setMem_sex(1);
//		memVO1.setMem_birth(java.sql.Date.valueOf("1996-10-10"));
//		memVO1.setMem_addr("台北市信義區");
//		memVO1.setMem_tel("0987654321");
//
//		dao.insert(memVO1);
//		System.out.println("新增成功");

//		MemVO memVO2 = new MemVO();
//		memVO2.setMem_id("M0002");
//		memVO2.setMem_account("GFHDFH");
//		memVO2.setMem_password("56465");
//		memVO2.setMem_name("董董董");
//		memVO2.setMem_email("GGGGGG@gmail.com");
//		memVO2.setMem_nickname("deeppink");
//		memVO2.setMem_sex(1);
//		memVO2.setMem_birth(java.sql.Date.valueOf("1980-10-10"));
//		memVO2.setMem_addr("這我就不知道了");
//		memVO2.setMem_tel("0987654321");
//		memVO2.setMem_bonus(new Double(500));
//		memVO2.setMem_pic(null);
//		memVO2.setMem_iskol(1);
//		memVO2.setMem_exp(new Double(0));
//		dao.update(memVO2);
//		System.out.println("修改成功");

//		dao.delete("M0001");

//		MemVO memVO3 = dao.findByPrimaryKey("M0001");
//		System.out.println(memVO3.getMem_id());
//		System.out.println(memVO3.getMem_account());
//		System.out.println(memVO3.getMem_password());
//		System.out.println(memVO3.getMem_name());
//		System.out.println(memVO3.getMem_email());
//		System.out.println(memVO3.getMem_nickname());
//		System.out.println(memVO3.getMem_sex());
//		System.out.println(memVO3.getMem_birth());
//		System.out.println(memVO3.getMem_addr());
//		System.out.println(memVO3.getMem_tel());
//		System.out.println(memVO3.getMem_bonus());
//		System.out.println(memVO3.getMem_pic());
//		System.out.println(memVO3.getMem_iskol());
//		System.out.println(memVO3.getMem_exp());
//
//		Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		System.out.println(f.format(memVO3.getMem_cretime().getTime()));

//		List<MemVO> list = dao.getAll();
//		for(MemVO aMem : list) {
//			System.out.print(aMem.getMem_id()+ ",");
//			System.out.print(aMem.getMem_account()+ ",");
//			System.out.print(aMem.getMem_password()+ ",");
//			System.out.print(aMem.getMem_name()+ ",");
//			System.out.print(aMem.getMem_email()+ ",");
//			System.out.print(aMem.getMem_nickname()+ ",");
//			System.out.print(aMem.getMem_sex()+ ",");
//			System.out.print(aMem.getMem_birth()+ ",");
//			System.out.print(aMem.getMem_addr()+ ",");
//			System.out.print(aMem.getMem_tel()+ ",");
//			System.out.print(aMem.getMem_bonus()+ ",");
//			System.out.print(aMem.getMem_pic()+ ",");
//			System.out.print(aMem.getMem_iskol()+ ",");
//			System.out.print(aMem.getMem_exp()+ ",");
//			System.out.println();
//		}

//		MemVO memVO4 = new MemVO();
//		memVO4.setMem_password("123");
//		memVO4.setMem_id("M0001");
//		dao.update_psw(memVO4);
		
//		MemVO memVO5 = dao.signIn("joel199583", "199583");
//		System.out.println(memVO5.getMem_id());
		
//		System.out.println(dao.checkAcc("seafood"));
		
		
//		String mem_email = dao.checkEmail("seafood@gmail.com");
//		System.out.println(mem_email);
	}

	@Override
	public boolean checkAcc(String mem_account) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(CHECK_ACC);

			pstmt.setString(1, mem_account);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				flag = true;
			} 
			
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());	
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(CHECK_EMAIL);

			pstmt.setString(1, mem_email);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				flag = true;
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());	
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MemVO> getAll(Map<String, String[]> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateBonus(String mem_id, Double mem_bonus, Connection con) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateStatusByAccoutn(String mem_account) {
		// TODO Auto-generated method stub
		
	}

}
