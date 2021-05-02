package com.language.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class LanguageDAO implements LanguageDAO_interface {
	private DataSource ds;

	public LanguageDAO(DataSource ds) {
		this.ds = ds;
	}

	private static final String GET_ALL_LANGUAGES = "SELECT * FROM LANGUAGES ORDER BY LANGUAGE_ID";
	private static final String GET_ONE_LANGUAGE = "SELECT * FROM LANGUAGES WHERE LANGUAGE_ID=?";
	private static final String UPDATE_LANGUAGE = "UPDATE LANGUAGES SET LANGUAGE_NAME=? WHERE LANGUAGE_ID=?";

	@Override
	public void update(Language languages) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_LANGUAGE);
			pstmt.setString(1, languages.getLanguage_ID());
			pstmt.setString(2, languages.getLanguage_Name());

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
	public Language findByPrimaryKey(String languages_id) {
		Language languages = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_LANGUAGE);

			pstmt.setString(1, languages_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				languages = new Language();
				languages.setLanguage_ID(rs.getString("language_ID"));
				languages.setLanguage_Name(rs.getString("language_Name"));

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
		return languages;
	}

	@Override
	public List<Language> getAll() {
		List<Language> list = new ArrayList<Language>();
		Language languages = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_LANGUAGES);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				languages = new Language();
				languages.setLanguage_ID(rs.getString("language_ID"));
				languages.setLanguage_Name(rs.getString("language_Name"));
				list.add(languages);
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
}
