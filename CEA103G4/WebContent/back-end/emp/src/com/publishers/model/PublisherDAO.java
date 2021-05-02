package com.publishers.model;

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

public class PublisherDAO implements PublisherDAO_interface {
	private DataSource ds;

	public PublisherDAO(DataSource ds) {
		this.ds = ds;
	}

	private static final String INSERT_PUBLISHER = "INSERT INTO PUBLISHERS(PUBLISHER_NAME, PUBLISHER_PHONE, PUBLISHER_ADDRESS, PUBLISHER_EMAIL) VALUES ('P' || lpad(PUBLISHERS_ID_SEQ.NEXTVAL, 9, '0'),?,?,?,?)";
	private static final String GET_ALL_PUBLISHERS = "SELECT * FROM PUBLISHERS ORDER BY PUBLISHER_ID";
	private static final String GET_ONE_PUBLISHER = "SELECT * FROM PUBLISHERS WHERE PUBLISHER_ID = ?";
	private static final String GET_PUBLISHER_BY_NAME = "SELECT * FROM PUBLISHERS WHERE upper(PUBLISHER_NAME) = ?";
	private static final String GET_PUBLISHERS_BY_NAME = "SELECT * FROM PUBLISHERS WHERE upper(PUBLISHER_NAME) LIKE '%'|| upper(?) || '%'";
	private static final String UPDATE_PUBLISHER = "UPDATE PUBLISHERS SET publisher_NAME =?, publisher_phone =?, publisher_address = ? ,publisher_email = ? WHERE publisher_id = ?";
	private static final String GET_PUBLISHER_BY_NAME_LIKE = "SELECT PUBLISHER_NAME FROM PUBLISHERS WHERE UPPER(PUBLISHER_NAME) LIKE (UPPER(?) || '%') AND ROWNUM <= 10";

	@Override
	public void update(Publisher publisher) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_PUBLISHER);
			pstmt.setString(1, publisher.getPublisher_ID());
			pstmt.setString(2, publisher.getPublisher_Name());
			pstmt.setString(3, publisher.getPublisher_Phone());
			pstmt.setString(4, publisher.getPublisher_Address());
			pstmt.setString(5, publisher.getPublisher_Email());

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
	public Publisher findByPrimaryKey(String publisher_id) {
		Publisher publishers = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_PUBLISHER);

			pstmt.setString(1, publisher_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				publishers = new Publisher();
				publishers.setPublisher_ID(rs.getString("publisher_ID"));
				publishers.setPublisher_Name(rs.getString("publisher_Name"));
				publishers.setPublisher_Phone(rs.getString("publisher_Phone"));
				publishers.setPublisher_Address(rs.getString("publisher_Address"));
				publishers.setPublisher_Email(rs.getString("publisher_Email"));
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
		return publishers;
	}

	@Override
	public List<Publisher> getAll() {
		List<Publisher> list = new ArrayList<Publisher>();
		Publisher publishers = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_PUBLISHERS);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				publishers = new Publisher();
				publishers.setPublisher_ID(rs.getString("publisher_ID"));
				publishers.setPublisher_Name(rs.getString("publisher_Name"));
				publishers.setPublisher_Phone(rs.getString("publisher_Phone"));
				publishers.setPublisher_Address(rs.getString("publisher_Address"));
				publishers.setPublisher_Email(rs.getString("publisher_Email"));
				list.add(publishers);
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
	public void insert(Publisher publisher) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_PUBLISHER);

			pstmt.setString(1, publisher.getPublisher_ID());
			pstmt.setString(2, publisher.getPublisher_Name());
			pstmt.setString(3, publisher.getPublisher_Phone());
			pstmt.setString(3, publisher.getPublisher_Address());
			pstmt.setString(3, publisher.getPublisher_Email());

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

	public List<Publisher> findByPublisherName(String publisher_name) {
		List<Publisher> list = new ArrayList<Publisher>();
		Publisher publisher = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_PUBLISHERS_BY_NAME);
			pstmt.setString(1, publisher_name);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				publisher = new Publisher();
				publisher.setPublisher_ID(rs.getString("publisher_ID"));
				publisher.setPublisher_Name(rs.getString("publisher_Name"));
				publisher.setPublisher_Phone(rs.getString("publisher_Phone"));
				publisher.setPublisher_Address(rs.getString("publisher_Address"));
				publisher.setPublisher_Email(rs.getString("publisher_Email"));
				list.add(publisher);
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

	public Publisher findOneByPublisherName(String publisher_name) {
		Publisher publisher = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_PUBLISHER_BY_NAME);

			pstmt.setString(1, publisher_name);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				publisher = new Publisher();
				publisher.setPublisher_ID(rs.getString("publisher_ID"));
				publisher.setPublisher_Name(rs.getString("publisher_Name"));
				publisher.setPublisher_Phone(rs.getString("publisher_Phone"));
				publisher.setPublisher_Address(rs.getString("publisher_Address"));
				publisher.setPublisher_Email(rs.getString("publisher_Email"));
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
		return publisher;
	}

	@Override
	public List<String> findByPublisherNameLike(String publisherName) {
		List<String> publisherNames = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_PUBLISHER_BY_NAME_LIKE);
			pstmt.setString(1, publisherName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				publisherNames.add(rs.getString(1));
			}

		} catch (

		SQLException se) {
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
		return publisherNames;
	}

}
