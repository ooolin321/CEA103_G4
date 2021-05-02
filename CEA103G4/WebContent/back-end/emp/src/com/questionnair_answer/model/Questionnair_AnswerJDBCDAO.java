package com.questionnair_answer.model;

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

import com.question.model.QuestionVO;

public class Questionnair_AnswerJDBCDAO implements Questionnair_AnswerDAO_interface{
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "BOOKSHOP";
	String passwd = "123456";
	// 新增
	private static final String INSERT_STMT = "INSERT INTO QUESTIONNAIR_ANSWER (BRD_ID,BCQ_ID,QA_DATA) VALUES (?,?,?)";
	// 搜尋全部答案
	private static final String GET_ALL_STMT = "SELECT BRD_ID,BCQ_ID,QA_DATA FROM QUESTIONNAIR_ANSWER ORDER BY BCQ_ID";
	// 搜尋一筆答案
	private static final String GET_ONE_STMT = "SELECT BRD_ID,BCQ_ID,QA_DATA FROM QUESTIONNAIR_ANSWER WHERE BRD_ID = ? AND BCQ_ID = ?";
	// 刪除答案
	private static final String DELETE = "DELETE FROM QUESTIONNAIR_ANSWER WHERE BRD_ID = ? AND BCQ_ID = ?";
	// 更新答案
	private static final String UPDATE = "UPDATE QUESTIONNAIR_ANSWER SET QA_DATA = ? WHERE BRD_ID = ? AND BCQ_ID = ?";
	
	@Override
	public void insert(Questionnair_AnswerVO questionnair_AnswerVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, questionnair_AnswerVO.getBrd_id());
			pstmt.setString(2, questionnair_AnswerVO.getBcq_id());
			pstmt.setString(3, questionnair_AnswerVO.getQa_data());
			pstmt.executeUpdate();
			
			
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		}catch(SQLException se) {
			se.printStackTrace();
		}finally {
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
	public void update(Questionnair_AnswerVO questionnair_AnswerVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, questionnair_AnswerVO.getQa_data());
			pstmt.setString(2, questionnair_AnswerVO.getBrd_id());
			pstmt.setString(3, questionnair_AnswerVO.getBcq_id());
			
			pstmt.executeUpdate();
			
			
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		}catch(SQLException se) {
			se.printStackTrace();
		}finally {
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
	public void delete(String brd_id, String bcq_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, brd_id);
			pstmt.setString(2, bcq_id);
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

	@Override
	public Questionnair_AnswerVO findByPrimaryKey(String brd_id, String bcq_id) {
		Questionnair_AnswerVO questionnair_AnswerVO = new Questionnair_AnswerVO();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, brd_id);
			pstmt.setString(2, bcq_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				questionnair_AnswerVO = new Questionnair_AnswerVO();
				questionnair_AnswerVO.setBrd_id(rs.getString("brd_id"));
				questionnair_AnswerVO.setBcq_id(rs.getString("bcq_id"));
				questionnair_AnswerVO.setQa_data(rs.getString("qa_data"));
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
		return questionnair_AnswerVO;
	}

	@Override
	public List<Questionnair_AnswerVO> getAll() {
		List<Questionnair_AnswerVO> list = new ArrayList<Questionnair_AnswerVO>();
		Questionnair_AnswerVO questionnair_AnswerVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				questionnair_AnswerVO = new Questionnair_AnswerVO();
				questionnair_AnswerVO.setBrd_id(rs.getString("brd_id"));
				questionnair_AnswerVO.setBcq_id(rs.getString("bcq_id"));
				questionnair_AnswerVO.setQa_data(rs.getString("qa_data"));
				list.add(questionnair_AnswerVO);
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
//		Questionnair_AnswerJDBCDAO dao = new Questionnair_AnswerJDBCDAO(); 
		// 新增
//		Questionnair_AnswerVO questionnair_AnswerVO1 = new Questionnair_AnswerVO();
//		
//		questionnair_AnswerVO1.setBrd_id("BRD0003");
//		questionnair_AnswerVO1.setBcq_id("BCQ0003");
//		questionnair_AnswerVO1.setQa_data("資策會學生");
//		dao.insert(questionnair_AnswerVO1);
//		System.out.println("新增成功 main method");
		
		// 搜尋一筆
//		Questionnair_AnswerVO questionnair_AnswerVO2 = dao.findByPrimaryKey("BRD0003", "BCQ0003");
//		
//		System.out.println(questionnair_AnswerVO2.getBrd_id());
//		System.out.println(questionnair_AnswerVO2.getBcq_id());
//		System.out.println(questionnair_AnswerVO2.getQa_data());
		
		//更新
//		Questionnair_AnswerVO questionnair_AnswerVO3 = new Questionnair_AnswerVO();
//		questionnair_AnswerVO3.setBrd_id("BRD0003");
//		questionnair_AnswerVO3.setBcq_id("BCQ0003");
//		questionnair_AnswerVO3.setQa_data("你真棒棒棒棒棒棒棒棒棒");
//		dao.update(questionnair_AnswerVO3);
//		System.out.println("更改成功 main method");
		
		//查全部
//		List<Questionnair_AnswerVO> list = dao.getAll();
//		
//		for(Questionnair_AnswerVO allQuestionnair_AnswerVO : list) {
//			System.out.println(allQuestionnair_AnswerVO.getBrd_id() + ",");
//			System.out.println(allQuestionnair_AnswerVO.getBcq_id() + ",");
//			System.out.println(allQuestionnair_AnswerVO.getQa_data() + ",");
//		}
		
		// 刪除
//		dao.delete("BRD0003","BCQ0003");
//		System.out.println("刪除成功 main method");
	}

	@Override
	public List<Questionnair_AnswerVO> findByBrdid(String brd_id) {
		// TODO Auto-generated method stub
		return null;
	}

}
