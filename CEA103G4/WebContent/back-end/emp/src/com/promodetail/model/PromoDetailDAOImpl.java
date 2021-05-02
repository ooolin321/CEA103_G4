package com.promodetail.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

public class PromoDetailDAOImpl implements PromoDetailDAO {
	private DataSource ds;

	public PromoDetailDAOImpl(DataSource dataSource) {
		this.ds = dataSource;
	}

	private static final String INSERT_STMT = "INSERT INTO PROMOTION_DETAILS(PROMO_ID, BOOK_ID, DISCOUNT, BP_PERCENT) VALUES (?,?,?,?)";
	private static final String FIND_BY_PROMO_ID_AND_BOOK_ID_STMT = "SELECT * FROM PROMOTION_DETAILS WHERE (? IS NULL OR PROMO_ID = ?) AND (? IS NULL OR BOOK_ID = ?)";
	private static final String UPDATE_STMT = "UPDATE PROMOTION_DETAILS SET DISCOUNT = ?, BP_PERCENT = ? WHERE PROMO_ID = ? AND BOOK_ID = ?";
	private static final String DELETE_BY_PROMO_ID_AND_BOOK_ID_STMT = "DELETE FROM PROMOTION_DETAILS WHERE (? IS NULL OR PROMO_ID = ?) AND (? IS NULL OR BOOK_ID = ?)";

	@Override
	public void insert(PromoDetail promoDetail) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, promoDetail.getPromoID());
			pstmt.setString(2, promoDetail.getBookID());
			pstmt.setInt(3, promoDetail.getDiscount());
			pstmt.setInt(4, promoDetail.getBpPercent());

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
	public List<PromoDetail> findByPromoIDAndBookID(String promoID, String bookID) {
		List<PromoDetail> promoDetails = new ArrayList<PromoDetail>();
		PromoDetail promoDetail = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_PROMO_ID_AND_BOOK_ID_STMT);
			pstmt.setString(1, promoID);
			pstmt.setString(2, promoID);
			pstmt.setString(3, bookID);
			pstmt.setString(4, bookID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				promoDetail = new PromoDetail();
				promoDetail.setPromoID(rs.getString("PROMO_ID"));
				promoDetail.setBookID(rs.getString("BOOK_ID"));
				promoDetail.setDiscount(rs.getInt("DISCOUNT"));
				promoDetail.setBpPercent(rs.getInt("BP_PERCENT"));
				promoDetails.add(promoDetail);
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
		return promoDetails;
	}

	@Override
	public void update(PromoDetail promoDetail) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setInt(1, promoDetail.getDiscount());
			pstmt.setInt(2, promoDetail.getBpPercent());
			pstmt.setString(3, promoDetail.getPromoID());
			pstmt.setString(4, promoDetail.getBookID());

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
	public void deleteByPromoIDAndBookID(String promoID, String bookID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DELETE_BY_PROMO_ID_AND_BOOK_ID_STMT);

			pstmt.setString(1, promoID);
			pstmt.setString(2, promoID);
			pstmt.setString(3, bookID);
			pstmt.setString(4, bookID);

			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void insertBatch(List<PromoDetail> promoDetails) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(INSERT_STMT);

			for (PromoDetail promoDetail : promoDetails) {
				pstmt.setString(1, promoDetail.getPromoID());
				pstmt.setString(2, promoDetail.getBookID());
				pstmt.setInt(3, promoDetail.getDiscount());
				pstmt.setInt(4, promoDetail.getBpPercent());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			con.commit();
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void updateBatch(List<PromoDetail> promoDetails) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE_STMT);

			for (PromoDetail promoDetail : promoDetails) {
				pstmt.setInt(1, promoDetail.getDiscount());
				pstmt.setInt(2, promoDetail.getBpPercent());
				pstmt.setString(3, promoDetail.getPromoID());
				pstmt.setString(4, promoDetail.getBookID());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			con.commit();
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void deleteByPromoIDAndBookIDList(String promoID, List<String> bookIDs) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DELETE_BY_PROMO_ID_AND_BOOK_ID_STMT);

			for (String bookID : bookIDs) {
				pstmt.setString(1, promoID);
				pstmt.setString(2, promoID);
				pstmt.setString(3, bookID);
				pstmt.setString(4, bookID);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			con.commit();
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

}
