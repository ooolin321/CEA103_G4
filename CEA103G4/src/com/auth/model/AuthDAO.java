package com.auth.model;

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



public class AuthDAO implements AuthDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CEA103_G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "insert into Auth (EMPNO,FUNNO) values (?, ?)";
	private static final String UPDATE_STMT = "update Auth set STATE=? where EMPNO=? and FUNNO=?";
	private static final String DELETE_STMT = "delete from Auth where EMPNO=? and FUNNO=?";
	private static final String GET_ONE_BY_EMPNO_STMT = "select * from Auth where EMPNO = ?";
	private static final String GET_ALL_BY_EMPNO_STMT = "select * from Auth order by EMPNO";

	@Override
	public void insert(AuthVO authVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, authVO.getEmpno());
			pstmt.setInt(2, authVO.getFunno());

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
	public void update(AuthVO authVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setInt(1, authVO.getState());
			pstmt.setInt(2, authVO.getEmpno());
			pstmt.setInt(3, authVO.getFunno());

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
	public void delete(Integer empno, Integer funno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setInt(1, empno);
			pstmt.setInt(2, funno);

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
	public AuthVO findByEmpNo(Integer empno) {
		AuthVO authVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_EMPNO_STMT);

			pstmt.setInt(1, empno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// authorityVo 也稱為 Domain objects
				authVO = new AuthVO();
				authVO.setEmpno(rs.getInt("EMPNO"));
				authVO.setFunno(rs.getInt("FUNCTION_NO"));
				authVO.setState(rs.getInt("STATE"));
			}
		}  catch (SQLException se) {
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
		return authVO;
	}

	@Override
	public List<AuthVO> getAll() {
		List<AuthVO> list = new ArrayList<AuthVO>();
		AuthVO authVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_EMPNO_STMT);
			rs = pstmt.executeQuery();
			

			while (rs.next()) {
				// authorityVO 也稱為 Domain objects
				authVO = new AuthVO();
				authVO.setEmpno(rs.getInt("EMPNO"));
				authVO.setFunno(rs.getInt("FUNNO"));
				authVO.setState(rs.getInt("STATE"));
				list.add(authVO); // Store the row in the list
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

}
