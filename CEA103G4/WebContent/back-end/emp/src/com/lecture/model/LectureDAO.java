package com.lecture.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class LectureDAO implements LectureDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
//			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BOOKSHOP");
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bookshop");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO LECTURES (LC_ID, LC_CLASS_ID, ADMIN_ID, LC_NAME, LC_PLACE, LC_TIME, LC_HR,"
			+ " LC_DEADLINE, LC_START_TIME, LC_PEO_LIM, LC_PEO_UP, LC_INFO, LC_PIC, LC_STATE)"
			+ " VALUES ('L' || lpad(LC_SEQ.NEXTVAL, 4, '0'),?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE = "UPDATE LECTURES SET LC_PLACE = ?, LC_TIME = ?, LC_HR= ?,"
			+ " LC_DEADLINE = ?, LC_START_TIME = ?, LC_PEO_LIM = ?, LC_PEO_UP = ?,LC_INFO = ? , LC_STATE = ?"
			+ " WHERE LC_ID = ?";
	private static final String UPDATE_STATE_1_2 = "UPDATE LECTURES SET LC_PLACE = ?, LC_TIME = ?, LC_HR= ?, LC_DEADLINE = ?, LC_STATE = ? "
			+ " WHERE LC_ID = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM LECTURES ORDER BY LC_ID";
	private static final String GET_ONE_STMT = "SELECT * FROM LECTURES WHERE LC_ID = ?";
	private static final String SELECT_BY_STATE = "SELECT * FROM LECTURES WHERE LC_STATE = 1 ORDER BY LC_ID DESC";
	private static final String DELETE = "DELETE FROM LECTURES WHERE LC_ID = ?";

	@Override
	public void insert(LectureVO lectureVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, lectureVO.getLc_class_id());
			pstmt.setString(2, lectureVO.getAdmin_id());
			pstmt.setString(3, lectureVO.getLc_name());
			pstmt.setString(4, lectureVO.getLc_place());
			pstmt.setTimestamp(5, lectureVO.getLc_time());
			pstmt.setInt(6, lectureVO.getLc_hr());
			pstmt.setTimestamp(7, lectureVO.getLc_deadline());
			pstmt.setTimestamp(8, lectureVO.getLc_start_time());
			pstmt.setInt(9, lectureVO.getLc_peo_lim());
			pstmt.setInt(10, lectureVO.getLc_peo_up());
			pstmt.setString(11, lectureVO.getLc_info());
			pstmt.setBytes(12, lectureVO.getLc_pic());
			pstmt.setInt(13, lectureVO.getLc_state());

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

	@Override
	public void update(LectureVO lectureVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, lectureVO.getLc_place());
			pstmt.setTimestamp(2, lectureVO.getLc_time());
			pstmt.setInt(3, lectureVO.getLc_hr());
			pstmt.setTimestamp(4, lectureVO.getLc_deadline());
			pstmt.setTimestamp(5, lectureVO.getLc_start_time());
			pstmt.setInt(6, lectureVO.getLc_peo_lim());
			pstmt.setInt(7, lectureVO.getLc_peo_up());
			pstmt.setString(8, lectureVO.getLc_info());
			pstmt.setInt(9, lectureVO.getLc_state());
			pstmt.setString(10, lectureVO.getLc_id());
			pstmt.executeUpdate();
			

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
	public void update_state1or2(LectureVO lectureVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STATE_1_2);

			pstmt.setString(1, lectureVO.getLc_place());
			pstmt.setTimestamp(2, lectureVO.getLc_time());
			pstmt.setInt(3, lectureVO.getLc_hr());
			pstmt.setTimestamp(4, lectureVO.getLc_deadline());
			pstmt.setInt(5, lectureVO.getLc_state());
			pstmt.setString(6, lectureVO.getLc_id());

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
	public void delete(String lc_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, lc_id);

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

	@Override
	public LectureVO findByPrimaryKey(String lc_id) {
		LectureVO lectureVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, lc_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				lectureVO = new LectureVO();
				lectureVO.setLc_id(rs.getString("lc_id"));
				lectureVO.setLc_class_id(rs.getString("lc_class_id"));
				lectureVO.setAdmin_id(rs.getString("admin_id"));
				lectureVO.setLc_name(rs.getString("lc_name"));
				lectureVO.setLc_place(rs.getString("lc_place"));
				lectureVO.setLc_time(rs.getTimestamp("lc_time"));
				lectureVO.setLc_hr(rs.getInt("lc_hr"));
				lectureVO.setLc_deadline(rs.getTimestamp("lc_deadline"));
				lectureVO.setLc_start_time(rs.getTimestamp("lc_start_time"));
				lectureVO.setLc_peo_lim(rs.getInt("lc_peo_lim"));
				lectureVO.setLc_peo_up(rs.getInt("lc_peo_up"));
				lectureVO.setLc_info(rs.getString("lc_info"));
				lectureVO.setLc_pic(rs.getBytes("lc_pic"));
				lectureVO.setLc_state(rs.getInt("lc_state"));
			}

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
		return lectureVO;
	}
	
	public List<LectureVO> findByState() {
		List<LectureVO> list = new ArrayList<LectureVO>();
		LectureVO lectureVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SELECT_BY_STATE);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				lectureVO = new LectureVO();
				lectureVO.setLc_id(rs.getString("lc_id"));
				lectureVO.setLc_class_id(rs.getString("lc_class_id"));
				lectureVO.setAdmin_id(rs.getString("admin_id"));
				lectureVO.setLc_name(rs.getString("lc_name"));
				lectureVO.setLc_place(rs.getString("lc_place"));
				lectureVO.setLc_time(rs.getTimestamp("lc_time"));
				lectureVO.setLc_hr(rs.getInt("lc_hr"));
				lectureVO.setLc_deadline(rs.getTimestamp("lc_deadline"));
				lectureVO.setLc_start_time(rs.getTimestamp("lc_start_time"));
				lectureVO.setLc_peo_lim(rs.getInt("lc_peo_lim"));
				lectureVO.setLc_peo_up(rs.getInt("lc_peo_up"));
				lectureVO.setLc_info(rs.getString("lc_info"));
				lectureVO.setLc_pic(rs.getBytes("lc_pic"));
				lectureVO.setLc_state(rs.getInt("lc_state"));
				list.add(lectureVO);
			}
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
	public List<LectureVO> getAll() {
		List<LectureVO> list = new ArrayList<LectureVO>();
		LectureVO lectureVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				lectureVO = new LectureVO();
				lectureVO.setLc_id(rs.getString("lc_id"));
				lectureVO.setLc_class_id(rs.getString("lc_class_id"));
				lectureVO.setAdmin_id(rs.getString("admin_id"));
				lectureVO.setLc_name(rs.getString("lc_name"));
				lectureVO.setLc_place(rs.getString("lc_place"));
				lectureVO.setLc_time(rs.getTimestamp("lc_time"));
				lectureVO.setLc_hr(rs.getInt("lc_hr"));
				lectureVO.setLc_deadline(rs.getTimestamp("lc_deadline"));
				lectureVO.setLc_start_time(rs.getTimestamp("lc_start_time"));
				lectureVO.setLc_peo_lim(rs.getInt("lc_peo_lim"));
				lectureVO.setLc_peo_up(rs.getInt("lc_peo_up"));
				lectureVO.setLc_info(rs.getString("lc_info"));
				lectureVO.setLc_pic(rs.getBytes("lc_pic"));
				lectureVO.setLc_state(rs.getInt("lc_state"));
				list.add(lectureVO);
			}
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
	public Optional<LectureVO> findLecturePicByLcId(String lc_id){
		LectureVO lectureVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, lc_id);
			

			rs = pstmt.executeQuery();

			while (rs.next()) {
				lectureVO = new LectureVO();
				lectureVO.setLc_pic(rs.getBytes("lc_pic"));
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
		return Optional.ofNullable(lectureVO);
	}
	

}
