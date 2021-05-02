package com.question.model;

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

public class QuestionJDBCDAO implements QuestionDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "BOOKSHOP";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO QUESTION VALUES ('BCQ'||TO_CHAR(BCQ_SEQ.NEXTVAL, 'FM0000'),?,?)";//
	// 搜尋所有問題
	private static final String GET_ALL_STMT = "SELECT BCQ_ID,BC_ID,Q_DATA FROM QUESTION ORDER BY BCQ_ID";
	// 搜尋一筆問題
	private static final String GET_ONE_STMT = "SELECT BCQ_ID,BC_ID,Q_DATA FROM QUESTION WHERE BCQ_ID = ?";
	// 搜尋所屬讀書會的問題
	private static final String GET_SOME_STMT = "SELECT BCQ_ID,BC_ID,Q_DATA FROM QUESTION WHERE BC_ID = ?";
	// 刪除問題
	private static final String DELETE = "DELETE FROM QUESTION WHERE BCQ_ID = ?";
	// 修改問題
	private static final String UPDATE = "UPDATE QUESTION SET Q_DATA = ? WHERE BCQ_ID = ?";

	public void insert(QuestionVO questionVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, questionVO.getBc_id());
			pstmt.setString(2, questionVO.getQ_data());
			pstmt.executeUpdate();
			System.out.println("新增成功 insert method");

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			se.printStackTrace();
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

	public void update(QuestionVO questionVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, questionVO.getQ_data());
			pstmt.setString(2, questionVO.getBcq_id());
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	}

	public void delete(String bcq_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, bcq_id);
			pstmt.executeUpdate();
			System.out.println("刪除成功 delete method");
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	}

	public QuestionVO findByPrimaryKey(String bcq_id) {
		QuestionVO questionVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, bcq_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				questionVO = new QuestionVO();
				questionVO.setBcq_id(rs.getString("bcq_id"));
				questionVO.setBc_id(rs.getString("bc_id"));
				questionVO.setQ_data(rs.getString("q_data"));
			}

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
		return questionVO;
	}

	public List<QuestionVO> getAll() {

		List<QuestionVO> list = new ArrayList<QuestionVO>();
		QuestionVO questionVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				questionVO = new QuestionVO();
				questionVO.setBcq_id(rs.getString("bcq_id"));
				questionVO.setBc_id(rs.getString("bc_id"));
				questionVO.setQ_data(rs.getString("q_data"));
				list.add(questionVO);
			}

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

	public List<QuestionVO> findByBcid(String bc_id) {

		List<QuestionVO> list = new ArrayList<QuestionVO>();
		QuestionVO questionVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_SOME_STMT);
			pstmt.setString(1, bc_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				questionVO = new QuestionVO();
				questionVO.setBcq_id(rs.getString("bcq_id"));
				questionVO.setBc_id(rs.getString("bc_id"));
				questionVO.setQ_data(rs.getString("q_data"));
				list.add(questionVO);
			}

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
		QuestionJDBCDAO dao = new QuestionJDBCDAO();

		// 新增
//		QuestionVO questionVO1 = new QuestionVO();
//		questionVO1.setBc_id("BC0002");
//		questionVO1.setQ_data("哈哈哈哈哈");
//		dao.insert(questionVO1);
//		System.out.println("新增成功 main method");

		// 刪除
//		dao.delete("BCQ0005");
//		System.out.println("刪除成功 main method");
		
		// 查詢一個(pk)
//		QuestionVO questionVO2 = dao.findByPrimaryKey("BCQ0007");
//		
//		System.out.println(questionVO2.getBcq_id());
//		System.out.println(questionVO2.getBc_id());
//		System.out.println(questionVO2.getQ_data());
		
		//查全部
//		List<QuestionVO> list = dao.getAll();
//		
//		for(QuestionVO allQuestion : list) {
//			System.out.println(allQuestion.getBcq_id() + ",");
//			System.out.println(allQuestion.getBc_id() + ",");
//			System.out.println(allQuestion.getQ_data() + ",");
//		}
		// 用bc_id查
//		List<QuestionVO> list = dao.findByBcid("BC0001");
//		
//		for(QuestionVO bc_idQuestionVO : list) {
//			
//			System.out.println(bc_idQuestionVO.getBcq_id());
//			System.out.println(bc_idQuestionVO.getBc_id());
//			System.out.println(bc_idQuestionVO.getQ_data());
//		}
		
		//修改
//		QuestionVO questionVO3 = new QuestionVO();
//		
//		questionVO3.setQ_data("你真棒棒棒棒棒棒");
//		questionVO3.setBcq_id("BCQ0007");
//		dao.update(questionVO3);
//		System.out.println("更改成功 main method");
	}
}
