package com.bookclub.model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;



public class BookClubJDBCDAO implements BookClubDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "BOOKSHOP";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO BOOKCLUB (BC_ID,MEM_ID,BC_NAME,BC_PLACE,BC_TIME_START,BC_TIME_END,BC_PEO_UPPER_LIMIT,BC_PEO_LOWER_LIMIT,BC_INTRO,BC_COVER_PIC,BC_COMFIRM_PEO,BC_STATUS,BC_INIT,BC_DEADLINE,BC_CREATE_TIME)VALUES ('BC'||TO_CHAR(BC_SEQ.NEXTVAL, 'FM0000'),'M0001',?,?,?,?,?,?,?,?,DEFAULT,DEFAULT,?,?,DEFAULT)";
	private static final String GET_ALL_STMT = "SELECT * FROM BOOKCLUB ORDER BY BC_ID";
	private static final String SELECT_BY_PK = "SELECT * FROM BOOKCLUB WHERE BC_ID = ?";
	private static final String DELETE = "DELETE FROM BOOKCLUB WHERE BC_ID = ?";
	private static final String UPDATE = "UPDATE BOOKCLUB SET BC_NAME = ?, BC_PLACE = ?, BC_TIME_START = ?, BC_TIME_END = ?, BC_PEO_UPPER_LIMIT = ?,BC_PEO_LOWER_LIMIT = ?,BC_INTRO = ?,BC_COVER_PIC = ?,BC_INIT = ?,BC_DEADLINE = ? WHERE BC_ID = ?";
	private static final String UPDATE_STATUS = "UPDATE BOOKCLUB SET BC_STATUS = 2 WHERE BC_ID = ?";
	private static final String SELECT_BY_STATUS = "SELECT * FROM BOOKCLUB WHERE BC_STATUS = 1";
	private static final String SELECT_BY_BLURRY = "SELECT * FROM BOOKCLUB WHERE BC_NAME LIKE '%' || ? || '%' and BC_STATUS = 1 ";

	public String insert(BookClubVO bookClubVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String bc_id = "";
		String[] pk = {"BC_ID"};
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT,pk);

