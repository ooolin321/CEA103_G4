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

public class BookClubJNDIDAO implements BookClubDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bookshop");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO BOOKCLUB (BC_ID,MEM_ID,BC_NAME,BC_PLACE,BC_TIME_START,BC_TIME_END,BC_PEO_UPPER_LIMIT,BC_PEO_LOWER_LIMIT,BC_INTRO,BC_COVER_PIC,BC_COMFIRM_PEO,BC_STATUS,BC_INIT,BC_DEADLINE,BC_CREATE_TIME)VALUES ('BC'||TO_CHAR(BC_SEQ.NEXTVAL, 'FM0000'),?,?,?,?,?,?,?,?,?,DEFAULT,DEFAULT,?,?,DEFAULT)";
	private static final String GET_ALL_STMT = "SELECT * FROM BOOKCLUB ORDER BY BC_CREATE_TIME DESC";
	private static final String SELECT_BY_PK = "SELECT * FROM BOOKCLUB WHERE BC_ID = ?";
	private static final String DELETE = "DELETE FROM BOOKCLUB WHERE BC_ID = ?";
	private static final String UPDATE = "UPDATE BOOKCLUB SET BC_NAME = ?, BC_PLACE = ?, BC_TIME_START = ?, BC_TIME_END = ?, BC_PEO_UPPER_LIMIT = ?,BC_PEO_LOWER_LIMIT = ?,BC_INTRO = ?,BC_COVER_PIC = ?,BC_INIT = ?,BC_DEADLINE = ? WHERE BC_ID = ?";
	private static final String UPDATE_STATUS = "UPDATE BOOKCLUB SET BC_STATUS = ? WHERE BC_ID = ?";
	private static final String SELECT_BY_STATUS = "SELECT * FROM BOOKCLUB WHERE BC_STATUS = 1 ORDER BY BC_CREATE_TIME DESC";
	private static final String SELECT_BY_BLURRY = "SELECT * FROM BOOKCLUB WHERE BC_NAME LIKE '%' || ? || '%' and BC_STATUS = 1 ";
	private static final String UPDATE_PEO = "UPDATE BOOKCLUB SET BC_COMFIRM_PEO = ? WHERE BC_ID = ?";
	private static final String SELECT_BY_MYSELF = "SELECT * FROM BOOKCLUB WHERE MEM_ID = ? ORDER BY BC_CREATE_TIME DESC";
	
	public String insert(BookClubVO bookClubVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String bc_id = "";
		String[] pk = {"BC_ID"};
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT,pk);

			pstmt.setString(1, bookClubVO.getMem_id()); // 取session的值,目前寫死
			pstmt.setString(2, bookClubVO.getBc_name());
			pstmt.setString(3, bookClubVO.getBc_place());
			pstmt.setTimestamp(4, bookClubVO.getBc_time_start());
			pstmt.setTimestamp(5, bookClubVO.getBc_time_end());
			pstmt.setInt(6, bookClubVO.getBc_peo_upper_limit());
			pstmt.setInt(7, bookClubVO.getBc_peo_lower_limit());
			pstmt.setString(8, bookClubVO.getBc_intro());
			pstmt.setBytes(9, bookClubVO.getBc_cover_pic());
			pstmt.setDate(10, bookClubVO.getBc_init());
			pstmt.setDate(11, bookClubVO.getBc_deadline());

			pstmt.executeUpdate();
			System.out.println("新增成功 insert method");
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				bc_id = rs.getString(1);
			}

		} catch (SQLException se) {
			se.printStackTrace();
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException se) {
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
		return bc_id;
	}

	public void update(BookClubVO bookClubVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
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
			System.out.println(bookClubVO.getBc_id());
			System.out.println("更新成功 update method");

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
	public void delete(String bc_id) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, bc_id);

			pstmt.executeUpdate();
			System.out.println("刪除成功 delete method");

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
			con = ds.getConnection();
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

			con = ds.getConnection();
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(SELECT_BY_STATUS);
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

	@Override
	public void updateStatus(String bc_id,Integer status) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STATUS);
			
			pstmt.setInt(1, status);
			pstmt.setString(2, bc_id);
			

			pstmt.executeUpdate();
			System.out.println("更新狀態成功(updateStatus method)");
			// 修改狀態成功
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
			con = ds.getConnection();
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

	@Override
	public void updateComfirmPeo(Integer comfirm_peo, String bc_id) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_PEO);

			pstmt.setInt(1, comfirm_peo);
			pstmt.setString(2, bc_id);

			pstmt.executeUpdate();
			System.out.println("更新人數成功(updateStatus method)");
			
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
	public List<BookClubVO> findByMyself(String mem_id) {
		List<BookClubVO> list = new ArrayList<BookClubVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BookClubVO bookClubVO = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SELECT_BY_MYSELF);
			pstmt.setString(1, mem_id);
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

}
