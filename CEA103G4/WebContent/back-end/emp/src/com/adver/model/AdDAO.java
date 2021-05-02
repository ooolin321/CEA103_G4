package com.adver.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class AdDAO implements AdDAO_Interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bookshop");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INS_AD = "INSERT INTO ADVERTISEMENTS VALUES(('AD'||LPAD(ADID_SEQ.NEXTVAL,10,'0')),?,?,?,?,?,?,?,?,DEFAULT)";
	private static final String UPD_AD = "UPDATE ADVERTISEMENTS SET ACT_NAME=?, AD_TYPE=?, AD_START=?, AD_END=?, AD_IMAGE=?, AD_URL=?, AD_COPY=?, AD_UPDATE=DEFAULT WHERE AD_ID=?";
	private static final String DEL_AD = "DELETE FROM ADVERTISEMENTS WHERE AD_ID=?";
	private static final String FIND_TIME = "SELECT * FROM ADVERTISEMENTS WHERE AD_START BETWEEN ? AND ?";
	private static final String GET_ALL = "SELECT * FROM ADVERTISEMENTS ORDER BY AD_ID";
	private static final String GET_ONE = "SELECT * FROM ADVERTISEMENTS WHERE AD_ID = ?";

	@Override
	public void doCreate(AdVO advo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INS_AD);

			pstmt.setInt(1, advo.getBlo_class());
			pstmt.setString(2, advo.getAct_name());
			pstmt.setString(3, advo.getAd_type());
			pstmt.setTimestamp(4, advo.getAd_start());
			pstmt.setTimestamp(5, advo.getAd_end());
			pstmt.setBytes(6, advo.getAd_image());
			pstmt.setString(7, advo.getAd_url());
			pstmt.setString(8, advo.getAd_copy());

			pstmt.executeUpdate();

		} catch (SQLException sqle) {
			throw new RuntimeException("▲Error： [doCreate] A database error occured. " + sqle.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
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
	public void update(AdVO advo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPD_AD);

			pstmt.setString(1, advo.getAct_name());
			pstmt.setString(2, advo.getAd_type());
			pstmt.setTimestamp(3, advo.getAd_start());
			pstmt.setTimestamp(4, advo.getAd_end());
			pstmt.setBytes(5, advo.getAd_image());
			pstmt.setString(6, advo.getAd_url());
			pstmt.setString(7, advo.getAd_copy());

			pstmt.executeUpdate();

		} catch (SQLException sqle) {
			throw new RuntimeException("▲Error： [Update]" + sqle.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
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
	public void cancel(String ad_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DEL_AD);

			pstmt.setString(1, ad_id);

			pstmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);

		} catch (SQLException sqle) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			throw new RuntimeException("▲Error： [cancel]" + sqle.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
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
	public AdVO findbytime(Timestamp ad_start) {
		AdVO adVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_TIME);

			pstmt.setTimestamp(1, adVO.getAd_start());
			pstmt.setTimestamp(2, adVO.getAd_start());

			rs = pstmt.executeQuery();
			while (rs.next()) {
				adVO = new AdVO();
				adVO.setAd_id(rs.getString("ad_id"));
				adVO.setBlo_class(rs.getInt("blo_class"));
				adVO.setAct_name(rs.getNString("act_name"));
				adVO.setAd_type(rs.getString("ad_type"));
				adVO.setAd_start(rs.getTimestamp("ad_start"));
				adVO.setAd_end(rs.getTimestamp("ad_end"));
				adVO.setAd_image(rs.getBytes("ad_image"));
				adVO.setAd_url(rs.getString("ad_url"));
				adVO.setAd_copy(rs.getString("ad_copy"));
				adVO.setAd_update(rs.getTimestamp("ad_update"));
				pstmt.executeUpdate();
			}
		} catch (SQLException sqle) {
			throw new RuntimeException("▲Error： [Findbytime]" + sqle.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
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
		return adVO;
	}

	@Override
	public List<AdVO> getAll() {
		List<AdVO> list = new ArrayList<AdVO>();
		AdVO adVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				adVO = new AdVO();
				adVO.setAd_id(rs.getString("ad_id"));
				adVO.setBlo_class(rs.getInt("blo_class"));
				adVO.setAct_name(rs.getNString("act_name"));
				adVO.setAd_type(rs.getString("ad_type"));
				adVO.setAd_start(rs.getTimestamp("ad_start"));
				adVO.setAd_end(rs.getTimestamp("ad_end"));
				adVO.setAd_image(rs.getBytes("ad_image"));
				adVO.setAd_url(rs.getString("ad_url"));
				adVO.setAd_copy(rs.getString("ad_copy"));
				adVO.setAd_update(rs.getTimestamp("ad_update"));
				list.add(adVO);
			}
		} catch (SQLException sqle) {
			throw new RuntimeException("▲Error： [GetAll]" + sqle.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
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
	public AdVO getOne(String ad_id) {
		AdVO adVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE);
			pstmt.setString(1, ad_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				adVO = new AdVO();
				adVO.setAd_id(rs.getString("ad_id"));
				adVO.setBlo_class(rs.getInt("blo_class"));
				adVO.setAct_name(rs.getNString("act_name"));
				adVO.setAd_type(rs.getString("ad_type"));
				adVO.setAd_start(rs.getTimestamp("ad_start"));
				adVO.setAd_end(rs.getTimestamp("ad_end"));
				adVO.setAd_image(rs.getBytes("ad_image"));
				adVO.setAd_url(rs.getString("ad_url"));
				adVO.setAd_copy(rs.getString("ad_copy"));
				adVO.setAd_update(rs.getTimestamp("ad_update"));
			}
		} catch (SQLException sqle) {
			throw new RuntimeException("▲Error： [GetAll]" + sqle.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
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
		return adVO;
	}
}
