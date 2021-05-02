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

public class Questionnair_AnswerJNDIDAO implements Questionnair_AnswerDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bookshop");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
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
	// 搜尋對應明細的答案
	private static final String SELECT_BY_BRDID = "SELECT BRD_ID,BCQ_ID,QA_DATA FROM QUESTIONNAIR_ANSWER WHERE BRD_ID = ?";

	@Override
	public void insert(Questionnair_AnswerVO questionnair_AnswerVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, questionnair_AnswerVO.getBrd_id());
			pstmt.setString(2, questionnair_AnswerVO.getBcq_id());
			pstmt.setString(3, questionnair_AnswerVO.getQa_data());
			pstmt.executeUpdate();

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

	@Override
	public void update(Questionnair_AnswerVO questionnair_AnswerVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, questionnair_AnswerVO.getQa_data());
			pstmt.setString(2, questionnair_AnswerVO.getBrd_id());
			pstmt.setString(3, questionnair_AnswerVO.getBcq_id());

			pstmt.executeUpdate();

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

	@Override
	public void delete(String brd_id, String bcq_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, brd_id);
			pstmt.setString(2, bcq_id);
			pstmt.executeUpdate();
			System.out.println("刪除成功 delete method");
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

			con = ds.getConnection();
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				questionnair_AnswerVO = new Questionnair_AnswerVO();
				questionnair_AnswerVO.setBrd_id(rs.getString("brd_id"));
				questionnair_AnswerVO.setBcq_id(rs.getString("bcq_id"));
				questionnair_AnswerVO.setQa_data(rs.getString("qa_data"));
				list.add(questionnair_AnswerVO);
			}

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

	@Override
	public List<Questionnair_AnswerVO> findByBrdid(String brd_id) {
		List<Questionnair_AnswerVO> list = new ArrayList<Questionnair_AnswerVO>();
		Questionnair_AnswerVO questionnair_AnswerVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(SELECT_BY_BRDID);
			pstmt.setString(1, brd_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				questionnair_AnswerVO = new Questionnair_AnswerVO();
				questionnair_AnswerVO.setBrd_id(rs.getString("brd_id"));
				questionnair_AnswerVO.setBcq_id(rs.getString("bcq_id"));
				questionnair_AnswerVO.setQa_data(rs.getString("qa_data"));
				list.add(questionnair_AnswerVO);
			}

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
}
