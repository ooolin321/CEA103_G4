package com.categorie.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategorieJDBCDAO implements CategorieDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "BOOKSHOPG4";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO LECTURE_CATEGORIES (LC_CLASS_ID,LC_CLASS_NAME) VALUES ('LC' || lpad(LC_CAT_SEQ.NEXTVAL, 3, '0'),?)";
	private static final String UPDATE = "UPDATE LECTURE_CATEGORIES SET LC_CLASS_NAME = ? where LC_CLASS_ID = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM LECTURE_CATEGORIES ORDER BY LC_CLASS_ID";
	private static final String GET_ONE_STMT = "SELECT * FROM LECTURE_CATEGORIES where LC_CLASS_ID = ?";
	private static final String DELETE_LECTURES = "DELETE FROM LECTURES WHERE LC_CLASS_ID = ?";
	private static final String DELETE_CATEGORIE = "DELETE FROM LECTURE_CATEGORIES WHERE LC_CLASS_ID = ?";
//	private static final String GET_Lectures_ByCategorieId_STMT = "SELECT * FROM LECTURES WHERE LC_CLASS_ID = ?";

	@Override
	public void insert(CategorieVO categorieVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, categorieVO.getLc_class_name());

			pstmt.executeUpdate();

			// Handle any SQL errors
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
	public void update(CategorieVO categorieVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, categorieVO.getLc_class_name());

			pstmt.executeUpdate();

			// Handle any SQL errors
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
	public void delete(String lc_class_id) {
		int updateCount_LECTURES = 0;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			// 1●設定於 pstm.executeUpdate()之前
			con.setAutoCommit(false);

			// 先刪除講座
			pstmt = con.prepareStatement(DELETE_LECTURES);
			pstmt.setString(1, lc_class_id);
			pstmt.executeUpdate();

			// 再刪除類別
			pstmt = con.prepareStatement(DELETE_CATEGORIE);
			pstmt.setString(1, lc_class_id);
			pstmt.executeUpdate();

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("刪除講座類別編號" + lc_class_id + "時,共有" + updateCount_LECTURES + "個講座同時被刪除");

			// Handle any SQL errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
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
	public CategorieVO findByPrimaryKey(String lc_class_id) {

		CategorieVO categorieVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, lc_class_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// categorieVO 也稱為 Domain objects
				categorieVO = new CategorieVO();
				categorieVO.setLc_class_id(rs.getString("lc_class_id"));
				categorieVO.setLc_class_name(rs.getString("lc_class_name"));
			}

			// Handle any SQL errors
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
		return categorieVO;
	}

	@Override
	public List<CategorieVO> getAll() {
		List<CategorieVO> list = new ArrayList<CategorieVO>();
		CategorieVO categorieVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				categorieVO = new CategorieVO();
				categorieVO.setLc_class_id(rs.getString("lc_class_id"));
				categorieVO.setLc_class_name(rs.getString("lc_class_name"));
				list.add(categorieVO); // Store the row in the list
			}

			// Handle any SQL errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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

	public static void main(String[] args) {
		CategorieJDBCDAO dao = new CategorieJDBCDAO();

		// 新增
//		CategorieVO categorieVO1 = new CategorieVO();
//		categorieVO1.setLc_class_name("測試2");
//		dao.insert(categorieVO1);

		// 修改
//		CategorieVO categorieVO2 = new CategorieVO();
//		categorieVO2.setLc_class_id("LC001");
//		categorieVO2.setLc_class_name("測試3");
//		dao.insert(categorieVO2);

		// 刪除
//		dao.delete("LC001");

		// 查詢
//		CategorieVO categorieVO3 = dao.findByPrimaryKey("LC001");
//		System.out.print(categorieVO3.getLc_class_id() + ",");
//		System.out.println(categorieVO3.getLc_class_name());	
//		System.out.println("---------------------");

		// 查詢類別
		List<CategorieVO> list = dao.getAll();
		for (CategorieVO aCategorie : list) {
			System.out.print(aCategorie.getLc_class_id() + ",");
			System.out.print(aCategorie.getLc_class_name());
			System.out.println();
		}

		// 查詢某類別講座
//		Set<LecturesVO> set = dao.getLecturesByCategorieId("LC001");
//		for (LecturesVO aLectures : set) {
//			System.out.print(aLectures.getLc_id() + ",");
//			System.out.print(aLectures.getLc_class_id() + ",");
//			System.out.print(aLectures.getAdmin_id() + ",");
//			System.out.print(aLectures.getLc_name() + ",");
//			System.out.print(aLectures.getLc_place() + ",");
//			System.out.print(aLectures.getLc_time() + ",");
//			System.out.print(aLectures.getLc_hr() + ",");
//			System.out.print(aLectures.getLc_deadline() + ",");
//			System.out.print(aLectures.getLc_start_time() + ",");
//			System.out.print(aLectures.getLc_peo_lim() + ",");
//			System.out.print(aLectures.getLc_peo_up() + ",");
//			System.out.print(aLectures.getLc_info() + ",");
//			System.out.print(aLectures.getLc_pic() + ",");
//			System.out.print(aLectures.getLc_free() + ",");
//			System.out.print(aLectures.getLc_price() + ",");
//			System.out.print(aLectures.getLc_state() + ",");
//			System.out.print(aLectures.getLc_seat_row() + ",");
//			System.out.print(aLectures.getLc_seat_colume() + ",");
//			System.out.println(aLectures.getLc_seat_state());
//			System.out.println();
//		}
	}

}