//			pstmt.setString(1, bookClubsVO.getMem_id()); // 取session的值,目前寫死
			pstmt.setString(1, bookClubVO.getBc_name());
			pstmt.setString(2, bookClubVO.getBc_place());
			pstmt.setTimestamp(3, bookClubVO.getBc_time_start());
			pstmt.setTimestamp(4, bookClubVO.getBc_time_end());
			pstmt.setInt(5, bookClubVO.getBc_peo_upper_limit());
			pstmt.setInt(6, bookClubVO.getBc_peo_lower_limit());
			pstmt.setString(7, bookClubVO.getBc_intro());
			pstmt.setBytes(8, bookClubVO.getBc_cover_pic());
			pstmt.setDate(9, bookClubVO.getBc_init());
			pstmt.setDate(10, bookClubVO.getBc_deadline());

			pstmt.executeUpdate();
			System.out.println("新增成功 insert method");
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				bc_id = rs.getString(1);
			}

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
		return bc_id;
	}

	public void update(BookClubVO bookClubVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, bookClubVO.getBc_name());
			pstmt.setString(2, bookClubVO.getBc_place());
			pstmt.setTimestamp(3, bookClubVO.getBc_time_start());
			pstmt.setTimestamp(4, bookClubVO.getBc_time_end());
			pstmt.setInt(5, bookClubVO.getBc_peo_upper_limit());
			pstmt.setInt(6, bookClubVO.getBc_peo_lower_limit());
			pstmt.setString(7, bookClubVO.getBc_intro());
			pstmt.setBytes(8, bookClubVO.getBc_cover_pic());
			pstmt.setDate(9, bookClubVO.getBc_init());
			pstmt.setDate(10, bookClubVO.getBc_deadline());
			pstmt.setString(11, bookClubVO.getBc_id());

			pstmt.executeUpdate();
			System.out.println("更新成功 update method");

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
	public void delete(String bc_id) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, bc_id);
			
			pstmt.executeUpdate();
			System.out.println("刪除成功 delete method");
			
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (Exception se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public BookClubVO findByPrimaryKey(String bc_id) {
		BookClubVO bookClubVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_BY_PK);
			
			pstmt.setString(1, bc_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bookClubVO = new BookClubVO();
				bookClubVO.setBc_id(rs.getString("bc_id"));
				bookClubVO.setMem_id(rs.getString("mem_id"));
				bookClubVO.setBc_name(rs.getString("bc_name"));
				bookClubVO.setBc_place(rs.getString("bc_place"));
				bookClubVO.setBc_time_start(rs.getTimestamp("bc_time_start"));
				bookClubVO.setBc_time_end(rs.getTimestamp("bc_time_end"));
				bookClubVO.setBc_peo_upper_limit(rs.getInt("bc_peo_upper_limit"));
				bookClubVO.setBc_peo_lower_limit(rs.getInt("bc_peo_lower_limit"));
				bookClubVO.setBc_intro(rs.getString("bc_intro"));
				bookClubVO.setBc_cover_pic(rs.getBytes("bc_cover_pic"));
				bookClubVO.setBc_comfirm_peo(rs.getInt("bc_comfirm_peo"));
				bookClubVO.setBc_status(rs.getInt("bc_status"));
				bookClubVO.setBc_init(rs.getDate("bc_init"));
				bookClubVO.setBc_deadline(rs.getDate("bc_deadline"));
				bookClubVO.setBc_create_time(rs.getTimestamp("bc_create_time"));
			}
		}  catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

		return bookClubVO;
	}

	@Override
	public List<BookClubVO> getAll() {
		List<BookClubVO> list = new ArrayList<BookClubVO>();
		BookClubVO bookClubVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				bookClubVO = new BookClubVO();
				bookClubVO.setBc_id(rs.getString("bc_id"));
				bookClubVO.setMem_id(rs.getString("mem_id"));
				bookClubVO.setBc_name(rs.getString("bc_name"));
				bookClubVO.setBc_place(rs.getString("bc_place"));
				bookClubVO.setBc_time_start(rs.getTimestamp("bc_time_start"));
				bookClubVO.setBc_time_end(rs.getTimestamp("bc_time_end"));
				bookClubVO.setBc_peo_upper_limit(rs.getInt("bc_peo_upper_limit"));
				bookClubVO.setBc_peo_lower_limit(rs.getInt("bc_peo_lower_limit"));
				bookClubVO.setBc_intro(rs.getString("bc_intro"));
				bookClubVO.setBc_cover_pic(rs.getBytes("bc_cover_pic"));
				bookClubVO.setBc_comfirm_peo(rs.getInt("bc_comfirm_peo"));
				bookClubVO.setBc_status(rs.getInt("bc_status"));
				bookClubVO.setBc_init(rs.getDate("bc_init"));
				bookClubVO.setBc_deadline(rs.getDate("bc_deadline"));
				bookClubVO.setBc_create_time(rs.getTimestamp("bc_create_time"));
				list.add(bookClubVO); // Store the row in the list
			}
		}  catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public List<BookClubVO> findByStatus() {
		
			List<BookClubVO> list = new ArrayList<BookClubVO>();
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			BookClubVO bookClubVO = null;
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(SELECT_BY_STATUS);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					bookClubVO = new BookClubVO();
					bookClubVO.setBc_id(rs.getString("bc_id"));
					bookClubVO.setMem_id(rs.getString("mem_id"));
					bookClubVO.setBc_name(rs.getString("bc_name"));
					bookClubVO.setBc_place(rs.getString("bc_place"));
					bookClubVO.setBc_time_start(rs.getTimestamp("bc_time_start"));
					bookClubVO.setBc_time_end(rs.getTimestamp("bc_time_end"));
					bookClubVO.setBc_peo_upper_limit(rs.getInt("bc_peo_upper_limit"));
					bookClubVO.setBc_peo_lower_limit(rs.getInt("bc_peo_lower_limit"));
					bookClubVO.setBc_intro(rs.getString("bc_intro"));
					bookClubVO.setBc_cover_pic(rs.getBytes("bc_cover_pic"));
					bookClubVO.setBc_comfirm_peo(rs.getInt("bc_comfirm_peo"));
					bookClubVO.setBc_status(rs.getInt("bc_status"));
					bookClubVO.setBc_init(rs.getDate("bc_init"));
					bookClubVO.setBc_deadline(rs.getDate("bc_deadline"));
					bookClubVO.setBc_create_time(rs.getTimestamp("bc_create_time"));
					list.add(bookClubVO);
				}
			}  catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. "
						+ e.getMessage());
			} catch (SQLException e) {
				throw new RuntimeException("A database error occured. "
						+ e.getMessage());
			} finally {
				if(rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if(pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if(con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			return list;
		}
	

	@Override
	public void updateStatus(String bc_id,Integer status) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STATUS);

			pstmt.setString(1, bc_id);

			pstmt.executeUpdate();
			System.out.println("更新狀態成功(updateStatus method)");
			// 修改狀態成功
		}  catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public List<BookClubVO> findByBlurry(String blurry) {
		List<BookClubVO> list = new ArrayList<BookClubVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BookClubVO bookClubVO = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SELECT_BY_BLURRY);
			pstmt.setString(1, blurry);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				bookClubVO = new BookClubVO();
				bookClubVO.setBc_id(rs.getString("bc_id"));
				bookClubVO.setMem_id(rs.getString("mem_id"));
				bookClubVO.setBc_name(rs.getString("bc_name"));
				bookClubVO.setBc_place(rs.getString("bc_place"));
				bookClubVO.setBc_time_start(rs.getTimestamp("bc_time_start"));
				bookClubVO.setBc_time_end(rs.getTimestamp("bc_time_end"));
				bookClubVO.setBc_peo_upper_limit(rs.getInt("bc_peo_upper_limit"));
				bookClubVO.setBc_peo_lower_limit(rs.getInt("bc_peo_lower_limit"));
				bookClubVO.setBc_intro(rs.getString("bc_intro"));
				bookClubVO.setBc_cover_pic(rs.getBytes("bc_cover_pic"));
				bookClubVO.setBc_comfirm_peo(rs.getInt("bc_comfirm_peo"));
				bookClubVO.setBc_status(rs.getInt("bc_status"));
				bookClubVO.setBc_init(rs.getDate("bc_init"));
				bookClubVO.setBc_deadline(rs.getDate("bc_deadline"));
				bookClubVO.setBc_create_time(rs.getTimestamp("bc_create_time"));
				list.add(bookClubVO);
			}
		}  catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	public static void main(String[] args) {
		BookClubJDBCDAO dao = new BookClubJDBCDAO();
		
		//新增
		BookClubVO bookClubVO1 = new BookClubVO();
		bookClubVO1.setBc_name("我就是愛讀書");
		bookClubVO1.setMem_id("M0002");
		bookClubVO1.setBc_place("中大");
		bookClubVO1.setBc_time_start(java.sql.Timestamp.valueOf("2020-09-29 20:00:00"));
		bookClubVO1.setBc_time_end(java.sql.Timestamp.valueOf("2020-09-29 21:00:00"));
		bookClubVO1.setBc_peo_upper_limit(40);
		bookClubVO1.setBc_peo_lower_limit(2);
		bookClubVO1.setBc_intro("你好阿");
		bookClubVO1.setBc_cover_pic(null);
		bookClubVO1.setBc_init(java.sql.Date.valueOf("2020-09-01"));
		bookClubVO1.setBc_deadline(java.sql.Date.valueOf("2020-09-20"));
//		dao.insert(bookClubVO1);
//		System.out.println("新增成功 main method");
		
		//修改
		BookClubVO bookClubVO2 = new BookClubVO();
		
		bookClubVO2.setBc_id("BC0008");
		bookClubVO2.setBc_name("我就是愛讀書");
		bookClubVO2.setBc_place("中大");
		bookClubVO2.setBc_time_start(java.sql.Timestamp.valueOf("2020-09-29 20:00:00"));
		bookClubVO2.setBc_time_end(java.sql.Timestamp.valueOf("2020-09-29 21:00:00"));
		bookClubVO2.setBc_peo_upper_limit(40);
		bookClubVO2.setBc_peo_lower_limit(2);
		bookClubVO2.setBc_intro("你好阿");
		bookClubVO2.setBc_cover_pic(null);
		bookClubVO2.setBc_init(java.sql.Date.valueOf("2020-09-01"));
		bookClubVO2.setBc_deadline(java.sql.Date.valueOf("2020-09-20"));
//		dao.update(bookClubVO2);
//		System.out.println("更新成功 main method");
		
		//刪除
//		dao.delete("BC0004");
//		System.out.println("刪除成功 main method");

		//查詢一個(PK)
//		BookClubVO bookClubVO3 = dao.findByPrimaryKey("BC0001");
//		
//		System.out.println(bookClubVO3.getBc_id() + ",");
//		System.out.println(bookClubVO3.getMem_id() + ",");
//		System.out.println(bookClubVO3.getBc_name() + ",");
//		System.out.println(bookClubVO3.getBc_place() + ",");
//		System.out.println(bookClubVO3.getBc_time_start() + ",");
//		System.out.println(bookClubVO3.getBc_time_end() + ",");
//		System.out.println(bookClubVO3.getBc_peo_upper_limit() + ",");
//		System.out.println(bookClubVO3.getBc_peo_lower_limit() + ",");
//		System.out.println(bookClubVO3.getBc_intro() + ",");
//		System.out.println(bookClubVO3.getBc_cover_pic() + ",");
//		System.out.println(bookClubVO3.getBc_comfirm_peo() + ",");
//		System.out.println(bookClubVO3.getBc_status() + ",");
//		System.out.println(bookClubVO3.getBc_init() + ",");
//		System.out.println(bookClubVO3.getBc_deadline() + ",");
//		System.out.println(bookClubVO3.getBc_create_time() + ",");
		
		//查全部
		List<BookClubVO> list = dao.getAll();
		for(BookClubVO allBookClub : list) {
			System.out.println(allBookClub.getBc_id() + ",");
			System.out.println(allBookClub.getMem_id() + ",");
			System.out.println(allBookClub.getBc_name() + ",");
			System.out.println(allBookClub.getBc_place() + ",");
			System.out.println(allBookClub.getBc_time_start() + ",");
			System.out.println(allBookClub.getBc_time_end() + ",");
			System.out.println(allBookClub.getBc_peo_upper_limit() + ",");
			System.out.println(allBookClub.getBc_peo_lower_limit() + ",");
			System.out.println(allBookClub.getBc_intro() + ",");
			System.out.println(allBookClub.getBc_cover_pic() + ",");
			System.out.println(allBookClub.getBc_comfirm_peo() + ",");
			System.out.println(allBookClub.getBc_status() + ",");
			System.out.println(allBookClub.getBc_init() + ",");
			System.out.println(allBookClub.getBc_deadline() + ",");
			System.out.println(allBookClub.getBc_create_time() + ",");
		}
		
		//更改狀態為2
//		dao.updateStatus("BC0004");
//		System.out.println("更新狀態成功(main method)");
		
//		List<BookClubVO> list = dao.findByBlurry("心");
//		for(BookClubVO blurryBookClub : list) {
//			System.out.println(blurryBookClub.getBc_name());
//		}
		
	}

	@Override
	public void updateComfirmPeo(Integer comfirm_peo, String bc_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BookClubVO> findByMyself(String mem_id) {
		// TODO Auto-generated method stub
		return null;
	}
}
