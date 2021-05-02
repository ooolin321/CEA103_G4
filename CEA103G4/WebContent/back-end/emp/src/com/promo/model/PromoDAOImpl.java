package com.promo.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.sql.DataSource;

public class PromoDAOImpl implements PromoDAO {
	private DataSource ds;

	public PromoDAOImpl(DataSource dataSource) {
		this.ds = dataSource;
	}

	private static final String INSERT_STMT = "INSERT INTO PROMOTIONS(PROMO_ID,PROMO_NAME,PROMO_START_TIME,PROMO_END_TIME) VALUES ('PROMO' || lpad(PROMO_ID_SEQ.NEXTVAL, 10, '0'),?,?,?)";
	private static final String FIND_BY_PROMO_ID_STMT = "SELECT * FROM PROMOTIONS WHERE PROMO_ID = ?";
	private static final String FIND_BY_PROMO_NAME_UNIQUE_STMT = "SELECT * FROM PROMOTIONS WHERE UPPER(PROMO_NAME) = UPPER(?)";
	private static final String UPDATE_STMT = "UPDATE PROMOTIONS SET PROMO_NAME = ?, PROMO_START_TIME = ?, PROMO_END_TIME = ? WHERE PROMO_ID = ?";
	private static final List<String> ADV_SEARCH_CONDITIONS = Arrays.asList("promoID", "promoName", "promoStartTime",
			"promoEndTime");
	private static final String ADV_SEARCH_STMT = "SELECT * FROM PROMOTIONS WHERE (? IS NULL OR PROMO_ID = ?) "
			+ "AND (? IS NULL OR upper(PROMO_NAME) LIKE '%'|| upper(?) || '%') "
			+ "AND (? IS NULL OR PROMO_START_TIME >= to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss.ff')) "
			+ "AND (? IS NULL OR PROMO_END_TIME <= to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss.ff')) "
			+ "ORDER BY PROMO_ID DESC";
	private static final String FIND_ALL_VALID_STMT = "SELECT * FROM PROMOTIONS WHERE (PROMO_START_TIME <= CURRENT_TIMESTAMP) AND (CURRENT_TIMESTAMP <= PROMO_END_TIME)";
	private static final String FIND_BY_PROMO_NAME_LIKE_STMT = "SELECT PROMO_NAME FROM PROMOTIONS WHERE UPPER(PROMO_NAME) LIKE (UPPER(?) || '%') AND ROWNUM <= 10";

	@Override
	public void insert(Promo promo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, promo.getPromoName());
			pstmt.setTimestamp(2, promo.getPromoStartTime());
			pstmt.setTimestamp(3, promo.getPromoEndTime());

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
	public Optional<Promo> findByPromoID(String promoID) {
		Promo promo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_PROMO_ID_STMT);
			pstmt.setString(1, promoID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				promo = new Promo();
				promo.setPromoID(rs.getString("PROMO_ID"));
				promo.setPromoName(rs.getString("PROMO_NAME"));
				promo.setPromoStartTime(rs.getTimestamp("PROMO_START_TIME"));
				promo.setPromoEndTime(rs.getTimestamp("PROMO_END_TIME"));
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
		return Optional.ofNullable(promo);
	}

	public List<Promo> advSearch(Map<String, String> map) {
		List<Promo> listPromo = new ArrayList<Promo>();
		Promo promo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String[] conditions = new String[ADV_SEARCH_CONDITIONS.size()];

		for (int i = 0; i < conditions.length; i++) {
			conditions[i] = map.get(ADV_SEARCH_CONDITIONS.get(i));
		}

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(ADV_SEARCH_STMT);

			for (int i = 1; i < conditions.length * 2 + 1; i += 2) {
				pstmt.setString(i, conditions[i / 2]);
				pstmt.setString(i + 1, conditions[i / 2]);
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				promo = new Promo();
				promo.setPromoID(rs.getString("PROMO_ID"));
				promo.setPromoName(rs.getString("PROMO_NAME"));
				promo.setPromoStartTime(rs.getTimestamp("PROMO_START_TIME"));
				promo.setPromoEndTime(rs.getTimestamp("PROMO_END_TIME"));
				listPromo.add(promo);
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
		return listPromo;
	}

	@Override
	public void update(Promo promo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, promo.getPromoName());
			pstmt.setTimestamp(2, promo.getPromoStartTime());
			pstmt.setTimestamp(3, promo.getPromoEndTime());
			pstmt.setString(4, promo.getPromoID());

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
	public Optional<Promo> findByPromoNameUnique(String promoName) {
		Promo promo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_PROMO_NAME_UNIQUE_STMT);
			pstmt.setString(1, promoName);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				promo = new Promo();
				promo.setPromoID(rs.getString("PROMO_ID"));
				promo.setPromoName(rs.getString("PROMO_NAME"));
				promo.setPromoStartTime(rs.getTimestamp("PROMO_START_TIME"));
				promo.setPromoEndTime(rs.getTimestamp("PROMO_END_TIME"));
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
		return Optional.ofNullable(promo);
	}

	@Override
	public Set<Promo> findValidPromos() {
		Set<Promo> listPromo = new HashSet<Promo>();
		Promo promo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_ALL_VALID_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				promo = new Promo();
				promo.setPromoID(rs.getString("PROMO_ID"));
				promo.setPromoName(rs.getString("PROMO_NAME"));
				promo.setPromoStartTime(rs.getTimestamp("PROMO_START_TIME"));
				promo.setPromoEndTime(rs.getTimestamp("PROMO_END_TIME"));
				listPromo.add(promo);
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
		return listPromo;
	}

	@Override
	public List<String> findByPromoNameLike(String promoName) {
		List<String> promoNames = new LinkedList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_PROMO_NAME_LIKE_STMT);
			pstmt.setString(1, promoName);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				promoNames.add(rs.getString(1));
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
		return promoNames;
	}

}
