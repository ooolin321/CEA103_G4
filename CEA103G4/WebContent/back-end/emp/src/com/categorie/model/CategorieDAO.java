package com.categorie.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class CategorieDAO implements CategorieDAO_interface{
	// �@�����ε{����,�w��@�Ӹ�Ʈw ,�@�Τ@��DataSource�Y�i
		private static DataSource ds = null;
		static {
			try {
				Context ctx = new InitialContext();
				ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bookshop");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}

		private static final String INSERT_STMT = "INSERT INTO LECTURE_CATEGORIES (LC_CLASS_ID,LC_CLASS_NAME) VALUES ('LC' || lpad(LC_CAT_SEQ.NEXTVAL, 3, '0'),?)";
		private static final String UPDATE = "UPDATE LECTURE_CATEGORIES SET LC_CLASS_NAME = ? where LC_CLASS_ID = ?";
		private static final String GET_ALL_STMT = "SELECT * FROM LECTURE_CATEGORIES ORDER BY LC_CLASS_ID";
		private static final String GET_ONE_STMT = "SELECT * FROM LECTURE_CATEGORIES WHERE LC_CLASS_ID = ?";
		private static final String DELETE_LECTURES = "DELETE FROM LECTURES WHERE LC_CLASS_ID = ?";
		private static final String DELETE_CATEGORIE = "DELETE FROM LECTURE_CATEGORIES WHERE LC_CLASS_ID = ?";	
//		private static final String GET_Lectures_ByCategorieId_STMT = "SELECT * FROM LECTURES WHERE LC_CLASS_ID = ?";
		
		@Override
		public void insert(CategorieVO categorieVO) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(INSERT_STMT);

				pstmt.setString(1, categorieVO.getLc_class_name());
				System.out.println(pstmt.executeUpdate());
				

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
		public void update(CategorieVO categorieVO) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE);

				pstmt.setString(1, categorieVO.getLc_class_name());
				pstmt.setString(2, categorieVO.getLc_class_id());

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
		public void delete(String lc_class_id) {
			int updateCount_LECTURES = 0;
			
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();

				// 1���]�w�� pstm.executeUpdate()���e
				con.setAutoCommit(false);

				// ���R�����y
				pstmt = con.prepareStatement(DELETE_LECTURES);
				pstmt.setString(1, lc_class_id);
				pstmt.executeUpdate();
				
				// �A�R�����O
				pstmt = con.prepareStatement(DELETE_CATEGORIE);
				pstmt.setString(1, lc_class_id);
				pstmt.executeUpdate();

				// 2���]�w�� pstm.executeUpdate()����
				con.commit();
				con.setAutoCommit(true);
				System.out.println("�R�����y���O�s��" + lc_class_id + "��,�@��" + updateCount_LECTURES
						+ "�����y�P�ɳQ�R��");
				
				// Handle any SQL errors
			} catch (SQLException se) {
				if (con != null) {
					try {
						// 3���]�w���exception�o�ͮɤ�catch�϶���
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

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ONE_STMT);

				pstmt.setString(1, lc_class_id);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					// deptVO �]�٬� Domain objects
					categorieVO = new CategorieVO();
					categorieVO.setLc_class_id(rs.getString("lc_class_id"));
					categorieVO.setLc_class_name(rs.getString("lc_class_name"));
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

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					categorieVO = new CategorieVO();
					categorieVO.setLc_class_id(rs.getString("lc_class_id"));
					categorieVO.setLc_class_name(rs.getString("lc_class_name"));
					list.add(categorieVO); // Store the row in the list
				}

				// Handle any SQL errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
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
		
//		@Override
//		public Set<LecturesVO> getLecturesByCategorieId(String lc_class_id) {
//			Set<LecturesVO> set = new LinkedHashSet<LecturesVO>();
//			LecturesVO lecturesVO = null;
//		
//			Connection con = null;
//			PreparedStatement pstmt = null;
//			ResultSet rs = null;
//		
//			try {
//		
//				con = ds.getConnection();
//				pstmt = con.prepareStatement(GET_Lectures_ByCategorieId_STMT);
//				pstmt.setString(1, lc_class_id);
//				rs = pstmt.executeQuery();
//		
//				while (rs.next()) {
//					lecturesVO = new LecturesVO();
//					lecturesVO.setLc_id(rs.getString("lc_id"));
//					lecturesVO.setLc_class_id(rs.getString("lc_class_id"));
//					lecturesVO.setAdmin_id(rs.getString("admin_id"));
//					lecturesVO.setLc_name(rs.getString("lc_name"));
//					lecturesVO.setLc_place(rs.getString("lc_place"));
//					lecturesVO.setLc_time(rs.getTimestamp("lc_time"));
//					lecturesVO.setLc_hr(rs.getInt("lc_hr"));
//					lecturesVO.setLc_deadline(rs.getTimestamp("lc_deadline"));
//					lecturesVO.setLc_start_time(rs.getTimestamp("lc_start_time"));
//					lecturesVO.setLc_peo_lim(rs.getInt("lc_peo_lim"));
//					lecturesVO.setLc_peo_up(rs.getInt("lc_peo_up"));
//					lecturesVO.setLc_info(rs.getString("lc_info"));
//					lecturesVO.setLc_pic(rs.getBytes("lc_pic"));
//					lecturesVO.setLc_free(rs.getInt("lc_free"));
//					lecturesVO.setLc_price(rs.getInt("lc_price"));
//					lecturesVO.setLc_state(rs.getInt("lc_state"));
//					lecturesVO.setLc_seat_row(rs.getInt("lc_seat_row"));
//					lecturesVO.setLc_seat_colume(rs.getInt("lc_seat_colume"));
//					lecturesVO.setLc_seat_state(rs.getString("lc_seat_state"));
//					set.add(lecturesVO); // Store the row in the vector
//				}
//		
//				// Handle any SQL errors
//			} catch (SQLException se) {
//				throw new RuntimeException("A database error occured. "
//						+ se.getMessage());
//			} finally {
//				if (rs != null) {
//					try {
//						rs.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (pstmt != null) {
//					try {
//						pstmt.close();
//					} catch (SQLException se) {
//						se.printStackTrace(System.err);
//					}
//				}
//				if (con != null) {
//					try {
//						con.close();
//					} catch (Exception e) {
//						e.printStackTrace(System.err);
//					}
//				}
//			}
//			return set;
//		}
	
}
